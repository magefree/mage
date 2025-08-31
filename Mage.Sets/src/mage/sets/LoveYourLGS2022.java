package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/plg22
 */
public class LoveYourLGS2022 extends ExpansionSet {

    private static final LoveYourLGS2022 instance = new LoveYourLGS2022();

    public static LoveYourLGS2022 getInstance() {
        return instance;
    }

    private LoveYourLGS2022() {
        super("Love Your LGS 2022", "PLG22", ExpansionSet.buildDate(2022, 7, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Sol Ring", 1, Rarity.RARE, mage.cards.s.SolRing.class, RETRO_ART));
        cards.add(new SetCardInfo("Thought Vessel", 2, Rarity.RARE, mage.cards.t.ThoughtVessel.class));
    }
}
