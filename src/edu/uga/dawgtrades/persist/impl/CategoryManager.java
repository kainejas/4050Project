package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.sql.PreparedStatement;

import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.Category;

public class CategoryManager {
	
    private ObjectModel objectModel = null;
    private Connection  conn = null;
	
	public CategoryManager( Connection conn, ObjectModel objectModel ) {
        this.conn = conn;
        this.objectModel = objectModel;
	}//end constructor
	
	public void save(Category category) throws DTException {
		String 				insertCategorySql = "insert into category ( name, parent_id ) values ( ?, ? )";
		String 				updateCategorySql = "update category  set name = ?, set parent_id = ? where id = ?";
		PreparedStatement 	stmt;
		int					inscnt;
		long				categoryId;
		
		try {
			
			if (!category.isPersistent())
				stmt = conn.prepareStatement(insertCategorySql);
			else
				stmt = conn.prepareStatement(updateCategorySql);
			
			if (category.getName() != null)
				stmt.setString(1, category.getName());
			else
				throw new DTException( "Category.save: can't save a Category: invalid category name");
			
			if (category.getParentId() > 0)
				stmt.setString( 2, Long.toString(category.getParentId()));
			else
				stmt.setNull( 2, java.sql.Types.NULL);
			
			if (category.isPersistent())
				stmt.setLong(3, category.getId());
			
			inscnt = stmt.executeUpdate();
			
			if (!category.isPersistent()) {
				if (inscnt >= 1) {
					String sql = "select last_insert_id()";
					if (stmt.execute(sql)) {
						ResultSet r = stmt.getResultSet();
						while(r.next()) {
							categoryId = r.getLong(1);
							if (categoryId > 0)
								category.setId(categoryId);
						}
					}
				}
				else
					throw new DTException("CategoryManager.save: failed to save a Category");
			}
			else {
				if (inscnt < 1)
					throw new DTException("CategoryManager.save: failed to save a Category");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DTException("CategoryManager.save: failed to save a Category");
		}
	}//end save()
	
	public Iterator<Category> restore(Category category) throws DTException {
		String selectCategorySql = "select id, name, parent_id from category";
		Statement stmt = null;
		StringBuffer query = new StringBuffer(100);
		StringBuffer condition = new StringBuffer(100);
		
		condition.setLength(0);
		
		query.append(selectCategorySql);
		
		if (category != null) {
			if (category.getId() >= 0)
				query.append(" where id = " + category.getId());
			else {
				if (category.getName() != null) {
					if (condition.length() != 0)
						condition.append(" and");
					condition.append(" name = '" + category.getName() + "'");
				}
				if (category.getParentId() >= 0) {
					if (condition.length() != 0)
						condition.append(" and");
					condition.append(" parent_id = '" + category.getParentId() + "'");
				}
				if (condition.length() > 0)
					query.append(" where " + condition);
			}
		}
		
		try {
			stmt = conn.createStatement();
			if (stmt.execute(query.toString())) {
				ResultSet r = stmt.getResultSet();
				return new CategoryIterator(r, objectModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DTException("CategoryManager.restore: Could not restore persistent Category object. Root cause: " + e);
		}
		
		throw new DTException("CategoryManager.restore: Could not restore persistent Category object.");
	}//end restore()
	
	public void delete(Category category) throws DTException{
		String deleteCategorySql = "delete from category where id = ?";
		PreparedStatement stmt = null;
		int inscnt;
		
		if (!category.isPersistent())
			return;
		
		try {
			stmt = conn.prepareStatement(deleteCategorySql);
			stmt.setLong(1,category.getId());
			inscnt = stmt.executeUpdate();
			if (inscnt == 1)
				return;
			else
				throw new DTException("CategoryManager.delete: failed to delete an Category");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DTException("CategoryManager.delete: failed to delete an Category. Root Cause: " + e);
		}
	}//end delete
	
	public Category restoreHasChild(Category category) throws DTException {
		String selectCategorySql = "select p.id, p.name, p.parent_id from category p, category c where c.parent_id = p.id";
		Statement stmt = null;
		StringBuffer query = new StringBuffer(100);
		StringBuffer condition = new StringBuffer(100);
		
		condition.setLength(0);
		
		query.append(selectCategorySql);
		
		if (category != null) {
			if (category.getId() >= 0)
				query.append(" and c.id = " + category.getId());
			else {
				if (category.getName() != null) {
					if (condition.length() != 0)
						condition.append(" and");
					condition.append(" c.name = '" + category.getName() + "'");
				}
				if (category.getParentId() >= 0) {
					if (condition.length() != 0)
						condition.append(" and");
					condition.append(" c.parent_id = '" + category.getParentId() + "'");
				}
				
				if (condition.length() > 0) {
					query.append(condition);
				}
			}
		}
		
		try {
			stmt = conn.createStatement();
			if (stmt.execute(query.toString())) {
				ResultSet r = stmt.getResultSet();
				return new CategoryIterator( r, objectModel).next();
			}
		} catch (Exception e) {
			throw new DTException("CategoryManager.restoreHasChild: Could not restore persistent Category objects. Root cause: " + e);
		}
		throw new DTException("CategoryManager.restoreHasChild: Could not restore persistent Category object.");
	}//end restoreParent()
	
	public Iterator<AttributeType> restoreAttributeTypes(Category category) throws DTException {
		String selectCategorySql = "select at.id, at.name, at.category_id from attributetype at, category c where at.category_id = c.id";
		Statement stmt = null;
		StringBuffer query = new StringBuffer(100);
		StringBuffer condition = new StringBuffer(100);
		
		condition.setLength(0);
		
		query.append(selectCategorySql);
		
		if (category != null) {
			if (category.getId() >= 0)
				query.append(" and c.id = " + category.getId());
			else {
				if (category.getName() != null) {
					if (condition.length() != 0)
						condition.append(" and");
					condition.append(" c.name = '" + category.getName() + "'");
				}
				if (category.getParentId() >= 0) {
					if (condition.length() != 0)
						condition.append(" and");
					condition.append(" c.parent_id = '" + category.getParentId() + "'");
				}
				
				if (condition.length() > 0) {
					query.append(condition);
				}
			}
		}
		
		try {
			stmt = conn.createStatement();
			if (stmt.execute(query.toString())) {
				ResultSet r = stmt.getResultSet();
				return new AttributeTypeIterator( r, objectModel);
			}
		} catch (Exception e) {
			throw new DTException("CategoryManager.restoreAttributeTypes: Could not restore persistent AttributeType objects. Root cause: " + e);
		}
		throw new DTException("CategoryManager.restoreAttributeTypes: Could not restore persistent AttributeType object.");
	}//end restoreAttributeTypes()
	
	public Iterator<Item> restoreItems(Category category) throws DTException {
		String selectCategorySql = "select i.id, i.name, i.description, i.category_id, i.user_id from item i, category c where item.category_id = c.id";
		Statement stmt = null;
		StringBuffer query = new StringBuffer(100);
		StringBuffer condition = new StringBuffer(100);
		
		condition.setLength(0);
		
		query.append(selectCategorySql);
		
		if (category != null) {
			if (category.getId() >= 0)
				query.append(" and c.id = " + category.getId());
			else {
				if (category.getName() != null) {
					if (condition.length() != 0)
						condition.append(" and");
					condition.append(" c.name = '" + category.getName() + "'");
				}
				if (category.getParentId() >= 0) {
					if (condition.length() != 0)
						condition.append(" and");
					condition.append(" c.parent_id = '" + category.getParentId() + "'");
				}
				
				if (condition.length() > 0) {
					query.append(condition);
				}
			}
		}
		
		try {
			stmt = conn.createStatement();
			if (stmt.execute(query.toString())) {
				ResultSet r = stmt.getResultSet();
				return new ItemIterator( r, objectModel);
			}
		} catch (Exception e) {
			throw new DTException("CategoryManager.restoreItems: Could not restore persistent Item objects. Root cause: " + e);
		}
		throw new DTException("CategoryManager.restoreItems: Could not restore persistent Item objects.");
	}//end restoreItem()
	
	public Iterator<Category> restoreHasParent(Category category) throws DTException {
		String selectCategorySql = "select c.id, c.name, c.parent_id from category c, category p where p.id = c.parent_id";
		Statement stmt = null;
		StringBuffer query = new StringBuffer(100);
		StringBuffer condition = new StringBuffer(100);
		
		condition.setLength(0);
		
		query.append(selectCategorySql);
		
		if (category != null) {
			if (category.getId() >= 0)
				query.append(" and p.id = " + category.getId());
			else {
				if (category.getName() != null) {
					if (condition.length() != 0)
						condition.append(" and");
					condition.append(" p.name = '" + category.getName() + "'");
				}
				if (category.getParentId() >= 0) {
					if (condition.length() != 0)
						condition.append(" and");
					condition.append(" p.parent_id = '" + category.getParentId() + "'");
				}
				
				if (condition.length() > 0) {
					query.append(condition);
				}
			}
		}
		
		try {
			stmt = conn.createStatement();
			if (stmt.execute(query.toString())) {
				ResultSet r = stmt.getResultSet();
				return new CategoryIterator( r, objectModel);
			}
		} catch (Exception e) {
			throw new DTException("CategoryManager.restoreHasParent: Could not restore persistent Category objects. Root cause: " + e);
		}
		throw new DTException("CategoryManager.restoreHasParent: Could not restore persistent Category objects.");
	}//end restoreChildren()

}
