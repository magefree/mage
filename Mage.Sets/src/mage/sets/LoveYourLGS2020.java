package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/plg20
 */
public class LoveYourLGS2020 extends ExpansionSet {

    private static final LoveYourLGS2020 instance = new LoveYourLGS2020();

    public static LoveYourLGS2020 getInstance() {
        return instance;
    }

    private LoveYourLGS2020() {
        super("Love Your LGS 2020", "PLG20", ExpansionSet.buildDate(2020, 5, 18), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Hangarback Walker", 2, Rarity.RARE, mage.cards.h.HangarbackWalker.class));
        cards.add(new SetCardInfo("Reliquary Tower", 1, Rarity.RARE, mage.cards.r.ReliquaryTower.class));
    }
}
