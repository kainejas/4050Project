package edu.uga.dawgtrades.logic.impl;

import java.util.Date;
import java.util.Iterator;

import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.ObjectModel;

public class CtrlCloseAuction {
	private ObjectModel objectModel = null;
	
	public CtrlCloseAuction(ObjectModel objectModel){
		this.objectModel = objectModel;
	}
	
	public void closeAuction(long auctionId) throws DTException{
		
        Auction auction = null, modelAuction = null;
        Iterator<Auction> auctionIter = null;
        
        modelAuction = objectModel.createAuction();
        modelAuction.setId(auctionId);
        auctionIter = objectModel.findAuction(modelAuction);
        if (!auctionIter.hasNext())
        	throw new DTException("Cannot close auction that does not exist!");
        auction = auctionIter.next();

        
        
	}
}
