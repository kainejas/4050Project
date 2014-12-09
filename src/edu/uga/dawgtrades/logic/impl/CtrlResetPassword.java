package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.ObjectModel;

public class CtrlResetPassword {
	private ObjectModel objectModel = null;
	
	public CtrlResetPassword(ObjectModel objectModel){
		this.objectModel = objectModel;
	}
	
	public void resetPassword(String email) throws DTException{
        
        RegisteredUser user = null, modelUser = null;
        Iterator<RegisteredUser> userIter = null;
        
        modelUser = objectModel.createRegisteredUser();
        modelUser.setEmail(email);
        userIter = objectModel.findRegisteredUser(modelUser);
        if (!userIter.hasNext())
            throw new DTException("Invalid email: " + email);
        
        user = userIter.next();
        
        
        //TODO Email the user
        
	}

}
