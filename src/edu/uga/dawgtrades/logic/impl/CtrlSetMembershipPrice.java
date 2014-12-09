package edu.uga.dawgtrades.logic.impl;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Membership;
import edu.uga.dawgtrades.model.ObjectModel;

public class CtrlSetMembershipPrice {
	private ObjectModel objectModel = null;
	
	public CtrlSetMembershipPrice(ObjectModel objectModel){
		this.objectModel = objectModel;
	}

	public void setMembershipPrice(float value) throws DTException{
		Membership membership = objectModel.findMembership();
		
		if(membership == null)
			throw new DTException("Membership not found, cannot set price");
		else
			membership.setPrice(value);
	}
}
