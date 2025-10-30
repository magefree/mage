package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class TeenageMutantNinjaTurtles extends ExpansionSet {

    private static final TeenageMutantNinjaTurtles instance = new TeenageMutantNinjaTurtles();

    public static TeenageMutantNinjaTurtles getInstance() {
        return instance;
    }

    private TeenageMutantNinjaTurtles() {
        super("Teenage Mutant Ninja Turtles", "TMT", ExpansionSet.buildDate(2026, 3, 6), SetType.EXPANSION);
        this.blockName = "Teenage Mutant Ninja Turtles"; // for sorting in GUI
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("April O'Neil, Hacktivist", 29, Rarity.RARE, mage.cards.a.AprilONeilHacktivist.class));
        cards.add(new SetCardInfo("Bebop & Rocksteady", 140, Rarity.RARE, mage.cards.b.BebopAndRocksteady.class));
        cards.add(new SetCardInfo("Casey Jones, Jury-Rig Justiciar", 87, Rarity.UNCOMMON, mage.cards.c.CaseyJonesJuryRigJusticiar.class));
        cards.add(new SetCardInfo("Forest", 257, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 314, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 254, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 311, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Krang, Master Mind", 43, Rarity.RARE, mage.cards.k.KrangMasterMind.class));
        cards.add(new SetCardInfo("Mountain", 256, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 313, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 253, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 310, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Raphael's Technique", 105, Rarity.RARE, mage.cards.r.RaphaelsTechnique.class));
        cards.add(new SetCardInfo("Super Shredder", 83, Rarity.MYTHIC, mage.cards.s.SuperShredder.class));
        cards.add(new SetCardInfo("Swamp", 255, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 312, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
    }
}
