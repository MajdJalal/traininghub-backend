package com.majd.accounts.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountModel {

    private String id;
    private long createdTimestamp;
    private String username;
    private boolean enabled;
    private boolean totp;
    private boolean emailVerified;
    private String email;
    private List<String> disableableCredentialTypes;
    private List<String> requiredActions;
    private int notBefore;

//    private Access access; //TODO

}