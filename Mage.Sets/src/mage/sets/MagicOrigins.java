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
 *
 * @author fireshoes
 */
public final class MagicOrigins extends ExpansionSet {

    private static final MagicOrigins instance = new MagicOrigins();

    public static MagicOrigins getInstance() {
        return instance;
    }

    private MagicOrigins() {
        super("Magic Origins", "ORI", ExpansionSet.buildDate(2015, 7, 17), SetType.CORE);
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.numBoosterDoubleFaced = -1;
        this.maxCardNumberInBooster = 272;
        cards.add(new SetCardInfo("Abbot of Keral Keep", 127, Rarity.RARE, mage.cards.a.AbbotOfKeralKeep.class));
        cards.add(new SetCardInfo("Acolyte of the Inferno", 128, Rarity.UNCOMMON, mage.cards.a.AcolyteOfTheInferno.class));
        cards.add(new SetCardInfo("Act of Treason", 129, Rarity.COMMON, mage.cards.a.ActOfTreason.class));
        cards.add(new SetCardInfo("Aegis Angel", 273, Rarity.RARE, mage.cards.a.AegisAngel.class));
        cards.add(new SetCardInfo("Aerial Volley", 168, Rarity.COMMON, mage.cards.a.AerialVolley.class));
        cards.add(new SetCardInfo("Akroan Jailer", 1, Rarity.COMMON, mage.cards.a.AkroanJailer.class));
        cards.add(new SetCardInfo("Akroan Sergeant", 130, Rarity.COMMON, mage.cards.a.AkroanSergeant.class));
        cards.add(new SetCardInfo("Alchemist's Vial", 220, Rarity.COMMON, mage.cards.a.AlchemistsVial.class));
        cards.add(new SetCardInfo("Alhammarret, High Arbiter", 43, Rarity.RARE, mage.cards.a.AlhammarretHighArbiter.class));
        cards.add(new SetCardInfo("Alhammarret's Archive", 221, Rarity.MYTHIC, mage.cards.a.AlhammarretsArchive.class));
        cards.add(new SetCardInfo("Ampryn Tactician", 2, Rarity.COMMON, mage.cards.a.AmprynTactician.class));
        cards.add(new SetCardInfo("Anchor to the Aether", 44, Rarity.UNCOMMON, mage.cards.a.AnchorToTheAether.class));
        cards.add(new SetCardInfo("Angel's Tomb", 222, Rarity.UNCOMMON, mage.cards.a.AngelsTomb.class));
        cards.add(new SetCardInfo("Animist's Awakening", 169, Rarity.RARE, mage.cards.a.AnimistsAwakening.class));
        cards.add(new SetCardInfo("Anointer of Champions", 3, Rarity.UNCOMMON, mage.cards.a.AnointerOfChampions.class));
        cards.add(new SetCardInfo("Archangel of Tithes", 4, Rarity.MYTHIC, mage.cards.a.ArchangelOfTithes.class));
        cards.add(new SetCardInfo("Artificer's Epiphany", 45, Rarity.COMMON, mage.cards.a.ArtificersEpiphany.class));
        cards.add(new SetCardInfo("Aspiring Aeronaut", 46, Rarity.COMMON, mage.cards.a.AspiringAeronaut.class));
        cards.add(new SetCardInfo("Auramancer", 5, Rarity.COMMON, mage.cards.a.Auramancer.class));
        cards.add(new SetCardInfo("Avaricious Dragon", 131, Rarity.MYTHIC, mage.cards.a.AvariciousDragon.class));
        cards.add(new SetCardInfo("Aven Battle Priest", 6, Rarity.COMMON, mage.cards.a.AvenBattlePriest.class));
        cards.add(new SetCardInfo("Battlefield Forge", 244, Rarity.RARE, mage.cards.b.BattlefieldForge.class));
        cards.add(new SetCardInfo("Bellows Lizard", 132, Rarity.COMMON, mage.cards.b.BellowsLizard.class));
        cards.add(new SetCardInfo("Blazing Hellhound", 210, Rarity.UNCOMMON, mage.cards.b.BlazingHellhound.class));
        cards.add(new SetCardInfo("Blessed Spirits", 7, Rarity.UNCOMMON, mage.cards.b.BlessedSpirits.class));
        cards.add(new SetCardInfo("Blightcaster", 85, Rarity.UNCOMMON, mage.cards.b.Blightcaster.class));
        cards.add(new SetCardInfo("Blood-Cursed Knight", 211, Rarity.UNCOMMON, mage.cards.b.BloodCursedKnight.class));
        cards.add(new SetCardInfo("Boggart Brute", 133, Rarity.COMMON, mage.cards.b.BoggartBrute.class));
        cards.add(new SetCardInfo("Bonded Construct", 223, Rarity.COMMON, mage.cards.b.BondedConstruct.class));
        cards.add(new SetCardInfo("Bone to Ash", 47, Rarity.COMMON, mage.cards.b.BoneToAsh.class));
        cards.add(new SetCardInfo("Bounding Krasis", 212, Rarity.UNCOMMON, mage.cards.b.BoundingKrasis.class));
        cards.add(new SetCardInfo("Brawler's Plate", 224, Rarity.UNCOMMON, mage.cards.b.BrawlersPlate.class));
        cards.add(new SetCardInfo("Calculated Dismissal", 48, Rarity.COMMON, mage.cards.c.CalculatedDismissal.class));
        cards.add(new SetCardInfo("Call of the Full Moon", 134, Rarity.UNCOMMON, mage.cards.c.CallOfTheFullMoon.class));
        cards.add(new SetCardInfo("Catacomb Slug", 86, Rarity.COMMON, mage.cards.c.CatacombSlug.class));
        cards.add(new SetCardInfo("Caustic Caterpillar", 170, Rarity.COMMON, mage.cards.c.CausticCaterpillar.class));
        cards.add(new SetCardInfo("Caves of Koilos", 245, Rarity.RARE, mage.cards.c.CavesOfKoilos.class));
        cards.add(new SetCardInfo("Celestial Flare", 8, Rarity.COMMON, mage.cards.c.CelestialFlare.class));
        cards.add(new SetCardInfo("Chandra, Fire of Kaladesh", 135, Rarity.MYTHIC, mage.cards.c.ChandraFireOfKaladesh.class));
        cards.add(new SetCardInfo("Chandra, Roaring Flame", 135, Rarity.MYTHIC, mage.cards.c.ChandraRoaringFlame.class));
        cards.add(new SetCardInfo("Chandra's Fury", 136, Rarity.COMMON, mage.cards.c.ChandrasFury.class));
        cards.add(new SetCardInfo("Chandra's Ignition", 137, Rarity.RARE, mage.cards.c.ChandrasIgnition.class));
        cards.add(new SetCardInfo("Charging Griffin", 9, Rarity.COMMON, mage.cards.c.ChargingGriffin.class));
        cards.add(new SetCardInfo("Chief of the Foundry", 225, Rarity.UNCOMMON, mage.cards.c.ChiefOfTheFoundry.class));
        cards.add(new SetCardInfo("Citadel Castellan", 213, Rarity.UNCOMMON, mage.cards.c.CitadelCastellan.class));
        cards.add(new SetCardInfo("Clash of Wills", 49, Rarity.UNCOMMON, mage.cards.c.ClashOfWills.class));
        cards.add(new SetCardInfo("Claustrophobia", 50, Rarity.COMMON, mage.cards.c.Claustrophobia.class));
        cards.add(new SetCardInfo("Cleric of the Forward Order", 10, Rarity.COMMON, mage.cards.c.ClericOfTheForwardOrder.class));
        cards.add(new SetCardInfo("Cobblebrute", 138, Rarity.COMMON, mage.cards.c.Cobblebrute.class));
        cards.add(new SetCardInfo("Conclave Naturalists", 171, Rarity.UNCOMMON, mage.cards.c.ConclaveNaturalists.class));
        cards.add(new SetCardInfo("Consecrated by Blood", 87, Rarity.UNCOMMON, mage.cards.c.ConsecratedByBlood.class));
        cards.add(new SetCardInfo("Consul's Lieutenant", 11, Rarity.UNCOMMON, mage.cards.c.ConsulsLieutenant.class));
        cards.add(new SetCardInfo("Cruel Revival", 88, Rarity.UNCOMMON, mage.cards.c.CruelRevival.class));
        cards.add(new SetCardInfo("Dark Dabbling", 89, Rarity.COMMON, mage.cards.d.DarkDabbling.class));
        cards.add(new SetCardInfo("Dark Petition", 90, Rarity.RARE, mage.cards.d.DarkPetition.class));
        cards.add(new SetCardInfo("Day's Undoing", 51, Rarity.MYTHIC, mage.cards.d.DaysUndoing.class));
        cards.add(new SetCardInfo("Deadbridge Shaman", 91, Rarity.COMMON, mage.cards.d.DeadbridgeShaman.class));
        cards.add(new SetCardInfo("Deep-Sea Terror", 52, Rarity.COMMON, mage.cards.d.DeepSeaTerror.class));
        cards.add(new SetCardInfo("Demolish", 139, Rarity.COMMON, mage.cards.d.Demolish.class));
        cards.add(new SetCardInfo("Demonic Pact", 92, Rarity.MYTHIC, mage.cards.d.DemonicPact.class));
        cards.add(new SetCardInfo("Despoiler of Souls", 93, Rarity.RARE, mage.cards.d.DespoilerOfSouls.class));
        cards.add(new SetCardInfo("Disciple of the Ring", 53, Rarity.MYTHIC, mage.cards.d.DiscipleOfTheRing.class));
        cards.add(new SetCardInfo("Disperse", 54, Rarity.COMMON, mage.cards.d.Disperse.class));
        cards.add(new SetCardInfo("Displacement Wave", 55, Rarity.RARE, mage.cards.d.DisplacementWave.class));
        cards.add(new SetCardInfo("Divine Verdict", 274, Rarity.COMMON, mage.cards.d.DivineVerdict.class));
        cards.add(new SetCardInfo("Dragon Fodder", 140, Rarity.COMMON, mage.cards.d.DragonFodder.class));
        cards.add(new SetCardInfo("Dreadwaters", 56, Rarity.COMMON, mage.cards.d.Dreadwaters.class));
        cards.add(new SetCardInfo("Dwynen, Gilt-Leaf Daen", 172, Rarity.RARE, mage.cards.d.DwynenGiltLeafDaen.class));
        cards.add(new SetCardInfo("Dwynen's Elite", 173, Rarity.UNCOMMON, mage.cards.d.DwynensElite.class));
        cards.add(new SetCardInfo("Eagle of the Watch", 275, Rarity.COMMON, mage.cards.e.EagleOfTheWatch.class));
        cards.add(new SetCardInfo("Elemental Bond", 174, Rarity.UNCOMMON, mage.cards.e.ElementalBond.class));
        cards.add(new SetCardInfo("Elvish Visionary", 175, Rarity.COMMON, mage.cards.e.ElvishVisionary.class));
        cards.add(new SetCardInfo("Embermaw Hellion", 141, Rarity.RARE, mage.cards.e.EmbermawHellion.class));
        cards.add(new SetCardInfo("Enlightened Ascetic", 12, Rarity.COMMON, mage.cards.e.EnlightenedAscetic.class));
        cards.add(new SetCardInfo("Enshrouding Mist", 13, Rarity.COMMON, mage.cards.e.EnshroudingMist.class));
        cards.add(new SetCardInfo("Enthralling Victor", 142, Rarity.UNCOMMON, mage.cards.e.EnthrallingVictor.class));
        cards.add(new SetCardInfo("Erebos's Titan", 94, Rarity.MYTHIC, mage.cards.e.ErebossTitan.class));
        cards.add(new SetCardInfo("Evolutionary Leap", 176, Rarity.RARE, mage.cards.e.EvolutionaryLeap.class));
        cards.add(new SetCardInfo("Evolving Wilds", 246, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Exquisite Firecraft", 143, Rarity.RARE, mage.cards.e.ExquisiteFirecraft.class));
        cards.add(new SetCardInfo("Eyeblight Assassin", 95, Rarity.COMMON, mage.cards.e.EyeblightAssassin.class));
        cards.add(new SetCardInfo("Eyeblight Massacre", 96, Rarity.UNCOMMON, mage.cards.e.EyeblightMassacre.class));
        cards.add(new SetCardInfo("Faerie Miscreant", 57, Rarity.COMMON, mage.cards.f.FaerieMiscreant.class));
        cards.add(new SetCardInfo("Fetid Imp", 97, Rarity.COMMON, mage.cards.f.FetidImp.class));
        cards.add(new SetCardInfo("Fiery Conclusion", 144, Rarity.UNCOMMON, mage.cards.f.FieryConclusion.class));
        cards.add(new SetCardInfo("Fiery Hellhound", 284, Rarity.COMMON, mage.cards.f.FieryHellhound.class));
        cards.add(new SetCardInfo("Fiery Impulse", 145, Rarity.COMMON, mage.cards.f.FieryImpulse.class));
        cards.add(new SetCardInfo("Firefiend Elemental", 146, Rarity.COMMON, mage.cards.f.FirefiendElemental.class));
        cards.add(new SetCardInfo("Flameshadow Conjuring", 147, Rarity.RARE, mage.cards.f.FlameshadowConjuring.class));
        cards.add(new SetCardInfo("Fleshbag Marauder", 98, Rarity.UNCOMMON, mage.cards.f.FleshbagMarauder.class));
        cards.add(new SetCardInfo("Flesh to Dust", 280, Rarity.COMMON, mage.cards.f.FleshToDust.class));
        cards.add(new SetCardInfo("Forest", 269, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 270, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 271, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 272, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Foundry of the Consuls", 247, Rarity.UNCOMMON, mage.cards.f.FoundryOfTheConsuls.class));
        cards.add(new SetCardInfo("Gaea's Revenge", 177, Rarity.RARE, mage.cards.g.GaeasRevenge.class));
        cards.add(new SetCardInfo("Gather the Pack", 178, Rarity.UNCOMMON, mage.cards.g.GatherThePack.class));
        cards.add(new SetCardInfo("Ghirapur Aether Grid", 148, Rarity.UNCOMMON, mage.cards.g.GhirapurAetherGrid.class));
        cards.add(new SetCardInfo("Ghirapur Gearcrafter", 149, Rarity.COMMON, mage.cards.g.GhirapurGearcrafter.class));
        cards.add(new SetCardInfo("Gideon, Battle-Forged", 23, Rarity.MYTHIC, mage.cards.g.GideonBattleForged.class));
        cards.add(new SetCardInfo("Gideon's Phalanx", 14, Rarity.RARE, mage.cards.g.GideonsPhalanx.class));
        cards.add(new SetCardInfo("Gilt-Leaf Winnower", 99, Rarity.RARE, mage.cards.g.GiltLeafWinnower.class));
        cards.add(new SetCardInfo("Gnarlroot Trapper", 100, Rarity.UNCOMMON, mage.cards.g.GnarlrootTrapper.class));
        cards.add(new SetCardInfo("Goblin Glory Chaser", 150, Rarity.UNCOMMON, mage.cards.g.GoblinGloryChaser.class));
        cards.add(new SetCardInfo("Goblin Piledriver", 151, Rarity.RARE, mage.cards.g.GoblinPiledriver.class));
        cards.add(new SetCardInfo("Gold-Forged Sentinel", 226, Rarity.UNCOMMON, mage.cards.g.GoldForgedSentinel.class));
        cards.add(new SetCardInfo("Grasp of the Hieromancer", 15, Rarity.COMMON, mage.cards.g.GraspOfTheHieromancer.class));
        cards.add(new SetCardInfo("Graveblade Marauder", 101, Rarity.RARE, mage.cards.g.GravebladeMarauder.class));
        cards.add(new SetCardInfo("Guardian Automaton", 227, Rarity.COMMON, mage.cards.g.GuardianAutomaton.class));
        cards.add(new SetCardInfo("Guardians of Meletis", 228, Rarity.COMMON, mage.cards.g.GuardiansOfMeletis.class));
        cards.add(new SetCardInfo("Hallowed Moonlight", 16, Rarity.RARE, mage.cards.h.HallowedMoonlight.class));
        cards.add(new SetCardInfo("Hangarback Walker", 229, Rarity.RARE, mage.cards.h.HangarbackWalker.class));
        cards.add(new SetCardInfo("Harbinger of the Tides", 58, Rarity.RARE, mage.cards.h.HarbingerOfTheTides.class));
        cards.add(new SetCardInfo("Healing Hands", 17, Rarity.COMMON, mage.cards.h.HealingHands.class));
        cards.add(new SetCardInfo("Heavy Infantry", 18, Rarity.COMMON, mage.cards.h.HeavyInfantry.class));
        cards.add(new SetCardInfo("Helm of the Gods", 230, Rarity.RARE, mage.cards.h.HelmOfTheGods.class));
        cards.add(new SetCardInfo("Herald of the Pantheon", 180, Rarity.RARE, mage.cards.h.HeraldOfThePantheon.class));
        cards.add(new SetCardInfo("Hitchclaw Recluse", 181, Rarity.COMMON, mage.cards.h.HitchclawRecluse.class));
        cards.add(new SetCardInfo("Hixus, Prison Warden", 19, Rarity.RARE, mage.cards.h.HixusPrisonWarden.class));
        cards.add(new SetCardInfo("Honored Hierarch", 182, Rarity.RARE, mage.cards.h.HonoredHierarch.class));
        cards.add(new SetCardInfo("Hydrolash", 59, Rarity.UNCOMMON, mage.cards.h.Hydrolash.class));
        cards.add(new SetCardInfo("Infectious Bloodlust", 152, Rarity.COMMON, mage.cards.i.InfectiousBloodlust.class));
        cards.add(new SetCardInfo("Infernal Scarring", 102, Rarity.COMMON, mage.cards.i.InfernalScarring.class));
        cards.add(new SetCardInfo("Infinite Obliteration", 103, Rarity.RARE, mage.cards.i.InfiniteObliteration.class));
        cards.add(new SetCardInfo("Into the Void", 277, Rarity.UNCOMMON, mage.cards.i.IntoTheVoid.class));
        cards.add(new SetCardInfo("Iroas's Champion", 214, Rarity.UNCOMMON, mage.cards.i.IroassChampion.class));
        cards.add(new SetCardInfo("Island", 257, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 258, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 259, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 260, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jace's Sanctum", 61, Rarity.RARE, mage.cards.j.JacesSanctum.class));
        cards.add(new SetCardInfo("Jace, Telepath Unbound", 60, Rarity.MYTHIC, mage.cards.j.JaceTelepathUnbound.class));
        cards.add(new SetCardInfo("Jace, Vryn's Prodigy", 60, Rarity.MYTHIC, mage.cards.j.JaceVrynsProdigy.class));
        cards.add(new SetCardInfo("Jayemdae Tome", 231, Rarity.UNCOMMON, mage.cards.j.JayemdaeTome.class));
        cards.add(new SetCardInfo("Jhessian Thief", 62, Rarity.UNCOMMON, mage.cards.j.JhessianThief.class));
        cards.add(new SetCardInfo("Joraga Invocation", 183, Rarity.UNCOMMON, mage.cards.j.JoragaInvocation.class));
        cards.add(new SetCardInfo("Knightly Valor", 22, Rarity.UNCOMMON, mage.cards.k.KnightlyValor.class));
        cards.add(new SetCardInfo("Knight of the Pilgrim's Road", 20, Rarity.COMMON, mage.cards.k.KnightOfThePilgrimsRoad.class));
        cards.add(new SetCardInfo("Knight of the White Orchid", 21, Rarity.RARE, mage.cards.k.KnightOfTheWhiteOrchid.class));
        cards.add(new SetCardInfo("Kothophed, Soul Hoarder", 104, Rarity.RARE, mage.cards.k.KothophedSoulHoarder.class));
        cards.add(new SetCardInfo("Kytheon, Hero of Akros", 23, Rarity.MYTHIC, mage.cards.k.KytheonHeroOfAkros.class));
        cards.add(new SetCardInfo("Kytheon's Irregulars", 24, Rarity.RARE, mage.cards.k.KytheonsIrregulars.class));
        cards.add(new SetCardInfo("Kytheon's Tactics", 25, Rarity.COMMON, mage.cards.k.KytheonsTactics.class));
        cards.add(new SetCardInfo("Languish", 105, Rarity.RARE, mage.cards.l.Languish.class));
        cards.add(new SetCardInfo("Leaf Gilder", 184, Rarity.COMMON, mage.cards.l.LeafGilder.class));
        cards.add(new SetCardInfo("Lightning Javelin", 153, Rarity.COMMON, mage.cards.l.LightningJavelin.class));
        cards.add(new SetCardInfo("Liliana, Defiant Necromancer", 106, Rarity.MYTHIC, mage.cards.l.LilianaDefiantNecromancer.class));
        cards.add(new SetCardInfo("Liliana, Heretical Healer", 106, Rarity.MYTHIC, mage.cards.l.LilianaHereticalHealer.class));
        cards.add(new SetCardInfo("Llanowar Empath", 185, Rarity.COMMON, mage.cards.l.LlanowarEmpath.class));
        cards.add(new SetCardInfo("Llanowar Wastes", 248, Rarity.RARE, mage.cards.l.LlanowarWastes.class));
        cards.add(new SetCardInfo("Macabre Waltz", 107, Rarity.COMMON, mage.cards.m.MacabreWaltz.class));
        cards.add(new SetCardInfo("Mage-Ring Bully", 154, Rarity.COMMON, mage.cards.m.MageRingBully.class));
        cards.add(new SetCardInfo("Mage-Ring Network", 249, Rarity.UNCOMMON, mage.cards.m.MageRingNetwork.class));
        cards.add(new SetCardInfo("Mage-Ring Responder", 232, Rarity.RARE, mage.cards.m.MageRingResponder.class));
        cards.add(new SetCardInfo("Magmatic Insight", 155, Rarity.UNCOMMON, mage.cards.m.MagmaticInsight.class));
        cards.add(new SetCardInfo("Mahamoti Djinn", 278, Rarity.RARE, mage.cards.m.MahamotiDjinn.class));
        cards.add(new SetCardInfo("Malakir Cullblade", 108, Rarity.UNCOMMON, mage.cards.m.MalakirCullblade.class));
        cards.add(new SetCardInfo("Managorger Hydra", 186, Rarity.RARE, mage.cards.m.ManagorgerHydra.class));
        cards.add(new SetCardInfo("Mantle of Webs", 187, Rarity.COMMON, mage.cards.m.MantleOfWebs.class));
        cards.add(new SetCardInfo("Maritime Guard", 63, Rarity.COMMON, mage.cards.m.MaritimeGuard.class));
        cards.add(new SetCardInfo("Meteorite", 233, Rarity.UNCOMMON, mage.cards.m.Meteorite.class));
        cards.add(new SetCardInfo("Might of the Masses", 188, Rarity.COMMON, mage.cards.m.MightOfTheMasses.class));
        cards.add(new SetCardInfo("Mighty Leap", 26, Rarity.COMMON, mage.cards.m.MightyLeap.class));
        cards.add(new SetCardInfo("Mind Rot", 281, Rarity.COMMON, mage.cards.m.MindRot.class));
        cards.add(new SetCardInfo("Mizzium Meddler", 64, Rarity.RARE, mage.cards.m.MizziumMeddler.class));
        cards.add(new SetCardInfo("Molten Vortex", 156, Rarity.RARE, mage.cards.m.MoltenVortex.class));
        cards.add(new SetCardInfo("Mountain", 265, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 266, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 267, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 268, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Murder Investigation", 27, Rarity.UNCOMMON, mage.cards.m.MurderInvestigation.class));
        cards.add(new SetCardInfo("Nantuko Husk", 109, Rarity.COMMON, mage.cards.n.NantukoHusk.class));
        cards.add(new SetCardInfo("Necromantic Summons", 110, Rarity.UNCOMMON, mage.cards.n.NecromanticSummons.class));
        cards.add(new SetCardInfo("Negate", 65, Rarity.COMMON, mage.cards.n.Negate.class));
        cards.add(new SetCardInfo("Nightmare", 282, Rarity.RARE, mage.cards.n.Nightmare.class));
        cards.add(new SetCardInfo("Nightsnare", 111, Rarity.COMMON, mage.cards.n.Nightsnare.class));
        cards.add(new SetCardInfo("Nissa, Sage Animist", 189, Rarity.MYTHIC, mage.cards.n.NissaSageAnimist.class));
        cards.add(new SetCardInfo("Nissa's Pilgrimage", 190, Rarity.COMMON, mage.cards.n.NissasPilgrimage.class));
        cards.add(new SetCardInfo("Nissa's Revelation", 191, Rarity.RARE, mage.cards.n.NissasRevelation.class));
        cards.add(new SetCardInfo("Nissa, Vastwood Seer", 189, Rarity.MYTHIC, mage.cards.n.NissaVastwoodSeer.class));
        cards.add(new SetCardInfo("Nivix Barrier", 66, Rarity.COMMON, mage.cards.n.NivixBarrier.class));
        cards.add(new SetCardInfo("Orbs of Warding", 234, Rarity.RARE, mage.cards.o.OrbsOfWarding.class));
        cards.add(new SetCardInfo("Orchard Spirit", 192, Rarity.COMMON, mage.cards.o.OrchardSpirit.class));
        cards.add(new SetCardInfo("Outland Colossus", 193, Rarity.RARE, mage.cards.o.OutlandColossus.class));
        cards.add(new SetCardInfo("Patron of the Valiant", 28, Rarity.UNCOMMON, mage.cards.p.PatronOfTheValiant.class));
        cards.add(new SetCardInfo("Pharika's Disciple", 194, Rarity.COMMON, mage.cards.p.PharikasDisciple.class));
        cards.add(new SetCardInfo("Pia and Kiran Nalaar", 157, Rarity.RARE, mage.cards.p.PiaAndKiranNalaar.class));
        cards.add(new SetCardInfo("Plains", 253, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 254, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 255, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 256, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plummet", 286, Rarity.COMMON, mage.cards.p.Plummet.class));
        cards.add(new SetCardInfo("Possessed Skaab", 215, Rarity.UNCOMMON, mage.cards.p.PossessedSkaab.class));
        cards.add(new SetCardInfo("Prickleboar", 158, Rarity.COMMON, mage.cards.p.Prickleboar.class));
        cards.add(new SetCardInfo("Priest of the Blood Rite", 112, Rarity.RARE, mage.cards.p.PriestOfTheBloodRite.class));
        cards.add(new SetCardInfo("Prism Ring", 235, Rarity.UNCOMMON, mage.cards.p.PrismRing.class));
        cards.add(new SetCardInfo("Prized Unicorn", 287, Rarity.UNCOMMON, mage.cards.p.PrizedUnicorn.class));
        cards.add(new SetCardInfo("Psychic Rebuttal", 67, Rarity.UNCOMMON, mage.cards.p.PsychicRebuttal.class));
        cards.add(new SetCardInfo("Pyromancer's Goggles", 236, Rarity.MYTHIC, mage.cards.p.PyromancersGoggles.class));
        cards.add(new SetCardInfo("Rabid Bloodsucker", 113, Rarity.COMMON, mage.cards.r.RabidBloodsucker.class));
        cards.add(new SetCardInfo("Ramroller", 237, Rarity.UNCOMMON, mage.cards.r.Ramroller.class));
        cards.add(new SetCardInfo("Ravaging Blaze", 159, Rarity.UNCOMMON, mage.cards.r.RavagingBlaze.class));
        cards.add(new SetCardInfo("Read the Bones", 114, Rarity.COMMON, mage.cards.r.ReadTheBones.class));
        cards.add(new SetCardInfo("Reave Soul", 115, Rarity.COMMON, mage.cards.r.ReaveSoul.class));
        cards.add(new SetCardInfo("Reclaim", 195, Rarity.COMMON, mage.cards.r.Reclaim.class));
        cards.add(new SetCardInfo("Reclusive Artificer", 216, Rarity.UNCOMMON, mage.cards.r.ReclusiveArtificer.class));
        cards.add(new SetCardInfo("Relic Seeker", 29, Rarity.RARE, mage.cards.r.RelicSeeker.class));
        cards.add(new SetCardInfo("Returned Centaur", 116, Rarity.COMMON, mage.cards.r.ReturnedCentaur.class));
        cards.add(new SetCardInfo("Revenant", 117, Rarity.UNCOMMON, mage.cards.r.Revenant.class));
        cards.add(new SetCardInfo("Rhox Maulers", 196, Rarity.COMMON, mage.cards.r.RhoxMaulers.class));
        cards.add(new SetCardInfo("Ringwarden Owl", 68, Rarity.COMMON, mage.cards.r.RingwardenOwl.class));
        cards.add(new SetCardInfo("Rogue's Passage", 250, Rarity.UNCOMMON, mage.cards.r.RoguesPassage.class));
        cards.add(new SetCardInfo("Runed Servitor", 238, Rarity.UNCOMMON, mage.cards.r.RunedServitor.class));
        cards.add(new SetCardInfo("Scab-Clan Berserker", 160, Rarity.RARE, mage.cards.s.ScabClanBerserker.class));
        cards.add(new SetCardInfo("Scrapskin Drake", 69, Rarity.COMMON, mage.cards.s.ScrapskinDrake.class));
        cards.add(new SetCardInfo("Screeching Skaab", 70, Rarity.COMMON, mage.cards.s.ScreechingSkaab.class));
        cards.add(new SetCardInfo("Seismic Elemental", 161, Rarity.UNCOMMON, mage.cards.s.SeismicElemental.class));
        cards.add(new SetCardInfo("Send to Sleep", 71, Rarity.COMMON, mage.cards.s.SendToSleep.class));
        cards.add(new SetCardInfo("Sengir Vampire", 283, Rarity.UNCOMMON, mage.cards.s.SengirVampire.class));
        cards.add(new SetCardInfo("Sentinel of the Eternal Watch", 30, Rarity.UNCOMMON, mage.cards.s.SentinelOfTheEternalWatch.class));
        cards.add(new SetCardInfo("Separatist Voidmage", 72, Rarity.COMMON, mage.cards.s.SeparatistVoidmage.class));
        cards.add(new SetCardInfo("Serra Angel", 276, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Shadows of the Past", 118, Rarity.UNCOMMON, mage.cards.s.ShadowsOfThePast.class));
        cards.add(new SetCardInfo("Shaman of the Pack", 217, Rarity.UNCOMMON, mage.cards.s.ShamanOfThePack.class));
        cards.add(new SetCardInfo("Shambling Ghoul", 119, Rarity.COMMON, mage.cards.s.ShamblingGhoul.class));
        cards.add(new SetCardInfo("Shivan Dragon", 285, Rarity.RARE, mage.cards.s.ShivanDragon.class));
        cards.add(new SetCardInfo("Shivan Reef", 251, Rarity.RARE, mage.cards.s.ShivanReef.class));
        cards.add(new SetCardInfo("Sigiled Starfish", 73, Rarity.UNCOMMON, mage.cards.s.SigiledStarfish.class));
        cards.add(new SetCardInfo("Sigil of the Empty Throne", 31, Rarity.RARE, mage.cards.s.SigilOfTheEmptyThrone.class));
        cards.add(new SetCardInfo("Sigil of Valor", 239, Rarity.UNCOMMON, mage.cards.s.SigilOfValor.class));
        cards.add(new SetCardInfo("Skaab Goliath", 74, Rarity.UNCOMMON, mage.cards.s.SkaabGoliath.class));
        cards.add(new SetCardInfo("Skyraker Giant", 162, Rarity.UNCOMMON, mage.cards.s.SkyrakerGiant.class));
        cards.add(new SetCardInfo("Skysnare Spider", 197, Rarity.UNCOMMON, mage.cards.s.SkysnareSpider.class));
        cards.add(new SetCardInfo("Smash to Smithereens", 163, Rarity.COMMON, mage.cards.s.SmashToSmithereens.class));
        cards.add(new SetCardInfo("Somberwald Alpha", 198, Rarity.UNCOMMON, mage.cards.s.SomberwaldAlpha.class));
        cards.add(new SetCardInfo("Soulblade Djinn", 75, Rarity.RARE, mage.cards.s.SoulbladeDjinn.class));
        cards.add(new SetCardInfo("Sphinx's Tutelage", 76, Rarity.UNCOMMON, mage.cards.s.SphinxsTutelage.class));
        cards.add(new SetCardInfo("Stalwart Aven", 32, Rarity.COMMON, mage.cards.s.StalwartAven.class));
        cards.add(new SetCardInfo("Starfield of Nyx", 33, Rarity.MYTHIC, mage.cards.s.StarfieldOfNyx.class));
        cards.add(new SetCardInfo("Stratus Walk", 77, Rarity.COMMON, mage.cards.s.StratusWalk.class));
        cards.add(new SetCardInfo("Subterranean Scout", 164, Rarity.COMMON, mage.cards.s.SubterraneanScout.class));
        cards.add(new SetCardInfo("Suppression Bonds", 34, Rarity.COMMON, mage.cards.s.SuppressionBonds.class));
        cards.add(new SetCardInfo("Swamp", 261, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 262, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 263, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 264, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swift Reckoning", 35, Rarity.UNCOMMON, mage.cards.s.SwiftReckoning.class));
        cards.add(new SetCardInfo("Sword of the Animist", 240, Rarity.RARE, mage.cards.s.SwordOfTheAnimist.class));
        cards.add(new SetCardInfo("Sylvan Messenger", 199, Rarity.UNCOMMON, mage.cards.s.SylvanMessenger.class));
        cards.add(new SetCardInfo("Tainted Remedy", 120, Rarity.RARE, mage.cards.t.TaintedRemedy.class));
        cards.add(new SetCardInfo("Talent of the Telepath", 78, Rarity.RARE, mage.cards.t.TalentOfTheTelepath.class));
        cards.add(new SetCardInfo("Terra Stomper", 288, Rarity.RARE, mage.cards.t.TerraStomper.class));
        cards.add(new SetCardInfo("The Great Aurora", 179, Rarity.MYTHIC, mage.cards.t.TheGreatAurora.class));
        cards.add(new SetCardInfo("Thopter Engineer", 165, Rarity.UNCOMMON, mage.cards.t.ThopterEngineer.class));
        cards.add(new SetCardInfo("Thopter Spy Network", 79, Rarity.RARE, mage.cards.t.ThopterSpyNetwork.class));
        cards.add(new SetCardInfo("Thornbow Archer", 121, Rarity.COMMON, mage.cards.t.ThornbowArcher.class));
        cards.add(new SetCardInfo("Throwing Knife", 241, Rarity.UNCOMMON, mage.cards.t.ThrowingKnife.class));
        cards.add(new SetCardInfo("Thunderclap Wyvern", 218, Rarity.UNCOMMON, mage.cards.t.ThunderclapWyvern.class));
        cards.add(new SetCardInfo("Timberpack Wolf", 200, Rarity.COMMON, mage.cards.t.TimberpackWolf.class));
        cards.add(new SetCardInfo("Titanic Growth", 201, Rarity.COMMON, mage.cards.t.TitanicGrowth.class));
        cards.add(new SetCardInfo("Titan's Strength", 166, Rarity.COMMON, mage.cards.t.TitansStrength.class));
        cards.add(new SetCardInfo("Topan Freeblade", 36, Rarity.COMMON, mage.cards.t.TopanFreeblade.class));
        cards.add(new SetCardInfo("Tormented Thoughts", 122, Rarity.UNCOMMON, mage.cards.t.TormentedThoughts.class));
        cards.add(new SetCardInfo("Totem-Guide Hartebeest", 37, Rarity.UNCOMMON, mage.cards.t.TotemGuideHartebeest.class));
        cards.add(new SetCardInfo("Touch of Moonglove", 123, Rarity.COMMON, mage.cards.t.TouchOfMoonglove.class));
        cards.add(new SetCardInfo("Tower Geist", 80, Rarity.UNCOMMON, mage.cards.t.TowerGeist.class));
        cards.add(new SetCardInfo("Tragic Arrogance", 38, Rarity.RARE, mage.cards.t.TragicArrogance.class));
        cards.add(new SetCardInfo("Turn to Frog", 81, Rarity.UNCOMMON, mage.cards.t.TurnToFrog.class));
        cards.add(new SetCardInfo("Undead Servant", 124, Rarity.COMMON, mage.cards.u.UndeadServant.class));
        cards.add(new SetCardInfo("Undercity Troll", 202, Rarity.UNCOMMON, mage.cards.u.UndercityTroll.class));
        cards.add(new SetCardInfo("Unholy Hunger", 125, Rarity.COMMON, mage.cards.u.UnholyHunger.class));
        cards.add(new SetCardInfo("Valeron Wardens", 203, Rarity.UNCOMMON, mage.cards.v.ValeronWardens.class));
        cards.add(new SetCardInfo("Valor in Akros", 39, Rarity.UNCOMMON, mage.cards.v.ValorInAkros.class));
        cards.add(new SetCardInfo("Vastwood Gorger", 204, Rarity.COMMON, mage.cards.v.VastwoodGorger.class));
        cards.add(new SetCardInfo("Veteran's Sidearm", 242, Rarity.COMMON, mage.cards.v.VeteransSidearm.class));
        cards.add(new SetCardInfo("Vine Snare", 205, Rarity.COMMON, mage.cards.v.VineSnare.class));
        cards.add(new SetCardInfo("Volcanic Rambler", 167, Rarity.COMMON, mage.cards.v.VolcanicRambler.class));
        cards.add(new SetCardInfo("Vryn Wingmare", 40, Rarity.RARE, mage.cards.v.VrynWingmare.class));
        cards.add(new SetCardInfo("War Horn", 243, Rarity.UNCOMMON, mage.cards.w.WarHorn.class));
        cards.add(new SetCardInfo("War Oracle", 41, Rarity.UNCOMMON, mage.cards.w.WarOracle.class));
        cards.add(new SetCardInfo("Watercourser", 82, Rarity.COMMON, mage.cards.w.Watercourser.class));
        cards.add(new SetCardInfo("Weave Fate", 279, Rarity.COMMON, mage.cards.w.WeaveFate.class));
        cards.add(new SetCardInfo("Weight of the Underworld", 126, Rarity.COMMON, mage.cards.w.WeightOfTheUnderworld.class));
        cards.add(new SetCardInfo("Whirler Rogue", 83, Rarity.UNCOMMON, mage.cards.w.WhirlerRogue.class));
        cards.add(new SetCardInfo("Wild Instincts", 206, Rarity.COMMON, mage.cards.w.WildInstincts.class));
        cards.add(new SetCardInfo("Willbreaker", 84, Rarity.RARE, mage.cards.w.Willbreaker.class));
        cards.add(new SetCardInfo("Woodland Bellower", 207, Rarity.MYTHIC, mage.cards.w.WoodlandBellower.class));
        cards.add(new SetCardInfo("Yavimaya Coast", 252, Rarity.RARE, mage.cards.y.YavimayaCoast.class));
        cards.add(new SetCardInfo("Yeva's Forcemage", 208, Rarity.COMMON, mage.cards.y.YevasForcemage.class));
        cards.add(new SetCardInfo("Yoked Ox", 42, Rarity.COMMON, mage.cards.y.YokedOx.class));
        cards.add(new SetCardInfo("Zendikar Incarnate", 219, Rarity.UNCOMMON, mage.cards.z.ZendikarIncarnate.class));
        cards.add(new SetCardInfo("Zendikar's Roil", 209, Rarity.UNCOMMON, mage.cards.z.ZendikarsRoil.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new MagicOriginsCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/ori.html
// Using USA collation for common/uncommon, rare collation inferred from other sets
class MagicOriginsCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "138", "13", "126", "130", "9", "86", "164", "5", "123", "129", "26", "107", "167", "2", "97", "163", "17", "102", "140", "42", "91", "138", "6", "111", "139", "5", "126", "152", "13", "95", "154", "15", "123", "164", "1", "89", "136", "9", "107", "129", "8", "121", "167", "26", "102", "154", "17", "91", "163", "2", "97", "130", "42", "86", "139", "6", "95", "136", "15", "111", "140", "8", "89", "152", "1", "121");
    private final CardRun commonB = new CardRun(true, "192", "71", "188", "82", "168", "47", "208", "63", "181", "65", "204", "45", "200", "77", "194", "82", "175", "66", "188", "70", "201", "71", "170", "46", "208", "65", "194", "54", "181", "47", "168", "63", "200", "77", "201", "66", "192", "46", "175", "45", "188", "70", "204", "54", "170", "71", "208", "82", "192", "65", "168", "63", "181", "66", "200", "47", "194", "70", "175", "77", "204", "45", "170", "46", "201", "54");
    private final CardRun commonC1 = new CardRun(true, "115", "56", "196", "25", "246", "166", "114", "57", "190", "12", "149", "113", "72", "195", "34", "242", "146", "124", "69", "205", "10", "153", "119", "48", "196", "18", "223", "158", "115", "57", "185", "25", "149", "124", "56", "190", "34", "242", "166", "114", "48", "185", "12", "146", "113", "69", "195", "10", "223", "153", "119", "72", "205", "18", "158");
    private final CardRun commonC2 = new CardRun(true, "206", "227", "50", "132", "32", "116", "68", "187", "133", "220", "125", "36", "50", "228", "145", "20", "109", "52", "206", "246", "32", "227", "184", "220", "68", "132", "36", "116", "50", "187", "133", "228", "125", "187", "32", "227", "145", "20", "125", "52", "206", "220", "109", "132", "184", "36", "68", "133", "20", "109", "52", "184", "228", "145", "116");
    private final CardRun uncommonA = new CardRun(true, "219", "235", "148", "183", "155", "224", "88", "214", "67", "161", "239", "249", "197", "243", "35", "76", "83", "122", "159", "27", "174", "183", "219", "235", "249", "28", "148", "239", "88", "214", "224", "161", "67", "174", "197", "76", "35", "159", "155", "243", "183", "122", "27", "83", "219", "235", "28", "148", "88", "239", "67", "214", "83", "224", "76", "161", "249", "159", "35", "155", "28", "174", "243", "197", "27", "122");
    private final CardRun uncommonB = new CardRun(true, "225", "128", "199", "37", "210", "118", "74", "134", "87", "150", "49", "171", "59", "110", "213", "211", "222", "238", "225", "37", "210", "118", "128", "39", "171", "3", "213", "74", "178", "150", "233", "222", "134", "49", "59", "199", "87", "211", "39", "3", "37", "225", "110", "178", "134", "150", "199", "238", "118", "74", "211", "49", "171", "87", "233", "210", "128", "110", "213", "59", "39", "238", "178", "3", "233", "222");
    private final CardRun uncommonC = new CardRun(true, "247", "162", "81", "241", "96", "218", "41", "198", "209", "11", "22", "216", "226", "80", "218", "100", "98", "247", "162", "212", "11", "209", "241", "216", "144", "22", "96", "81", "41", "80", "198", "144", "162", "241", "226", "212", "209", "98", "41", "81", "100", "218", "96", "247", "198", "11", "22", "216", "212", "80", "226", "98", "100", "144");
    private final CardRun uncommonD = new CardRun(true, "250", "117", "217", "202", "44", "237", "165", "85", "231", "108", "203", "7", "173", "73", "117", "62", "142", "165", "85", "203", "202", "30", "237", "215", "250", "173", "217", "108", "44", "7", "142", "30", "231", "73", "202", "62", "117", "165", "215", "44", "85", "217", "237", "250", "7", "203", "30", "231", "73", "62", "173", "215", "142", "108");
    private final CardRun rare = new CardRun(false, "14", "16", "19", "21", "24", "29", "31", "38", "40", "43", "55", "58", "61", "64", "75", "78", "79", "84", "90", "93", "99", "101", "103", "104", "105", "112", "120", "127", "137", "141", "143", "147", "151", "156", "157", "160", "169", "172", "176", "177", "180", "182", "186", "191", "193", "229", "230", "232", "234", "240", "244", "245", "248", "251", "252", "14", "16", "19", "21", "24", "29", "31", "38", "40", "43", "55", "58", "61", "64", "75", "78", "79", "84", "90", "93", "99", "101", "103", "104", "105", "112", "120", "127", "137", "141", "143", "147", "151", "156", "157", "160", "169", "172", "176", "177", "180", "182", "186", "191", "193", "229", "230", "232", "234", "240", "244", "245", "248", "251", "252", "4", "23", "33", "51", "53", "60", "92", "94", "106", "131", "135", "179", "189", "207", "221", "236");
    private final CardRun land = new CardRun(false, "253", "254", "255", "256", "257", "258", "259", "260", "261", "262", "263", "264", "265", "266", "267", "268", "269", "270", "271", "272");

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
    private final BoosterStructure ABC = new BoosterStructure(uncommonA, uncommonB, uncommonC);
    private final BoosterStructure ABD = new BoosterStructure(uncommonA, uncommonB, uncommonD);
    private final BoosterStructure ACD = new BoosterStructure(uncommonA, uncommonC, uncommonD);
    private final BoosterStructure BCD = new BoosterStructure(uncommonB, uncommonC, uncommonD);
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
    // In order for equal numbers of each uncommon to exist, the average booster must contain:
    // 0.825 A uncommons (33 / 40)
    // 0.825 B uncommons (33 / 40)
    // 0.675 C uncommons (27 / 40)
    // 0.675 D uncommons (27 / 40)
    // These numbers are the same for all sets with 80 uncommons in asymmetrical A/B/C/D print runs
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            ABC, ABD, ABC, ABD, ABC, ABD, ABC, ABD, ABC, ABD, ABC, ABD, ABC,
            ABD, ABC, ABD, ABC, ABD, ABC, ABD, ABC, ABD, ABC, ABD, ABC, ABD,
            ACD, BCD, ACD, BCD, ACD, BCD, ACD,
            BCD, ACD, BCD, ACD, BCD, ACD, BCD
    );
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
