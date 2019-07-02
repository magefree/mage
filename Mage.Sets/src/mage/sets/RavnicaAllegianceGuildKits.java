package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author JayDi85
 */
public final class RavnicaAllegianceGuildKits extends ExpansionSet {

    private static final RavnicaAllegianceGuildKits instance = new RavnicaAllegianceGuildKits();

    public static RavnicaAllegianceGuildKits getInstance() {
        return instance;
    }

    private RavnicaAllegianceGuildKits() {
        super("Ravnica Allegiance Guild Kits", "GK2", ExpansionSet.buildDate(2019, 2, 15), SetType.SUPPLEMENTAL);
        this.blockName = "Guild Kits";
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Isperia, Supreme Judge", 1, Rarity.MYTHIC, mage.cards.i.IsperiaSupremeJudge.class));
        cards.add(new SetCardInfo("Azorius Herald", 2, Rarity.UNCOMMON, mage.cards.a.AzoriusHerald.class));
        cards.add(new SetCardInfo("Azorius Justiciar", 3, Rarity.UNCOMMON, mage.cards.a.AzoriusJusticiar.class));
        cards.add(new SetCardInfo("Stoic Ephemera", 4, Rarity.UNCOMMON, mage.cards.s.StoicEphemera.class));
        cards.add(new SetCardInfo("Court Hussar", 5, Rarity.UNCOMMON, mage.cards.c.CourtHussar.class));
        cards.add(new SetCardInfo("Hover Barrier", 6, Rarity.UNCOMMON, mage.cards.h.HoverBarrier.class));
        cards.add(new SetCardInfo("Archon of the Triumvirate", 7, Rarity.RARE, mage.cards.a.ArchonOfTheTriumvirate.class));
        cards.add(new SetCardInfo("Azorius Charm", 8, Rarity.UNCOMMON, mage.cards.a.AzoriusCharm.class));
        cards.add(new SetCardInfo("Azorius Guildmage", 9, Rarity.UNCOMMON, mage.cards.a.AzoriusGuildmage.class));
        cards.add(new SetCardInfo("Detention Sphere", 10, Rarity.RARE, mage.cards.d.DetentionSphere.class));
        cards.add(new SetCardInfo("Dovescape", 11, Rarity.RARE, mage.cards.d.Dovescape.class));
        cards.add(new SetCardInfo("Dramatic Rescue", 12, Rarity.COMMON, mage.cards.d.DramaticRescue.class));
        cards.add(new SetCardInfo("Isperia the Inscrutable", 13, Rarity.RARE, mage.cards.i.IsperiaTheInscrutable.class));
        cards.add(new SetCardInfo("Judge's Familiar", 14, Rarity.UNCOMMON, mage.cards.j.JudgesFamiliar.class));
        cards.add(new SetCardInfo("Lavinia of the Tenth", 15, Rarity.RARE, mage.cards.l.LaviniaOfTheTenth.class));
        cards.add(new SetCardInfo("Lyev Skyknight", 16, Rarity.UNCOMMON, mage.cards.l.LyevSkyknight.class));
        cards.add(new SetCardInfo("Pride of the Clouds", 17, Rarity.RARE, mage.cards.p.PrideOfTheClouds.class));
        cards.add(new SetCardInfo("Render Silent", 18, Rarity.RARE, mage.cards.r.RenderSilent.class));
        cards.add(new SetCardInfo("Sky Hussar", 19, Rarity.UNCOMMON, mage.cards.s.SkyHussar.class));
        cards.add(new SetCardInfo("Skymark Roc", 20, Rarity.UNCOMMON, mage.cards.s.SkymarkRoc.class));
        cards.add(new SetCardInfo("Sphinx's Revelation", 21, Rarity.MYTHIC, mage.cards.s.SphinxsRevelation.class));
        cards.add(new SetCardInfo("Windreaver", 22, Rarity.RARE, mage.cards.w.Windreaver.class));
        cards.add(new SetCardInfo("Azorius Keyrune", 23, Rarity.UNCOMMON, mage.cards.a.AzoriusKeyrune.class));
        cards.add(new SetCardInfo("Azorius Signet", 24, Rarity.UNCOMMON, mage.cards.a.AzoriusSignet.class));
        cards.add(new SetCardInfo("Azorius Chancery", 25, Rarity.UNCOMMON, mage.cards.a.AzoriusChancery.class));
        cards.add(new SetCardInfo("Plains", 26, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 27, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teysa, Orzhov Scion", 28, Rarity.RARE, mage.cards.t.TeysaOrzhovScion.class));
        cards.add(new SetCardInfo("Belfry Spirit", 29, Rarity.UNCOMMON, mage.cards.b.BelfrySpirit.class));
        cards.add(new SetCardInfo("Martyred Rusalka", 30, Rarity.UNCOMMON, mage.cards.m.MartyredRusalka.class));
        cards.add(new SetCardInfo("Keening Banshee", 31, Rarity.UNCOMMON, mage.cards.k.KeeningBanshee.class));
        cards.add(new SetCardInfo("Plagued Rusalka", 32, Rarity.UNCOMMON, mage.cards.p.PlaguedRusalka.class));
        cards.add(new SetCardInfo("Pontiff of Blight", 33, Rarity.RARE, mage.cards.p.PontiffOfBlight.class));
        cards.add(new SetCardInfo("Skeletal Vampire", 34, Rarity.RARE, mage.cards.s.SkeletalVampire.class));
        cards.add(new SetCardInfo("Stab Wound", 35, Rarity.UNCOMMON, mage.cards.s.StabWound.class));
        cards.add(new SetCardInfo("Ultimate Price", 36, Rarity.UNCOMMON, mage.cards.u.UltimatePrice.class));
        cards.add(new SetCardInfo("Angel of Despair", 37, Rarity.RARE, mage.cards.a.AngelOfDespair.class));
        cards.add(new SetCardInfo("Deathpact Angel", 38, Rarity.MYTHIC, mage.cards.d.DeathpactAngel.class));
        cards.add(new SetCardInfo("Debtors' Knell", 39, Rarity.RARE, mage.cards.d.DebtorsKnell.class));
        cards.add(new SetCardInfo("Ghost Council of Orzhova", 40, Rarity.RARE, mage.cards.g.GhostCouncilOfOrzhova.class));
        cards.add(new SetCardInfo("One Thousand Lashes", 41, Rarity.UNCOMMON, mage.cards.o.OneThousandLashes.class));
        cards.add(new SetCardInfo("Orzhov Charm", 42, Rarity.UNCOMMON, mage.cards.o.OrzhovCharm.class));
        cards.add(new SetCardInfo("Orzhov Pontiff", 43, Rarity.RARE, mage.cards.o.OrzhovPontiff.class));
        cards.add(new SetCardInfo("Pillory of the Sleepless", 44, Rarity.UNCOMMON, mage.cards.p.PilloryOfTheSleepless.class));
        cards.add(new SetCardInfo("Sin Collector", 45, Rarity.UNCOMMON, mage.cards.s.SinCollector.class));
        cards.add(new SetCardInfo("Treasury Thrull", 46, Rarity.RARE, mage.cards.t.TreasuryThrull.class));
        cards.add(new SetCardInfo("Vizkopa Guildmage", 47, Rarity.UNCOMMON, mage.cards.v.VizkopaGuildmage.class));
        cards.add(new SetCardInfo("Orzhov Signet", 48, Rarity.COMMON, mage.cards.o.OrzhovSignet.class));
        cards.add(new SetCardInfo("Orzhov Basilica", 49, Rarity.COMMON, mage.cards.o.OrzhovBasilica.class));
        cards.add(new SetCardInfo("Plains", 50, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 51, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rakdos, Lord of Riots", 52, Rarity.MYTHIC, mage.cards.r.RakdosLordOfRiots.class));
        cards.add(new SetCardInfo("Crypt Champion", 53, Rarity.UNCOMMON, mage.cards.c.CryptChampion.class));
        cards.add(new SetCardInfo("Thrill-Kill Assassin", 54, Rarity.UNCOMMON, mage.cards.t.ThrillKillAssassin.class));
        cards.add(new SetCardInfo("Cackling Flames", 55, Rarity.COMMON, mage.cards.c.CacklingFlames.class));
        cards.add(new SetCardInfo("Demonfire", 56, Rarity.RARE, mage.cards.d.Demonfire.class));
        cards.add(new SetCardInfo("Rakdos Pit Dragon", 57, Rarity.RARE, mage.cards.r.RakdosPitDragon.class));
        cards.add(new SetCardInfo("Splatter Thug", 58, Rarity.COMMON, mage.cards.s.SplatterThug.class));
        cards.add(new SetCardInfo("Utvara Hellkite", 59, Rarity.MYTHIC, mage.cards.u.UtvaraHellkite.class));
        cards.add(new SetCardInfo("Auger Spree", 60, Rarity.COMMON, mage.cards.a.AugerSpree.class));
        cards.add(new SetCardInfo("Avatar of Discord", 61, Rarity.RARE, mage.cards.a.AvatarOfDiscord.class));
        cards.add(new SetCardInfo("Carnival Hellsteed", 62, Rarity.RARE, mage.cards.c.CarnivalHellsteed.class));
        cards.add(new SetCardInfo("Dreadbore", 63, Rarity.RARE, mage.cards.d.Dreadbore.class));
        cards.add(new SetCardInfo("Jagged Poppet", 64, Rarity.UNCOMMON, mage.cards.j.JaggedPoppet.class));
        cards.add(new SetCardInfo("Lyzolda, the Blood Witch", 65, Rarity.RARE, mage.cards.l.LyzoldaTheBloodWitch.class));
        cards.add(new SetCardInfo("Master of Cruelties", 66, Rarity.MYTHIC, mage.cards.m.MasterOfCruelties.class));
        cards.add(new SetCardInfo("Rakdos Cackler", 67, Rarity.UNCOMMON, mage.cards.r.RakdosCackler.class));
        cards.add(new SetCardInfo("Rakdos Charm", 68, Rarity.UNCOMMON, mage.cards.r.RakdosCharm.class));
        cards.add(new SetCardInfo("Rakdos Guildmage", 69, Rarity.UNCOMMON, mage.cards.r.RakdosGuildmage.class));
        cards.add(new SetCardInfo("Rakdos Shred-Freak", 70, Rarity.COMMON, mage.cards.r.RakdosShredFreak.class));
        cards.add(new SetCardInfo("Rakdos the Defiler", 71, Rarity.RARE, mage.cards.r.RakdosTheDefiler.class));
        cards.add(new SetCardInfo("Rakdos's Return", 72, Rarity.MYTHIC, mage.cards.r.RakdossReturn.class));
        cards.add(new SetCardInfo("Riot Spikes", 73, Rarity.COMMON, mage.cards.r.RiotSpikes.class));
        cards.add(new SetCardInfo("Wrecking Ball", 74, Rarity.COMMON, mage.cards.w.WreckingBall.class));
        cards.add(new SetCardInfo("Rakdos Keyrune", 75, Rarity.UNCOMMON, mage.cards.r.RakdosKeyrune.class));
        cards.add(new SetCardInfo("Rakdos Signet", 76, Rarity.UNCOMMON, mage.cards.r.RakdosSignet.class));
        cards.add(new SetCardInfo("Rakdos Carnarium", 77, Rarity.COMMON, mage.cards.r.RakdosCarnarium.class));
        cards.add(new SetCardInfo("Swamp", 78, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 79, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ruric Thar, the Unbowed", 80, Rarity.RARE, mage.cards.r.RuricTharTheUnbowed.class));
        cards.add(new SetCardInfo("Skarrgan Firebird", 81, Rarity.RARE, mage.cards.s.SkarrganFirebird.class));
        cards.add(new SetCardInfo("Birds of Paradise", 82, Rarity.RARE, mage.cards.b.BirdsOfParadise.class));
        cards.add(new SetCardInfo("Protean Hulk", 83, Rarity.RARE, mage.cards.p.ProteanHulk.class));
        cards.add(new SetCardInfo("Skarrgan Pit-Skulk", 84, Rarity.COMMON, mage.cards.s.SkarrganPitSkulk.class));
        cards.add(new SetCardInfo("Wasteland Viper", 85, Rarity.UNCOMMON, mage.cards.w.WastelandViper.class));
        cards.add(new SetCardInfo("Wurmweaver Coil", 86, Rarity.RARE, mage.cards.w.WurmweaverCoil.class));
        cards.add(new SetCardInfo("Borborygmos", 87, Rarity.RARE, mage.cards.b.Borborygmos.class));
        cards.add(new SetCardInfo("Burning-Tree Emissary", 88, Rarity.UNCOMMON, mage.cards.b.BurningTreeEmissary.class));
        cards.add(new SetCardInfo("Burning-Tree Shaman", 89, Rarity.RARE, mage.cards.b.BurningTreeShaman.class));
        cards.add(new SetCardInfo("Ghor-Clan Rampager", 90, Rarity.UNCOMMON, mage.cards.g.GhorClanRampager.class));
        cards.add(new SetCardInfo("Giant Solifuge", 91, Rarity.RARE, mage.cards.g.GiantSolifuge.class));
        cards.add(new SetCardInfo("Gruul Charm", 92, Rarity.UNCOMMON, mage.cards.g.GruulCharm.class));
        cards.add(new SetCardInfo("Pit Fight", 93, Rarity.COMMON, mage.cards.p.PitFight.class));
        cards.add(new SetCardInfo("Rubblebelt Raiders", 94, Rarity.RARE, mage.cards.r.RubblebeltRaiders.class));
        cards.add(new SetCardInfo("Rubblehulk", 95, Rarity.RARE, mage.cards.r.Rubblehulk.class));
        cards.add(new SetCardInfo("Rumbling Slum", 96, Rarity.RARE, mage.cards.r.RumblingSlum.class));
        cards.add(new SetCardInfo("Savage Twister", 97, Rarity.UNCOMMON, mage.cards.s.SavageTwister.class));
        cards.add(new SetCardInfo("Savageborn Hydra", 98, Rarity.MYTHIC, mage.cards.s.SavagebornHydra.class));
        cards.add(new SetCardInfo("Scab-Clan Mauler", 99, Rarity.COMMON, mage.cards.s.ScabClanMauler.class));
        cards.add(new SetCardInfo("Ulasht, the Hate Seed", 100, Rarity.RARE, mage.cards.u.UlashtTheHateSeed.class));
        cards.add(new SetCardInfo("Zhur-Taa Druid", 101, Rarity.COMMON, mage.cards.z.ZhurTaaDruid.class));
        cards.add(new SetCardInfo("Zhur-Taa Swine", 102, Rarity.COMMON, mage.cards.z.ZhurTaaSwine.class));
        cards.add(new SetCardInfo("Gruul Signet", 103, Rarity.UNCOMMON, mage.cards.g.GruulSignet.class));
        cards.add(new SetCardInfo("Gruul Turf", 104, Rarity.COMMON, mage.cards.g.GruulTurf.class));
        cards.add(new SetCardInfo("Mountain", 105, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 106, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zegana, Utopian Speaker", 107, Rarity.RARE, mage.cards.z.ZeganaUtopianSpeaker.class));
        cards.add(new SetCardInfo("Cloudfin Raptor", 108, Rarity.COMMON, mage.cards.c.CloudfinRaptor.class));
        cards.add(new SetCardInfo("Rapid Hybridization", 109, Rarity.UNCOMMON, mage.cards.r.RapidHybridization.class));
        cards.add(new SetCardInfo("Cytoplast Root-Kin", 110, Rarity.RARE, mage.cards.c.CytoplastRootKin.class));
        cards.add(new SetCardInfo("Experiment One", 111, Rarity.UNCOMMON, mage.cards.e.ExperimentOne.class));
        cards.add(new SetCardInfo("Gyre Sage", 112, Rarity.RARE, mage.cards.g.GyreSage.class));
        cards.add(new SetCardInfo("Miming Slime", 113, Rarity.COMMON, mage.cards.m.MimingSlime.class));
        cards.add(new SetCardInfo("Vinelasher Kudzu", 114, Rarity.RARE, mage.cards.v.VinelasherKudzu.class));
        cards.add(new SetCardInfo("Coiling Oracle", 115, Rarity.COMMON, mage.cards.c.CoilingOracle.class));
        cards.add(new SetCardInfo("Elusive Krasis", 116, Rarity.UNCOMMON, mage.cards.e.ElusiveKrasis.class));
        cards.add(new SetCardInfo("Experiment Kraj", 117, Rarity.RARE, mage.cards.e.ExperimentKraj.class));
        cards.add(new SetCardInfo("Fathom Mage", 118, Rarity.RARE, mage.cards.f.FathomMage.class));
        cards.add(new SetCardInfo("Momir Vig, Simic Visionary", 119, Rarity.RARE, mage.cards.m.MomirVigSimicVisionary.class));
        cards.add(new SetCardInfo("Nimbus Swimmer", 120, Rarity.UNCOMMON, mage.cards.n.NimbusSwimmer.class));
        cards.add(new SetCardInfo("Omnibian", 121, Rarity.RARE, mage.cards.o.Omnibian.class));
        cards.add(new SetCardInfo("Plaxcaster Frogling", 122, Rarity.UNCOMMON, mage.cards.p.PlaxcasterFrogling.class));
        cards.add(new SetCardInfo("Progenitor Mimic", 123, Rarity.MYTHIC, mage.cards.p.ProgenitorMimic.class));
        cards.add(new SetCardInfo("Simic Sky Swallower", 124, Rarity.RARE, mage.cards.s.SimicSkySwallower.class));
        cards.add(new SetCardInfo("Trygon Predator", 125, Rarity.UNCOMMON, mage.cards.t.TrygonPredator.class));
        cards.add(new SetCardInfo("Urban Evolution", 126, Rarity.UNCOMMON, mage.cards.u.UrbanEvolution.class));
        cards.add(new SetCardInfo("Voidslime", 127, Rarity.RARE, mage.cards.v.Voidslime.class));
        cards.add(new SetCardInfo("Vorel of the Hull Clade", 128, Rarity.RARE, mage.cards.v.VorelOfTheHullClade.class));
        cards.add(new SetCardInfo("Zameck Guildmage", 129, Rarity.UNCOMMON, mage.cards.z.ZameckGuildmage.class));
        cards.add(new SetCardInfo("Simic Signet", 130, Rarity.UNCOMMON, mage.cards.s.SimicSignet.class));
        cards.add(new SetCardInfo("Simic Growth Chamber", 131, Rarity.UNCOMMON, mage.cards.s.SimicGrowthChamber.class));
        cards.add(new SetCardInfo("Forest", 133, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 132, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
    }
}
