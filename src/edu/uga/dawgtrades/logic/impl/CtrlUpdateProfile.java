package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.ObjectModel;

public class CtrlUpdateProfile {
	private ObjectModel objectModel = null;
	
	public CtrlUpdateProfile(ObjectModel objectModel){
		this.objectModel = objectModel;
	}
	
	public long updateProfile(String user_name, String first_name, String last_name, String password, String email, String phone, boolean canText) throws DTException{
        
        RegisteredUser user = null, modelUser = null;
        Iterator userIter = null;
        
        modelUser = objectModel.createRegisteredUser();
        modelUser.setName(user_name);
        userIter = objectModel.findRegisteredUser(modelUser);
        if (!userIter.hasNext())
            throw DTException("User not found: " + user_name);
        
        user = userIter.next();
        user.setFirstName(first_name);
        user.setLastName(last_name);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setCanText(canText);
        
        objectModel.storeRegisteredUser(objectModel);
        return user.getId();
        
	}

}
