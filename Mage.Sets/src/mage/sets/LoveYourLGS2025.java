package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/plg25
 */
public class LoveYourLGS2025 extends ExpansionSet {

    private static final LoveYourLGS2025 instance = new LoveYourLGS2025();

    public static LoveYourLGS2025 getInstance() {
        return instance;
    }

    private LoveYourLGS2025() {
        super("Love Your LGS 2025", "PLG25", ExpansionSet.buildDate(2025, 3, 26), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Chromatic Lantern", 1, Rarity.RARE, mage.cards.c.ChromaticLantern.class));
        cards.add(new SetCardInfo("Wastes", 2, Rarity.RARE, mage.cards.w.Wastes.class));
    }
}
