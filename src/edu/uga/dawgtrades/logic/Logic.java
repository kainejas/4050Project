package edu.uga.dawgtrades.logic;

import java.util.Date;
import java.util.List;

import edu.uga.dawgtrades.model.*;

public interface Logic {
	public long defineCategory(String categoryName, long parentId) throws DTException;
	public List<Category> browseCategory(String category_name) throws DTException;
	public long reAuctionItem(long auctionId, String username, Category category, String item_name, String desc, long minPrice, Date aucDuration) throws DTException;
	public void deleteCategory(String category_name) throws DTException;
	public void deleteItem(String item_name) throws DTException;
	public void deleteUser(String user_name) throws DTException;
	public List<Item> findItems(String category_name) throws DTException;
	public long register(String user_name, String first_name, String last_name, String password, boolean isAdmin, String email, String phoneNumber, boolean canText) throws DTException;
	public void setMembershipPrice(float value) throws DTException;
	public void unregister(String user_name) throws DTException;
	public long updateCategory(long categoryId, String category_name, long parentId) throws DTException;
	public long updateProfile(String user_name, String first_name, String last_name, String password, String email, String phone, boolean canText) throws DTException;
	public long viewProfile(String user_name) throws DTException;
    
    public List<Item> browseCategoryItems(String category_name);
	
}