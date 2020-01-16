package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/jgp
 */
public class JudgeGiftCards1998 extends ExpansionSet {

    private static final JudgeGiftCards1998 instance = new JudgeGiftCards1998();

    public static JudgeGiftCards1998 getInstance() {
        return instance;
    }

    private JudgeGiftCards1998() {
        super("Judge Gift Cards 1998", "JGP", ExpansionSet.buildDate(1998, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Gaea's Cradle", 3, Rarity.RARE, mage.cards.g.GaeasCradle.class));
        cards.add(new SetCardInfo("Lightning Bolt", 1, Rarity.RARE, mage.cards.l.LightningBolt.class));
        cards.add(new SetCardInfo("Stroke of Genius", 2, Rarity.RARE, mage.cards.s.StrokeOfGenius.class));
     }
}
