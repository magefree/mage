package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pal00
 */
public class ArenaLeague2000 extends ExpansionSet {

    private static final ArenaLeague2000 instance = new ArenaLeague2000();

    public static ArenaLeague2000 getInstance() {
        return instance;
    }

    private ArenaLeague2000() {
        super("Arena League 2000", "PAL00", ExpansionSet.buildDate(2000, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Chill", 4, Rarity.RARE, mage.cards.c.Chill.class));
        cards.add(new SetCardInfo("Duress", 2, Rarity.RARE, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Enlightened Tutor", 6, Rarity.RARE, mage.cards.e.EnlightenedTutor.class));
        cards.add(new SetCardInfo("Forest", 12, Rarity.LAND, mage.cards.basiclands.Forest.class));
        cards.add(new SetCardInfo("Island", 9, Rarity.LAND, mage.cards.basiclands.Island.class));
        cards.add(new SetCardInfo("Mountain", 11, Rarity.LAND, mage.cards.basiclands.Mountain.class));
        cards.add(new SetCardInfo("Pillage", 5, Rarity.RARE, mage.cards.p.Pillage.class));
        cards.add(new SetCardInfo("Plains", 8, Rarity.LAND, mage.cards.basiclands.Plains.class));
        cards.add(new SetCardInfo("Stupor", 7, Rarity.RARE, mage.cards.s.Stupor.class));
        cards.add(new SetCardInfo("Swamp", 10, Rarity.LAND, mage.cards.basiclands.Swamp.class));
        cards.add(new SetCardInfo("Uktabi Orangutan", 3, Rarity.RARE, mage.cards.u.UktabiOrangutan.class));
     }
}
