package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import edu.uga.dawgtrades.model.Membership;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;


public class MembershipManager {
	private ObjectModel objectModel = null;
	private Connection conn = null;
	
	public MembershipManager(Connection conn, ObjectModel objectModel){
		this.conn = conn;
		this.objectModel = objectModel;
	}
	
	public void save(Membership membership) throws DTException{
		String insertMembershipSql = "insert into membership (price, date) values (?, ?)";
		String updateMembershipSql = "update membership set price = ?, date = ? where id = ?";
		PreparedStatement stmt = null;
		int inscnt;
		long membershipId;
		
		if(membership.getId() == -1)
			throw new DTException("MembershipManager.save: Attempting to save a Membership without an ID");
		
		try{
			
			if(!membership.isPersistent())
				stmt = conn.prepareStatement(insertMembershipSql);
			else
				stmt = conn.prepareStatement(updateMembershipSql);
			
			if(membership.getPrice() >= 0)
				stmt.setString(1, Float.toString(membership.getPrice()));
			else
				throw new DTException("MembershipManager.save: can't save a Mambership: price undefined");
            
			if(membership.getDate() != null)
				stmt.setDate(2, (java.sql.Date) membership.getDate());
			else
				throw new DTException("MembershipManager.save: can't save an Membership: date undefined");
			
			if(membership.isPersistent())
				stmt.setLong(3, membership.getId());
			
			inscnt = stmt.executeUpdate();
			
			if(!membership.isPersistent()){
				if(inscnt >= 1){
					String sql = "select last_insert_id()";
					if(stmt.execute(sql)){
						ResultSet r = stmt.getResultSet();
						while(r.next()){
							membershipId = r.getLong(1);
							if(membershipId >= 0)
								membership.setId(membershipId);
						}
					}
				}
				else
					throw new DTException("MembershipManager.save: failed to save a Membership");
			}
			else{
				if(inscnt < 1)
					throw new DTException("MembershipManager.save: failed to save a Membership");
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			throw new DTException("MembershipManager.save: failed to save a Membership: " + e);
		}
	}
    
	public Iterator<Membership> restore(Membership membership) throws DTException{
		String selectMembershipSql = "select id, price, date from membership";
		Statement stmt = null;
		StringBuffer query = new StringBuffer(100);
		StringBuffer condition = new StringBuffer(100);
		
		condition.setLength(0);
		
		query.append(selectMembershipSql);
		
		if(membership != null){
			if(membership.getId() >= 0)
				query.append(" where id = " + membership.getId());
			else {
				if(membership.getPrice() >= 0)
                    condition.append(" price = " + membership.getPrice() + "'");
			
				if(membership.getDate() != null){
					if( condition.length() > 0 )
                        condition.append(" and ");
                    condition.append(" date = '" + membership.getDate() + "'");
                }
                
                if(condition.length() > 0){
					query.append(" where ");
					query.append(condition);
				}
			}
		}
		
		try{
			stmt = conn.createStatement();
			if(stmt.execute(query.toString())){
				ResultSet r = stmt.getResultSet();
				return new MembershipIterator(r, objectModel);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new DTException("MembershipManager.restore: Could not restore persistent Membership object; Root cause: " + e);
		}
		
		throw new DTException("MembershipManager.restore: Could not restore persistent Membership object");
	}
	
	
	public void delete(Membership membership) throws DTException{
		String deleteMembershipSql = "delete from membership where id = ?";
		PreparedStatement stmt = null;
		int inscnt;
		
		if(!membership.isPersistent())
			return;
		try{
			stmt = conn.prepareStatement(deleteMembershipSql);
			stmt.setLong(1, membership.getId());
			inscnt = stmt.executeUpdate();
			if(inscnt == 1){
				return;
			}
			else
				throw new DTException("MembershipManager.delete: failed to delete a Membership");
		}
		catch(SQLException e){
			e.printStackTrace();
			throw new DTException("MembershipManager.delete: failed to delete a Membership" + e);
		}
	}
	

}