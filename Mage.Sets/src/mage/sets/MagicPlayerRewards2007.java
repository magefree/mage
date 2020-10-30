package mage.sets;

import mage.cards.CardGraphicInfo;
import mage.cards.ExpansionSet;
import mage.cards.FrameStyle;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/p07
 */
public class MagicPlayerRewards2007 extends ExpansionSet {

    private static final MagicPlayerRewards2007 instance = new MagicPlayerRewards2007();

    public static MagicPlayerRewards2007 getInstance() {
        return instance;
    }

    private MagicPlayerRewards2007() {
        super("Magic Player Rewards 2007", "P07", ExpansionSet.buildDate(2007, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        final CardGraphicInfo MPR_FULL_ART = new CardGraphicInfo(FrameStyle.MPRP_FULL_ART_BASIC, false);

        cards.add(new SetCardInfo("Condemn", 2, Rarity.RARE, mage.cards.c.Condemn.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Cruel Edict", 5, Rarity.RARE, mage.cards.c.CruelEdict.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Disenchant", 6, Rarity.RARE, mage.cards.d.Disenchant.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Mortify", 3, Rarity.RARE, mage.cards.m.Mortify.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Psionic Blast", 4, Rarity.RARE, mage.cards.p.PsionicBlast.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Recollect", 7, Rarity.RARE, mage.cards.r.Recollect.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Wrath of God", 1, Rarity.RARE, mage.cards.w.WrathOfGod.class, MPR_FULL_ART));
     }
}
