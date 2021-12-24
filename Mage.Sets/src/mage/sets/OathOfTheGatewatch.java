package mage.sets;

import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.List;

/**
 * @author fireshoes
 */
public final class OathOfTheGatewatch extends ExpansionSet {

    private static final OathOfTheGatewatch instance = new OathOfTheGatewatch();

    public static OathOfTheGatewatch getInstance() {
        return instance;
    }

    private OathOfTheGatewatch() {
        super("Oath of the Gatewatch", "OGW", ExpansionSet.buildDate(2016, 1, 22), SetType.EXPANSION);
        this.blockName = "Battle for Zendikar";
        this.parentSet = BattleForZendikar.getInstance();
        this.hasBoosters = true;
        this.hasBasicLands = false;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.ratioBoosterSpecialCommon = 144;

        cards.add(new SetCardInfo("Abstruse Interference", 40, Rarity.COMMON, mage.cards.a.AbstruseInterference.class));
        cards.add(new SetCardInfo("Affa Protector", 14, Rarity.COMMON, mage.cards.a.AffaProtector.class));
        cards.add(new SetCardInfo("Akoum Flameseeker", 101, Rarity.COMMON, mage.cards.a.AkoumFlameseeker.class));
        cards.add(new SetCardInfo("Allied Reinforcements", 15, Rarity.UNCOMMON, mage.cards.a.AlliedReinforcements.class));
        cards.add(new SetCardInfo("Ancient Crab", 50, Rarity.COMMON, mage.cards.a.AncientCrab.class));
        cards.add(new SetCardInfo("Ayli, Eternal Pilgrim", 151, Rarity.RARE, mage.cards.a.AyliEternalPilgrim.class));
        cards.add(new SetCardInfo("Baloth Null", 152, Rarity.UNCOMMON, mage.cards.b.BalothNull.class));
        cards.add(new SetCardInfo("Baloth Pup", 127, Rarity.UNCOMMON, mage.cards.b.BalothPup.class));
        cards.add(new SetCardInfo("Bearer of Silence", 67, Rarity.RARE, mage.cards.b.BearerOfSilence.class));
        cards.add(new SetCardInfo("Birthing Hulk", 121, Rarity.UNCOMMON, mage.cards.b.BirthingHulk.class));
        cards.add(new SetCardInfo("Blinding Drone", 41, Rarity.COMMON, mage.cards.b.BlindingDrone.class));
        cards.add(new SetCardInfo("Bonds of Mortality", 128, Rarity.UNCOMMON, mage.cards.b.BondsOfMortality.class));
        cards.add(new SetCardInfo("Bone Saw", 161, Rarity.COMMON, mage.cards.b.BoneSaw.class));
        cards.add(new SetCardInfo("Boulder Salvo", 102, Rarity.COMMON, mage.cards.b.BoulderSalvo.class));
        cards.add(new SetCardInfo("Brute Strength", 103, Rarity.COMMON, mage.cards.b.BruteStrength.class));
        cards.add(new SetCardInfo("Call the Gatewatch", 16, Rarity.RARE, mage.cards.c.CallTheGatewatch.class));
        cards.add(new SetCardInfo("Canopy Gorger", 129, Rarity.COMMON, mage.cards.c.CanopyGorger.class));
        cards.add(new SetCardInfo("Captain's Claws", "162+", Rarity.RARE, mage.cards.c.CaptainsClaws.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain's Claws", 162, Rarity.RARE, mage.cards.c.CaptainsClaws.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chandra, Flamecaller", 104, Rarity.MYTHIC, mage.cards.c.ChandraFlamecaller.class));
        cards.add(new SetCardInfo("Chitinous Cloak", 163, Rarity.UNCOMMON, mage.cards.c.ChitinousCloak.class));
        cards.add(new SetCardInfo("Cinder Barrens", 168, Rarity.UNCOMMON, mage.cards.c.CinderBarrens.class));
        cards.add(new SetCardInfo("Cinder Hellion", 105, Rarity.COMMON, mage.cards.c.CinderHellion.class));
        cards.add(new SetCardInfo("Cliffhaven Vampire", 153, Rarity.UNCOMMON, mage.cards.c.CliffhavenVampire.class));
        cards.add(new SetCardInfo("Comparative Analysis", 51, Rarity.COMMON, mage.cards.c.ComparativeAnalysis.class));
        cards.add(new SetCardInfo("Consuming Sinkhole", 94, Rarity.COMMON, mage.cards.c.ConsumingSinkhole.class));
        cards.add(new SetCardInfo("Containment Membrane", 52, Rarity.COMMON, mage.cards.c.ContainmentMembrane.class));
        cards.add(new SetCardInfo("Corpse Churn", 83, Rarity.COMMON, mage.cards.c.CorpseChurn.class));
        cards.add(new SetCardInfo("Corrupted Crossroads", 169, Rarity.RARE, mage.cards.c.CorruptedCrossroads.class));
        cards.add(new SetCardInfo("Crumbling Vestige", 170, Rarity.COMMON, mage.cards.c.CrumblingVestige.class));
        cards.add(new SetCardInfo("Crush of Tentacles", 53, Rarity.MYTHIC, mage.cards.c.CrushOfTentacles.class));
        cards.add(new SetCardInfo("Cultivator Drone", 42, Rarity.COMMON, mage.cards.c.CultivatorDrone.class));
        cards.add(new SetCardInfo("Cyclone Sire", 54, Rarity.UNCOMMON, mage.cards.c.CycloneSire.class));
        cards.add(new SetCardInfo("Dazzling Reflection", 17, Rarity.COMMON, mage.cards.d.DazzlingReflection.class));
        cards.add(new SetCardInfo("Deceiver of Form", 1, Rarity.RARE, mage.cards.d.DeceiverOfForm.class));
        cards.add(new SetCardInfo("Deepfathom Skulker", 43, Rarity.RARE, mage.cards.d.DeepfathomSkulker.class));
        cards.add(new SetCardInfo("Devour in Flames", 106, Rarity.UNCOMMON, mage.cards.d.DevourInFlames.class));
        cards.add(new SetCardInfo("Dimensional Infiltrator", 44, Rarity.RARE, mage.cards.d.DimensionalInfiltrator.class));
        cards.add(new SetCardInfo("Drana's Chosen", 84, Rarity.RARE, mage.cards.d.DranasChosen.class));
        cards.add(new SetCardInfo("Dread Defiler", 68, Rarity.RARE, mage.cards.d.DreadDefiler.class));
        cards.add(new SetCardInfo("Eldrazi Aggressor", 95, Rarity.COMMON, mage.cards.e.EldraziAggressor.class));
        cards.add(new SetCardInfo("Eldrazi Displacer", 13, Rarity.RARE, mage.cards.e.EldraziDisplacer.class));
        cards.add(new SetCardInfo("Eldrazi Mimic", 2, Rarity.RARE, mage.cards.e.EldraziMimic.class));
        cards.add(new SetCardInfo("Eldrazi Obligator", 96, Rarity.RARE, mage.cards.e.EldraziObligator.class));
        cards.add(new SetCardInfo("Elemental Uprising", 130, Rarity.COMMON, mage.cards.e.ElementalUprising.class));
        cards.add(new SetCardInfo("Embodiment of Fury", 107, Rarity.UNCOMMON, mage.cards.e.EmbodimentOfFury.class));
        cards.add(new SetCardInfo("Embodiment of Insight", 131, Rarity.UNCOMMON, mage.cards.e.EmbodimentOfInsight.class));
        cards.add(new SetCardInfo("Endbringer", 3, Rarity.RARE, mage.cards.e.Endbringer.class));
        cards.add(new SetCardInfo("Essence Depleter", 69, Rarity.UNCOMMON, mage.cards.e.EssenceDepleter.class));
        cards.add(new SetCardInfo("Expedite", 108, Rarity.COMMON, mage.cards.e.Expedite.class));
        cards.add(new SetCardInfo("Expedition Raptor", 18, Rarity.COMMON, mage.cards.e.ExpeditionRaptor.class));
        cards.add(new SetCardInfo("Fall of the Titans", 109, Rarity.RARE, mage.cards.f.FallOfTheTitans.class));
        cards.add(new SetCardInfo("Flayer Drone", 148, Rarity.UNCOMMON, mage.cards.f.FlayerDrone.class));
        cards.add(new SetCardInfo("Flaying Tendrils", 70, Rarity.UNCOMMON, mage.cards.f.FlayingTendrils.class));
        cards.add(new SetCardInfo("General Tazri", 19, Rarity.MYTHIC, mage.cards.g.GeneralTazri.class));
        cards.add(new SetCardInfo("Gift of Tusks", 55, Rarity.UNCOMMON, mage.cards.g.GiftOfTusks.class));
        cards.add(new SetCardInfo("Gladehart Cavalry", 132, Rarity.RARE, mage.cards.g.GladehartCavalry.class));
        cards.add(new SetCardInfo("Goblin Dark-Dwellers", 110, Rarity.RARE, mage.cards.g.GoblinDarkDwellers.class));
        cards.add(new SetCardInfo("Goblin Freerunner", 111, Rarity.COMMON, mage.cards.g.GoblinFreerunner.class));
        cards.add(new SetCardInfo("Grasp of Darkness", 85, Rarity.UNCOMMON, mage.cards.g.GraspOfDarkness.class));
        cards.add(new SetCardInfo("Gravity Negator", 45, Rarity.COMMON, mage.cards.g.GravityNegator.class));
        cards.add(new SetCardInfo("Grip of the Roil", 56, Rarity.UNCOMMON, mage.cards.g.GripOfTheRoil.class));
        cards.add(new SetCardInfo("Harvester Troll", 133, Rarity.UNCOMMON, mage.cards.h.HarvesterTroll.class));
        cards.add(new SetCardInfo("Havoc Sower", 71, Rarity.UNCOMMON, mage.cards.h.HavocSower.class));
        cards.add(new SetCardInfo("Hedron Alignment", 57, Rarity.RARE, mage.cards.h.HedronAlignment.class));
        cards.add(new SetCardInfo("Hedron Crawler", 164, Rarity.COMMON, mage.cards.h.HedronCrawler.class));
        cards.add(new SetCardInfo("Hissing Quagmire", 171, Rarity.RARE, mage.cards.h.HissingQuagmire.class));
        cards.add(new SetCardInfo("Holdout Settlement", 172, Rarity.COMMON, mage.cards.h.HoldoutSettlement.class));
        cards.add(new SetCardInfo("Immobilizer Eldrazi", 97, Rarity.UNCOMMON, mage.cards.i.ImmobilizerEldrazi.class));
        cards.add(new SetCardInfo("Immolating Glare", 20, Rarity.UNCOMMON, mage.cards.i.ImmolatingGlare.class));
        cards.add(new SetCardInfo("Inverter of Truth", 72, Rarity.MYTHIC, mage.cards.i.InverterOfTruth.class));
        cards.add(new SetCardInfo("Iona's Blessing", 21, Rarity.UNCOMMON, mage.cards.i.IonasBlessing.class));
        cards.add(new SetCardInfo("Isolation Zone", 22, Rarity.COMMON, mage.cards.i.IsolationZone.class));
        cards.add(new SetCardInfo("Joraga Auxiliary", 154, Rarity.UNCOMMON, mage.cards.j.JoragaAuxiliary.class));
        cards.add(new SetCardInfo("Jori En, Ruin Diver", 155, Rarity.RARE, mage.cards.j.JoriEnRuinDiver.class));
        cards.add(new SetCardInfo("Jwar Isle Avenger", 58, Rarity.COMMON, mage.cards.j.JwarIsleAvenger.class));
        cards.add(new SetCardInfo("Kalitas, Traitor of Ghet", 86, Rarity.MYTHIC, mage.cards.k.KalitasTraitorOfGhet.class));
        cards.add(new SetCardInfo("Kazuul's Toll Collector", 112, Rarity.UNCOMMON, mage.cards.k.KazuulsTollCollector.class));
        cards.add(new SetCardInfo("Kor Scythemaster", 23, Rarity.COMMON, mage.cards.k.KorScythemaster.class));
        cards.add(new SetCardInfo("Kor Sky Climber", 24, Rarity.COMMON, mage.cards.k.KorSkyClimber.class));
        cards.add(new SetCardInfo("Kozilek's Pathfinder", 5, Rarity.COMMON, mage.cards.k.KozileksPathfinder.class));
        cards.add(new SetCardInfo("Kozilek's Return", 98, Rarity.MYTHIC, mage.cards.k.KozileksReturn.class));
        cards.add(new SetCardInfo("Kozilek's Shrieker", 73, Rarity.COMMON, mage.cards.k.KozileksShrieker.class));
        cards.add(new SetCardInfo("Kozilek's Translator", 74, Rarity.COMMON, mage.cards.k.KozileksTranslator.class));
        cards.add(new SetCardInfo("Kozilek, the Great Distortion", 4, Rarity.MYTHIC, mage.cards.k.KozilekTheGreatDistortion.class));
        cards.add(new SetCardInfo("Lead by Example", 134, Rarity.COMMON, mage.cards.l.LeadByExample.class));
        cards.add(new SetCardInfo("Linvala, the Preserver", 25, Rarity.MYTHIC, mage.cards.l.LinvalaThePreserver.class));
        cards.add(new SetCardInfo("Loam Larva", 135, Rarity.COMMON, mage.cards.l.LoamLarva.class));
        cards.add(new SetCardInfo("Make a Stand", 26, Rarity.UNCOMMON, mage.cards.m.MakeAStand.class));
        cards.add(new SetCardInfo("Makindi Aeronaut", 27, Rarity.COMMON, mage.cards.m.MakindiAeronaut.class));
        cards.add(new SetCardInfo("Malakir Soothsayer", 87, Rarity.UNCOMMON, mage.cards.m.MalakirSoothsayer.class));
        cards.add(new SetCardInfo("Matter Reshaper", 6, Rarity.RARE, mage.cards.m.MatterReshaper.class));
        cards.add(new SetCardInfo("Maw of Kozilek", 99, Rarity.COMMON, mage.cards.m.MawOfKozilek.class));
        cards.add(new SetCardInfo("Meandering River", 173, Rarity.UNCOMMON, mage.cards.m.MeanderingRiver.class));
        cards.add(new SetCardInfo("Mighty Leap", 28, Rarity.COMMON, mage.cards.m.MightyLeap.class));
        cards.add(new SetCardInfo("Mina and Denn, Wildborn", 156, Rarity.RARE, mage.cards.m.MinaAndDennWildborn.class));
        cards.add(new SetCardInfo("Mindmelter", 149, Rarity.UNCOMMON, mage.cards.m.Mindmelter.class));
        cards.add(new SetCardInfo("Mirrorpool", 174, Rarity.MYTHIC, mage.cards.m.Mirrorpool.class));
        cards.add(new SetCardInfo("Munda's Vanguard", 29, Rarity.RARE, mage.cards.m.MundasVanguard.class));
        cards.add(new SetCardInfo("Natural State", 136, Rarity.COMMON, mage.cards.n.NaturalState.class));
        cards.add(new SetCardInfo("Needle Spires", 175, Rarity.RARE, mage.cards.n.NeedleSpires.class));
        cards.add(new SetCardInfo("Negate", 59, Rarity.COMMON, mage.cards.n.Negate.class));
        cards.add(new SetCardInfo("Netcaster Spider", 137, Rarity.COMMON, mage.cards.n.NetcasterSpider.class));
        cards.add(new SetCardInfo("Nissa's Judgment", 139, Rarity.UNCOMMON, mage.cards.n.NissasJudgment.class));
        cards.add(new SetCardInfo("Nissa, Voice of Zendikar", 138, Rarity.MYTHIC, mage.cards.n.NissaVoiceOfZendikar.class));
        cards.add(new SetCardInfo("Null Caller", 88, Rarity.UNCOMMON, mage.cards.n.NullCaller.class));
        cards.add(new SetCardInfo("Oath of Chandra", 113, Rarity.RARE, mage.cards.o.OathOfChandra.class));
        cards.add(new SetCardInfo("Oath of Gideon", 30, Rarity.RARE, mage.cards.o.OathOfGideon.class));
        cards.add(new SetCardInfo("Oath of Jace", 60, Rarity.RARE, mage.cards.o.OathOfJace.class));
        cards.add(new SetCardInfo("Oath of Nissa", 140, Rarity.RARE, mage.cards.o.OathOfNissa.class));
        cards.add(new SetCardInfo("Oblivion Strike", 75, Rarity.COMMON, mage.cards.o.OblivionStrike.class));
        cards.add(new SetCardInfo("Ondu War Cleric", 31, Rarity.COMMON, mage.cards.o.OnduWarCleric.class));
        cards.add(new SetCardInfo("Overwhelming Denial", 61, Rarity.RARE, mage.cards.o.OverwhelmingDenial.class));
        cards.add(new SetCardInfo("Press into Service", 114, Rarity.UNCOMMON, mage.cards.p.PressIntoService.class));
        cards.add(new SetCardInfo("Prophet of Distortion", 46, Rarity.UNCOMMON, mage.cards.p.ProphetOfDistortion.class));
        cards.add(new SetCardInfo("Pulse of Murasa", 141, Rarity.COMMON, mage.cards.p.PulseOfMurasa.class));
        cards.add(new SetCardInfo("Pyromancer's Assault", 115, Rarity.UNCOMMON, mage.cards.p.PyromancersAssault.class));
        cards.add(new SetCardInfo("Reality Hemorrhage", 100, Rarity.COMMON, mage.cards.r.RealityHemorrhage.class));
        cards.add(new SetCardInfo("Reality Smasher", 7, Rarity.RARE, mage.cards.r.RealitySmasher.class));
        cards.add(new SetCardInfo("Reaver Drone", 76, Rarity.UNCOMMON, mage.cards.r.ReaverDrone.class));
        cards.add(new SetCardInfo("Reckless Bushwhacker", 116, Rarity.UNCOMMON, mage.cards.r.RecklessBushwhacker.class));
        cards.add(new SetCardInfo("Reflector Mage", 157, Rarity.UNCOMMON, mage.cards.r.ReflectorMage.class));
        cards.add(new SetCardInfo("Relentless Hunter", 158, Rarity.UNCOMMON, mage.cards.r.RelentlessHunter.class));
        cards.add(new SetCardInfo("Relief Captain", 32, Rarity.UNCOMMON, mage.cards.r.ReliefCaptain.class));
        cards.add(new SetCardInfo("Remorseless Punishment", 89, Rarity.RARE, mage.cards.r.RemorselessPunishment.class));
        cards.add(new SetCardInfo("Roiling Waters", 62, Rarity.UNCOMMON, mage.cards.r.RoilingWaters.class));
        cards.add(new SetCardInfo("Ruin in Their Wake", 122, Rarity.UNCOMMON, mage.cards.r.RuinInTheirWake.class));
        cards.add(new SetCardInfo("Ruins of Oran-Rief", 176, Rarity.RARE, mage.cards.r.RuinsOfOranRief.class));
        cards.add(new SetCardInfo("Saddleback Lagac", 142, Rarity.COMMON, mage.cards.s.SaddlebackLagac.class));
        cards.add(new SetCardInfo("Scion Summoner", 123, Rarity.COMMON, mage.cards.s.ScionSummoner.class));
        cards.add(new SetCardInfo("Sea Gate Wreckage", 177, Rarity.RARE, mage.cards.s.SeaGateWreckage.class));
        cards.add(new SetCardInfo("Searing Light", 33, Rarity.COMMON, mage.cards.s.SearingLight.class));
        cards.add(new SetCardInfo("Seed Guardian", 143, Rarity.UNCOMMON, mage.cards.s.SeedGuardian.class));
        cards.add(new SetCardInfo("Seer's Lantern", 165, Rarity.COMMON, mage.cards.s.SeersLantern.class));
        cards.add(new SetCardInfo("Shoulder to Shoulder", 34, Rarity.COMMON, mage.cards.s.ShoulderToShoulder.class));
        cards.add(new SetCardInfo("Sifter of Skulls", 77, Rarity.RARE, mage.cards.s.SifterOfSkulls.class));
        cards.add(new SetCardInfo("Sky Scourer", 78, Rarity.COMMON, mage.cards.s.SkyScourer.class));
        cards.add(new SetCardInfo("Slaughter Drone", 79, Rarity.COMMON, mage.cards.s.SlaughterDrone.class));
        cards.add(new SetCardInfo("Slip Through Space", 47, Rarity.COMMON, mage.cards.s.SlipThroughSpace.class));
        cards.add(new SetCardInfo("Sparkmage's Gambit", 117, Rarity.COMMON, mage.cards.s.SparkmagesGambit.class));
        cards.add(new SetCardInfo("Spatial Contortion", 8, Rarity.UNCOMMON, mage.cards.s.SpatialContortion.class));
        cards.add(new SetCardInfo("Spawnbinder Mage", 35, Rarity.COMMON, mage.cards.s.SpawnbinderMage.class));
        cards.add(new SetCardInfo("Sphinx of the Final Word", 63, Rarity.MYTHIC, mage.cards.s.SphinxOfTheFinalWord.class));
        cards.add(new SetCardInfo("Stalking Drone", 124, Rarity.COMMON, mage.cards.s.StalkingDrone.class));
        cards.add(new SetCardInfo("Steppe Glider", 36, Rarity.UNCOMMON, mage.cards.s.SteppeGlider.class));
        cards.add(new SetCardInfo("Stone Haven Outfitter", 37, Rarity.RARE, mage.cards.s.StoneHavenOutfitter.class));
        cards.add(new SetCardInfo("Stoneforge Acolyte", 38, Rarity.UNCOMMON, mage.cards.s.StoneforgeAcolyte.class));
        cards.add(new SetCardInfo("Stoneforge Masterwork", 166, Rarity.RARE, mage.cards.s.StoneforgeMasterwork.class));
        cards.add(new SetCardInfo("Stormchaser Mage", 159, Rarity.UNCOMMON, mage.cards.s.StormchaserMage.class));
        cards.add(new SetCardInfo("Strider Harness", 167, Rarity.UNCOMMON, mage.cards.s.StriderHarness.class));
        cards.add(new SetCardInfo("Submerged Boneyard", 178, Rarity.UNCOMMON, mage.cards.s.SubmergedBoneyard.class));
        cards.add(new SetCardInfo("Sweep Away", 64, Rarity.COMMON, mage.cards.s.SweepAway.class));
        cards.add(new SetCardInfo("Sylvan Advocate", 144, Rarity.RARE, mage.cards.s.SylvanAdvocate.class));
        cards.add(new SetCardInfo("Tajuru Pathwarden", 145, Rarity.COMMON, mage.cards.t.TajuruPathwarden.class));
        cards.add(new SetCardInfo("Tar Snare", 90, Rarity.COMMON, mage.cards.t.TarSnare.class));
        cards.add(new SetCardInfo("Tears of Valakut", 118, Rarity.UNCOMMON, mage.cards.t.TearsOfValakut.class));
        cards.add(new SetCardInfo("Thought Harvester", 48, Rarity.UNCOMMON, mage.cards.t.ThoughtHarvester.class));
        cards.add(new SetCardInfo("Thought-Knot Seer", 9, Rarity.RARE, mage.cards.t.ThoughtKnotSeer.class));
        cards.add(new SetCardInfo("Timber Gorge", 179, Rarity.UNCOMMON, mage.cards.t.TimberGorge.class));
        cards.add(new SetCardInfo("Tranquil Expanse", 180, Rarity.UNCOMMON, mage.cards.t.TranquilExpanse.class));
        cards.add(new SetCardInfo("Tyrant of Valakut", 119, Rarity.RARE, mage.cards.t.TyrantOfValakut.class));
        cards.add(new SetCardInfo("Umara Entangler", 65, Rarity.COMMON, mage.cards.u.UmaraEntangler.class));
        cards.add(new SetCardInfo("Unity of Purpose", 66, Rarity.UNCOMMON, mage.cards.u.UnityOfPurpose.class));
        cards.add(new SetCardInfo("Unknown Shores", 181, Rarity.COMMON, mage.cards.u.UnknownShores.class));
        cards.add(new SetCardInfo("Unnatural Endurance", 80, Rarity.COMMON, mage.cards.u.UnnaturalEndurance.class));
        cards.add(new SetCardInfo("Untamed Hunger", 91, Rarity.COMMON, mage.cards.u.UntamedHunger.class));
        cards.add(new SetCardInfo("Vampire Envoy", 92, Rarity.COMMON, mage.cards.v.VampireEnvoy.class));
        cards.add(new SetCardInfo("Vile Redeemer", 125, Rarity.RARE, mage.cards.v.VileRedeemer.class));
        cards.add(new SetCardInfo("Vines of the Recluse", 146, Rarity.COMMON, mage.cards.v.VinesOfTheRecluse.class));
        cards.add(new SetCardInfo("Visions of Brutality", 81, Rarity.UNCOMMON, mage.cards.v.VisionsOfBrutality.class));
        cards.add(new SetCardInfo("Void Grafter", 150, Rarity.UNCOMMON, mage.cards.v.VoidGrafter.class));
        cards.add(new SetCardInfo("Void Shatter", 49, Rarity.UNCOMMON, mage.cards.v.VoidShatter.class));
        cards.add(new SetCardInfo("Walker of the Wastes", 10, Rarity.UNCOMMON, mage.cards.w.WalkerOfTheWastes.class));
        cards.add(new SetCardInfo("Wall of Resurgence", 39, Rarity.UNCOMMON, mage.cards.w.WallOfResurgence.class));
        cards.add(new SetCardInfo("Wandering Fumarole", 182, Rarity.RARE, mage.cards.w.WanderingFumarole.class));
        cards.add(new SetCardInfo("Warden of Geometries", 11, Rarity.COMMON, mage.cards.w.WardenOfGeometries.class));
        cards.add(new SetCardInfo("Warping Wail", 12, Rarity.UNCOMMON, mage.cards.w.WarpingWail.class));
        cards.add(new SetCardInfo("Wastes", "183a", Rarity.COMMON, mage.cards.w.Wastes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wastes", "184a", Rarity.COMMON, mage.cards.w.Wastes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wastes", 183, Rarity.COMMON, mage.cards.w.Wastes.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Wastes", 184, Rarity.COMMON, mage.cards.w.Wastes.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Weapons Trainer", 160, Rarity.UNCOMMON, mage.cards.w.WeaponsTrainer.class));
        cards.add(new SetCardInfo("Witness the End", 82, Rarity.COMMON, mage.cards.w.WitnessTheEnd.class));
        cards.add(new SetCardInfo("World Breaker", 126, Rarity.MYTHIC, mage.cards.w.WorldBreaker.class));
        cards.add(new SetCardInfo("Zada's Commando", 120, Rarity.COMMON, mage.cards.z.ZadasCommando.class));
        cards.add(new SetCardInfo("Zendikar Resurgent", 147, Rarity.RARE, mage.cards.z.ZendikarResurgent.class));
        cards.add(new SetCardInfo("Zulaport Chainmage", 93, Rarity.COMMON, mage.cards.z.ZulaportChainmage.class));
    }

    @Override
    protected List<CardInfo> findCardsByRarity(Rarity rarity) {
        List<CardInfo> cardInfos = super.findCardsByRarity(rarity);
        if (rarity == Rarity.COMMON) {
            // only the full-art versions of Wastes are found in boosters
            cardInfos.removeIf(cardInfo -> cardInfo.getCardNumber().contains("a"));
        } else if (rarity == Rarity.SPECIAL) {
            cardInfos.addAll(CardRepository.instance.findCards(new CardCriteria().setCodes("EXP").minCardNumber(26)));
        }
        return cardInfos;
    }
}
