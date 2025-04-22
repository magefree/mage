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
 *
 * @author noxx
 */
public final class UrzasLegacy extends ExpansionSet {

    private static final UrzasLegacy instance = new UrzasLegacy();

    public static UrzasLegacy getInstance() {
        return instance;
    }

    private UrzasLegacy() {
        super("Urza's Legacy", "ULG", ExpansionSet.buildDate(1999, 2, 15), SetType.EXPANSION);
        this.blockName = "Urza";
        this.parentSet = UrzasSaga.getInstance();
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;

        cards.add(new SetCardInfo("About Face", 73, Rarity.COMMON, mage.cards.a.AboutFace.class, RETRO_ART));
        cards.add(new SetCardInfo("Angel's Trumpet", 121, Rarity.UNCOMMON, mage.cards.a.AngelsTrumpet.class, RETRO_ART));
        cards.add(new SetCardInfo("Angelic Curator", 1, Rarity.COMMON, mage.cards.a.AngelicCurator.class, RETRO_ART));
        cards.add(new SetCardInfo("Anthroplasm", 25, Rarity.RARE, mage.cards.a.Anthroplasm.class, RETRO_ART));
        cards.add(new SetCardInfo("Archivist", 26, Rarity.RARE, mage.cards.a.Archivist.class, RETRO_ART));
        cards.add(new SetCardInfo("Aura Flux", 27, Rarity.COMMON, mage.cards.a.AuraFlux.class, RETRO_ART));
        cards.add(new SetCardInfo("Avalanche Riders", 74, Rarity.UNCOMMON, mage.cards.a.AvalancheRiders.class, RETRO_ART));
        cards.add(new SetCardInfo("Beast of Burden", 122, Rarity.RARE, mage.cards.b.BeastOfBurden.class, RETRO_ART));
        cards.add(new SetCardInfo("Blessed Reversal", 2, Rarity.RARE, mage.cards.b.BlessedReversal.class, RETRO_ART));
        cards.add(new SetCardInfo("Bloated Toad", 97, Rarity.UNCOMMON, mage.cards.b.BloatedToad.class, RETRO_ART));
        cards.add(new SetCardInfo("Bone Shredder", 49, Rarity.UNCOMMON, mage.cards.b.BoneShredder.class, RETRO_ART));
        cards.add(new SetCardInfo("Bouncing Beebles", 28, Rarity.COMMON, mage.cards.b.BouncingBeebles.class, RETRO_ART));
        cards.add(new SetCardInfo("Brink of Madness", 50, Rarity.RARE, mage.cards.b.BrinkOfMadness.class, RETRO_ART));
        cards.add(new SetCardInfo("Burst of Energy", 3, Rarity.COMMON, mage.cards.b.BurstOfEnergy.class, RETRO_ART));
        cards.add(new SetCardInfo("Cessation", 4, Rarity.COMMON, mage.cards.c.Cessation.class, RETRO_ART));
        cards.add(new SetCardInfo("Cloud of Faeries", 29, Rarity.COMMON, mage.cards.c.CloudOfFaeries.class, RETRO_ART));
        cards.add(new SetCardInfo("Crawlspace", 123, Rarity.RARE, mage.cards.c.Crawlspace.class, RETRO_ART));
        cards.add(new SetCardInfo("Crop Rotation", 98, Rarity.COMMON, mage.cards.c.CropRotation.class, RETRO_ART));
        cards.add(new SetCardInfo("Damping Engine", 124, Rarity.RARE, mage.cards.d.DampingEngine.class, RETRO_ART));
        cards.add(new SetCardInfo("Darkwatch Elves", 99, Rarity.UNCOMMON, mage.cards.d.DarkwatchElves.class, RETRO_ART));
        cards.add(new SetCardInfo("Defender of Chaos", 75, Rarity.COMMON, mage.cards.d.DefenderOfChaos.class, RETRO_ART));
        cards.add(new SetCardInfo("Defender of Law", 5, Rarity.COMMON, mage.cards.d.DefenderOfLaw.class, RETRO_ART));
        cards.add(new SetCardInfo("Defense Grid", 125, Rarity.RARE, mage.cards.d.DefenseGrid.class, RETRO_ART));
        cards.add(new SetCardInfo("Defense of the Heart", 100, Rarity.RARE, mage.cards.d.DefenseOfTheHeart.class, RETRO_ART));
        cards.add(new SetCardInfo("Delusions of Mediocrity", 30, Rarity.RARE, mage.cards.d.DelusionsOfMediocrity.class, RETRO_ART));
        cards.add(new SetCardInfo("Deranged Hermit", 101, Rarity.RARE, mage.cards.d.DerangedHermit.class, RETRO_ART));
        cards.add(new SetCardInfo("Devout Harpist", 6, Rarity.COMMON, mage.cards.d.DevoutHarpist.class, RETRO_ART));
        cards.add(new SetCardInfo("Engineered Plague", 51, Rarity.UNCOMMON, mage.cards.e.EngineeredPlague.class, RETRO_ART));
        cards.add(new SetCardInfo("Erase", 7, Rarity.COMMON, mage.cards.e.Erase.class, RETRO_ART));
        cards.add(new SetCardInfo("Eviscerator", 52, Rarity.RARE, mage.cards.e.Eviscerator.class, RETRO_ART));
        cards.add(new SetCardInfo("Expendable Troops", 8, Rarity.COMMON, mage.cards.e.ExpendableTroops.class, RETRO_ART));
        cards.add(new SetCardInfo("Faerie Conclave", 139, Rarity.UNCOMMON, mage.cards.f.FaerieConclave.class, RETRO_ART));
        cards.add(new SetCardInfo("Fleeting Image", 31, Rarity.RARE, mage.cards.f.FleetingImage.class, RETRO_ART));
        cards.add(new SetCardInfo("Fog of Gnats", 53, Rarity.COMMON, mage.cards.f.FogOfGnats.class, RETRO_ART));
        cards.add(new SetCardInfo("Forbidding Watchtower", 140, Rarity.UNCOMMON, mage.cards.f.ForbiddingWatchtower.class, RETRO_ART));
        cards.add(new SetCardInfo("Frantic Search", 32, Rarity.COMMON, mage.cards.f.FranticSearch.class, RETRO_ART));
        cards.add(new SetCardInfo("Gang of Elk", 102, Rarity.UNCOMMON, mage.cards.g.GangOfElk.class, RETRO_ART));
        cards.add(new SetCardInfo("Ghitu Encampment", 141, Rarity.UNCOMMON, mage.cards.g.GhituEncampment.class, RETRO_ART));
        cards.add(new SetCardInfo("Ghitu Fire-Eater", 76, Rarity.UNCOMMON, mage.cards.g.GhituFireEater.class, RETRO_ART));
        cards.add(new SetCardInfo("Ghitu Slinger", 77, Rarity.COMMON, mage.cards.g.GhituSlinger.class, RETRO_ART));
        cards.add(new SetCardInfo("Ghitu War Cry", 78, Rarity.UNCOMMON, mage.cards.g.GhituWarCry.class, RETRO_ART));
        cards.add(new SetCardInfo("Giant Cockroach", 54, Rarity.COMMON, mage.cards.g.GiantCockroach.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Medics", 79, Rarity.COMMON, mage.cards.g.GoblinMedics.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Welder", 80, Rarity.RARE, mage.cards.g.GoblinWelder.class, RETRO_ART));
        cards.add(new SetCardInfo("Granite Grip", 81, Rarity.COMMON, mage.cards.g.GraniteGrip.class, RETRO_ART));
        cards.add(new SetCardInfo("Grim Monolith", 126, Rarity.RARE, mage.cards.g.GrimMonolith.class, RETRO_ART));
        cards.add(new SetCardInfo("Harmonic Convergence", 103, Rarity.UNCOMMON, mage.cards.h.HarmonicConvergence.class, RETRO_ART));
        cards.add(new SetCardInfo("Hidden Gibbons", 104, Rarity.RARE, mage.cards.h.HiddenGibbons.class, RETRO_ART));
        cards.add(new SetCardInfo("Hope and Glory", 9, Rarity.UNCOMMON, mage.cards.h.HopeAndGlory.class, RETRO_ART));
        cards.add(new SetCardInfo("Impending Disaster", 82, Rarity.RARE, mage.cards.i.ImpendingDisaster.class, RETRO_ART));
        cards.add(new SetCardInfo("Intervene", 33, Rarity.COMMON, mage.cards.i.Intervene.class, RETRO_ART));
        cards.add(new SetCardInfo("Iron Maiden", 127, Rarity.RARE, mage.cards.i.IronMaiden.class, RETRO_ART));
        cards.add(new SetCardInfo("Iron Will", 10, Rarity.COMMON, mage.cards.i.IronWill.class, RETRO_ART));
        cards.add(new SetCardInfo("Jhoira's Toolbox", 128, Rarity.UNCOMMON, mage.cards.j.JhoirasToolbox.class, RETRO_ART));
        cards.add(new SetCardInfo("Karmic Guide", 11, Rarity.RARE, mage.cards.k.KarmicGuide.class, RETRO_ART));
        cards.add(new SetCardInfo("King Crab", 34, Rarity.UNCOMMON, mage.cards.k.KingCrab.class, RETRO_ART));
        cards.add(new SetCardInfo("Knighthood", 12, Rarity.UNCOMMON, mage.cards.k.Knighthood.class, RETRO_ART));
        cards.add(new SetCardInfo("Last-Ditch Effort", 83, Rarity.UNCOMMON, mage.cards.l.LastDitchEffort.class, RETRO_ART));
        cards.add(new SetCardInfo("Lava Axe", 84, Rarity.COMMON, mage.cards.l.LavaAxe.class, RETRO_ART));
        cards.add(new SetCardInfo("Levitation", 35, Rarity.UNCOMMON, mage.cards.l.Levitation.class, RETRO_ART));
        cards.add(new SetCardInfo("Lone Wolf", 105, Rarity.UNCOMMON, mage.cards.l.LoneWolf.class, RETRO_ART));
        cards.add(new SetCardInfo("Lurking Skirge", 55, Rarity.RARE, mage.cards.l.LurkingSkirge.class, RETRO_ART));
        cards.add(new SetCardInfo("Martyr's Cause", 13, Rarity.UNCOMMON, mage.cards.m.MartyrsCause.class, RETRO_ART));
        cards.add(new SetCardInfo("Memory Jar", 129, Rarity.RARE, mage.cards.m.MemoryJar.class, RETRO_ART));
        cards.add(new SetCardInfo("Might of Oaks", 106, Rarity.RARE, mage.cards.m.MightOfOaks.class, RETRO_ART));
        cards.add(new SetCardInfo("Miscalculation", 36, Rarity.COMMON, mage.cards.m.Miscalculation.class, RETRO_ART));
        cards.add(new SetCardInfo("Molten Hydra", 85, Rarity.RARE, mage.cards.m.MoltenHydra.class, RETRO_ART));
        cards.add(new SetCardInfo("Mother of Runes", 14, Rarity.UNCOMMON, mage.cards.m.MotherOfRunes.class, RETRO_ART));
        cards.add(new SetCardInfo("Multani's Acolyte", 108, Rarity.COMMON, mage.cards.m.MultanisAcolyte.class, RETRO_ART));
        cards.add(new SetCardInfo("Multani's Presence", 109, Rarity.UNCOMMON, mage.cards.m.MultanisPresence.class, RETRO_ART));
        cards.add(new SetCardInfo("Multani, Maro-Sorcerer", 107, Rarity.RARE, mage.cards.m.MultaniMaroSorcerer.class, RETRO_ART));
        cards.add(new SetCardInfo("No Mercy", 56, Rarity.RARE, mage.cards.n.NoMercy.class, RETRO_ART));
        cards.add(new SetCardInfo("Opal Avenger", 15, Rarity.RARE, mage.cards.o.OpalAvenger.class, RETRO_ART));
        cards.add(new SetCardInfo("Opal Champion", 16, Rarity.COMMON, mage.cards.o.OpalChampion.class, RETRO_ART));
        cards.add(new SetCardInfo("Opportunity", 37, Rarity.UNCOMMON, mage.cards.o.Opportunity.class, RETRO_ART));
        cards.add(new SetCardInfo("Ostracize", 57, Rarity.COMMON, mage.cards.o.Ostracize.class, RETRO_ART));
        cards.add(new SetCardInfo("Palinchron", 38, Rarity.RARE, mage.cards.p.Palinchron.class, RETRO_ART));
        cards.add(new SetCardInfo("Parch", 86, Rarity.COMMON, mage.cards.p.Parch.class, RETRO_ART));
        cards.add(new SetCardInfo("Peace and Quiet", 17, Rarity.UNCOMMON, mage.cards.p.PeaceAndQuiet.class, RETRO_ART));
        cards.add(new SetCardInfo("Phyrexian Broodlings", 58, Rarity.COMMON, mage.cards.p.PhyrexianBroodlings.class, RETRO_ART));
        cards.add(new SetCardInfo("Phyrexian Debaser", 59, Rarity.COMMON, mage.cards.p.PhyrexianDebaser.class, RETRO_ART));
        cards.add(new SetCardInfo("Phyrexian Defiler", 60, Rarity.UNCOMMON, mage.cards.p.PhyrexianDefiler.class, RETRO_ART));
        cards.add(new SetCardInfo("Phyrexian Denouncer", 61, Rarity.COMMON, mage.cards.p.PhyrexianDenouncer.class, RETRO_ART));
        cards.add(new SetCardInfo("Phyrexian Plaguelord", 62, Rarity.RARE, mage.cards.p.PhyrexianPlaguelord.class, RETRO_ART));
        cards.add(new SetCardInfo("Phyrexian Reclamation", 63, Rarity.UNCOMMON, mage.cards.p.PhyrexianReclamation.class, RETRO_ART));
        cards.add(new SetCardInfo("Plague Beetle", 64, Rarity.COMMON, mage.cards.p.PlagueBeetle.class, RETRO_ART));
        cards.add(new SetCardInfo("Planar Collapse", 18, Rarity.RARE, mage.cards.p.PlanarCollapse.class, RETRO_ART));
        cards.add(new SetCardInfo("Purify", 19, Rarity.RARE, mage.cards.p.Purify.class, RETRO_ART));
        cards.add(new SetCardInfo("Pygmy Pyrosaur", 87, Rarity.COMMON, mage.cards.p.PygmyPyrosaur.class, RETRO_ART));
        cards.add(new SetCardInfo("Pyromancy", 88, Rarity.RARE, mage.cards.p.Pyromancy.class, RETRO_ART));
        cards.add(new SetCardInfo("Quicksilver Amulet", 130, Rarity.RARE, mage.cards.q.QuicksilverAmulet.class, RETRO_ART));
        cards.add(new SetCardInfo("Rack and Ruin", 89, Rarity.UNCOMMON, mage.cards.r.RackAndRuin.class, RETRO_ART));
        cards.add(new SetCardInfo("Radiant's Dragoons", 21, Rarity.UNCOMMON, mage.cards.r.RadiantsDragoons.class, RETRO_ART));
        cards.add(new SetCardInfo("Radiant's Judgment", 22, Rarity.COMMON, mage.cards.r.RadiantsJudgment.class, RETRO_ART));
        cards.add(new SetCardInfo("Radiant, Archangel", 20, Rarity.RARE, mage.cards.r.RadiantArchangel.class, RETRO_ART));
        cards.add(new SetCardInfo("Rancor", 110, Rarity.COMMON, mage.cards.r.Rancor.class, RETRO_ART));
        cards.add(new SetCardInfo("Rank and File", 65, Rarity.UNCOMMON, mage.cards.r.RankAndFile.class, RETRO_ART));
        cards.add(new SetCardInfo("Raven Familiar", 39, Rarity.UNCOMMON, mage.cards.r.RavenFamiliar.class, RETRO_ART));
        cards.add(new SetCardInfo("Rebuild", 40, Rarity.UNCOMMON, mage.cards.r.Rebuild.class, RETRO_ART));
        cards.add(new SetCardInfo("Repopulate", 111, Rarity.COMMON, mage.cards.r.Repopulate.class, RETRO_ART));
        cards.add(new SetCardInfo("Ring of Gix", 131, Rarity.RARE, mage.cards.r.RingOfGix.class, RETRO_ART));
        cards.add(new SetCardInfo("Rivalry", 90, Rarity.RARE, mage.cards.r.Rivalry.class, RETRO_ART));
        cards.add(new SetCardInfo("Scrapheap", 132, Rarity.RARE, mage.cards.s.Scrapheap.class, RETRO_ART));
        cards.add(new SetCardInfo("Second Chance", 41, Rarity.RARE, mage.cards.s.SecondChance.class, RETRO_ART));
        cards.add(new SetCardInfo("Shivan Phoenix", 91, Rarity.RARE, mage.cards.s.ShivanPhoenix.class, RETRO_ART));
        cards.add(new SetCardInfo("Sick and Tired", 66, Rarity.COMMON, mage.cards.s.SickAndTired.class, RETRO_ART));
        cards.add(new SetCardInfo("Silk Net", 112, Rarity.COMMON, mage.cards.s.SilkNet.class, RETRO_ART));
        cards.add(new SetCardInfo("Simian Grunts", 113, Rarity.COMMON, mage.cards.s.SimianGrunts.class, RETRO_ART));
        cards.add(new SetCardInfo("Sleeper's Guile", 67, Rarity.COMMON, mage.cards.s.SleepersGuile.class, RETRO_ART));
        cards.add(new SetCardInfo("Slow Motion", 42, Rarity.COMMON, mage.cards.s.SlowMotion.class, RETRO_ART));
        cards.add(new SetCardInfo("Sluggishness", 92, Rarity.COMMON, mage.cards.s.Sluggishness.class, RETRO_ART));
        cards.add(new SetCardInfo("Snap", 43, Rarity.COMMON, mage.cards.s.Snap.class, RETRO_ART));
        cards.add(new SetCardInfo("Spawning Pool", 142, Rarity.UNCOMMON, mage.cards.s.SpawningPool.class, RETRO_ART));
        cards.add(new SetCardInfo("Subversion", 68, Rarity.RARE, mage.cards.s.Subversion.class, RETRO_ART));
        cards.add(new SetCardInfo("Sustainer of the Realm", 23, Rarity.UNCOMMON, mage.cards.s.SustainerOfTheRealm.class, RETRO_ART));
        cards.add(new SetCardInfo("Swat", 69, Rarity.COMMON, mage.cards.s.Swat.class, RETRO_ART));
        cards.add(new SetCardInfo("Tethered Skirge", 70, Rarity.UNCOMMON, mage.cards.t.TetheredSkirge.class, RETRO_ART));
        cards.add(new SetCardInfo("Thornwind Faeries", 44, Rarity.COMMON, mage.cards.t.ThornwindFaeries.class, RETRO_ART));
        cards.add(new SetCardInfo("Thran Lens", 133, Rarity.RARE, mage.cards.t.ThranLens.class, RETRO_ART));
        cards.add(new SetCardInfo("Thran War Machine", 134, Rarity.UNCOMMON, mage.cards.t.ThranWarMachine.class, RETRO_ART));
        cards.add(new SetCardInfo("Thran Weaponry", 135, Rarity.RARE, mage.cards.t.ThranWeaponry.class, RETRO_ART));
        cards.add(new SetCardInfo("Ticking Gnomes", 136, Rarity.UNCOMMON, mage.cards.t.TickingGnomes.class, RETRO_ART));
        cards.add(new SetCardInfo("Tinker", 45, Rarity.UNCOMMON, mage.cards.t.Tinker.class, RETRO_ART));
        cards.add(new SetCardInfo("Tragic Poet", 24, Rarity.COMMON, mage.cards.t.TragicPoet.class, RETRO_ART));
        cards.add(new SetCardInfo("Treacherous Link", 71, Rarity.UNCOMMON, mage.cards.t.TreacherousLink.class, RETRO_ART));
        cards.add(new SetCardInfo("Treefolk Mystic", 114, Rarity.COMMON, mage.cards.t.TreefolkMystic.class, RETRO_ART));
        cards.add(new SetCardInfo("Treetop Village", 143, Rarity.UNCOMMON, mage.cards.t.TreetopVillage.class, RETRO_ART));
        cards.add(new SetCardInfo("Unearth", 72, Rarity.COMMON, mage.cards.u.Unearth.class, RETRO_ART));
        cards.add(new SetCardInfo("Urza's Blueprints", 137, Rarity.RARE, mage.cards.u.UrzasBlueprints.class, RETRO_ART));
        cards.add(new SetCardInfo("Viashino Bey", 93, Rarity.COMMON, mage.cards.v.ViashinoBey.class, RETRO_ART));
        cards.add(new SetCardInfo("Viashino Cutthroat", 94, Rarity.UNCOMMON, mage.cards.v.ViashinoCutthroat.class, RETRO_ART));
        cards.add(new SetCardInfo("Viashino Heretic", 95, Rarity.UNCOMMON, mage.cards.v.ViashinoHeretic.class, RETRO_ART));
        cards.add(new SetCardInfo("Viashino Sandscout", 96, Rarity.COMMON, mage.cards.v.ViashinoSandscout.class, RETRO_ART));
        cards.add(new SetCardInfo("Vigilant Drake", 46, Rarity.COMMON, mage.cards.v.VigilantDrake.class, RETRO_ART));
        cards.add(new SetCardInfo("Walking Sponge", 47, Rarity.UNCOMMON, mage.cards.w.WalkingSponge.class, RETRO_ART));
        cards.add(new SetCardInfo("Weatherseed Elf", 115, Rarity.COMMON, mage.cards.w.WeatherseedElf.class, RETRO_ART));
        cards.add(new SetCardInfo("Weatherseed Faeries", 48, Rarity.COMMON, mage.cards.w.WeatherseedFaeries.class, RETRO_ART));
        cards.add(new SetCardInfo("Weatherseed Treefolk", 116, Rarity.RARE, mage.cards.w.WeatherseedTreefolk.class, RETRO_ART));
        cards.add(new SetCardInfo("Wheel of Torture", 138, Rarity.RARE, mage.cards.w.WheelOfTorture.class, RETRO_ART));
        cards.add(new SetCardInfo("Wing Snare", 117, Rarity.UNCOMMON, mage.cards.w.WingSnare.class, RETRO_ART));
        cards.add(new SetCardInfo("Yavimaya Granger", 118, Rarity.COMMON, mage.cards.y.YavimayaGranger.class, RETRO_ART));
        cards.add(new SetCardInfo("Yavimaya Scion", 119, Rarity.COMMON, mage.cards.y.YavimayaScion.class, RETRO_ART));
        cards.add(new SetCardInfo("Yavimaya Wurm", 120, Rarity.COMMON, mage.cards.y.YavimayaWurm.class, RETRO_ART));
    }

    @Override
    public BoosterCollator createCollator() {
        return new UrzasLegacyCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/ulg.html
// Using common collation
class UrzasLegacyCollator implements BoosterCollator {

    private final CardRun commonA = new CardRun(true, "72", "120", "48", "75", "16", "66", "118", "44", "96", "22", "54", "113", "29", "75", "7", "66", "119", "46", "96", "5", "59", "110", "48", "86", "8", "54", "120", "42", "79", "4", "64", "113", "46", "93", "7", "58", "110", "44", "86", "5", "64", "119", "36", "79", "16", "72", "114", "42", "77", "8", "59", "118", "36", "93", "22", "58", "114", "29", "77", "4");
    private final CardRun commonB = new CardRun(true, "69", "98", "28", "87", "10", "57", "111", "33", "73", "1", "67", "115", "28", "84", "3", "53", "112", "27", "81", "10", "61", "108", "43", "73", "6", "53", "98", "32", "87", "3", "69", "115", "43", "81", "24", "57", "112", "33", "92", "6", "67", "108", "27", "84", "1", "61", "111", "32", "92", "24");
    private final CardRun uncommon = new CardRun(false, "121", "74", "97", "49", "99", "51", "139", "140", "102", "141", "76", "78", "103", "9", "128", "34", "12", "83", "35", "105", "13", "14", "109", "37", "17", "60", "63", "89", "21", "65", "39", "40", "142", "23", "70", "134", "136", "45", "71", "143", "94", "95", "47", "117");
    private final CardRun rare = new CardRun(false, "25", "26", "122", "2", "50", "123", "124", "125", "100", "30", "101", "52", "31", "80", "126", "104", "82", "127", "11", "55", "129", "106", "85", "107", "56", "15", "38", "62", "18", "19", "88", "130", "20", "131", "90", "132", "41", "91", "68", "133", "135", "137", "116", "138");

    private final BoosterStructure AAAAAABBBBB = new BoosterStructure(
            commonA, commonA, commonA, commonA, commonA, commonA,
            commonB, commonB, commonB, commonB, commonB
    );
    private final BoosterStructure U3 = new BoosterStructure(uncommon, uncommon, uncommon);
    private final BoosterStructure R1 = new BoosterStructure(rare);

    private final RarityConfiguration commonRuns = new RarityConfiguration(AAAAAABBBBB);
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(U3);
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
