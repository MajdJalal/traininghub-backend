package com.majd.accounts.config;


import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
//@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @since 31/8 removes the security from accounts to the api gateway
     * @author Majd Alkhawaja
     */
    //NOTE: we make sure that the access token is valid based on a certificate we have (configured in the properties), (public key)
//    @Bean
//    public SecurityFilterChain customSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeHttpRequests(exchanges -> exchanges
//                        .requestMatchers(HttpMethod.POST, "/accounts/v1").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/accounts/v1").authenticated()
//        ).oauth2ResourceServer(oAuth2ResourceServerSpec ->
//                oAuth2ResourceServerSpec.jwt(Customizer.withDefaults()));
//        httpSecurity.csrf(csrfSpec -> csrfSpec.disable());
//        return  httpSecurity.build();
//    }


}
