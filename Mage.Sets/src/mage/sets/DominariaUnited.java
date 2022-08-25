package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class DominariaUnited extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList("Argivian Cavalier", "Balduvian Berserker", "Coalition Skyknight", "Guardian of New Benalia", "Keldon Flamesage", "Linebreaker Baloth", "Yavimaya Steelcrusher");

    private static final DominariaUnited instance = new DominariaUnited();

    public static DominariaUnited getInstance() {
        return instance;
    }

    private DominariaUnited() {
        super("Dominaria United", "DMU", ExpansionSet.buildDate(2022, 11, 9), SetType.EXPANSION);
        this.blockName = "Dominaria United";
        this.hasBoosters = false; // temporary
        this.hasBasicLands = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 7;
        this.maxCardNumberInBooster = 281;

        cards.add(new SetCardInfo("Academy Wall", 41, Rarity.COMMON, mage.cards.a.AcademyWall.class));
        cards.add(new SetCardInfo("Adarkar Wastes", 243, Rarity.RARE, mage.cards.a.AdarkarWastes.class));
        cards.add(new SetCardInfo("Ajani, Sleeper Agent", 192, Rarity.MYTHIC, mage.cards.a.AjaniSleeperAgent.class));
        cards.add(new SetCardInfo("Archangel of Wrath", 3, Rarity.RARE, mage.cards.a.ArchangelOfWrath.class));
        cards.add(new SetCardInfo("Argivian Cavalier", 4, Rarity.COMMON, mage.cards.a.ArgivianCavalier.class));
        cards.add(new SetCardInfo("Aron, Benalia's Ruin", 193, Rarity.UNCOMMON, mage.cards.a.AronBenaliasRuin.class));
        cards.add(new SetCardInfo("Astor, Bearer of Blades", 194, Rarity.RARE, mage.cards.a.AstorBearerOfBlades.class));
        cards.add(new SetCardInfo("Automatic Librarian", 229, Rarity.COMMON, mage.cards.a.AutomaticLibrarian.class));
        cards.add(new SetCardInfo("Balduvian Atrocity", 79, Rarity.UNCOMMON, mage.cards.b.BalduvianAtrocity.class));
        cards.add(new SetCardInfo("Balmor, Battlemage Captain", 196, Rarity.UNCOMMON, mage.cards.b.BalmorBattlemageCaptain.class));
        cards.add(new SetCardInfo("Battlewing Mystic", 43, Rarity.UNCOMMON, mage.cards.b.BattlewingMystic.class));
        cards.add(new SetCardInfo("Benalish Sleeper", 8, Rarity.COMMON, mage.cards.b.BenalishSleeper.class));
        cards.add(new SetCardInfo("Bog Badger", 156, Rarity.COMMON, mage.cards.b.BogBadger.class));
        cards.add(new SetCardInfo("Bortuk Bonerattle", 197, Rarity.UNCOMMON, mage.cards.b.BortukBonerattle.class));
        cards.add(new SetCardInfo("Braids, Arisen Nightmare", 84, Rarity.RARE, mage.cards.b.BraidsArisenNightmare.class));
        cards.add(new SetCardInfo("Briar Hydra", 286, Rarity.RARE, mage.cards.b.BriarHydra.class));
        cards.add(new SetCardInfo("Caves of Koilos", 244, Rarity.RARE, mage.cards.c.CavesOfKoilos.class));
        cards.add(new SetCardInfo("Charismatic Vanguard", 10, Rarity.COMMON, mage.cards.c.CharismaticVanguard.class));
        cards.add(new SetCardInfo("Citizen's Arrest", 11, Rarity.COMMON, mage.cards.c.CitizensArrest.class));
        cards.add(new SetCardInfo("Cleaving Skyrider", 12, Rarity.UNCOMMON, mage.cards.c.CleavingSkyrider.class));
        cards.add(new SetCardInfo("Coalition Skyknight", 14, Rarity.UNCOMMON, mage.cards.c.CoalitionSkyknight.class));
        cards.add(new SetCardInfo("Colossal Growth", 158, Rarity.COMMON, mage.cards.c.ColossalGrowth.class));
        cards.add(new SetCardInfo("Cosmic Epiphany", 283, Rarity.RARE, mage.cards.c.CosmicEpiphany.class));
        cards.add(new SetCardInfo("Cult Conscript", 88, Rarity.UNCOMMON, mage.cards.c.CultConscript.class));
        cards.add(new SetCardInfo("Cut Down", 89, Rarity.UNCOMMON, mage.cards.c.CutDown.class));
        cards.add(new SetCardInfo("Dragon Whelp", 120, Rarity.UNCOMMON, mage.cards.d.DragonWhelp.class));
        cards.add(new SetCardInfo("Evolved Sleeper", 93, Rarity.RARE, mage.cards.e.EvolvedSleeper.class));
        cards.add(new SetCardInfo("Fires of Victory", 123, Rarity.UNCOMMON, mage.cards.f.FiresOfVictory.class));
        cards.add(new SetCardInfo("Forest", 274, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frostfist Strider", 51, Rarity.UNCOMMON, mage.cards.f.FrostfistStrider.class));
        cards.add(new SetCardInfo("Gaea's Might", 164, Rarity.COMMON, mage.cards.g.GaeasMight.class));
        cards.add(new SetCardInfo("Haughty Djinn", 52, Rarity.RARE, mage.cards.h.HaughtyDjinn.class));
        cards.add(new SetCardInfo("Herd Migration", 165, Rarity.RARE, mage.cards.h.HerdMigration.class));
        cards.add(new SetCardInfo("Hero's Heirloom", 231, Rarity.UNCOMMON, mage.cards.h.HerosHeirloom.class));
        cards.add(new SetCardInfo("Hurler Cyclops", 130, Rarity.UNCOMMON, mage.cards.h.HurlerCyclops.class));
        cards.add(new SetCardInfo("Hurloon Battle Hymn", 131, Rarity.UNCOMMON, mage.cards.h.HurloonBattleHymn.class));
        cards.add(new SetCardInfo("Impede Momentum", 54, Rarity.COMMON, mage.cards.i.ImpedeMomentum.class));
        cards.add(new SetCardInfo("Inscribed Tablet", 232, Rarity.UNCOMMON, mage.cards.i.InscribedTablet.class));
        cards.add(new SetCardInfo("Island", 265, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jaya's Firenado", 134, Rarity.COMMON, mage.cards.j.JayasFirenado.class));
        cards.add(new SetCardInfo("Jaya, Fiery Negotiator", 133, Rarity.MYTHIC, mage.cards.j.JayaFieryNegotiator.class));
        cards.add(new SetCardInfo("Jhoira, Ageless Innovator", 202, Rarity.RARE, mage.cards.j.JhoiraAgelessInnovator.class));
        cards.add(new SetCardInfo("Join Forces", 21, Rarity.UNCOMMON, mage.cards.j.JoinForces.class));
        cards.add(new SetCardInfo("Juniper Order Rootweaver", 22, Rarity.COMMON, mage.cards.j.JuniperOrderRootweaver.class));
        cards.add(new SetCardInfo("Karplusan Forest", 250, Rarity.RARE, mage.cards.k.KarplusanForest.class));
        cards.add(new SetCardInfo("King Darien XLVIII", 204, Rarity.RARE, mage.cards.k.KingDarienXLVIII.class));
        cards.add(new SetCardInfo("Lagomos, Hand of Hatred", 205, Rarity.UNCOMMON, mage.cards.l.LagomosHandOfHatred.class));
        cards.add(new SetCardInfo("Lightning Strike", 137, Rarity.COMMON, mage.cards.l.LightningStrike.class));
        cards.add(new SetCardInfo("Liliana of the Veil", 97, Rarity.MYTHIC, mage.cards.l.LilianaOfTheVeil.class));
        cards.add(new SetCardInfo("Linebreaker Baloth", 168, Rarity.UNCOMMON, mage.cards.l.LinebreakerBaloth.class));
        cards.add(new SetCardInfo("Llanowar Loamspeaker", 170, Rarity.RARE, mage.cards.l.LlanowarLoamspeaker.class));
        cards.add(new SetCardInfo("Magnigoth Sentry", 172, Rarity.COMMON, mage.cards.m.MagnigothSentry.class));
        cards.add(new SetCardInfo("Mesa Cavalier", 26, Rarity.COMMON, mage.cards.m.MesaCavalier.class));
        cards.add(new SetCardInfo("Micromancer", 57, Rarity.UNCOMMON, mage.cards.m.Micromancer.class));
        cards.add(new SetCardInfo("Molten Monstrosity", 139, Rarity.COMMON, mage.cards.m.MoltenMonstrosity.class));
        cards.add(new SetCardInfo("Monstrous War-Leech", 98, Rarity.UNCOMMON, mage.cards.m.MonstrousWarLeech.class));
        cards.add(new SetCardInfo("Mossbeard Ancient", 173, Rarity.UNCOMMON, mage.cards.m.MossbeardAncient.class));
        cards.add(new SetCardInfo("Mountain", 271, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Negate", 58, Rarity.COMMON, mage.cards.n.Negate.class));
        cards.add(new SetCardInfo("Nishoba Brawler", 174, Rarity.UNCOMMON, mage.cards.n.NishobaBrawler.class));
        cards.add(new SetCardInfo("Phoenix Chick", 140, Rarity.UNCOMMON, mage.cards.p.PhoenixChick.class));
        cards.add(new SetCardInfo("Phyrexian Espionage", 60, Rarity.COMMON, mage.cards.p.PhyrexianEspionage.class));
        cards.add(new SetCardInfo("Phyrexian Rager", 99, Rarity.COMMON, mage.cards.p.PhyrexianRager.class));
        cards.add(new SetCardInfo("Pilfer", 102, Rarity.UNCOMMON, mage.cards.p.Pilfer.class));
        cards.add(new SetCardInfo("Plains", 262, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Protect the Negotiators", 62, Rarity.UNCOMMON, mage.cards.p.ProtectTheNegotiators.class));
        cards.add(new SetCardInfo("Raff, Weatherlight Stalwart", 212, Rarity.UNCOMMON, mage.cards.r.RaffWeatherlightStalwart.class));
        cards.add(new SetCardInfo("Resolute Reinforcements", 29, Rarity.UNCOMMON, mage.cards.r.ResoluteReinforcements.class));
        cards.add(new SetCardInfo("Rona's Vortex", 63, Rarity.UNCOMMON, mage.cards.r.RonasVortex.class));
        cards.add(new SetCardInfo("Rulik Mons, Warren Chief", 217, Rarity.UNCOMMON, mage.cards.r.RulikMonsWarrenChief.class));
        cards.add(new SetCardInfo("Runic Shot", 30, Rarity.UNCOMMON, mage.cards.r.RunicShot.class));
        cards.add(new SetCardInfo("Samite Herbalist", 31, Rarity.COMMON, mage.cards.s.SamiteHerbalist.class));
        cards.add(new SetCardInfo("Scout the Wilderness", 176, Rarity.COMMON, mage.cards.s.ScoutTheWilderness.class));
        cards.add(new SetCardInfo("Sengir Connoisseur", 104, Rarity.UNCOMMON, mage.cards.s.SengirConnoisseur.class));
        cards.add(new SetCardInfo("Shalai's Acolyte", 33, Rarity.UNCOMMON, mage.cards.s.ShalaisAcolyte.class));
        cards.add(new SetCardInfo("Sheoldred, the Apocalypse", 107, Rarity.MYTHIC, mage.cards.s.SheoldredTheApocalypse.class));
        cards.add(new SetCardInfo("Shivan Devastator", 143, Rarity.MYTHIC, mage.cards.s.ShivanDevastator.class));
        cards.add(new SetCardInfo("Shivan Reef", 255, Rarity.RARE, mage.cards.s.ShivanReef.class));
        cards.add(new SetCardInfo("Slimefoot's Survey", 178, Rarity.UNCOMMON, mage.cards.s.SlimefootsSurvey.class));
        cards.add(new SetCardInfo("Snarespinner", 179, Rarity.COMMON, mage.cards.s.Snarespinner.class));
        cards.add(new SetCardInfo("Soul of Windgrace", 220, Rarity.MYTHIC, mage.cards.s.SoulOfWindgrace.class));
        cards.add(new SetCardInfo("Stall for Time", 34, Rarity.COMMON, mage.cards.s.StallForTime.class));
        cards.add(new SetCardInfo("Strength of the Coalition", 180, Rarity.UNCOMMON, mage.cards.s.StrengthOfTheCoalition.class));
        cards.add(new SetCardInfo("Stronghold Arena", 110, Rarity.RARE, mage.cards.s.StrongholdArena.class));
        cards.add(new SetCardInfo("Sulfurous Springs", 256, Rarity.RARE, mage.cards.s.SulfurousSprings.class));
        cards.add(new SetCardInfo("Swamp", 268, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Take Up the Shield", 35, Rarity.COMMON, mage.cards.t.TakeUpTheShield.class));
        cards.add(new SetCardInfo("Tattered Apparition", 111, Rarity.COMMON, mage.cards.t.TatteredApparition.class));
        cards.add(new SetCardInfo("Temporal Firestorm", 147, Rarity.RARE, mage.cards.t.TemporalFirestorm.class));
        cards.add(new SetCardInfo("Territorial Maro", 184, Rarity.UNCOMMON, mage.cards.t.TerritorialMaro.class));
        cards.add(new SetCardInfo("The Raven Man", 103, Rarity.RARE, mage.cards.t.TheRavenMan.class));
        cards.add(new SetCardInfo("Threats Undetected", 185, Rarity.RARE, mage.cards.t.ThreatsUndetected.class));
        cards.add(new SetCardInfo("Thrill of Possibility", 148, Rarity.COMMON, mage.cards.t.ThrillOfPossibility.class));
        cards.add(new SetCardInfo("Timeless Lotus", 239, Rarity.MYTHIC, mage.cards.t.TimelessLotus.class));
        cards.add(new SetCardInfo("Timely Interference", 70, Rarity.COMMON, mage.cards.t.TimelyInterference.class));
        cards.add(new SetCardInfo("Tolarian Geyser", 71, Rarity.COMMON, mage.cards.t.TolarianGeyser.class));
        cards.add(new SetCardInfo("Tolarian Terror", 72, Rarity.COMMON, mage.cards.t.TolarianTerror.class));
        cards.add(new SetCardInfo("Tori D'Avenant, Fury Rider", 223, Rarity.UNCOMMON, mage.cards.t.ToriDAvenantFuryRider.class));
        cards.add(new SetCardInfo("Toxic Abomination", 112, Rarity.COMMON, mage.cards.t.ToxicAbomination.class));
        cards.add(new SetCardInfo("Tura Kennerud, Skyknight", 224, Rarity.UNCOMMON, mage.cards.t.TuraKennerudSkyknight.class));
        cards.add(new SetCardInfo("Viashino Branchrider", 150, Rarity.COMMON, mage.cards.v.ViashinoBranchrider.class));
        cards.add(new SetCardInfo("Vineshaper Prodigy", 187, Rarity.COMMON, mage.cards.v.VineshaperProdigy.class));
        cards.add(new SetCardInfo("Voda Sea Scavenger", 74, Rarity.COMMON, mage.cards.v.VodaSeaScavenger.class));
        cards.add(new SetCardInfo("Writhing Necromass", 115, Rarity.COMMON, mage.cards.w.WrithingNecromass.class));
        cards.add(new SetCardInfo("Yavimaya Coast", 261, Rarity.RARE, mage.cards.y.YavimayaCoast.class));
        cards.add(new SetCardInfo("Yavimaya Iconoclast", 190, Rarity.UNCOMMON, mage.cards.y.YavimayaIconoclast.class));
        cards.add(new SetCardInfo("Yavimaya Steelcrusher", 152, Rarity.COMMON, mage.cards.y.YavimayaSteelcrusher.class));
    }

//    @Override
//    public BoosterCollator createCollator() {
//        return new DominariaUnitedCollator();
//    }
}
