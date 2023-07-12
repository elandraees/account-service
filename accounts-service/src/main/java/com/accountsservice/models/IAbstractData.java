package com.accountsservice.models;

import java.util.List;
import java.util.Map;

import com.accountsservice.controllers.beans.abstractdata.AbstractDataSection;

public interface IAbstractData {

    public List<AbstractDataSection> getEntityData();

    public Map<String, Object> getEntityDataMap();
}