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
        this.hasBasicLands = true;

        // this.enablePlayBooster(276); // TODO: Temporary until spoilers conclude

        cards.add(new SetCardInfo("A Good Day to Die", 111, Rarity.COMMON, mage.cards.a.AGoodDayToDie.class));
        cards.add(new SetCardInfo("Amok Time", 183, Rarity.UNCOMMON, mage.cards.a.AmokTime.class));
        cards.add(new SetCardInfo("Assault Drone", 92, Rarity.COMMON, mage.cards.a.AssaultDrone.class));
        cards.add(new SetCardInfo("Automated Warfare System", 93, Rarity.COMMON, mage.cards.a.AutomatedWarfareSystem.class));
        cards.add(new SetCardInfo("Bat'leth", 94, Rarity.UNCOMMON, mage.cards.b.Batleth.class));
        cards.add(new SetCardInfo("Battle-Scarred Survivalist", 95, Rarity.COMMON, mage.cards.b.BattleScarredSurvivalist.class));
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
        cards.add(new SetCardInfo("Captain's Tutelage", 144, Rarity.COMMON, mage.cards.c.CaptainsTutelage.class));
        cards.add(new SetCardInfo("Cha'DIch Investigator", 97, Rarity.COMMON, mage.cards.c.ChaDIchInvestigator.class));
        cards.add(new SetCardInfo("Cloistered Telepath", 50, Rarity.COMMON, mage.cards.c.CloisteredTelepath.class));
        cards.add(new SetCardInfo("Cold-Blooded Crew", 189, Rarity.COMMON, mage.cards.c.ColdBloodedCrew.class));
        cards.add(new SetCardInfo("Collective Drone", 99, Rarity.UNCOMMON, mage.cards.c.CollectiveDrone.class));
        cards.add(new SetCardInfo("Command Decision", 8, Rarity.COMMON, mage.cards.c.CommandDecision.class));
        cards.add(new SetCardInfo("Common Goal", 190, Rarity.COMMON, mage.cards.c.CommonGoal.class));
        cards.add(new SetCardInfo("Consider the Prime Directive", 51, Rarity.COMMON, mage.cards.c.ConsiderThePrimeDirective.class));
        cards.add(new SetCardInfo("Crystalline Entity", 261, Rarity.MYTHIC, mage.cards.c.CrystallineEntity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crystalline Entity", 478, Rarity.MYTHIC, mage.cards.c.CrystallineEntity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cybernetic Specialist", 262, Rarity.COMMON, mage.cards.c.CyberneticSpecialist.class));
        cards.add(new SetCardInfo("DOT-7 Repair Squad", 12, Rarity.COMMON, mage.cards.d.DOT7RepairSquad.class));
        cards.add(new SetCardInfo("Dathon and Picard at El-Adrel", 192, Rarity.COMMON, mage.cards.d.DathonAndPicardAtElAdrel.class));
        cards.add(new SetCardInfo("Dominion Supervisor", 195, Rarity.COMMON, mage.cards.d.DominionSupervisor.class));
        cards.add(new SetCardInfo("Dr. Beverly Crusher", 13, Rarity.MYTHIC, mage.cards.d.DrBeverlyCrusher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dr. Beverly Crusher", 366, Rarity.MYTHIC, mage.cards.d.DrBeverlyCrusher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dr. Beverly Crusher", 500, Rarity.MYTHIC, mage.cards.d.DrBeverlyCrusher.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eject the Warp Core", 105, Rarity.COMMON, mage.cards.e.EjectTheWarpCore.class));
        cards.add(new SetCardInfo("Emergency Medical Hologram", 15, Rarity.COMMON, mage.cards.e.EmergencyMedicalHologram.class));
        cards.add(new SetCardInfo("Federation Field Medic", 17, Rarity.COMMON, mage.cards.f.FederationFieldMedic.class));
        cards.add(new SetCardInfo("Federation Probe", 59, Rarity.COMMON, mage.cards.f.FederationProbe.class));
        cards.add(new SetCardInfo("Forest", 325, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 326, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Free Borg Revolutionaries", 19, Rarity.COMMON, mage.cards.f.FreeBorgRevolutionaries.class));
        cards.add(new SetCardInfo("General Chang, Cold Warrior", 109, Rarity.RARE, mage.cards.g.GeneralChangColdWarrior.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("General Chang, Cold Warrior", 415, Rarity.RARE, mage.cards.g.GeneralChangColdWarrior.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("General Chang, Cold Warrior", 427, Rarity.RARE, mage.cards.g.GeneralChangColdWarrior.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("General Chang, Cold Warrior", 437, Rarity.RARE, mage.cards.g.GeneralChangColdWarrior.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("General Chang, Cold Warrior", 539, Rarity.RARE, mage.cards.g.GeneralChangColdWarrior.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Godless Shrine", 285, Rarity.RARE, mage.cards.g.GodlessShrine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Godless Shrine", 397, Rarity.RARE, mage.cards.g.GodlessShrine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Godless Shrine", 492, Rarity.RARE, mage.cards.g.GodlessShrine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Guidance Failure", 60, Rarity.COMMON, mage.cards.g.GuidanceFailure.class));
        cards.add(new SetCardInfo("Hallowed Fountain", 286, Rarity.RARE, mage.cards.h.HallowedFountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hallowed Fountain", 392, Rarity.RARE, mage.cards.h.HallowedFountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hallowed Fountain", 487, Rarity.RARE, mage.cards.h.HallowedFountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("He's Dead, Jim", 114, Rarity.COMMON, mage.cards.h.HesDeadJim.class));
        cards.add(new SetCardInfo("Highly Illogical", 62, Rarity.UNCOMMON, mage.cards.h.HighlyIllogical.class));
        cards.add(new SetCardInfo("Hive Mind Coprocessor", 115, Rarity.UNCOMMON, mage.cards.h.HiveMindCoprocessor.class));
        cards.add(new SetCardInfo("Horta", 152, Rarity.COMMON, mage.cards.h.Horta.class));
        cards.add(new SetCardInfo("Humpback Whales", 65, Rarity.COMMON, mage.cards.h.HumpbackWhales.class));
        cards.add(new SetCardInfo("I'm a Doctor, Not a ...", 203, Rarity.COMMON, mage.cards.i.ImADoctorNotA.class));
        cards.add(new SetCardInfo("In the Pale Moonlight", 117, Rarity.UNCOMMON, mage.cards.i.InThePaleMoonlight.class));
        cards.add(new SetCardInfo("Island", 319, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 320, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Khaaaaaaaaaaaannn!", 154, Rarity.UNCOMMON, mage.cards.k.Khaaaaaaaaaaaannn.class));
        cards.add(new SetCardInfo("La'An Noonien-Singh, Security", 156, Rarity.UNCOMMON, mage.cards.l.LaAnNoonienSinghSecurity.class));
        cards.add(new SetCardInfo("Malfunctioning Holodeck", 22, Rarity.COMMON, mage.cards.m.MalfunctioningHolodeck.class));
        cards.add(new SetCardInfo("Moopsy", 207, Rarity.COMMON, mage.cards.m.Moopsy.class));
        cards.add(new SetCardInfo("Mountain", 323, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 324, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mugato", 208, Rarity.UNCOMMON, mage.cards.m.Mugato.class));
        cards.add(new SetCardInfo("Munitions Enthusiast", 158, Rarity.UNCOMMON, mage.cards.m.MunitionsEnthusiast.class));
        cards.add(new SetCardInfo("Organic Avulsion Unit", 121, Rarity.UNCOMMON, mage.cards.o.OrganicAvulsionUnit.class));
        cards.add(new SetCardInfo("Overgrown Tomb", 289, Rarity.RARE, mage.cards.o.OvergrownTomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overgrown Tomb", 399, Rarity.RARE, mage.cards.o.OvergrownTomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overgrown Tomb", 494, Rarity.RARE, mage.cards.o.OvergrownTomb.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Perils of the Past", 161, Rarity.COMMON, mage.cards.p.PerilsOfThePast.class));
        cards.add(new SetCardInfo("Picard, Leading by Example", 29, Rarity.UNCOMMON, mage.cards.p.PicardLeadingByExample.class));
        cards.add(new SetCardInfo("Plains", 317, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 318, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Planetary Patrol", 163, Rarity.COMMON, mage.cards.p.PlanetaryPatrol.class));
        cards.add(new SetCardInfo("Plasma Cascade", 164, Rarity.COMMON, mage.cards.p.PlasmaCascade.class));
        cards.add(new SetCardInfo("Reckless Impulse", 168, Rarity.COMMON, mage.cards.r.RecklessImpulse.class));
        cards.add(new SetCardInfo("Relentless Drednok", 124, Rarity.COMMON, mage.cards.r.RelentlessDrednok.class));
        cards.add(new SetCardInfo("Resistance Is Futile", 125, Rarity.UNCOMMON, mage.cards.r.ResistanceIsFutile.class));
        cards.add(new SetCardInfo("Rogue Artificial Intelligence", 126, Rarity.COMMON, mage.cards.r.RogueArtificialIntelligence.class));
        cards.add(new SetCardInfo("Sacred Foundry", 295, Rarity.RARE, mage.cards.s.SacredFoundry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sacred Foundry", 400, Rarity.RARE, mage.cards.s.SacredFoundry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sacred Foundry", 495, Rarity.RARE, mage.cards.s.SacredFoundry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Saurian Explorer", 213, Rarity.COMMON, mage.cards.s.SaurianExplorer.class));
        cards.add(new SetCardInfo("Set Phasers to...", 34, Rarity.COMMON, mage.cards.s.SetPhasersTo.class));
        cards.add(new SetCardInfo("Shields Up!", 35, Rarity.COMMON, mage.cards.s.ShieldsUp.class));
        cards.add(new SetCardInfo("Shuttle Ace", 36, Rarity.COMMON, mage.cards.s.ShuttleAce.class));
        cards.add(new SetCardInfo("Silicate Surveyor", 172, Rarity.COMMON, mage.cards.s.SilicateSurveyor.class));
        cards.add(new SetCardInfo("Steam Vents", 298, Rarity.RARE, mage.cards.s.SteamVents.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Steam Vents", 398, Rarity.RARE, mage.cards.s.SteamVents.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Steam Vents", 493, Rarity.RARE, mage.cards.s.SteamVents.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stomping Ground", 299, Rarity.RARE, mage.cards.s.StompingGround.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stomping Ground", 395, Rarity.RARE, mage.cards.s.StompingGround.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stomping Ground", 490, Rarity.RARE, mage.cards.s.StompingGround.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Support Mission", 216, Rarity.UNCOMMON, mage.cards.s.SupportMission.class));
        cards.add(new SetCardInfo("Swamp", 321, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 322, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Syndicate Liquidators", 218, Rarity.UNCOMMON, mage.cards.s.SyndicateLiquidators.class));
        cards.add(new SetCardInfo("Tactical Officer", 38, Rarity.COMMON, mage.cards.t.TacticalOfficer.class));
        cards.add(new SetCardInfo("Temple Garden", 301, Rarity.RARE, mage.cards.t.TempleGarden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple Garden", 396, Rarity.RARE, mage.cards.t.TempleGarden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple Garden", 491, Rarity.RARE, mage.cards.t.TempleGarden.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The City on the Edge of Forever", 228, Rarity.RARE, mage.cards.t.TheCityOnTheEdgeOfForever.class));
        cards.add(new SetCardInfo("U.S.S. Enterprise-D, Galaxy-Class", 273, Rarity.RARE, mage.cards.u.USSEnterpriseDGalaxyClass.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("U.S.S. Enterprise-D, Galaxy-Class", 481, Rarity.RARE, mage.cards.u.USSEnterpriseDGalaxyClass.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("U.S.S. Enterprise-D, Galaxy-Class", 557, Rarity.RARE, mage.cards.u.USSEnterpriseDGalaxyClass.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("V'Ger, the Intruder", 87, Rarity.UNCOMMON, mage.cards.v.VGerTheIntruder.class));
        cards.add(new SetCardInfo("Watery Grave", 306, Rarity.RARE, mage.cards.w.WateryGrave.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Watery Grave", 393, Rarity.RARE, mage.cards.w.WateryGrave.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Watery Grave", 488, Rarity.RARE, mage.cards.w.WateryGrave.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Xenobotanist", 224, Rarity.COMMON, mage.cards.x.Xenobotanist.class));
        cards.add(new SetCardInfo("Xindi Surveyors", 225, Rarity.COMMON, mage.cards.x.XindiSurveyors.class));
    }
}
