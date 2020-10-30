package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/g06
 */
public class JudgeGiftCards2006 extends ExpansionSet {

    private static final JudgeGiftCards2006 instance = new JudgeGiftCards2006();

    public static JudgeGiftCards2006 getInstance() {
        return instance;
    }

    private JudgeGiftCards2006() {
        super("Judge Gift Cards 2006", "G06", ExpansionSet.buildDate(2006, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Exalted Angel", 1, Rarity.RARE, mage.cards.e.ExaltedAngel.class));
        cards.add(new SetCardInfo("Grim Lavamancer", 2, Rarity.RARE, mage.cards.g.GrimLavamancer.class));
        cards.add(new SetCardInfo("Meddling Mage", 3, Rarity.RARE, mage.cards.m.MeddlingMage.class));
        cards.add(new SetCardInfo("Pernicious Deed", 4, Rarity.RARE, mage.cards.p.PerniciousDeed.class));
     }
}
