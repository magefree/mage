package mage.deck;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author mikalinn777
 * 
 * Historic is a nonrotating format in MTGA. https://mtg.gamepedia.com/Historic_(format)
 */
public class Historic extends Constructed {

    public Historic() {
        super("Constructed - Historic");

        Date cutoff = new GregorianCalendar(2017, Calendar.SEPTEMBER, 29).getTime(); // XLN release date
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.getSetType().isStandardLegal() && (set.getReleaseDate().after(cutoff) || set.getReleaseDate().equals(cutoff))) {
                setCodes.add(set.getCode());
                setCodes.add(mage.sets.HistoricAnthology1.getInstance().getCode());
                setCodes.add(mage.sets.HistoricAnthology2.getInstance().getCode());
                setCodes.add(mage.sets.HistoricAnthology3.getInstance().getCode());
                singleCards.add("Rhys the Redeemed");
                singleCards.add("Spiritual Guardian");
                singleCards.add("Sanctuary Cat");
                singleCards.add("Raging Goblin");
                singleCards.add("Hanna, Ship's Navigator");
                singleCards.add("Angelic Reward");
                singleCards.add("Confront the Assault");
                singleCards.add("Inspiring Commander");
                singleCards.add("Shrine Keeper");
                singleCards.add("Tactical Advantage");
                singleCards.add("River's Favor");
                singleCards.add("Shorecomber Crab");
                singleCards.add("Zephyr Gull");
                singleCards.add("Feral Roar");
                singleCards.add("Treetop Warden");
                singleCards.add("Angler Turtle");
                singleCards.add("Vengeant Vampire");
                singleCards.add("Rampaging Brontodon");
                singleCards.add("Bladewing the Risen");
                singleCards.add("The Gitrog Monster");
                singleCards.add("Talrand, Sky Summoner");
            }
        }
        banned.add("Fires of Invention");
        banned.add("Agent of Treachery");
        banned.add("Winota, Joiner of Forces");
        banned.add("Nexus of Fate");
        banned.add("Oko, Thief of Crowns");
        banned.add("Once Upon a Time");
        banned.add("Veil of Summer");
    }
}
