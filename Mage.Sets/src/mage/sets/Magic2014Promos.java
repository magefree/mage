package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pm14
 */
public class Magic2014Promos extends ExpansionSet {

    private static final Magic2014Promos instance = new Magic2014Promos();

    public static Magic2014Promos getInstance() {
        return instance;
    }

    private Magic2014Promos() {
        super("Magic 2014 Promos", "PM14", ExpansionSet.buildDate(2013, 7, 18), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Colossal Whale", "48*", Rarity.RARE, mage.cards.c.ColossalWhale.class));
        cards.add(new SetCardInfo("Goblin Diplomats", "141*", Rarity.RARE, mage.cards.g.GoblinDiplomats.class));
        cards.add(new SetCardInfo("Hive Stirrings", "21*", Rarity.COMMON, mage.cards.h.HiveStirrings.class));
        cards.add(new SetCardInfo("Megantic Sliver", "185*", Rarity.RARE, mage.cards.m.MeganticSliver.class));
        cards.add(new SetCardInfo("Ratchet Bomb", "215*", Rarity.RARE, mage.cards.r.RatchetBomb.class));
    }
}
