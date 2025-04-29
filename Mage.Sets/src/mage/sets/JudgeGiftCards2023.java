package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/p23
 */
public class JudgeGiftCards2023 extends ExpansionSet {

    private static final JudgeGiftCards2023 instance = new JudgeGiftCards2023();

    public static JudgeGiftCards2023 getInstance() {
        return instance;
    }

    private JudgeGiftCards2023() {
        super("Judge Gift Cards 2023", "P23", ExpansionSet.buildDate(2023, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;

        cards.add(new SetCardInfo("Forest", 10, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Grindstone", 2, Rarity.RARE, mage.cards.g.Grindstone.class, RETRO_ART));
        cards.add(new SetCardInfo("Island", 7, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 9, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mycosynth Lattice", 3, Rarity.RARE, mage.cards.m.MycosynthLattice.class, RETRO_ART));
        cards.add(new SetCardInfo("Painter's Servant", 1, Rarity.RARE, mage.cards.p.PaintersServant.class, RETRO_ART));
        cards.add(new SetCardInfo("Plains", 6, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Retrofitter Foundry", 4, Rarity.RARE, mage.cards.r.RetrofitterFoundry.class, RETRO_ART));
        cards.add(new SetCardInfo("Swamp", 8, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Sword of War and Peace", 5, Rarity.MYTHIC, mage.cards.s.SwordOfWarAndPeace.class, RETRO_ART));
    }
}
