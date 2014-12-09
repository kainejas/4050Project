package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.ObjectModel;

public class CtrlViewMyAuctions {
	private ObjectModel objectModel = null;
	
	public CtrlViewMyAuctions(ObjectModel objectModel){
		this.objectModel = objectModel;
	}
	
	public List<Auction> viewMyAuctions(String user_name) throws DTException{
		
        
        RegisteredUser modelUser = objectModel.createRegisteredUser();
        modelUser.setName(user_name);
        
        RegisteredUser user = objectModel.findRegisteredUser(modelUser).next();
        
        Iterator<Item> itemIter = objectModel.getItem(user);
        
        List<Auction> auctionList = new LinkedList<Auction>();
        
        if(!itemIter.hasNext())
            return null;
        
        Item item = null;
        while(itemIter.hasNext()) {
            item = itemIter.next();
            Auction temp = objectModel.getAuction(item);
            if(temp != null)
            auctionList.add(temp);
        }
        
        return auctionList;
	}
}
