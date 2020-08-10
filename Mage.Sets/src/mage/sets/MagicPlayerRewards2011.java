package mage.sets;

import mage.cards.CardGraphicInfo;
import mage.cards.ExpansionSet;
import mage.cards.FrameStyle;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/p11
 */
public class MagicPlayerRewards2011 extends ExpansionSet {

    private static final MagicPlayerRewards2011 instance = new MagicPlayerRewards2011();

    public static MagicPlayerRewards2011 getInstance() {
        return instance;
    }

    private MagicPlayerRewards2011() {
        super("Magic Player Rewards 2011", "P11", ExpansionSet.buildDate(2011, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        final CardGraphicInfo MPR_FULL_ART = new CardGraphicInfo(FrameStyle.MPRP_FULL_ART_BASIC, false);

        cards.add(new SetCardInfo("Brave the Elements", 2, Rarity.RARE, mage.cards.b.BraveTheElements.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Day of Judgment", 1, Rarity.RARE, mage.cards.d.DayOfJudgment.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Doom Blade", 3, Rarity.RARE, mage.cards.d.DoomBlade.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Searing Blaze", 5, Rarity.RARE, mage.cards.s.SearingBlaze.class, MPR_FULL_ART));
        cards.add(new SetCardInfo("Treasure Hunt", 4, Rarity.RARE, mage.cards.t.TreasureHunt.class, MPR_FULL_ART));
     }
}
