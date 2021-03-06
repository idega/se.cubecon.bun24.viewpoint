package se.cubecon.bun24.viewpoint.data;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.FinderException;

import com.idega.block.process.data.AbstractCaseBMPBean;
import com.idega.block.process.data.Case;
import com.idega.block.process.data.CaseBMPBean;
import com.idega.data.IDOAddRelationshipException;
import com.idega.data.IDORemoveRelationshipException;
import com.idega.user.data.Group;
import com.idega.user.data.User;

/**
 * Last modified: $Date: 2005/07/05 16:41:50 $ by $Author: thomas $
 *
 * @author <a href="http://www.staffannoteberg.com">Staffan N�teberg</a>
 * @version $Revision: 1.19 $
 */
public class ViewpointBMPBean extends AbstractCaseBMPBean implements Viewpoint, Case {

	public static final String CASE_CODE_KEY = "SYMESYN";
	public static final String STATUSKEY_ANSWERED = "ASWD";

    private static final String ENTITY_NAME = "vp_viewpoint";
    private static final String CASE_CODE_DESCRIPTION = "User viewpoint";
	private static final String [] CASE_STATUS_DESCRIPTIONS = { "Answered" };
	private static final String [] CASE_STATUS_KEYS =
    { STATUSKEY_ANSWERED };

	private static final String COLUMN_CATEGORY = "CATEGORY";
	private static final String COLUMN_USER_ID = "USER_ID";
	private static final String COLUMN_SUBJECT = "SUBJECT";
	private static final String COLUMN_MESSAGE = "BODY";
    private static final String COLUMN_ANSWER = "ANSWER";
    private static final String COLUMN_ROADRESPONSIBLE_ID
        = "ROADRESPONSIBLE_ID";
    private static final String COLUMN_USERNAME = "INITIATERNAME";
    private static final String COLUMN_USEREMAIL = "INITIATEREMAIL";
    private static final String COLUMN_ANSWERDATE = "ANSWERDATE";

	@Override
	public String getEntityName() {
		return ENTITY_NAME;
	}

	@Override
	public String getCaseCodeKey() {
		return CASE_CODE_KEY;
	}

    @Override
	public String getCaseCodeDescription () {
        return CASE_CODE_DESCRIPTION;
    }

    @Override
	public String [] getCaseStatusKeys () {
        return CASE_STATUS_KEYS;
    }

    @Override
	public String [] getCaseStatusDescriptions () {
        return CASE_STATUS_DESCRIPTIONS;
    }

    @Override
	public void insertStartData () {
        super.insertStartData ();
    }

    @Override
	public void initializeAttributes () {
		addGeneralCaseRelation();
		addAttribute (COLUMN_CATEGORY, "Category", String.class);
       	addAttribute (COLUMN_USER_ID, "User", true, true, Integer.class,
                      "many-to-one", User.class);
		addAttribute (COLUMN_SUBJECT, "Subject", String.class);
		addAttribute (COLUMN_MESSAGE, "Message", String.class, 10000);
		addAttribute (COLUMN_ANSWER, "Answer", String.class, 10000);
       	addAttribute (COLUMN_ROADRESPONSIBLE_ID, "RoadResponsible", true, true,
                      Integer.class, "many-to-one", RoadResponsible.class);
		addAttribute (COLUMN_USEREMAIL, "UserEmail", String.class);
		addAttribute (COLUMN_USERNAME, "UserName", String.class);
		addAttribute (COLUMN_ANSWERDATE, "AnswerDate", java.sql.Date.class);
    }

    public Group getHandlerGroup () {
        return getHandler ();
    }

    public String getCategory () {
		return getStringColumnValue (COLUMN_CATEGORY);
    }

    @Override
	public String getSubject () {
		return getStringColumnValue (COLUMN_SUBJECT);
    }

    public String getMessage () {
        return getStringColumnValue (COLUMN_MESSAGE);
    }

    public String getAnswer () {
        return getStringColumnValue (COLUMN_ANSWER);
    }

    public Integer getUserId () {
        return getIntegerColumnValue (COLUMN_USER_ID);
    }

    public boolean isAnswered () {
        return getStatus ().equalsIgnoreCase (STATUSKEY_ANSWERED);
    }

    public Integer getRoadResponsibleId () {
        return getIntegerColumnValue (COLUMN_ROADRESPONSIBLE_ID);
    }

    public String getUserEmail () {
        return getStringColumnValue (COLUMN_USEREMAIL);
    }

    public String getUserName () {
        return getStringColumnValue (COLUMN_USERNAME);
    }

    public java.util.Date getAnswerDate () {
        return (java.util.Date) getColumnValue (COLUMN_ANSWERDATE);
    }

    public java.util.Date getQuestionDate () {
        return getCreated ();
    }

    public void setHandlerGroupId (final int handlerGroupId) {
        setHandler (handlerGroupId);
    }

    public void setUser (final User user) {
        setColumn (COLUMN_USER_ID, ((Integer) user.getPrimaryKey()).intValue());
    }

    public void setCategory (final String category) {
		setColumn (COLUMN_CATEGORY, category);
    }

    @Override
	public void setSubject (final String subject) {
		setColumn (COLUMN_SUBJECT, subject);
    }

    public void setMessage (final String message) {
		setColumn (COLUMN_MESSAGE, message);
    }

    public void setAnswer (final String answer) {
        setColumn (COLUMN_ANSWER, answer);
        setColumn (COLUMN_ANSWERDATE,
                   new java.sql.Date (new java.util.Date ().getTime ()));
    }

    public void setHandler (final User user) {
        setOwner (user);
    }

    public void unsetHandler () {
        final CaseBMPBean entity = (CaseBMPBean) getGeneralCase ();
        entity.removeFromColumn ("USER_ID");
    }

    public void setRoadResponsibleId (final int id) {
        setColumn (COLUMN_ROADRESPONSIBLE_ID, new Integer (id));
    }

    public void setUserEmail (final String email) {
        setColumn (COLUMN_USEREMAIL, email);
    }

    public void setUserName (final String name) {
        setColumn (COLUMN_USERNAME, name);
    }

    public Collection ejbFindUnhandledViewpointsInGroups
        (final Group [] groups) throws FinderException {
        final StringBuffer sql = new StringBuffer ();
        if (groups.length == 0) return new ArrayList (); // return empty list
        sql.append ("select * from " + ENTITY_NAME + ", proc_case");
        sql.append (" where " + ENTITY_NAME + "." + ENTITY_NAME + "_id");
        sql.append (" = proc_case.proc_case_id");
        sql.append (" and proc_case.user_id is null");
        for (int i = 0; i < groups.length; i++) {
            sql.append (i == 0 ? " and (" : " or ");
            final int groupId
                    = ((Integer) groups [i].getPrimaryKey ()).intValue ();
            sql.append ("proc_case.handler_group_id = '" + groupId + "'");
        }
        sql.append (")");
        sql.append(" order by " + COLUMN_ANSWERDATE);
        final Collection viewpoints = idoFindPKsBySQL (sql.toString ());
        return viewpoints;
    }

	public void addSubscriber(User arg0) throws IDOAddRelationshipException {
		// TODO Auto-generated method stub

	}

	public Collection<User> getSubscribers() {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeSubscriber(User arg0)
			throws IDORemoveRelationshipException {
		// TODO Auto-generated method stub

	}
}
