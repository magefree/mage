package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pm13
 */
public class Magic2013Promos extends ExpansionSet {

    private static final Magic2013Promos instance = new Magic2013Promos();

    public static Magic2013Promos getInstance() {
        return instance;
    }

    private Magic2013Promos() {
        super("Magic 2013 Promos", "PM13", ExpansionSet.buildDate(2012, 7, 12), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Cathedral of War", "221*", Rarity.RARE, mage.cards.c.CathedralOfWar.class));
        cards.add(new SetCardInfo("Magmaquake", "140*", Rarity.RARE, mage.cards.m.Magmaquake.class));
        cards.add(new SetCardInfo("Mwonvuli Beast Tracker", "177*", Rarity.UNCOMMON, mage.cards.m.MwonvuliBeastTracker.class));
        cards.add(new SetCardInfo("Staff of Nin", "217*", Rarity.RARE, mage.cards.s.StaffOfNin.class));
        cards.add(new SetCardInfo("Xathrid Gorgon", "118*", Rarity.RARE, mage.cards.x.XathridGorgon.class));
    }
}
