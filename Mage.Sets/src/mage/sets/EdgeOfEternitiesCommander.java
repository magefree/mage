package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class EdgeOfEternitiesCommander extends ExpansionSet {

    private static final EdgeOfEternitiesCommander instance = new EdgeOfEternitiesCommander();

    public static EdgeOfEternitiesCommander getInstance() {
        return instance;
    }

    private EdgeOfEternitiesCommander() {
        super("Edge of Eternities Commander", "EOC", ExpansionSet.buildDate(2025, 8, 1), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Aftermath Analyst", 91, Rarity.UNCOMMON, mage.cards.a.AftermathAnalyst.class));
        cards.add(new SetCardInfo("Arcane Signet", 53, Rarity.COMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Augur of Autumn", 92, Rarity.RARE, mage.cards.a.AugurOfAutumn.class));
        cards.add(new SetCardInfo("Baloth Prime", 13, Rarity.RARE, mage.cards.b.BalothPrime.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Baloth Prime", 33, Rarity.RARE, mage.cards.b.BalothPrime.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Beast Within", 93, Rarity.UNCOMMON, mage.cards.b.BeastWithin.class));
        cards.add(new SetCardInfo("Binding the Old Gods", 52, Rarity.UNCOMMON, mage.cards.b.BindingTheOldGods.class));
        cards.add(new SetCardInfo("Blasphemous Act", 86, Rarity.RARE, mage.cards.b.BlasphemousAct.class));
        cards.add(new SetCardInfo("Bojuka Bog", 149, Rarity.COMMON, mage.cards.b.BojukaBog.class));
        cards.add(new SetCardInfo("Braids, Arisen Nightmare", 82, Rarity.RARE, mage.cards.b.BraidsArisenNightmare.class));
        cards.add(new SetCardInfo("Cabaretti Courtyard", 151, Rarity.COMMON, mage.cards.c.CabarettiCourtyard.class));
        cards.add(new SetCardInfo("Canyon Slough", 152, Rarity.RARE, mage.cards.c.CanyonSlough.class));
        cards.add(new SetCardInfo("Centaur Vinecrasher", 94, Rarity.RARE, mage.cards.c.CentaurVinecrasher.class));
        cards.add(new SetCardInfo("Cinder Glade", 154, Rarity.RARE, mage.cards.c.CinderGlade.class));
        cards.add(new SetCardInfo("Command Tower", 59, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Cultivate", 95, Rarity.COMMON, mage.cards.c.Cultivate.class));
        cards.add(new SetCardInfo("Dakmor Salvage", 156, Rarity.UNCOMMON, mage.cards.d.DakmorSalvage.class));
        cards.add(new SetCardInfo("Escape Tunnel", 157, Rarity.COMMON, mage.cards.e.EscapeTunnel.class));
        cards.add(new SetCardInfo("Escape to the Wilds", 115, Rarity.RARE, mage.cards.e.EscapeToTheWilds.class));
        cards.add(new SetCardInfo("Evolving Wilds", 158, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Fabled Passage", 60, Rarity.RARE, mage.cards.f.FabledPassage.class));
        cards.add(new SetCardInfo("Farseek", 50, Rarity.COMMON, mage.cards.f.Farseek.class));
        cards.add(new SetCardInfo("Festering Thicket", 21, Rarity.RARE, mage.cards.f.FesteringThicket.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Festering Thicket", 41, Rarity.RARE, mage.cards.f.FesteringThicket.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Formless Genesis", 96, Rarity.RARE, mage.cards.f.FormlessGenesis.class));
        cards.add(new SetCardInfo("Gaze of Granite", 116, Rarity.RARE, mage.cards.g.GazeOfGranite.class));
        cards.add(new SetCardInfo("God-Eternal Bontu", 83, Rarity.MYTHIC, mage.cards.g.GodEternalBontu.class));
        cards.add(new SetCardInfo("Groundskeeper", 97, Rarity.UNCOMMON, mage.cards.g.Groundskeeper.class));
        cards.add(new SetCardInfo("Hammer of Purphoros", 88, Rarity.RARE, mage.cards.h.HammerOfPurphoros.class));
        cards.add(new SetCardInfo("Harrow", 98, Rarity.COMMON, mage.cards.h.Harrow.class));
        cards.add(new SetCardInfo("Hearthhull, the Worldseed", 1, Rarity.MYTHIC, mage.cards.h.HearthhullTheWorldseed.class));
        cards.add(new SetCardInfo("Horizon Explorer", 15, Rarity.RARE, mage.cards.h.HorizonExplorer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Horizon Explorer", 35, Rarity.RARE, mage.cards.h.HorizonExplorer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Infernal Grasp", 84, Rarity.UNCOMMON, mage.cards.i.InfernalGrasp.class));
        cards.add(new SetCardInfo("Inspirit, Flagship Vessel", 2, Rarity.MYTHIC, mage.cards.i.InspiritFlagshipVessel.class));
        cards.add(new SetCardInfo("Juri, Master of the Revue", 119, Rarity.UNCOMMON, mage.cards.j.JuriMasterOfTheRevue.class));
        cards.add(new SetCardInfo("Karplusan Forest", 164, Rarity.RARE, mage.cards.k.KarplusanForest.class));
        cards.add(new SetCardInfo("Kilo, Apogee Mind", 3, Rarity.MYTHIC, mage.cards.k.KiloApogeeMind.class));
        cards.add(new SetCardInfo("Korvold, Fae-Cursed King", 120, Rarity.MYTHIC, mage.cards.k.KorvoldFaeCursedKing.class));
        cards.add(new SetCardInfo("Llanowar Wastes", 165, Rarity.RARE, mage.cards.l.LlanowarWastes.class));
        cards.add(new SetCardInfo("Loamcrafter Faun", 99, Rarity.RARE, mage.cards.l.LoamcrafterFaun.class));
        cards.add(new SetCardInfo("Maestros Theater", 167, Rarity.COMMON, mage.cards.m.MaestrosTheater.class));
        cards.add(new SetCardInfo("Mayhem Devil", 121, Rarity.UNCOMMON, mage.cards.m.MayhemDevil.class));
        cards.add(new SetCardInfo("Mazirek, Kraul Death Priest", 122, Rarity.RARE, mage.cards.m.MazirekKraulDeathPriest.class));
        cards.add(new SetCardInfo("Moraug, Fury of Akoum", 89, Rarity.MYTHIC, mage.cards.m.MoraugFuryOfAkoum.class));
        cards.add(new SetCardInfo("Mountain Valley", 61, Rarity.UNCOMMON, mage.cards.m.MountainValley.class));
        cards.add(new SetCardInfo("Multani, Yavimaya's Avatar", 100, Rarity.MYTHIC, mage.cards.m.MultaniYavimayasAvatar.class));
        cards.add(new SetCardInfo("Myriad Landscape", 169, Rarity.UNCOMMON, mage.cards.m.MyriadLandscape.class));
        cards.add(new SetCardInfo("Nature's Lore", 101, Rarity.UNCOMMON, mage.cards.n.NaturesLore.class));
        cards.add(new SetCardInfo("Night's Whisper", 85, Rarity.COMMON, mage.cards.n.NightsWhisper.class));
        cards.add(new SetCardInfo("Omnath, Locus of Rage", 123, Rarity.MYTHIC, mage.cards.o.OmnathLocusOfRage.class));
        cards.add(new SetCardInfo("Oracle of Mul Daya", 102, Rarity.RARE, mage.cards.o.OracleOfMulDaya.class));
        cards.add(new SetCardInfo("Pest Infestation", 103, Rarity.RARE, mage.cards.p.PestInfestation.class));
        cards.add(new SetCardInfo("Putrefy", 124, Rarity.UNCOMMON, mage.cards.p.Putrefy.class));
        cards.add(new SetCardInfo("Rakdos Charm", 125, Rarity.UNCOMMON, mage.cards.r.RakdosCharm.class));
        cards.add(new SetCardInfo("Rampaging Baloths", 104, Rarity.RARE, mage.cards.r.RampagingBaloths.class));
        cards.add(new SetCardInfo("Riveteers Overlook", 172, Rarity.COMMON, mage.cards.r.RiveteersOverlook.class));
        cards.add(new SetCardInfo("Rocky Tar Pit", 173, Rarity.UNCOMMON, mage.cards.r.RockyTarPit.class));
        cards.add(new SetCardInfo("Roiling Regrowth", 105, Rarity.UNCOMMON, mage.cards.r.RoilingRegrowth.class));
        cards.add(new SetCardInfo("Satyr Wayfinder", 106, Rarity.COMMON, mage.cards.s.SatyrWayfinder.class));
        cards.add(new SetCardInfo("Scouring Swarm", 16, Rarity.RARE, mage.cards.s.ScouringSwarm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scouring Swarm", 36, Rarity.RARE, mage.cards.s.ScouringSwarm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sheltered Thicket", 178, Rarity.RARE, mage.cards.s.ShelteredThicket.class));
        cards.add(new SetCardInfo("Skyshroud Claim", 107, Rarity.COMMON, mage.cards.s.SkyshroudClaim.class));
        cards.add(new SetCardInfo("Smoldering Marsh", 182, Rarity.RARE, mage.cards.s.SmolderingMarsh.class));
        cards.add(new SetCardInfo("Sol Ring", 57, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Soul of Windgrace", 126, Rarity.MYTHIC, mage.cards.s.SoulOfWindgrace.class));
        cards.add(new SetCardInfo("Splendid Reclamation", 108, Rarity.RARE, mage.cards.s.SplendidReclamation.class));
        cards.add(new SetCardInfo("Springbloom Druid", 51, Rarity.COMMON, mage.cards.s.SpringbloomDruid.class));
        cards.add(new SetCardInfo("Sprouting Goblin", 90, Rarity.UNCOMMON, mage.cards.s.SproutingGoblin.class));
        cards.add(new SetCardInfo("Starfield Shepherd", 393, Rarity.UNCOMMON, mage.cards.s.StarfieldShepherd.class));
        cards.add(new SetCardInfo("Sulfurous Springs", 185, Rarity.RARE, mage.cards.s.SulfurousSprings.class));
        cards.add(new SetCardInfo("Szarel, Genesis Shepherd", 4, Rarity.MYTHIC, mage.cards.s.SzarelGenesisShepherd.class));
        cards.add(new SetCardInfo("Tear Asunder", 109, Rarity.UNCOMMON, mage.cards.t.TearAsunder.class));
        cards.add(new SetCardInfo("Terramorphic Expanse", 62, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
        cards.add(new SetCardInfo("The Gitrog Monster", 117, Rarity.MYTHIC, mage.cards.t.TheGitrogMonster.class));
        cards.add(new SetCardInfo("Tireless Tracker", 110, Rarity.RARE, mage.cards.t.TirelessTracker.class));
        cards.add(new SetCardInfo("Titania, Protector of Argoth", 111, Rarity.MYTHIC, mage.cards.t.TitaniaProtectorOfArgoth.class));
        cards.add(new SetCardInfo("Twilight Mire", 189, Rarity.RARE, mage.cards.t.TwilightMire.class));
        cards.add(new SetCardInfo("Uurg, Spawn of Turg", 127, Rarity.UNCOMMON, mage.cards.u.UurgSpawnOfTurg.class));
        cards.add(new SetCardInfo("Vernal Fen", 24, Rarity.RARE, mage.cards.v.VernalFen.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vernal Fen", 44, Rarity.RARE, mage.cards.v.VernalFen.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Viridescent Bog", 190, Rarity.RARE, mage.cards.v.ViridescentBog.class));
        cards.add(new SetCardInfo("Wastes", 191, Rarity.COMMON, mage.cards.w.Wastes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Windgrace's Judgment", 129, Rarity.RARE, mage.cards.w.WindgracesJudgment.class));
        cards.add(new SetCardInfo("World Breaker", 112, Rarity.MYTHIC, mage.cards.w.WorldBreaker.class));
        cards.add(new SetCardInfo("Worldsoul's Rage", 130, Rarity.RARE, mage.cards.w.WorldsoulsRage.class));
    }
}
