package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.persist.impl.CategoryManager;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;

public class CtrlFindItems {
	private ObjectModel objectModel = null;
	private CategoryManager categoryManager = null;
	
	public CtrlFindItems(ObjectModel objectModel, CategoryManager categoryManger){
		this.objectModel = objectModel;
		this.categoryManager = categoryManager;
	}
	
	public List<Item> findItems(String category_name) throws DTException{
		Category category = null;
		Category modelCategory = null;
		Iterator<Category> categoryIter = null;
		List<Item> items = null;
		
		items = new LinkedList<Item>();
		
		modelCategory = objectModel.createCategory();
		modelCategory.setName(category_name);
		categoryIter = objectModel.findCategory(modelCategory);
		while(categoryIter.hasNext()){
			category = categoryIter.next();
		}
		if(category == null)
			throw new DTException("A category with this name does not exist: " + category_name);
		
		Iterator<Item> itemIter = categoryManager.restoreItems(category);
		while(itemIter != null && itemIter.hasNext()){
			Item i = itemIter.next();
			items.add(i);
		}
		return items;
	}
}
