package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.ObjectModel;

public class CtrlUpdateCategory {
	private ObjectModel objectModel = null;
	
	public CtrlUpdateCategory(ObjectModel objectModel){
		this.objectModel = objectModel;
	}
	
	public long updateCategory(long categoryId, String category_name, long parentId) throws DTException{
		Category category = null;
		Category modelCategory = null;
		Iterator<Category> categoryIter = null;
		Category parent = null;
		Category modelParent = null;
		Iterator<Category> parentIter = null;

		modelCategory = objectModel.createCategory();
		modelCategory.setId(categoryId);
		categoryIter = objectModel.findCategory(modelCategory);
		if(categoryIter.hasNext())
			category = categoryIter.next();
		else
			throw new DTException("Category not found: " + category_name);
		 
		modelParent = objectModel.createCategory();
		modelParent.setId(parentId);
		parentIter = objectModel.findCategory(modelParent);
		while(parentIter.hasNext()){
			parent = parentIter.next();
		}

		category.setName(category_name);
		category.setParentId(parent.getId());
		objectModel.storeCategory(category);
		
		return category.getId();
	}

}
