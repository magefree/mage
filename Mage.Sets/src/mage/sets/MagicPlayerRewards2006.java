package mage.sets;

import mage.cards.CardGraphicInfo;
import mage.cards.ExpansionSet;
import mage.cards.FrameStyle;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/p06
 */
public class MagicPlayerRewards2006 extends ExpansionSet {

    private static final MagicPlayerRewards2006 instance = new MagicPlayerRewards2006();

    public static MagicPlayerRewards2006 getInstance() {
        return instance;
    }

    private MagicPlayerRewards2006() {
        super("Magic Player Rewards 2006", "P06", ExpansionSet.buildDate(2006, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        final CardGraphicInfo MPR_FULL_ART = new CardGraphicInfo(FrameStyle.MPRP_FULL_ART_BASIC, false);

        cards.add(new SetCardInfo("Giant Growth", 4, Rarity.RARE, mage.cards.g.GiantGrowth.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Hinder", 2, Rarity.RARE, mage.cards.h.Hinder.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Hypnotic Specter", 1, Rarity.RARE, mage.cards.h.HypnoticSpecter.class));
        cards.add(new SetCardInfo("Lightning Helix", 7, Rarity.RARE, mage.cards.l.LightningHelix.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Putrefy", 5, Rarity.RARE, mage.cards.p.Putrefy.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Pyroclasm", 3, Rarity.RARE, mage.cards.p.Pyroclasm.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Zombify", 6, Rarity.RARE, mage.cards.z.Zombify.class, MPR_FULL_ART));
     }
}
