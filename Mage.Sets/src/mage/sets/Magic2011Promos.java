package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pm11
 */
public class Magic2011Promos extends ExpansionSet {

    private static final Magic2011Promos instance = new Magic2011Promos();

    public static Magic2011Promos getInstance() {
        return instance;
    }

    private Magic2011Promos() {
        super("Magic 2011 Promos", "PM11", ExpansionSet.buildDate(2010, 7, 15), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ancient Hellkite", "122*", Rarity.RARE, mage.cards.a.AncientHellkite.class));
        cards.add(new SetCardInfo("Birds of Paradise", "165*", Rarity.RARE, mage.cards.b.BirdsOfParadise.class));
        cards.add(new SetCardInfo("Sun Titan", "35*", Rarity.MYTHIC, mage.cards.s.SunTitan.class));
    }
}
