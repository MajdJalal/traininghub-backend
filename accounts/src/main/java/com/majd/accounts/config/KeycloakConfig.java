package com.majd.accounts.config;


import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Value("${keycloak.server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String keycloakRealm;

    @Value("${keycloak.client-id}")
    private String keycloakClientId;

    @Value("${keycloak.client-secret}")
    private String keycloakClientSecret;

    @Value("${keycloak.admin-username}")
    private String keycloakAdminUsername;

    @Value("${keycloak.admin-password}")
    private String keycloakAdminPassword;
    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .realm(keycloakRealm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(keycloakClientId)
                .clientSecret(keycloakClientSecret)
                .username(keycloakAdminUsername)
                .password(keycloakAdminPassword)
                .build();
    }

    @Bean
    public RealmResource realmResource(Keycloak keycloak) {
        return keycloak.realm(keycloakRealm);
    }


}
