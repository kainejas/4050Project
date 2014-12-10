package edu.uga.dawgtrades.logic.impl;

import java.util.Date;
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

	@Override
	public List<Category> browseCategory(String category_name)
			throws DTException {
		CtrlBrowseCategory ctrlBrowse = new CtrlBrowseCategory(objectModel);
		return ctrlBrowse.browseCategory(category_name);
	}

	@Override
	public long reAuctionItem(long auctionId, String username,
			Category category, String item_name, String desc, long minPrice,
			Date aucDuration) throws DTException {
		CtrlReAuctionItem ctrl = new CtrlReAuctionItem(objectModel);
		return ctrl.reAuctionItem(auctionId, username, category, item_name, desc, minPrice, aucDuration);
	}

	@Override
	public void deleteCategory(String category_name) throws DTException {
		(new CtrlDeleteCategory(objectModel)).deleteCategory(category_name);
	}

	@Override
	public void deleteItem(String item_name) throws DTException {
		(new CtrlDeleteItem(objectModel)).deleteItem(item_name);
	}

	@Override
	public void deleteUser(String user_name) throws DTException {
		(new CtrlDeleteUser(objectModel)).deleteUser(user_name);
	}

	@Override
	public List<Item> findItems(String category_name) throws DTException {
		return (new CtrlFindItems(objectModel)).findItems(category_name);
	}

	@Override
	public long register(String user_name, String first_name, String last_name,
			String password, boolean isAdmin, String email, String phoneNumber,
			boolean canText) throws DTException {
		return (new CtrlRegister(objectModel)).register(user_name, first_name, last_name, password, isAdmin, email, phoneNumber, canText);
	}

	@Override
	public void setMembershipPrice(float value) throws DTException {
		(new CtrlSetMembershipPrice(objectModel)).setMembershipPrice(value);
	}

	@Override
	public void unregister(RegisteredUser user) throws DTException {
		(new CtrlUnregister(objectModel)).unregister(user);
	}

	@Override
	public long updateCategory(long categoryId, String category_name,
			long parentId) throws DTException {
		return (new CtrlUpdateCategory(objectModel)).updateCategory(categoryId, category_name, parentId);
	}

	@Override
	public long updateProfile(String user_name, String first_name,
			String last_name, String password, String email, String phone,
			boolean canText) throws DTException {
		return (new CtrlUpdateProfile(objectModel)).updateProfile(user_name, first_name, last_name, password, email, phone, canText);
	}

	@Override
	public long viewProfile(String user_name) throws DTException {
		return (new CtrlViewProfile(objectModel)).viewProfile(user_name);
	}
    
    @Override
    public List<Item> browseCategoryItems(String category_name) throws DTException{
        return (new CtrlBrowseCategory(objectModel)).browseCategoryItems(category_name);
    }
    
    @Override
    public List<Auction> viewMyAuctions(RegisteredUser user) throws DTException{
        return (new CtrlViewMyAuctions(objectModel)).viewMyAuctions(user);
    }
    @Override
    public long createAuctionItem(String category_name, String user, String item_name, String description, String min_price, String duration, String[] values ) throws DTException {
        return (new CtrlAuctionItem(objectModel)).auctionItem(category_name, user, item_name, description, min_price, duration, values);
    }
    
    @Override
    public List<Auction> trackAuction(String auction_id) throws DTException {
        return (new CtrlTrackAuction(objectModel)).trackAuction(auction_id);
    }
}
