package com.accountsservice.services;

import com.accountsservice.models.AbstractEntity;
import com.accountsservice.utils.AbstractResult;
import com.accountsservice.utils.LogManager;

import org.springframework.data.jpa.repository.JpaRepository;

public class AbstractService {

    /*
     * This will only return an AbstractResult if its successful or model validation
     * fails, else throws an exception
     * 
     * Method can be used to create or update an entity
     */

    @SuppressWarnings("rawtypes")
    public AbstractResult saveSingleObject(AbstractEntity entity, JpaRepository repo, boolean forInsert) throws Exception {
        AbstractResult result = new AbstractResult();
        try {

            result = entity.validate(forInsert);
            if (result.isSuccess()) {
                Object object = repo.save(entity);
                if (object == null)
                    throw new Exception("An error has occured");
                else
                    result.addToResult(object);
            }
        } 
        catch (Exception e) {
            LogManager.addError(e.getMessage(), this.getClass());
            throw new Exception(e.getMessage());
            
        }

        return result;
    }

}
