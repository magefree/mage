package mage.sets;

import mage.cards.CardGraphicInfo;
import mage.cards.ExpansionSet;
import mage.cards.FrameStyle;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/p09
 */
public class MagicPlayerRewards2009 extends ExpansionSet {

    private static final MagicPlayerRewards2009 instance = new MagicPlayerRewards2009();

    public static MagicPlayerRewards2009 getInstance() {
        return instance;
    }

    private MagicPlayerRewards2009() {
        super("Magic Player Rewards 2009", "P09", ExpansionSet.buildDate(2009, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        final CardGraphicInfo MPR_FULL_ART = new CardGraphicInfo(FrameStyle.MPRP_FULL_ART_BASIC, false);

        cards.add(new SetCardInfo("Blightning", 6, Rarity.RARE, mage.cards.b.Blightning.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Cryptic Command", 1, Rarity.RARE, mage.cards.c.CrypticCommand.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Flame Javelin", 2, Rarity.RARE, mage.cards.f.FlameJavelin.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Nameless Inversion", 4, Rarity.RARE, mage.cards.n.NamelessInversion.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Negate", 8, Rarity.RARE, mage.cards.n.Negate.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Rampant Growth", 7, Rarity.RARE, mage.cards.r.RampantGrowth.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Remove Soul", 5, Rarity.RARE, mage.cards.r.RemoveSoul.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Terminate", 9, Rarity.RARE, mage.cards.t.Terminate.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Unmake", 3, Rarity.RARE, mage.cards.u.Unmake.class, MPR_FULL_ART));
     }
}
