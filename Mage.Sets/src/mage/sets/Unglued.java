package mage.sets;

import mage.cards.CardGraphicInfo;
import mage.cards.ExpansionSet;
import mage.cards.FrameStyle;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author lopho
 */
public class Unglued extends ExpansionSet {

    private static final Unglued instance = new Unglued();

    public static Unglued getInstance() {
        return instance;
    }

    private Unglued() {
        super("Unglued", "UGL", ExpansionSet.buildDate(1998, 8, 11), SetType.JOKESET);

        cards.add(new SetCardInfo("Chicken Egg", 41, Rarity.COMMON, mage.cards.c.ChickenEgg.class));
        cards.add(new SetCardInfo("Forest", 88, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(FrameStyle.UGL_FULL_ART_BASIC, false)));
        cards.add(new SetCardInfo("Growth Spurt", 61, Rarity.COMMON, mage.cards.g.GrowthSpurt.class));
        cards.add(new SetCardInfo("Island", 85, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(FrameStyle.UGL_FULL_ART_BASIC, false)));
        cards.add(new SetCardInfo("Jack-in-the-Mox", 75, Rarity.RARE, mage.cards.j.JackInTheMox.class));
        cards.add(new SetCardInfo("Jumbo Imp", 34, Rarity.UNCOMMON, mage.cards.j.JumboImp.class));
        cards.add(new SetCardInfo("Krazy Kow", 48, Rarity.COMMON, mage.cards.k.KrazyKow.class));
        cards.add(new SetCardInfo("Mountain", 87, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(FrameStyle.UGL_FULL_ART_BASIC, false)));
        cards.add(new SetCardInfo("Plains", 84, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(FrameStyle.UGL_FULL_ART_BASIC, false)));
        cards.add(new SetCardInfo("Poultrygeist", 37, Rarity.COMMON, mage.cards.p.Poultrygeist.class));
        cards.add(new SetCardInfo("Swamp", 86, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(FrameStyle.UGL_FULL_ART_BASIC, false)));
        cards.add(new SetCardInfo("Timmy, Power Gamer", 68, Rarity.RARE, mage.cards.t.TimmyPowerGamer.class));
    }
}
