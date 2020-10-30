package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pnat
 */
public class NationalsPromos extends ExpansionSet {

    private static final NationalsPromos instance = new NationalsPromos();

    public static NationalsPromos getInstance() {
        return instance;
    }

    private NationalsPromos() {
        super("Nationals Promos", "PNAT", ExpansionSet.buildDate(2018, 1, 25), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Flooded Strand", 2018, Rarity.RARE, mage.cards.f.FloodedStrand.class));
     }
}
