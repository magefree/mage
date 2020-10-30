package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/p04
 */
public class MagicPlayerRewards2004 extends ExpansionSet {

    private static final MagicPlayerRewards2004 instance = new MagicPlayerRewards2004();

    public static MagicPlayerRewards2004 getInstance() {
        return instance;
    }

    private MagicPlayerRewards2004() {
        super("Magic Player Rewards 2004", "P04", ExpansionSet.buildDate(2004, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Powder Keg", 1, Rarity.RARE, mage.cards.p.PowderKeg.class));
     }
}
