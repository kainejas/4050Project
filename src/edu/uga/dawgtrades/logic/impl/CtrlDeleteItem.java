package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;

public class CtrlDeleteItem {
	private ObjectModel objectModel = null;
	
	public CtrlDeleteItem(ObjectModel objectModel){
		this.objectModel = objectModel;
	}
	
	public void deleteItem(String item_name) throws DTException{
		Item item = null;
		Item modelItem = null;
		Iterator<Item> itemIter = null;
		
		modelItem = objectModel.createItem();
		modelItem.setName(item_name);
		itemIter = objectModel.findItem(modelItem);
		if(itemIter.hasNext())
			item = itemIter.next();
		else
			throw new DTException("Item not found: " + item_name);
		
		objectModel.deleteItem(item);
	}
}
