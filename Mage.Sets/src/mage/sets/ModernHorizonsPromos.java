package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pmh1
 */
public class ModernHorizonsPromos extends ExpansionSet {

    private static final ModernHorizonsPromos instance = new ModernHorizonsPromos();

    public static ModernHorizonsPromos getInstance() {
        return instance;
    }

    private ModernHorizonsPromos() {
        super("Modern Horizons Promos", "PMH1", ExpansionSet.buildDate(2019, 6, 14), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Astral Drift", 3, Rarity.RARE, mage.cards.a.AstralDrift.class));
     }
}
