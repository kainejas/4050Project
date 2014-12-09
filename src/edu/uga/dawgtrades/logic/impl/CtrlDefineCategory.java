package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.ObjectModel;

public class CtrlDefineCategory {
	private ObjectModel objectModel = null;
	
	public CtrlDefineCategory(ObjectModel objectModel){
		this.objectModel = objectModel;
	}
	
	public long defineCategory(String category_name, long parentId) throws DTException{
		Category category = null;
		Category modelCategory = null;
		Iterator<Category> categoryIter = null;
		Category parent = null;
		Category modelParent = null;
		Iterator<Category> parentIter = null;
		
		modelCategory = objectModel.createCategory();
		modelCategory.setName(category_name);
		categoryIter = objectModel.findCategory(modelCategory);
		if(categoryIter.hasNext())
			throw new DTException("A category with this name already exists: " + category_name);
		 
		modelParent = objectModel.createCategory();
		modelParent.setId(parentId);
		
        parent = objectModel.findCategory(modelParent).next();
		

		category = objectModel.createCategory(parent, category_name);
		objectModel.storeCategory(category);
		
		return category.getId();
	}
}