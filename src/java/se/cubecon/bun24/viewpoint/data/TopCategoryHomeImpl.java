package se.cubecon.bun24.viewpoint.data;

import com.idega.data.*;
import java.util.*;
import javax.ejb.*;
import java.rmi.RemoteException;

/**
 * @author <a href="http://www.staffannoteberg.com">Staffan N�teberg</a>
 */
public class TopCategoryHomeImpl extends IDOFactory implements TopCategoryHome {
    public TopCategory create () throws CreateException{
        return (TopCategory) createIDO ();
    }

    public TopCategory [] findAll () throws FinderException, RemoteException {
        final IDOEntity entity = idoCheckOutPooledEntity();
        final Collection ids = ((TopCategoryBMPBean)entity).ejbFindAll ();
        idoCheckInPooledEntity (entity);
        return (TopCategory []) getEntityCollectionForPrimaryKeys(ids).toArray
                (new TopCategory [0]);
    }

    protected Class getEntityInterfaceClass() {
        return TopCategory.class;
    }
}
