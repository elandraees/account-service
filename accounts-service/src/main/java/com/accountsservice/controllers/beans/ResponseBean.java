package com.accountsservice.controllers.beans;

import javax.xml.bind.annotation.XmlElement;

import com.accountsservice.utils.AbstractResult;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ResponseBean implements Serializable{

    @XmlElement
    @Getter
    @Setter
    private boolean success = true;
    @XmlElement
    @Getter
    @Setter
    private String message;
    @XmlElement
    @Getter
    @Setter
    private List<?> result = new ArrayList<Object>();
    @XmlElement
    @Getter
    @Setter
    private List<String> errorFields = new ArrayList<String>();

    public void setDetails(boolean success, String message, List<Object> result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }

    public void setDetailsFromResult(AbstractResult result) {
        this.success = result.isSuccess();
        this.message = result.getErrorsAsSingleString();
        this.errorFields = result.getErrorsFields();
        this.result = result.getResults();
    }

    
}
