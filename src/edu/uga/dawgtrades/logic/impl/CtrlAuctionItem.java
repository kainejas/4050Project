package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Date;
import java.util.Calendar;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.persist.impl.CategoryManager;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.ObjectModel;

public class CtrlAuctionItem {
	private ObjectModel objectModel = null;
	
	public CtrlAuctionItem(ObjectModel objectModel){
		this.objectModel = objectModel;

	}
	
	public long auctionItem(String category_name, String user, String item_name, String description, String min_price, String duration, String[] values) throws DTException{
       //create auction with confirmation message
        
        
        Category modelCategory = objectModel.createCategory();
        modelCategory.setName(category_name);
        
        Iterator<Category> catIter = objectModel.findCategory(modelCategory);
        
        if(!catIter.hasNext())
            throw new DTException("Category name does not exist");
        
        Category cat = catIter.next();
        
        
        float minPrice = 0.0f;
        try {
            minPrice = Float.parseFloat(min_price);
        }
        catch(Exception e) {
            throw new DTException("Negative or non-numeric minimum price");
        }
        if(minPrice <= 0.0f)
            throw new DTException("Negative or non-numeric minimum price");
        
        
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        
        if(duration.equals("1 month"))
                c.add(Calendar.DATE, 30);
                
        else if(duration.equals( "1 week"))
                c.add(Calendar.DATE, 7);
                
        else if(duration.equals( "1 day"))
                c.add(Calendar.DATE, 1);
               
        else    throw new DTException("Illegal duration");
        
        dt = c.getTime();
        
        RegisteredUser modelOwner = objectModel.createRegisteredUser();
        modelOwner.setName(user);
        Iterator<RegisteredUser> ownerIter = objectModel.findRegisteredUser(modelOwner);
        
        if(!ownerIter.hasNext())
            throw new DTException("No user found when creating auction");
        
        
        Item item = objectModel.createItem(cat,ownerIter.next(), item_name, description);
        objectModel.storeItem(item);
        Attribute attr = null;
        for(int i = 0; i < values.length; i++) {
            String[] vals = values[i].split(",");
            AttributeType at = objectModel.createAttributeType();
            at.setName(vals[0]);
            at = objectModel.findAttributeType(at).next();
            if(at != null) {
                attr = objectModel.createAttribute(at, item, vals[1]);
                objectModel.storeAttribute(attr);
            }
        }
        
        Auction auction = objectModel.createAuction(item, minPrice, dt );
        objectModel.storeAuction(auction);
        
        return auction.getId();
        
      
        
      
	}
}
