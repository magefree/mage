package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class TheBrothersWar extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList("Arcane Proxy", "Autonomous Assembler", "Blitz Automaton", "Boulderbranch Golem", "Combat Thresher", "Cradle Clearcutter", "Depth Charge Colossus", "Fallaji Dragon Engine", "Goring Warplow", "Hulking Metamorph", "Iron-Craw Crusher", "Phyrexian Fleshgorger", "Rootwire Amalgam", "Rust Goliath", "Skitterbeam Battalion", "Spotter Thopter", "Steel Seraph", "Woodcaller Automaton");

    private static final TheBrothersWar instance = new TheBrothersWar();

    public static TheBrothersWar getInstance() {
        return instance;
    }

    private TheBrothersWar() {
        super("The Brothers' War", "BRO", ExpansionSet.buildDate(2022, 11, 18), SetType.EXPANSION);
        this.blockName = "The Brothers' War";
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Aeronaut Cavalry", 1, Rarity.COMMON, mage.cards.a.AeronautCavalry.class));
        cards.add(new SetCardInfo("Air Marshal", 43, Rarity.COMMON, mage.cards.a.AirMarshal.class));
        cards.add(new SetCardInfo("Alloy Animist", 166, Rarity.UNCOMMON, mage.cards.a.AlloyAnimist.class));
        cards.add(new SetCardInfo("Ambush Paratrooper", 3, Rarity.COMMON, mage.cards.a.AmbushParatrooper.class));
        cards.add(new SetCardInfo("Arbalest Engineers", 206, Rarity.UNCOMMON, mage.cards.a.ArbalestEngineers.class));
        cards.add(new SetCardInfo("Arcane Proxy", 75, Rarity.MYTHIC, mage.cards.a.ArcaneProxy.class));
        cards.add(new SetCardInfo("Argivian Avenger", 232, Rarity.UNCOMMON, mage.cards.a.ArgivianAvenger.class));
        cards.add(new SetCardInfo("Argoth, Sanctum of Nature", "256a", Rarity.RARE, mage.cards.a.ArgothSanctumOfNature.class));
        cards.add(new SetCardInfo("Argothian Opportunist", 167, Rarity.COMMON, mage.cards.a.ArgothianOpportunist.class));
        cards.add(new SetCardInfo("Argothian Sprite", 168, Rarity.COMMON, mage.cards.a.ArgothianSprite.class));
        cards.add(new SetCardInfo("Artificer's Dragon", 291, Rarity.RARE, mage.cards.a.ArtificersDragon.class));
        cards.add(new SetCardInfo("Ashnod's Harvester", 117, Rarity.UNCOMMON, mage.cards.a.AshnodsHarvester.class));
        cards.add(new SetCardInfo("Ashnod, Flesh Mechanist", 84, Rarity.RARE, mage.cards.a.AshnodFleshMechanist.class));
        cards.add(new SetCardInfo("Audacity", 169, Rarity.UNCOMMON, mage.cards.a.Audacity.class));
        cards.add(new SetCardInfo("Autonomous Assembler", 34, Rarity.RARE, mage.cards.a.AutonomousAssembler.class));
        cards.add(new SetCardInfo("Awaken the Woods", 170, Rarity.MYTHIC, mage.cards.a.AwakenTheWoods.class));
        cards.add(new SetCardInfo("Battlefield Butcher", 86, Rarity.UNCOMMON, mage.cards.b.BattlefieldButcher.class));
        cards.add(new SetCardInfo("Battlefield Forge", 257, Rarity.RARE, mage.cards.b.BattlefieldForge.class));
        cards.add(new SetCardInfo("Bitter Reunion", 127, Rarity.COMMON, mage.cards.b.BitterReunion.class));
        cards.add(new SetCardInfo("Bladecoil Serpent", 229, Rarity.MYTHIC, mage.cards.b.BladecoilSerpent.class));
        cards.add(new SetCardInfo("Blanchwood Armor", 171, Rarity.UNCOMMON, mage.cards.b.BlanchwoodArmor.class));
        cards.add(new SetCardInfo("Blast Zone", 258, Rarity.RARE, mage.cards.b.BlastZone.class));
        cards.add(new SetCardInfo("Brotherhood's End", 128, Rarity.RARE, mage.cards.b.BrotherhoodsEnd.class));
        cards.add(new SetCardInfo("Brushland", 259, Rarity.RARE, mage.cards.b.Brushland.class));
        cards.add(new SetCardInfo("Bushwhack", 174, Rarity.UNCOMMON, mage.cards.b.Bushwhack.class));
        cards.add(new SetCardInfo("Citanul Stalwart", 175, Rarity.COMMON, mage.cards.c.CitanulStalwart.class));
        cards.add(new SetCardInfo("Clay Champion", 230, Rarity.MYTHIC, mage.cards.c.ClayChampion.class));
        cards.add(new SetCardInfo("Clay Revenant", 118, Rarity.COMMON, mage.cards.c.ClayRevenant.class));
        cards.add(new SetCardInfo("Combat Courier", 77, Rarity.COMMON, mage.cards.c.CombatCourier.class));
        cards.add(new SetCardInfo("Combat Thresher", 35, Rarity.UNCOMMON, mage.cards.c.CombatThresher.class));
        cards.add(new SetCardInfo("Corrupt", 88, Rarity.UNCOMMON, mage.cards.c.Corrupt.class));
        cards.add(new SetCardInfo("Curate", 44, Rarity.COMMON, mage.cards.c.Curate.class));
        cards.add(new SetCardInfo("Defabricate", 45, Rarity.UNCOMMON, mage.cards.d.Defabricate.class));
        cards.add(new SetCardInfo("Depth Charge Colossus", 78, Rarity.COMMON, mage.cards.d.DepthChargeColossus.class));
        cards.add(new SetCardInfo("Diabolic Intent", 89, Rarity.RARE, mage.cards.d.DiabolicIntent.class));
        cards.add(new SetCardInfo("Disciples of Gix", 90, Rarity.UNCOMMON, mage.cards.d.DisciplesOfGix.class));
        cards.add(new SetCardInfo("Disenchant", 6, Rarity.COMMON, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Disfigure", 91, Rarity.COMMON, mage.cards.d.Disfigure.class));
        cards.add(new SetCardInfo("Draconic Destiny", 130, Rarity.MYTHIC, mage.cards.d.DraconicDestiny.class));
        cards.add(new SetCardInfo("Drafna, Founder of Lat-Nam", 47, Rarity.RARE, mage.cards.d.DrafnaFounderOfLatNam.class));
        cards.add(new SetCardInfo("Energy Refractor", 234, Rarity.COMMON, mage.cards.e.EnergyRefractor.class));
        cards.add(new SetCardInfo("Epic Confrontation", 176, Rarity.COMMON, mage.cards.e.EpicConfrontation.class));
        cards.add(new SetCardInfo("Evangel of Synthesis", 209, Rarity.UNCOMMON, mage.cards.e.EvangelOfSynthesis.class));
        cards.add(new SetCardInfo("Evolving Wilds", 261, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Excavation Explosion", 132, Rarity.COMMON, mage.cards.e.ExcavationExplosion.class));
        cards.add(new SetCardInfo("Fade from History", 177, Rarity.RARE, mage.cards.f.FadeFromHistory.class));
        cards.add(new SetCardInfo("Fallaji Chaindancer", 134, Rarity.COMMON, mage.cards.f.FallajiChaindancer.class));
        cards.add(new SetCardInfo("Fallaji Dragon Engine", 159, Rarity.UNCOMMON, mage.cards.f.FallajiDragonEngine.class));
        cards.add(new SetCardInfo("Fallaji Excavation", 178, Rarity.UNCOMMON, mage.cards.f.FallajiExcavation.class));
        cards.add(new SetCardInfo("Fallaji Vanguard", 210, Rarity.UNCOMMON, mage.cards.f.FallajiVanguard.class));
        cards.add(new SetCardInfo("Fauna Shaman", 179, Rarity.RARE, mage.cards.f.FaunaShaman.class));
        cards.add(new SetCardInfo("Feldon, Ronom Excavator", 135, Rarity.RARE, mage.cards.f.FeldonRonomExcavator.class));
        cards.add(new SetCardInfo("Flow of Knowledge", 49, Rarity.UNCOMMON, mage.cards.f.FlowOfKnowledge.class));
        cards.add(new SetCardInfo("Fog of War", 180, Rarity.COMMON, mage.cards.f.FogOfWar.class));
        cards.add(new SetCardInfo("Forest", 276, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forging the Anchor", 50, Rarity.UNCOMMON, mage.cards.f.ForgingTheAnchor.class));
        cards.add(new SetCardInfo("Gaea's Courser", 181, Rarity.UNCOMMON, mage.cards.g.GaeasCourser.class));
        cards.add(new SetCardInfo("Geology Enthusiast", 289, Rarity.RARE, mage.cards.g.GeologyEnthusiast.class));
        cards.add(new SetCardInfo("Giant Growth", 183, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Gix's Caress", 96, Rarity.COMMON, mage.cards.g.GixsCaress.class));
        cards.add(new SetCardInfo("Gix's Command", 97, Rarity.RARE, mage.cards.g.GixsCommand.class));
        cards.add(new SetCardInfo("Gixian Puppeteer", 99, Rarity.RARE, mage.cards.g.GixianPuppeteer.class));
        cards.add(new SetCardInfo("Gixian Skullflayer", 100, Rarity.COMMON, mage.cards.g.GixianSkullflayer.class));
        cards.add(new SetCardInfo("Gnawing Vermin", 101, Rarity.UNCOMMON, mage.cards.g.GnawingVermin.class));
        cards.add(new SetCardInfo("Go for the Throat", 102, Rarity.UNCOMMON, mage.cards.g.GoForTheThroat.class));
        cards.add(new SetCardInfo("Goblin Blast-Runner", 137, Rarity.COMMON, mage.cards.g.GoblinBlastRunner.class));
        cards.add(new SetCardInfo("Goblin Firebomb", 235, Rarity.COMMON, mage.cards.g.GoblinFirebomb.class));
        cards.add(new SetCardInfo("Great Desert Prospector", 7, Rarity.UNCOMMON, mage.cards.g.GreatDesertProspector.class));
        cards.add(new SetCardInfo("Gruesome Realization", 103, Rarity.UNCOMMON, mage.cards.g.GruesomeRealization.class));
        cards.add(new SetCardInfo("Hajar, Loyal Bodyguard", 211, Rarity.RARE, mage.cards.h.HajarLoyalBodyguard.class));
        cards.add(new SetCardInfo("Hall of Tagsin", 263, Rarity.RARE, mage.cards.h.HallOfTagsin.class));
        cards.add(new SetCardInfo("Harbin, Vanguard Aviator", 212, Rarity.RARE, mage.cards.h.HarbinVanguardAviator.class));
        cards.add(new SetCardInfo("Haywire Mite", 199, Rarity.UNCOMMON, mage.cards.h.HaywireMite.class));
        cards.add(new SetCardInfo("Heavyweight Demolisher", 160, Rarity.UNCOMMON, mage.cards.h.HeavyweightDemolisher.class));
        cards.add(new SetCardInfo("Hero of the Dunes", 213, Rarity.UNCOMMON, mage.cards.h.HeroOfTheDunes.class));
        cards.add(new SetCardInfo("Hurkyl's Final Meditation", 52, Rarity.RARE, mage.cards.h.HurkylsFinalMeditation.class));
        cards.add(new SetCardInfo("Hurkyl, Master Wizard", 51, Rarity.RARE, mage.cards.h.HurkylMasterWizard.class));
        cards.add(new SetCardInfo("In the Trenches", 8, Rarity.MYTHIC, mage.cards.i.InTheTrenches.class));
        cards.add(new SetCardInfo("Island", 270, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Junkyard Genius", 214, Rarity.UNCOMMON, mage.cards.j.JunkyardGenius.class));
        cards.add(new SetCardInfo("Kayla's Command", 9, Rarity.RARE, mage.cards.k.KaylasCommand.class));
        cards.add(new SetCardInfo("Keeper of the Cadence", 54, Rarity.UNCOMMON, mage.cards.k.KeeperOfTheCadence.class));
        cards.add(new SetCardInfo("Kill-Zone Acrobat", 106, Rarity.COMMON, mage.cards.k.KillZoneAcrobat.class));
        cards.add(new SetCardInfo("Koilos Roc", 55, Rarity.COMMON, mage.cards.k.KoilosRoc.class));
        cards.add(new SetCardInfo("Lat-Nam Adept", 56, Rarity.COMMON, mage.cards.l.LatNamAdept.class));
        cards.add(new SetCardInfo("Levitating Statue", 236, Rarity.UNCOMMON, mage.cards.l.LevitatingStatue.class));
        cards.add(new SetCardInfo("Llanowar Wastes", 264, Rarity.RARE, mage.cards.l.LlanowarWastes.class));
        cards.add(new SetCardInfo("Loran of the Third Path", 12, Rarity.RARE, mage.cards.l.LoranOfTheThirdPath.class));
        cards.add(new SetCardInfo("Loran, Disciple of History", 13, Rarity.UNCOMMON, mage.cards.l.LoranDiscipleOfHistory.class));
        cards.add(new SetCardInfo("Machine Over Matter", 57, Rarity.COMMON, mage.cards.m.MachineOverMatter.class));
        cards.add(new SetCardInfo("Mass Production", 15, Rarity.UNCOMMON, mage.cards.m.MassProduction.class));
        cards.add(new SetCardInfo("Mine Worker", 239, Rarity.COMMON, mage.cards.m.MineWorker.class));
        cards.add(new SetCardInfo("Mishra's Command", 141, Rarity.RARE, mage.cards.m.MishrasCommand.class));
        cards.add(new SetCardInfo("Mishra's Domination", 142, Rarity.COMMON, mage.cards.m.MishrasDomination.class));
        cards.add(new SetCardInfo("Mishra's Foundry", 265, Rarity.RARE, mage.cards.m.MishrasFoundry.class));
        cards.add(new SetCardInfo("Mishra's Juggernaut", 161, Rarity.COMMON, mage.cards.m.MishrasJuggernaut.class));
        cards.add(new SetCardInfo("Mishra's Onslaught", 143, Rarity.COMMON, mage.cards.m.MishrasOnslaught.class));
        cards.add(new SetCardInfo("Mishra, Claimed by Gix", 216, Rarity.MYTHIC, mage.cards.m.MishraClaimedByGix.class));
        cards.add(new SetCardInfo("Mishra, Excavation Prodigy", 140, Rarity.UNCOMMON, mage.cards.m.MishraExcavationProdigy.class));
        cards.add(new SetCardInfo("Mishra, Lost to Phyrexia", "163b", Rarity.MYTHIC, mage.cards.m.MishraLostToPhyrexia.class));
        cards.add(new SetCardInfo("Mishra, Tamer of Mak Fawa", 217, Rarity.RARE, mage.cards.m.MishraTamerOfMakFawa.class));
        cards.add(new SetCardInfo("Monastery Swiftspear", 144, Rarity.UNCOMMON, mage.cards.m.MonasterySwiftspear.class));
        cards.add(new SetCardInfo("Mountain", 274, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Obliterating Bolt", 145, Rarity.UNCOMMON, mage.cards.o.ObliteratingBolt.class));
        cards.add(new SetCardInfo("Obstinate Baloth", 187, Rarity.UNCOMMON, mage.cards.o.ObstinateBaloth.class));
        cards.add(new SetCardInfo("Overwhelming Remorse", 110, Rarity.COMMON, mage.cards.o.OverwhelmingRemorse.class));
        cards.add(new SetCardInfo("Painful Quandary", 111, Rarity.RARE, mage.cards.p.PainfulQuandary.class));
        cards.add(new SetCardInfo("Perennial Behemoth", 202, Rarity.RARE, mage.cards.p.PerennialBehemoth.class));
        cards.add(new SetCardInfo("Perimeter Patrol", 188, Rarity.COMMON, mage.cards.p.PerimeterPatrol.class));
        cards.add(new SetCardInfo("Phalanx Vanguard", 19, Rarity.COMMON, mage.cards.p.PhalanxVanguard.class));
        cards.add(new SetCardInfo("Phyrexian Dragon Engine", "163a", Rarity.RARE, mage.cards.p.PhyrexianDragonEngine.class));
        cards.add(new SetCardInfo("Phyrexian Fleshgorger", 121, Rarity.MYTHIC, mage.cards.p.PhyrexianFleshgorger.class));
        cards.add(new SetCardInfo("Plains", 268, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Platoon Dispenser", 36, Rarity.MYTHIC, mage.cards.p.PlatoonDispenser.class));
        cards.add(new SetCardInfo("Power Plant Worker", 241, Rarity.COMMON, mage.cards.p.PowerPlantWorker.class));
        cards.add(new SetCardInfo("Powerstone Engineer", 20, Rarity.COMMON, mage.cards.p.PowerstoneEngineer.class));
        cards.add(new SetCardInfo("Powerstone Fracture", 112, Rarity.COMMON, mage.cards.p.PowerstoneFracture.class));
        cards.add(new SetCardInfo("Pyrrhic Blast", 148, Rarity.UNCOMMON, mage.cards.p.PyrrhicBlast.class));
        cards.add(new SetCardInfo("Queen Kayla bin-Kroog", 218, Rarity.RARE, mage.cards.q.QueenKaylaBinKroog.class));
        cards.add(new SetCardInfo("Raze to the Ground", 149, Rarity.COMMON, mage.cards.r.RazeToTheGround.class));
        cards.add(new SetCardInfo("Reconstructed Thopter", 242, Rarity.UNCOMMON, mage.cards.r.ReconstructedThopter.class));
        cards.add(new SetCardInfo("Recruitment Officer", 23, Rarity.UNCOMMON, mage.cards.r.RecruitmentOfficer.class));
        cards.add(new SetCardInfo("Repair and Recharge", 24, Rarity.UNCOMMON, mage.cards.r.RepairAndRecharge.class));
        cards.add(new SetCardInfo("Roc Hunter", 150, Rarity.COMMON, mage.cards.r.RocHunter.class));
        cards.add(new SetCardInfo("Rust Goliath", 204, Rarity.COMMON, mage.cards.r.RustGoliath.class));
        cards.add(new SetCardInfo("Sardian Cliffstomper", 151, Rarity.UNCOMMON, mage.cards.s.SardianCliffstomper.class));
        cards.add(new SetCardInfo("Sarinth Steelseeker", 189, Rarity.UNCOMMON, mage.cards.s.SarinthSteelseeker.class));
        cards.add(new SetCardInfo("Scrapwork Cohort", 37, Rarity.COMMON, mage.cards.s.ScrapworkCohort.class));
        cards.add(new SetCardInfo("Scrapwork Mutt", 164, Rarity.COMMON, mage.cards.s.ScrapworkMutt.class));
        cards.add(new SetCardInfo("Scrapwork Rager", 123, Rarity.COMMON, mage.cards.s.ScrapworkRager.class));
        cards.add(new SetCardInfo("Shoot Down", 190, Rarity.COMMON, mage.cards.s.ShootDown.class));
        cards.add(new SetCardInfo("Sibling Rivalry", 152, Rarity.COMMON, mage.cards.s.SiblingRivalry.class));
        cards.add(new SetCardInfo("Siege Veteran", 25, Rarity.RARE, mage.cards.s.SiegeVeteran.class));
        cards.add(new SetCardInfo("Simian Simulacrum", 205, Rarity.RARE, mage.cards.s.SimianSimulacrum.class));
        cards.add(new SetCardInfo("Skitterbeam Battalion", 165, Rarity.MYTHIC, mage.cards.s.SkitterbeamBattalion.class));
        cards.add(new SetCardInfo("Skystrike Officer", 62, Rarity.RARE, mage.cards.s.SkystrikeOfficer.class));
        cards.add(new SetCardInfo("Splitting the Powerstone", 63, Rarity.UNCOMMON, mage.cards.s.SplittingThePowerstone.class));
        cards.add(new SetCardInfo("Static Net", 27, Rarity.UNCOMMON, mage.cards.s.StaticNet.class));
        cards.add(new SetCardInfo("Stern Lesson", 64, Rarity.COMMON, mage.cards.s.SternLesson.class));
        cards.add(new SetCardInfo("Stone Retrieval Unit", 248, Rarity.COMMON, mage.cards.s.StoneRetrievalUnit.class));
        cards.add(new SetCardInfo("Su-Chi Cave Guard", 249, Rarity.UNCOMMON, mage.cards.s.SuChiCaveGuard.class));
        cards.add(new SetCardInfo("Supply Drop", 250, Rarity.COMMON, mage.cards.s.SupplyDrop.class));
        cards.add(new SetCardInfo("Surge Engine", 81, Rarity.MYTHIC, mage.cards.s.SurgeEngine.class));
        cards.add(new SetCardInfo("Survivor of Korlis", 28, Rarity.COMMON, mage.cards.s.SurvivorOfKorlis.class));
        cards.add(new SetCardInfo("Swamp", 272, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Symmetry Matrix", 252, Rarity.UNCOMMON, mage.cards.s.SymmetryMatrix.class));
        cards.add(new SetCardInfo("Tawnos, the Toymaker", 222, Rarity.RARE, mage.cards.t.TawnosTheToymaker.class));
        cards.add(new SetCardInfo("Teething Wurmlet", 192, Rarity.RARE, mage.cards.t.TeethingWurmlet.class));
        cards.add(new SetCardInfo("Teferi, Temporal Pilgrim", 66, Rarity.MYTHIC, mage.cards.t.TeferiTemporalPilgrim.class));
        cards.add(new SetCardInfo("Terisian Mindbreaker", 83, Rarity.RARE, mage.cards.t.TerisianMindbreaker.class));
        cards.add(new SetCardInfo("Terror Ballista", 290, Rarity.RARE, mage.cards.t.TerrorBallista.class));
        cards.add(new SetCardInfo("The Mightstone and Weakstone", "238a", Rarity.RARE, mage.cards.t.TheMightstoneAndWeakstone.class));
        cards.add(new SetCardInfo("The Stasis Coffin", 245, Rarity.RARE, mage.cards.t.TheStasisCoffin.class));
        cards.add(new SetCardInfo("Third Path Iconoclast", 223, Rarity.UNCOMMON, mage.cards.t.ThirdPathIconoclast.class));
        cards.add(new SetCardInfo("Third Path Savant", 67, Rarity.COMMON, mage.cards.t.ThirdPathSavant.class));
        cards.add(new SetCardInfo("Thopter Architect", 29, Rarity.UNCOMMON, mage.cards.t.ThopterArchitect.class));
        cards.add(new SetCardInfo("Thopter Mechanic", 68, Rarity.UNCOMMON, mage.cards.t.ThopterMechanic.class));
        cards.add(new SetCardInfo("Thran Spider", 254, Rarity.RARE, mage.cards.t.ThranSpider.class));
        cards.add(new SetCardInfo("Thraxodemon", 115, Rarity.COMMON, mage.cards.t.Thraxodemon.class));
        cards.add(new SetCardInfo("Titania, Gaea Incarnate", "256b", Rarity.MYTHIC, mage.cards.t.TitaniaGaeaIncarnate.class));
        cards.add(new SetCardInfo("Titania, Voice of Gaea", 193, Rarity.MYTHIC, mage.cards.t.TitaniaVoiceOfGaea.class));
        cards.add(new SetCardInfo("Tocasia's Dig Site", 266, Rarity.COMMON, mage.cards.t.TocasiasDigSite.class));
        cards.add(new SetCardInfo("Tocasia's Onulet", 39, Rarity.COMMON, mage.cards.t.TocasiasOnulet.class));
        cards.add(new SetCardInfo("Tocasia's Welcome", 30, Rarity.RARE, mage.cards.t.TocasiasWelcome.class));
        cards.add(new SetCardInfo("Tomakul Honor Guard", 195, Rarity.COMMON, mage.cards.t.TomakulHonorGuard.class));
        cards.add(new SetCardInfo("Tower Worker", 255, Rarity.COMMON, mage.cards.t.TowerWorker.class));
        cards.add(new SetCardInfo("Transmogrant's Crown", 125, Rarity.RARE, mage.cards.t.TransmograntsCrown.class));
        cards.add(new SetCardInfo("Tyrant of Kher Ridges", 154, Rarity.RARE, mage.cards.t.TyrantOfKherRidges.class));
        cards.add(new SetCardInfo("Underground River", 267, Rarity.RARE, mage.cards.u.UndergroundRiver.class));
        cards.add(new SetCardInfo("Union of the Third Path", 31, Rarity.COMMON, mage.cards.u.UnionOfTheThirdPath.class));
        cards.add(new SetCardInfo("Unleash Shell", 155, Rarity.COMMON, mage.cards.u.UnleashShell.class));
        cards.add(new SetCardInfo("Urza's Command", 70, Rarity.RARE, mage.cards.u.UrzasCommand.class));
        cards.add(new SetCardInfo("Urza's Rebuff", 71, Rarity.COMMON, mage.cards.u.UrzasRebuff.class));
        cards.add(new SetCardInfo("Urza's Sylex", 40, Rarity.MYTHIC, mage.cards.u.UrzasSylex.class));
        cards.add(new SetCardInfo("Urza, Lord Protector", 225, Rarity.MYTHIC, mage.cards.u.UrzaLordProtector.class));
        cards.add(new SetCardInfo("Urza, Planeswalker", "238b", Rarity.MYTHIC, mage.cards.u.UrzaPlaneswalker.class));
        cards.add(new SetCardInfo("Urza, Powerstone Prodigy", 69, Rarity.UNCOMMON, mage.cards.u.UrzaPowerstoneProdigy.class));
        cards.add(new SetCardInfo("Urza, Prince of Kroog", 226, Rarity.RARE, mage.cards.u.UrzaPrinceOfKroog.class));
        cards.add(new SetCardInfo("Veteran's Powerblade", 41, Rarity.COMMON, mage.cards.v.VeteransPowerblade.class));
        cards.add(new SetCardInfo("Weakstone's Subjugation", 72, Rarity.COMMON, mage.cards.w.WeakstonesSubjugation.class));
        cards.add(new SetCardInfo("Whirling Strike", 157, Rarity.COMMON, mage.cards.w.WhirlingStrike.class));
        cards.add(new SetCardInfo("Wing Commando", 73, Rarity.COMMON, mage.cards.w.WingCommando.class));
        cards.add(new SetCardInfo("Yotian Dissident", 227, Rarity.UNCOMMON, mage.cards.y.YotianDissident.class));
        cards.add(new SetCardInfo("Yotian Frontliner", 42, Rarity.UNCOMMON, mage.cards.y.YotianFrontliner.class));
        cards.add(new SetCardInfo("Yotian Medic", 33, Rarity.COMMON, mage.cards.y.YotianMedic.class));
        cards.add(new SetCardInfo("Yotian Tactician", 228, Rarity.UNCOMMON, mage.cards.y.YotianTactician.class));
        cards.add(new SetCardInfo("Zephyr Sentinel", 74, Rarity.UNCOMMON, mage.cards.z.ZephyrSentinel.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName())); // remove when mechanic is implemented
    }

//    @Override
//    public BoosterCollator createCollator() {
//        return new TheBrothersWarCollator();
//    }
}
