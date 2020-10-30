package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/j18
 */
public class JudgeGiftCards2018 extends ExpansionSet {

    private static final JudgeGiftCards2018 instance = new JudgeGiftCards2018();

    public static JudgeGiftCards2018 getInstance() {
        return instance;
    }

    private JudgeGiftCards2018() {
        super("Judge Gift Cards 2018", "J18", ExpansionSet.buildDate(2018, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Commander's Sphere", 4, Rarity.RARE, mage.cards.c.CommandersSphere.class));
        cards.add(new SetCardInfo("Food Chain", 8, Rarity.RARE, mage.cards.f.FoodChain.class));
        cards.add(new SetCardInfo("Lord of Atlantis", 6, Rarity.RARE, mage.cards.l.LordOfAtlantis.class));
        cards.add(new SetCardInfo("Merchant Scroll", 1, Rarity.RARE, mage.cards.m.MerchantScroll.class));
        cards.add(new SetCardInfo("Nin, the Pain Artist", 3, Rarity.RARE, mage.cards.n.NinThePainArtist.class));
        cards.add(new SetCardInfo("Rhystic Study", 7, Rarity.RARE, mage.cards.r.RhysticStudy.class));
        cards.add(new SetCardInfo("Teferi's Protection", 5, Rarity.RARE, mage.cards.t.TeferisProtection.class));
        cards.add(new SetCardInfo("Vampiric Tutor", 2, Rarity.RARE, mage.cards.v.VampiricTutor.class));
     }
}
