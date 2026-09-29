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
        super("Unfinity", "UNF", ExpansionSet.buildDate(2022, 4, 1), SetType.SUPPLEMENTAL);
        this.hasBasicLands = true;
        this.hasBoosters = false; // un-set, low implemented cards

        // set contains both legal and joke cards, so must use SetType.SUPPLEMENTAL:
        // https://mtg.fandom.com/wiki/Unfinity
        // The set is the first Un-set to include a mix of eternal-legal cards and acorn cards.

        cards.add(new SetCardInfo("\"Name Sticker\" Goblin", "107m", Rarity.COMMON, mage.cards.n.NameStickerGoblin.class));
        cards.add(new SetCardInfo("Atomwheel Acrobats", 130, Rarity.COMMON, mage.cards.a.AtomwheelAcrobats.class));
        cards.add(new SetCardInfo("Attempted Murder", 352, Rarity.UNCOMMON, mage.cards.a.AttemptedMurder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Attempted Murder", 66, Rarity.UNCOMMON, mage.cards.a.AttemptedMurder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blood Crypt", 279, Rarity.RARE, mage.cards.b.BloodCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blood Crypt", 530, Rarity.RARE, mage.cards.b.BloodCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blorbian Buddy", 131, Rarity.COMMON, mage.cards.b.BlorbianBuddy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blorbian Buddy", 417, Rarity.COMMON, mage.cards.b.BlorbianBuddy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Boing!", 40, Rarity.COMMON, mage.cards.b.Boing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Boing!", 40, Rarity.COMMON, mage.cards.b.Boing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Breeding Pool", 286, Rarity.RARE, mage.cards.b.BreedingPool.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Breeding Pool", 537, Rarity.RARE, mage.cards.b.BreedingPool.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Celebr-8000", 185, Rarity.RARE, mage.cards.c.Celebr8000.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Celebr-8000", 471, Rarity.RARE, mage.cards.c.Celebr8000.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Circuits Act", 103, Rarity.COMMON, mage.cards.c.CircuitsAct.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Circuits Act", 389, Rarity.COMMON, mage.cards.c.CircuitsAct.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Clown Car", 186, Rarity.RARE, mage.cards.c.ClownCar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Clown Car", 472, Rarity.RARE, mage.cards.c.ClownCar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Clowning Around", 292, Rarity.COMMON, mage.cards.c.ClowningAround.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Clowning Around", 6, Rarity.COMMON, mage.cards.c.ClowningAround.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Comet, Stellar Pup", 166, Rarity.MYTHIC, mage.cards.c.CometStellarPup.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Comet, Stellar Pup", 275, Rarity.MYTHIC, mage.cards.c.CometStellarPup.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Comet, Stellar Pup", 452, Rarity.MYTHIC, mage.cards.c.CometStellarPup.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Comet, Stellar Pup", 526, Rarity.MYTHIC, mage.cards.c.CometStellarPup.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dissatisfied Customer", 358, Rarity.COMMON, mage.cards.d.DissatisfiedCustomer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dissatisfied Customer", 72, Rarity.COMMON, mage.cards.d.DissatisfiedCustomer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Embiggen", 137, Rarity.COMMON, mage.cards.e.Embiggen.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Embiggen", 423, Rarity.COMMON, mage.cards.e.Embiggen.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 239, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_UST_VARIOUS));
        cards.add(new SetCardInfo("Forest", 244, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_UST_VARIOUS));
        cards.add(new SetCardInfo("Forest", 490, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_UST_VARIOUS));
        cards.add(new SetCardInfo("Forest", 495, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_UST_VARIOUS));
        cards.add(new SetCardInfo("Godless Shrine", 282, Rarity.RARE, mage.cards.g.GodlessShrine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Godless Shrine", 533, Rarity.RARE, mage.cards.g.GodlessShrine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hallowed Fountain", 277, Rarity.RARE, mage.cards.h.HallowedFountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hallowed Fountain", 528, Rarity.RARE, mage.cards.h.HallowedFountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 236, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_UST_VARIOUS));
        cards.add(new SetCardInfo("Island", 241, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_UST_VARIOUS));
        cards.add(new SetCardInfo("Island", 487, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_UST_VARIOUS));
        cards.add(new SetCardInfo("Island", 492, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_UST_VARIOUS));
        cards.add(new SetCardInfo("Lila, Hospitality Hostess", 170, Rarity.MYTHIC, mage.cards.l.LilaHospitalityHostess.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lila, Hospitality Hostess", 262, Rarity.MYTHIC, mage.cards.l.LilaHospitalityHostess.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lila, Hospitality Hostess", 456, Rarity.MYTHIC, mage.cards.l.LilaHospitalityHostess.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lila, Hospitality Hostess", 513, Rarity.MYTHIC, mage.cards.l.LilaHospitalityHostess.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Monoxa, Midway Manager", 173, Rarity.UNCOMMON, mage.cards.m.MonoxaMidwayManager.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Monoxa, Midway Manager", 265, Rarity.UNCOMMON, mage.cards.m.MonoxaMidwayManager.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Monoxa, Midway Manager", 459, Rarity.UNCOMMON, mage.cards.m.MonoxaMidwayManager.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Monoxa, Midway Manager", 516, Rarity.UNCOMMON, mage.cards.m.MonoxaMidwayManager.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 238, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_UST_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 243, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_UST_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 489, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_UST_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 494, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_UST_VARIOUS));
        cards.add(new SetCardInfo("Nearby Planet", 198, Rarity.COMMON, mage.cards.n.NearbyPlanet.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nearby Planet", 484, Rarity.COMMON, mage.cards.n.NearbyPlanet.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Non-Human Cannonball", 115, Rarity.COMMON, mage.cards.n.NonHumanCannonball.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Non-Human Cannonball", 401, Rarity.COMMON, mage.cards.n.NonHumanCannonball.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("One-Clown Band", 117, Rarity.COMMON, mage.cards.o.OneClownBand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("One-Clown Band", 403, Rarity.COMMON, mage.cards.o.OneClownBand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overgrown Tomb", 284, Rarity.RARE, mage.cards.o.OvergrownTomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overgrown Tomb", 535, Rarity.RARE, mage.cards.o.OvergrownTomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pair o' Dice Lost", 149, Rarity.UNCOMMON, mage.cards.p.PairODiceLost.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pair o' Dice Lost", 149, Rarity.UNCOMMON, mage.cards.p.PairODiceLost.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 235, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_UST_VARIOUS));
        cards.add(new SetCardInfo("Plains", 240, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_UST_VARIOUS));
        cards.add(new SetCardInfo("Plains", 486, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_UST_VARIOUS));
        cards.add(new SetCardInfo("Plains", 491, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_UST_VARIOUS));
        cards.add(new SetCardInfo("Priority Boarding", 119, Rarity.UNCOMMON, mage.cards.p.PriorityBoarding.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Priority Boarding", 405, Rarity.UNCOMMON, mage.cards.p.PriorityBoarding.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sacred Foundry", 285, Rarity.RARE, mage.cards.s.SacredFoundry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sacred Foundry", 536, Rarity.RARE, mage.cards.s.SacredFoundry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Saw in Half", 374, Rarity.RARE, mage.cards.s.SawInHalf.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Saw in Half", 88, Rarity.RARE, mage.cards.s.SawInHalf.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Six-Sided Die", 378, Rarity.COMMON, mage.cards.s.SixSidedDie.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Six-Sided Die", 92, Rarity.COMMON, mage.cards.s.SixSidedDie.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Slight Malfunction", 123, Rarity.COMMON, mage.cards.s.SlightMalfunction.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Slight Malfunction", 409, Rarity.COMMON, mage.cards.s.SlightMalfunction.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Starlight Spectacular", 28, Rarity.RARE, mage.cards.s.StarlightSpectacular.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Starlight Spectacular", 314, Rarity.RARE, mage.cards.s.StarlightSpectacular.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Steam Vents", 283, Rarity.RARE, mage.cards.s.SteamVents.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Steam Vents", 534, Rarity.RARE, mage.cards.s.SteamVents.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stomping Ground", 280, Rarity.RARE, mage.cards.s.StompingGround.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stomping Ground", 531, Rarity.RARE, mage.cards.s.StompingGround.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Strength-Testing Hammer", 193, Rarity.UNCOMMON, mage.cards.s.StrengthTestingHammer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Strength-Testing Hammer", 479, Rarity.UNCOMMON, mage.cards.s.StrengthTestingHammer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 237, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_UST_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 242, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_UST_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 488, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_UST_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 493, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_UST_VARIOUS));
        cards.add(new SetCardInfo("Temple Garden", 281, Rarity.RARE, mage.cards.t.TempleGarden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple Garden", 532, Rarity.RARE, mage.cards.t.TempleGarden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Space Family Goblinson", 179, Rarity.UNCOMMON, mage.cards.t.TheSpaceFamilyGoblinson.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Space Family Goblinson", 270, Rarity.UNCOMMON, mage.cards.t.TheSpaceFamilyGoblinson.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Space Family Goblinson", 465, Rarity.UNCOMMON, mage.cards.t.TheSpaceFamilyGoblinson.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Space Family Goblinson", 521, Rarity.UNCOMMON, mage.cards.t.TheSpaceFamilyGoblinson.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ticket Turbotubes", 194, Rarity.COMMON, mage.cards.t.TicketTurbotubes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ticket Turbotubes", 480, Rarity.COMMON, mage.cards.t.TicketTurbotubes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vegetation Abomination", 160, Rarity.COMMON, mage.cards.v.VegetationAbomination.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vegetation Abomination", 446, Rarity.COMMON, mage.cards.v.VegetationAbomination.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Watery Grave", 278, Rarity.RARE, mage.cards.w.WateryGrave.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Watery Grave", 529, Rarity.RARE, mage.cards.w.WateryGrave.class, NON_FULL_USE_VARIOUS));
    }
}
