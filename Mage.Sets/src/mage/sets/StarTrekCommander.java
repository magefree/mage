package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */
public final class StarTrekCommander extends ExpansionSet {

    private static final StarTrekCommander instance = new StarTrekCommander();

    public static StarTrekCommander getInstance() {
        return instance;
    }

    private StarTrekCommander() {
        super("Star Trek Commander", "TRC", ExpansionSet.buildDate(2026, 11, 13), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Academy Ruins", 512, Rarity.RARE, mage.cards.a.AcademyRuins.class));
        cards.add(new SetCardInfo("Ambulatory Reactor", 35, Rarity.RARE, mage.cards.a.AmbulatoryReactor.class));
        cards.add(new SetCardInfo("Anguished Unmaking", 472, Rarity.RARE, mage.cards.a.AnguishedUnmaking.class));
        cards.add(new SetCardInfo("Arcane Sanctum", 513, Rarity.UNCOMMON, mage.cards.a.ArcaneSanctum.class));
        cards.add(new SetCardInfo("Arcane Signet", 485, Rarity.UNCOMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Astral Cornucopia", 486, Rarity.RARE, mage.cards.a.AstralCornucopia.class));
        cards.add(new SetCardInfo("Badgey, Malicious Glitch", 152, Rarity.UNCOMMON, mage.cards.b.BadgeyMaliciousGlitch.class));
        cards.add(new SetCardInfo("Battle of Wolf 359", 57, Rarity.RARE, mage.cards.b.BattleOfWolf359.class));
        cards.add(new SetCardInfo("Benjamin Sisko, Besieged", 200, Rarity.MYTHIC, mage.cards.b.BenjaminSiskoBesieged.class));
        cards.add(new SetCardInfo("Bio-Asset Allocator", 196, Rarity.RARE, mage.cards.b.BioAssetAllocator.class));
        cards.add(new SetCardInfo("Bitter Triumph", 445, Rarity.UNCOMMON, mage.cards.b.BitterTriumph.class));
        cards.add(new SetCardInfo("Borg Queen, One Who Is Many", 131, Rarity.RARE, mage.cards.b.BorgQueenOneWhoIsMany.class));
        cards.add(new SetCardInfo("Borg Scout Sphere", 36, Rarity.RARE, mage.cards.b.BorgScoutSphere.class));
        cards.add(new SetCardInfo("Captain Kirk, Boldly Going", 85, Rarity.MYTHIC, mage.cards.c.CaptainKirkBoldlyGoing.class));
        cards.add(new SetCardInfo("Ceti Eel", 153, Rarity.COMMON, mage.cards.c.CetiEel.class));
        cards.add(new SetCardInfo("Choked Estuary", 517, Rarity.RARE, mage.cards.c.ChokedEstuary.class));
        cards.add(new SetCardInfo("Christine Chapel, Combat Medic", 300, Rarity.RARE, mage.cards.c.ChristineChapelCombatMedic.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Christine Chapel, Combat Medic", 78, Rarity.RARE, mage.cards.c.ChristineChapelCombatMedic.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Class-4 Tactical Cube", 110, Rarity.RARE, mage.cards.c.Class4TacticalCube.class));
        cards.add(new SetCardInfo("Collective Override", 90, Rarity.RARE, mage.cards.c.CollectiveOverride.class));
        cards.add(new SetCardInfo("Co-Option Adjutant", 39, Rarity.RARE, mage.cards.c.CoOptionAdjutant.class));
        cards.add(new SetCardInfo("Command Chair", 111, Rarity.RARE, mage.cards.c.CommandChair.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Command Chair", 340, Rarity.RARE, mage.cards.c.CommandChair.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Command Tower", 521, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Commander's Sphere", 487, Rarity.COMMON, mage.cards.c.CommandersSphere.class));
        cards.add(new SetCardInfo("Contaminated Landscape", 522, Rarity.COMMON, mage.cards.c.ContaminatedLandscape.class));
        cards.add(new SetCardInfo("Cryogenic Stasis", 142, Rarity.COMMON, mage.cards.c.CryogenicStasis.class));
        cards.add(new SetCardInfo("Darksteel Plate", 488, Rarity.RARE, mage.cards.d.DarksteelPlate.class));
        cards.add(new SetCardInfo("Defense Force Aggressor", 161, Rarity.COMMON, mage.cards.d.DefenseForceAggressor.class));
        cards.add(new SetCardInfo("Direct Hit", 162, Rarity.COMMON, mage.cards.d.DirectHit.class));
        cards.add(new SetCardInfo("Disallow", 436, Rarity.RARE, mage.cards.d.Disallow.class));
        cards.add(new SetCardInfo("Dispatch", 421, Rarity.UNCOMMON, mage.cards.d.Dispatch.class));
        cards.add(new SetCardInfo("Disruptor Pistol", 177, Rarity.COMMON, mage.cards.d.DisruptorPistol.class));
        cards.add(new SetCardInfo("Door of Destinies", 489, Rarity.RARE, mage.cards.d.DoorOfDestinies.class));
        cards.add(new SetCardInfo("Drowned Catacomb", 525, Rarity.RARE, mage.cards.d.DrownedCatacomb.class));
        cards.add(new SetCardInfo("Eclipsed Steppe", 526, Rarity.RARE, mage.cards.e.EclipsedSteppe.class));
        cards.add(new SetCardInfo("Evasive Maneuvers", 132, Rarity.COMMON, mage.cards.e.EvasiveManeuvers.class));
        cards.add(new SetCardInfo("Everflowing Chalice", 490, Rarity.COMMON, mage.cards.e.EverflowingChalice.class));
        cards.add(new SetCardInfo("Evolving Wilds", 527, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Exocomp", 145, Rarity.COMMON, mage.cards.e.Exocomp.class));
        cards.add(new SetCardInfo("Exotic Orchard", 528, Rarity.RARE, mage.cards.e.ExoticOrchard.class));
        cards.add(new SetCardInfo("Fetid Pools", 530, Rarity.RARE, mage.cards.f.FetidPools.class));
        cards.add(new SetCardInfo("Forsaken Monument", 491, Rarity.MYTHIC, mage.cards.f.ForsakenMonument.class));
        cards.add(new SetCardInfo("Gin'tak Charge", 163, Rarity.COMMON, mage.cards.g.GintakCharge.class));
        cards.add(new SetCardInfo("Glacial Fortress", 535, Rarity.RARE, mage.cards.g.GlacialFortress.class));
        cards.add(new SetCardInfo("Gorn Captain", 172, Rarity.COMMON, mage.cards.g.GornCaptain.class));
        cards.add(new SetCardInfo("Guinan, Ancient Advisor", 17, Rarity.RARE, mage.cards.g.GuinanAncientAdvisor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Guinan, Ancient Advisor", 223, Rarity.RARE, mage.cards.g.GuinanAncientAdvisor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gumato", 173, Rarity.UNCOMMON, mage.cards.g.Gumato.class));
        cards.add(new SetCardInfo("Head of Security", 133, Rarity.COMMON, mage.cards.h.HeadOfSecurity.class));
        cards.add(new SetCardInfo("Irrigated Farmland", 538, Rarity.RARE, mage.cards.i.IrrigatedFarmland.class));
        cards.add(new SetCardInfo("Isolated Chapel", 539, Rarity.RARE, mage.cards.i.IsolatedChapel.class));
        cards.add(new SetCardInfo("Jean-Luc Picard, Luminary", 3, Rarity.MYTHIC, mage.cards.j.JeanLucPicardLuminary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jean-Luc Picard, Luminary", 7, Rarity.MYTHIC, mage.cards.j.JeanLucPicardLuminary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Join the Collective", 20, Rarity.RARE, mage.cards.j.JoinTheCollective.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Join the Collective", 226, Rarity.RARE, mage.cards.j.JoinTheCollective.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kirk, Enterprising Captain", 198, Rarity.MYTHIC, mage.cards.k.KirkEnterprisingCaptain.class));
        cards.add(new SetCardInfo("Klingon Strike Force", 199, Rarity.RARE, mage.cards.k.KlingonStrikeForce.class));
        cards.add(new SetCardInfo("Kolinahr Priest", 146, Rarity.COMMON, mage.cards.k.KolinahrPriest.class));
        cards.add(new SetCardInfo("Koloth, Tribble Hunter", 289, Rarity.RARE, mage.cards.k.KolothTribbleHunter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Koloth, Tribble Hunter", 72, Rarity.RARE, mage.cards.k.KolothTribbleHunter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kruge, Genesis Seeker", 165, Rarity.UNCOMMON, mage.cards.k.KrugeGenesisSeeker.class));
        cards.add(new SetCardInfo("La Forge, Perceptive Engineer", 147, Rarity.UNCOMMON, mage.cards.l.LaForgePerceptiveEngineer.class));
        cards.add(new SetCardInfo("Last Gasp", 580, Rarity.COMMON, mage.cards.l.LastGasp.class));
        cards.add(new SetCardInfo("Locutus of Borg", 209, Rarity.MYTHIC, mage.cards.l.LocutusOfBorg.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Locutus of Borg", 97, Rarity.MYTHIC, mage.cards.l.LocutusOfBorg.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mek'leth Berserker", 166, Rarity.COMMON, mage.cards.m.MeklethBerserker.class));
        cards.add(new SetCardInfo("Morphic Pool", 540, Rarity.RARE, mage.cards.m.MorphicPool.class));
        cards.add(new SetCardInfo("Operations Officer", 192, Rarity.RARE, mage.cards.o.OperationsOfficer.class));
        cards.add(new SetCardInfo("Path of Ancestry", 545, Rarity.COMMON, mage.cards.p.PathOfAncestry.class));
        cards.add(new SetCardInfo("Pelia, Immortal Innovator", 148, Rarity.UNCOMMON, mage.cards.p.PeliaImmortalInnovator.class));
        cards.add(new SetCardInfo("Picard, Steadfast Captain", 193, Rarity.MYTHIC, mage.cards.p.PicardSteadfastCaptain.class));
        cards.add(new SetCardInfo("Port Town", 547, Rarity.RARE, mage.cards.p.PortTown.class));
        cards.add(new SetCardInfo("Prairie Stream", 548, Rarity.RARE, mage.cards.p.PrairieStream.class));
        cards.add(new SetCardInfo("Refute", 579, Rarity.COMMON, mage.cards.r.Refute.class));
        cards.add(new SetCardInfo("Return to Dust", 427, Rarity.UNCOMMON, mage.cards.r.ReturnToDust.class));
        cards.add(new SetCardInfo("Rogue's Passage", 552, Rarity.UNCOMMON, mage.cards.r.RoguesPassage.class));
        cards.add(new SetCardInfo("Run Amok", 582, Rarity.COMMON, mage.cards.r.RunAmok.class));
        cards.add(new SetCardInfo("Shineshadow Snarl", 558, Rarity.RARE, mage.cards.s.ShineshadowSnarl.class));
        cards.add(new SetCardInfo("Shuttle Crew", 137, Rarity.UNCOMMON, mage.cards.s.ShuttleCrew.class));
        cards.add(new SetCardInfo("Sickbay Orderly", 138, Rarity.COMMON, mage.cards.s.SickbayOrderly.class));
        cards.add(new SetCardInfo("Skullclamp", 498, Rarity.RARE, mage.cards.s.Skullclamp.class));
        cards.add(new SetCardInfo("Skycloud Expanse", 559, Rarity.RARE, mage.cards.s.SkycloudExpanse.class));
        cards.add(new SetCardInfo("Sol Ring", 499, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Spire of Industry", 562, Rarity.RARE, mage.cards.s.SpireOfIndustry.class));
        cards.add(new SetCardInfo("Spock, Logical Choice", 194, Rarity.MYTHIC, mage.cards.s.SpockLogicalChoice.class));
        cards.add(new SetCardInfo("Spot, Felis catus", 237, Rarity.RARE, mage.cards.s.SpotFelisCatus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spot, Felis catus", 29, Rarity.RARE, mage.cards.s.SpotFelisCatus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Starfleet Communicator", 121, Rarity.RARE, mage.cards.s.StarfleetCommunicator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Starfleet Communicator", 356, Rarity.RARE, mage.cards.s.StarfleetCommunicator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Starfleet Crew", 139, Rarity.COMMON, mage.cards.s.StarfleetCrew.class));
        cards.add(new SetCardInfo("Starship Enterprise", 122, Rarity.RARE, mage.cards.s.StarshipEnterprise.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Starship Enterprise", 211, Rarity.RARE, mage.cards.s.StarshipEnterprise.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stonespeaker Crystal", 500, Rarity.UNCOMMON, mage.cards.s.StonespeakerCrystal.class));
        cards.add(new SetCardInfo("Sunken Hollow", 564, Rarity.RARE, mage.cards.s.SunkenHollow.class));
        cards.add(new SetCardInfo("Swiftfoot Boots", 501, Rarity.UNCOMMON, mage.cards.s.SwiftfootBoots.class));
        cards.add(new SetCardInfo("T'Pol, Vulcan Representative", 151, Rarity.UNCOMMON, mage.cards.t.TPolVulcanRepresentative.class));
        cards.add(new SetCardInfo("Talarian Hook Spider", 175, Rarity.COMMON, mage.cards.t.TalarianHookSpider.class));
        cards.add(new SetCardInfo("Talisman of Dominance", 505, Rarity.UNCOMMON, mage.cards.t.TalismanOfDominance.class));
        cards.add(new SetCardInfo("Talisman of Hierarchy", 506, Rarity.UNCOMMON, mage.cards.t.TalismanOfHierarchy.class));
        cards.add(new SetCardInfo("Talisman of Progress", 508, Rarity.UNCOMMON, mage.cards.t.TalismanOfProgress.class));
        cards.add(new SetCardInfo("Tempered Steel", 430, Rarity.RARE, mage.cards.t.TemperedSteel.class));
        cards.add(new SetCardInfo("Tenacious Tosk", 201, Rarity.RARE, mage.cards.t.TenaciousTosk.class));
        cards.add(new SetCardInfo("Terramorphic Expanse", 571, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
        cards.add(new SetCardInfo("The Immortal Sun", 495, Rarity.MYTHIC, mage.cards.t.TheImmortalSun.class));
        cards.add(new SetCardInfo("The Sword of Kahless", 123, Rarity.RARE, mage.cards.t.TheSwordOfKahless.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Sword of Kahless", 357, Rarity.RARE, mage.cards.t.TheSwordOfKahless.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thirst for Knowledge", 443, Rarity.UNCOMMON, mage.cards.t.ThirstForKnowledge.class));
        cards.add(new SetCardInfo("Thoughtcast", 444, Rarity.COMMON, mage.cards.t.Thoughtcast.class));
        cards.add(new SetCardInfo("Toxic Deluge", 448, Rarity.RARE, mage.cards.t.ToxicDeluge.class));
        cards.add(new SetCardInfo("Trelane, Squire of Gothos", 195, Rarity.RARE, mage.cards.t.TrelaneSquireOfGothos.class));
        cards.add(new SetCardInfo("Umbral Expanse", 574, Rarity.RARE, mage.cards.u.UmbralExpanse.class));
        cards.add(new SetCardInfo("Underground River", 575, Rarity.RARE, mage.cards.u.UndergroundRiver.class));
        cards.add(new SetCardInfo("Unwinding Clock", 510, Rarity.RARE, mage.cards.u.UnwindingClock.class));
        cards.add(new SetCardInfo("Warship Flight Crew", 168, Rarity.COMMON, mage.cards.w.WarshipFlightCrew.class));
        cards.add(new SetCardInfo("Wayfarer's Bauble", 511, Rarity.COMMON, mage.cards.w.WayfarersBauble.class));
        cards.add(new SetCardInfo("Will Riker, Assuming Command", 140, Rarity.UNCOMMON, mage.cards.w.WillRikerAssumingCommand.class));
        cards.add(new SetCardInfo("Worf, Chief Tactical Officer", 141, Rarity.UNCOMMON, mage.cards.w.WorfChiefTacticalOfficer.class));
        cards.add(new SetCardInfo("Worf, Defiant Commander", 4, Rarity.MYTHIC, mage.cards.w.WorfDefiantCommander.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Worf, Defiant Commander", 8, Rarity.MYTHIC, mage.cards.w.WorfDefiantCommander.class, NON_FULL_USE_VARIOUS));
    }
}
