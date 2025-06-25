package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class EdgeOfEternities extends ExpansionSet {

    private static final EdgeOfEternities instance = new EdgeOfEternities();

    public static EdgeOfEternities getInstance() {
        return instance;
    }

    private EdgeOfEternities() {
        super("Edge of Eternities", "EOE", ExpansionSet.buildDate(2025, 8, 1), SetType.EXPANSION);
        this.blockName = "Edge of Eternities"; // for sorting in GUI
        this.rotationSet = true;
        this.hasBasicLands = false; // temporary

        cards.add(new SetCardInfo("Alpharael, Dreaming Acolyte", 212, Rarity.UNCOMMON, mage.cards.a.AlpharaelDreamingAcolyte.class));
        cards.add(new SetCardInfo("Breeding Pool", 251, Rarity.RARE, mage.cards.b.BreedingPool.class));
        cards.add(new SetCardInfo("Forest", 266, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 371, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Godless Shrine", 254, Rarity.RARE, mage.cards.g.GodlessShrine.class));
        cards.add(new SetCardInfo("Harmonious Grovestrider", 189, Rarity.UNCOMMON, mage.cards.h.HarmoniousGrovestrider.class));
        cards.add(new SetCardInfo("Island", 263, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 368, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 265, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 370, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 262, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 367, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Sacred Foundry", 256, Rarity.RARE, mage.cards.s.SacredFoundry.class));
        cards.add(new SetCardInfo("Sami, Ship's Engineer", 225, Rarity.UNCOMMON, mage.cards.s.SamiShipsEngineer.class));
        cards.add(new SetCardInfo("Sothera, the Supervoid", 115, Rarity.MYTHIC, mage.cards.s.SotheraTheSupervoid.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sothera, the Supervoid", 360, Rarity.MYTHIC, mage.cards.s.SotheraTheSupervoid.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sothera, the Supervoid", 382, Rarity.MYTHIC, mage.cards.s.SotheraTheSupervoid.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sothera, the Supervoid", 386, Rarity.MYTHIC, mage.cards.s.SotheraTheSupervoid.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stomping Ground", 258, Rarity.RARE, mage.cards.s.StompingGround.class));
        cards.add(new SetCardInfo("Swamp", 264, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 369, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Tannuk, Memorial Ensign", 233, Rarity.UNCOMMON, mage.cards.t.TannukMemorialEnsign.class));
        cards.add(new SetCardInfo("Tezzeret, Cruel Captain", 2, Rarity.MYTHIC, mage.cards.t.TezzeretCruelCaptain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tezzeret, Cruel Captain", 287, Rarity.MYTHIC, mage.cards.t.TezzeretCruelCaptain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Watery Grave", 261, Rarity.RARE, mage.cards.w.WateryGrave.class));
    }
}
