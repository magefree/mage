package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.SetType;

import mage.constants.Rarity;
import mage.cards.CardGraphicInfo;
import mage.cards.FrameStyle;

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
        cards.add(new SetCardInfo("Forest", 88, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(FrameStyle.UGL_FULL_ART_BASIC, false)));
        cards.add(new SetCardInfo("Island", 85, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(FrameStyle.UGL_FULL_ART_BASIC, false)));
        cards.add(new SetCardInfo("Mountain", 87, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(FrameStyle.UGL_FULL_ART_BASIC, false)));
        cards.add(new SetCardInfo("Plains", 84, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(FrameStyle.UGL_FULL_ART_BASIC, false)));
        cards.add(new SetCardInfo("Swamp", 86, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(FrameStyle.UGL_FULL_ART_BASIC, false)));
    }
}
