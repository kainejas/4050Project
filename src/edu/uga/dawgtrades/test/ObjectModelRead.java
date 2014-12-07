package edu.uga.dawgtrades.test;

import java.sql.Connection;
import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;


import edu.uga.dawgtrades.model.*;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persist.Persistence;
import edu.uga.dawgtrades.persist.impl.DbUtils;
import edu.uga.dawgtrades.persist.impl.PersistenceImpl;

public class ObjectModelRead {


	// This is a simple class to test the reading of the entity classes
	// and associations in the Dawgtrades schema.
	//
  public static void main(String[] args)
	    {
	  Connection  conn = null;
      ObjectModel objectModel = null;
      Persistence persistence = null;

      // get a database connection
      try {
          conn = DbUtils.connect();
      } 
      catch (Exception seq) {
          System.err.println( "ObjectModelDelete: Unable to obtain a database connection" );
      }
      
   
      // obtain a reference to the ObjectModel module      
      objectModel = new ObjectModelImpl();
      // obtain a reference to Persistence module and connect it to the ObjectModel        
      persistence = new PersistenceImpl( conn, objectModel ); 
      // connect the ObjectModel module to the Persistence module
            objectModel.setPersistence(persistence);
            persistence.setObjectModel(objectModel);
            persistence.init();

      
               
      try {
          
	     // Retrieve all existing Club objects and list their founders and members
          System.out.println( "RegisteredUser objects:" );
          Iterator<RegisteredUser> userIter = objectModel.findRegisteredUser( null );
          while( userIter.hasNext() ) {
              RegisteredUser user = userIter.next();
              System.out.println( "REGISTEREDUSER ID: " + user.getId() + "  Name: " + user.getName() + "  First Name: " + user.getFirstName() );
              Iterator<Item> itemIter = objectModel.getItem( user );
              
              System.out.println("    Owner of: " );
              while( itemIter != null && itemIter.hasNext()) {
                  try{
                  Item item = itemIter.next();
                
            	  System.out.println("Item id: " + item.getId() + " name: " + item.getName() + " owner: " + item.getOwnerId() + " category: " + item.getCategoryId()  );
            	  System.out.println("Description: " + item.getDescription() + "\n");
                  }
                  catch(Exception e) {
                      e.printStackTrace();
                  }
              }
              System.out.println();
              
              System.out.println("   Bidder of: ");
              Iterator<Bid> bidIter = objectModel.getBids(user);
              while( bidIter != null && bidIter.hasNext()) {
                  try{
            	  Bid bid = bidIter.next();
            	  System.out.println("BID id: " + bid.getId() + " amount: " + bid.getAmount() + " user_id: " + bid.getRegisteredUser().getId() + " auction_id: " + bid.getAuction().getId()  );
                  }
                  catch(Exception e) {
                      e.printStackTrace();
                  }
              }
              System.out.println();
              
              System.out.println("   Reviewed in: ");
              Iterator<ExperienceReport> reviewedReportIter = objectModel.getReviewedReports( user );
              while(reviewedReportIter != null && reviewedReportIter.hasNext()) {
            	  ExperienceReport report = reviewedReportIter.next();
            	  System.out.println("EXPERIENCEREPORT id: " + report.getId() + " reviewer_id: " + report.getReviewer().getId() +" reviewed_id: " + report.getReviewed().getId() + " rating: " + report.getRating());
            	  System.out.println("report: " + report.getReport());
              }
              System.out.println();
              
              System.out.println("   Reviewer of: ");
              Iterator<ExperienceReport> reviewerReportIter = objectModel.getReviewerReports( user );
              while(reviewerReportIter != null && reviewerReportIter.hasNext()) {
            	  ExperienceReport report = reviewerReportIter.next();
            	  System.out.println("EXPERIENCEREPORT id: " + report.getId() + " reviewer_id: " + report.getReviewer().getId() +" reviewed_id: " + report.getReviewed().getId() + " rating: " + report.getRating());
            	  System.out.println("report: " + report.getReport());
              }
              System.out.println();
          }
          
	     // Retrieve all existing Person objects and list clubs they founded 
	     // and in which they are members
          System.out.println( "Attribute objects:" );
          Iterator<Attribute> attributeIter = objectModel.findAttribute( null );
          while( attributeIter.hasNext() ) {
              Attribute a = attributeIter.next();
              System.out.println( a );
              System.out.println("ATTRIBUTE with Value of: " + a.getValue());
              
              System.out.print( "    Describer of: " );
              Item item= objectModel.getItem( a );
              System.out.print("ITEM id: " + item.getId() + " name: " + item.getName() + " owner: " + item.getOwnerId() + " category: " + item.getCategoryId()  );
        	  System.out.println("Description: " + item.getDescription() + "\n");
             
              System.out.println();
              System.out.print("    With Type: ");
              AttributeType at = objectModel.getAttributeType(a);
              System.out.print("ATTRIBUTETYPE id: " + at.getId() + " name: " + at.getName() + " category: " + at.getCategoryId() );
             
              
              System.out.println();
                       
          }
          System.out.println();
          System.out.println("Attribute Types: ");
          Iterator<AttributeType> attributeTypeIter = objectModel.findAttributeType(null);
          while( attributeTypeIter.hasNext() ) {
        	  AttributeType attributeType = attributeTypeIter.next();
        	  System.out.println( attributeType );
        	  
        	  System.out.println("ATTRIBUTETYPE Name: " + attributeType.getName());
        	  System.out.println("For Category: ");
        	  Category cat = objectModel.getCategory(attributeType);
        	  System.out.println("Category id: " + cat.getId() + " name: " + cat.getName() + " parent_id: " + cat.getParentId());
        
          }
          
          System.out.println();
          System.out.println("Auctions: ");
          Iterator<Auction> auctionIter = objectModel.findAuction(null);
          while( auctionIter.hasNext() ) {
        	  Auction a = auctionIter.next();
        	  System.out.println( a );
        	  
        	  System.out.println("AUCTION id: " + a.getId() + " min_price " + a.getMinPrice() + " expiration: " + a.getExpiration());
         
        	  System.out.println("   Selling Item: " );
        	  Item item= objectModel.getItem( a );
              System.out.print("Item id: " + item.getId() + " name: " + item.getName() + " owner_id: " + item.getOwnerId() + " category_id: " + item.getCategoryId()  );
        	  System.out.println("Description: " + item.getDescription() + "\n");
             
          }
          
          System.out.println();
          System.out.println("  Bids: ");
          Iterator<Bid> bidIter =null;
          try{
        bidIter = objectModel.findBid(null);
          }
          catch(Exception e) {
              e.printStackTrace();
          }
          while( bidIter.hasNext() ) {
              Bid b = bidIter.next();
             
        	  System.out.println("  For amount: ");
        	  System.out.println(b.getAmount());
        	  
        	  System.out.println("  Made by: ");
        	  RegisteredUser user = objectModel.getBidder(b);
        	  System.out.println("RegisteredUser id: " + user.getId() + " username: " + user.getName() + " first_name: " + user.getFirstName() + " last_name: " + user.getLastName() + " password: " + user.getPassword() + " email: " + user.getEmail() + " phone: " + user.getPhone() + " canText: " + user.getCanText() + " isAdmin: " + user.getIsAdmin());
        	  System.out.println("   Made for: " );
        	  Auction a = objectModel.getAuction( b );
        	  System.out.println("Auction id: " + a.getId() + " min_price " + a.getMinPrice() + " item_id: " + a.getItemId() + " expiration: " + a.getExpiration());
             
          }
          
          System.out.println();
          System.out.println("  Categories: ");
          Iterator<Category> catIter = objectModel.findCategory(null);
          while( bidIter.hasNext() ) {
        	  Category c = catIter.next();
        	  System.out.println( c );
        	  
        	  System.out.println("  With Name: ");
        	  System.out.println(c.getName());
        	  
        	  System.out.println( "   Child of: " );
        	  Category parent = objectModel.getParent(c);
        	  System.out.println("Category id: " + parent.getId() + " name: " + parent.getName() + " parent_id: " + parent.getParentId());
        	  
          }
          
          System.out.println();
          System.out.println("  ExperienceReports: ");
          Iterator<ExperienceReport> expReportIter = objectModel.findExperienceReport(null);
          while( expReportIter.hasNext() ) {
        	 ExperienceReport er = expReportIter.next();
        	  System.out.println( er );
        	  
        	  System.out.println("  Rating of: ");
        	  System.out.println(er.getRating());
        	  
        	  System.out.println( "   With Report: " );
        	  System.out.println(er.getReport());
        	  
        	  System.out.println("  User reviewer: ");
        	  RegisteredUser reviewer = objectModel.getReviewer(er);
        	  System.out.println("RegisteredUser id: " + reviewer.getId() + " username: " + reviewer.getName() + " first_name: " + reviewer.getFirstName() + " last_name: " + reviewer.getLastName() + " password: " + reviewer.getPassword() + " email: " + reviewer.getEmail() + " phone: " + reviewer.getPhone() + " canText: " + reviewer.getCanText() + " isAdmin: " + reviewer.getIsAdmin());
         	 
        	  System.out.println("  User reviewed: ");
        	  RegisteredUser reviewed = objectModel.getReviewed(er);
        	  System.out.println("RegisteredUser id: " + reviewed.getId() + " username: " + reviewed.getName() + " first_name: " + reviewed.getFirstName() + " last_name: " + reviewed.getLastName() + " password: " + reviewed.getPassword() + " email: " + reviewed.getEmail() + " phone: " + reviewed.getPhone() + " canText: " + reviewed.getCanText() + " isAdmin: " + reviewed.getIsAdmin());
          	 
        	  
        	  System.out.println( "   Submitted On: ");
        	  System.out.println(er.getDate());
          }
          
          System.out.println();
          System.out.println("  Items: ");
          Iterator<Item> itemIter = objectModel.findItem(null);
          while( itemIter.hasNext() ) {
        	  Item item = itemIter.next();
        	  System.out.println( item );
        	  
        	  System.out.println("  With Name: ");
        	  System.out.println(item.getName());
        	  
        	    System.out.println("    With Attributes : ");
                Iterator<Attribute> attributesIter = objectModel.getAttributes(item);
                while( attributesIter != null && attributesIter.hasNext()) {
              	  Attribute a = attributesIter.next();
              	  System.out.println("Attribute id: " + a.getId() + " value: " + a.getValue() + " item_id: " + a.getItemId() + " attribute_type_id: " + a.getAttributeType());
                }
            
        	  
        	  System.out.println("  With Description: ");
        	  System.out.println(item.getDescription());
        	  
        	  System.out.println("  Owned By: ");
        	  RegisteredUser user = objectModel.getOwner(item);
        	  System.out.println("RegisteredUser id: " + user.getId() + " username: " + user.getName() + " first_name: " + user.getFirstName() + " last_name: " + user.getLastName() + " password: " + user.getPassword() + " email: " + user.getEmail() + " phone: " + user.getPhone() + " canText: " + user.getCanText() + " isAdmin: " + user.getIsAdmin());
          	 
        	  System.out.println( "   In Category: " );
        	  Category cat = objectModel.getCategory(item);
        	  System.out.println("Category id: " + cat.getId() + " name: " + cat.getName() + " parent_id: " + cat.getParentId());
        	  
          }
          
          System.out.println();
          System.out.println("  Membership: ");
          Membership member= objectModel.findMembership();
          
          System.out.println( "  Membership Price: ");
          System.out.println( member.getPrice());
          
          System.out.println( " Membership Date: " );
          System.out.println(member.getDate());
          
      }
      catch( DTException de)
      {
          System.err.println( "ClubsException: " + de );
      }
      catch( Exception e)
      {
          System.err.println( "Exception: " + e );
      }
      finally {
          // close the connection
          try {
              conn.close();
          }
          catch( Exception e ) {
              System.err.println( "Exception: " + e );
          }
      }
	    }  
	
}
