package se.cubecon.bun24.viewpoint.data;

import java.util.Collection;

import javax.ejb.FinderException;

import com.idega.data.GenericEntity;
import com.idega.data.IDOLookup;

/**
 * Last modified: $Date: 2003/11/10 18:59:32 $ by $Author: laddi $
 *
 * @author <a href="http://www.staffannoteberg.com">Staffan N�teberg</a>
 * @version $Revision: 1.6 $
 */
public class TopCategoryBMPBean extends GenericEntity implements TopCategory {

    private static final String ENTITY_NAME = "vp_topcategory";
    private static final String COLUMN_ID = ENTITY_NAME + "_id";
	private static final String COLUMN_NAME = "name";

	public String getEntityName() {
		return ENTITY_NAME;
	}
    
    public void insertStartData () throws Exception {
        super.insertStartData ();
        
        System.out.println ("��� Invoked " + ENTITY_NAME + ".insertStartData ()");

        TopCategoryHome home
                = (TopCategoryHome) IDOLookup.getHome(TopCategory.class);
        final String [] topCategoryNames = { "Barnomsorg", "Skola",
                                             "Gymnasieskola", "Komvux",
                                             "Politiker", "Myndigheten" };
        for (int i = 0; i < topCategoryNames.length; i++) {
            final TopCategory topCategory = home.create();
            topCategory.setName (topCategoryNames [i]);
            topCategory.store();
        }
    }

    public void initializeAttributes () {
        addAttribute(COLUMN_ID, "Id", Integer.class);
		addAttribute (COLUMN_NAME, "Name", String.class);
        setAsPrimaryKey (COLUMN_ID, true);
    }

    public String getName () {
		return getStringColumnValue (COLUMN_NAME);
    }

    public void setName (final String name) {
		setColumn (COLUMN_NAME, name);
    }

    public Collection ejbFindAll () throws FinderException {
        return idoFindPKsBySQL ("select * from " + ENTITY_NAME);
    }
}
