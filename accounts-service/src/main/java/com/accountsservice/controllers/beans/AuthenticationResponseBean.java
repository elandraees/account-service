package com.accountsservice.controllers.beans;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;

public class AuthenticationResponseBean implements Serializable {

    @Getter
    private final String token;

    @Getter
    private final Date expirationDate;

    public AuthenticationResponseBean(String token, Date expirationDate) {
        this.token = token;
        this.expirationDate = expirationDate;
    }

}
