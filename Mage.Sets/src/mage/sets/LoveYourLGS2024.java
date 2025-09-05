package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/plg24
 */
public class LoveYourLGS2024 extends ExpansionSet {

    private static final LoveYourLGS2024 instance = new LoveYourLGS2024();

    public static LoveYourLGS2024 getInstance() {
        return instance;
    }

    private LoveYourLGS2024() {
        super("Love Your LGS 2024", "PLG24", ExpansionSet.buildDate(2024, 8, 6), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Cut Down", "3J", Rarity.RARE, mage.cards.c.CutDown.class));
        cards.add(new SetCardInfo("Lay Down Arms", "1J", Rarity.RARE, mage.cards.l.LayDownArms.class));
        cards.add(new SetCardInfo("Sakura-Tribe Elder", 1, Rarity.RARE, mage.cards.s.SakuraTribeElder.class, FULL_ART));
        cards.add(new SetCardInfo("Sheoldred's Edict", "4J", Rarity.RARE, mage.cards.s.SheoldredsEdict.class));
        cards.add(new SetCardInfo("Sleight of Hand", "2J", Rarity.RARE, mage.cards.s.SleightOfHand.class));
    }
}
