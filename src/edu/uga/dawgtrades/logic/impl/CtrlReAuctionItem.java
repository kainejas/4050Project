package edu.uga.dawgtrades.logic.impl;

import java.util.Date;
import java.util.Iterator;

import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.ObjectModel;

public class CtrlReAuctionItem {
	private ObjectModel objectModel = null;
	
	public CtrlReAuctionItem(ObjectModel objectModel){
		this.objectModel = objectModel;
	}
	
	public long reAuctionItem(long auctionId, String username, Category category, String name, String desc, long minPrice, Date aucDuration) throws DTException{
		
        Auction auction = null, modelAuction = null;
        Iterator<Auction> auctionIter = null;
        
        modelAuction = objectModel.createAuction();
        modelAuction.setId(auctionId);
        auctionIter = objectModel.findAuction(modelAuction);
        if (!auctionIter.hasNext())
        	throw new DTException("Auction not found: auction id = " + auctionId);
        
        RegisteredUser user = null, modelUser = null;
        Iterator<RegisteredUser> userIter = null;
        modelUser = objectModel.createRegisteredUser();
        modelUser.setName(username);
        userIter = objectModel.findRegisteredUser(modelUser);
        if (!userIter.hasNext())
        	throw new DTException("User not found: " + username);
        user = userIter.next();
        
        Item item = objectModel.createItem(category, user, name, desc);
        
        auction = auctionIter.next();
        auction.setExpiration(aucDuration);
        auction.setItemId(item.getId());
        auction.setMinPrice(minPrice);
        
        return auction.getId();
	}
}
