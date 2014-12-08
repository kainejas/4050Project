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
         
         // obtain a reference to the ObjectModel module      
         objectModel = new ObjectModelImpl();
         // obtain a reference to Persistence module and connect it to the ObjectModel        
         persistence = new PersistenceImpl( conn, objectModel ); 
         // connect the ObjectModel module to the Persistence module
        objectModel.setPersistence(persistence);
        persistence.setObjectModel(objectModel);
        persistence.init();

         
                          
         try {
             /*
              get user
              delete experience report
              delete bid
              delete auction
              delete attribute
              delete item
              delete user
              delete attributetype
              delete category
              delete membership
            */
             // Delete the test User object along with their bid and ExpirenceReport
             // First: find the test User
             RegisteredUser testUser = null;
             RegisteredUser modelUser = objectModel.createRegisteredUser();
             modelUser.setName( "obama" );
             
             Iterator<RegisteredUser> userIter = null;
             userIter = objectModel.findRegisteredUser( modelUser );
             while( userIter.hasNext() ) {
                 testUser = userIter.next();
                 System.out.println( testUser );
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
             
             ExperienceReport testExperienceReportTwo = null;
             ExperienceReport modelExperienceReportTwo = objectModel.createExperienceReport();
             modelExperienceReportTwo.setReviewed( testUser );
             Iterator<ExperienceReport> experienceReportIterTwo = objectModel.findExperienceReport( modelExperienceReportTwo );
             while( experienceReportIterTwo.hasNext() ) {
                 testExperienceReportTwo = experienceReportIterTwo.next();
                 System.out.println( testExperienceReportTwo );
             }
             
             
             if(testExperienceReport != null) {
                 objectModel.deleteExperienceReport(testExperienceReport);
                 System.out.println("Deleted the test Experience Report");
             } else
                 System.out.println( "Failed to retrieve the test Experience Report");
             
             
             
             
             if(testExperienceReportTwo != null) {
                 objectModel.deleteExperienceReport(testExperienceReportTwo);
                 System.out.println("Deleted the test Experience Report2");
             } else
                 System.out.println( "Failed to retrieve the test Experience Report2");
             
             
             //Third: find bid from user
             Bid testBid = null;
             Bid modelBid = objectModel.createBid();
             modelBid.setRegisteredUser(testUser);
             testBid = objectModel.findBid(modelBid).next();
             
             if(testBid != null) {
                 objectModel.deleteBid( testBid);
                 System.out.println("Deleted the test Bid object");
             }
             else
                 System.out.println("Failed to find test Bid");
             
             // Second: find test Item's Auction
             Auction testAuction = null;
             Auction modelAuction = objectModel.createAuction();
             modelAuction.setId( testBid.getAuction().getId() );
             Iterator<Auction> auctionIter = objectModel.findAuction( modelAuction );
             while( auctionIter.hasNext() ) {
                 testAuction = auctionIter.next();
                 System.out.println( testAuction );
             }
             
             
             // Eighth: delete test Auction
             if( testAuction != null ) {
                 objectModel.deleteAuction( testAuction );
                 System.out.println( "Deleted the test Auction object" );
             }
             else
                 System.out.println( "Failed to find test Auction" );
             
             // Delete test Item object along with it's Attribute, the Attribute's
             // corresponding Attribute Type, and the Item's Auction object
             // First: find test Item
             Item testItem = null;
             Item modelItem = objectModel.createItem();
             modelItem.setRegisteredUser( testUser);
             //modelItem.setLastName( "Brooks" );
             Iterator<Item> itemIter = objectModel.findItem( modelItem );
             while( itemIter.hasNext() ) {
                 testItem = itemIter.next();
                 System.out.println( testItem );
             }
             // Third: find test Item's Attributes
             Attribute testAttribute = null;
             //modelItem.setLastName( "Brooks" );
             Iterator<Attribute> attributeIter = objectModel.getAttributes( testItem );
             while( attributeIter.hasNext() ) {
                 testAttribute = attributeIter.next();
                 System.out.println( testAttribute );
             }
             // Fourth: find test Attribute's AttributeType
             AttributeType testAttributeType = objectModel.getAttributeType( testAttribute );

             // Sixth: delete test Attribute
             if( testAttribute != null ) {
                 objectModel.deleteAttribute( testAttribute);
                 System.out.println( "Deleted the test Attribute object" );
             }
             else
                 System.out.println( "Failed to find test Attribute" );
             
            //Next: delete the Item
            objectModel.deleteItem( testItem );
             System.out.println( "Deleted the test Item object" );

             
             // Fourth: delete the test User
             if( testUser != null ) {
                 objectModel.deleteRegisteredUser( testUser );
                 System.out.println( "Deleted the test user" );
             }
             else
                 System.out.println( "Failed to retrieve the test User object" );
             
             
             // Seventh: delete test AttributeType
             if( testAttributeType != null ) {
                 objectModel.deleteAttributeType( testAttribute );
                 System.out.println( "Deleted the test AttributeType object" );
             }
             else
                 System.out.println( "Failed to find test AttributeType" );

             
             
             // Delete test Category object
             // First: find test Category
             Category testCategory = null;
             Category modelCategory = objectModel.createCategory();
             modelCategory.setName( "category3" );
             
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
             
          // Delete test Membership object
             // First: find test Membership
             Membership testMembership = objectModel.findMembership();
             
                 System.out.println( testMembership );
             
             
             // Second: delete test Membership
             if( testMembership != null ) {
                 objectModel.deleteMembership( testMembership );
                 System.out.println( "Deleted the test Membership object" );
             }
             else
                 System.out.println( "Failed to find test Membership" );
        
    

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
