package com.accountsservice.models;



import com.accountsservice.controllers.beans.abstractdata.AbstractDataSection;
import com.accountsservice.controllers.beans.abstractdata.AbtractDataField;
import com.accountsservice.services.AccountsService;
import com.accountsservice.utils.AbstractResult;
import com.accountsservice.utils.ValidationUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account extends AbstractEntity implements IAbstractData {

    public static int ACCOUNT_TYPE_BUSINESS = 1;
    public static int ACCOUNT_TYPE_CUSTOMER = 2;

    @Getter
    @Setter
    @Column(name = "account_code")
    private String accountCode;

    @Getter
    @Setter
    @Column(name = "physical_address")
    private String physicalAddress;

    @Getter
    @Setter
    @Column(name = "email_address")
    private String emailAddress;

    @Getter
    @Setter
    @Column(name = "contact_number")
    private String contactNumber;

    @Getter
    @Setter
    @Column(name = "alternate_contact_number")
    private String alternateContactNumber;

    @Getter
    @Setter
    @Column(name = "bank_account_number")
    private String bankAccountNumber;

    @Getter
    @Setter
    @Column(name = "debit_day")
    private int debitDay;

    @Getter
    @Setter
    @Column(name = "credit_card_number")
    private String creditCardNumber;

    @Getter
    @Setter
    @Column(name = "credit_card_cvv")
    private String creditCardCvv;

    @Getter
    @Setter
    @Column(name = "credit_card_expiry_date")
    private Date creditCardExpiryDate;

    @Getter
    @Setter
    @Column(name = "type_id")
    private int typeId;

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
    @Column(name = "business_name")
    private String businessName;

    @Getter
    @Setter
    @Column(name = "registration_number")
    private String registrationNumber;

    @Getter
    @Setter
    @Column(name = "tax_number")
    private String taxNumber;

    private transient String password;


    public Account(String firstName, String lastName, String physicalAddress, String emailAddress,
            String contactNumber, String alternateContactNumber, String bankAccountNumber, int debitDay,
            String creditCardNumber,
            String creditCardCvv, Date creditCardExpiryDate, int typeId, String password) {
        super();
        this.accountCode = constructAccountCode(emailAddress, contactNumber);
        this.physicalAddress = physicalAddress;
        this.emailAddress = emailAddress;
        this.contactNumber = contactNumber;

        this.alternateContactNumber = alternateContactNumber;
        this.bankAccountNumber = bankAccountNumber;
        this.debitDay = debitDay;
        this.creditCardNumber = creditCardNumber;
        this.creditCardCvv = creditCardCvv;
        this.creditCardExpiryDate = creditCardExpiryDate;
        this.typeId = typeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public Account(String firstName, String lastName, String businessName, String physicalAddress,
            String registrationNumber,
            String emailAddress, String taxNumber, String contactNumber, String alternateContactNumber,
            String cryptBankAccountNumber, int debitDay, String cryptCreditCardNumber, String creditCardCvv,
            Date creditCardExpiryDate, String password) {
        this(firstName, lastName, physicalAddress, emailAddress, contactNumber, alternateContactNumber,
                cryptBankAccountNumber, debitDay,
                cryptCreditCardNumber, creditCardCvv, creditCardExpiryDate, ACCOUNT_TYPE_BUSINESS, password);

        this.businessName = businessName;
        this.registrationNumber = registrationNumber;
        this.taxNumber = taxNumber;
    }

    @Override
    public AbstractResult validate(boolean forInsert) {
        AbstractResult result = new AbstractResult();

        if (physicalAddress == null || physicalAddress.length() < 5) {
            result.addError("Please specify a physical address");
        }

        if (!ValidationUtil.isValidEmailAddress(emailAddress)) {
            result.addError("Please specify a valid email address");
        }

        if (!ValidationUtil.isValidPhoneNumber(contactNumber)) {
            result.addError("Please specify a valid contact number");
        }

        if (ValidationUtil.isObjectNotNullOrEmptyOrZero(this.alternateContactNumber)
                && !ValidationUtil.isValidPhoneNumber(alternateContactNumber)) {
            result.addError("Please specify a valid alternate contact number");
        }

        if (ValidationUtil.isObjectNullOrEmptyOrZero(this.firstName)) {
            result.addError("Please specify a first name");
        }

        if (ValidationUtil.isObjectNullOrEmptyOrZero(this.lastName)) {
            result.addError("Please specify a last name");
        }

        if (ValidationUtil.isObjectNullOrEmptyOrZero(this.password)) {
            result.addError("Please specify a password");
        }

        return result;
    }

    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Transient
    public String getPassword() {
        return this.password;
    }

    @Transient
    public List<AbstractDataSection> getEntityData() {
        
        List<AbstractDataSection> data = new ArrayList<>();

        List<AbtractDataField> businessDetails = new ArrayList<>();
        businessDetails.add(new AbtractDataField("Name", getBusinessName()));
        businessDetails.add(new AbtractDataField("Registration Number", getRegistrationNumber()));
        data.add(new AbstractDataSection("Business Details", businessDetails));

        List<AbtractDataField> accountFields = new ArrayList<>();
        accountFields.add(new AbtractDataField("Email Address", getEmailAddress()));
        accountFields.add(new AbtractDataField("Contact Number", getContactNumber()));
        accountFields.add(new AbtractDataField("Alternate Contact Number", getAlternateContactNumber()));
        data.add(new AbstractDataSection("Contact Details", accountFields));

        // Customer Address section
        List<AbtractDataField> addressFields = new ArrayList<>();
        addressFields.add(new AbtractDataField("Physical Address", getPhysicalAddress()));
        data.add(new AbstractDataSection("Address Details", addressFields));

        // Customer Banking Details section
        List<AbtractDataField> bankingFields = new ArrayList<>();
        bankingFields.add(new AbtractDataField("First Name", getFirstName()));
        data.add(new AbstractDataSection("Banking Details", bankingFields));

        return data;
    }

    @Transient
    public Map<String, Object> getEntityDataMap() {
        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("businessName", this.businessName);
        dataMap.put("physicalAddress", this.physicalAddress);

        return dataMap;
    }

    /*
     * Create customer account code using the email and contact number
     */
    private String constructAccountCode(String emailAddress, String contactNumber) {
        String email = emailAddress.substring(0, emailAddress.indexOf("@"));
        String number = contactNumber.substring(contactNumber.length() - 4);

        return email + number;
    }
}
