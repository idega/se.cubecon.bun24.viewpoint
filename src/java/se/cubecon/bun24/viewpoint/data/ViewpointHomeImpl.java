package se.cubecon.bun24.viewpoint.data;

import com.idega.data.*;
import com.idega.user.data.Group;
import java.util.Collection;
import javax.ejb.*;

/**
 * Last modified: $Date: 2003/11/10 18:57:57 $ by $Author: laddi $
 *
 * @author <a href="http://www.staffannoteberg.com">Staffan N�teberg</a>
 * @version $Revision: 1.3 $
 */
public class ViewpointHomeImpl extends IDOFactory implements ViewpointHome {
    public Viewpoint create () throws CreateException{
        return (Viewpoint) createIDO ();
    }

    public Viewpoint findByPrimaryKey (final Integer primaryKey)
        throws FinderException {
        return (Viewpoint) findByPrimaryKeyIDO (primaryKey);
    }

    protected Class getEntityInterfaceClass() {
        return Viewpoint.class;
    }

    public Viewpoint [] findUnhandledViewpointsInGroups (final Group [] groups)
        throws FinderException {
        final IDOEntity entity = idoCheckOutPooledEntity();
        final Collection ids
                = ((ViewpointBMPBean)entity).ejbFindUnhandledViewpointsInGroups
                (groups);
        idoCheckInPooledEntity (entity);
        return (Viewpoint []) getEntityCollectionForPrimaryKeys(ids).toArray
                (new Viewpoint [0]);
    }
}
