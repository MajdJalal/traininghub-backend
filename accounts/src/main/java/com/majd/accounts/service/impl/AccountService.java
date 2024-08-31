package com.majd.accounts.service.impl;


import com.majd.accounts.dto.AccountRequestDto;
import com.majd.accounts.exception.InvalidDataException;
import com.majd.accounts.service.IAccountService;
import jakarta.ws.rs.core.Response;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.keycloak.representations.AccessTokenResponse;

import java.util.Collections;
import java.util.Objects;

@Service
public class AccountService implements IAccountService {


    private  final Logger logger=  LoggerFactory.getLogger(AccountService.class);

    private final PasswordEncoder passwordEncoder;
//    private final Keycloak keycloak;
    private final RealmResource realmResource;


    public AccountService(PasswordEncoder passwordEncoder, RealmResource realmResource) {
        this.passwordEncoder = passwordEncoder;
        this.realmResource = realmResource;
    }

    //TO_DO
    /*
        TO-DO 1: handle constraints on the users in keycloak(uniqueness of name,...)
        TO-DO 2: decouple client details from the business logic of new user or log user, or any other solution but u have to make the code cleaner
        TO-DO 3 : add roles to users
        TO-DO 4: try to document the roles that each client need to operate any operation in keycloak
    */

    @Override
    public void createAccount(AccountRequestDto accountRequestDto) {

        //extract email and password from dto
        String name = accountRequestDto.getName();
        String email = accountRequestDto.getEmail();
        String password  = accountRequestDto.getPassword();
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
        } catch (Exception e) {
            logger.error(e + " failed to create user ");

        }

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
