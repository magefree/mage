package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * Alchemy - Digital-only rebalanced cards from MTG Arena
 * @author Vernon
 */
public final class Alchemy extends ExpansionSet {

    private static final Alchemy instance = new Alchemy();

    public static Alchemy getInstance() {
        return instance;
    }

    private Alchemy() {
        super("Alchemy", "ALC", ExpansionSet.buildDate(2021, 12, 8), SetType.DIGITAL);
        this.hasBasicLands = false;
        this.hasBoosters = false;

        // A-Luminarch Aspirant - digital rebalance with End Step trigger instead of Combat
        cards.add(new SetCardInfo("A-Luminarch Aspirant", 1, Rarity.UNCOMMON, mage.cards.a.ALuminarchAspirant.class));
    }
}

