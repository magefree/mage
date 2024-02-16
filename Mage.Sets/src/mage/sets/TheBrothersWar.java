package mage.sets;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardRepository;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheElk801
 */
public final class TheBrothersWar extends ExpansionSet {
    private static final TheBrothersWar instance = new TheBrothersWar();

    public static TheBrothersWar getInstance() {
        return instance;
    }

    private TheBrothersWar() {
        super("The Brothers' War", "BRO", ExpansionSet.buildDate(2022, 11, 18), SetType.EXPANSION);
        this.blockName = "The Brothers' War";
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.numBoosterSpecial = 1;
        this.ratioBoosterMythic = 7;
        this.maxCardNumberInBooster = 287;

        cards.add(new SetCardInfo("Aeronaut Cavalry", 1, Rarity.COMMON, mage.cards.a.AeronautCavalry.class));
        cards.add(new SetCardInfo("Aeronaut's Wings", 231, Rarity.COMMON, mage.cards.a.AeronautsWings.class));
        cards.add(new SetCardInfo("Air Marshal", 43, Rarity.COMMON, mage.cards.a.AirMarshal.class));
        cards.add(new SetCardInfo("Airlift Chaplain", 2, Rarity.COMMON, mage.cards.a.AirliftChaplain.class));
        cards.add(new SetCardInfo("Alloy Animist", 166, Rarity.UNCOMMON, mage.cards.a.AlloyAnimist.class));
        cards.add(new SetCardInfo("Ambush Paratrooper", 3, Rarity.COMMON, mage.cards.a.AmbushParatrooper.class));
        cards.add(new SetCardInfo("Arbalest Engineers", 206, Rarity.UNCOMMON, mage.cards.a.ArbalestEngineers.class));
        cards.add(new SetCardInfo("Arcane Proxy", 319, Rarity.MYTHIC, mage.cards.a.ArcaneProxy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Arcane Proxy", 75, Rarity.MYTHIC, mage.cards.a.ArcaneProxy.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Argivian Avenger", 232, Rarity.UNCOMMON, mage.cards.a.ArgivianAvenger.class));
        cards.add(new SetCardInfo("Argoth, Sanctum of Nature", "256a", Rarity.RARE, mage.cards.a.ArgothSanctumOfNature.class));
        cards.add(new SetCardInfo("Argothian Opportunist", 167, Rarity.COMMON, mage.cards.a.ArgothianOpportunist.class));
        cards.add(new SetCardInfo("Argothian Sprite", 168, Rarity.COMMON, mage.cards.a.ArgothianSprite.class));
        cards.add(new SetCardInfo("Arms Race", 126, Rarity.UNCOMMON, mage.cards.a.ArmsRace.class));
        cards.add(new SetCardInfo("Artificer's Dragon", 291, Rarity.RARE, mage.cards.a.ArtificersDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Artificer's Dragon", 376, Rarity.RARE, mage.cards.a.ArtificersDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ashnod's Harvester", 117, Rarity.UNCOMMON, mage.cards.a.AshnodsHarvester.class));
        cards.add(new SetCardInfo("Ashnod's Intervention", 85, Rarity.COMMON, mage.cards.a.AshnodsIntervention.class));
        cards.add(new SetCardInfo("Ashnod, Flesh Mechanist", 323, Rarity.RARE, mage.cards.a.AshnodFleshMechanist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ashnod, Flesh Mechanist", 84, Rarity.RARE, mage.cards.a.AshnodFleshMechanist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Audacity", 169, Rarity.UNCOMMON, mage.cards.a.Audacity.class));
        cards.add(new SetCardInfo("Autonomous Assembler", 309, Rarity.RARE, mage.cards.a.AutonomousAssembler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Autonomous Assembler", 34, Rarity.RARE, mage.cards.a.AutonomousAssembler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Awaken the Woods", 170, Rarity.MYTHIC, mage.cards.a.AwakenTheWoods.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Awaken the Woods", 344, Rarity.MYTHIC, mage.cards.a.AwakenTheWoods.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Battery Bearer", 207, Rarity.UNCOMMON, mage.cards.b.BatteryBearer.class));
        cards.add(new SetCardInfo("Battlefield Butcher", 86, Rarity.UNCOMMON, mage.cards.b.BattlefieldButcher.class));
        cards.add(new SetCardInfo("Battlefield Forge", 257, Rarity.RARE, mage.cards.b.BattlefieldForge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Battlefield Forge", 297, Rarity.RARE, mage.cards.b.BattlefieldForge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bitter Reunion", 127, Rarity.COMMON, mage.cards.b.BitterReunion.class));
        cards.add(new SetCardInfo("Bladecoil Serpent", 229, Rarity.MYTHIC, mage.cards.b.BladecoilSerpent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bladecoil Serpent", 361, Rarity.MYTHIC, mage.cards.b.BladecoilSerpent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blanchwood Armor", 171, Rarity.UNCOMMON, mage.cards.b.BlanchwoodArmor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blanchwood Armor", 384, Rarity.UNCOMMON, mage.cards.b.BlanchwoodArmor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blanchwood Prowler", 172, Rarity.COMMON, mage.cards.b.BlanchwoodProwler.class));
        cards.add(new SetCardInfo("Blast Zone", 258, Rarity.RARE, mage.cards.b.BlastZone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blast Zone", 369, Rarity.RARE, mage.cards.b.BlastZone.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blitz Automaton", 158, Rarity.COMMON, mage.cards.b.BlitzAutomaton.class));
        cards.add(new SetCardInfo("Boulderbranch Golem", 197, Rarity.COMMON, mage.cards.b.BoulderbranchGolem.class));
        cards.add(new SetCardInfo("Brotherhood's End", 128, Rarity.RARE, mage.cards.b.BrotherhoodsEnd.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Brotherhood's End", 335, Rarity.RARE, mage.cards.b.BrotherhoodsEnd.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Brushland", 259, Rarity.RARE, mage.cards.b.Brushland.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Brushland", 298, Rarity.RARE, mage.cards.b.Brushland.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Burrowing Razormaw", 173, Rarity.COMMON, mage.cards.b.BurrowingRazormaw.class));
        cards.add(new SetCardInfo("Bushwhack", 174, Rarity.UNCOMMON, mage.cards.b.Bushwhack.class));
        cards.add(new SetCardInfo("Calamity's Wake", 4, Rarity.UNCOMMON, mage.cards.c.CalamitysWake.class));
        cards.add(new SetCardInfo("Carrion Locust", 87, Rarity.COMMON, mage.cards.c.CarrionLocust.class));
        cards.add(new SetCardInfo("Citanul Stalwart", 175, Rarity.COMMON, mage.cards.c.CitanulStalwart.class));
        cards.add(new SetCardInfo("Cityscape Leveler", 233, Rarity.MYTHIC, mage.cards.c.CityscapeLeveler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cityscape Leveler", 363, Rarity.MYTHIC, mage.cards.c.CityscapeLeveler.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Clay Champion", 230, Rarity.MYTHIC, mage.cards.c.ClayChampion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Clay Champion", 362, Rarity.MYTHIC, mage.cards.c.ClayChampion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Clay Revenant", 118, Rarity.COMMON, mage.cards.c.ClayRevenant.class));
        cards.add(new SetCardInfo("Coastal Bulwark", 76, Rarity.COMMON, mage.cards.c.CoastalBulwark.class));
        cards.add(new SetCardInfo("Combat Courier", 77, Rarity.COMMON, mage.cards.c.CombatCourier.class));
        cards.add(new SetCardInfo("Combat Thresher", 35, Rarity.UNCOMMON, mage.cards.c.CombatThresher.class));
        cards.add(new SetCardInfo("Conscripted Infantry", 129, Rarity.COMMON, mage.cards.c.ConscriptedInfantry.class));
        cards.add(new SetCardInfo("Corrupt", 382, Rarity.UNCOMMON, mage.cards.c.Corrupt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Corrupt", 88, Rarity.UNCOMMON, mage.cards.c.Corrupt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cradle Clearcutter", 198, Rarity.UNCOMMON, mage.cards.c.CradleClearcutter.class));
        cards.add(new SetCardInfo("Curate", 44, Rarity.COMMON, mage.cards.c.Curate.class));
        cards.add(new SetCardInfo("Deadly Riposte", 5, Rarity.COMMON, mage.cards.d.DeadlyRiposte.class));
        cards.add(new SetCardInfo("Deathbloom Ritualist", 208, Rarity.RARE, mage.cards.d.DeathbloomRitualist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deathbloom Ritualist", 353, Rarity.RARE, mage.cards.d.DeathbloomRitualist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Defabricate", 45, Rarity.UNCOMMON, mage.cards.d.Defabricate.class));
        cards.add(new SetCardInfo("Demolition Field", 260, Rarity.UNCOMMON, mage.cards.d.DemolitionField.class));
        cards.add(new SetCardInfo("Depth Charge Colossus", 78, Rarity.COMMON, mage.cards.d.DepthChargeColossus.class));
        cards.add(new SetCardInfo("Desynchronize", 46, Rarity.COMMON, mage.cards.d.Desynchronize.class));
        cards.add(new SetCardInfo("Diabolic Intent", 324, Rarity.RARE, mage.cards.d.DiabolicIntent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Diabolic Intent", 89, Rarity.RARE, mage.cards.d.DiabolicIntent.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Disciples of Gix", 90, Rarity.UNCOMMON, mage.cards.d.DisciplesOfGix.class));
        cards.add(new SetCardInfo("Disenchant", 6, Rarity.COMMON, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Disfigure", 91, Rarity.COMMON, mage.cards.d.Disfigure.class));
        cards.add(new SetCardInfo("Draconic Destiny", 130, Rarity.MYTHIC, mage.cards.d.DraconicDestiny.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Draconic Destiny", 336, Rarity.MYTHIC, mage.cards.d.DraconicDestiny.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Drafna, Founder of Lat-Nam", 313, Rarity.RARE, mage.cards.d.DrafnaFounderOfLatNam.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Drafna, Founder of Lat-Nam", 47, Rarity.RARE, mage.cards.d.DrafnaFounderOfLatNam.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dreams of Steel and Oil", 92, Rarity.UNCOMMON, mage.cards.d.DreamsOfSteelAndOil.class));
        cards.add(new SetCardInfo("Dredging Claw", 119, Rarity.COMMON, mage.cards.d.DredgingClaw.class));
        cards.add(new SetCardInfo("Dwarven Forge-Chanter", 131, Rarity.COMMON, mage.cards.d.DwarvenForgeChanter.class));
        cards.add(new SetCardInfo("Emergency Weld", 93, Rarity.COMMON, mage.cards.e.EmergencyWeld.class));
        cards.add(new SetCardInfo("Energy Refractor", 234, Rarity.COMMON, mage.cards.e.EnergyRefractor.class));
        cards.add(new SetCardInfo("Epic Confrontation", 176, Rarity.COMMON, mage.cards.e.EpicConfrontation.class));
        cards.add(new SetCardInfo("Evangel of Synthesis", 209, Rarity.UNCOMMON, mage.cards.e.EvangelOfSynthesis.class));
        cards.add(new SetCardInfo("Evolving Wilds", 261, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Excavation Explosion", 132, Rarity.COMMON, mage.cards.e.ExcavationExplosion.class));
        cards.add(new SetCardInfo("Fade from History", 177, Rarity.RARE, mage.cards.f.FadeFromHistory.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fade from History", 345, Rarity.RARE, mage.cards.f.FadeFromHistory.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fallaji Archaeologist", 48, Rarity.COMMON, mage.cards.f.FallajiArchaeologist.class));
        cards.add(new SetCardInfo("Fallaji Chaindancer", 134, Rarity.COMMON, mage.cards.f.FallajiChaindancer.class));
        cards.add(new SetCardInfo("Fallaji Dragon Engine", 159, Rarity.UNCOMMON, mage.cards.f.FallajiDragonEngine.class));
        cards.add(new SetCardInfo("Fallaji Excavation", 178, Rarity.UNCOMMON, mage.cards.f.FallajiExcavation.class));
        cards.add(new SetCardInfo("Fallaji Vanguard", 210, Rarity.UNCOMMON, mage.cards.f.FallajiVanguard.class));
        cards.add(new SetCardInfo("Fateful Handoff", 325, Rarity.RARE, mage.cards.f.FatefulHandoff.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fateful Handoff", 94, Rarity.RARE, mage.cards.f.FatefulHandoff.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fauna Shaman", 179, Rarity.RARE, mage.cards.f.FaunaShaman.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fauna Shaman", 346, Rarity.RARE, mage.cards.f.FaunaShaman.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Feldon, Ronom Excavator", 135, Rarity.RARE, mage.cards.f.FeldonRonomExcavator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Feldon, Ronom Excavator", 337, Rarity.RARE, mage.cards.f.FeldonRonomExcavator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flow of Knowledge", 381, Rarity.UNCOMMON, mage.cards.f.FlowOfKnowledge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flow of Knowledge", 49, Rarity.UNCOMMON, mage.cards.f.FlowOfKnowledge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fog of War", 180, Rarity.COMMON, mage.cards.f.FogOfWar.class));
        cards.add(new SetCardInfo("Forest", 276, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 277, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 286, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forest", 287, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Forging the Anchor", 50, Rarity.UNCOMMON, mage.cards.f.ForgingTheAnchor.class));
        cards.add(new SetCardInfo("Fortified Beachhead", 262, Rarity.RARE, mage.cards.f.FortifiedBeachhead.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fortified Beachhead", 370, Rarity.RARE, mage.cards.f.FortifiedBeachhead.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gaea's Courser", 181, Rarity.UNCOMMON, mage.cards.g.GaeasCourser.class));
        cards.add(new SetCardInfo("Gaea's Gift", 182, Rarity.COMMON, mage.cards.g.GaeasGift.class));
        cards.add(new SetCardInfo("Geology Enthusiast", 289, Rarity.RARE, mage.cards.g.GeologyEnthusiast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Geology Enthusiast", 374, Rarity.RARE, mage.cards.g.GeologyEnthusiast.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Giant Cindermaw", 136, Rarity.UNCOMMON, mage.cards.g.GiantCindermaw.class));
        cards.add(new SetCardInfo("Giant Growth", 183, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Gix's Caress", 96, Rarity.COMMON, mage.cards.g.GixsCaress.class));
        cards.add(new SetCardInfo("Gix's Command", 327, Rarity.RARE, mage.cards.g.GixsCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gix's Command", 97, Rarity.RARE, mage.cards.g.GixsCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gix, Yawgmoth Praetor", 326, Rarity.MYTHIC, mage.cards.g.GixYawgmothPraetor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gix, Yawgmoth Praetor", 95, Rarity.MYTHIC, mage.cards.g.GixYawgmothPraetor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gixian Infiltrator", 98, Rarity.COMMON, mage.cards.g.GixianInfiltrator.class));
        cards.add(new SetCardInfo("Gixian Puppeteer", 328, Rarity.RARE, mage.cards.g.GixianPuppeteer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gixian Puppeteer", 99, Rarity.RARE, mage.cards.g.GixianPuppeteer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gixian Skullflayer", 100, Rarity.COMMON, mage.cards.g.GixianSkullflayer.class));
        cards.add(new SetCardInfo("Gnarlroot Pallbearer", 184, Rarity.COMMON, mage.cards.g.GnarlrootPallbearer.class));
        cards.add(new SetCardInfo("Gnawing Vermin", 101, Rarity.UNCOMMON, mage.cards.g.GnawingVermin.class));
        cards.add(new SetCardInfo("Go for the Throat", 102, Rarity.UNCOMMON, mage.cards.g.GoForTheThroat.class));
        cards.add(new SetCardInfo("Goblin Blast-Runner", 137, Rarity.COMMON, mage.cards.g.GoblinBlastRunner.class));
        cards.add(new SetCardInfo("Goblin Firebomb", 235, Rarity.COMMON, mage.cards.g.GoblinFirebomb.class));
        cards.add(new SetCardInfo("Goring Warplow", 120, Rarity.COMMON, mage.cards.g.GoringWarplow.class));
        cards.add(new SetCardInfo("Great Desert Prospector", 7, Rarity.UNCOMMON, mage.cards.g.GreatDesertProspector.class));
        cards.add(new SetCardInfo("Gruesome Realization", 103, Rarity.UNCOMMON, mage.cards.g.GruesomeRealization.class));
        cards.add(new SetCardInfo("Gurgling Anointer", 104, Rarity.UNCOMMON, mage.cards.g.GurglingAnointer.class));
        cards.add(new SetCardInfo("Gwenna, Eyes of Gaea", 185, Rarity.RARE, mage.cards.g.GwennaEyesOfGaea.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gwenna, Eyes of Gaea", 347, Rarity.RARE, mage.cards.g.GwennaEyesOfGaea.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hajar, Loyal Bodyguard", 211, Rarity.RARE, mage.cards.h.HajarLoyalBodyguard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hajar, Loyal Bodyguard", 354, Rarity.RARE, mage.cards.h.HajarLoyalBodyguard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hall of Tagsin", 263, Rarity.RARE, mage.cards.h.HallOfTagsin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hall of Tagsin", 371, Rarity.RARE, mage.cards.h.HallOfTagsin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Harbin, Vanguard Aviator", 212, Rarity.RARE, mage.cards.h.HarbinVanguardAviator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Harbin, Vanguard Aviator", 355, Rarity.RARE, mage.cards.h.HarbinVanguardAviator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Haywire Mite", 199, Rarity.UNCOMMON, mage.cards.h.HaywireMite.class));
        cards.add(new SetCardInfo("Heavyweight Demolisher", 160, Rarity.UNCOMMON, mage.cards.h.HeavyweightDemolisher.class));
        cards.add(new SetCardInfo("Hero of the Dunes", 213, Rarity.UNCOMMON, mage.cards.h.HeroOfTheDunes.class));
        cards.add(new SetCardInfo("Hoarding Recluse", 186, Rarity.COMMON, mage.cards.h.HoardingRecluse.class));
        cards.add(new SetCardInfo("Horned Stoneseeker", 138, Rarity.UNCOMMON, mage.cards.h.HornedStoneseeker.class));
        cards.add(new SetCardInfo("Hostile Negotiations", 105, Rarity.RARE, mage.cards.h.HostileNegotiations.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hostile Negotiations", 329, Rarity.RARE, mage.cards.h.HostileNegotiations.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hulking Metamorph", 79, Rarity.UNCOMMON, mage.cards.h.HulkingMetamorph.class));
        cards.add(new SetCardInfo("Hurkyl's Final Meditation", 315, Rarity.RARE, mage.cards.h.HurkylsFinalMeditation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hurkyl's Final Meditation", 52, Rarity.RARE, mage.cards.h.HurkylsFinalMeditation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hurkyl, Master Wizard", 314, Rarity.MYTHIC, mage.cards.h.HurkylMasterWizard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hurkyl, Master Wizard", 51, Rarity.RARE, mage.cards.h.HurkylMasterWizard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("In the Trenches", 301, Rarity.MYTHIC, mage.cards.i.InTheTrenches.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("In the Trenches", 8, Rarity.MYTHIC, mage.cards.i.InTheTrenches.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Involuntary Cooldown", 53, Rarity.UNCOMMON, mage.cards.i.InvoluntaryCooldown.class));
        cards.add(new SetCardInfo("Iron-Craw Crusher", 200, Rarity.UNCOMMON, mage.cards.i.IronCrawCrusher.class));
        cards.add(new SetCardInfo("Island", 270, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 271, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 280, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 281, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Junkyard Genius", 214, Rarity.UNCOMMON, mage.cards.j.JunkyardGenius.class));
        cards.add(new SetCardInfo("Kayla's Command", 302, Rarity.RARE, mage.cards.k.KaylasCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kayla's Command", 9, Rarity.RARE, mage.cards.k.KaylasCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kayla's Reconstruction", 10, Rarity.RARE, mage.cards.k.KaylasReconstruction.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kayla's Reconstruction", 303, Rarity.RARE, mage.cards.k.KaylasReconstruction.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Keeper of the Cadence", 54, Rarity.UNCOMMON, mage.cards.k.KeeperOfTheCadence.class));
        cards.add(new SetCardInfo("Kill-Zone Acrobat", 106, Rarity.COMMON, mage.cards.k.KillZoneAcrobat.class));
        cards.add(new SetCardInfo("Koilos Roc", 55, Rarity.COMMON, mage.cards.k.KoilosRoc.class));
        cards.add(new SetCardInfo("Lat-Nam Adept", 56, Rarity.COMMON, mage.cards.l.LatNamAdept.class));
        cards.add(new SetCardInfo("Lay Down Arms", 11, Rarity.UNCOMMON, mage.cards.l.LayDownArms.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lay Down Arms", 380, Rarity.UNCOMMON, mage.cards.l.LayDownArms.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Legions to Ashes", 215, Rarity.RARE, mage.cards.l.LegionsToAshes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Legions to Ashes", 356, Rarity.RARE, mage.cards.l.LegionsToAshes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Levitating Statue", 236, Rarity.UNCOMMON, mage.cards.l.LevitatingStatue.class));
        cards.add(new SetCardInfo("Liberator, Urza's Battlethopter", 237, Rarity.RARE, mage.cards.l.LiberatorUrzasBattlethopter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liberator, Urza's Battlethopter", 364, Rarity.RARE, mage.cards.l.LiberatorUrzasBattlethopter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Llanowar Wastes", 264, Rarity.RARE, mage.cards.l.LlanowarWastes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Llanowar Wastes", 299, Rarity.RARE, mage.cards.l.LlanowarWastes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Loran of the Third Path", 12, Rarity.RARE, mage.cards.l.LoranOfTheThirdPath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Loran of the Third Path", 304, Rarity.RARE, mage.cards.l.LoranOfTheThirdPath.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Loran's Escape", 14, Rarity.COMMON, mage.cards.l.LoransEscape.class));
        cards.add(new SetCardInfo("Loran, Disciple of History", 13, Rarity.UNCOMMON, mage.cards.l.LoranDiscipleOfHistory.class));
        cards.add(new SetCardInfo("Machine Over Matter", 57, Rarity.COMMON, mage.cards.m.MachineOverMatter.class));
        cards.add(new SetCardInfo("Mask of the Jadecrafter", 201, Rarity.UNCOMMON, mage.cards.m.MaskOfTheJadecrafter.class));
        cards.add(new SetCardInfo("Mass Production", 15, Rarity.UNCOMMON, mage.cards.m.MassProduction.class));
        cards.add(new SetCardInfo("Mechanized Warfare", 139, Rarity.RARE, mage.cards.m.MechanizedWarfare.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mechanized Warfare", 338, Rarity.RARE, mage.cards.m.MechanizedWarfare.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Meticulous Excavation", 16, Rarity.UNCOMMON, mage.cards.m.MeticulousExcavation.class));
        cards.add(new SetCardInfo("Mightstone's Animation", 58, Rarity.COMMON, mage.cards.m.MightstonesAnimation.class));
        cards.add(new SetCardInfo("Military Discipline", 17, Rarity.COMMON, mage.cards.m.MilitaryDiscipline.class));
        cards.add(new SetCardInfo("Mine Worker", 239, Rarity.COMMON, mage.cards.m.MineWorker.class));
        cards.add(new SetCardInfo("Misery's Shadow", 107, Rarity.RARE, mage.cards.m.MiserysShadow.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Misery's Shadow", 330, Rarity.RARE, mage.cards.m.MiserysShadow.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mishra's Command", 141, Rarity.RARE, mage.cards.m.MishrasCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mishra's Command", 339, Rarity.RARE, mage.cards.m.MishrasCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mishra's Domination", 142, Rarity.COMMON, mage.cards.m.MishrasDomination.class));
        cards.add(new SetCardInfo("Mishra's Foundry", 265, Rarity.RARE, mage.cards.m.MishrasFoundry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mishra's Foundry", 372, Rarity.RARE, mage.cards.m.MishrasFoundry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mishra's Foundry", 378, Rarity.RARE, mage.cards.m.MishrasFoundry.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mishra's Juggernaut", 161, Rarity.COMMON, mage.cards.m.MishrasJuggernaut.class));
        cards.add(new SetCardInfo("Mishra's Onslaught", 143, Rarity.COMMON, mage.cards.m.MishrasOnslaught.class));
        cards.add(new SetCardInfo("Mishra's Research Desk", 162, Rarity.UNCOMMON, mage.cards.m.MishrasResearchDesk.class));
        cards.add(new SetCardInfo("Mishra, Claimed by Gix", 216, Rarity.MYTHIC, mage.cards.m.MishraClaimedByGix.class));
        cards.add(new SetCardInfo("Mishra, Excavation Prodigy", 140, Rarity.UNCOMMON, mage.cards.m.MishraExcavationProdigy.class));
        cards.add(new SetCardInfo("Mishra, Lost to Phyrexia", "163b", Rarity.MYTHIC, mage.cards.m.MishraLostToPhyrexia.class));
        cards.add(new SetCardInfo("Mishra, Tamer of Mak Fawa", 217, Rarity.RARE, mage.cards.m.MishraTamerOfMakFawa.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mishra, Tamer of Mak Fawa", 295, Rarity.RARE, mage.cards.m.MishraTamerOfMakFawa.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Moment of Defiance", 108, Rarity.COMMON, mage.cards.m.MomentOfDefiance.class));
        cards.add(new SetCardInfo("Monastery Swiftspear", 144, Rarity.UNCOMMON, mage.cards.m.MonasterySwiftspear.class));
        cards.add(new SetCardInfo("Mountain", 274, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 275, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 284, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 285, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Myrel, Shield of Argive", 18, Rarity.MYTHIC, mage.cards.m.MyrelShieldOfArgive.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Myrel, Shield of Argive", 305, Rarity.MYTHIC, mage.cards.m.MyrelShieldOfArgive.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("No One Left Behind", 109, Rarity.UNCOMMON, mage.cards.n.NoOneLeftBehind.class));
        cards.add(new SetCardInfo("Obliterating Bolt", 145, Rarity.UNCOMMON, mage.cards.o.ObliteratingBolt.class));
        cards.add(new SetCardInfo("Obstinate Baloth", 187, Rarity.UNCOMMON, mage.cards.o.ObstinateBaloth.class));
        cards.add(new SetCardInfo("One with the Multiverse", 316, Rarity.MYTHIC, mage.cards.o.OneWithTheMultiverse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("One with the Multiverse", 59, Rarity.MYTHIC, mage.cards.o.OneWithTheMultiverse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Over the Top", 146, Rarity.RARE, mage.cards.o.OverTheTop.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Over the Top", 340, Rarity.RARE, mage.cards.o.OverTheTop.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Overwhelming Remorse", 110, Rarity.COMMON, mage.cards.o.OverwhelmingRemorse.class));
        cards.add(new SetCardInfo("Painful Quandary", 111, Rarity.RARE, mage.cards.p.PainfulQuandary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Painful Quandary", 331, Rarity.RARE, mage.cards.p.PainfulQuandary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Penregon Strongbull", 147, Rarity.COMMON, mage.cards.p.PenregonStrongbull.class));
        cards.add(new SetCardInfo("Perennial Behemoth", 202, Rarity.RARE, mage.cards.p.PerennialBehemoth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Perennial Behemoth", 350, Rarity.RARE, mage.cards.p.PerennialBehemoth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Perimeter Patrol", 188, Rarity.COMMON, mage.cards.p.PerimeterPatrol.class));
        cards.add(new SetCardInfo("Phalanx Vanguard", 19, Rarity.COMMON, mage.cards.p.PhalanxVanguard.class));
        cards.add(new SetCardInfo("Phyrexian Dragon Engine", "163a", Rarity.RARE, mage.cards.p.PhyrexianDragonEngine.class));
        cards.add(new SetCardInfo("Phyrexian Fleshgorger", 121, Rarity.MYTHIC, mage.cards.p.PhyrexianFleshgorger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Phyrexian Fleshgorger", 332, Rarity.MYTHIC, mage.cards.p.PhyrexianFleshgorger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 268, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 269, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 278, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 279, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Platoon Dispenser", 310, Rarity.MYTHIC, mage.cards.p.PlatoonDispenser.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Platoon Dispenser", 36, Rarity.MYTHIC, mage.cards.p.PlatoonDispenser.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Portal to Phyrexia", 240, Rarity.MYTHIC, mage.cards.p.PortalToPhyrexia.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Portal to Phyrexia", 365, Rarity.MYTHIC, mage.cards.p.PortalToPhyrexia.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Power Plant Worker", 241, Rarity.COMMON, mage.cards.p.PowerPlantWorker.class));
        cards.add(new SetCardInfo("Powerstone Engineer", 20, Rarity.COMMON, mage.cards.p.PowerstoneEngineer.class));
        cards.add(new SetCardInfo("Powerstone Fracture", 112, Rarity.COMMON, mage.cards.p.PowerstoneFracture.class));
        cards.add(new SetCardInfo("Prison Sentence", 21, Rarity.COMMON, mage.cards.p.PrisonSentence.class));
        cards.add(new SetCardInfo("Pyrrhic Blast", 148, Rarity.UNCOMMON, mage.cards.p.PyrrhicBlast.class));
        cards.add(new SetCardInfo("Queen Kayla bin-Kroog", 218, Rarity.RARE, mage.cards.q.QueenKaylaBinKroog.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Queen Kayla bin-Kroog", 357, Rarity.RARE, mage.cards.q.QueenKaylaBinKroog.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Queen Kayla bin-Kroog", 379, Rarity.RARE, mage.cards.q.QueenKaylaBinKroog.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ravenous Gigamole", 113, Rarity.COMMON, mage.cards.r.RavenousGigamole.class));
        cards.add(new SetCardInfo("Raze to the Ground", 149, Rarity.COMMON, mage.cards.r.RazeToTheGround.class));
        cards.add(new SetCardInfo("Razorlash Transmogrant", 122, Rarity.RARE, mage.cards.r.RazorlashTransmogrant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Razorlash Transmogrant", 333, Rarity.RARE, mage.cards.r.RazorlashTransmogrant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Recommission", 22, Rarity.COMMON, mage.cards.r.Recommission.class));
        cards.add(new SetCardInfo("Reconstructed Thopter", 242, Rarity.UNCOMMON, mage.cards.r.ReconstructedThopter.class));
        cards.add(new SetCardInfo("Recruitment Officer", 23, Rarity.UNCOMMON, mage.cards.r.RecruitmentOfficer.class));
        cards.add(new SetCardInfo("Repair and Recharge", 24, Rarity.UNCOMMON, mage.cards.r.RepairAndRecharge.class));
        cards.add(new SetCardInfo("Rescue Retriever", 288, Rarity.RARE, mage.cards.r.RescueRetriever.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rescue Retriever", 373, Rarity.RARE, mage.cards.r.RescueRetriever.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Retrieval Agent", 60, Rarity.COMMON, mage.cards.r.RetrievalAgent.class));
        cards.add(new SetCardInfo("Roc Hunter", 150, Rarity.COMMON, mage.cards.r.RocHunter.class));
        cards.add(new SetCardInfo("Rootwire Amalgam", 203, Rarity.MYTHIC, mage.cards.r.RootwireAmalgam.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rootwire Amalgam", 351, Rarity.MYTHIC, mage.cards.r.RootwireAmalgam.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rust Goliath", 204, Rarity.COMMON, mage.cards.r.RustGoliath.class));
        cards.add(new SetCardInfo("Saheeli, Filigree Master", 219, Rarity.MYTHIC, mage.cards.s.SaheeliFiligreeMaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Saheeli, Filigree Master", 294, Rarity.MYTHIC, mage.cards.s.SaheeliFiligreeMaster.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sardian Cliffstomper", 151, Rarity.UNCOMMON, mage.cards.s.SardianCliffstomper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sardian Cliffstomper", 383, Rarity.UNCOMMON, mage.cards.s.SardianCliffstomper.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sarinth Greatwurm", 220, Rarity.MYTHIC, mage.cards.s.SarinthGreatwurm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sarinth Greatwurm", 358, Rarity.MYTHIC, mage.cards.s.SarinthGreatwurm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sarinth Steelseeker", 189, Rarity.UNCOMMON, mage.cards.s.SarinthSteelseeker.class));
        cards.add(new SetCardInfo("Scatter Ray", 61, Rarity.COMMON, mage.cards.s.ScatterRay.class));
        cards.add(new SetCardInfo("Scrapwork Cohort", 37, Rarity.COMMON, mage.cards.s.ScrapworkCohort.class));
        cards.add(new SetCardInfo("Scrapwork Mutt", 164, Rarity.COMMON, mage.cards.s.ScrapworkMutt.class));
        cards.add(new SetCardInfo("Scrapwork Rager", 123, Rarity.COMMON, mage.cards.s.ScrapworkRager.class));
        cards.add(new SetCardInfo("Shoot Down", 190, Rarity.COMMON, mage.cards.s.ShootDown.class));
        cards.add(new SetCardInfo("Sibling Rivalry", 152, Rarity.COMMON, mage.cards.s.SiblingRivalry.class));
        cards.add(new SetCardInfo("Siege Veteran", 25, Rarity.RARE, mage.cards.s.SiegeVeteran.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Siege Veteran", 306, Rarity.RARE, mage.cards.s.SiegeVeteran.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Simian Simulacrum", 205, Rarity.RARE, mage.cards.s.SimianSimulacrum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Simian Simulacrum", 352, Rarity.RARE, mage.cards.s.SimianSimulacrum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skitterbeam Battalion", 165, Rarity.MYTHIC, mage.cards.s.SkitterbeamBattalion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skitterbeam Battalion", 343, Rarity.MYTHIC, mage.cards.s.SkitterbeamBattalion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skyfisher Spider", 221, Rarity.UNCOMMON, mage.cards.s.SkyfisherSpider.class));
        cards.add(new SetCardInfo("Skystrike Officer", 317, Rarity.RARE, mage.cards.s.SkystrikeOfficer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Skystrike Officer", 62, Rarity.RARE, mage.cards.s.SkystrikeOfficer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Slagstone Refinery", 243, Rarity.UNCOMMON, mage.cards.s.SlagstoneRefinery.class));
        cards.add(new SetCardInfo("Soul Partition", 26, Rarity.RARE, mage.cards.s.SoulPartition.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Soul Partition", 307, Rarity.RARE, mage.cards.s.SoulPartition.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spectrum Sentinel", 244, Rarity.UNCOMMON, mage.cards.s.SpectrumSentinel.class));
        cards.add(new SetCardInfo("Splitting the Powerstone", 63, Rarity.UNCOMMON, mage.cards.s.SplittingThePowerstone.class));
        cards.add(new SetCardInfo("Spotter Thopter", 80, Rarity.UNCOMMON, mage.cards.s.SpotterThopter.class));
        cards.add(new SetCardInfo("Static Net", 27, Rarity.UNCOMMON, mage.cards.s.StaticNet.class));
        cards.add(new SetCardInfo("Steel Exemplar", 246, Rarity.UNCOMMON, mage.cards.s.SteelExemplar.class));
        cards.add(new SetCardInfo("Steel Seraph", 311, Rarity.RARE, mage.cards.s.SteelSeraph.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Steel Seraph", 38, Rarity.RARE, mage.cards.s.SteelSeraph.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stern Lesson", 64, Rarity.COMMON, mage.cards.s.SternLesson.class));
        cards.add(new SetCardInfo("Stone Retrieval Unit", 248, Rarity.COMMON, mage.cards.s.StoneRetrievalUnit.class));
        cards.add(new SetCardInfo("Su-Chi Cave Guard", 249, Rarity.UNCOMMON, mage.cards.s.SuChiCaveGuard.class));
        cards.add(new SetCardInfo("Supply Drop", 250, Rarity.COMMON, mage.cards.s.SupplyDrop.class));
        cards.add(new SetCardInfo("Surge Engine", 320, Rarity.MYTHIC, mage.cards.s.SurgeEngine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Surge Engine", 81, Rarity.MYTHIC, mage.cards.s.SurgeEngine.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Survivor of Korlis", 28, Rarity.COMMON, mage.cards.s.SurvivorOfKorlis.class));
        cards.add(new SetCardInfo("Swamp", 272, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 273, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 282, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 283, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swiftgear Drake", 251, Rarity.COMMON, mage.cards.s.SwiftgearDrake.class));
        cards.add(new SetCardInfo("Symmetry Matrix", 252, Rarity.UNCOMMON, mage.cards.s.SymmetryMatrix.class));
        cards.add(new SetCardInfo("Take Flight", 65, Rarity.UNCOMMON, mage.cards.t.TakeFlight.class));
        cards.add(new SetCardInfo("Tawnos's Tinkering", 191, Rarity.COMMON, mage.cards.t.TawnossTinkering.class));
        cards.add(new SetCardInfo("Tawnos, the Toymaker", 222, Rarity.RARE, mage.cards.t.TawnosTheToymaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tawnos, the Toymaker", 359, Rarity.RARE, mage.cards.t.TawnosTheToymaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teething Wurmlet", 192, Rarity.RARE, mage.cards.t.TeethingWurmlet.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teething Wurmlet", 348, Rarity.RARE, mage.cards.t.TeethingWurmlet.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teferi, Temporal Pilgrim", 293, Rarity.MYTHIC, mage.cards.t.TeferiTemporalPilgrim.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teferi, Temporal Pilgrim", 66, Rarity.MYTHIC, mage.cards.t.TeferiTemporalPilgrim.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terisian Mindbreaker", 322, Rarity.RARE, mage.cards.t.TerisianMindbreaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terisian Mindbreaker", 83, Rarity.RARE, mage.cards.t.TerisianMindbreaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terror Ballista", 290, Rarity.RARE, mage.cards.t.TerrorBallista.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terror Ballista", 375, Rarity.RARE, mage.cards.t.TerrorBallista.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Fall of Kroog", 133, Rarity.UNCOMMON, mage.cards.t.TheFallOfKroog.class));
        cards.add(new SetCardInfo("The Mightstone and Weakstone", "238a", Rarity.RARE, mage.cards.t.TheMightstoneAndWeakstone.class));
        cards.add(new SetCardInfo("The Stasis Coffin", 245, Rarity.RARE, mage.cards.t.TheStasisCoffin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Stasis Coffin", 366, Rarity.RARE, mage.cards.t.TheStasisCoffin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Stone Brain", 247, Rarity.RARE, mage.cards.t.TheStoneBrain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Stone Brain", 367, Rarity.RARE, mage.cards.t.TheStoneBrain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Temporal Anchor", 321, Rarity.RARE, mage.cards.t.TheTemporalAnchor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Temporal Anchor", 82, Rarity.RARE, mage.cards.t.TheTemporalAnchor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Third Path Iconoclast", 223, Rarity.UNCOMMON, mage.cards.t.ThirdPathIconoclast.class));
        cards.add(new SetCardInfo("Third Path Savant", 67, Rarity.COMMON, mage.cards.t.ThirdPathSavant.class));
        cards.add(new SetCardInfo("Thopter Architect", 29, Rarity.UNCOMMON, mage.cards.t.ThopterArchitect.class));
        cards.add(new SetCardInfo("Thopter Mechanic", 68, Rarity.UNCOMMON, mage.cards.t.ThopterMechanic.class));
        cards.add(new SetCardInfo("Thran Power Suit", 253, Rarity.UNCOMMON, mage.cards.t.ThranPowerSuit.class));
        cards.add(new SetCardInfo("Thran Spider", 254, Rarity.RARE, mage.cards.t.ThranSpider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thran Spider", 368, Rarity.RARE, mage.cards.t.ThranSpider.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thran Vigil", 114, Rarity.UNCOMMON, mage.cards.t.ThranVigil.class));
        cards.add(new SetCardInfo("Thraxodemon", 115, Rarity.COMMON, mage.cards.t.Thraxodemon.class));
        cards.add(new SetCardInfo("Titania's Command", 194, Rarity.RARE, mage.cards.t.TitaniasCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Titania's Command", 349, Rarity.RARE, mage.cards.t.TitaniasCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Titania, Gaea Incarnate", "256b", Rarity.MYTHIC, mage.cards.t.TitaniaGaeaIncarnate.class));
        cards.add(new SetCardInfo("Titania, Voice of Gaea", 193, Rarity.MYTHIC, mage.cards.t.TitaniaVoiceOfGaea.class));
        cards.add(new SetCardInfo("Tocasia's Dig Site", 266, Rarity.COMMON, mage.cards.t.TocasiasDigSite.class));
        cards.add(new SetCardInfo("Tocasia's Onulet", 39, Rarity.COMMON, mage.cards.t.TocasiasOnulet.class));
        cards.add(new SetCardInfo("Tocasia's Welcome", 30, Rarity.RARE, mage.cards.t.TocasiasWelcome.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tocasia's Welcome", 308, Rarity.RARE, mage.cards.t.TocasiasWelcome.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tocasia, Dig Site Mentor", 224, Rarity.RARE, mage.cards.t.TocasiaDigSiteMentor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tocasia, Dig Site Mentor", 360, Rarity.RARE, mage.cards.t.TocasiaDigSiteMentor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tomakul Honor Guard", 195, Rarity.COMMON, mage.cards.t.TomakulHonorGuard.class));
        cards.add(new SetCardInfo("Tomakul Scrapsmith", 153, Rarity.COMMON, mage.cards.t.TomakulScrapsmith.class));
        cards.add(new SetCardInfo("Tower Worker", 255, Rarity.COMMON, mage.cards.t.TowerWorker.class));
        cards.add(new SetCardInfo("Transmogrant Altar", 124, Rarity.UNCOMMON, mage.cards.t.TransmograntAltar.class));
        cards.add(new SetCardInfo("Transmogrant's Crown", 125, Rarity.RARE, mage.cards.t.TransmograntsCrown.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Transmogrant's Crown", 334, Rarity.RARE, mage.cards.t.TransmograntsCrown.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Trench Stalker", 116, Rarity.COMMON, mage.cards.t.TrenchStalker.class));
        cards.add(new SetCardInfo("Tyrant of Kher Ridges", 154, Rarity.RARE, mage.cards.t.TyrantOfKherRidges.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tyrant of Kher Ridges", 341, Rarity.RARE, mage.cards.t.TyrantOfKherRidges.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Underground River", 267, Rarity.RARE, mage.cards.u.UndergroundRiver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Underground River", 300, Rarity.RARE, mage.cards.u.UndergroundRiver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Union of the Third Path", 31, Rarity.COMMON, mage.cards.u.UnionOfTheThirdPath.class));
        cards.add(new SetCardInfo("Unleash Shell", 155, Rarity.COMMON, mage.cards.u.UnleashShell.class));
        cards.add(new SetCardInfo("Urza's Command", 318, Rarity.RARE, mage.cards.u.UrzasCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Command", 70, Rarity.RARE, mage.cards.u.UrzasCommand.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Rebuff", 71, Rarity.COMMON, mage.cards.u.UrzasRebuff.class));
        cards.add(new SetCardInfo("Urza's Sylex", 312, Rarity.MYTHIC, mage.cards.u.UrzasSylex.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza's Sylex", 40, Rarity.MYTHIC, mage.cards.u.UrzasSylex.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza, Lord Protector", 225, Rarity.MYTHIC, mage.cards.u.UrzaLordProtector.class));
        cards.add(new SetCardInfo("Urza, Planeswalker", "238b", Rarity.MYTHIC, mage.cards.u.UrzaPlaneswalker.class));
        cards.add(new SetCardInfo("Urza, Powerstone Prodigy", 69, Rarity.UNCOMMON, mage.cards.u.UrzaPowerstoneProdigy.class));
        cards.add(new SetCardInfo("Urza, Prince of Kroog", 226, Rarity.RARE, mage.cards.u.UrzaPrinceOfKroog.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Urza, Prince of Kroog", 296, Rarity.RARE, mage.cards.u.UrzaPrinceOfKroog.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Veteran's Powerblade", 41, Rarity.COMMON, mage.cards.v.VeteransPowerblade.class));
        cards.add(new SetCardInfo("Visions of Phyrexia", 156, Rarity.RARE, mage.cards.v.VisionsOfPhyrexia.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Visions of Phyrexia", 342, Rarity.RARE, mage.cards.v.VisionsOfPhyrexia.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Warlord's Elite", 32, Rarity.COMMON, mage.cards.w.WarlordsElite.class));
        cards.add(new SetCardInfo("Wasteful Harvest", 196, Rarity.COMMON, mage.cards.w.WastefulHarvest.class));
        cards.add(new SetCardInfo("Weakstone's Subjugation", 72, Rarity.COMMON, mage.cards.w.WeakstonesSubjugation.class));
        cards.add(new SetCardInfo("Whirling Strike", 157, Rarity.COMMON, mage.cards.w.WhirlingStrike.class));
        cards.add(new SetCardInfo("Wing Commando", 73, Rarity.COMMON, mage.cards.w.WingCommando.class));
        cards.add(new SetCardInfo("Woodcaller Automaton", 292, Rarity.RARE, mage.cards.w.WoodcallerAutomaton.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Woodcaller Automaton", 377, Rarity.RARE, mage.cards.w.WoodcallerAutomaton.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yotian Dissident", 227, Rarity.UNCOMMON, mage.cards.y.YotianDissident.class));
        cards.add(new SetCardInfo("Yotian Frontliner", 42, Rarity.UNCOMMON, mage.cards.y.YotianFrontliner.class));
        cards.add(new SetCardInfo("Yotian Medic", 33, Rarity.COMMON, mage.cards.y.YotianMedic.class));
        cards.add(new SetCardInfo("Yotian Tactician", 228, Rarity.UNCOMMON, mage.cards.y.YotianTactician.class));
        cards.add(new SetCardInfo("Zephyr Sentinel", 74, Rarity.UNCOMMON, mage.cards.z.ZephyrSentinel.class));
    }

    @Override
    protected void addSpecialCards(List<Card> booster, int number) {
        // number is here always 1
        // Boosters have one card from BRR, odds are 2/3 for uncommon, 4/15 for rare, 1/15 for mythic
        Rarity rarity;
        int rarityKey = RandomUtil.nextInt(15);
        if (rarityKey == 14) {
            rarity = Rarity.MYTHIC;
        } else if (rarityKey >= 10) {
            rarity = Rarity.RARE;
        } else {
            rarity = Rarity.UNCOMMON;
        }
        addToBooster(booster, TheBrothersWarRetroArtifacts.getInstance().getCardsByRarity(rarity));
    }

    @Override
    protected void generateBoosterMap() {
        super.generateBoosterMap();
        CardRepository
                .instance
                .findCards(new CardCriteria().setCodes("BRR"))
                .stream()
                .forEach(cardInfo -> inBoosterMap.put("BRR_" + cardInfo.getCardNumber(), cardInfo));
    }

    @Override
    public BoosterCollator createCollator() {
        return new TheBrothersWarCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/bro.html
// Using Japanese collation
class TheBrothersWarCollator implements BoosterCollator {

    private final CardRun commonA = new CardRun(true, "41", "78", "61", "204", "71", "248", "44", "255", "46", "6", "76", "261", "78", "41", "56", "129", "57", "33", "55", "173", "48", "150", "60", "168", "67", "120", "43", "250", "77", "55", "41", "44", "261", "57", "168", "71", "150", "67", "248", "60", "6", "77", "204", "46", "250", "43", "33", "56", "255", "76", "129", "48", "173", "78", "120", "61", "44", "250", "46", "33", "76", "255", "57", "129", "55", "6", "67", "150", "71", "204", "60", "41", "48", "168", "78", "261", "43", "120", "56", "248", "77", "173", "71", "55", "168", "61", "150", "44", "204", "56", "250", "60", "33", "48", "248", "61", "120", "77", "6", "46", "261", "67", "173", "57", "255", "43", "129", "76");

    private final CardRun commonB = new CardRun(true, "132", "108", "231", "147", "87", "186", "149", "106", "234", "155", "98", "190", "161", "85", "251", "164", "112", "5", "152", "113", "106", "143", "239", "85", "161", "155", "96", "186", "164", "113", "266", "127", "118", "5", "142", "98", "72", "131", "110", "234", "152", "112", "251", "137", "123", "241", "147", "108", "39", "149", "93", "190", "132", "87", "231", "158", "115", "98", "127", "161", "93", "251", "131", "118", "266", "164", "110", "239", "152", "123", "72", "147", "115", "231", "142", "106", "241", "137", "85", "190", "155", "87", "234", "158", "96", "5", "143", "108", "186", "132", "113", "39", "127", "112", "149", "123", "158", "93", "239", "137", "110", "39", "131", "115", "266", "142", "96", "241", "143", "118", "72");

    private final CardRun commonC = new CardRun(true, "197", "3", "58", "172", "19", "116", "180", "37", "235", "176", "22", "196", "32", "182", "20", "100", "184", "19", "235", "180", "21", "134", "172", "1", "119", "167", "17", "64", "175", "28", "91", "191", "31", "58", "195", "20", "157", "183", "3", "100", "188", "22", "153", "176", "14", "73", "197", "2", "195", "1", "175", "21", "119", "180", "20", "153", "188", "31", "73", "191", "17", "91", "172", "28", "58", "182", "37", "134", "195", "2", "235", "183", "22", "116", "167", "14", "64", "197", "32", "100", "175", "19", "196", "3", "73", "184", "17", "188", "14", "157", "176", "28", "64", "196", "2", "153", "167", "31", "119", "183", "1", "134", "182", "21", "116", "184", "37", "157", "191", "32", "91");

    private final CardRun commonL = new CardRun(false, "278", "279", "280", "281", "282", "283", "284", "285", "286", "287");

    private final CardRun uncommonA = new CardRun(true, "15", "7", "236", "24", "11", "13", "232", "23", "42", "29", "7", "232", "242", "35", "24", "236", "246", "29", "23", "15", "16", "35", "243", "13", "42", "15", "236", "242", "16", "13", "29", "11", "23", "232", "7", "42", "11", "24", "246", "243", "242", "11", "35", "16", "246", "15", "29", "243", "42", "7", "13", "236", "23", "24", "232", "42", "246", "13", "7", "35", "29", "236", "232", "15", "23", "11", "242", "24", "16", "232", "243", "11", "29", "24", "13", "23", "16", "243", "7", "242", "246", "35", "236", "42", "13", "15", "246", "242", "7", "24", "243", "16", "29", "232", "35", "23", "246", "236", "16", "15", "11", "42", "243", "35", "242");

    private final CardRun uncommonB = new CardRun(true, "214", "201", "213", "162", "169", "151", "65", "109", "80", "252", "117", "227", "124", "162", "174", "160", "214", "159", "244", "104", "228", "166", "69", "171", "80", "201", "221", "252", "54", "178", "114", "169", "213", "148", "79", "166", "109", "171", "117", "159", "124", "214", "65", "228", "148", "244", "201", "227", "104", "169", "221", "160", "54", "174", "80", "162", "79", "151", "178", "213", "69", "252", "114", "166", "124", "227", "162", "201", "80", "160", "65", "244", "114", "252", "221", "178", "214", "148", "117", "171", "79", "174", "54", "151", "228", "69", "213", "169", "109", "159", "104", "174", "114", "244", "178", "69", "124", "151", "117", "228", "54", "159", "65", "171", "227", "79", "109", "160", "221", "148", "104", "166");

    private final CardRun uncommonC = new CardRun(true, "209", "27", "126", "206", "86", "260", "138", "198", "49", "200", "210", "189", "68", "90", "249", "187", "133", "63", "206", "50", "88", "4", "181", "86", "126", "198", "90", "145", "253", "140", "74", "260", "103", "53", "210", "133", "223", "209", "138", "63", "199", "92", "45", "102", "144", "27", "189", "101", "200", "49", "249", "68", "207", "199", "260", "136", "198", "138", "74", "4", "49", "102", "140", "68", "189", "145", "101", "27", "206", "88", "133", "187", "253", "223", "53", "90", "126", "207", "103", "50", "200", "144", "181", "210", "86", "63", "209", "136", "249", "45", "92", "187", "4", "253", "88", "50", "207", "144", "223", "45", "101", "145", "53", "140", "102", "181", "103", "74", "199", "136", "92");

    private final CardRun rareMythic = new CardRun(false, "26", "9", "10", "12", "25", "30", "47", "51", "62", "70", "82", "52", "84", "89", "107", "94", "99", "105", "97", "111", "141", "135", "128", "139", "156", "154", "146", "192", "179", "185", "177", "194", "212", "226", "217", "211", "215", "208", "218", "222", "224", "34", "38", "83", "122", "125", "163a", "205", "202", "247", "237", "245", "254", "238a", "256a", "262", "267", "259", "264", "257", "258", "263", "265",
            "26", "9", "10", "12", "25", "30", "47", "51", "62", "70", "82", "52", "84", "89", "107", "94", "99", "105", "97", "111", "141", "135", "128", "139", "156", "154", "146", "192", "179", "185", "177", "194", "212", "226", "217", "211", "215", "208", "218", "222", "224", "34", "38", "83", "122", "125", "163a", "205", "202", "247", "237", "245", "254", "238a", "256a", "262", "267", "259", "264", "257", "258", "263", "265",
            "8", "18", "66", "59", "95", "130", "170", "193", "225", "216", "220", "219", "40", "36", "81", "75", "121", "165", "203", "230", "229", "233", "240");

    private final CardRun uncommonArchive = new CardRun(true, "24", "15", "23", "7", "53", "71", "54", "16", "58", "32", "37", "84", "11", "28", "55", "21", "43", "121", "34", "51", "8", "15", "32", "118", "54", "24", "23", "16", "7", "116", "21", "11", "34", "58", "53", "100", "8", "51", "37", "43", "55", "114", "28", "24", "21", "16", "32", "91", "11", "54", "15", "37", "7", "78", "51", "53", "43", "58", "23", "117", "34", "28", "8", "55", "24", "79", "37", "21", "15", "53", "16", "70", "23", "34", "54", "58", "28", "97", "43", "7", "51", "55", "8", "74", "21", "32", "11", "53", "54", "106", "28", "23", "24", "7", "15", "87", "16", "51", "58", "55", "32", "86", "34", "11", "43", "37", "8", "95");

    private final CardRun rareArchive = new CardRun(false, "1", "4", "5", "6", "10", "12", "13", "14", "17", "18", "20", "22", "25", "26", "27", "29", "30", "40", "42", "44", "45", "46", "48", "49", "50", "52", "59", "60", "61", "62",
            "1", "4", "5", "6", "10", "12", "13", "14", "17", "18", "20", "22", "25", "26", "27", "29", "30", "40", "42", "44", "45", "46", "48", "49", "50", "52", "59", "60", "61", "62",
            "2", "3", "9", "19", "31", "33", "35", "36", "38", "39", "41", "47", "56", "57", "63",
            "1", "4", "5", "6", "10", "12", "13", "14", "17", "18", "20", "22", "25", "26", "27", "29", "30", "40", "42", "44", "45", "46", "48", "49", "50", "52", "59", "60", "61", "62",
            "1", "4", "5", "6", "10", "12", "13", "14", "17", "18", "20", "22", "25", "26", "27", "29", "30", "40", "42", "44", "45", "46", "48", "49", "50", "52", "59", "60", "61", "62",
            "2", "3", "9", "19", "31", "33", "35", "36", "38", "39", "41", "47", "56", "57", "63",
            "1", "4", "5", "6", "10", "12", "13", "14", "17", "18", "20", "22", "25", "26", "27", "29", "30", "40", "42", "44", "45", "46", "48", "49", "50", "52", "59", "60", "61", "62",
            "1", "4", "5", "6", "10", "12", "13", "14", "17", "18", "20", "22", "25", "26", "27", "29", "30", "40", "42", "44", "45", "46", "48", "49", "50", "52", "59", "60", "61", "62",
            "2", "3", "9", "19", "31", "33", "35", "36", "38", "39", "41", "47", "56", "57", "63",
            "1", "4", "5", "6", "10", "12", "13", "14", "17", "18", "20", "22", "25", "26", "27", "29", "30", "40", "42", "44", "45", "46", "48", "49", "50", "52", "59", "60", "61", "62",
            "1", "4", "5", "6", "10", "12", "13", "14", "17", "18", "20", "22", "25", "26", "27", "29", "30", "40", "42", "44", "45", "46", "48", "49", "50", "52", "59", "60", "61", "62",
            "2", "3", "9", "19", "31", "33", "35", "36", "38", "39", "41", "47", "56", "57", "63",
            "1", "4", "5", "6", "10", "12", "13", "14", "17", "18", "20", "22", "25", "26", "27", "29", "30", "40", "42", "44", "45", "46", "48", "49", "50", "52", "59", "60", "61", "62",
            "1", "4", "5", "6", "10", "12", "13", "14", "17", "18", "20", "22", "25", "26", "27", "29", "30", "40", "42", "44", "45", "46", "48", "49", "50", "52", "59", "60", "61", "62",
            "2", "3", "9", "19", "31", "33", "35", "36", "38", "39", "41", "47", "56", "57", "63",
            "64", "67", "68", "69", "73", "75", "76", "77", "80", "81", "83", "85", "88", "89", "90", "92", "93", "103", "105", "107", "108", "109", "111", "112", "113", "115", "122", "123", "124", "125",
            "64", "67", "68", "69", "73", "75", "76", "77", "80", "81", "83", "85", "88", "89", "90", "92", "93", "103", "105", "107", "108", "109", "111", "112", "113", "115", "122", "123", "124", "125",
            "65", "66", "72", "82", "94", "96", "98", "99", "101", "102", "104", "110", "119", "120", "126");

    private final BoosterStructure AABBBBCCCC = new BoosterStructure(
            commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC, commonC, commonC, commonC
    );

    private final BoosterStructure AAABBBCCCC = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC, commonC, commonC, commonC
    );

    private final BoosterStructure AAABBBBCCC = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC, commonC, commonC
    );

    private final BoosterStructure LAABBBCCCC = new BoosterStructure(
            commonL,
            commonA, commonA,
            commonB, commonB, commonB,
            commonC, commonC, commonC, commonC
    );

    private final BoosterStructure LAABBBBCCC = new BoosterStructure(
            commonL,
            commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC, commonC, commonC
    );

    private final BoosterStructure LAAABBBCCC = new BoosterStructure(
            commonL,
            commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC, commonC, commonC
    );

    private final BoosterStructure ABC = new BoosterStructure(
            uncommonA, uncommonB, uncommonC // 45/80
    );

    private final BoosterStructure BBC = new BoosterStructure(
            uncommonB, uncommonB, uncommonC // 4/80
    );

    private final BoosterStructure BCC = new BoosterStructure(
            uncommonB, uncommonC, uncommonC // 31/80
    );

    private final BoosterStructure R1 = new BoosterStructure(rareMythic);
    private final BoosterStructure A1 = new BoosterStructure(uncommonArchive); // 1/6 are schematic alt art
    private final BoosterStructure A2 = new BoosterStructure(rareArchive); // 1/6 are schematic alt art

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 2.67 A commons (270 / 101)
    // 3.66 B commons (370 / 101)
    // 3.66 C commons (370 / 101)
    // Approximately 1 in 4 boosters contains a basic land instead of a common
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, LAABBBCCCC, LAABBBBCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, LAABBBCCCC, LAABBBBCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, LAABBBCCCC, LAABBBBCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, LAABBBCCCC, LAABBBBCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, AABBBBCCCC, LAABBBCCCC, LAABBBBCCC,
            AABBBBCCCC, AABBBBCCCC, AABBBBCCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, LAABBBCCCC, AAABBBCCCC, LAAABBBCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, LAABBBCCCC, AAABBBCCCC, LAAABBBCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, LAABBBCCCC, AAABBBCCCC, LAAABBBCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, LAAABBBCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, LAAABBBCCC,
            AAABBBCCCC, AAABBBCCCC, AAABBBCCCC, AAABBBCCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, LAABBBBCCC, LAAABBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, LAABBBBCCC, LAAABBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, LAABBBBCCC, LAAABBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, LAABBBBCCC, LAAABBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, LAAABBBCCC, AAABBBBCCC,
            AAABBBBCCC, AAABBBBCCC, AAABBBBCCC, AAABBBBCCC,
            LAABBBCCCC,
            LAABBBBCCC,
            LAAABBBCCC
            // one of each land common run adds 7A, 10B, 10C
            // therefore replace 7A, 10B, 10C with lands to compensate
            // total is now 30/104 packs have lands (slightly higher than 1/4 since no foils)
            // but still preserves 27A:37B:37C ratio
    );

    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            ABC, ABC, ABC, ABC, ABC, ABC, ABC, ABC, ABC, ABC,
            ABC, ABC, ABC, ABC, ABC, ABC, ABC, ABC, ABC, ABC,
            ABC, ABC, ABC, ABC, ABC, ABC, ABC, ABC, ABC, ABC,
            ABC, ABC, ABC, ABC, ABC, ABC, ABC, ABC, ABC, ABC,
            ABC, ABC, ABC, ABC, ABC, BBC, BBC, BBC, BBC, BCC,
            BCC, BCC, BCC, BCC, BCC, BCC, BCC, BCC, BCC, BCC,
            BCC, BCC, BCC, BCC, BCC, BCC, BCC, BCC, BCC, BCC,
            BCC, BCC, BCC, BCC, BCC, BCC, BCC, BCC, BCC, BCC
            );

    private final RarityConfiguration rareRuns = new RarityConfiguration(R1);

    private final RarityConfiguration archiveRuns = new RarityConfiguration(A1, A1, A2);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        archiveRuns.getNext().makeRun().stream().map(s -> "BRR_" + s).forEach(booster::add);
        return booster;
    }

}
