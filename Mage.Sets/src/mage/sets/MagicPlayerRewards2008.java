package mage.sets;

import mage.cards.CardGraphicInfo;
import mage.cards.ExpansionSet;
import mage.cards.FrameStyle;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/p08
 */
public class MagicPlayerRewards2008 extends ExpansionSet {

    private static final MagicPlayerRewards2008 instance = new MagicPlayerRewards2008();

    public static MagicPlayerRewards2008 getInstance() {
        return instance;
    }

    private MagicPlayerRewards2008() {
        super("Magic Player Rewards 2008", "P08", ExpansionSet.buildDate(2008, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        final CardGraphicInfo MPR_FULL_ART = new CardGraphicInfo(FrameStyle.MPRP_FULL_ART_BASIC, false);

        cards.add(new SetCardInfo("Corrupt", 7, Rarity.RARE, mage.cards.c.Corrupt.class));
        cards.add(new SetCardInfo("Damnation", 1, Rarity.RARE, mage.cards.d.Damnation.class));
        cards.add(new SetCardInfo("Harmonize", 5, Rarity.RARE, mage.cards.h.Harmonize.class));
        cards.add(new SetCardInfo("Incinerate", 3, Rarity.RARE, mage.cards.i.Incinerate.class));
        cards.add(new SetCardInfo("Mana Tithe", 4, Rarity.RARE, mage.cards.m.ManaTithe.class));
        cards.add(new SetCardInfo("Ponder", 6, Rarity.RARE, mage.cards.p.Ponder.class));
        cards.add(new SetCardInfo("Tidings", 2, Rarity.RARE, mage.cards.t.Tidings.class));
     }
}
