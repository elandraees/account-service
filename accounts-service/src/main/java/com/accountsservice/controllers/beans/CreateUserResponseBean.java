package com.accountsservice.controllers.beans;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CreateUserResponseBean implements Serializable {

    @Getter
    private String emailAddress;
    @Getter
    private String fullName;

    public CreateUserResponseBean(String emailAddress, String fullName) {
        this.emailAddress = emailAddress;
        this.fullName = fullName;
    }

}
