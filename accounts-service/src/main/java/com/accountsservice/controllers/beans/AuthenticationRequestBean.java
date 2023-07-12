package com.accountsservice.controllers.beans;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Need default constructor for JSON Parsing
@NoArgsConstructor
public class AuthenticationRequestBean implements Serializable {

    @Getter @Setter
    private String username;
    @Getter @Setter
    private String password;

    public AuthenticationRequestBean(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

}
