package com.majd.accounts.service.impl;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.majd.accounts.dto.AccountRequestDto;
import com.majd.accounts.dto.LoginDto;
import com.majd.accounts.model.AccountModel;
import com.majd.accounts.service.IAccountService;
import com.majd.accounts.webrequest.KeycloakFeignClient;
import com.majd.accounts.webrequest.TokenFeignClient;
import com.majd.accounts.webrequest.UserFeignClient;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
public class AccountService implements IAccountService {

    //reading client_id and client_secret from env variables.
    @Value("${client_id}")
    String client_id;
    @Value("${client_secret}")
    String client_secret;

    private static final String ACCOUNTS_CACHE_KEY = "accounts";

    private  final Logger logger=  LoggerFactory.getLogger(AccountService.class);

    private final PasswordEncoder passwordEncoder;
//    private final Keycloak keycloak;
    private final RealmResource realmResource;
    private  final KeycloakFeignClient keycloakFeignClient;
    private  final UserFeignClient userFeignClient;

    private final TokenFeignClient tokenFeignClient;

    private final RedisTemplate<String, Object> redisTemplate;

    public AccountService(PasswordEncoder passwordEncoder, RealmResource realmResource, KeycloakFeignClient keycloakFeignClient, UserFeignClient userFeignClient, TokenFeignClient tokenFeignClient, RedisTemplate<String, Object> redisTemplate) {
        this.passwordEncoder = passwordEncoder;
        this.realmResource = realmResource;
        this.keycloakFeignClient = keycloakFeignClient;
        this.userFeignClient = userFeignClient;
        this.tokenFeignClient = tokenFeignClient;
        this.redisTemplate = redisTemplate;
    }

    //TO_DO
    /*
        TO-DO 1: handle constraints on the users in keycloak(uniqueness of name,...)
        TO-DO 2: decouple client details from the business logic of new user or log user, or any other solution but u have to make the code cleaner
        TO-DO 3 : add roles to users
        TO-DO 4: try to document the roles that each client need to operate any operation in keycloak
    */

    @Override
    public boolean createAccount(AccountRequestDto accountRequestDto) {

        //extract email and password from dto
        String name = accountRequestDto.getName();
        String email = accountRequestDto.getEmail();
        String password  = accountRequestDto.getPassword();
        String role = accountRequestDto.getRole();
        logger.info(name + " " + email + " " + password);
        //define the user
        UserRepresentation user = new UserRepresentation();
        user.setUsername(name);
        user.setEmail(email);
        user.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);

        //add credential to user
        user.setCredentials(Collections.singletonList(credential));

        try {
            UsersResource usersResource = realmResource.users();
            // Create user (requires manage-users role)
            Response response = usersResource.create(user);
            if(!Objects.equals(response.getStatus(), 201)) throw new Exception("invalid data");
            // Get the user ID from the response location header
            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
            // Assign roles to the user
            boolean isRoleAssigned = assignRoleToUser(userId, role);
            if(!isRoleAssigned) return false;
        } catch (Exception e) {
            logger.error(e + " failed to create user ");
            return false;
        }
        return true;
    }

    private boolean assignRoleToUser(String userId, String roleName) {
        try {
            UsersResource usersResource = realmResource.users();
            UserResource userResource = usersResource.get(userId);
            // Get the role representation
            RoleRepresentation roleRepresentation = realmResource.roles().get(roleName).toRepresentation();
            // Assign the role to the user
            userResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));
        } catch (Exception e) {
            logger.error("Failed to assign role to user: " + e.getMessage());
            // Throw some custom exception
            return false;
        }
        return true;
    }

    public String login(LoginDto loginDto) throws Exception {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        String token = null;
        try {
            // Get the users resource
            UsersResource usersResource = realmResource.users();
            // Query the user by email
            List<UserRepresentation> users = usersResource.searchByEmail(email, true);

            if (users.isEmpty()) {
                logger.error("User with email " + email + " not found.");
                throw new Exception("User not found.");
            }

            // The user exists, now we need to validate the password
            UserRepresentation user = users.get(0);

            // Assuming you are using Keycloak's direct access authentication method
            token = getAccessTokenPasswordFlow(email, password);

        } catch (Exception e) {
            logger.error("Error during login: " + e.getMessage());
            throw new Exception("login failed.");
        }
        return token;
    }

    /**
     * return a list of accounts models from keycloak
     */
    @Override
    public List<AccountModel> getAccounts() {
        // Try to get accounts from Redis cache
        Object cachedAccounts = getFromRedis(ACCOUNTS_CACHE_KEY);
        if(cachedAccounts != null && !((List<AccountModel>)cachedAccounts).isEmpty()) return (List<AccountModel>) cachedAccounts;
        //else get the data(accounts) yourself from keycloak realm
        String accessToken =  getAccessToken();
        logger.info(accessToken);
        // Call the Keycloak Admin API to fetch users
        try {
            String users = userFeignClient.getUsers("Bearer " + extractAccessToken(accessToken));
            logger.info("Users: " + users);
            ObjectMapper objectMapper = new ObjectMapper();
            List<AccountModel> accountModels = objectMapper.readValue(users, new TypeReference<List<AccountModel>>() {});
            redisTemplate.opsForValue().set(ACCOUNTS_CACHE_KEY, accountModels, 1, TimeUnit.MINUTES);
            return accountModels;
        } catch (Exception e) {
            logger.error("Error fetching users: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    /**
     * general method that accepts a redis key and returns an Object or null
     * the caller handles the conversion of the Object to the actual type of data
     * @param key
     * @return
     */
    private Object getFromRedis(String key){
        Object cachedData =  redisTemplate.opsForValue().get(key);
        if (cachedData != null) {
            logger.info("Returning data from Redis cache");
            return cachedData;
        }
        return null;
    }

    /**
     * as token is received with other info as JSON string i have to extract the accessToken alone
     * @param jsonString
     * @return
     */
    private String extractAccessToken(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            return jsonNode.get("access_token").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return accessToken (with additional info)
     */
    private String getAccessToken() {
        Map<String, String> formParams = new HashMap<>();
        // credentials are extracted from env variables
        formParams.put("client_id", client_id);
        formParams.put("client_secret", client_secret);
        formParams.put("grant_type", "client_credentials");

        return keycloakFeignClient.getAccessToken(formParams);
    }

    private String getAccessTokenPasswordFlow(String name, String password) {
        Map<String, String> formParams = new HashMap<>();
        // credentials are extracted from env variables
        formParams.put("client_id", "client2");
//        formParams.put("client_secret", client_secret);
        formParams.put("grant_type", "password");
        formParams.put("username", name);
        formParams.put("password", password);
        logger.debug(name);
        return tokenFeignClient.getToken(formParams);
    }




//    public void assignRole(String userId, String roleName) {
//        UserResource userResource = realmResource.users().get(userId);
//        RoleRepresentation role = realmResource.roles().get(roleName).toRepresentation();
//        userResource.roles().realmLevel().add(Collections.singletonList(role));
//    }

//    @Override
//    public String login(AccountRequestDto accountRequestDto) {
////        String serverUrl = "http://localhost:7080";
////        String realm = "trainingHubV1";
////        String clientId = "client2";
////        String clientSecret = "KEkQWX4IphRNl3kjkwuO2xAvVrQzT8zP";
////        try {
////            Keycloak keycloak = KeycloakBuilder.builder()
////                    .serverUrl(serverUrl)
////                    .realm(realm)
////                    .clientId(clientId)
////                    .clientSecret(clientSecret)
////                    .grantType(OAuth2Constants.PASSWORD)
////                    .username(accountRequestDto.getName())
////                    .password(accountRequestDto.getPassword())
////                    .build();
////
////            AccessTokenResponse tokenResponse = keycloak.tokenManager().getAccessToken();
////
////            return tokenResponse.getToken();
////        } catch (Exception e) {
////            throw new InvalidDataException(e.getMessage());
////        }
//    }
}
