package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/p10e
 */
public class TenthEditionPromos extends ExpansionSet {

    private static final TenthEditionPromos instance = new TenthEditionPromos();

    public static TenthEditionPromos getInstance() {
        return instance;
    }

    private TenthEditionPromos() {
        super("Tenth Edition Promos", "P10E", ExpansionSet.buildDate(2007, 7, 13), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Faerie Conclave", 1, Rarity.RARE, mage.cards.f.FaerieConclave.class));
        cards.add(new SetCardInfo("Treetop Village", 2, Rarity.RARE, mage.cards.t.TreetopVillage.class));
        cards.add(new SetCardInfo("Reya Dawnbringer", 35, Rarity.RARE, mage.cards.r.ReyaDawnbringer.class));
     }
}
