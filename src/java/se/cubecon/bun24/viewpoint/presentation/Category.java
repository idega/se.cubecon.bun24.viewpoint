package se.cubecon.bun24.viewpoint.presentation;

import java.util.*;

/**
 * @author <a href="http://www.staffannoteberg.com">Staffan N�teberg</a>
 */
abstract class Category {
    private static final int ANORDNARE = 11;
    private static final int EKONOMI = 12;
    private static final int FINANSGRUPPEN = 13;
    private static final int INTAGNINGSGRUPPEN = 14;
    private static final int KUNDVALSGRUPPEN = 15;
    private static final int MYNDIGHETSGRUPPEN = 16;
    private static final int NAMNDSEKRETERARE = 17;

    private static int categoryCount = 0;
    private static final Map categories = new HashMap ();
    private final int id = categoryCount++;
    private final String name;

    private Category (final String name) {
        this.name = name;
        categories.put (new Integer (id), this);
    }

    String getName () {
        return name;
    }

    int getId () {
        return id;
    }

    static Category getCategory (final int id) {
        return (Category) categories.get (new Integer(id));
    }

    static TopCategory [] getTopCategories () {
        final List result = new ArrayList ();
        final Collection categoryCollection = categories.values ();
        for (final Iterator i = categoryCollection.iterator (); i.hasNext ();) {
            final Category category = (Category) i.next ();
            if (category instanceof TopCategory) {
                result.add (category);
            }
        }
        return (TopCategory []) result.toArray (new TopCategory [0]);
    }

    static class TopCategory extends Category {
        private final Set subCategories = new HashSet ();

        private TopCategory (final String name) {
            super (name);
        }

        SubCategory [] getSubCategories () {
            return (SubCategory []) subCategories.toArray (new SubCategory [0]);
        }

        private void addSubCategory (final SubCategory subCategory) {
            subCategories.add (subCategory);
        }
    }
    
    static class SubCategory extends Category {
        private final int handlerGroupId;

        private SubCategory (final String name, final TopCategory parent,
                             final int handlerGroupId) {
            super (name);
            this.handlerGroupId = handlerGroupId;
            parent.addSubCategory (this);
        }

        int getHandlerGroupId () {
            return handlerGroupId;
        }
    }

    static {
        final TopCategory Barnomsorg = new TopCategory ("Barnomsorg");
        final TopCategory Skola = new TopCategory ("Skola");
        final TopCategory Gymnasieskola = new TopCategory ("Gymnasieskola");
        final TopCategory Komvux = new TopCategory ("Komvux");
        final TopCategory Politiker = new TopCategory ("Politiker");
        final TopCategory Myndigheten = new TopCategory ("Myndigheten"); 

        new SubCategory ("Anordnare", Barnomsorg, MYNDIGHETSGRUPPEN);
        new SubCategory ("Betalningar", Barnomsorg, EKONOMI);
        new SubCategory ("K�tid", Barnomsorg, ANORDNARE);
        new SubCategory ("Regelverk", Barnomsorg, KUNDVALSGRUPPEN);
        new SubCategory ("Taxan", Barnomsorg, KUNDVALSGRUPPEN);
        new SubCategory ("�vrigt", Barnomsorg, KUNDVALSGRUPPEN);

        new SubCategory ("Fritids", Skola, KUNDVALSGRUPPEN);
        new SubCategory ("F�rskoleklass", Skola, KUNDVALSGRUPPEN);
        new SubCategory ("Likv�rdighetsgaranti", Skola, MYNDIGHETSGRUPPEN);
        new SubCategory ("Modersm�l", Skola, MYNDIGHETSGRUPPEN);
        new SubCategory ("Regelverk", Skola, KUNDVALSGRUPPEN);
        new SubCategory ("Skolskjuts", Skola, MYNDIGHETSGRUPPEN);
        new SubCategory ("Skolval", Skola, ANORDNARE);
        new SubCategory ("�vrigt", Skola, KUNDVALSGRUPPEN);

        new SubCategory ("Elevv�rd", Gymnasieskola, MYNDIGHETSGRUPPEN);
        new SubCategory ("Inackorderingsbidrag", Gymnasieskola, MYNDIGHETSGRUPPEN);
        new SubCategory ("Intagning", Gymnasieskola, INTAGNINGSGRUPPEN);
        new SubCategory ("Programval", Gymnasieskola, KUNDVALSGRUPPEN);
        new SubCategory ("Skolh�lsov�rd", Gymnasieskola, MYNDIGHETSGRUPPEN);
        new SubCategory ("Studiebidrag", Gymnasieskola, MYNDIGHETSGRUPPEN);
        new SubCategory ("�vrigt", Gymnasieskola, KUNDVALSGRUPPEN);

        new SubCategory ("Anordnare", Komvux, MYNDIGHETSGRUPPEN);
        new SubCategory ("Kurser/utbud", Komvux, KUNDVALSGRUPPEN);
        new SubCategory ("SFI", Komvux, KUNDVALSGRUPPEN);
        new SubCategory ("Studiebidrag", Komvux, MYNDIGHETSGRUPPEN);
        new SubCategory ("Studiev�gledning", Komvux, KUNDVALSGRUPPEN);
        new SubCategory ("�vrigt", Komvux, KUNDVALSGRUPPEN);

        new SubCategory ("Beslut i n�mnden", Politiker, NAMNDSEKRETERARE);
        new SubCategory ("Enskilt �rende", Politiker, MYNDIGHETSGRUPPEN);
        new SubCategory ("F�rslag", Politiker, NAMNDSEKRETERARE);
        new SubCategory ("Kundvalet", Politiker, KUNDVALSGRUPPEN);
        new SubCategory ("�vrigt", Politiker, NAMNDSEKRETERARE);

        new SubCategory ("BUN24", Myndigheten, KUNDVALSGRUPPEN);
        new SubCategory ("Barnomsorgscheck", Myndigheten, FINANSGRUPPEN);
        new SubCategory ("Handikapp", Myndigheten, MYNDIGHETSGRUPPEN);
        new SubCategory ("Likv�rdighetsgaranti", Myndigheten, MYNDIGHETSGRUPPEN);
        new SubCategory ("Service", Myndigheten, KUNDVALSGRUPPEN);
        new SubCategory ("Skolpeng", Myndigheten, FINANSGRUPPEN);
        new SubCategory ("�vrigt", Myndigheten, MYNDIGHETSGRUPPEN);
    }
}
