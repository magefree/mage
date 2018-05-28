package mage.sets;

import mage.cards.CardGraphicInfo;
import mage.cards.ExpansionSet;
import mage.cards.FrameStyle;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author magenoxx
 */
public final class Unhinged extends ExpansionSet {

    private static final Unhinged instance = new Unhinged();

    public static Unhinged getInstance() {
        return instance;
    }

    private Unhinged() {
        super("Unhinged", "UNH", ExpansionSet.buildDate(2004, 11, 20), SetType.JOKESET);

        cards.add(new SetCardInfo("Forest", 140, Rarity.LAND, mage.cards.basiclands.Forest.class, new CardGraphicInfo(FrameStyle.UNH_FULL_ART_BASIC, false)));
        cards.add(new SetCardInfo("Island", 137, Rarity.LAND, mage.cards.basiclands.Island.class, new CardGraphicInfo(FrameStyle.UNH_FULL_ART_BASIC, false)));
        cards.add(new SetCardInfo("Johnny, Combo Player", 35, Rarity.RARE, mage.cards.j.JohnnyComboPlayer.class));
        cards.add(new SetCardInfo("Mountain", 139, Rarity.LAND, mage.cards.basiclands.Mountain.class, new CardGraphicInfo(FrameStyle.UNH_FULL_ART_BASIC, false)));
        cards.add(new SetCardInfo("Mox Lotus", 124, Rarity.RARE, mage.cards.m.MoxLotus.class));
        cards.add(new SetCardInfo("Plains", 136, Rarity.LAND, mage.cards.basiclands.Plains.class, new CardGraphicInfo(FrameStyle.UNH_FULL_ART_BASIC, false)));
        cards.add(new SetCardInfo("Swamp", 138, Rarity.LAND, mage.cards.basiclands.Swamp.class, new CardGraphicInfo(FrameStyle.UNH_FULL_ART_BASIC, false)));
    }
}
