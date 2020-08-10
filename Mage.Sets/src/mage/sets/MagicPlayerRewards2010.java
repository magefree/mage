package mage.sets;

import mage.cards.CardGraphicInfo;
import mage.cards.ExpansionSet;
import mage.cards.FrameStyle;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/p10
 */
public class MagicPlayerRewards2010 extends ExpansionSet {

    private static final MagicPlayerRewards2010 instance = new MagicPlayerRewards2010();

    public static MagicPlayerRewards2010 getInstance() {
        return instance;
    }

    private MagicPlayerRewards2010() {
        super("Magic Player Rewards 2010", "P10", ExpansionSet.buildDate(2010, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        final CardGraphicInfo MPR_FULL_ART = new CardGraphicInfo(FrameStyle.MPRP_FULL_ART_BASIC, false);

        cards.add(new SetCardInfo("Bituminous Blast", 7, Rarity.RARE, mage.cards.b.BituminousBlast.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Burst Lightning", 8, Rarity.RARE, mage.cards.b.BurstLightning.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Cancel", 2, Rarity.RARE, mage.cards.c.Cancel.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Celestial Purge", 6, Rarity.RARE, mage.cards.c.CelestialPurge.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Harrow", 9, Rarity.RARE, mage.cards.h.Harrow.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Infest", 4, Rarity.RARE, mage.cards.i.Infest.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Lightning Bolt", 1, Rarity.RARE, mage.cards.l.LightningBolt.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Sign in Blood", 3, Rarity.RARE, mage.cards.s.SignInBlood.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Volcanic Fallout", 5, Rarity.RARE, mage.cards.v.VolcanicFallout.class, MPR_FULL_ART));
     }
}
