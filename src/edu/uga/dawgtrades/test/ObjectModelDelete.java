package edu.uga.dawgtrades.test;

import java.sql.Connection;
import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Bid;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.ExperienceReport;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.Membership;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persist.Persistence;
import edu.uga.dawgtrades.persist.impl.DbUtils;
import edu.uga.dawgtrades.persist.impl.PersistenceImpl;


// A class to illustrate how to delete entity objects and associations
//
public class ObjectModelDelete
{
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
         
         // obtain a reference to Persistence module and connect it to the ObjectModel        
         persistence = new PersistenceImpl( conn, objectModel ); 
         
         // obtain a reference to the ObjectModel module      
         objectModel = new ObjectModelImpl(persistence);
         
         Iterator<RegisteredUser> userIter = null;
                  
         try {
             
	     // Delete the test User object along with their bid and ExpirenceReport
             // First: find the test User
             RegisteredUser testUser = null;
             RegisteredUser modelUser = objectModel.createRegisteredUser();
             modelUser.setName( "TestUser" );
             userIter = objectModel.findRegisteredUser( modelUser );
             while( userIter.hasNext() ) {
                 testUser = userIter.next();
                 System.out.println( testUser );
             }
             
             // Second: find test Bid
             Bid testBid = null;
             Bid modelBid = objectModel.createBid();
             modelBid.setRegisteredUser( testUser );
             Iterator<Bid> bidIter = objectModel.findBid( modelBid );
             while( bidIter.hasNext() ) {
                 testBid = bidIter.next();
                 System.out.println( testBid );
             }
             
             // Third: find test ExperienceReport
             ExperienceReport testExperienceReport = null;
             ExperienceReport modelExperienceReport = objectModel.createExperienceReport();
             modelExperienceReport.setReviewer( testUser );
             Iterator<ExperienceReport> experienceReportIter = objectModel.findExperienceReport( modelExperienceReport );
             while( experienceReportIter.hasNext() ) {
                 testExperienceReport = experienceReportIter.next();
                 System.out.println( testExperienceReport );
             }
             
             // Fourth: delete the test User
             if( testUser != null ) {
                 objectModel.deleteRegisteredUser( testUser );
                 System.out.println( "Deleted the test user" );
             }
             else
                 System.out.println( "Failed to retrieve the test User object" );
             
             // Fifth: delete test Bid
             if( testBid != null ) {
                 objectModel.deleteBid( testBid );
                 System.out.println( "Deleted the test Bid object" );
             }
             else
                 System.out.println( "Failed to find test Bid" );
             
             // Sixth: delete test ExperienceReport
             if( testExperienceReport != null ) {
                 objectModel.deleteExperienceReport( testExperienceReport );
                 System.out.println( "Deleted the test ExperienceReport object" );
             }
             else
                 System.out.println( "Failed to find test ExperienceReport" );
             
           
	     // Delete test Item object along with it's Attribute, the Attribute's 
         // corresponding Attribute Type, and the Item's Auction object
             // First: find test Item
             Item testItem = null;
             Item modelItem = objectModel.createItem();
             modelItem.setName( "Heather" );
             //modelItem.setLastName( "Brooks" );
             Iterator<Item> itemIter = objectModel.findItem( modelItem );
             while( itemIter.hasNext() ) {
                 testItem = itemIter.next();
                 System.out.println( testItem );
             }
          
             // Second: find test Item's Auction
             Auction testAuction = null;
             Auction modelAuction = objectModel.createAuction();
             modelAuction.setItemId( testItem.getId() );
             Iterator<Auction> auctionIter = objectModel.findAuction( modelAuction );
             while( auctionIter.hasNext() ) {
                 testAuction = auctionIter.next();
                 System.out.println( testAuction );
             }
             
             // Third: find test Item's Attribute
             Attribute testAttribute = null;
             //modelItem.setLastName( "Brooks" );
             Iterator<Attribute> attributeIter = objectModel.getAttribute( testItem );
             while( attributeIter.hasNext() ) {
                 testAttribute = attributeIter.next();
                 System.out.println( testAttribute );
             }
             
             // Fourth: find test Attribute's AttributeType
             AttributeType testAttributeType = objectModel.getAttributeType( testAttribute );
             
             // Fifth: delete test Item
             if( testItem != null ) {
                 objectModel.deleteItem( testItem );
                 System.out.println( "Deleted the test Item object" );
             }
             else
                 System.out.println( "Failed to find test Item" );

             // Sixth: delete test Attribute
             if( testAttribute != null ) {
                 objectModel.deleteAttribute( testAttribute );
                 System.out.println( "Deleted the test Attribute object" );
             }
             else
                 System.out.println( "Failed to find test Attribute" );
             
             // Seventh: delete test AttributeType
             if( testAttributeType != null ) {
                 objectModel.deleteAttributeType( testAttributeType );
                 System.out.println( "Deleted the test AttributeType object" );
             }
             else
                 System.out.println( "Failed to find test AttributeType" );
             
             // Eighth: delete test Auction
             if( testAuction != null ) {
                 objectModel.deleteAuction( testAuction );
                 System.out.println( "Deleted the test Auction object" );
             }
             else
                 System.out.println( "Failed to find test Auction" );

             
          // Delete test Category object
             // First: find test Category
             Category testCategory = null;
             Category modelCategory = objectModel.createCategory();
             modelCategory.setName( "Test" );
             Iterator<Category> categoryIter = objectModel.findCategory( modelCategory );
             while( categoryIter.hasNext() ) {
                 testCategory = categoryIter.next();
                 System.out.println( testCategory );
             }
             
             // Second: delete test Category
             if( testCategory != null ) {
                 objectModel.deleteCategory( testCategory );
                 System.out.println( "Deleted the test Category object" );
             }
             else
                 System.out.println( "Failed to find test Category" );
             

  /*           
          // Delete test Membership object
             // First: find test Membership
             Membership testMembership = null;
             Membership modelMembership = objectModel.createMembership();
             modelMembership.setDate(  );
             Iterator<Membership> membershipIter = objectModel.findMembership( modelMembership );
             while( membershipIter.hasNext() ) {
                 testMembership = membershipIter.next();
                 System.out.println( testMembership );
             }
             
             // Second: delete test Membership
             if( testMembership != null ) {
                 objectModel.deleteMembership( testMembership );
                 System.out.println( "Deleted the test Membership object" );
             }
             else
                 System.out.println( "Failed to find test Membership" );
        
    */    

         }
         catch( DTException dte ) {
             System.err.println( "DTException: " + dte );
         }
         catch( Exception e ) {
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
