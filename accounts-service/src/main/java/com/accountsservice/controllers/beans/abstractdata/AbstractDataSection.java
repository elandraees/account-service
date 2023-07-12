package com.accountsservice.controllers.beans.abstractdata;

import java.util.List;


public class AbstractDataSection{

    public String title;
    public List<AbtractDataField> fields;

    public AbstractDataSection(String title, List<AbtractDataField> fields) {
        this.title = title;
        this.fields = fields;
    }

}
