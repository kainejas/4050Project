package edu.uga.dawgtrades.persist;

//Gnu Emacs C++ mode:  -*- Java -*-
//
//Interface:	PersistenceModule.java
//
//K.J. Kochut
//
//
//

import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.Membership;

import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Bid;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.ExperienceReport;
import edu.uga.dawgtrades.model.Item;

public interface Persistence {

	void saveAttribute(Attribute attribute) throws DTException;

	Iterator<Attribute> restoreAttribute(Attribute attribute)
			throws DTException;

	void deleteAttribute(Attribute attribute) throws DTException;

	void saveAttributeType(AttributeType attributeType) throws DTException;

	Iterator<AttributeType> restoreAttributeType(AttributeType attributeType)
			throws DTException;

	void deleteAttributeType(AttributeType attributeType) throws DTException;

	void saveAuction(Auction auction) throws DTException;

	Iterator<Auction> restoreAuction(Auction auction) throws DTException;

	void deleteAuction(Auction auction) throws DTException;

	void saveBid(Bid bid) throws DTException;

	Iterator<Bid> restoreBid(Bid bid) throws DTException;

	void deleteBid(Bid bid) throws DTException;

	void saveCategory(Category category) throws DTException;

	Iterator<Category> restoreCategory(Category category) throws DTException;

	void deleteCategory(Category category) throws DTException;

	void saveExperienceReport(ExperienceReport experienceReport)
			throws DTException;

	Iterator<ExperienceReport> restoreExperienceReport(
			ExperienceReport experienceReport) throws DTException;

	void deleteExperienceReport(ExperienceReport experienceReport)
			throws DTException;

	void saveItem(Item item) throws DTException;

	Iterator<Item> restoreItem(Item item) throws DTException;

	void deleteItem(Item item) throws DTException;

	void saveMembership(Membership membership) throws DTException;

	Iterator<Membership> restoreMembership(Membership members)
			throws DTException;

	void deleteMembership(Membership membership) throws DTException;

	void saveRegisteredUser(RegisteredUser user) throws DTException;

	Iterator<RegisteredUser> restoreRegisteredUser(RegisteredUser user)
			throws DTException;

	void deleteRegisteredUser(RegisteredUser user) throws DTException;

	Iterator<Item> restoreOwns(RegisteredUser user) throws DTException;

	RegisteredUser restoreOwns(Item item) throws DTException;

	Category restoreHasChild(Category category) throws DTException;

	Iterator<Category> restoreHasParent(Category category) throws DTException;

	Category restoreDescribedBy(AttributeType attributeType) throws DTException;

	Iterator<AttributeType> restoreDescribedBy(Category category)
			throws DTException;

	Category restoreIsOfType(Item item) throws DTException;

	Iterator<Item> restoreIsOfType(Category category) throws DTException;

	AttributeType restoreHasType(Attribute attribute) throws DTException;

	Iterator<Attribute> restoreHasType(AttributeType attributeType)
			throws DTException;

	Item restoreHasAttribute(Attribute attribute) throws DTException;

	Iterator<Attribute> restoreHasAttribute(Item item) throws DTException;

	Item restoreIsSoldAt(Auction auction) throws DTException;

	Auction restoreIsSoldAt(Item item) throws DTException;
	
	Auction restoreAuction(Bid bid) throws DTException;
	

	RegisteredUser restoreBidder(Bid bid) throws DTException;

};