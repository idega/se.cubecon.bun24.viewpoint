package se.cubecon.bun24.viewpoint.data;

import java.rmi.RemoteException;
import javax.ejb.FinderException;
import java.util.Collection;
import com.idega.data.IDOEntity;

/**
 * @author <a href="http://www.staffannoteberg.com">Staffan N�teberg</a>
 */
public interface TopCategory extends IDOEntity {
    String getName ();
    void setName (String name);
    Collection ejbFindAll () throws FinderException, RemoteException;
}
