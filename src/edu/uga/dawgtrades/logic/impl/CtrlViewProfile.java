package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.ObjectModel;

public class CtrlViewProfile {
	private ObjectModel objectModel = null;
	
	public CtrlViewProfile(ObjectModel objectModel){
		this.objectModel = objectModel;
	}
	
	public long viewProfile(String user_name) throws DTException{
        
        RegisteredUser user = null, modelUser = null;
        Iterator<RegisteredUser> userIter = null;
        
        modelUser = objectModel.createRegisteredUser();
        modelUser.setName(user_name);
        userIter = objectModel.findRegisteredUser(modelUser);
        if (!userIter.hasNext())
            throw new DTException("User not found: " + user_name);
        
    
        user = userIter.next();
        return user.getId();
        
	}

}
