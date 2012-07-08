package mage.client.util.sets;

import mage.cards.ExpansionSet;
import mage.client.cards.CardsStorage;
import mage.sets.Sets;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Utility class for constructed formats.
 *
 * @author nantuko
 */
public class ConstructedFormats {

    private static final String[] constructedFormats = {"M13", "ISD\\DKA\\AVR", "Standard", "Extended", "Modern", "All"};

    private ConstructedFormats() {
    }

    public static String[] getTypes() {
        return constructedFormats;
    }

    public static String getDefault() {
        return constructedFormats[1];
    }

    public static List<String> getSetsByFormat(String format) {
        if (format.equals("M13")) {
            return m13;
        }
        if (format.equals("ISD\\DKA\\AVR")) {
            return innistradBlock;
        }
        if (format.equals("Standard")) {
            return standard;
        }
        if (format.equals("Extended")) {
            return extended;
        }
        if (format.equals("Modern")) {
            return modern;
        }
        return all;
    }

    private static void buildLists() {
        for (String setCode : CardsStorage.getSetCodes()) {
            ExpansionSet set = Sets.findSet(setCode);
            if (set.getReleaseDate().after(m13Date)) {
                m13.add(set.getCode());
            }
            if (set.getReleaseDate().after(innistradBlockDate) && set.getReleaseDate().before(m13Date)) {
                innistradBlock.add(set.getCode());
            }
            if (set.getReleaseDate().after(standardDate)) {
                standard.add(set.getCode());
            }
            if (set.getReleaseDate().after(extendedDate)) {
                extended.add(set.getCode());
            }
            if (set.getReleaseDate().after(modernDate)) {
                modern.add(set.getCode());
            }
        }
    }

    private static final List<String> m13= new ArrayList<String>();
    private static final Date m13Date = new GregorianCalendar(2012, 6, 6).getTime();

    private static final List<String> innistradBlock = new ArrayList<String>();
    private static final Date innistradBlockDate = new GregorianCalendar(2011, 9, 29).getTime();

    private static final List<String> standard = new ArrayList<String>();
    private static final Date standardDate = new GregorianCalendar(2010, 9, 20).getTime();

    private static final List<String> extended = new ArrayList<String>();
    private static final Date extendedDate = new GregorianCalendar(2008, 9, 20).getTime();

    private static final List<String> modern = new ArrayList<String>();
    private static final Date modernDate = new GregorianCalendar(2003, 7, 20).getTime();

    // for all sets just return empty list
    private static final List<String> all = new ArrayList<String>();

    static {
        buildLists();
    }
}
