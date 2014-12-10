package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import edu.uga.dawgtrades.model.DTException;

import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.Auction;
public class CtrlTrackAuction {
	private ObjectModel objectModel = null;
	
	public CtrlTrackAuction(ObjectModel objectModel){
		this.objectModel = objectModel;
	}
	
	public List<Auction> trackAuction(String auction_id) throws DTException{
        
        Auction modelAuction = null;
        Iterator<Auction> auctionIter = null;
        List<Auction> auctionList = new LinkedList<Auction>();
        
        long id = -1;
        
        try{
            id = Long.parseLong(auction_id);
        }
        catch(Exception e) {
             throw new DTException("Invalid auction id: " + id);
        }
        modelAuction = objectModel.createAuction();
        modelAuction.setId(id);
        auctionIter = objectModel.findAuction(modelUser);
        if (auctionIter != null || !auctionIter.hasNext())
            return null;
        
        while(auctionIter.hasNext() ) {
            Auction auc = auctionIter.next();
            auctionList.add(auc);
        }
        return auctionList;
        
	}

}
