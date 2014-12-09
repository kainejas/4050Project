package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.ObjectModel;

public class CtrlRegister {
	private ObjectModel objectModel = null;
	
	public CtrlRegister(ObjectModel objectModel){
		this.objectModel = objectModel;
	}
	
	public long register(String user_name, String first_name, String last_name, String password, boolean isAdmin, String email, String phoneNumber, boolean canText) throws DTException{
        
        RegisteredUser user = null, modelUser = null;
        Iterator<RegisteredUser> userIter = null;
        
        modelUser = objectModel.createRegisteredUser(user_name,first_name,last_name,password,isAdmin,email,phoneNumber,canText);
        userIter = objectModel.findRegisteredUser(modelUser);
        if (userIter.hasNext())
            throw new DTException("A user with this name already exists: " + user_name);
        
        objectModel.storeRegisteredUser(modelUser);
        
        return modelUser.getId();
        
	}
}
