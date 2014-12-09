package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.ObjectModel;


public class CtrlBrowseCategory {
	private ObjectModel objectModel = null;
    
	public CtrlBrowseCategory(ObjectModel objectModel){
		this.objectModel = objectModel;
	}
	
	public List<Category> browseCategory(String category_name) throws DTException{
        
        Category modelCategory = null;
        Iterator<Category> catIter = null;
        List<Category> list = null;
        
        modelCategory = objectModel.createCategory();
        modelCategory.setName(category_name);
        catIter = objectModel.getChild(modelCategory);
        if (catIter == null)
            return null;
        
        list = new LinkedList<Category>();
        
        while (catIter.hasNext())
            list.add(catIter.next());
        
        return list;
	}//end browseCategory
    
    public List<Item> browseCategoryItems(String category_name) throws DTException {
        Category modelCategory = null;
        Iterator<Item> itemIter = null;
        List<Item> list = null;
        
        modelCategory = objectModel.createCategory();
        modelCategory.setName(category_name);
        itemIter = objectModel.getItem(modelCategory);
        if (itemIter == null)
            return null;
        
        list = new LinkedList<Item>();
        
        while (itemIter.hasNext()) {
            Item tempItem = itemIter.next();
            Auction tempAuction = objectModel.getAuction(tempItem);
            if (!tempAuction.getIsClosed())
                list.add(tempItem);
        }
        return list;
    }//end browseCategoryItems
}
