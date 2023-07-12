package com.accountsservice.models;


import com.accountsservice.controllers.beans.UserUpdateRequestBean;
import com.accountsservice.utils.AbstractResult;
import com.accountsservice.utils.EncryptionUtil;
import com.accountsservice.utils.ValidationUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends AbstractEntity {

    public static int PRIMARY_USER = 1;
    public static int SECONDARY_USER = 2;

    @Getter
    @Setter
    @Column(name = "account_id")
    private long accountId;

    @Getter
    @Setter
    @Column(name = "username")
    private String username;

    @Getter
    @Setter
    @Column(name = "password")
    private String password;

    @Getter
    @Setter
    @Column(name = "first_name")
    private String firstName;

    @Getter
    @Setter
    @Column(name = "last_name")
    private String lastName;

    @Getter
    @Setter
    @Column(name = "email_address")
    private String emailAddress;

    @Getter
    @Setter
    @Column(name = "mobile_number")
    private String mobileNumber;

    @Getter
    @Setter
    @Column(name = "type_id")
    private int typeId;

    private transient String accountCode;

    public User(long accountId, String username, String password, String firstName,
            String lastName, String emailAddress, String mobileNumber, int typeId) {
        super();
        this.accountId = accountId;
        this.username = username;
        this.password = EncryptionUtil.encyptString(password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.mobileNumber = mobileNumber;
        this.typeId = typeId;
    }

    public void update(UserUpdateRequestBean user) {
        this.password = EncryptionUtil.encyptString(user.password);
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.emailAddress = user.emailAddress;
        this.mobileNumber = user.mobileNumber;
    }

    @Override
    public AbstractResult validate(boolean forInsert) {
        AbstractResult result = new AbstractResult();

        // TODO add validation

        if(!ValidationUtil.isValidFirstName(this.firstName))
            result.addError("firstName", "Please specify a valid first name.");

        if(!ValidationUtil.isValidLastName(this.lastName))
            result.addError("lastName", "Please specify a valid last name.");

        if(ValidationUtil.isObjectNullOrEmptyOrZero(this.accountId))
            result.addError("accountCode", "No account attached to this user, please specify an account.");

        return result;
    }

    @Transient
    public String getAccountCode() {
        return this.accountCode;
    }

    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
