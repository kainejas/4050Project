package edu.uga.dawgtrades.logic.impl;

import java.util.List;

import edu.uga.dawgtrades.logic.Logic;
import edu.uga.dawgtrades.model.*;

public class LogicImpl implements Logic{
	private ObjectModel objectModel = null;
	
	public LogicImpl(ObjectModel objectModel){
		this.objectModel = objectModel;
	}
	
	@Override
	public long defineCategory(String category_name, long parent_id) throws DTException{
		CtrlDefineCategory ctrlDefineCategory = new CtrlDefineCategory(objectModel);
		return ctrlDefineCategory.defineCategory(category_name, parent_id);
	}
}
