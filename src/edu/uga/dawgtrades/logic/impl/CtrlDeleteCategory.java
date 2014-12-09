package edu.uga.dawgtrades.logic.impl;

import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.ObjectModel;

public class CtrlDeleteCategory {
	private ObjectModel objectModel = null;
	
	public CtrlDeleteCategory(ObjectModel objectModel){
		this.objectModel = objectModel;
	}
	
	public void deleteCategory(String category_name) throws DTException{
		Category category = null;
		Category modelCategory = null;
		Iterator<Category> categoryIter = null;
		
		modelCategory = objectModel.createCategory();
		modelCategory.setName(category_name);
		categoryIter = objectModel.findCategory(modelCategory);
		if(categoryIter.hasNext())
			category = categoryIter.next();
		else
			throw new DTException("Category not found: " + category_name);
		
		objectModel.deleteCategory(category);
	}
}
