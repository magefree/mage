package mage.sets;

import mage.cards.ExpansionSet;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheElk801
 */
public final class ModernHorizons extends ExpansionSet {

    private static final ModernHorizons instance = new ModernHorizons();

    public static ModernHorizons getInstance() {
        return instance;
    }

    private ModernHorizons() {
        super("Modern Horizons", "MH1", ExpansionSet.buildDate(2019, 6, 14), SetType.SUPPLEMENTAL_MODERN_LEGAL);
        this.blockName = "Modern Horizons";
        this.hasBasicLands = true;
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 254;

        cards.add(new SetCardInfo("Abominable Treefolk", 194, Rarity.UNCOMMON, mage.cards.a.AbominableTreefolk.class));
        cards.add(new SetCardInfo("Alpine Guide", 117, Rarity.UNCOMMON, mage.cards.a.AlpineGuide.class));
        cards.add(new SetCardInfo("Altar of Dementia", 218, Rarity.RARE, mage.cards.a.AltarOfDementia.class));
        cards.add(new SetCardInfo("Amorphous Axe", 219, Rarity.COMMON, mage.cards.a.AmorphousAxe.class));
        cards.add(new SetCardInfo("Answered Prayers", 2, Rarity.COMMON, mage.cards.a.AnsweredPrayers.class));
        cards.add(new SetCardInfo("Archmage's Charm", 40, Rarity.RARE, mage.cards.a.ArchmagesCharm.class));
        cards.add(new SetCardInfo("Arcum's Astrolabe", 220, Rarity.COMMON, mage.cards.a.ArcumsAstrolabe.class));
        cards.add(new SetCardInfo("Aria of Flame", 118, Rarity.RARE, mage.cards.a.AriaOfFlame.class));
        cards.add(new SetCardInfo("Astral Drift", 3, Rarity.RARE, mage.cards.a.AstralDrift.class));
        cards.add(new SetCardInfo("Ayula's Influence", 156, Rarity.RARE, mage.cards.a.AyulasInfluence.class));
        cards.add(new SetCardInfo("Ayula, Queen Among Bears", 155, Rarity.RARE, mage.cards.a.AyulaQueenAmongBears.class));
        cards.add(new SetCardInfo("Azra Smokeshaper", 79, Rarity.COMMON, mage.cards.a.AzraSmokeshaper.class));
        cards.add(new SetCardInfo("Barren Moor", 236, Rarity.UNCOMMON, mage.cards.b.BarrenMoor.class));
        cards.add(new SetCardInfo("Battle Screech", 4, Rarity.UNCOMMON, mage.cards.b.BattleScreech.class));
        cards.add(new SetCardInfo("Bazaar Trademage", 41, Rarity.RARE, mage.cards.b.BazaarTrademage.class));
        cards.add(new SetCardInfo("Bellowing Elk", 157, Rarity.COMMON, mage.cards.b.BellowingElk.class));
        cards.add(new SetCardInfo("Birthing Boughs", 221, Rarity.UNCOMMON, mage.cards.b.BirthingBoughs.class));
        cards.add(new SetCardInfo("Bladeback Sliver", 119, Rarity.COMMON, mage.cards.b.BladebackSliver.class));
        cards.add(new SetCardInfo("Blizzard Strix", 42, Rarity.UNCOMMON, mage.cards.b.BlizzardStrix.class));
        cards.add(new SetCardInfo("Bogardan Dragonheart", 120, Rarity.COMMON, mage.cards.b.BogardanDragonheart.class));
        cards.add(new SetCardInfo("Cabal Therapist", 80, Rarity.RARE, mage.cards.c.CabalTherapist.class));
        cards.add(new SetCardInfo("Carrion Feeder", 81, Rarity.UNCOMMON, mage.cards.c.CarrionFeeder.class));
        cards.add(new SetCardInfo("Cave of Temptation", 237, Rarity.COMMON, mage.cards.c.CaveOfTemptation.class));
        cards.add(new SetCardInfo("Changeling Outcast", 82, Rarity.COMMON, mage.cards.c.ChangelingOutcast.class));
        cards.add(new SetCardInfo("Chillerpillar", 43, Rarity.COMMON, mage.cards.c.Chillerpillar.class));
        cards.add(new SetCardInfo("Choking Tethers", 44, Rarity.COMMON, mage.cards.c.ChokingTethers.class));
        cards.add(new SetCardInfo("Cleaving Sliver", 121, Rarity.COMMON, mage.cards.c.CleavingSliver.class));
        cards.add(new SetCardInfo("Cloudshredder Sliver", 195, Rarity.RARE, mage.cards.c.CloudshredderSliver.class));
        cards.add(new SetCardInfo("Collected Conjuring", 196, Rarity.RARE, mage.cards.c.CollectedConjuring.class));
        cards.add(new SetCardInfo("Collector Ouphe", 158, Rarity.RARE, mage.cards.c.CollectorOuphe.class));
        cards.add(new SetCardInfo("Conifer Wurm", 159, Rarity.UNCOMMON, mage.cards.c.ConiferWurm.class));
        cards.add(new SetCardInfo("Cordial Vampire", 83, Rarity.RARE, mage.cards.c.CordialVampire.class));
        cards.add(new SetCardInfo("Crashing Footfalls", 160, Rarity.RARE, mage.cards.c.CrashingFootfalls.class));
        cards.add(new SetCardInfo("Crypt Rats", 84, Rarity.UNCOMMON, mage.cards.c.CryptRats.class));
        cards.add(new SetCardInfo("Cunning Evasion", 45, Rarity.UNCOMMON, mage.cards.c.CunningEvasion.class));
        cards.add(new SetCardInfo("Dead of Winter", 85, Rarity.RARE, mage.cards.d.DeadOfWinter.class));
        cards.add(new SetCardInfo("Deep Forest Hermit", 161, Rarity.RARE, mage.cards.d.DeepForestHermit.class));
        cards.add(new SetCardInfo("Defile", 86, Rarity.COMMON, mage.cards.d.Defile.class));
        cards.add(new SetCardInfo("Diabolic Edict", 87, Rarity.COMMON, mage.cards.d.DiabolicEdict.class));
        cards.add(new SetCardInfo("Dismantling Blow", 5, Rarity.UNCOMMON, mage.cards.d.DismantlingBlow.class));
        cards.add(new SetCardInfo("Dregscape Sliver", 88, Rarity.UNCOMMON, mage.cards.d.DregscapeSliver.class));
        cards.add(new SetCardInfo("Echo of Eons", 46, Rarity.MYTHIC, mage.cards.e.EchoOfEons.class));
        cards.add(new SetCardInfo("Eladamri's Call", 197, Rarity.RARE, mage.cards.e.EladamrisCall.class));
        cards.add(new SetCardInfo("Elvish Fury", 162, Rarity.COMMON, mage.cards.e.ElvishFury.class));
        cards.add(new SetCardInfo("Endling", 89, Rarity.RARE, mage.cards.e.Endling.class));
        cards.add(new SetCardInfo("Enduring Sliver", 6, Rarity.COMMON, mage.cards.e.EnduringSliver.class));
        cards.add(new SetCardInfo("Ephemerate", 7, Rarity.COMMON, mage.cards.e.Ephemerate.class));
        cards.add(new SetCardInfo("Etchings of the Chosen", 198, Rarity.UNCOMMON, mage.cards.e.EtchingsOfTheChosen.class));
        cards.add(new SetCardInfo("Everdream", 47, Rarity.UNCOMMON, mage.cards.e.Everdream.class));
        cards.add(new SetCardInfo("Excavating Anurid", 163, Rarity.COMMON, mage.cards.e.ExcavatingAnurid.class));
        cards.add(new SetCardInfo("Exclude", 48, Rarity.UNCOMMON, mage.cards.e.Exclude.class));
        cards.add(new SetCardInfo("Eyekite", 49, Rarity.COMMON, mage.cards.e.Eyekite.class));
        cards.add(new SetCardInfo("Face of Divinity", 8, Rarity.UNCOMMON, mage.cards.f.FaceOfDivinity.class));
        cards.add(new SetCardInfo("Fact or Fiction", 50, Rarity.UNCOMMON, mage.cards.f.FactOrFiction.class));
        cards.add(new SetCardInfo("Faerie Seer", 51, Rarity.COMMON, mage.cards.f.FaerieSeer.class));
        cards.add(new SetCardInfo("Fallen Shinobi", 199, Rarity.RARE, mage.cards.f.FallenShinobi.class));
        cards.add(new SetCardInfo("Farmstead Gleaner", 222, Rarity.UNCOMMON, mage.cards.f.FarmsteadGleaner.class));
        cards.add(new SetCardInfo("Feaster of Fools", 90, Rarity.UNCOMMON, mage.cards.f.FeasterOfFools.class));
        cards.add(new SetCardInfo("Fiery Islet", 238, Rarity.RARE, mage.cards.f.FieryIslet.class));
        cards.add(new SetCardInfo("Firebolt", 122, Rarity.UNCOMMON, mage.cards.f.Firebolt.class));
        cards.add(new SetCardInfo("First Sliver's Chosen", 9, Rarity.UNCOMMON, mage.cards.f.FirstSliversChosen.class));
        cards.add(new SetCardInfo("First-Sphere Gargantua", 91, Rarity.COMMON, mage.cards.f.FirstSphereGargantua.class));
        cards.add(new SetCardInfo("Fists of Flame", 123, Rarity.COMMON, mage.cards.f.FistsOfFlame.class));
        cards.add(new SetCardInfo("Flusterstorm", 255, Rarity.RARE, mage.cards.f.Flusterstorm.class));
        cards.add(new SetCardInfo("Force of Despair", 92, Rarity.RARE, mage.cards.f.ForceOfDespair.class));
        cards.add(new SetCardInfo("Force of Negation", 52, Rarity.RARE, mage.cards.f.ForceOfNegation.class));
        cards.add(new SetCardInfo("Force of Rage", 124, Rarity.RARE, mage.cards.f.ForceOfRage.class));
        cards.add(new SetCardInfo("Force of Vigor", 164, Rarity.RARE, mage.cards.f.ForceOfVigor.class));
        cards.add(new SetCardInfo("Force of Virtue", 10, Rarity.RARE, mage.cards.f.ForceOfVirtue.class));
        cards.add(new SetCardInfo("Forgotten Cave", 239, Rarity.UNCOMMON, mage.cards.f.ForgottenCave.class));
        cards.add(new SetCardInfo("Fountain of Ichor", 223, Rarity.COMMON, mage.cards.f.FountainOfIchor.class));
        cards.add(new SetCardInfo("Frostwalk Bastion", 240, Rarity.UNCOMMON, mage.cards.f.FrostwalkBastion.class));
        cards.add(new SetCardInfo("Frostwalla", 165, Rarity.COMMON, mage.cards.f.Frostwalla.class));
        cards.add(new SetCardInfo("Future Sight", 53, Rarity.RARE, mage.cards.f.FutureSight.class));
        cards.add(new SetCardInfo("Generous Gift", 11, Rarity.UNCOMMON, mage.cards.g.GenerousGift.class));
        cards.add(new SetCardInfo("Genesis", 166, Rarity.RARE, mage.cards.g.Genesis.class));
        cards.add(new SetCardInfo("Geomancer's Gambit", 125, Rarity.COMMON, mage.cards.g.GeomancersGambit.class));
        cards.add(new SetCardInfo("Gilded Light", 12, Rarity.COMMON, mage.cards.g.GildedLight.class));
        cards.add(new SetCardInfo("Giver of Runes", 13, Rarity.RARE, mage.cards.g.GiverOfRunes.class));
        cards.add(new SetCardInfo("Glacial Revelation", 167, Rarity.UNCOMMON, mage.cards.g.GlacialRevelation.class));
        cards.add(new SetCardInfo("Gluttonous Slug", 93, Rarity.COMMON, mage.cards.g.GluttonousSlug.class));
        cards.add(new SetCardInfo("Goatnap", 126, Rarity.COMMON, mage.cards.g.Goatnap.class));
        cards.add(new SetCardInfo("Goblin Champion", 127, Rarity.COMMON, mage.cards.g.GoblinChampion.class));
        cards.add(new SetCardInfo("Goblin Engineer", 128, Rarity.RARE, mage.cards.g.GoblinEngineer.class));
        cards.add(new SetCardInfo("Goblin Matron", 129, Rarity.UNCOMMON, mage.cards.g.GoblinMatron.class));
        cards.add(new SetCardInfo("Goblin Oriflamme", 130, Rarity.UNCOMMON, mage.cards.g.GoblinOriflamme.class));
        cards.add(new SetCardInfo("Goblin War Party", 131, Rarity.COMMON, mage.cards.g.GoblinWarParty.class));
        cards.add(new SetCardInfo("Good-Fortune Unicorn", 201, Rarity.UNCOMMON, mage.cards.g.GoodFortuneUnicorn.class));
        cards.add(new SetCardInfo("Graveshifter", 94, Rarity.UNCOMMON, mage.cards.g.Graveshifter.class));
        cards.add(new SetCardInfo("Hall of Heliod's Generosity", 241, Rarity.RARE, mage.cards.h.HallOfHeliodsGenerosity.class));
        cards.add(new SetCardInfo("Headless Specter", 95, Rarity.COMMON, mage.cards.h.HeadlessSpecter.class));
        cards.add(new SetCardInfo("Hexdrinker", 168, Rarity.MYTHIC, mage.cards.h.Hexdrinker.class));
        cards.add(new SetCardInfo("Hogaak, Arisen Necropolis", 202, Rarity.RARE, mage.cards.h.HogaakArisenNecropolis.class));
        cards.add(new SetCardInfo("Hollowhead Sliver", 132, Rarity.UNCOMMON, mage.cards.h.HollowheadSliver.class));
        cards.add(new SetCardInfo("Ice-Fang Coatl", 203, Rarity.RARE, mage.cards.i.IceFangCoatl.class));
        cards.add(new SetCardInfo("Iceberg Cancrix", 54, Rarity.COMMON, mage.cards.i.IcebergCancrix.class));
        cards.add(new SetCardInfo("Icehide Golem", 224, Rarity.UNCOMMON, mage.cards.i.IcehideGolem.class));
        cards.add(new SetCardInfo("Igneous Elemental", 133, Rarity.COMMON, mage.cards.i.IgneousElemental.class));
        cards.add(new SetCardInfo("Impostor of the Sixth Pride", 14, Rarity.COMMON, mage.cards.i.ImpostorOfTheSixthPride.class));
        cards.add(new SetCardInfo("Ingenious Infiltrator", 204, Rarity.UNCOMMON, mage.cards.i.IngeniousInfiltrator.class));
        cards.add(new SetCardInfo("Irregular Cohort", 15, Rarity.COMMON, mage.cards.i.IrregularCohort.class));
        cards.add(new SetCardInfo("Kaya's Guile", 205, Rarity.RARE, mage.cards.k.KayasGuile.class));
        cards.add(new SetCardInfo("Kess, Dissident Mage", 206, Rarity.MYTHIC, mage.cards.k.KessDissidentMage.class));
        cards.add(new SetCardInfo("King of the Pride", 16, Rarity.UNCOMMON, mage.cards.k.KingOfThePride.class));
        cards.add(new SetCardInfo("Knight of Old Benalia", 17, Rarity.COMMON, mage.cards.k.KnightOfOldBenalia.class));
        cards.add(new SetCardInfo("Krosan Tusker", 169, Rarity.COMMON, mage.cards.k.KrosanTusker.class));
        cards.add(new SetCardInfo("Lancer Sliver", 18, Rarity.COMMON, mage.cards.l.LancerSliver.class));
        cards.add(new SetCardInfo("Lava Dart", 134, Rarity.COMMON, mage.cards.l.LavaDart.class));
        cards.add(new SetCardInfo("Lavabelly Sliver", 207, Rarity.UNCOMMON, mage.cards.l.LavabellySliver.class));
        cards.add(new SetCardInfo("Lesser Masticore", 225, Rarity.UNCOMMON, mage.cards.l.LesserMasticore.class));
        cards.add(new SetCardInfo("Lightning Skelemental", 208, Rarity.RARE, mage.cards.l.LightningSkelemental.class));
        cards.add(new SetCardInfo("Llanowar Tribe", 170, Rarity.UNCOMMON, mage.cards.l.LlanowarTribe.class));
        cards.add(new SetCardInfo("Lonely Sandbar", 242, Rarity.UNCOMMON, mage.cards.l.LonelySandbar.class));
        cards.add(new SetCardInfo("Magmatic Sinkhole", 135, Rarity.COMMON, mage.cards.m.MagmaticSinkhole.class));
        cards.add(new SetCardInfo("Man-o'-War", 55, Rarity.COMMON, mage.cards.m.ManOWar.class));
        cards.add(new SetCardInfo("Marit Lage's Slumber", 56, Rarity.RARE, mage.cards.m.MaritLagesSlumber.class));
        cards.add(new SetCardInfo("Martyr's Soul", 19, Rarity.COMMON, mage.cards.m.MartyrsSoul.class));
        cards.add(new SetCardInfo("Mind Rake", 96, Rarity.COMMON, mage.cards.m.MindRake.class));
        cards.add(new SetCardInfo("Mirrodin Besieged", 57, Rarity.RARE, mage.cards.m.MirrodinBesieged.class));
        cards.add(new SetCardInfo("Mist-Syndicate Naga", 58, Rarity.RARE, mage.cards.m.MistSyndicateNaga.class));
        cards.add(new SetCardInfo("Mob", 97, Rarity.COMMON, mage.cards.m.Mob.class));
        cards.add(new SetCardInfo("Moonblade Shinobi", 59, Rarity.COMMON, mage.cards.m.MoonbladeShinobi.class));
        cards.add(new SetCardInfo("Morophon, the Boundless", 1, Rarity.MYTHIC, mage.cards.m.MorophonTheBoundless.class));
        cards.add(new SetCardInfo("Mother Bear", 171, Rarity.COMMON, mage.cards.m.MotherBear.class));
        cards.add(new SetCardInfo("Mox Tantalite", 226, Rarity.MYTHIC, mage.cards.m.MoxTantalite.class));
        cards.add(new SetCardInfo("Munitions Expert", 209, Rarity.UNCOMMON, mage.cards.m.MunitionsExpert.class));
        cards.add(new SetCardInfo("Murasa Behemoth", 172, Rarity.COMMON, mage.cards.m.MurasaBehemoth.class));
        cards.add(new SetCardInfo("Nantuko Cultivator", 173, Rarity.UNCOMMON, mage.cards.n.NantukoCultivator.class));
        cards.add(new SetCardInfo("Nature's Chant", 210, Rarity.COMMON, mage.cards.n.NaturesChant.class));
        cards.add(new SetCardInfo("Nether Spirit", 98, Rarity.RARE, mage.cards.n.NetherSpirit.class));
        cards.add(new SetCardInfo("Nimble Mongoose", 174, Rarity.COMMON, mage.cards.n.NimbleMongoose.class));
        cards.add(new SetCardInfo("Ninja of the New Moon", 99, Rarity.COMMON, mage.cards.n.NinjaOfTheNewMoon.class));
        cards.add(new SetCardInfo("Nurturing Peatland", 243, Rarity.RARE, mage.cards.n.NurturingPeatland.class));
        cards.add(new SetCardInfo("On Thin Ice", 20, Rarity.RARE, mage.cards.o.OnThinIce.class));
        cards.add(new SetCardInfo("Oneirophage", 60, Rarity.UNCOMMON, mage.cards.o.Oneirophage.class));
        cards.add(new SetCardInfo("Orcish Hellraiser", 136, Rarity.COMMON, mage.cards.o.OrcishHellraiser.class));
        cards.add(new SetCardInfo("Ore-Scale Guardian", 137, Rarity.UNCOMMON, mage.cards.o.OreScaleGuardian.class));
        cards.add(new SetCardInfo("Pashalik Mons", 138, Rarity.RARE, mage.cards.p.PashalikMons.class));
        cards.add(new SetCardInfo("Phantasmal Form", 61, Rarity.COMMON, mage.cards.p.PhantasmalForm.class));
        cards.add(new SetCardInfo("Phantom Ninja", 62, Rarity.COMMON, mage.cards.p.PhantomNinja.class));
        cards.add(new SetCardInfo("Pillage", 139, Rarity.UNCOMMON, mage.cards.p.Pillage.class));
        cards.add(new SetCardInfo("Plague Engineer", 100, Rarity.RARE, mage.cards.p.PlagueEngineer.class));
        cards.add(new SetCardInfo("Planebound Accomplice", 140, Rarity.RARE, mage.cards.p.PlaneboundAccomplice.class));
        cards.add(new SetCardInfo("Pondering Mage", 63, Rarity.COMMON, mage.cards.p.PonderingMage.class));
        cards.add(new SetCardInfo("Prismatic Vista", 244, Rarity.RARE, mage.cards.p.PrismaticVista.class));
        cards.add(new SetCardInfo("Prohibit", 64, Rarity.COMMON, mage.cards.p.Prohibit.class));
        cards.add(new SetCardInfo("Putrid Goblin", 101, Rarity.COMMON, mage.cards.p.PutridGoblin.class));
        cards.add(new SetCardInfo("Pyrophobia", 141, Rarity.COMMON, mage.cards.p.Pyrophobia.class));
        cards.add(new SetCardInfo("Quakefoot Cyclops", 142, Rarity.COMMON, mage.cards.q.QuakefootCyclops.class));
        cards.add(new SetCardInfo("Rain of Revelation", 65, Rarity.COMMON, mage.cards.r.RainOfRevelation.class));
        cards.add(new SetCardInfo("Ranger-Captain of Eos", 21, Rarity.MYTHIC, mage.cards.r.RangerCaptainOfEos.class));
        cards.add(new SetCardInfo("Rank Officer", 102, Rarity.COMMON, mage.cards.r.RankOfficer.class));
        cards.add(new SetCardInfo("Ransack the Lab", 103, Rarity.COMMON, mage.cards.r.RansackTheLab.class));
        cards.add(new SetCardInfo("Ravenous Giant", 143, Rarity.UNCOMMON, mage.cards.r.RavenousGiant.class));
        cards.add(new SetCardInfo("Reap the Past", 211, Rarity.RARE, mage.cards.r.ReapThePast.class));
        cards.add(new SetCardInfo("Rebuild", 66, Rarity.UNCOMMON, mage.cards.r.Rebuild.class));
        cards.add(new SetCardInfo("Reckless Charge", 144, Rarity.COMMON, mage.cards.r.RecklessCharge.class));
        cards.add(new SetCardInfo("Recruit the Worthy", 22, Rarity.COMMON, mage.cards.r.RecruitTheWorthy.class));
        cards.add(new SetCardInfo("Regrowth", 175, Rarity.UNCOMMON, mage.cards.r.Regrowth.class));
        cards.add(new SetCardInfo("Reprobation", 23, Rarity.COMMON, mage.cards.r.Reprobation.class));
        cards.add(new SetCardInfo("Return from Extinction", 104, Rarity.COMMON, mage.cards.r.ReturnFromExtinction.class));
        cards.add(new SetCardInfo("Rhox Veteran", 24, Rarity.COMMON, mage.cards.r.RhoxVeteran.class));
        cards.add(new SetCardInfo("Rime Tender", 176, Rarity.COMMON, mage.cards.r.RimeTender.class));
        cards.add(new SetCardInfo("Rotwidow Pack", 212, Rarity.UNCOMMON, mage.cards.r.RotwidowPack.class));
        cards.add(new SetCardInfo("Ruination Rioter", 213, Rarity.UNCOMMON, mage.cards.r.RuinationRioter.class));
        cards.add(new SetCardInfo("Saddled Rimestag", 177, Rarity.UNCOMMON, mage.cards.s.SaddledRimestag.class));
        cards.add(new SetCardInfo("Sadistic Obsession", 105, Rarity.UNCOMMON, mage.cards.s.SadisticObsession.class));
        cards.add(new SetCardInfo("Savage Swipe", 178, Rarity.COMMON, mage.cards.s.SavageSwipe.class));
        cards.add(new SetCardInfo("Scale Up", 179, Rarity.UNCOMMON, mage.cards.s.ScaleUp.class));
        cards.add(new SetCardInfo("Scour All Possibilities", 67, Rarity.COMMON, mage.cards.s.ScourAllPossibilities.class));
        cards.add(new SetCardInfo("Scrapyard Recombiner", 227, Rarity.RARE, mage.cards.s.ScrapyardRecombiner.class));
        cards.add(new SetCardInfo("Scuttling Sliver", 68, Rarity.UNCOMMON, mage.cards.s.ScuttlingSliver.class));
        cards.add(new SetCardInfo("Seasoned Pyromancer", 145, Rarity.MYTHIC, mage.cards.s.SeasonedPyromancer.class));
        cards.add(new SetCardInfo("Secluded Steppe", 245, Rarity.UNCOMMON, mage.cards.s.SecludedSteppe.class));
        cards.add(new SetCardInfo("Segovian Angel", 25, Rarity.COMMON, mage.cards.s.SegovianAngel.class));
        cards.add(new SetCardInfo("Serra the Benevolent", 26, Rarity.MYTHIC, mage.cards.s.SerraTheBenevolent.class));
        cards.add(new SetCardInfo("Settle Beyond Reality", 27, Rarity.COMMON, mage.cards.s.SettleBeyondReality.class));
        cards.add(new SetCardInfo("Shatter Assumptions", 106, Rarity.UNCOMMON, mage.cards.s.ShatterAssumptions.class));
        cards.add(new SetCardInfo("Shelter", 28, Rarity.COMMON, mage.cards.s.Shelter.class));
        cards.add(new SetCardInfo("Shenanigans", 146, Rarity.COMMON, mage.cards.s.Shenanigans.class));
        cards.add(new SetCardInfo("Silent Clearing", 246, Rarity.RARE, mage.cards.s.SilentClearing.class));
        cards.add(new SetCardInfo("Silumgar Scavenger", 107, Rarity.COMMON, mage.cards.s.SilumgarScavenger.class));
        cards.add(new SetCardInfo("Sisay, Weatherlight Captain", 29, Rarity.RARE, mage.cards.s.SisayWeatherlightCaptain.class));
        cards.add(new SetCardInfo("Sling-Gang Lieutenant", 108, Rarity.UNCOMMON, mage.cards.s.SlingGangLieutenant.class));
        cards.add(new SetCardInfo("Smiting Helix", 109, Rarity.UNCOMMON, mage.cards.s.SmitingHelix.class));
        cards.add(new SetCardInfo("Smoke Shroud", 69, Rarity.COMMON, mage.cards.s.SmokeShroud.class));
        cards.add(new SetCardInfo("Snow-Covered Forest", 254, Rarity.LAND, mage.cards.s.SnowCoveredForest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Snow-Covered Island", 251, Rarity.LAND, mage.cards.s.SnowCoveredIsland.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Snow-Covered Mountain", 253, Rarity.LAND, mage.cards.s.SnowCoveredMountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Snow-Covered Plains", 250, Rarity.LAND, mage.cards.s.SnowCoveredPlains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Snow-Covered Swamp", 252, Rarity.LAND, mage.cards.s.SnowCoveredSwamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Soul-Strike Technique", 30, Rarity.COMMON, mage.cards.s.SoulStrikeTechnique.class));
        cards.add(new SetCardInfo("Soulherder", 214, Rarity.UNCOMMON, mage.cards.s.Soulherder.class));
        cards.add(new SetCardInfo("Spell Snuff", 70, Rarity.COMMON, mage.cards.s.SpellSnuff.class));
        cards.add(new SetCardInfo("Spinehorn Minotaur", 147, Rarity.COMMON, mage.cards.s.SpinehornMinotaur.class));
        cards.add(new SetCardInfo("Spiteful Sliver", 148, Rarity.RARE, mage.cards.s.SpitefulSliver.class));
        cards.add(new SetCardInfo("Splicer's Skill", 31, Rarity.UNCOMMON, mage.cards.s.SplicersSkill.class));
        cards.add(new SetCardInfo("Spore Frog", 180, Rarity.COMMON, mage.cards.s.SporeFrog.class));
        cards.add(new SetCardInfo("Springbloom Druid", 181, Rarity.COMMON, mage.cards.s.SpringbloomDruid.class));
        cards.add(new SetCardInfo("Squirrel Nest", 182, Rarity.UNCOMMON, mage.cards.s.SquirrelNest.class));
        cards.add(new SetCardInfo("Stirring Address", 32, Rarity.COMMON, mage.cards.s.StirringAddress.class));
        cards.add(new SetCardInfo("Stream of Thought", 71, Rarity.COMMON, mage.cards.s.StreamOfThought.class));
        cards.add(new SetCardInfo("String of Disappearances", 72, Rarity.COMMON, mage.cards.s.StringOfDisappearances.class));
        cards.add(new SetCardInfo("Sunbaked Canyon", 247, Rarity.RARE, mage.cards.s.SunbakedCanyon.class));
        cards.add(new SetCardInfo("Sword of Sinew and Steel", 228, Rarity.MYTHIC, mage.cards.s.SwordOfSinewAndSteel.class));
        cards.add(new SetCardInfo("Sword of Truth and Justice", 229, Rarity.MYTHIC, mage.cards.s.SwordOfTruthAndJustice.class));
        cards.add(new SetCardInfo("Talisman of Conviction", 230, Rarity.UNCOMMON, mage.cards.t.TalismanOfConviction.class));
        cards.add(new SetCardInfo("Talisman of Creativity", 231, Rarity.UNCOMMON, mage.cards.t.TalismanOfCreativity.class));
        cards.add(new SetCardInfo("Talisman of Curiosity", 232, Rarity.UNCOMMON, mage.cards.t.TalismanOfCuriosity.class));
        cards.add(new SetCardInfo("Talisman of Hierarchy", 233, Rarity.UNCOMMON, mage.cards.t.TalismanOfHierarchy.class));
        cards.add(new SetCardInfo("Talisman of Resilience", 234, Rarity.UNCOMMON, mage.cards.t.TalismanOfResilience.class));
        cards.add(new SetCardInfo("Tectonic Reformation", 149, Rarity.RARE, mage.cards.t.TectonicReformation.class));
        cards.add(new SetCardInfo("Tempered Sliver", 183, Rarity.UNCOMMON, mage.cards.t.TemperedSliver.class));
        cards.add(new SetCardInfo("The First Sliver", 200, Rarity.MYTHIC, mage.cards.t.TheFirstSliver.class));
        cards.add(new SetCardInfo("Thornado", 184, Rarity.COMMON, mage.cards.t.Thornado.class));
        cards.add(new SetCardInfo("Throatseeker", 110, Rarity.UNCOMMON, mage.cards.t.Throatseeker.class));
        cards.add(new SetCardInfo("Throes of Chaos", 150, Rarity.UNCOMMON, mage.cards.t.ThroesOfChaos.class));
        cards.add(new SetCardInfo("Thundering Djinn", 215, Rarity.UNCOMMON, mage.cards.t.ThunderingDjinn.class));
        cards.add(new SetCardInfo("Tranquil Thicket", 248, Rarity.UNCOMMON, mage.cards.t.TranquilThicket.class));
        cards.add(new SetCardInfo("Treefolk Umbra", 185, Rarity.COMMON, mage.cards.t.TreefolkUmbra.class));
        cards.add(new SetCardInfo("Treetop Ambusher", 186, Rarity.COMMON, mage.cards.t.TreetopAmbusher.class));
        cards.add(new SetCardInfo("Tribute Mage", 73, Rarity.UNCOMMON, mage.cards.t.TributeMage.class));
        cards.add(new SetCardInfo("Trumpeting Herd", 187, Rarity.COMMON, mage.cards.t.TrumpetingHerd.class));
        cards.add(new SetCardInfo("Trustworthy Scout", 33, Rarity.COMMON, mage.cards.t.TrustworthyScout.class));
        cards.add(new SetCardInfo("Twin-Silk Spider", 188, Rarity.COMMON, mage.cards.t.TwinSilkSpider.class));
        cards.add(new SetCardInfo("Twisted Reflection", 74, Rarity.UNCOMMON, mage.cards.t.TwistedReflection.class));
        cards.add(new SetCardInfo("Umezawa's Charm", 111, Rarity.COMMON, mage.cards.u.UmezawasCharm.class));
        cards.add(new SetCardInfo("Unbound Flourishing", 189, Rarity.MYTHIC, mage.cards.u.UnboundFlourishing.class));
        cards.add(new SetCardInfo("Undead Augur", 112, Rarity.UNCOMMON, mage.cards.u.UndeadAugur.class));
        cards.add(new SetCardInfo("Unearth", 113, Rarity.COMMON, mage.cards.u.Unearth.class));
        cards.add(new SetCardInfo("Universal Automaton", 235, Rarity.COMMON, mage.cards.u.UniversalAutomaton.class));
        cards.add(new SetCardInfo("Unsettled Mariner", 216, Rarity.RARE, mage.cards.u.UnsettledMariner.class));
        cards.add(new SetCardInfo("Urza's Rage", 151, Rarity.UNCOMMON, mage.cards.u.UrzasRage.class));
        cards.add(new SetCardInfo("Urza, Lord High Artificer", 75, Rarity.MYTHIC, mage.cards.u.UrzaLordHighArtificer.class));
        cards.add(new SetCardInfo("Valiant Changeling", 34, Rarity.UNCOMMON, mage.cards.v.ValiantChangeling.class));
        cards.add(new SetCardInfo("Vengeful Devil", 152, Rarity.UNCOMMON, mage.cards.v.VengefulDevil.class));
        cards.add(new SetCardInfo("Venomous Changeling", 114, Rarity.COMMON, mage.cards.v.VenomousChangeling.class));
        cards.add(new SetCardInfo("Vesperlark", 35, Rarity.UNCOMMON, mage.cards.v.Vesperlark.class));
        cards.add(new SetCardInfo("Viashino Sandsprinter", 153, Rarity.COMMON, mage.cards.v.ViashinoSandsprinter.class));
        cards.add(new SetCardInfo("Volatile Claws", 154, Rarity.COMMON, mage.cards.v.VolatileClaws.class));
        cards.add(new SetCardInfo("Wall of Blossoms", 190, Rarity.UNCOMMON, mage.cards.w.WallOfBlossoms.class));
        cards.add(new SetCardInfo("Wall of One Thousand Cuts", 36, Rarity.COMMON, mage.cards.w.WallOfOneThousandCuts.class));
        cards.add(new SetCardInfo("Warteye Witch", 115, Rarity.COMMON, mage.cards.w.WarteyeWitch.class));
        cards.add(new SetCardInfo("Watcher for Tomorrow", 76, Rarity.UNCOMMON, mage.cards.w.WatcherForTomorrow.class));
        cards.add(new SetCardInfo("Waterlogged Grove", 249, Rarity.RARE, mage.cards.w.WaterloggedGrove.class));
        cards.add(new SetCardInfo("Weather the Storm", 191, Rarity.COMMON, mage.cards.w.WeatherTheStorm.class));
        cards.add(new SetCardInfo("Webweaver Changeling", 192, Rarity.UNCOMMON, mage.cards.w.WebweaverChangeling.class));
        cards.add(new SetCardInfo("Windcaller Aven", 77, Rarity.COMMON, mage.cards.w.WindcallerAven.class));
        cards.add(new SetCardInfo("Winding Way", 193, Rarity.COMMON, mage.cards.w.WindingWay.class));
        cards.add(new SetCardInfo("Winds of Abandon", 37, Rarity.RARE, mage.cards.w.WindsOfAbandon.class));
        cards.add(new SetCardInfo("Wing Shards", 38, Rarity.UNCOMMON, mage.cards.w.WingShards.class));
        cards.add(new SetCardInfo("Winter's Rest", 78, Rarity.COMMON, mage.cards.w.WintersRest.class));
        cards.add(new SetCardInfo("Wrenn and Six", 217, Rarity.MYTHIC, mage.cards.w.WrennAndSix.class));
        cards.add(new SetCardInfo("Yawgmoth, Thran Physician", 116, Rarity.MYTHIC, mage.cards.y.YawgmothThranPhysician.class));
        cards.add(new SetCardInfo("Zhalfirin Decoy", 39, Rarity.UNCOMMON, mage.cards.z.ZhalfirinDecoy.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new ModernHorizonsCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/mh1.html
// Using "classic" US collation for common/uncommon, rare collation inferred from other sets
class ModernHorizonsCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "30", "43", "142", "33", "51", "119", "7", "70", "153", "22", "67", "123", "6", "59", "120", "32", "61", "144", "36", "49", "127", "23", "44", "147", "14", "62", "131", "2", "43", "134", "30", "69", "123", "19", "72", "126", "22", "51", "127", "33", "70", "142", "32", "67", "153", "6", "61", "120", "7", "44", "119", "23", "59", "147", "14", "62", "144", "2", "72", "134", "19", "49", "131", "36", "69", "126");
    private final CardRun commonB = new CardRun(true, "79", "184", "93", "165", "99", "157", "103", "185", "82", "180", "95", "186", "115", "193", "113", "188", "87", "174", "111", "185", "91", "171", "79", "176", "99", "184", "93", "165", "103", "157", "82", "186", "87", "180", "113", "188", "95", "174", "115", "193", "91", "171", "111", "185", "79", "184", "103", "165", "99", "157", "93", "180", "87", "186", "82", "176", "95", "174", "91", "171", "115", "176", "113", "193", "111", "188");
    private final CardRun commonC1 = new CardRun(true, "17", "63", "96", "141", "77", "18", "104", "169", "54", "97", "220", "223", "172", "25", "55", "237", "121", "107", "27", "71", "163", "136", "114", "15", "125", "181", "220", "63", "102", "18", "54", "169", "104", "77", "141", "25", "97", "235", "27", "125", "172", "96", "55", "17", "121", "163", "71", "114", "136", "223", "107", "15", "237", "181", "102");
    private final CardRun commonC2 = new CardRun(true, "65", "219", "135", "12", "187", "24", "191", "78", "101", "162", "64", "133", "219", "178", "210", "28", "65", "12", "187", "154", "86", "146", "24", "101", "135", "191", "78", "86", "146", "28", "219", "162", "12", "65", "154", "178", "64", "133", "24", "210", "187", "235", "78", "191", "135", "101", "162", "210", "28", "133", "64", "86", "154", "178", "146");
    private final CardRun uncommonA = new CardRun(true, "48", "159", "112", "143", "233", "248", "38", "94", "204", "132", "221", "60", "182", "225", "122", "73", "183", "16", "88", "213", "236", "39", "50", "170", "84", "137", "233", "239", "34", "108", "215", "117", "221", "74", "167", "231", "143", "201", "192", "4", "110", "212", "151", "38", "48", "159", "94", "132", "60", "183", "39", "112", "204", "248", "225", "73", "170", "88", "122", "213", "236", "16", "84", "201", "117", "233", "50", "192", "239", "137", "231", "182", "34", "108", "215", "151", "221", "74", "167", "110", "143", "212", "159", "4", "112", "204", "248", "38", "48", "183", "88", "122", "60", "236", "39", "94", "213", "132", "16", "73", "182", "225", "117", "201", "192", "34", "108", "215", "151", "231", "50", "167", "84", "137", "74", "170", "4", "110", "212", "239");
    private final CardRun uncommonB = new CardRun(true, "234", "11", "129", "81", "47", "209", "190", "8", "232", "66", "150", "214", "5", "152", "106", "129", "194", "173", "240", "222", "42", "130", "109", "31", "139", "90", "242", "207", "175", "245", "230", "45", "150", "240", "179", "198", "68", "242", "152", "11", "177", "209", "105", "234", "245", "173", "214", "76", "224", "9", "130", "190", "207", "106", "194", "8", "175", "105", "42", "230", "129", "5", "179", "66", "81", "139", "35", "177", "106", "76", "222", "150", "31", "173", "47", "198", "234", "11", "190", "90", "45", "232", "245", "8", "175", "68", "109", "224", "9", "240", "105", "42", "230", "209", "35", "242", "47", "81", "214", "31", "139", "90", "66", "194", "179", "5", "232", "45", "152", "222", "9", "130", "109", "68", "207", "177", "35", "224", "76", "198");
    private final CardRun rare = new CardRun(false, "3", "10", "13", "20", "29", "37", "40", "41", "52", "53", "56", "57", "58", "80", "83", "85", "89", "92", "98", "100", "118", "124", "128", "138", "140", "148", "149", "155", "156", "158", "160", "161", "164", "166", "195", "196", "197", "199", "202", "203", "205", "208", "211", "216", "218", "227", "238", "241", "243", "244", "246", "247", "249", "3", "10", "13", "20", "29", "37", "40", "41", "52", "53", "56", "57", "58", "80", "83", "85", "89", "92", "98", "100", "118", "124", "128", "138", "140", "148", "149", "155", "156", "158", "160", "161", "164", "166", "195", "196", "197", "199", "202", "203", "205", "208", "211", "216", "218", "227", "238", "241", "243", "244", "246", "247", "249", "1", "21", "26", "46", "75", "116", "145", "168", "189", "200", "206", "217", "226", "228", "229");
    private final CardRun land = new CardRun(false, "250", "251", "252", "253", "254");

    private final BoosterStructure AABBC1C1C1C1C1C1 = new BoosterStructure(
            commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAABBC1C1C1C1C1 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAAABBC2C2C2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB,
            commonC2, commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBBC2C2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBBBC2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC2, commonC2
    );
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB);
    private final BoosterStructure R1 = new BoosterStructure(rare);
    private final BoosterStructure L1 = new BoosterStructure(land);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 3.27 A commons (36 / 11)
    // 2.18 B commons (24 / 11)
    // 2.73 C1 commons (30 / 11, or 60 / 11 in each C1 booster)
    // 1.82 C2 commons (20 / 11, or 40 / 11 in each C2 booster)
    // These numbers are the same for all sets with 101 commons in A/B/C1/C2 print runs
    // and with 10 common slots per booster
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,

            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBBC2C2C2,
            AAAABBBC2C2C2,
            AAAABBBBC2C2
    );
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(AAB, ABB);
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1);
    private final RarityConfiguration landRuns = new RarityConfiguration(L1);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        booster.addAll(landRuns.getNext().makeRun());
        return booster;
    }
}
