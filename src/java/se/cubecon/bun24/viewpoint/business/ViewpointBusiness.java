package se.cubecon.bun24.viewpoint.business;

import com.idega.block.process.business.CaseBusiness;
import com.idega.business.IBOService;
import com.idega.user.data.*;
import java.rmi.RemoteException;
import javax.ejb.*;
import se.cubecon.bun24.viewpoint.data.*;

/**
 * ViewpointBusiness is a session ejb interface for creating, managing and
 * broking general viewpoints from a system user in IdegaWeb. The user is
 * selecting from a category tree - first a top category, then a sub category -
 * in order to help the system to chose what handler group should be responsible
 * for answering the entered viewpoint.
 * <p>
 * Last modified: $Date: 2002/11/28 08:31:41 $ by $Author: staffan $
 *
 * @author <a href="http://www.staffannoteberg.com">Staffan N�teberg</a>
 * @version $Revision: 1.4 $
 * @see com.idega.block.process.business
 * @see com.idega.business
 * @see com.idega.user.data
 * @see javax.ejb
 * @see se.cubecon.bun24.viewpoint.data
 */
public interface ViewpointBusiness extends IBOService, CaseBusiness {
	String CONFIRMENTERVIEWPOINT_KEY = "viewpoint.confirmEnterViewpoint";
	String CONFIRMENTERVIEWPOINT_DEFAULT
        = "Tack f�r din synpunkt. Den �r nu registrerad som ett �rende p� "
        + "Nacka24. En handl�ggare kommer att hantera och besvara �rendet.";

    /**
     * Creates a new viewpoint and stores it in the database.
     *
     * @param user the user that enters the viewpoint
     * @param subject a free text subject for the viewpoint
     * @param message the actual viewpoint text
     * @param category the name of the user chosen viewpoint category
     * @param handlerGroupId group responsible for answering the viewpoint
     */
    void createViewpoint (User user, String subject, String message,
                          String category, int handlerGroupId)
        throws CreateException, RemoteException;

	Viewpoint findViewpoint (int viewpointID)
        throws RemoteException, FinderException;
    Viewpoint [] findUnhandledViewpointsInGroups (Group [] groups)
        throws RemoteException, FinderException;
    void registerHandler (int viewpointId, User handler)
        throws RemoteException, FinderException;
    void answerAndDeregisterViewpoint (int viewpointId, String answer)
        throws RemoteException, FinderException, CreateException;

    TopCategory [] findAllTopCategories ()
        throws RemoteException, FinderException;
    SubCategory [] findSubCategories (int topCategoryId)
        throws RemoteException, FinderException;
    SubCategory findSubCategory (int subCategoryId)
        throws RemoteException, FinderException;
}
