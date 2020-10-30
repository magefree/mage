package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/p03
 */
public class MagicPlayerRewards2003 extends ExpansionSet {

    private static final MagicPlayerRewards2003 instance = new MagicPlayerRewards2003();

    public static MagicPlayerRewards2003 getInstance() {
        return instance;
    }

    private MagicPlayerRewards2003() {
        super("Magic Player Rewards 2003", "P03", ExpansionSet.buildDate(2003, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Voidmage Prodigy", 1, Rarity.RARE, mage.cards.v.VoidmageProdigy.class));
     }
}
