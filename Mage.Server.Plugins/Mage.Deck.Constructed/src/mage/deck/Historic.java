package mage.deck;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author mikalinn777
 * Historic is a nonrotating format in MTGA. https://mtg.gamepedia.com/Historic_(format)
 */
public class Historic extends Constructed {

    public Historic() {
        super("Constructed - Historic");

        Date cutoff = new GregorianCalendar(2017, Calendar.SEPTEMBER, 29).getTime(); // XLN release date
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.getSetType().isHistoricLegal() && (set.getReleaseDate().after(cutoff) || set.getReleaseDate().equals(cutoff))) {
                setCodes.add(set.getCode());
            }
        }
        // Additional sets
        setCodes.add("JMP"); // Jumpstart (replacements below)
        setCodes.add("STA"); // Strixhaven Mystical Archive
        setCodes.add("J21"); // Jumpstart: Historic Horizons

        // Regular ban list
        banned.add("Agent of Treachery");
        banned.add("Brainstorm");
        banned.add("Channel");
        banned.add("Counterspell");
        banned.add("Dark Ritual");
        banned.add("Demonic Tutor");
        banned.add("Fires of Invention");
        banned.add("Lightning Bolt");
        banned.add("Memory Lapse");
        banned.add("Natural Order");
        banned.add("Nexus of Fate");
        banned.add("Oko, Thief of Crowns");
        banned.add("Omnath, Locus of Creation");
        banned.add("Once Upon a Time");
        banned.add("Swords to Plowshares");
        banned.add("Teferi, Time Raveler");
        banned.add("Thassa's Oracle");
        banned.add("Tibalt's Trickery");
        banned.add("Time Warp");
        banned.add("Uro, Titan of Nature's Wrath");
        banned.add("Veil of Summer");
        banned.add("Wilderness Reclamation");
        banned.add("Winota, Joiner of Forces");

        // Individual cards added
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

        // Jumpstart replacements
        singleCards.add("Archon of Sun's Grace");
        singleCards.add("Banishing Light");
        singleCards.add("Gadwick, the Wizened");
        singleCards.add("Carnifex Demon");
        singleCards.add("Weight of Memory");
        singleCards.add("Bond of Revival");
        singleCards.add("Audacious Thief");
        singleCards.add("Lightning Serpent");
        singleCards.add("Fanatic of Mogis");
        singleCards.add("Woe Strider");
        singleCards.add("Goblin Oriflamme");
        singleCards.add("Doomed Necromancer");
        singleCards.add("Scorching Dragonfire");
        singleCards.add("Prey Upon");
        singleCards.add("Lightning Strike");
        singleCards.add("Pollenbright Druid");
        singleCards.add("Dryad Greenseeker");
        singleCards.add("Serra's Guardian");
        // these cards aren't technically banned, so if any of them are added later they should be removed here
        banned.add("Ajani's Chosen");
        banned.add("Angelic Arbiter");
        banned.add("Path to Exile");
        banned.add("Read the Runes");
        banned.add("Rhystic Study");
        banned.add("Thought Scour");
        banned.add("Exhume");
        banned.add("Mausoleum Turnkey");
        banned.add("Reanimate");
        banned.add("Scourge of Nel Toth");
        banned.add("Ball Lightning");
        banned.add("Chain Lightning");
        banned.add("Draconic Roar");
        banned.add("Flametongue Kavu");
        banned.add("Goblin Lore");
        banned.add("Fa'adiyah Seer");
        banned.add("Scrounging Bandar");
        banned.add("Time to Feed");
    }
}
