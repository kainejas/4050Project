// Gnu Emacs C++ mode:  -*- Java -*-
//
// Class:	ItemIteratorImpl
//
// S.J. DeBrock
//
//
//

package edu.uga.dawgtrades.persist.impl;



import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Date;
import java.util.NoSuchElementException;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.Membership;

public class MembershipIterator
    implements Iterator<Membership>
{
    private ResultSet   rs = null;
    private ObjectModel objectModel = null;
    private boolean     more = false;

    // these two will be used to create a new object
    //
    public MembershipIterator( ResultSet rs, ObjectModel objectModel )
            throws DTException
    { 
        this.rs = rs;
        this.objectModel = objectModel;
        try {
            more = rs.next();
        }
        catch( Exception e ) {	// just in case...
            throw new DTException( "MembershipIterator: Cannot create Membership iterator; root cause: " + e );
        }
    }

    public boolean hasNext() 
    { 
        return more; 
    }

    public Membership next()
    {
        long id;
        float price;
        Date date;

        if( more ) {

            try {
                id = rs.getLong(1);
                price = rs.getFloat( 2 );
                date = rs.getDate( 3 );

                more = rs.next();
            }
            catch( Exception e ) {	// just in case...
                throw new NoSuchElementException( "MembershipIterator: No next Membership object; root cause: " + e );
            }
            
            Membership membership = objectModel.createMembership();
            membership.setPrice(price);
            membership.setId(id);
            membership.setDate(date);
        
            return membership;
        }
        else {
            throw new NoSuchElementException( "MembershipIterator: No next Membership object" );
        }
    }

    public void remove()
    {
        throw new UnsupportedOperationException();
    }

};
