package com.majd.accounts.webrequest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "keycloak-client", url = "http://localhost:7080/realms/trainingHubV1/protocol/openid-connect/token")
public interface KeycloakFeignClient {


    /**
     * When interacting with OAuth2 authorization servers
     * (like Keycloak, Google, or other identity providers),
     * the application/x-www-form-urlencoded encoding is often required when exchanging client credentials,
     * authorization codes, or refreshing tokens.
     * @param formParams
     * @return
     */
    @PostMapping(value = "", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String getAccessToken(@RequestBody Map<String, ?> formParams);
}
