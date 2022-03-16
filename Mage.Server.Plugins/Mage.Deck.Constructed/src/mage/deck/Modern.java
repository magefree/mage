package mage.deck;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author LevelX2
 */
public class Modern extends Constructed {

    public Modern() {
        super("Constructed - Modern");

        Date cutoff = new GregorianCalendar(2003, Calendar.JULY, 28).getTime(); // Eight edition release date
        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.getSetType().isModernLegal() && (set.getReleaseDate().after(cutoff) || set.getReleaseDate().equals(cutoff))) {
                setCodes.add(set.getCode());
            }
        }

        banned.add("Ancient Den");
        banned.add("Arcum's Astrolabe");
        banned.add("Birthing Pod");
        banned.add("Blazing Shoal");
        banned.add("Bridge from Below");
        banned.add("Chrome Mox");
        banned.add("Cloudpost");
        banned.add("Dark Depths");
        banned.add("Deathrite Shaman");
        banned.add("Dig Through Time");
        banned.add("Dread Return");
        banned.add("Eye of Ugin");
        banned.add("Faithless Looting");
        banned.add("Field of the Dead");
        banned.add("Gitaxian Probe");
        banned.add("Glimpse of Nature");
        banned.add("Golgari Grave-Troll");
        banned.add("Great Furnace");
        banned.add("Green Sun's Zenith");
        banned.add("Hogaak, Arisen Necropolis");
        banned.add("Hypergenesis");
        banned.add("Krark-Clan Ironworks");
        banned.add("Lurrus of the Dream-Den");
        banned.add("Mental Misstep");
        banned.add("Mox Opal");
        banned.add("Mycosynth Lattice");
        banned.add("Mystic Sanctuary");
        banned.add("Oko, Thief of Crowns");
        banned.add("Once Upon a Time");
        banned.add("Ponder");
        banned.add("Preordain");
        banned.add("Punishing Fire");
        banned.add("Rite of Flame");
        banned.add("Seat of the Synod");
        banned.add("Second Sunrise");
        banned.add("Seething Song");
        banned.add("Sensei's Divining Top");
        banned.add("Simian Spirit Guide");
        banned.add("Skullclamp");
        banned.add("Splinter Twin");
        banned.add("Summer Bloom");
        banned.add("Tibalt's Trickery");
        banned.add("Treasure Cruise");
        banned.add("Tree of Tales");
        banned.add("Umezawa's Jitte");
        banned.add("Uro, Titan of Nature's Wrath");
        banned.add("Vault of Whispers");
    }
}
