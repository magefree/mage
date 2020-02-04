package mage.sets;

import mage.cards.CardGraphicInfo;
import mage.cards.ExpansionSet;
import mage.cards.FrameStyle;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/p05
 */
public class MagicPlayerRewards2005 extends ExpansionSet {

    private static final MagicPlayerRewards2005 instance = new MagicPlayerRewards2005();

    public static MagicPlayerRewards2005 getInstance() {
        return instance;
    }

    private MagicPlayerRewards2005() {
        super("Magic Player Rewards 2005", "P05", ExpansionSet.buildDate(2005, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        final CardGraphicInfo MPR_FULL_ART = new CardGraphicInfo(FrameStyle.MPRP_FULL_ART_BASIC, false);

        cards.add(new SetCardInfo("Fireball", 3, Rarity.RARE, mage.cards.f.Fireball.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Mana Leak", 5, Rarity.RARE, mage.cards.m.ManaLeak.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Oxidize", 4, Rarity.RARE, mage.cards.o.Oxidize.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Psychatog", 1, Rarity.RARE, mage.cards.p.Psychatog.class));
        cards.add(new SetCardInfo("Reciprocate", 6, Rarity.RARE, mage.cards.r.Reciprocate.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Terror", 2, Rarity.RARE, mage.cards.t.Terror.class, MPR_FULL_ART));
     }
}
