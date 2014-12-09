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
	
	public void unregister(RegisteredUser user) throws DTException{
        
        
        Iterator<Item> items = objectModel.getItem(user);
        if (items.hasNext()) {
        	if (!objectModel.getAuction(items.next()).getIsClosed())
        		throw new DTException("Cannot unregister. " + user.getName() + " has item(s) currently being auctioned.");
        }
        
        objectModel.deleteRegisteredUser(user);
        
	}
}
