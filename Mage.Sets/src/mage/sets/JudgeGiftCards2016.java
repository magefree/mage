package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/j16
 */
public class JudgeGiftCards2016 extends ExpansionSet {

    private static final JudgeGiftCards2016 instance = new JudgeGiftCards2016();

    public static JudgeGiftCards2016 getInstance() {
        return instance;
    }

    private JudgeGiftCards2016() {
        super("Judge Gift Cards 2016", "J16", ExpansionSet.buildDate(2016, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Azusa, Lost but Seeking", 3, Rarity.RARE, mage.cards.a.AzusaLostButSeeking.class));
        cards.add(new SetCardInfo("Command Beacon", 4, Rarity.RARE, mage.cards.c.CommandBeacon.class));
        cards.add(new SetCardInfo("Defense of the Heart", 7, Rarity.RARE, mage.cards.d.DefenseOfTheHeart.class));
        cards.add(new SetCardInfo("Imperial Seal", 6, Rarity.RARE, mage.cards.i.ImperialSeal.class));
        cards.add(new SetCardInfo("Mana Drain", 2, Rarity.RARE, mage.cards.m.ManaDrain.class));
        cards.add(new SetCardInfo("Mystic Confluence", 5, Rarity.RARE, mage.cards.m.MysticConfluence.class));
        cards.add(new SetCardInfo("Zur the Enchanter", 8, Rarity.RARE, mage.cards.z.ZurTheEnchanter.class));
     }
}
