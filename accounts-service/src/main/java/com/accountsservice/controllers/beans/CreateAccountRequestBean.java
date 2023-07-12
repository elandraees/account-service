package com.accountsservice.controllers.beans;

import java.io.Serializable;
import java.util.Date;

public class CreateAccountRequestBean implements Serializable {

    public String physicalAddress;
    public String emailAddress;
    public String contactNumber;
    public String alternateContactNumber;
    public String cryptBankAccountNumber;
    public int debitDay;
    public String cryptCreditCardNumber;
    public String creditCardCvv;
    public Date creditCardExpiryDate;
    public int typeId;
    public String firstName;
    public String lastName;
    public String businessName;
    public String registrationNumber;
    public String taxNumber;
    public String password;

}
