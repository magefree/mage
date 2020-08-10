package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/mpr
 */
public class MagicPlayerRewards2001 extends ExpansionSet {

    private static final MagicPlayerRewards2001 instance = new MagicPlayerRewards2001();

    public static MagicPlayerRewards2001 getInstance() {
        return instance;
    }

    private MagicPlayerRewards2001() {
        super("Magic Player Rewards 2001", "MPR", ExpansionSet.buildDate(2001, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Wasteland", 1, Rarity.RARE, mage.cards.w.Wasteland.class));
     }
}
