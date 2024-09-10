package mage.sets;

import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardRepository;
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
 * @author nantuko84
 */
public final class MirrodinBesieged extends ExpansionSet {

    private static final MirrodinBesieged instance = new MirrodinBesieged();

    public static MirrodinBesieged getInstance() {
        return instance;
    }

    private MirrodinBesieged() {
        super("Mirrodin Besieged", "MBS", ExpansionSet.buildDate(2011, 1, 4), SetType.EXPANSION);
        this.blockName = "Scars of Mirrodin";
        this.parentSet = ScarsOfMirrodin.getInstance();
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Accorder Paladin", 1, Rarity.UNCOMMON, mage.cards.a.AccorderPaladin.class));
        cards.add(new SetCardInfo("Ardent Recruit", 2, Rarity.COMMON, mage.cards.a.ArdentRecruit.class));
        cards.add(new SetCardInfo("Banishment Decree", 3, Rarity.COMMON, mage.cards.b.BanishmentDecree.class));
        cards.add(new SetCardInfo("Black Sun's Zenith", 39, Rarity.RARE, mage.cards.b.BlackSunsZenith.class));
        cards.add(new SetCardInfo("Bladed Sentinel", 98, Rarity.COMMON, mage.cards.b.BladedSentinel.class));
        cards.add(new SetCardInfo("Blightsteel Colossus", 99, Rarity.MYTHIC, mage.cards.b.BlightsteelColossus.class));
        cards.add(new SetCardInfo("Blightwidow", 77, Rarity.COMMON, mage.cards.b.Blightwidow.class));
        cards.add(new SetCardInfo("Blisterstick Shaman", 58, Rarity.COMMON, mage.cards.b.BlisterstickShaman.class));
        cards.add(new SetCardInfo("Blue Sun's Zenith", 20, Rarity.RARE, mage.cards.b.BlueSunsZenith.class));
        cards.add(new SetCardInfo("Bonehoard", 100, Rarity.RARE, mage.cards.b.Bonehoard.class));
        cards.add(new SetCardInfo("Brass Squire", 101, Rarity.UNCOMMON, mage.cards.b.BrassSquire.class));
        cards.add(new SetCardInfo("Burn the Impure", 59, Rarity.COMMON, mage.cards.b.BurnTheImpure.class));
        cards.add(new SetCardInfo("Caustic Hound", 40, Rarity.COMMON, mage.cards.c.CausticHound.class));
        cards.add(new SetCardInfo("Choking Fumes", 4, Rarity.UNCOMMON, mage.cards.c.ChokingFumes.class));
        cards.add(new SetCardInfo("Concussive Bolt", 60, Rarity.COMMON, mage.cards.c.ConcussiveBolt.class));
        cards.add(new SetCardInfo("Consecrated Sphinx", 21, Rarity.MYTHIC, mage.cards.c.ConsecratedSphinx.class));
        cards.add(new SetCardInfo("Contested War Zone", 144, Rarity.RARE, mage.cards.c.ContestedWarZone.class));
        cards.add(new SetCardInfo("Copper Carapace", 102, Rarity.COMMON, mage.cards.c.CopperCarapace.class));
        cards.add(new SetCardInfo("Core Prowler", 103, Rarity.UNCOMMON, mage.cards.c.CoreProwler.class));
        cards.add(new SetCardInfo("Corrupted Conscience", 22, Rarity.UNCOMMON, mage.cards.c.CorruptedConscience.class));
        cards.add(new SetCardInfo("Creeping Corrosion", 78, Rarity.RARE, mage.cards.c.CreepingCorrosion.class));
        cards.add(new SetCardInfo("Crush", 61, Rarity.COMMON, mage.cards.c.Crush.class));
        cards.add(new SetCardInfo("Cryptoplasm", 23, Rarity.RARE, mage.cards.c.Cryptoplasm.class));
        cards.add(new SetCardInfo("Darksteel Plate", 104, Rarity.RARE, mage.cards.d.DarksteelPlate.class));
        cards.add(new SetCardInfo("Decimator Web", 105, Rarity.RARE, mage.cards.d.DecimatorWeb.class));
        cards.add(new SetCardInfo("Distant Memories", 24, Rarity.RARE, mage.cards.d.DistantMemories.class));
        cards.add(new SetCardInfo("Divine Offering", 5, Rarity.COMMON, mage.cards.d.DivineOffering.class));
        cards.add(new SetCardInfo("Dross Ripper", 106, Rarity.COMMON, mage.cards.d.DrossRipper.class));
        cards.add(new SetCardInfo("Fangren Marauder", 79, Rarity.COMMON, mage.cards.f.FangrenMarauder.class));
        cards.add(new SetCardInfo("Flayer Husk", 107, Rarity.COMMON, mage.cards.f.FlayerHusk.class));
        cards.add(new SetCardInfo("Flensermite", 41, Rarity.COMMON, mage.cards.f.Flensermite.class));
        cards.add(new SetCardInfo("Flesh-Eater Imp", 42, Rarity.UNCOMMON, mage.cards.f.FleshEaterImp.class));
        cards.add(new SetCardInfo("Forest", 154, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 155, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frantic Salvage", 6, Rarity.COMMON, mage.cards.f.FranticSalvage.class));
        cards.add(new SetCardInfo("Fuel for the Cause", 25, Rarity.COMMON, mage.cards.f.FuelForTheCause.class));
        cards.add(new SetCardInfo("Galvanoth", 62, Rarity.RARE, mage.cards.g.Galvanoth.class));
        cards.add(new SetCardInfo("Glissa's Courier", 80, Rarity.COMMON, mage.cards.g.GlissasCourier.class));
        cards.add(new SetCardInfo("Glissa, the Traitor", 96, Rarity.MYTHIC, mage.cards.g.GlissaTheTraitor.class));
        cards.add(new SetCardInfo("Gnathosaur", 63, Rarity.COMMON, mage.cards.g.Gnathosaur.class));
        cards.add(new SetCardInfo("Goblin Wardriver", 64, Rarity.UNCOMMON, mage.cards.g.GoblinWardriver.class));
        cards.add(new SetCardInfo("Go for the Throat", 43, Rarity.UNCOMMON, mage.cards.g.GoForTheThroat.class));
        cards.add(new SetCardInfo("Gore Vassal", 7, Rarity.UNCOMMON, mage.cards.g.GoreVassal.class));
        cards.add(new SetCardInfo("Green Sun's Zenith", 81, Rarity.RARE, mage.cards.g.GreenSunsZenith.class));
        cards.add(new SetCardInfo("Gruesome Encore", 44, Rarity.UNCOMMON, mage.cards.g.GruesomeEncore.class));
        cards.add(new SetCardInfo("Gust-Skimmer", 108, Rarity.COMMON, mage.cards.g.GustSkimmer.class));
        cards.add(new SetCardInfo("Hellkite Igniter", 65, Rarity.RARE, mage.cards.h.HellkiteIgniter.class));
        cards.add(new SetCardInfo("Hero of Bladehold", 8, Rarity.MYTHIC, mage.cards.h.HeroOfBladehold.class));
        cards.add(new SetCardInfo("Hero of Oxid Ridge", 66, Rarity.MYTHIC, mage.cards.h.HeroOfOxidRidge.class));
        cards.add(new SetCardInfo("Hexplate Golem", 109, Rarity.COMMON, mage.cards.h.HexplateGolem.class));
        cards.add(new SetCardInfo("Horrifying Revelation", 45, Rarity.COMMON, mage.cards.h.HorrifyingRevelation.class));
        cards.add(new SetCardInfo("Ichor Wellspring", 110, Rarity.COMMON, mage.cards.i.IchorWellspring.class));
        cards.add(new SetCardInfo("Inkmoth Nexus", 145, Rarity.RARE, mage.cards.i.InkmothNexus.class));
        cards.add(new SetCardInfo("Into the Core", 67, Rarity.UNCOMMON, mage.cards.i.IntoTheCore.class));
        cards.add(new SetCardInfo("Island", 148, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 149, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kemba's Legion", 9, Rarity.UNCOMMON, mage.cards.k.KembasLegion.class));
        cards.add(new SetCardInfo("Knowledge Pool", 111, Rarity.RARE, mage.cards.k.KnowledgePool.class));
        cards.add(new SetCardInfo("Koth's Courier", 68, Rarity.COMMON, mage.cards.k.KothsCourier.class));
        cards.add(new SetCardInfo("Kuldotha Flamefiend", 69, Rarity.UNCOMMON, mage.cards.k.KuldothaFlamefiend.class));
        cards.add(new SetCardInfo("Kuldotha Ringleader", 70, Rarity.COMMON, mage.cards.k.KuldothaRingleader.class));
        cards.add(new SetCardInfo("Lead the Stampede", 82, Rarity.UNCOMMON, mage.cards.l.LeadTheStampede.class));
        cards.add(new SetCardInfo("Leonin Relic-Warder", 10, Rarity.UNCOMMON, mage.cards.l.LeoninRelicWarder.class));
        cards.add(new SetCardInfo("Leonin Skyhunter", 11, Rarity.COMMON, mage.cards.l.LeoninSkyhunter.class));
        cards.add(new SetCardInfo("Loxodon Partisan", 12, Rarity.COMMON, mage.cards.l.LoxodonPartisan.class));
        cards.add(new SetCardInfo("Lumengrid Gargoyle", 112, Rarity.UNCOMMON, mage.cards.l.LumengridGargoyle.class));
        cards.add(new SetCardInfo("Magnetic Mine", 113, Rarity.RARE, mage.cards.m.MagneticMine.class));
        cards.add(new SetCardInfo("Massacre Wurm", 46, Rarity.MYTHIC, mage.cards.m.MassacreWurm.class));
        cards.add(new SetCardInfo("Master's Call", 13, Rarity.COMMON, mage.cards.m.MastersCall.class));
        cards.add(new SetCardInfo("Melira's Keepers", 83, Rarity.UNCOMMON, mage.cards.m.MelirasKeepers.class));
        cards.add(new SetCardInfo("Metallic Mastery", 71, Rarity.UNCOMMON, mage.cards.m.MetallicMastery.class));
        cards.add(new SetCardInfo("Mirran Crusader", 14, Rarity.RARE, mage.cards.m.MirranCrusader.class));
        cards.add(new SetCardInfo("Mirran Mettle", 84, Rarity.COMMON, mage.cards.m.MirranMettle.class));
        cards.add(new SetCardInfo("Mirran Spy", 26, Rarity.COMMON, mage.cards.m.MirranSpy.class));
        cards.add(new SetCardInfo("Mirrorworks", 114, Rarity.RARE, mage.cards.m.Mirrorworks.class));
        cards.add(new SetCardInfo("Mitotic Manipulation", 27, Rarity.RARE, mage.cards.m.MitoticManipulation.class));
        cards.add(new SetCardInfo("Morbid Plunder", 47, Rarity.COMMON, mage.cards.m.MorbidPlunder.class));
        cards.add(new SetCardInfo("Mortarpod", 115, Rarity.UNCOMMON, mage.cards.m.Mortarpod.class));
        cards.add(new SetCardInfo("Mountain", 152, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 153, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Myr Sire", 116, Rarity.COMMON, mage.cards.m.MyrSire.class));
        cards.add(new SetCardInfo("Myr Turbine", 117, Rarity.RARE, mage.cards.m.MyrTurbine.class));
        cards.add(new SetCardInfo("Myr Welder", 118, Rarity.RARE, mage.cards.m.MyrWelder.class));
        cards.add(new SetCardInfo("Nested Ghoul", 48, Rarity.UNCOMMON, mage.cards.n.NestedGhoul.class));
        cards.add(new SetCardInfo("Neurok Commando", 28, Rarity.UNCOMMON, mage.cards.n.NeurokCommando.class));
        cards.add(new SetCardInfo("Oculus", 29, Rarity.COMMON, mage.cards.o.Oculus.class));
        cards.add(new SetCardInfo("Ogre Resister", 72, Rarity.COMMON, mage.cards.o.OgreResister.class));
        cards.add(new SetCardInfo("Peace Strider", 119, Rarity.UNCOMMON, mage.cards.p.PeaceStrider.class));
        cards.add(new SetCardInfo("Phyresis", 49, Rarity.COMMON, mage.cards.p.Phyresis.class));
        cards.add(new SetCardInfo("Phyrexian Crusader", 50, Rarity.RARE, mage.cards.p.PhyrexianCrusader.class));
        cards.add(new SetCardInfo("Phyrexian Digester", 120, Rarity.COMMON, mage.cards.p.PhyrexianDigester.class));
        cards.add(new SetCardInfo("Phyrexian Hydra", 85, Rarity.RARE, mage.cards.p.PhyrexianHydra.class));
        cards.add(new SetCardInfo("Phyrexian Juggernaut", 121, Rarity.UNCOMMON, mage.cards.p.PhyrexianJuggernaut.class));
        cards.add(new SetCardInfo("Phyrexian Rager", 51, Rarity.COMMON, mage.cards.p.PhyrexianRager.class));
        cards.add(new SetCardInfo("Phyrexian Rebirth", 15, Rarity.RARE, mage.cards.p.PhyrexianRebirth.class));
        cards.add(new SetCardInfo("Phyrexian Revoker", 122, Rarity.RARE, mage.cards.p.PhyrexianRevoker.class));
        cards.add(new SetCardInfo("Phyrexian Vatmother", 52, Rarity.RARE, mage.cards.p.PhyrexianVatmother.class));
        cards.add(new SetCardInfo("Pierce Strider", 123, Rarity.UNCOMMON, mage.cards.p.PierceStrider.class));
        cards.add(new SetCardInfo("Piston Sledge", 124, Rarity.UNCOMMON, mage.cards.p.PistonSledge.class));
        cards.add(new SetCardInfo("Pistus Strike", 86, Rarity.COMMON, mage.cards.p.PistusStrike.class));
        cards.add(new SetCardInfo("Plaguemaw Beast", 87, Rarity.UNCOMMON, mage.cards.p.PlaguemawBeast.class));
        cards.add(new SetCardInfo("Plague Myr", 125, Rarity.UNCOMMON, mage.cards.p.PlagueMyr.class));
        cards.add(new SetCardInfo("Plains", 146, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 147, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Praetor's Counsel", 88, Rarity.MYTHIC, mage.cards.p.PraetorsCounsel.class));
        cards.add(new SetCardInfo("Priests of Norn", 16, Rarity.COMMON, mage.cards.p.PriestsOfNorn.class));
        cards.add(new SetCardInfo("Psychosis Crawler", 126, Rarity.RARE, mage.cards.p.PsychosisCrawler.class));
        cards.add(new SetCardInfo("Quicksilver Geyser", 30, Rarity.COMMON, mage.cards.q.QuicksilverGeyser.class));
        cards.add(new SetCardInfo("Quilled Slagwurm", 89, Rarity.UNCOMMON, mage.cards.q.QuilledSlagwurm.class));
        cards.add(new SetCardInfo("Rally the Forces", 73, Rarity.COMMON, mage.cards.r.RallyTheForces.class));
        cards.add(new SetCardInfo("Razorfield Rhino", 127, Rarity.COMMON, mage.cards.r.RazorfieldRhino.class));
        cards.add(new SetCardInfo("Red Sun's Zenith", 74, Rarity.RARE, mage.cards.r.RedSunsZenith.class));
        cards.add(new SetCardInfo("Rot Wolf", 90, Rarity.COMMON, mage.cards.r.RotWolf.class));
        cards.add(new SetCardInfo("Rusted Slasher", 128, Rarity.COMMON, mage.cards.r.RustedSlasher.class));
        cards.add(new SetCardInfo("Sangromancer", 53, Rarity.RARE, mage.cards.s.Sangromancer.class));
        cards.add(new SetCardInfo("Scourge Servant", 54, Rarity.COMMON, mage.cards.s.ScourgeServant.class));
        cards.add(new SetCardInfo("Septic Rats", 55, Rarity.UNCOMMON, mage.cards.s.SepticRats.class));
        cards.add(new SetCardInfo("Serum Raker", 31, Rarity.COMMON, mage.cards.s.SerumRaker.class));
        cards.add(new SetCardInfo("Shimmer Myr", 129, Rarity.RARE, mage.cards.s.ShimmerMyr.class));
        cards.add(new SetCardInfo("Shriekhorn", 130, Rarity.COMMON, mage.cards.s.Shriekhorn.class));
        cards.add(new SetCardInfo("Signal Pest", 131, Rarity.UNCOMMON, mage.cards.s.SignalPest.class));
        cards.add(new SetCardInfo("Silverskin Armor", 132, Rarity.UNCOMMON, mage.cards.s.SilverskinArmor.class));
        cards.add(new SetCardInfo("Skinwing", 133, Rarity.UNCOMMON, mage.cards.s.Skinwing.class));
        cards.add(new SetCardInfo("Slagstorm", 75, Rarity.RARE, mage.cards.s.Slagstorm.class));
        cards.add(new SetCardInfo("Sphere of the Suns", 134, Rarity.UNCOMMON, mage.cards.s.SphereOfTheSuns.class));
        cards.add(new SetCardInfo("Spin Engine", 135, Rarity.COMMON, mage.cards.s.SpinEngine.class));
        cards.add(new SetCardInfo("Spine of Ish Sah", 136, Rarity.RARE, mage.cards.s.SpineOfIshSah.class));
        cards.add(new SetCardInfo("Spiraling Duelist", 76, Rarity.UNCOMMON, mage.cards.s.SpiralingDuelist.class));
        cards.add(new SetCardInfo("Spire Serpent", 32, Rarity.COMMON, mage.cards.s.SpireSerpent.class));
        cards.add(new SetCardInfo("Spread the Sickness", 56, Rarity.COMMON, mage.cards.s.SpreadTheSickness.class));
        cards.add(new SetCardInfo("Steel Sabotage", 33, Rarity.COMMON, mage.cards.s.SteelSabotage.class));
        cards.add(new SetCardInfo("Strandwalker", 137, Rarity.UNCOMMON, mage.cards.s.Strandwalker.class));
        cards.add(new SetCardInfo("Swamp", 150, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 151, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sword of Feast and Famine", 138, Rarity.MYTHIC, mage.cards.s.SwordOfFeastAndFamine.class));
        cards.add(new SetCardInfo("Tangle Hulk", 139, Rarity.COMMON, mage.cards.t.TangleHulk.class));
        cards.add(new SetCardInfo("Tangle Mantis", 91, Rarity.COMMON, mage.cards.t.TangleMantis.class));
        cards.add(new SetCardInfo("Tezzeret, Agent of Bolas", 97, Rarity.MYTHIC, mage.cards.t.TezzeretAgentOfBolas.class));
        cards.add(new SetCardInfo("Thopter Assembly", 140, Rarity.RARE, mage.cards.t.ThopterAssembly.class));
        cards.add(new SetCardInfo("Thrun, the Last Troll", 92, Rarity.MYTHIC, mage.cards.t.ThrunTheLastTroll.class));
        cards.add(new SetCardInfo("Tine Shrike", 17, Rarity.COMMON, mage.cards.t.TineShrike.class));
        cards.add(new SetCardInfo("Titan Forge", 141, Rarity.RARE, mage.cards.t.TitanForge.class));
        cards.add(new SetCardInfo("Training Drone", 142, Rarity.COMMON, mage.cards.t.TrainingDrone.class));
        cards.add(new SetCardInfo("Treasure Mage", 34, Rarity.UNCOMMON, mage.cards.t.TreasureMage.class));
        cards.add(new SetCardInfo("Turn the Tide", 35, Rarity.COMMON, mage.cards.t.TurnTheTide.class));
        cards.add(new SetCardInfo("Unnatural Predation", 93, Rarity.COMMON, mage.cards.u.UnnaturalPredation.class));
        cards.add(new SetCardInfo("Vedalken Anatomist", 36, Rarity.UNCOMMON, mage.cards.v.VedalkenAnatomist.class));
        cards.add(new SetCardInfo("Vedalken Infuser", 37, Rarity.UNCOMMON, mage.cards.v.VedalkenInfuser.class));
        cards.add(new SetCardInfo("Victory's Herald", 18, Rarity.RARE, mage.cards.v.VictorysHerald.class));
        cards.add(new SetCardInfo("Viridian Claw", 143, Rarity.UNCOMMON, mage.cards.v.ViridianClaw.class));
        cards.add(new SetCardInfo("Viridian Corrupter", 94, Rarity.UNCOMMON, mage.cards.v.ViridianCorrupter.class));
        cards.add(new SetCardInfo("Viridian Emissary", 95, Rarity.COMMON, mage.cards.v.ViridianEmissary.class));
        cards.add(new SetCardInfo("Virulent Wound", 57, Rarity.COMMON, mage.cards.v.VirulentWound.class));
        cards.add(new SetCardInfo("Vivisection", 38, Rarity.COMMON, mage.cards.v.Vivisection.class));
        cards.add(new SetCardInfo("White Sun's Zenith", 19, Rarity.RARE, mage.cards.w.WhiteSunsZenith.class));
    }

    // need to explicitly add SOM basics for collation since MBS has some basics itself
    @Override
    protected void generateBoosterMap() {
        super.generateBoosterMap();
        CardRepository
                .instance
                .findCards(new CardCriteria().setCodes("SOM").rarities(Rarity.LAND))
                .stream()
                .forEach(cardInfo -> inBoosterMap.put("SOM_" + cardInfo.getCardNumber(), cardInfo));
    }

    @Override
    public BoosterCollator createCollator() {
        return new MirrodinBesiegedCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/mbs.html
class MirrodinBesiegedCollator implements BoosterCollator {
    private final CardRun commonMirran = new CardRun(true, "13", "102", "79", "109", "2", "61", "35", "11", "98", "72", "135", "84", "108", "73", "13", "6", "68", "26", "58", "142", "91", "59", "109", "35", "102", "12", "60", "108", "6", "72", "70", "26", "142", "30", "135", "73", "91", "2", "60", "5", "108", "61", "58", "12", "30", "127", "79", "98", "32", "63", "13", "84", "59", "68", "102", "26", "2", "130", "5", "142", "79", "70", "6", "127", "59", "72", "91", "32", "13", "98", "35", "130", "61", "70", "11", "135", "68", "5", "84", "63", "26", "109", "60", "127", "35", "12", "98", "73", "58", "5", "61", "79", "32", "135", "63", "130", "30", "2", "102", "72", "11", "73", "6", "91", "127", "109", "63", "68", "30", "84", "142", "60", "70", "12", "59", "11", "130", "32", "108", "58");
    private final CardRun commonPhyrexian = new CardRun(true, "51", "45", "86", "128", "56", "38", "95", "139", "16", "120", "33", "80", "41", "57", "106", "25", "77", "51", "128", "3", "90", "107", "38", "49", "120", "54", "139", "25", "95", "17", "47", "80", "110", "31", "107", "41", "93", "40", "106", "16", "38", "86", "47", "116", "3", "77", "139", "29", "57", "80", "106", "45", "54", "93", "33", "128", "17", "86", "120", "56", "29", "77", "49", "110", "51", "40", "16", "25", "80", "107", "139", "33", "41", "95", "45", "56", "17", "40", "49", "116", "93", "16", "31", "57", "90", "25", "110", "54", "47", "77", "17", "116", "45", "33", "106", "49", "90", "29", "51", "107", "56", "95", "3", "40", "38", "116", "31", "86", "41", "47", "93", "128", "120", "57", "3", "54", "29", "110", "90", "31");
    private final CardRun uncommonA = new CardRun(true, "112", "64", "83", "10", "1", "34", "69", "119", "28", "143", "134", "112", "1", "28", "64", "34", "143", "83", "69", "134", "10", "119", "112", "10", "143", "119", "64", "134", "34", "69", "83", "28", "1", "112", "10", "69", "143", "64", "1", "119", "134", "83", "34", "28", "112", "69", "64", "119", "83", "28", "10", "143", "1", "134", "34", "112", "143", "119", "34", "10", "64", "134", "28", "69", "1", "83");
    private final CardRun uncommonB = new CardRun(true, "132", "101", "82", "131", "76", "37", "9", "43", "124", "82", "37", "132", "82", "76", "9", "124", "101", "131", "37", "43", "124", "101", "132", "76", "124", "131", "43", "82", "9", "101", "37", "76", "131", "132", "37", "101", "9", "82", "43", "131", "124", "76", "43", "132", "124", "9", "131", "132", "101", "9", "43", "82", "76", "37");
    private final CardRun uncommonC = new CardRun(true, "4", "48", "36", "125", "133", "94", "121", "87", "137", "67", "42", "4", "48", "87", "36", "133", "42", "125", "94", "137", "121", "67", "4", "87", "133", "125", "137", "67", "48", "36", "42", "94", "121", "4", "36", "125", "121", "48", "133", "94", "67", "87", "42", "137", "4", "133", "137", "48", "42", "121", "87", "125", "67", "36", "94", "4", "42", "67", "133", "121", "36", "137", "87", "94", "48", "125");
    private final CardRun uncommonD = new CardRun(true, "44", "55", "103", "7", "44", "71", "103", "123", "89", "115", "22", "44", "71", "89", "7", "115", "22", "103", "123", "55", "89", "22", "44", "89", "115", "103", "55", "71", "7", "22", "123", "55", "71", "44", "115", "55", "7", "123", "89", "103", "71", "22", "115", "7", "44", "22", "71", "103", "89", "123", "7", "55", "115", "123");

    // no point in collating the rares/mythics, just randomize with mythics in 1/8 packs
    private final CardRun rare = new CardRun(false, "39", "20", "100", "144", "78", "23", "104", "105", "24", "62", "81", "65", "145", "111", "113", "14", "114", "27", "117", "118", "50", "85", "15", "122", "52", "126", "74", "53", "129", "75", "136", "140", "141", "18", "19");
    private final CardRun mythic = new CardRun(false, "99", "21", "96", "8", "66", "46", "88", "138", "97", "92");
    private final CardRun land = new CardRun(false, "SOM_232", "SOM_233", "SOM_235", "SOM_236", "SOM_240", "SOM_241", "SOM_242", "SOM_243", "SOM_248", "SOM_249", "146", "147", "148", "149", "150", "151", "152", "153", "154", "155");

    private final BoosterStructure commons = new BoosterStructure(
            commonMirran, commonMirran, commonMirran, commonMirran, commonMirran,
            commonPhyrexian, commonPhyrexian, commonPhyrexian, commonPhyrexian, commonPhyrexian
    );

    private final BoosterStructure ABC = new BoosterStructure(uncommonA, uncommonB, uncommonC);
    private final BoosterStructure BCD = new BoosterStructure(uncommonB, uncommonC, uncommonD);

    private final BoosterStructure CDA = new BoosterStructure(uncommonC, uncommonD, uncommonA);
    private final BoosterStructure DAB = new BoosterStructure(uncommonD, uncommonA, uncommonB);
    private final BoosterStructure R1 = new BoosterStructure(rare);
    private final BoosterStructure M1 = new BoosterStructure(mythic);
    private final BoosterStructure L1 = new BoosterStructure(land);

    private final RarityConfiguration commonRuns = new RarityConfiguration(commons);
    // Assuming all uncommons appear with equal frenquency,
    // each of A and C should be left out 7/40 of the time,
    // and each of B and D should be left out 13/40 of the time.
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            ABC, ABC, ABC, ABC, ABC, ABC, ABC, ABC, ABC, ABC, ABC, ABC, ABC,
            BCD, BCD, BCD, BCD, BCD, BCD, BCD,
            CDA, CDA, CDA, CDA, CDA, CDA, CDA, CDA, CDA, CDA, CDA, CDA, CDA,
            DAB, DAB, DAB, DAB, DAB, DAB, DAB
    );
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1, R1, R1, R1, R1, R1, R1, M1);
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
