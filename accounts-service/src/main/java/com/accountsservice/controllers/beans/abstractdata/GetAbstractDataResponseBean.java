package com.accountsservice.controllers.beans.abstractdata;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import jakarta.xml.bind.annotation.XmlElement;

import com.accountsservice.models.IAbstractData;

import lombok.Getter;

/*
 * JSON structure:
 * { 
 *     data: [
 *              {
 *                 title: "", 
 *                 fields:[
 *                          {fieldName:"", fieldValue:""}, 
 *                          {...}, 
 *                          {...}
 *                        ],
 *              }, 
 *              {....}
 *           ],
 *    dataMap: {'fieldName':'fieldValue', ....}
 * 
 * }
 */

public class GetAbstractDataResponseBean implements Serializable {

    @XmlElement
    @Getter
    public List<AbstractDataSection> data;

    @XmlElement
    @Getter
    public Map<String, Object> dataMap;

    public GetAbstractDataResponseBean(IAbstractData data) {

        this.data = data.getEntityData();
        this.dataMap = data.getEntityDataMap();
    }

    public List<AbstractDataSection> getData() {
        return data;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

}
