package mage.deck;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;
import mage.cards.decks.Deck;
import mage.cards.decks.DeckValidatorError;

import java.util.*;

/**
 * This class represents a deck from any past standard.
 * <p>
 * This class was originally made to work with the historical standard ruleset.
 * Data taken from http://thattournament.website/historic-tournament.php (site
 * changed, originally http://mtgt.nfshost.com/historic-tournament.php)
 * <p>
 * If there are any questions or corrections, feel free to contact me.
 *
 * @author Marthinwurer (at gmail.com)
 */
public class HistoricalType2 extends Constructed {

    /*
     * This array stores the set codes of each standard up to
     * Kamigawa/Ravnica standard, where rotation stabilized.
     */
    protected static final String[][] standards = {
            //  1st standard: The Dark, Fallen Empires, and 4th.
            {"DRK", "FEM", "4ED"},
            //  2nd standard: 4th, Fallen Empires, Ice Age, Chronicles, Homelands,
            //                Alliances, and Mirage.
            {"FEM", "4ED", "ICE", "CHR", "HML", "ALL", "MIR"},
            //  3rd standard: 4th, Chronicles, Alliances, Mirage, Visions.
            {"4ED", "CHR", "ALL", "MIR", "VIS"},
            //  4th Standard: Ice Age, Homelands, Alliances, Mirage, Visions, 5th,
            //                and Weatherlight.
            {"ICE", "HML", "ALL", "MIR", "VIS", "5ED", "WTH"},
            //  5th Standard: Mirage, Visions, 5th, Weatherlight, Tempest,
            //                Stronghold, and Exodus.
            {"MIR", "VIS", "5ED", "WTH", "TMP", "STH", "EXO"},
            //  6th Standard: 5th, Tempest, Stronghold, Exodus, Urza's Saga, Urza's
            //                Legacy, Urza's Destiny.
            {"5ED", "TMP", "STH", "EXO", "USG", "ULG"},
            //  7th Standard: Tempest, Stronghold, Exodus, Urza's Saga, Urza's
            //                Legacy, 6th, Urza's Destiny.
            {"TMP", "STH", "EXO", "USG", "ULG", "6ED", "UDS"},
            //  8th Standard: Urza's Saga, Urza's Legacy, 6th, Urza's Destiny,
            //                Mercadian Masques, Nemesis, Prophecy.
            {"USG", "ULG", "6ED", "UDS", "MMQ", "NEM", "PCY"},
            //  9th Standard
            {"6ED", "MMQ", "NEM", "PCY", "INV", "PLS"},
            //  10th Standard
            {"7ED", "MMQ", "NEM", "PCY", "INV", "PLS", "APC"},
            // 11th Standard
            {"7ED", "INV", "APC", "PLS", "ODY", "TOR", "JUD"},
            // 12th Standard
            {"7ED", "ODY", "TOR", "JUD", "ONS", "LGN", "SCG"},
            // 13th Standard
            {"8ED", "ODY", "TOR", "JUD", "ONS", "LGN", "SCG"},
            // 14th Standard
            {"8ED", "ONS", "LGN", "SCG", "MRD", "DST", "5DN"},
            // 15th Standard
            {"8ED", "MRD", "DST", "5DN", "CHK", "BOK", "SOK"},
            // 16th Standard
            {"9ED", "MRD", "DST", "5DN", "CHK", "BOK", "SOK"},
            // 17th Standard
            {"9ED", "CHK", "BOK", "SOK", "RAV", "GPT", "DIS", "CSP"},
            // 18th Standard
            {"9ED", "RAV", "GPT", "DIS", "CSP", "TSP", "TSB", "PLC", "FUT"},
            // 19th Standard
            {"10E", "RAV", "GPT", "DIS", "CSP", "TSP", "TSB", "PLC", "FUT"},
            // 20th Standard
            {"10E", "CSP", "TSP", "TSB", "PLC", "FUT", "LRW", "MOR", "SHM", "EVE"},
            // 21st Standard
            {"10E", "LRW", "MOR", "SHM", "EVE", "ALA", "CON", "ARB"}
    };

    /**
     * Constructor. Don't need to mess with any of the sets yet; that will be
     * done in the overridden validate function.
     */
    public HistoricalType2() {
        super("Constructed - Historical Type 2", "Hist. Type 2");

        // banned cards
        banned.add("Balance");
        banned.add("Earthcraft");
        banned.add("Memory Jar");
        banned.add("Mind Over Matter");
        banned.add("Mind Twist");
        banned.add("Skullclamp");
        banned.add("Time Spiral");
        banned.add("Tolarian Academy");
        banned.add("Yawgmoth's Bargain");
    }

    /**
     * Overridden validate function. Changes the standard sets, then uses the
     * regular validation function to test validity.
     *
     * @param deck - the deck to validate.
     * @return
     */
    @Override
    public boolean validate(Deck deck) {

        List<DeckValidatorError> leastInvalid = null;

        boolean valid = false;
        errorsList.clear();

        // first, check whether misty and batterskull are in the same deck.
        Map<String, Integer> counts = new HashMap<>();
        countCards(counts, deck.getCards());
        countCards(counts, deck.getSideboard());
        if (counts.containsKey("Stoneforge Mystic")
                && counts.containsKey("Batterskull")) {

            // if both, then skip all following tests by returning
            return false;
        }

        // up to Lorwyn/Alara, standards will have to be hard-coded.
        // iterate through the array of standards.
        for (String[] sets : standards) {

            // clear the invalid list
            errorsList.clear();

            // add the sets to the setCodes.
            setCodes = new ArrayList<>(Arrays.asList(sets));

            // validate it. If it validates, clear the invalid cards and break.
            if (super.validate(deck)) {
                valid = true;
                break;
            }

            // if the map holding the invalid cards is empty, set it to a
            // copy of the current invalid list.
            if (leastInvalid == null) {
                leastInvalid = new ArrayList<>(this.getErrorsList());
                continue;
            }

            // see how many invalid cards there are. if there are less invalid
            // cards than the stored invalid list, assign the current invalid
            // to leastInvalid.
            if (leastInvalid.size() > this.getErrorsList().size()) {
                leastInvalid = new ArrayList<>(this.getErrorsList());
            }
        }

        // After testing the first few standards, do the regular ones.
        // set the initial starting and ending date, as well as the current.
        GregorianCalendar start = new GregorianCalendar(2006,
                Calendar.SEPTEMBER, 1);
        GregorianCalendar end = new GregorianCalendar(2008,
                Calendar.SEPTEMBER, 1);
        GregorianCalendar current = new GregorianCalendar();

        // use the method for determining regular standard legality, but change
        // the date for each standard.
        while (end.before(current) && !valid) {

            // clear the invalid list and set codes.
            setCodes.clear();
            errorsList.clear();

            // increment the start and end dates.
            start.set(Calendar.YEAR, start.get(Calendar.YEAR) + 1);
            end.set(Calendar.YEAR, start.get(Calendar.YEAR) + 2);

            // Get the sets in that time period.
            // (code taken from standard.java)
            for (ExpansionSet set : Sets.getInstance().values()) {
                if (set.getSetType().isStandardLegal()
                        && set.getReleaseDate().after(start.getTime())
                        && set.getReleaseDate().before(end.getTime())) {
                    setCodes.add(set.getCode());
                }
            }

            // validate it. If it validates, clear the invalid cards and break.
            if (super.validate(deck)) {
                errorsList.clear();
                valid = true;
                break;
            }

            // see how many invalid cards there are. if there are less invalid
            // cards than the stored invalid list, assign the current invalid
            // to leastInvalid.
            if (leastInvalid == null) {
                leastInvalid = new ArrayList<>(this.getErrorsList());
            } else if (leastInvalid.size() > this.getErrorsList().size()) {
                leastInvalid = new ArrayList<>(this.getErrorsList());
            }
        }

        // if no standard environment is valid, set the invalid to the
        // invalid that had the least errors.
        if (!valid) {
            this.errorsList = new ArrayList<>(leastInvalid);
        }

        // return the validity.
        return valid;
    }

}
