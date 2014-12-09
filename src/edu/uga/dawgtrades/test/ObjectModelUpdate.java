package edu.uga.dawgtrades.test;

import java.sql.Connection;
import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;


import edu.uga.dawgtrades.model.*;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persist.Persistence;
import edu.uga.dawgtrades.persist.impl.DbUtils;
import edu.uga.dawgtrades.persist.impl.PersistenceImpl;

public class ObjectModelUpdate {


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
          
          Iterator<RegisteredUser> userIter = objectModel.findRegisteredUser( null );
          if(userIter.hasNext()){
              RegisteredUser testUser = userIter.next();
              String oldName = testUser.getName();
              testUser.setName("UpdateTest1");
              objectModel.storeRegisteredUser(testUser);
              testUser = objectModel.findRegisteredUser(testUser).next();
              System.out.println("Before name: " + oldName);
              System.out.println("After name: " + testUser.getName());
          }
          
          Attribute attr = objectModel.findAttribute(null).next();
          if (attr != null) {
              String oldValue = attr.getValue();
              attr.setValue("New Value");
              objectModel.storeAttribute(attr);
              attr = objectModel.findAttribute(attr).next();
              System.out.println("Before value: " + oldValue);
              System.out.println("After value: " + attr.getValue());
          }
          
          AttributeType attrTyp = objectModel.findAttributeType(null).next();
          if (attrTyp != null) {
              String oldName = attrTyp.getName();
              attrTyp.setName("New Name AttributeType");
              objectModel.storeAttributeType(attrTyp);
              attrTyp = objectModel.findAttributeType(attrTyp).next();
              System.out.println("Before name: " + oldName);
              System.out.println("After name: " + attrTyp.getName());
          }
          
          Item testItem = objectModel.findItem(null).next();
          if (testItem != null) {
              String oldName = testItem.getName();
              testItem.setName("New Name Item");
              objectModel.storeItem(testItem);
              testItem = objectModel.findItem(testItem).next();
              System.out.println("Before name: " + oldName);
              System.out.println("After name: " + testItem.getName());
          }
          
          Category testCat = objectModel.findCategory(null).next();
          if (testCat != null) {
              String oldName = testCat.getName();
              testCat.setName("New Name Category");
              objectModel.storeCategory(testCat);
              testCat = objectModel.findCategory(testCat).next();
              System.out.println("Before name: " + oldName);
              System.out.println("After name: " + testCat.getName());
          }
          
          Auction testAuc = objectModel.findAuction(null).next();
          if (testAuc != null) {
              float oldPrice = testAuc.getMinPrice();
              testAuc.setMinPrice(oldPrice + 7.2f);
              objectModel.storeAuction(testAuc);
              testAuc = objectModel.findAuction(testAuc).next();
              System.out.println("Before price: " + oldPrice);
              System.out.println("After price: " + testAuc.getMinPrice());
          }
          
          Bid testBid = objectModel.findBid(null).next();
          if (testBid != null) {
              float oldBid = testBid.getAmount();
              testBid.setAmount(oldBid + 7.2f);
              objectModel.storeBid(testBid);
              testBid = objectModel.findBid(testBid).next();
              System.out.println("Before bid: " + oldBid);
              System.out.println("After bid: " + testBid.getAmount());
          }
          
          ExperienceReport testExp = objectModel.findExperienceReport(null).next();
          if (testExp != null) {
              float oldRating = testExp.getRating();
              if (oldRating == 5)
                  testExp.setRating(1);
              else
                  testExp.setRating((int)oldRating + 1);
              objectModel.storeExperienceReport(testExp);
              testExp = objectModel.findExperienceReport(testExp).next();
              System.out.println("Before rating: " + oldRating);
              System.out.println("After rating: " + testExp.getRating());
          }
          
          Membership testMem = objectModel.findMembership();
          if (testMem != null) {
              float oldPrice = testMem.getPrice();
              testMem.setPrice(oldPrice + 1.5f);
              objectModel.storeMembership(testMem);
              testMem = objectModel.findMembership();
              System.out.println("Before price: " + oldPrice);
              System.out.println("After price: " + testMem.getPrice());
          }
          
        
      }//end try
    catch( DTException de)
      {
          System.err.println( "DTException: " + de );
      }// end catch
      catch( Exception e)
      {
          System.err.println( "Exception: " + e );
      }//end catch
      finally {
          // close the connection
          try {
              conn.close();
          }
          catch( Exception e ) {
              System.err.println( "Exception: " + e );
          }
      }//end finally
            
            
	    }// end main
	
}
