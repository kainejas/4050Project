package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.ObjectModel;

public class CtrlPrintReport {
	private ObjectModel objectModel = null;
	
	public CtrlPrintReport(ObjectModel objectModel){
		this.objectModel = objectModel;
	}
	
	public String printReport(String time_period) throws DTException{
        //number of new registeredUsers
        //number of completed Auctions - done
        //total value of completed Auctions - done
        //number of ongoing auctions - done
        
        int userCount = 0;
        Iterator<RegisteredUser> userIter = objectModel.findRegisteredUsers(null);
        RegisteredUser user = null;
        while(userIter.hasNext()) {
            user = userIter.next();
            userCount++;
        }
        
        Iterator<Auction> auctionIter = objectModel.findAuction(null);
        
        if(!auctionIter.hasNext())
            //ERROR or something
            
        Auction auction = null;
        int activeAuctions = 0;
        int closedAuctions = 0;
        float price = 0.0f;
        while(auctionIter.hasNext()) {
            auction = auctionIter.next();
            if(!auction.getIsClosed()) {
                activeAuctions++;
            }
            else {
                closedAuctions++;
                price += auction.getSellingPrice();
            }
                
        }

		
		modelCategory = objectModel.createCategory();
		modelCategory.setName(category_name);
		categoryIter = objectModel.findCategory(modelCategory);
		while(categoryIter.hasNext()){
			category = categoryIter.next();
		}
		if(category == null)
			throw new DTException("A category with this name does not exist: " + category_name);
		
		Iterator<Item> itemIter = objectModel.findItem(category);
		while(itemIter != null && itemIter.hasNext()){
			Item i = itemIter.next();
			items.add(i);
		}
		return userCount + " " + closedAuctions + " " + price + " " + activeAuctions;
	}
}
