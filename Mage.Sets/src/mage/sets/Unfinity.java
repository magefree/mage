package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Unfinity extends ExpansionSet {

    private static final Unfinity instance = new Unfinity();

    public static Unfinity getInstance() {
        return instance;
    }

    private Unfinity() {
        super("Unfinity", "UNF", ExpansionSet.buildDate(2022, 4, 1), SetType.JOKESET); // TODO: some of these cards are legacy legal
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Blood Crypt", 279, Rarity.COMMON, mage.cards.b.BloodCrypt.class));
        cards.add(new SetCardInfo("Breeding Pool", 286, Rarity.RARE, mage.cards.b.BreedingPool.class));
        cards.add(new SetCardInfo("Forest", 239, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Godless Shrine", 282, Rarity.RARE, mage.cards.g.GodlessShrine.class));
        cards.add(new SetCardInfo("Hallowed Fountain", 277, Rarity.RARE, mage.cards.h.HallowedFountain.class));
        cards.add(new SetCardInfo("Island", 236, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 238, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Overgrown Tomb", 284, Rarity.RARE, mage.cards.o.OvergrownTomb.class));
        cards.add(new SetCardInfo("Plains", 240, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Sacred Foundry", 285, Rarity.RARE, mage.cards.s.SacredFoundry.class));
        cards.add(new SetCardInfo("Saw in Half", 88, Rarity.RARE, mage.cards.s.SawInHalf.class));
        cards.add(new SetCardInfo("Steam Vents", 283, Rarity.RARE, mage.cards.s.SteamVents.class));
        cards.add(new SetCardInfo("Stomping Ground", 280, Rarity.RARE, mage.cards.s.StompingGround.class));
        cards.add(new SetCardInfo("Swamp", 237, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Temple Garden", 281, Rarity.RARE, mage.cards.t.TempleGarden.class));
        cards.add(new SetCardInfo("The Space Family Goblinson", 179, Rarity.UNCOMMON, mage.cards.t.TheSpaceFamilyGoblinson.class));
        cards.add(new SetCardInfo("Watery Grave", 278, Rarity.RARE, mage.cards.w.WateryGrave.class));
    }
}
