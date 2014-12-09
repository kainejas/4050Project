package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;

public class CtrlUnregister {
	private ObjectModel objectModel = null;
	
	public CtrlUnregister(ObjectModel objectModel){
		this.objectModel = objectModel;
	}
	
	public void unregister(String user_name) throws DTException{
        RegisteredUser user = null, modelUser = null;
        Iterator<RegisteredUser> userIter = null;
        
        modelUser = objectModel.createRegisteredUser();
        modelUser.setName(user_name);
        userIter = objectModel.findRegisteredUser(modelUser);
        if (userIter.hasNext())
            user = userIter.next();
        else
            throw new DTException("User not found: " + user_name);
        
        Iterator<Item> items = objectModel.getItem(user);
        if (items.hasNext()) {
        	if (!objectModel.getAuction(items.next()).getIsClosed())
        		throw new DTException("Cannot unregister. " + user_name + " has item(s) currently being auctioned.");
        }
        
        objectModel.deleteRegisteredUser(user);
        
	}
}
