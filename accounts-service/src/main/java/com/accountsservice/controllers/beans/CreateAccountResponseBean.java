package com.accountsservice.controllers.beans;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class CreateAccountResponseBean implements Serializable {

    @Getter
    @Setter
    private String accountCode;
    @Getter
    @Setter
    private String name;

    public CreateAccountResponseBean(String accountCode, String name) {
        this.accountCode = accountCode;
        this.name = name;
    }

}
