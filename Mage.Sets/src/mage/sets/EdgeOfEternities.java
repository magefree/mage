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

        cards.add(new SetCardInfo("Breeding Pool", 251, Rarity.RARE, mage.cards.b.BreedingPool.class));
        cards.add(new SetCardInfo("Godless Shrine", 254, Rarity.RARE, mage.cards.g.GodlessShrine.class));
        cards.add(new SetCardInfo("Sacred Foundry", 256, Rarity.RARE, mage.cards.s.SacredFoundry.class));
        cards.add(new SetCardInfo("Stomping Ground", 258, Rarity.RARE, mage.cards.s.StompingGround.class));
        cards.add(new SetCardInfo("Tezzeret, Cruel Captain", 2, Rarity.MYTHIC, mage.cards.t.TezzeretCruelCaptain.class));
        cards.add(new SetCardInfo("Watery Grave", 261, Rarity.RARE, mage.cards.w.WateryGrave.class));
    }
}
