package com.accountsservice.utils;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/*
Class to record action results, so we can build up results during run time execution
 */

public class AbstractResult {

    @Getter @Setter
    private boolean isSuccess;

    @Getter @Setter
    private List<String> errors;
    @Getter @Setter
    private List<String> errorsFields;

    @Getter @Setter
    private List<Object> results;

    public AbstractResult() {
        this.isSuccess = true;
        this.errors = new ArrayList<>();
        this.results = new ArrayList<>();
        this.errorsFields = new ArrayList<>();
    }

    public void addError(String errorMessage) {
        this.isSuccess = false;
        this.errors.add(errorMessage);
    }

    public void addError(String field, String errorMessage) {
        this.isSuccess = false;
        this.errors.add(errorMessage);
        this.errorsFields.add(field);
    }

    public String getErrorsAsSingleString() {
        return String.join("\n", errors);

    }

    public void addToResult(Object object) {
        results.add(object);
    }
}
