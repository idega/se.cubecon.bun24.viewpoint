package se.cubecon.bun24.viewpoint.presentation;

import com.idega.business.IBOLookup;
import com.idega.presentation.*;
import com.idega.presentation.text.*;
import com.idega.presentation.ui.*;
import com.idega.user.business.UserBusiness;
import com.idega.user.data.*;
import java.rmi.RemoteException;
import javax.ejb.*;
import se.cubecon.bun24.viewpoint.business.ViewpointBusiness;
import se.cubecon.bun24.viewpoint.data.*;
import se.idega.idegaweb.commune.presentation.CommuneBlock;

/**
 * ViewpointForm is an IdegaWeb block that inputs and handles viewpoints from
 * all kind of system users. It is based on session ejb classes in
 * {@link se.cubecon.bun24.viewpoint.business} and entity ejb classes in
 * {@link se.cubecon.bun24.viewpoint.data}.
 * <p>
 * The user enters viewpoints by first selecting in a category tree and then
 * writes the message. This makes it possible for the system to act as a
 * broker when deciding who should be able to manage the viewpoint and send an
 * answer.
 * <p>
 * Last modified: $Date: 2002/11/13 12:45:30 $ by $Author: staffan $
 *
 * @author <a href="http://www.staffannoteberg.com">Staffan N�teberg</a>
 * @version $Revision: 1.11 $
 * @see com.idega.business
 * @see com.idega.presentation
 * @see com.idega.presentation.text
 * @see com.idega.presentation.ui
 * @see com.idega.user.business
 * @see com.idega.user.data
 * @see javax.ejb
 * @see se.cubecon.bun24.viewpoint.business
 * @see se.cubecon.bun24.viewpoint.data
 */
public class ViewpointForm extends CommuneBlock {
    public final static String PARAM_ACTION = "vp_action";
	public final static String PARAM_CATEGORY = "vp_category";
	public final static String PARAM_SUBJECT = "vp_subject";
	public final static String PARAM_MESSAGE = "vp_message";
    public final static String PARAM_ANSWER = "vp_answer";
    public final static String PARAM_VIEWPOINT_ID = "vp_viewpoint_id";

    public final static int SHOWTOPCATEGORIESFORM_ACTION = 1;
    public final static int SHOWSUBCATEGORIESFORM_ACTION = 2;
    public final static int REGISTERVIEWPOINT_ACTION = 3;
    public final static int SHOWACCEPTFORM_ACTION = 4;
    public final static int SHOWANSWERFORM_ACTION = 5;
    public final static int NOTLOGGEDON_ACTION = 6;
    public final static int ACCEPTTOHANDLEVIEWPOINT_ACTION = 7;
    public final static int ANSWERVIEWPOINT_ACTION = 8;

    private final static String ANSWER_KEY = "viewpoint.answer";
    private final static String ANSWER_DEFAULT = "Svar till medborgare";
    private final static String APPLIES_KEY = "viewpoint.applies";
    private final static String APPLIES_DEFAULT = "Avser";
    private final static String CONFIRMANSWERSENT_KEY
        = "viewpoint.confirmAnswerSent";
    private final static String CONFIRMANSWERSENT_DEFAULT
        = "Ditt svar har nu skickats till medborgaren.";
    private final static String CONFIRMENTERVIEWPOINT_KEY
        = "viewpoint.confirmEnterViewpoint";
    private final static String CONFIRMENTERVIEWPOINT_DEFAULT
        = "Tack f�r din synpunkt. Den �r nu registrerad som ett �rende p� "
        + "BUN24. En handl�ggare kommer att hantera och besvara �rendet.";
    private final static String CONFIRMSETHANDLER_KEY
        = "viewpoint.confirmSetHandler";
    private final static String CONFIRMSETHANDLER_DEFAULT
        = "Du �r nu registrerad som handl�ggare f�r det h�r �rendet.";
    private final static String CONTINUE_KEY = "viewpoint.continue";
    private final static String CONTINUE_DEFAULT = "Forts�tt";
    private final static String DESCRIPTION1_KEY = "viewpoint.description1";
    private final static String DESCRIPTION1_DEFAULT
        = "N�r du anv�nder 'Synpunkter' i BUN24 s� ska du ange vilken typ av "
        + "synpunkter du vill l�mna. Det m�jligg�r f�r oss att l�nka dem till "
        + "r�tt tj�nsteman f�r snabb handl�ggning och respons. De olika "
        + "kategorierna ger ocks� m�jlighet till en systematisk uppf�ljning av "
        + "hur medborgarna uppfattar verksamhet och service i kommunen.";
    private final static String DESCRIPTION2_KEY = "viewpoint.description2";
    private final static String DESCRIPTION2_DEFAULT
        = "Problem ska i f�rsta hand l�sas d�r de uppst�r. Om du har "
        + "synpunkter p� hur en enskild skola eller f�rskola fungerar s� ska "
        + "du d�rf�r i f�rsta hand v�nda dig till personal, rektor eller "
        + "f�rskolechef.";
    private final static String ENTERSUBCATEGORY_KEY
        = "viewpoint.enterSubCategory";
    private final static String ENTERSUBCATEGORY_DEFAULT
        = "Vilket underomr�de vill du ge synpunkter om?";
    private final static String ENTERTOPCATEGORY_KEY
        = "viewpoint.enterTopCategory";
    private final static String ENTERTOPCATEGORY_DEFAULT
        = "Vilket omr�de vill du ge synpunkter om?";
    private final static String FROMCITIZEN_KEY = "viewpoint.fromCitizen";
    private final static String FROMCITIZEN_DEFAULT = "Fr�n medborgare";
    private final static String IACCEPTTOHANDLETHISVIEWPOINT_KEY
        = "viewpoint.iAcceptToHandleThisViewpoint";
    private final static String IACCEPTTOHANDLETHISVIEWPOINT_DEFAULT
        = "Jag accepterar att handl�gga den h�r synpunkten";
    private final static String MESSAGE_KEY = "viewpoint.message";
    private final static String MESSAGE_DEFAULT = "Meddelande";
    private final static String NOTLOGGEDON_KEY = "viewpoint.notLoggedOn";
    private final static String NOTLOGGEDON_DEFAULT
        = "Du m�ste vara inloggad f�r att anv�nda den h�r funktionen.";
    private final static String SENDANSWERTOCITIZEN_KEY
        = "viewpoint.sendAnswerToCitizen";
    private final static String SENDANSWERTOCITIZEN_DEFAULT
        = "Skicka svar till medborgare";
    private final static String SUBJECT_KEY = "viewpoint.subject";
    private final static String SUBJECT_DEFAULT = "Rubrik";
    private final static String SUBMITVIEWPOINT_KEY
        = "viewpoint.submitViewpoint";
    private final static String SUBMITVIEWPOINT_DEFAULT = "Skicka synpunkt";
    private final static String VIEWPOINTS_KEY = "viewpoint.viewpoints";
    private final static String VIEWPOINTS_DEFAULT = "Synpunkter";
    private final static String GOBACKTOMYPAGE_KEY = "viewpoint.goBackToMyPage";
    private final static String GOBACKTOMYPAGE_DEFAULT
        = "Tillbaka till min sida";

    private final static String UNKNOWN_PAGE = "Unknown Page";

    private int userHomePageId = -1;    

    /**
     * main is the event handler of ViewpointForm. It can handle the following
     * events set in {@link #PARAM_ACTION}:<br>
     * - {@link #SHOWTOPCATEGORIESFORM_ACTION}<br>
     * - {@link #SHOWSUBCATEGORIESFORM_ACTION}<br>
     * - {@link #REGISTERVIEWPOINT_ACTION}<br>
     * - {@link #SHOWACCEPTFORM_ACTION}<br>
     * - {@link #SHOWANSWERFORM_ACTION}<br>
     * - {@link #NOTLOGGEDON_ACTION}<br>
     * - {@link #ACCEPTTOHANDLEVIEWPOINT_ACTION}<br>
     * - {@link #ANSWERVIEWPOINT_ACTION}<br>
     *
     * @param iwc session data like user info etc.
     */
	public void main (final IWContext iwc) {
		setResourceBundle(getResourceBundle(iwc));

        try {
            switch (parseInput (iwc)) {
                case SHOWTOPCATEGORIESFORM_ACTION:
                    showTopCategoriesForm (iwc);
                    break;
                    
                case SHOWSUBCATEGORIESFORM_ACTION:
                    showSubCategoriesForm (iwc);
                    break;
                    
                case REGISTERVIEWPOINT_ACTION:
                    registerViewPoint (iwc);
                    break;

                case SHOWACCEPTFORM_ACTION:
                    showAcceptForm (iwc);
                    break;

                case SHOWANSWERFORM_ACTION:
                    showAnswerForm (iwc);
                    break;

                case NOTLOGGEDON_ACTION:
                    add (getLocalizedHeader (NOTLOGGEDON_KEY,
                                             NOTLOGGEDON_DEFAULT));
                    break;

                case ACCEPTTOHANDLEVIEWPOINT_ACTION:
                    acceptToHandleViewpoint (iwc);
                    break;

                case ANSWERVIEWPOINT_ACTION:
                    answerViewpoint (iwc);
                    break;

                default:
                    add (UNKNOWN_PAGE);
                    break;
            }
        } catch (final Exception exception) {
            add (new ExceptionWrapper(exception, this));
        }
	}

    private int parseInput (final IWContext iwc) {
        int action = NOTLOGGEDON_ACTION;

        if (iwc.isLoggedOn()) {
            if (iwc.isParameterSet (PARAM_ACTION)) {
                action = Integer.parseInt (iwc.getParameter (PARAM_ACTION));
            } else {
                action = SHOWTOPCATEGORIESFORM_ACTION;
            }                
        }
        
        return action;
    }
    
    private void showAcceptForm (final IWContext iwc)
        throws RemoteException, FinderException {
        final ViewpointBusiness viewpointBusiness = getViewpointBusiness (iwc);
        final int viewpointId
                = Integer.parseInt (iwc.getParameter (PARAM_VIEWPOINT_ID));
        final Viewpoint viewpoint
                = viewpointBusiness.findViewpoint (viewpointId);
		final UserBusiness userBusiness = (UserBusiness)
                IBOLookup.getServiceInstance (iwc, UserBusiness.class);
        final User user = userBusiness.getUser (viewpoint.getUserId ());
		final Form form = new Form();
		form.add (new HiddenInput (PARAM_ACTION,
                                   ACCEPTTOHANDLEVIEWPOINT_ACTION + ""));
		form.add (new HiddenInput (PARAM_VIEWPOINT_ID, viewpointId + ""));
		SubmitButton submit = new SubmitButton
                (getLocalizedString (IACCEPTTOHANDLETHISVIEWPOINT_KEY,
                                     IACCEPTTOHANDLETHISVIEWPOINT_DEFAULT));
		submit.setAsImageButton(true);
		final Table table = new Table (1, 9);
        int row = 1;
		table.setWidth (600);
		table.setCellspacing (0);
		table.setCellpadding (14);
		table.setColor (getBackgroundColor());
        table.add (getLocalizedHeader (APPLIES_KEY, APPLIES_DEFAULT), 1,
                   row);
		table.add (new Break(), 1, row);
        table.add (new Text (viewpoint.getCategory ()), 1, row++);
        table.add (getLocalizedHeader (FROMCITIZEN_KEY, FROMCITIZEN_DEFAULT),
                   1, row);
		table.add (new Break(), 1, row);
        table.add (new Text (user.getName ()), 1, row++);
        table.add (getLocalizedHeader (SUBJECT_KEY, SUBJECT_DEFAULT), 1, row);
		table.add (new Break(), 1, row);
        table.add (new Text (viewpoint.getSubject ()), 1, row++);
        table.add (getLocalizedHeader (MESSAGE_KEY, MESSAGE_DEFAULT), 1, row);
		table.add (new Break(), 1, row);
        table.add (new Text (viewpoint.getMessage ()), 1, row++);
		table.add (submit, 1, row++);
		form.add (table);
		add (form);
    }

    private void acceptToHandleViewpoint (final IWContext iwc)
        throws RemoteException, FinderException {
        // 1. parse input
        final int viewpointId
                = Integer.parseInt (iwc.getParameter (PARAM_VIEWPOINT_ID));

        // 2. registerhandler
        final ViewpointBusiness viewpointBusiness = getViewpointBusiness (iwc);
        viewpointBusiness.registerHandler (viewpointId, iwc.getCurrentUser ());

        // 3. print feedback
 		final Text text = new Text
                (getLocalizedString (CONFIRMSETHANDLER_KEY,
                                     CONFIRMSETHANDLER_DEFAULT));
		text.setWidth (Table.HUNDRED_PERCENT);
		final Table table = new Table (1, 2);
        int row = 1;
		table.setWidth (600);
		table.setCellspacing (0);
		table.setCellpadding (14);
		table.setColor (getBackgroundColor());
        table.add (text, 1, row++);
        table.add (getUserHomePageLink (iwc), 1, row++);
        add (table);
    }

    private void answerViewpoint (final IWContext iwc)
        throws RemoteException, CreateException, FinderException,
               RemoveException {
        // 1. parse input 
        final int viewpointId
                = Integer.parseInt (iwc.getParameter (PARAM_VIEWPOINT_ID));
        final String answer = iwc.getParameter (PARAM_ANSWER);

        // 2. register answer
        final ViewpointBusiness viewpointBusiness = getViewpointBusiness (iwc);
        viewpointBusiness.answerAndDeregisterViewpoint (viewpointId, answer);

        // 3. print feedback
 		final Text text
                = new Text (getLocalizedString (CONFIRMANSWERSENT_KEY,
                                                CONFIRMANSWERSENT_DEFAULT));
		text.setWidth (Table.HUNDRED_PERCENT);
		final Table table = new Table (1, 2);
        int row = 1;
		table.setWidth (600);
		table.setCellspacing (0);
		table.setCellpadding (14);
		table.setColor (getBackgroundColor());
        table.add (text, 1, row++);
        table.add (getUserHomePageLink (iwc), 1, row++);
		add (table);
    }

    private void showAnswerForm (final IWContext iwc)
        throws RemoteException, FinderException {
        final ViewpointBusiness viewpointBusiness = getViewpointBusiness (iwc);
        final int viewpointId
                = Integer.parseInt (iwc.getParameter (PARAM_VIEWPOINT_ID));
        final Viewpoint viewpoint
                = viewpointBusiness.findViewpoint (viewpointId);
		final UserBusiness userBusiness = (UserBusiness)
                IBOLookup.getServiceInstance (iwc, UserBusiness.class);
        final User user = userBusiness.getUser (viewpoint.getUserId ());
		final Form form = new Form();
		form.add (new HiddenInput (PARAM_ACTION,
                                   ANSWERVIEWPOINT_ACTION + ""));
		form.add (new HiddenInput (PARAM_VIEWPOINT_ID, viewpointId + ""));
		final TextArea textArea = new TextArea (PARAM_ANSWER);
        textArea.setColumns (40);
        textArea.setRows (10);
		SubmitButton submit = new SubmitButton (getLocalizedString
                                                (SENDANSWERTOCITIZEN_KEY,
                                                 SENDANSWERTOCITIZEN_DEFAULT));
		submit.setAsImageButton(true);
		final Table table = new Table (1, 10);
        int row = 1;
		table.setWidth (600);
		table.setCellspacing (0);
		table.setCellpadding (14);
		table.setColor (getBackgroundColor());
        table.add (getLocalizedHeader (APPLIES_KEY, APPLIES_DEFAULT), 1, row);
		table.add(new Break(), 1, row);
        table.add (new Text (viewpoint.getCategory ()), 1, row++);
        table.add (getLocalizedHeader (FROMCITIZEN_KEY, FROMCITIZEN_DEFAULT), 1,
                   row);
		table.add(new Break(), 1, row);
        table.add (new Text (user.getName ()), 1, row++);
        table.add (getLocalizedHeader (SUBJECT_KEY, SUBJECT_DEFAULT), 1, row);
		table.add(new Break(), 1, row);
        table.add (new Text (viewpoint.getSubject ()), 1, row++);
        table.add (getLocalizedHeader (MESSAGE_KEY, MESSAGE_DEFAULT), 1, row);
		table.add(new Break(), 1, row);
        table.add (new Text (viewpoint.getMessage ()), 1, row++);
        table.add (getLocalizedHeader (ANSWER_KEY, ANSWER_DEFAULT), 1, row);
		table.add(new Break(), 1, row);
        table.add (textArea, 1, row++);
		table.add (submit, 1, row++);
		form.add (table);
		add (form);
    }

    private void registerViewPoint (final IWContext iwc)
        throws RemoteException, CreateException, FinderException {
        final ViewpointBusiness viewpointBusiness = getViewpointBusiness (iwc);
        final int categoryId
                = new Integer (iwc.getParameter (PARAM_CATEGORY)).intValue ();
        final SubCategory category
                = viewpointBusiness.findSubCategory (categoryId);
        final Group handlerGroup = category.getHandlerGroup ();
        final int handlerGroupId
                = ((Integer) handlerGroup.getPrimaryKey ()).intValue ();
        viewpointBusiness.createViewpoint
                (iwc.getCurrentUser (), iwc.getParameter (PARAM_SUBJECT),
                 iwc.getParameter (PARAM_MESSAGE), category.getName (),
                 handlerGroupId);
 		final Text text1
                = new Text (getLocalizedString (CONFIRMENTERVIEWPOINT_KEY,
                                                CONFIRMENTERVIEWPOINT_DEFAULT));
		text1.setWidth (Table.HUNDRED_PERCENT);
		final Table table = new Table (1, 2);
        int row = 1;
		table.setWidth (600);
		table.setCellspacing (0);
		table.setCellpadding (14);
		table.setColor (getBackgroundColor());
        table.add (text1, 1, row++);
        table.add (getUserHomePageLink (iwc), 1, row++);
		add (table);
    }

	private void showSubCategoriesForm (final IWContext iwc)
        throws RemoteException, FinderException {
        if (!iwc.isParameterSet (PARAM_CATEGORY)) {
            showTopCategoriesForm (iwc);
            return;
        }
        
		final Form form = new Form();
		form.add (new HiddenInput (PARAM_ACTION,
                                   REGISTERVIEWPOINT_ACTION + ""));
		final DropdownMenu dropdown = new DropdownMenu (PARAM_CATEGORY);
        final int topCategoryId
                = new Integer (iwc.getParameter (PARAM_CATEGORY)).intValue ();
        final SubCategory [] categories
                = getViewpointBusiness (iwc).findSubCategories (topCategoryId);
        for (int i = 0; i < categories.length; i++) {
            final String id = categories [i].getPrimaryKey ().toString ();
            dropdown.addMenuElementFirst (id, categories [i].getName ());
        }
		final TextInput textInput = new TextInput(PARAM_SUBJECT);
		textInput.setLength (40);
		final TextArea textArea = new TextArea (PARAM_MESSAGE);
        textArea.setColumns (40);
        textArea.setRows (10);
		SubmitButton submit = new SubmitButton
                (getResourceBundle().getLocalizedString
                 (SUBMITVIEWPOINT_KEY, SUBMITVIEWPOINT_DEFAULT));
		submit.setAsImageButton(true);

		final Table table = new Table (1, 5);
        int row = 1;
		table.setWidth (600);
		table.setCellspacing (0);
		table.setCellpadding (14);
		table.setColor (getBackgroundColor());
        table.add (getLocalizedHeader (ENTERSUBCATEGORY_KEY,
                                       ENTERSUBCATEGORY_DEFAULT), 1, row++);
        table.add (dropdown, 1, row++);
        table.add (getLocalizedHeader (SUBJECT_KEY, SUBJECT_DEFAULT), 1, row);
		table.add(new Break(), 1, row);
        table.add (textInput, 1, row++);
        table.add (getLocalizedHeader (VIEWPOINTS_KEY, VIEWPOINTS_DEFAULT), 1,
                   row);
		table.add(new Break(), 1, row);
        table.add (textArea, 1, row++);
		table.add (submit, 1, row++);

		form.add (table);
		add (form);
	}

	private void showTopCategoriesForm (final IWContext iwc)
        throws RemoteException, FinderException {
		final Form form = new Form();
		form.add (new HiddenInput (PARAM_ACTION, SHOWSUBCATEGORIESFORM_ACTION
                                   + ""));
        final String description1 = getLocalizedString (DESCRIPTION1_KEY,
                                                        DESCRIPTION1_DEFAULT);
 		final Text text1 = new Text (description1);
		text1.setWidth (Table.HUNDRED_PERCENT);
        final String description2 = getLocalizedString (DESCRIPTION2_KEY,
                                                        DESCRIPTION2_DEFAULT);
        final Text text2 = new Text (description2);
		text2.setWidth (Table.HUNDRED_PERCENT);
        final RadioGroup radioGroup = new RadioGroup (PARAM_CATEGORY);
        final TopCategory [] categories
                = getViewpointBusiness (iwc).findAllTopCategories ();
        for (int i = 0; i < categories.length; i++) {
            final String id = categories [i].getPrimaryKey ().toString ();
            radioGroup.addRadioButton (id,
                                       new Text (categories [i].getName ()));
            radioGroup.setSelected (id);
        }
		SubmitButton submit = new SubmitButton
                (getLocalizedString (CONTINUE_KEY, CONTINUE_DEFAULT));
		submit.setAsImageButton(true);

		final Table table = new Table (1,5);
        int row = 1;
		table.setWidth (600);
		table.setCellspacing (0);
		table.setCellpadding (14);
		table.setColor (getBackgroundColor());
        table.add (text1, 1, row++);
        table.add (text2, 1, row++);
        table.add (getLocalizedHeader (ENTERTOPCATEGORY_KEY,
                                       ENTERTOPCATEGORY_DEFAULT), 1, row++);
        table.add (radioGroup, 1, row++);
		table.add (submit, 1, row++);

		form.add (table);
		add (form);
	}

    private Link getUserHomePageLink (final IWContext iwc)
        throws RemoteException {
        final Text userHomePageText
                = new Text (getLocalizedString (GOBACKTOMYPAGE_KEY,
                                                GOBACKTOMYPAGE_DEFAULT));
        final Link link = new Link (userHomePageText);
        link.setPage(iwc.getCurrentUser ().getHomePageID ());
        return (link);
    }

    private String getLocalizedString (final String key, final String value) {
        return getResourceBundle ().getLocalizedString (key, value);
    }

	private ViewpointBusiness getViewpointBusiness (final IWContext iwc)
        throws RemoteException {
		return (ViewpointBusiness)
                IBOLookup.getServiceInstance(iwc, ViewpointBusiness.class);
	}
}
