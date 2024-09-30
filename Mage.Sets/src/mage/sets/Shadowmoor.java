package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author North
 */
public final class Shadowmoor extends ExpansionSet {

    private static final Shadowmoor instance = new Shadowmoor();

    public static Shadowmoor getInstance() {
        return instance;
    }

    private Shadowmoor() {
        super("Shadowmoor", "SHM", ExpansionSet.buildDate(2008, 4, 2), SetType.EXPANSION);
        this.blockName = "Shadowmoor";
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;

        cards.add(new SetCardInfo("Advice from the Fae", 28, Rarity.UNCOMMON, mage.cards.a.AdviceFromTheFae.class));
        cards.add(new SetCardInfo("Aethertow", 136, Rarity.COMMON, mage.cards.a.Aethertow.class));
        cards.add(new SetCardInfo("Aphotic Wisps", 55, Rarity.COMMON, mage.cards.a.AphoticWisps.class));
        cards.add(new SetCardInfo("Apothecary Initiate", 1, Rarity.COMMON, mage.cards.a.ApothecaryInitiate.class));
        cards.add(new SetCardInfo("Armored Ascension", 2, Rarity.UNCOMMON, mage.cards.a.ArmoredAscension.class));
        cards.add(new SetCardInfo("Ashenmoor Cohort", 56, Rarity.COMMON, mage.cards.a.AshenmoorCohort.class));
        cards.add(new SetCardInfo("Ashenmoor Gouger", 180, Rarity.UNCOMMON, mage.cards.a.AshenmoorGouger.class));
        cards.add(new SetCardInfo("Ashenmoor Liege", 181, Rarity.RARE, mage.cards.a.AshenmoorLiege.class));
        cards.add(new SetCardInfo("Augury Adept", 137, Rarity.RARE, mage.cards.a.AuguryAdept.class));
        cards.add(new SetCardInfo("Ballynock Cohort", 3, Rarity.COMMON, mage.cards.b.BallynockCohort.class));
        cards.add(new SetCardInfo("Barkshell Blessing", 224, Rarity.COMMON, mage.cards.b.BarkshellBlessing.class));
        cards.add(new SetCardInfo("Barrenton Cragtreads", 138, Rarity.COMMON, mage.cards.b.BarrentonCragtreads.class));
        cards.add(new SetCardInfo("Barrenton Medic", 4, Rarity.COMMON, mage.cards.b.BarrentonMedic.class));
        cards.add(new SetCardInfo("Beseech the Queen", 57, Rarity.UNCOMMON, mage.cards.b.BeseechTheQueen.class));
        cards.add(new SetCardInfo("Biting Tether", 29, Rarity.UNCOMMON, mage.cards.b.BitingTether.class));
        cards.add(new SetCardInfo("Blazethorn Scarecrow", 246, Rarity.COMMON, mage.cards.b.BlazethornScarecrow.class));
        cards.add(new SetCardInfo("Blight Sickle", 247, Rarity.COMMON, mage.cards.b.BlightSickle.class));
        cards.add(new SetCardInfo("Blistering Dieflyn", 82, Rarity.COMMON, mage.cards.b.BlisteringDieflyn.class));
        cards.add(new SetCardInfo("Bloodmark Mentor", 83, Rarity.UNCOMMON, mage.cards.b.BloodmarkMentor.class));
        cards.add(new SetCardInfo("Bloodshed Fever", 84, Rarity.COMMON, mage.cards.b.BloodshedFever.class));
        cards.add(new SetCardInfo("Blowfly Infestation", 58, Rarity.UNCOMMON, mage.cards.b.BlowflyInfestation.class));
        cards.add(new SetCardInfo("Boartusk Liege", 202, Rarity.RARE, mage.cards.b.BoartuskLiege.class));
        cards.add(new SetCardInfo("Boggart Arsonists", 85, Rarity.COMMON, mage.cards.b.BoggartArsonists.class));
        cards.add(new SetCardInfo("Boggart Ram-Gang", 203, Rarity.UNCOMMON, mage.cards.b.BoggartRamGang.class));
        cards.add(new SetCardInfo("Boon Reflection", 5, Rarity.RARE, mage.cards.b.BoonReflection.class));
        cards.add(new SetCardInfo("Briarberry Cohort", 30, Rarity.COMMON, mage.cards.b.BriarberryCohort.class));
        cards.add(new SetCardInfo("Burn Trail", 86, Rarity.COMMON, mage.cards.b.BurnTrail.class));
        cards.add(new SetCardInfo("Cauldron of Souls", 248, Rarity.RARE, mage.cards.c.CauldronOfSouls.class));
        cards.add(new SetCardInfo("Cemetery Puca", 158, Rarity.RARE, mage.cards.c.CemeteryPuca.class));
        cards.add(new SetCardInfo("Cerulean Wisps", 31, Rarity.COMMON, mage.cards.c.CeruleanWisps.class));
        cards.add(new SetCardInfo("Chainbreaker", 249, Rarity.COMMON, mage.cards.c.Chainbreaker.class));
        cards.add(new SetCardInfo("Cinderbones", 59, Rarity.COMMON, mage.cards.c.Cinderbones.class));
        cards.add(new SetCardInfo("Cinderhaze Wretch", 60, Rarity.COMMON, mage.cards.c.CinderhazeWretch.class));
        cards.add(new SetCardInfo("Consign to Dream", 32, Rarity.COMMON, mage.cards.c.ConsignToDream.class));
        cards.add(new SetCardInfo("Corrosive Mentor", 61, Rarity.UNCOMMON, mage.cards.c.CorrosiveMentor.class));
        cards.add(new SetCardInfo("Corrupt", 62, Rarity.UNCOMMON, mage.cards.c.Corrupt.class));
        cards.add(new SetCardInfo("Counterbore", 33, Rarity.RARE, mage.cards.c.Counterbore.class));
        cards.add(new SetCardInfo("Crabapple Cohort", 109, Rarity.COMMON, mage.cards.c.CrabappleCohort.class));
        cards.add(new SetCardInfo("Cragganwick Cremator", 87, Rarity.RARE, mage.cards.c.CragganwickCremator.class));
        cards.add(new SetCardInfo("Crimson Wisps", 88, Rarity.COMMON, mage.cards.c.CrimsonWisps.class));
        cards.add(new SetCardInfo("Crowd of Cinders", 63, Rarity.UNCOMMON, mage.cards.c.CrowdOfCinders.class));
        cards.add(new SetCardInfo("Cultbrand Cinder", 182, Rarity.COMMON, mage.cards.c.CultbrandCinder.class));
        cards.add(new SetCardInfo("Curse of Chains", 139, Rarity.COMMON, mage.cards.c.CurseOfChains.class));
        cards.add(new SetCardInfo("Cursecatcher", 34, Rarity.UNCOMMON, mage.cards.c.Cursecatcher.class));
        cards.add(new SetCardInfo("Dawnglow Infusion", 225, Rarity.UNCOMMON, mage.cards.d.DawnglowInfusion.class));
        cards.add(new SetCardInfo("Deep-Slumber Titan", 89, Rarity.RARE, mage.cards.d.DeepSlumberTitan.class));
        cards.add(new SetCardInfo("Deepchannel Mentor", 35, Rarity.UNCOMMON, mage.cards.d.DeepchannelMentor.class));
        cards.add(new SetCardInfo("Demigod of Revenge", 183, Rarity.RARE, mage.cards.d.DemigodOfRevenge.class));
        cards.add(new SetCardInfo("Deus of Calamity", 204, Rarity.RARE, mage.cards.d.DeusOfCalamity.class));
        cards.add(new SetCardInfo("Devoted Druid", 110, Rarity.COMMON, mage.cards.d.DevotedDruid.class));
        cards.add(new SetCardInfo("Din of the Fireherd", 184, Rarity.RARE, mage.cards.d.DinOfTheFireherd.class));
        cards.add(new SetCardInfo("Dire Undercurrents", 159, Rarity.RARE, mage.cards.d.DireUndercurrents.class));
        cards.add(new SetCardInfo("Disturbing Plot", 64, Rarity.COMMON, mage.cards.d.DisturbingPlot.class));
        cards.add(new SetCardInfo("Dramatic Entrance", 111, Rarity.RARE, mage.cards.d.DramaticEntrance.class));
        cards.add(new SetCardInfo("Dream Salvage", 160, Rarity.UNCOMMON, mage.cards.d.DreamSalvage.class));
        cards.add(new SetCardInfo("Drove of Elves", 112, Rarity.UNCOMMON, mage.cards.d.DroveOfElves.class));
        cards.add(new SetCardInfo("Drowner Initiate", 36, Rarity.COMMON, mage.cards.d.DrownerInitiate.class));
        cards.add(new SetCardInfo("Dusk Urchins", 65, Rarity.RARE, mage.cards.d.DuskUrchins.class));
        cards.add(new SetCardInfo("Elemental Mastery", 90, Rarity.RARE, mage.cards.e.ElementalMastery.class));
        cards.add(new SetCardInfo("Elsewhere Flask", 250, Rarity.COMMON, mage.cards.e.ElsewhereFlask.class));
        cards.add(new SetCardInfo("Elvish Hexhunter", 226, Rarity.COMMON, mage.cards.e.ElvishHexhunter.class));
        cards.add(new SetCardInfo("Ember Gale", 91, Rarity.COMMON, mage.cards.e.EmberGale.class));
        cards.add(new SetCardInfo("Emberstrike Duo", 185, Rarity.COMMON, mage.cards.e.EmberstrikeDuo.class));
        cards.add(new SetCardInfo("Enchanted Evening", 140, Rarity.RARE, mage.cards.e.EnchantedEvening.class));
        cards.add(new SetCardInfo("Everlasting Torment", 186, Rarity.RARE, mage.cards.e.EverlastingTorment.class));
        cards.add(new SetCardInfo("Faerie Macabre", 66, Rarity.COMMON, mage.cards.f.FaerieMacabre.class));
        cards.add(new SetCardInfo("Faerie Swarm", 37, Rarity.UNCOMMON, mage.cards.f.FaerieSwarm.class));
        cards.add(new SetCardInfo("Farhaven Elf", 113, Rarity.COMMON, mage.cards.f.FarhavenElf.class));
        cards.add(new SetCardInfo("Fate Transfer", 161, Rarity.COMMON, mage.cards.f.FateTransfer.class));
        cards.add(new SetCardInfo("Fire-Lit Thicket", 271, Rarity.RARE, mage.cards.f.FireLitThicket.class));
        cards.add(new SetCardInfo("Firespout", 205, Rarity.UNCOMMON, mage.cards.f.Firespout.class));
        cards.add(new SetCardInfo("Fists of the Demigod", 187, Rarity.COMMON, mage.cards.f.FistsOfTheDemigod.class));
        cards.add(new SetCardInfo("Flame Javelin", 92, Rarity.UNCOMMON, mage.cards.f.FlameJavelin.class));
        cards.add(new SetCardInfo("Flourishing Defenses", 114, Rarity.UNCOMMON, mage.cards.f.FlourishingDefenses.class));
        cards.add(new SetCardInfo("Flow of Ideas", 38, Rarity.UNCOMMON, mage.cards.f.FlowOfIdeas.class));
        cards.add(new SetCardInfo("Forest", 298, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 299, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 300, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 301, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fossil Find", 206, Rarity.UNCOMMON, mage.cards.f.FossilFind.class));
        cards.add(new SetCardInfo("Foxfire Oak", 115, Rarity.COMMON, mage.cards.f.FoxfireOak.class));
        cards.add(new SetCardInfo("Fracturing Gust", 227, Rarity.RARE, mage.cards.f.FracturingGust.class));
        cards.add(new SetCardInfo("Fulminator Mage", 188, Rarity.RARE, mage.cards.f.FulminatorMage.class));
        cards.add(new SetCardInfo("Furystoke Giant", 93, Rarity.RARE, mage.cards.f.FurystokeGiant.class));
        cards.add(new SetCardInfo("Ghastlord of Fugue", 162, Rarity.RARE, mage.cards.g.GhastlordOfFugue.class));
        cards.add(new SetCardInfo("Ghastly Discovery", 39, Rarity.COMMON, mage.cards.g.GhastlyDiscovery.class));
        cards.add(new SetCardInfo("Giantbaiting", 207, Rarity.COMMON, mage.cards.g.Giantbaiting.class));
        cards.add(new SetCardInfo("Glamer Spinners", 141, Rarity.UNCOMMON, mage.cards.g.GlamerSpinners.class));
        cards.add(new SetCardInfo("Gleeful Sabotage", 116, Rarity.COMMON, mage.cards.g.GleefulSabotage.class));
        cards.add(new SetCardInfo("Glen Elendra Liege", 163, Rarity.RARE, mage.cards.g.GlenElendraLiege.class));
        cards.add(new SetCardInfo("Gloomlance", 67, Rarity.COMMON, mage.cards.g.Gloomlance.class));
        cards.add(new SetCardInfo("Gloomwidow", 117, Rarity.UNCOMMON, mage.cards.g.Gloomwidow.class));
        cards.add(new SetCardInfo("Gloomwidow's Feast", 118, Rarity.COMMON, mage.cards.g.GloomwidowsFeast.class));
        cards.add(new SetCardInfo("Gnarled Effigy", 251, Rarity.UNCOMMON, mage.cards.g.GnarledEffigy.class));
        cards.add(new SetCardInfo("Godhead of Awe", 142, Rarity.RARE, mage.cards.g.GodheadOfAwe.class));
        cards.add(new SetCardInfo("Goldenglow Moth", 6, Rarity.COMMON, mage.cards.g.GoldenglowMoth.class));
        cards.add(new SetCardInfo("Gravelgill Axeshark", 164, Rarity.COMMON, mage.cards.g.GravelgillAxeshark.class));
        cards.add(new SetCardInfo("Gravelgill Duo", 165, Rarity.COMMON, mage.cards.g.GravelgillDuo.class));
        cards.add(new SetCardInfo("Graven Cairns", 272, Rarity.RARE, mage.cards.g.GravenCairns.class));
        cards.add(new SetCardInfo("Greater Auramancy", 7, Rarity.RARE, mage.cards.g.GreaterAuramancy.class));
        cards.add(new SetCardInfo("Grief Tyrant", 189, Rarity.UNCOMMON, mage.cards.g.GriefTyrant.class));
        cards.add(new SetCardInfo("Grim Poppet", 252, Rarity.RARE, mage.cards.g.GrimPoppet.class));
        cards.add(new SetCardInfo("Guttural Response", 208, Rarity.UNCOMMON, mage.cards.g.GutturalResponse.class));
        cards.add(new SetCardInfo("Heap Doll", 253, Rarity.UNCOMMON, mage.cards.h.HeapDoll.class));
        cards.add(new SetCardInfo("Heartmender", 228, Rarity.RARE, mage.cards.h.Heartmender.class));
        cards.add(new SetCardInfo("Helm of the Ghastlord", 166, Rarity.COMMON, mage.cards.h.HelmOfTheGhastlord.class));
        cards.add(new SetCardInfo("Hollowborn Barghest", 68, Rarity.RARE, mage.cards.h.HollowbornBarghest.class));
        cards.add(new SetCardInfo("Hollowsage", 69, Rarity.UNCOMMON, mage.cards.h.Hollowsage.class));
        cards.add(new SetCardInfo("Horde of Boggarts", 94, Rarity.UNCOMMON, mage.cards.h.HordeOfBoggarts.class));
        cards.add(new SetCardInfo("Howl of the Night Pack", 119, Rarity.UNCOMMON, mage.cards.h.HowlOfTheNightPack.class));
        cards.add(new SetCardInfo("Hungry Spriggan", 120, Rarity.COMMON, mage.cards.h.HungrySpriggan.class));
        cards.add(new SetCardInfo("Illuminated Folio", 254, Rarity.UNCOMMON, mage.cards.i.IlluminatedFolio.class));
        cards.add(new SetCardInfo("Impromptu Raid", 209, Rarity.RARE, mage.cards.i.ImpromptuRaid.class));
        cards.add(new SetCardInfo("Incremental Blight", 70, Rarity.UNCOMMON, mage.cards.i.IncrementalBlight.class));
        cards.add(new SetCardInfo("Inescapable Brute", 95, Rarity.COMMON, mage.cards.i.InescapableBrute.class));
        cards.add(new SetCardInfo("Inkfathom Infiltrator", 167, Rarity.UNCOMMON, mage.cards.i.InkfathomInfiltrator.class));
        cards.add(new SetCardInfo("Inkfathom Witch", 168, Rarity.UNCOMMON, mage.cards.i.InkfathomWitch.class));
        cards.add(new SetCardInfo("Inquisitor's Snare", 8, Rarity.COMMON, mage.cards.i.InquisitorsSnare.class));
        cards.add(new SetCardInfo("Intimidator Initiate", 96, Rarity.COMMON, mage.cards.i.IntimidatorInitiate.class));
        cards.add(new SetCardInfo("Island", 286, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 287, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 288, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 289, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Isleback Spawn", 40, Rarity.RARE, mage.cards.i.IslebackSpawn.class));
        cards.add(new SetCardInfo("Jaws of Stone", 97, Rarity.UNCOMMON, mage.cards.j.JawsOfStone.class));
        cards.add(new SetCardInfo("Juvenile Gloomwidow", 121, Rarity.COMMON, mage.cards.j.JuvenileGloomwidow.class));
        cards.add(new SetCardInfo("Kinscaer Harpoonist", 41, Rarity.COMMON, mage.cards.k.KinscaerHarpoonist.class));
        cards.add(new SetCardInfo("Kitchen Finks", 229, Rarity.UNCOMMON, mage.cards.k.KitchenFinks.class));
        cards.add(new SetCardInfo("Kithkin Rabble", 9, Rarity.UNCOMMON, mage.cards.k.KithkinRabble.class));
        cards.add(new SetCardInfo("Kithkin Shielddare", 10, Rarity.COMMON, mage.cards.k.KithkinShielddare.class));
        cards.add(new SetCardInfo("Knacksaw Clique", 42, Rarity.RARE, mage.cards.k.KnacksawClique.class));
        cards.add(new SetCardInfo("Knollspine Dragon", 98, Rarity.RARE, mage.cards.k.KnollspineDragon.class));
        cards.add(new SetCardInfo("Knollspine Invocation", 99, Rarity.RARE, mage.cards.k.KnollspineInvocation.class));
        cards.add(new SetCardInfo("Kulrath Knight", 190, Rarity.UNCOMMON, mage.cards.k.KulrathKnight.class));
        cards.add(new SetCardInfo("Last Breath", 11, Rarity.COMMON, mage.cards.l.LastBreath.class));
        cards.add(new SetCardInfo("Leech Bonder", 43, Rarity.UNCOMMON, mage.cards.l.LeechBonder.class));
        cards.add(new SetCardInfo("Leechridden Swamp", 273, Rarity.UNCOMMON, mage.cards.l.LeechriddenSwamp.class));
        cards.add(new SetCardInfo("Loamdragger Giant", 210, Rarity.COMMON, mage.cards.l.LoamdraggerGiant.class));
        cards.add(new SetCardInfo("Loch Korrigan", 71, Rarity.COMMON, mage.cards.l.LochKorrigan.class));
        cards.add(new SetCardInfo("Lockjaw Snapper", 255, Rarity.UNCOMMON, mage.cards.l.LockjawSnapper.class));
        cards.add(new SetCardInfo("Lurebound Scarecrow", 256, Rarity.UNCOMMON, mage.cards.l.LureboundScarecrow.class));
        cards.add(new SetCardInfo("Madblind Mountain", 274, Rarity.UNCOMMON, mage.cards.m.MadblindMountain.class));
        cards.add(new SetCardInfo("Mana Reflection", 122, Rarity.RARE, mage.cards.m.ManaReflection.class));
        cards.add(new SetCardInfo("Manaforge Cinder", 191, Rarity.COMMON, mage.cards.m.ManaforgeCinder.class));
        cards.add(new SetCardInfo("Manamorphose", 211, Rarity.COMMON, mage.cards.m.Manamorphose.class));
        cards.add(new SetCardInfo("Mass Calcify", 12, Rarity.RARE, mage.cards.m.MassCalcify.class));
        cards.add(new SetCardInfo("Medicine Runner", 230, Rarity.COMMON, mage.cards.m.MedicineRunner.class));
        cards.add(new SetCardInfo("Memory Plunder", 169, Rarity.RARE, mage.cards.m.MemoryPlunder.class));
        cards.add(new SetCardInfo("Memory Sluice", 170, Rarity.COMMON, mage.cards.m.MemorySluice.class));
        cards.add(new SetCardInfo("Mercy Killing", 231, Rarity.UNCOMMON, mage.cards.m.MercyKilling.class));
        cards.add(new SetCardInfo("Merrow Grimeblotter", 171, Rarity.UNCOMMON, mage.cards.m.MerrowGrimeblotter.class));
        cards.add(new SetCardInfo("Merrow Wavebreakers", 44, Rarity.COMMON, mage.cards.m.MerrowWavebreakers.class));
        cards.add(new SetCardInfo("Midnight Banshee", 72, Rarity.RARE, mage.cards.m.MidnightBanshee.class));
        cards.add(new SetCardInfo("Mine Excavation", 13, Rarity.COMMON, mage.cards.m.MineExcavation.class));
        cards.add(new SetCardInfo("Mirrorweave", 143, Rarity.RARE, mage.cards.m.Mirrorweave.class));
        cards.add(new SetCardInfo("Mistmeadow Skulk", 14, Rarity.UNCOMMON, mage.cards.m.MistmeadowSkulk.class));
        cards.add(new SetCardInfo("Mistmeadow Witch", 144, Rarity.UNCOMMON, mage.cards.m.MistmeadowWitch.class));
        cards.add(new SetCardInfo("Mistveil Plains", 275, Rarity.UNCOMMON, mage.cards.m.MistveilPlains.class));
        cards.add(new SetCardInfo("Moonring Island", 276, Rarity.UNCOMMON, mage.cards.m.MoonringIsland.class));
        cards.add(new SetCardInfo("Morselhoarder", 212, Rarity.COMMON, mage.cards.m.Morselhoarder.class));
        cards.add(new SetCardInfo("Mossbridge Troll", 123, Rarity.RARE, mage.cards.m.MossbridgeTroll.class));
        cards.add(new SetCardInfo("Mountain", 294, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 295, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 296, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 297, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mudbrawler Cohort", 100, Rarity.COMMON, mage.cards.m.MudbrawlerCohort.class));
        cards.add(new SetCardInfo("Mudbrawler Raiders", 213, Rarity.COMMON, mage.cards.m.MudbrawlerRaiders.class));
        cards.add(new SetCardInfo("Murderous Redcap", 192, Rarity.UNCOMMON, mage.cards.m.MurderousRedcap.class));
        cards.add(new SetCardInfo("Mystic Gate", 277, Rarity.RARE, mage.cards.m.MysticGate.class));
        cards.add(new SetCardInfo("Niveous Wisps", 15, Rarity.COMMON, mage.cards.n.NiveousWisps.class));
        cards.add(new SetCardInfo("Nurturer Initiate", 124, Rarity.COMMON, mage.cards.n.NurturerInitiate.class));
        cards.add(new SetCardInfo("Old Ghastbark", 232, Rarity.COMMON, mage.cards.o.OldGhastbark.class));
        cards.add(new SetCardInfo("Oona's Gatewarden", 173, Rarity.COMMON, mage.cards.o.OonasGatewarden.class));
        cards.add(new SetCardInfo("Oona, Queen of the Fae", 172, Rarity.RARE, mage.cards.o.OonaQueenOfTheFae.class));
        cards.add(new SetCardInfo("Oracle of Nectars", 233, Rarity.RARE, mage.cards.o.OracleOfNectars.class));
        cards.add(new SetCardInfo("Order of Whiteclay", 16, Rarity.RARE, mage.cards.o.OrderOfWhiteclay.class));
        cards.add(new SetCardInfo("Oversoul of Dusk", 234, Rarity.RARE, mage.cards.o.OversoulOfDusk.class));
        cards.add(new SetCardInfo("Painter's Servant", 257, Rarity.RARE, mage.cards.p.PaintersServant.class));
        cards.add(new SetCardInfo("Pale Wayfarer", 17, Rarity.UNCOMMON, mage.cards.p.PaleWayfarer.class));
        cards.add(new SetCardInfo("Parapet Watchers", 45, Rarity.COMMON, mage.cards.p.ParapetWatchers.class));
        cards.add(new SetCardInfo("Pili-Pala", 258, Rarity.COMMON, mage.cards.p.PiliPala.class));
        cards.add(new SetCardInfo("Plague of Vermin", 73, Rarity.RARE, mage.cards.p.PlagueOfVermin.class));
        cards.add(new SetCardInfo("Plains", 282, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 283, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 284, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 285, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plumeveil", 145, Rarity.UNCOMMON, mage.cards.p.Plumeveil.class));
        cards.add(new SetCardInfo("Poison the Well", 193, Rarity.COMMON, mage.cards.p.PoisonTheWell.class));
        cards.add(new SetCardInfo("Polluted Bonds", 74, Rarity.RARE, mage.cards.p.PollutedBonds.class));
        cards.add(new SetCardInfo("Power of Fire", 101, Rarity.COMMON, mage.cards.p.PowerOfFire.class));
        cards.add(new SetCardInfo("Presence of Gond", 125, Rarity.COMMON, mage.cards.p.PresenceOfGond.class));
        cards.add(new SetCardInfo("Prismatic Omen", 126, Rarity.RARE, mage.cards.p.PrismaticOmen.class));
        cards.add(new SetCardInfo("Prismwake Merrow", 46, Rarity.COMMON, mage.cards.p.PrismwakeMerrow.class));
        cards.add(new SetCardInfo("Prison Term", 18, Rarity.UNCOMMON, mage.cards.p.PrisonTerm.class));
        cards.add(new SetCardInfo("Puca's Mischief", 47, Rarity.RARE, mage.cards.p.PucasMischief.class));
        cards.add(new SetCardInfo("Puncture Bolt", 102, Rarity.COMMON, mage.cards.p.PunctureBolt.class));
        cards.add(new SetCardInfo("Puppeteer Clique", 75, Rarity.RARE, mage.cards.p.PuppeteerClique.class));
        cards.add(new SetCardInfo("Puresight Merrow", 146, Rarity.UNCOMMON, mage.cards.p.PuresightMerrow.class));
        cards.add(new SetCardInfo("Put Away", 48, Rarity.COMMON, mage.cards.p.PutAway.class));
        cards.add(new SetCardInfo("Pyre Charger", 103, Rarity.UNCOMMON, mage.cards.p.PyreCharger.class));
        cards.add(new SetCardInfo("Rage Reflection", 104, Rarity.RARE, mage.cards.r.RageReflection.class));
        cards.add(new SetCardInfo("Raking Canopy", 127, Rarity.UNCOMMON, mage.cards.r.RakingCanopy.class));
        cards.add(new SetCardInfo("Rattleblaze Scarecrow", 259, Rarity.COMMON, mage.cards.r.RattleblazeScarecrow.class));
        cards.add(new SetCardInfo("Raven's Run Dragoon", 235, Rarity.COMMON, mage.cards.r.RavensRunDragoon.class));
        cards.add(new SetCardInfo("Reaper King", 260, Rarity.RARE, mage.cards.r.ReaperKing.class));
        cards.add(new SetCardInfo("Reflecting Pool", "278*", Rarity.RARE, mage.cards.r.ReflectingPool.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reflecting Pool", 278, Rarity.RARE, mage.cards.r.ReflectingPool.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Reknit", 236, Rarity.UNCOMMON, mage.cards.r.Reknit.class));
        cards.add(new SetCardInfo("Repel Intruders", 147, Rarity.UNCOMMON, mage.cards.r.RepelIntruders.class));
        cards.add(new SetCardInfo("Resplendent Mentor", 19, Rarity.UNCOMMON, mage.cards.r.ResplendentMentor.class));
        cards.add(new SetCardInfo("Revelsong Horn", 261, Rarity.UNCOMMON, mage.cards.r.RevelsongHorn.class));
        cards.add(new SetCardInfo("Rhys the Redeemed", 237, Rarity.RARE, mage.cards.r.RhysTheRedeemed.class));
        cards.add(new SetCardInfo("Rite of Consumption", 76, Rarity.COMMON, mage.cards.r.RiteOfConsumption.class));
        cards.add(new SetCardInfo("River Kelpie", 49, Rarity.RARE, mage.cards.r.RiverKelpie.class));
        cards.add(new SetCardInfo("River's Grasp", 174, Rarity.UNCOMMON, mage.cards.r.RiversGrasp.class));
        cards.add(new SetCardInfo("Rosheen Meanderer", 214, Rarity.RARE, mage.cards.r.RosheenMeanderer.class));
        cards.add(new SetCardInfo("Roughshod Mentor", 128, Rarity.UNCOMMON, mage.cards.r.RoughshodMentor.class));
        cards.add(new SetCardInfo("Rune-Cervin Rider", 20, Rarity.COMMON, mage.cards.r.RuneCervinRider.class));
        cards.add(new SetCardInfo("Runed Halo", 21, Rarity.RARE, mage.cards.r.RunedHalo.class));
        cards.add(new SetCardInfo("Runes of the Deus", 215, Rarity.COMMON, mage.cards.r.RunesOfTheDeus.class));
        cards.add(new SetCardInfo("Rustrazor Butcher", 105, Rarity.COMMON, mage.cards.r.RustrazorButcher.class));
        cards.add(new SetCardInfo("Safehold Duo", 238, Rarity.COMMON, mage.cards.s.SafeholdDuo.class));
        cards.add(new SetCardInfo("Safehold Elite", 239, Rarity.COMMON, mage.cards.s.SafeholdElite.class));
        cards.add(new SetCardInfo("Safehold Sentry", 22, Rarity.COMMON, mage.cards.s.SafeholdSentry.class));
        cards.add(new SetCardInfo("Safewright Quest", 240, Rarity.COMMON, mage.cards.s.SafewrightQuest.class));
        cards.add(new SetCardInfo("Sapseep Forest", 279, Rarity.UNCOMMON, mage.cards.s.SapseepForest.class));
        cards.add(new SetCardInfo("Savor the Moment", 50, Rarity.RARE, mage.cards.s.SavorTheMoment.class));
        cards.add(new SetCardInfo("Scar", 194, Rarity.COMMON, mage.cards.s.Scar.class));
        cards.add(new SetCardInfo("Scarscale Ritual", 175, Rarity.COMMON, mage.cards.s.ScarscaleRitual.class));
        cards.add(new SetCardInfo("Scrapbasket", 262, Rarity.COMMON, mage.cards.s.Scrapbasket.class));
        cards.add(new SetCardInfo("Scuttlemutt", 263, Rarity.COMMON, mage.cards.s.Scuttlemutt.class));
        cards.add(new SetCardInfo("Scuzzback Marauders", 216, Rarity.COMMON, mage.cards.s.ScuzzbackMarauders.class));
        cards.add(new SetCardInfo("Scuzzback Scrapper", 217, Rarity.COMMON, mage.cards.s.ScuzzbackScrapper.class));
        cards.add(new SetCardInfo("Seedcradle Witch", 241, Rarity.UNCOMMON, mage.cards.s.SeedcradleWitch.class));
        cards.add(new SetCardInfo("Shield of the Oversoul", 242, Rarity.COMMON, mage.cards.s.ShieldOfTheOversoul.class));
        cards.add(new SetCardInfo("Sickle Ripper", 77, Rarity.COMMON, mage.cards.s.SickleRipper.class));
        cards.add(new SetCardInfo("Silkbind Faerie", 148, Rarity.COMMON, mage.cards.s.SilkbindFaerie.class));
        cards.add(new SetCardInfo("Sinking Feeling", 51, Rarity.COMMON, mage.cards.s.SinkingFeeling.class));
        cards.add(new SetCardInfo("Slinking Giant", 106, Rarity.UNCOMMON, mage.cards.s.SlinkingGiant.class));
        cards.add(new SetCardInfo("Smash to Smithereens", 107, Rarity.COMMON, mage.cards.s.SmashToSmithereens.class));
        cards.add(new SetCardInfo("Smolder Initiate", 78, Rarity.COMMON, mage.cards.s.SmolderInitiate.class));
        cards.add(new SetCardInfo("Somnomancer", 149, Rarity.COMMON, mage.cards.s.Somnomancer.class));
        cards.add(new SetCardInfo("Sootstoke Kindler", 195, Rarity.COMMON, mage.cards.s.SootstokeKindler.class));
        cards.add(new SetCardInfo("Sootwalkers", 196, Rarity.COMMON, mage.cards.s.Sootwalkers.class));
        cards.add(new SetCardInfo("Spawnwrithe", 129, Rarity.RARE, mage.cards.s.Spawnwrithe.class));
        cards.add(new SetCardInfo("Spectral Procession", 23, Rarity.UNCOMMON, mage.cards.s.SpectralProcession.class));
        cards.add(new SetCardInfo("Spell Syphon", 52, Rarity.COMMON, mage.cards.s.SpellSyphon.class));
        cards.add(new SetCardInfo("Spiteflame Witch", 197, Rarity.UNCOMMON, mage.cards.s.SpiteflameWitch.class));
        cards.add(new SetCardInfo("Spiteful Visions", 198, Rarity.RARE, mage.cards.s.SpitefulVisions.class));
        cards.add(new SetCardInfo("Splitting Headache", 79, Rarity.COMMON, mage.cards.s.SplittingHeadache.class));
        cards.add(new SetCardInfo("Steel of the Godhead", 150, Rarity.COMMON, mage.cards.s.SteelOfTheGodhead.class));
        cards.add(new SetCardInfo("Strip Bare", 24, Rarity.COMMON, mage.cards.s.StripBare.class));
        cards.add(new SetCardInfo("Sunken Ruins", 280, Rarity.RARE, mage.cards.s.SunkenRuins.class));
        cards.add(new SetCardInfo("Swamp", 290, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 291, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 292, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 293, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swans of Bryn Argoll", 151, Rarity.RARE, mage.cards.s.SwansOfBrynArgoll.class));
        cards.add(new SetCardInfo("Sygg, River Cutthroat", 176, Rarity.RARE, mage.cards.s.SyggRiverCutthroat.class));
        cards.add(new SetCardInfo("Tatterkite", 264, Rarity.UNCOMMON, mage.cards.t.Tatterkite.class));
        cards.add(new SetCardInfo("Tattermunge Duo", 218, Rarity.COMMON, mage.cards.t.TattermungeDuo.class));
        cards.add(new SetCardInfo("Tattermunge Maniac", 219, Rarity.UNCOMMON, mage.cards.t.TattermungeManiac.class));
        cards.add(new SetCardInfo("Tattermunge Witch", 220, Rarity.UNCOMMON, mage.cards.t.TattermungeWitch.class));
        cards.add(new SetCardInfo("Thistledown Duo", 152, Rarity.COMMON, mage.cards.t.ThistledownDuo.class));
        cards.add(new SetCardInfo("Thistledown Liege", 153, Rarity.RARE, mage.cards.t.ThistledownLiege.class));
        cards.add(new SetCardInfo("Thornwatch Scarecrow", 265, Rarity.COMMON, mage.cards.t.ThornwatchScarecrow.class));
        cards.add(new SetCardInfo("Thought Reflection", 53, Rarity.RARE, mage.cards.t.ThoughtReflection.class));
        cards.add(new SetCardInfo("Thoughtweft Gambit", 154, Rarity.UNCOMMON, mage.cards.t.ThoughtweftGambit.class));
        cards.add(new SetCardInfo("Toil to Renown", 130, Rarity.COMMON, mage.cards.t.ToilToRenown.class));
        cards.add(new SetCardInfo("Torpor Dust", 177, Rarity.COMMON, mage.cards.t.TorporDust.class));
        cards.add(new SetCardInfo("Torrent of Souls", 199, Rarity.UNCOMMON, mage.cards.t.TorrentOfSouls.class));
        cards.add(new SetCardInfo("Torture", 80, Rarity.COMMON, mage.cards.t.Torture.class));
        cards.add(new SetCardInfo("Tower Above", 131, Rarity.UNCOMMON, mage.cards.t.TowerAbove.class));
        cards.add(new SetCardInfo("Traitor's Roar", 200, Rarity.COMMON, mage.cards.t.TraitorsRoar.class));
        cards.add(new SetCardInfo("Trip Noose", 266, Rarity.UNCOMMON, mage.cards.t.TripNoose.class));
        cards.add(new SetCardInfo("Turn to Mist", 155, Rarity.COMMON, mage.cards.t.TurnToMist.class));
        cards.add(new SetCardInfo("Twilight Shepherd", 25, Rarity.RARE, mage.cards.t.TwilightShepherd.class));
        cards.add(new SetCardInfo("Tyrannize", 201, Rarity.RARE, mage.cards.t.Tyrannize.class));
        cards.add(new SetCardInfo("Umbral Mantle", 267, Rarity.UNCOMMON, mage.cards.u.UmbralMantle.class));
        cards.add(new SetCardInfo("Valleymaker", 221, Rarity.RARE, mage.cards.v.Valleymaker.class));
        cards.add(new SetCardInfo("Vexing Shusher", 222, Rarity.RARE, mage.cards.v.VexingShusher.class));
        cards.add(new SetCardInfo("Viridescent Wisps", 132, Rarity.COMMON, mage.cards.v.ViridescentWisps.class));
        cards.add(new SetCardInfo("Wanderbrine Rootcutters", 178, Rarity.COMMON, mage.cards.w.WanderbrineRootcutters.class));
        cards.add(new SetCardInfo("Wasp Lancer", 179, Rarity.UNCOMMON, mage.cards.w.WaspLancer.class));
        cards.add(new SetCardInfo("Watchwing Scarecrow", 268, Rarity.COMMON, mage.cards.w.WatchwingScarecrow.class));
        cards.add(new SetCardInfo("Wheel of Sun and Moon", 243, Rarity.RARE, mage.cards.w.WheelOfSunAndMoon.class));
        cards.add(new SetCardInfo("Whimwader", 54, Rarity.COMMON, mage.cards.w.Whimwader.class));
        cards.add(new SetCardInfo("Wicker Warcrawler", 269, Rarity.UNCOMMON, mage.cards.w.WickerWarcrawler.class));
        cards.add(new SetCardInfo("Wild Swing", 108, Rarity.UNCOMMON, mage.cards.w.WildSwing.class));
        cards.add(new SetCardInfo("Wildslayer Elves", 133, Rarity.COMMON, mage.cards.w.WildslayerElves.class));
        cards.add(new SetCardInfo("Wilt-Leaf Cavaliers", 244, Rarity.UNCOMMON, mage.cards.w.WiltLeafCavaliers.class));
        cards.add(new SetCardInfo("Wilt-Leaf Liege", 245, Rarity.RARE, mage.cards.w.WiltLeafLiege.class));
        cards.add(new SetCardInfo("Windbrisk Raptor", 26, Rarity.RARE, mage.cards.w.WindbriskRaptor.class));
        cards.add(new SetCardInfo("Wingrattle Scarecrow", 270, Rarity.COMMON, mage.cards.w.WingrattleScarecrow.class));
        cards.add(new SetCardInfo("Witherscale Wurm", 134, Rarity.RARE, mage.cards.w.WitherscaleWurm.class));
        cards.add(new SetCardInfo("Woeleecher", 27, Rarity.COMMON, mage.cards.w.Woeleecher.class));
        cards.add(new SetCardInfo("Wooded Bastion", 281, Rarity.RARE, mage.cards.w.WoodedBastion.class));
        cards.add(new SetCardInfo("Woodfall Primus", 135, Rarity.RARE, mage.cards.w.WoodfallPrimus.class));
        cards.add(new SetCardInfo("Worldpurge", 156, Rarity.RARE, mage.cards.w.Worldpurge.class));
        cards.add(new SetCardInfo("Wort, the Raidmother", 223, Rarity.RARE, mage.cards.w.WortTheRaidmother.class));
        cards.add(new SetCardInfo("Wound Reflection", 81, Rarity.RARE, mage.cards.w.WoundReflection.class));
        cards.add(new SetCardInfo("Zealous Guardian", 157, Rarity.COMMON, mage.cards.z.ZealousGuardian.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new ShadowmoorCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/shm.html
// order not known for uncommon runs, but card division is
// pack distribution for uncommons not specified, assuming AAB ABB as in Onslaught
class ShadowmoorCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "238", "263", "230", "39", "133", "67", "102", "212", "249", "11", "30", "196", "82", "125", "242", "41", "182", "100", "113", "11", "178", "196", "22", "102", "238", "270", "232", "39", "173", "86", "133", "60", "148", "178", "194", "85", "125", "30", "22", "32", "182", "82", "212", "270", "242", "44", "67", "85", "235", "215", "230", "41", "60", "86", "113", "263", "232", "44", "194", "100", "235", "249", "148", "32", "173", "215");
    private final CardRun commonB = new CardRun(true, "20", "121", "218", "77", "177", "247", "149", "109", "136", "164", "195", "150", "239", "124", "213", "80", "177", "268", "138", "216", "66", "200", "139", "149", "259", "110", "152", "187", "166", "165", "20", "216", "101", "64", "45", "268", "3", "224", "195", "66", "139", "166", "152", "121", "101", "164", "150", "259", "138", "110", "218", "64", "124", "136", "239", "224", "213", "77", "45", "247", "3", "109", "200", "165", "80", "187");
    private final CardRun commonC = new CardRun(true, "265", "13", "52", "95", "115", "79", "27", "46", "88", "132", "59", "13", "246", "52", "105", "55", "10", "161", "193", "116", "56", "1", "54", "130", "265", "105", "240", "55", "27", "170", "207", "246", "185", "116", "10", "46", "95", "240", "79", "4", "170", "193", "115", "258", "59", "1", "161", "88", "130", "185", "4", "54", "207", "132", "56");
    private final CardRun commonD = new CardRun(true, "107", "78", "262", "24", "36", "175", "96", "51", "8", "118", "191", "217", "226", "6", "157", "71", "91", "15", "210", "36", "258", "84", "211", "155", "175", "250", "31", "91", "78", "118", "51", "6", "107", "120", "71", "262", "217", "76", "8", "48", "191", "211", "31", "24", "120", "96", "250", "15", "157", "76", "84", "210", "48", "155", "226");
    private final CardRun uncommonA = new CardRun(false, "28", "57", "83", "58", "61", "63", "34", "225", "35", "160", "112", "38", "206", "208", "253", "69", "94", "168", "9", "273", "255", "256", "274", "231", "171", "14", "275", "276", "17", "103", "127", "236", "19", "261", "128", "279", "106", "197", "267", "108");
    private final CardRun uncommonB = new CardRun(false, "2", "180", "29", "203", "62", "37", "205", "92", "114", "141", "117", "251", "189", "119", "254", "70", "167", "97", "229", "190", "43", "144", "192", "145", "18", "146", "147", "174", "241", "23", "264", "219", "220", "154", "199", "131", "266", "179", "269", "244");
    private final CardRun rare = new CardRun(false, "181", "137", "202", "5", "248", "158", "33", "87", "89", "183", "204", "184", "159", "111", "65", "90", "140", "186", "271", "227", "188", "93", "162", "163", "142", "272", "7", "252", "228", "68", "209", "40", "42", "98", "99", "122", "12", "169", "72", "143", "123", "277", "172", "233", "16", "234", "257", "73", "74", "126", "47", "75", "104", "260", "278", "237", "49", "214", "21", "50", "129", "198", "280", "151", "176", "153", "53", "25", "201", "221", "222", "243", "245", "26", "134", "281", "135", "156", "223", "81");

    private final BoosterStructure AAAAAACCCCC = new BoosterStructure(
            commonA, commonA, commonA, commonA, commonA, commonA,
            commonC, commonC, commonC, commonC, commonC
    );
    private final BoosterStructure AAAAAADDDDD = new BoosterStructure(
            commonA, commonA, commonA, commonA, commonA, commonA,
            commonD, commonD, commonD, commonD, commonD
    );
    private final BoosterStructure BBBBBBCCCCC = new BoosterStructure(
            commonB, commonB, commonB, commonB, commonB, commonB,
            commonC, commonC, commonC, commonC, commonC
    );
    private final BoosterStructure BBBBBBDDDDD = new BoosterStructure(
            commonB, commonB, commonB, commonB, commonB, commonB,
            commonD, commonD, commonD, commonD, commonD
    );
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB);
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB);

    private final BoosterStructure R1 = new BoosterStructure(rare);

    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AAAAAACCCCC, AAAAAADDDDD, BBBBBBCCCCC, BBBBBBDDDDD
    );

    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            AAB, ABB
    );

    private final RarityConfiguration rareRuns = new RarityConfiguration(R1);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        return booster;
    }
}
