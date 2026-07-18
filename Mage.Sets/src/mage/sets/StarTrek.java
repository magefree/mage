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
        super("Star Trek", "TRK", ExpansionSet.buildDate(2026, 11, 13), SetType.EXPANSION);
        this.blockName = "Star Trek"; // for sorting in GUI
        this.hasBasicLands = false; // TODO: Temporary until spoilers conclude

        // this.enablePlayBooster(276); // TODO: Temporary until spoilers conclude

        cards.add(new SetCardInfo("Amok Time", 183, Rarity.UNCOMMON, mage.cards.a.AmokTime.class));
        cards.add(new SetCardInfo("Beckett Mariner, Impetuous Ensign", 138, Rarity.UNCOMMON, mage.cards.b.BeckettMarinerImpetuousEnsign.class));
        cards.add(new SetCardInfo("Blood Crypt", 278, Rarity.RARE, mage.cards.b.BloodCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blood Crypt", 394, Rarity.RARE, mage.cards.b.BloodCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blood Crypt", 489, Rarity.RARE, mage.cards.b.BloodCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Breeding Pool", 279, Rarity.RARE, mage.cards.b.BreedingPool.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Breeding Pool", 401, Rarity.RARE, mage.cards.b.BreedingPool.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Breeding Pool", 496, Rarity.RARE, mage.cards.b.BreedingPool.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain James T. Kirk", 142, Rarity.MYTHIC, mage.cards.c.CaptainJamesTKirk.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain James T. Kirk", 1701, Rarity.MYTHIC, mage.cards.c.CaptainJamesTKirk.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain James T. Kirk", 418, Rarity.MYTHIC, mage.cards.c.CaptainJamesTKirk.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain James T. Kirk", 542, Rarity.MYTHIC, mage.cards.c.CaptainJamesTKirk.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain Kathryn Janeway", 1704, Rarity.MYTHIC, mage.cards.c.CaptainKathrynJaneway.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain Kathryn Janeway", 187, Rarity.MYTHIC, mage.cards.c.CaptainKathrynJaneway.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain Kathryn Janeway", 340, Rarity.MYTHIC, mage.cards.c.CaptainKathrynJaneway.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain Kathryn Janeway", 386, Rarity.MYTHIC, mage.cards.c.CaptainKathrynJaneway.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain Kathryn Janeway", 520, Rarity.MYTHIC, mage.cards.c.CaptainKathrynJaneway.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Consider the Prime Directive", 51, Rarity.COMMON, mage.cards.c.ConsiderThePrimeDirective.class));
        cards.add(new SetCardInfo("Crystalline Entity", 261, Rarity.MYTHIC, mage.cards.c.CrystallineEntity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crystalline Entity", 478, Rarity.MYTHIC, mage.cards.c.CrystallineEntity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dr. Beverly Crusher", 13, Rarity.MYTHIC, mage.cards.d.DrBeverlyCrusher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dr. Beverly Crusher", 366, Rarity.MYTHIC, mage.cards.d.DrBeverlyCrusher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dr. Beverly Crusher", 500, Rarity.MYTHIC, mage.cards.d.DrBeverlyCrusher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Godless Shrine", 285, Rarity.RARE, mage.cards.g.GodlessShrine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Godless Shrine", 397, Rarity.RARE, mage.cards.g.GodlessShrine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Godless Shrine", 492, Rarity.RARE, mage.cards.g.GodlessShrine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hallowed Fountain", 286, Rarity.RARE, mage.cards.h.HallowedFountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hallowed Fountain", 392, Rarity.RARE, mage.cards.h.HallowedFountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hallowed Fountain", 487, Rarity.RARE, mage.cards.h.HallowedFountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Highly Illogical", 62, Rarity.UNCOMMON, mage.cards.h.HighlyIllogical.class));
        cards.add(new SetCardInfo("In the Pale Moonlight", 117, Rarity.UNCOMMON, mage.cards.i.InThePaleMoonlight.class));
        cards.add(new SetCardInfo("Overgrown Tomb", 289, Rarity.RARE, mage.cards.o.OvergrownTomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overgrown Tomb", 399, Rarity.RARE, mage.cards.o.OvergrownTomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overgrown Tomb", 494, Rarity.RARE, mage.cards.o.OvergrownTomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sacred Foundry", 295, Rarity.RARE, mage.cards.s.SacredFoundry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sacred Foundry", 400, Rarity.RARE, mage.cards.s.SacredFoundry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sacred Foundry", 495, Rarity.RARE, mage.cards.s.SacredFoundry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Set Phasers to...", 34, Rarity.COMMON, mage.cards.s.SetPhasersTo.class));
        cards.add(new SetCardInfo("Steam Vents", 298, Rarity.RARE, mage.cards.s.SteamVents.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Steam Vents", 398, Rarity.RARE, mage.cards.s.SteamVents.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Steam Vents", 493, Rarity.RARE, mage.cards.s.SteamVents.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stomping Ground", 299, Rarity.RARE, mage.cards.s.StompingGround.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stomping Ground", 395, Rarity.RARE, mage.cards.s.StompingGround.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stomping Ground", 490, Rarity.RARE, mage.cards.s.StompingGround.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple Garden", 301, Rarity.RARE, mage.cards.t.TempleGarden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple Garden", 396, Rarity.RARE, mage.cards.t.TempleGarden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple Garden", 491, Rarity.RARE, mage.cards.t.TempleGarden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("U.S.S. Enterprise-D, Galaxy-Class", 273, Rarity.RARE, mage.cards.u.USSEnterpriseDGalaxyClass.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("U.S.S. Enterprise-D, Galaxy-Class", 481, Rarity.RARE, mage.cards.u.USSEnterpriseDGalaxyClass.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("U.S.S. Enterprise-D, Galaxy-Class", 557, Rarity.RARE, mage.cards.u.USSEnterpriseDGalaxyClass.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Watery Grave", 306, Rarity.RARE, mage.cards.w.WateryGrave.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Watery Grave", 393, Rarity.RARE, mage.cards.w.WateryGrave.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Watery Grave", 488, Rarity.RARE, mage.cards.w.WateryGrave.class, NON_FULL_USE_VARIOUS));
    }
}
