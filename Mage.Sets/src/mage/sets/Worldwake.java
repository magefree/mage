
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Worldwake extends ExpansionSet {

    private static final Worldwake instance = new Worldwake();

    public static Worldwake getInstance() {
        return instance;
    }

    private Worldwake() {
        super("Worldwake", "WWK", ExpansionSet.buildDate(2010, 1, 30), SetType.EXPANSION);
        this.blockName = "Zendikar";
        this.parentSet = Zendikar.getInstance();
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Abyssal Persecutor", 47, Rarity.MYTHIC, mage.cards.a.AbyssalPersecutor.class));
        cards.add(new SetCardInfo("Admonition Angel", 1, Rarity.MYTHIC, mage.cards.a.AdmonitionAngel.class));
        cards.add(new SetCardInfo("Aether Tradewinds", 24, Rarity.COMMON, mage.cards.a.AetherTradewinds.class));
        cards.add(new SetCardInfo("Agadeem Occultist", 48, Rarity.RARE, mage.cards.a.AgadeemOccultist.class));
        cards.add(new SetCardInfo("Akoum Battlesinger", 71, Rarity.COMMON, mage.cards.a.AkoumBattlesinger.class));
        cards.add(new SetCardInfo("Amulet of Vigor", 121, Rarity.RARE, mage.cards.a.AmuletOfVigor.class));
        cards.add(new SetCardInfo("Anowon, the Ruin Sage", 49, Rarity.RARE, mage.cards.a.AnowonTheRuinSage.class));
        cards.add(new SetCardInfo("Apex Hawks", 2, Rarity.COMMON, mage.cards.a.ApexHawks.class));
        cards.add(new SetCardInfo("Arbor Elf", 95, Rarity.COMMON, mage.cards.a.ArborElf.class));
        cards.add(new SetCardInfo("Archon of Redemption", 3, Rarity.RARE, mage.cards.a.ArchonOfRedemption.class));
        cards.add(new SetCardInfo("Avenger of Zendikar", 96, Rarity.MYTHIC, mage.cards.a.AvengerOfZendikar.class));
        cards.add(new SetCardInfo("Basilisk Collar", 122, Rarity.RARE, mage.cards.b.BasiliskCollar.class));
        cards.add(new SetCardInfo("Battle Hurda", 4, Rarity.COMMON, mage.cards.b.BattleHurda.class));
        cards.add(new SetCardInfo("Bazaar Trader", 72, Rarity.RARE, mage.cards.b.BazaarTrader.class));
        cards.add(new SetCardInfo("Bestial Menace", 97, Rarity.UNCOMMON, mage.cards.b.BestialMenace.class));
        cards.add(new SetCardInfo("Bloodhusk Ritualist", 50, Rarity.UNCOMMON, mage.cards.b.BloodhuskRitualist.class));
        cards.add(new SetCardInfo("Bojuka Bog", 132, Rarity.COMMON, mage.cards.b.BojukaBog.class));
        cards.add(new SetCardInfo("Bojuka Brigand", 51, Rarity.COMMON, mage.cards.b.BojukaBrigand.class));
        cards.add(new SetCardInfo("Brink of Disaster", 52, Rarity.COMMON, mage.cards.b.BrinkOfDisaster.class));
        cards.add(new SetCardInfo("Bull Rush", 73, Rarity.COMMON, mage.cards.b.BullRush.class));
        cards.add(new SetCardInfo("Butcher of Malakir", 53, Rarity.RARE, mage.cards.b.ButcherOfMalakir.class));
        cards.add(new SetCardInfo("Calcite Snapper", 25, Rarity.COMMON, mage.cards.c.CalciteSnapper.class));
        cards.add(new SetCardInfo("Canopy Cover", 98, Rarity.UNCOMMON, mage.cards.c.CanopyCover.class));
        cards.add(new SetCardInfo("Caustic Crawler", 54, Rarity.UNCOMMON, mage.cards.c.CausticCrawler.class));
        cards.add(new SetCardInfo("Celestial Colonnade", 133, Rarity.RARE, mage.cards.c.CelestialColonnade.class));
        cards.add(new SetCardInfo("Chain Reaction", 74, Rarity.RARE, mage.cards.c.ChainReaction.class));
        cards.add(new SetCardInfo("Claws of Valakut", 75, Rarity.COMMON, mage.cards.c.ClawsOfValakut.class));
        cards.add(new SetCardInfo("Comet Storm", 76, Rarity.MYTHIC, mage.cards.c.CometStorm.class));
        cards.add(new SetCardInfo("Corrupted Zendikon", 55, Rarity.COMMON, mage.cards.c.CorruptedZendikon.class));
        cards.add(new SetCardInfo("Cosi's Ravager", 77, Rarity.COMMON, mage.cards.c.CosisRavager.class));
        cards.add(new SetCardInfo("Creeping Tar Pit", 134, Rarity.RARE, mage.cards.c.CreepingTarPit.class));
        cards.add(new SetCardInfo("Crusher Zendikon", 78, Rarity.COMMON, mage.cards.c.CrusherZendikon.class));
        cards.add(new SetCardInfo("Cunning Sparkmage", 79, Rarity.UNCOMMON, mage.cards.c.CunningSparkmage.class));
        cards.add(new SetCardInfo("Dead Reckoning", 56, Rarity.COMMON, mage.cards.d.DeadReckoning.class));
        cards.add(new SetCardInfo("Deathforge Shaman", 80, Rarity.UNCOMMON, mage.cards.d.DeathforgeShaman.class));
        cards.add(new SetCardInfo("Death's Shadow", 57, Rarity.RARE, mage.cards.d.DeathsShadow.class));
        cards.add(new SetCardInfo("Dispel", 26, Rarity.COMMON, mage.cards.d.Dispel.class));
        cards.add(new SetCardInfo("Dragonmaster Outcast", 81, Rarity.MYTHIC, mage.cards.d.DragonmasterOutcast.class));
        cards.add(new SetCardInfo("Dread Statuary", 135, Rarity.UNCOMMON, mage.cards.d.DreadStatuary.class));
        cards.add(new SetCardInfo("Enclave Elite", 27, Rarity.COMMON, mage.cards.e.EnclaveElite.class));
        cards.add(new SetCardInfo("Everflowing Chalice", 123, Rarity.UNCOMMON, mage.cards.e.EverflowingChalice.class));
        cards.add(new SetCardInfo("Explore", 99, Rarity.COMMON, mage.cards.e.Explore.class));
        cards.add(new SetCardInfo("Eye of Ugin", 136, Rarity.MYTHIC, mage.cards.e.EyeOfUgin.class));
        cards.add(new SetCardInfo("Feral Contest", 100, Rarity.COMMON, mage.cards.f.FeralContest.class));
        cards.add(new SetCardInfo("Fledgling Griffin", 5, Rarity.COMMON, mage.cards.f.FledglingGriffin.class));
        cards.add(new SetCardInfo("Gnarlid Pack", 101, Rarity.COMMON, mage.cards.g.GnarlidPack.class));
        cards.add(new SetCardInfo("Goblin Roughrider", 82, Rarity.COMMON, mage.cards.g.GoblinRoughrider.class));
        cards.add(new SetCardInfo("Goliath Sphinx", 28, Rarity.RARE, mage.cards.g.GoliathSphinx.class));
        cards.add(new SetCardInfo("Grappler Spider", 102, Rarity.COMMON, mage.cards.g.GrapplerSpider.class));
        cards.add(new SetCardInfo("Graypelt Hunter", 103, Rarity.COMMON, mage.cards.g.GraypeltHunter.class));
        cards.add(new SetCardInfo("Grotag Thrasher", 83, Rarity.COMMON, mage.cards.g.GrotagThrasher.class));
        cards.add(new SetCardInfo("Groundswell", 104, Rarity.COMMON, mage.cards.g.Groundswell.class));
        cards.add(new SetCardInfo("Guardian Zendikon", 6, Rarity.COMMON, mage.cards.g.GuardianZendikon.class));
        cards.add(new SetCardInfo("Hada Freeblade", 7, Rarity.UNCOMMON, mage.cards.h.HadaFreeblade.class));
        cards.add(new SetCardInfo("Halimar Depths", 137, Rarity.COMMON, mage.cards.h.HalimarDepths.class));
        cards.add(new SetCardInfo("Halimar Excavator", 29, Rarity.COMMON, mage.cards.h.HalimarExcavator.class));
        cards.add(new SetCardInfo("Hammer of Ruin", 124, Rarity.UNCOMMON, mage.cards.h.HammerOfRuin.class));
        cards.add(new SetCardInfo("Harabaz Druid", 105, Rarity.RARE, mage.cards.h.HarabazDruid.class));
        cards.add(new SetCardInfo("Hedron Rover", 125, Rarity.COMMON, mage.cards.h.HedronRover.class));
        cards.add(new SetCardInfo("Horizon Drake", 30, Rarity.UNCOMMON, mage.cards.h.HorizonDrake.class));
        cards.add(new SetCardInfo("Iona's Judgment", 8, Rarity.COMMON, mage.cards.i.IonasJudgment.class));
        cards.add(new SetCardInfo("Jace, the Mind Sculptor", 31, Rarity.MYTHIC, mage.cards.j.JaceTheMindSculptor.class));
        cards.add(new SetCardInfo("Jagwasp Swarm", 58, Rarity.COMMON, mage.cards.j.JagwaspSwarm.class));
        cards.add(new SetCardInfo("Join the Ranks", 9, Rarity.COMMON, mage.cards.j.JoinTheRanks.class));
        cards.add(new SetCardInfo("Joraga Warcaller", 106, Rarity.RARE, mage.cards.j.JoragaWarcaller.class));
        cards.add(new SetCardInfo("Jwari Shapeshifter", 32, Rarity.RARE, mage.cards.j.JwariShapeshifter.class));
        cards.add(new SetCardInfo("Kalastria Highborn", 59, Rarity.RARE, mage.cards.k.KalastriaHighborn.class));
        cards.add(new SetCardInfo("Kazuul, Tyrant of the Cliffs", 84, Rarity.RARE, mage.cards.k.KazuulTyrantOfTheCliffs.class));
        cards.add(new SetCardInfo("Khalni Garden", 138, Rarity.COMMON, mage.cards.k.KhalniGarden.class));
        cards.add(new SetCardInfo("Kitesail", 126, Rarity.COMMON, mage.cards.k.Kitesail.class));
        cards.add(new SetCardInfo("Kitesail Apprentice", 10, Rarity.COMMON, mage.cards.k.KitesailApprentice.class));
        cards.add(new SetCardInfo("Kor Firewalker", 11, Rarity.UNCOMMON, mage.cards.k.KorFirewalker.class));
        cards.add(new SetCardInfo("Lavaclaw Reaches", 139, Rarity.RARE, mage.cards.l.LavaclawReaches.class));
        cards.add(new SetCardInfo("Leatherback Baloth", 107, Rarity.UNCOMMON, mage.cards.l.LeatherbackBaloth.class));
        cards.add(new SetCardInfo("Lightkeeper of Emeria", 12, Rarity.UNCOMMON, mage.cards.l.LightkeeperOfEmeria.class));
        cards.add(new SetCardInfo("Loam Lion", 13, Rarity.UNCOMMON, mage.cards.l.LoamLion.class));
        cards.add(new SetCardInfo("Lodestone Golem", 127, Rarity.RARE, mage.cards.l.LodestoneGolem.class));
        cards.add(new SetCardInfo("Marshal's Anthem", 15, Rarity.RARE, mage.cards.m.MarshalsAnthem.class));
        cards.add(new SetCardInfo("Marsh Threader", 14, Rarity.COMMON, mage.cards.m.MarshThreader.class));
        cards.add(new SetCardInfo("Mire's Toll", 60, Rarity.COMMON, mage.cards.m.MiresToll.class));
        cards.add(new SetCardInfo("Mordant Dragon", 85, Rarity.RARE, mage.cards.m.MordantDragon.class));
        cards.add(new SetCardInfo("Mysteries of the Deep", 33, Rarity.COMMON, mage.cards.m.MysteriesOfTheDeep.class));
        cards.add(new SetCardInfo("Nature's Claim", 108, Rarity.COMMON, mage.cards.n.NaturesClaim.class));
        cards.add(new SetCardInfo("Nemesis Trap", 61, Rarity.UNCOMMON, mage.cards.n.NemesisTrap.class));
        cards.add(new SetCardInfo("Novablast Wurm", 119, Rarity.MYTHIC, mage.cards.n.NovablastWurm.class));
        cards.add(new SetCardInfo("Omnath, Locus of Mana", 109, Rarity.MYTHIC, mage.cards.o.OmnathLocusOfMana.class));
        cards.add(new SetCardInfo("Perimeter Captain", 16, Rarity.UNCOMMON, mage.cards.p.PerimeterCaptain.class));
        cards.add(new SetCardInfo("Permafrost Trap", 34, Rarity.UNCOMMON, mage.cards.p.PermafrostTrap.class));
        cards.add(new SetCardInfo("Pilgrim's Eye", 128, Rarity.COMMON, mage.cards.p.PilgrimsEye.class));
        cards.add(new SetCardInfo("Pulse Tracker", 62, Rarity.COMMON, mage.cards.p.PulseTracker.class));
        cards.add(new SetCardInfo("Quag Vampires", 63, Rarity.COMMON, mage.cards.q.QuagVampires.class));
        cards.add(new SetCardInfo("Quest for Renewal", 110, Rarity.UNCOMMON, mage.cards.q.QuestForRenewal.class));
        cards.add(new SetCardInfo("Quest for the Goblin Lord", 86, Rarity.UNCOMMON, mage.cards.q.QuestForTheGoblinLord.class));
        cards.add(new SetCardInfo("Quest for the Nihil Stone", 64, Rarity.RARE, mage.cards.q.QuestForTheNihilStone.class));
        cards.add(new SetCardInfo("Quest for Ula's Temple", 35, Rarity.RARE, mage.cards.q.QuestForUlasTemple.class));
        cards.add(new SetCardInfo("Quicksand", 140, Rarity.COMMON, mage.cards.q.Quicksand.class));
        cards.add(new SetCardInfo("Raging Ravine", 141, Rarity.RARE, mage.cards.r.RagingRavine.class));
        cards.add(new SetCardInfo("Razor Boomerang", 129, Rarity.UNCOMMON, mage.cards.r.RazorBoomerang.class));
        cards.add(new SetCardInfo("Refraction Trap", 17, Rarity.UNCOMMON, mage.cards.r.RefractionTrap.class));
        cards.add(new SetCardInfo("Rest for the Weary", 18, Rarity.COMMON, mage.cards.r.RestForTheWeary.class));
        cards.add(new SetCardInfo("Ricochet Trap", 87, Rarity.UNCOMMON, mage.cards.r.RicochetTrap.class));
        cards.add(new SetCardInfo("Roiling Terrain", 88, Rarity.COMMON, mage.cards.r.RoilingTerrain.class));
        cards.add(new SetCardInfo("Ruin Ghost", 19, Rarity.UNCOMMON, mage.cards.r.RuinGhost.class));
        cards.add(new SetCardInfo("Rumbling Aftershocks", 89, Rarity.UNCOMMON, mage.cards.r.RumblingAftershocks.class));
        cards.add(new SetCardInfo("Ruthless Cullblade", 65, Rarity.COMMON, mage.cards.r.RuthlessCullblade.class));
        cards.add(new SetCardInfo("Scrib Nibblers", 66, Rarity.UNCOMMON, mage.cards.s.ScribNibblers.class));
        cards.add(new SetCardInfo("Searing Blaze", 90, Rarity.COMMON, mage.cards.s.SearingBlaze.class));
        cards.add(new SetCardInfo("Seer's Sundial", 130, Rarity.RARE, mage.cards.s.SeersSundial.class));
        cards.add(new SetCardInfo("Sejiri Merfolk", 36, Rarity.UNCOMMON, mage.cards.s.SejiriMerfolk.class));
        cards.add(new SetCardInfo("Sejiri Steppe", 142, Rarity.COMMON, mage.cards.s.SejiriSteppe.class));
        cards.add(new SetCardInfo("Selective Memory", 37, Rarity.RARE, mage.cards.s.SelectiveMemory.class));
        cards.add(new SetCardInfo("Shoreline Salvager", 67, Rarity.UNCOMMON, mage.cards.s.ShorelineSalvager.class));
        cards.add(new SetCardInfo("Skitter of Lizards", 91, Rarity.COMMON, mage.cards.s.SkitterOfLizards.class));
        cards.add(new SetCardInfo("Slavering Nulls", 92, Rarity.UNCOMMON, mage.cards.s.SlaveringNulls.class));
        cards.add(new SetCardInfo("Slingbow Trap", 111, Rarity.UNCOMMON, mage.cards.s.SlingbowTrap.class));
        cards.add(new SetCardInfo("Smoldering Spires", 143, Rarity.COMMON, mage.cards.s.SmolderingSpires.class));
        cards.add(new SetCardInfo("Smother", 68, Rarity.UNCOMMON, mage.cards.s.Smother.class));
        cards.add(new SetCardInfo("Snapping Creeper", 112, Rarity.COMMON, mage.cards.s.SnappingCreeper.class));
        cards.add(new SetCardInfo("Spell Contortion", 38, Rarity.UNCOMMON, mage.cards.s.SpellContortion.class));
        cards.add(new SetCardInfo("Stirring Wildwood", 144, Rarity.RARE, mage.cards.s.StirringWildwood.class));
        cards.add(new SetCardInfo("Stoneforge Mystic", 20, Rarity.RARE, mage.cards.s.StoneforgeMystic.class));
        cards.add(new SetCardInfo("Stone Idol Trap", 93, Rarity.RARE, mage.cards.s.StoneIdolTrap.class));
        cards.add(new SetCardInfo("Strength of the Tajuru", 113, Rarity.RARE, mage.cards.s.StrengthOfTheTajuru.class));
        cards.add(new SetCardInfo("Summit Apes", 114, Rarity.UNCOMMON, mage.cards.s.SummitApes.class));
        cards.add(new SetCardInfo("Surrakar Banisher", 39, Rarity.COMMON, mage.cards.s.SurrakarBanisher.class));
        cards.add(new SetCardInfo("Talus Paladin", 21, Rarity.RARE, mage.cards.t.TalusPaladin.class));
        cards.add(new SetCardInfo("Tectonic Edge", 145, Rarity.UNCOMMON, mage.cards.t.TectonicEdge.class));
        cards.add(new SetCardInfo("Terastodon", 115, Rarity.RARE, mage.cards.t.Terastodon.class));
        cards.add(new SetCardInfo("Terra Eternal", 22, Rarity.RARE, mage.cards.t.TerraEternal.class));
        cards.add(new SetCardInfo("Thada Adel, Acquisitor", 40, Rarity.RARE, mage.cards.t.ThadaAdelAcquisitor.class));
        cards.add(new SetCardInfo("Tideforce Elemental", 41, Rarity.UNCOMMON, mage.cards.t.TideforceElemental.class));
        cards.add(new SetCardInfo("Tomb Hex", 69, Rarity.COMMON, mage.cards.t.TombHex.class));
        cards.add(new SetCardInfo("Treasure Hunt", 42, Rarity.COMMON, mage.cards.t.TreasureHunt.class));
        cards.add(new SetCardInfo("Tuktuk Scrapper", 94, Rarity.UNCOMMON, mage.cards.t.TuktukScrapper.class));
        cards.add(new SetCardInfo("Twitch", 43, Rarity.COMMON, mage.cards.t.Twitch.class));
        cards.add(new SetCardInfo("Urge to Feed", 70, Rarity.UNCOMMON, mage.cards.u.UrgeToFeed.class));
        cards.add(new SetCardInfo("Vapor Snare", 44, Rarity.UNCOMMON, mage.cards.v.VaporSnare.class));
        cards.add(new SetCardInfo("Vastwood Animist", 116, Rarity.UNCOMMON, mage.cards.v.VastwoodAnimist.class));
        cards.add(new SetCardInfo("Vastwood Zendikon", 117, Rarity.COMMON, mage.cards.v.VastwoodZendikon.class));
        cards.add(new SetCardInfo("Veteran's Reflexes", 23, Rarity.COMMON, mage.cards.v.VeteransReflexes.class));
        cards.add(new SetCardInfo("Voyager Drake", 45, Rarity.UNCOMMON, mage.cards.v.VoyagerDrake.class));
        cards.add(new SetCardInfo("Walking Atlas", 131, Rarity.COMMON, mage.cards.w.WalkingAtlas.class));
        cards.add(new SetCardInfo("Wind Zendikon", 46, Rarity.COMMON, mage.cards.w.WindZendikon.class));
        cards.add(new SetCardInfo("Wolfbriar Elemental", 118, Rarity.RARE, mage.cards.w.WolfbriarElemental.class));
        cards.add(new SetCardInfo("Wrexial, the Risen Deep", 120, Rarity.MYTHIC, mage.cards.w.WrexialTheRisenDeep.class));
    }

}
