package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */
public final class StarTrek extends ExpansionSet {

    private static final StarTrek instance = new StarTrek();

    public static StarTrek getInstance() {
        return instance;
    }

    private StarTrek() {
        super("Star Trek", "TRK", ExpansionSet.buildDate(2026, 11, 20), SetType.EXPANSION);
        this.blockName = "Star Trek"; // for sorting in GUI
        this.hasBasicLands = false; // TODO: Temporary until spoilers conclude

        // this.enablePlayBooster(276); // TODO: Temporary until spoilers conclude

        cards.add(new SetCardInfo("Blood Crypt", 278, Rarity.RARE, mage.cards.b.BloodCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blood Crypt", 394, Rarity.RARE, mage.cards.b.BloodCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blood Crypt", 489, Rarity.RARE, mage.cards.b.BloodCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Breeding Pool", 279, Rarity.RARE, mage.cards.b.BreedingPool.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Breeding Pool", 401, Rarity.RARE, mage.cards.b.BreedingPool.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Breeding Pool", 496, Rarity.RARE, mage.cards.b.BreedingPool.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Godless Shrine", 285, Rarity.RARE, mage.cards.g.GodlessShrine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Godless Shrine", 397, Rarity.RARE, mage.cards.g.GodlessShrine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Godless Shrine", 492, Rarity.RARE, mage.cards.g.GodlessShrine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hallowed Fountain", 286, Rarity.RARE, mage.cards.h.HallowedFountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hallowed Fountain", 392, Rarity.RARE, mage.cards.h.HallowedFountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hallowed Fountain", 487, Rarity.RARE, mage.cards.h.HallowedFountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Highly Illogical", 62, Rarity.UNCOMMON, mage.cards.h.HighlyIllogical.class));
        cards.add(new SetCardInfo("Overgrown Tomb", 289, Rarity.RARE, mage.cards.o.OvergrownTomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overgrown Tomb", 399, Rarity.RARE, mage.cards.o.OvergrownTomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overgrown Tomb", 494, Rarity.RARE, mage.cards.o.OvergrownTomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sacred Foundry", 295, Rarity.RARE, mage.cards.s.SacredFoundry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sacred Foundry", 400, Rarity.RARE, mage.cards.s.SacredFoundry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sacred Foundry", 495, Rarity.RARE, mage.cards.s.SacredFoundry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Steam Vents", 298, Rarity.RARE, mage.cards.s.SteamVents.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Steam Vents", 398, Rarity.RARE, mage.cards.s.SteamVents.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Steam Vents", 493, Rarity.RARE, mage.cards.s.SteamVents.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stomping Ground", 299, Rarity.RARE, mage.cards.s.StompingGround.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stomping Ground", 395, Rarity.RARE, mage.cards.s.StompingGround.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stomping Ground", 490, Rarity.RARE, mage.cards.s.StompingGround.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple Garden", 301, Rarity.RARE, mage.cards.t.TempleGarden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple Garden", 396, Rarity.RARE, mage.cards.t.TempleGarden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple Garden", 491, Rarity.RARE, mage.cards.t.TempleGarden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Watery Grave", 306, Rarity.RARE, mage.cards.w.WateryGrave.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Watery Grave", 393, Rarity.RARE, mage.cards.w.WateryGrave.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Watery Grave", 488, Rarity.RARE, mage.cards.w.WateryGrave.class, NON_FULL_USE_VARIOUS));
    }
}
