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
 * @author Backfir3
 */
public final class Judgment extends ExpansionSet {

    private static final Judgment instance = new Judgment();

    public static Judgment getInstance() {
        return instance;
    }

    private Judgment() {
        super("Judgment", "JUD", ExpansionSet.buildDate(2002, 5, 27), SetType.EXPANSION);
        this.blockName = "Odyssey";
        this.parentSet = Odyssey.getInstance();
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        this.hasUnbalancedColors = true;

        cards.add(new SetCardInfo("Ancestor's Chosen", 1, Rarity.UNCOMMON, mage.cards.a.AncestorsChosen.class));
        cards.add(new SetCardInfo("Anger", 77, Rarity.UNCOMMON, mage.cards.a.Anger.class));
        cards.add(new SetCardInfo("Anurid Barkripper", 104, Rarity.COMMON, mage.cards.a.AnuridBarkripper.class));
        cards.add(new SetCardInfo("Anurid Brushhopper", 137, Rarity.RARE, mage.cards.a.AnuridBrushhopper.class));
        cards.add(new SetCardInfo("Anurid Swarmsnapper", 105, Rarity.UNCOMMON, mage.cards.a.AnuridSwarmsnapper.class));
        cards.add(new SetCardInfo("Arcane Teachings", 78, Rarity.COMMON, mage.cards.a.ArcaneTeachings.class));
        cards.add(new SetCardInfo("Aven Fogbringer", 34, Rarity.COMMON, mage.cards.a.AvenFogbringer.class));
        cards.add(new SetCardInfo("Aven Warcraft", 2, Rarity.UNCOMMON, mage.cards.a.AvenWarcraft.class));
        cards.add(new SetCardInfo("Balthor the Defiled", 61, Rarity.RARE, mage.cards.b.BalthorTheDefiled.class));
        cards.add(new SetCardInfo("Barbarian Bully", 79, Rarity.COMMON, mage.cards.b.BarbarianBully.class));
        cards.add(new SetCardInfo("Battle Screech", 3, Rarity.UNCOMMON, mage.cards.b.BattleScreech.class));
        cards.add(new SetCardInfo("Battlefield Scrounger", 106, Rarity.COMMON, mage.cards.b.BattlefieldScrounger.class));
        cards.add(new SetCardInfo("Battlewise Aven", 4, Rarity.COMMON, mage.cards.b.BattlewiseAven.class));
        cards.add(new SetCardInfo("Benevolent Bodyguard", 5, Rarity.COMMON, mage.cards.b.BenevolentBodyguard.class));
        cards.add(new SetCardInfo("Book Burning", 80, Rarity.COMMON, mage.cards.b.BookBurning.class));
        cards.add(new SetCardInfo("Border Patrol", 6, Rarity.COMMON, mage.cards.b.BorderPatrol.class));
        cards.add(new SetCardInfo("Brawn", 107, Rarity.UNCOMMON, mage.cards.b.Brawn.class));
        cards.add(new SetCardInfo("Breaking Point", 81, Rarity.RARE, mage.cards.b.BreakingPoint.class));
        cards.add(new SetCardInfo("Browbeat", 82, Rarity.UNCOMMON, mage.cards.b.Browbeat.class));
        cards.add(new SetCardInfo("Burning Wish", 83, Rarity.RARE, mage.cards.b.BurningWish.class));
        cards.add(new SetCardInfo("Cabal Therapy", 62, Rarity.UNCOMMON, mage.cards.c.CabalTherapy.class));
        cards.add(new SetCardInfo("Cabal Trainee", 63, Rarity.COMMON, mage.cards.c.CabalTrainee.class));
        cards.add(new SetCardInfo("Cagemail", 7, Rarity.COMMON, mage.cards.c.Cagemail.class));
        cards.add(new SetCardInfo("Canopy Claws", 108, Rarity.COMMON, mage.cards.c.CanopyClaws.class));
        cards.add(new SetCardInfo("Centaur Rootcaster", 109, Rarity.COMMON, mage.cards.c.CentaurRootcaster.class));
        cards.add(new SetCardInfo("Cephalid Constable", 35, Rarity.RARE, mage.cards.c.CephalidConstable.class));
        cards.add(new SetCardInfo("Cephalid Inkshrouder", 36, Rarity.UNCOMMON, mage.cards.c.CephalidInkshrouder.class));
        cards.add(new SetCardInfo("Chastise", 8, Rarity.UNCOMMON, mage.cards.c.Chastise.class));
        cards.add(new SetCardInfo("Commander Eesha", 9, Rarity.RARE, mage.cards.c.CommanderEesha.class));
        cards.add(new SetCardInfo("Crush of Wurms", 110, Rarity.RARE, mage.cards.c.CrushOfWurms.class));
        cards.add(new SetCardInfo("Cunning Wish", 37, Rarity.RARE, mage.cards.c.CunningWish.class));
        cards.add(new SetCardInfo("Death Wish", 64, Rarity.RARE, mage.cards.d.DeathWish.class));
        cards.add(new SetCardInfo("Defy Gravity", 38, Rarity.COMMON, mage.cards.d.DefyGravity.class));
        cards.add(new SetCardInfo("Dwarven Bloodboiler", 84, Rarity.RARE, mage.cards.d.DwarvenBloodboiler.class));
        cards.add(new SetCardInfo("Dwarven Driller", 85, Rarity.UNCOMMON, mage.cards.d.DwarvenDriller.class));
        cards.add(new SetCardInfo("Dwarven Scorcher", 86, Rarity.COMMON, mage.cards.d.DwarvenScorcher.class));
        cards.add(new SetCardInfo("Earsplitting Rats", 65, Rarity.COMMON, mage.cards.e.EarsplittingRats.class));
        cards.add(new SetCardInfo("Elephant Guide", 111, Rarity.UNCOMMON, mage.cards.e.ElephantGuide.class));
        cards.add(new SetCardInfo("Ember Shot", 87, Rarity.COMMON, mage.cards.e.EmberShot.class));
        cards.add(new SetCardInfo("Envelop", 39, Rarity.COMMON, mage.cards.e.Envelop.class));
        cards.add(new SetCardInfo("Epic Struggle", 112, Rarity.RARE, mage.cards.e.EpicStruggle.class));
        cards.add(new SetCardInfo("Erhnam Djinn", 113, Rarity.RARE, mage.cards.e.ErhnamDjinn.class));
        cards.add(new SetCardInfo("Exoskeletal Armor", 114, Rarity.UNCOMMON, mage.cards.e.ExoskeletalArmor.class));
        cards.add(new SetCardInfo("Filth", 66, Rarity.UNCOMMON, mage.cards.f.Filth.class));
        cards.add(new SetCardInfo("Firecat Blitz", 88, Rarity.UNCOMMON, mage.cards.f.FirecatBlitz.class));
        cards.add(new SetCardInfo("Flaring Pain", 89, Rarity.COMMON, mage.cards.f.FlaringPain.class));
        cards.add(new SetCardInfo("Flash of Insight", 40, Rarity.UNCOMMON, mage.cards.f.FlashOfInsight.class));
        cards.add(new SetCardInfo("Fledgling Dragon", 90, Rarity.RARE, mage.cards.f.FledglingDragon.class));
        cards.add(new SetCardInfo("Folk Medicine", 115, Rarity.COMMON, mage.cards.f.FolkMedicine.class));
        cards.add(new SetCardInfo("Forcemage Advocate", 116, Rarity.UNCOMMON, mage.cards.f.ForcemageAdvocate.class));
        cards.add(new SetCardInfo("Funeral Pyre", 10, Rarity.COMMON, mage.cards.f.FuneralPyre.class));
        cards.add(new SetCardInfo("Genesis", 117, Rarity.RARE, mage.cards.g.Genesis.class));
        cards.add(new SetCardInfo("Giant Warthog", 118, Rarity.COMMON, mage.cards.g.GiantWarthog.class));
        cards.add(new SetCardInfo("Glory", 11, Rarity.RARE, mage.cards.g.Glory.class));
        cards.add(new SetCardInfo("Golden Wish", 12, Rarity.RARE, mage.cards.g.GoldenWish.class));
        cards.add(new SetCardInfo("Goretusk Firebeast", 91, Rarity.COMMON, mage.cards.g.GoretuskFirebeast.class));
        cards.add(new SetCardInfo("Grave Consequences", 67, Rarity.UNCOMMON, mage.cards.g.GraveConsequences.class));
        cards.add(new SetCardInfo("Grip of Amnesia", 41, Rarity.COMMON, mage.cards.g.GripOfAmnesia.class));
        cards.add(new SetCardInfo("Grizzly Fate", 119, Rarity.UNCOMMON, mage.cards.g.GrizzlyFate.class));
        cards.add(new SetCardInfo("Guided Strike", 13, Rarity.COMMON, mage.cards.g.GuidedStrike.class));
        cards.add(new SetCardInfo("Guiltfeeder", 68, Rarity.RARE, mage.cards.g.Guiltfeeder.class));
        cards.add(new SetCardInfo("Hapless Researcher", 42, Rarity.COMMON, mage.cards.h.HaplessResearcher.class));
        cards.add(new SetCardInfo("Harvester Druid", 120, Rarity.COMMON, mage.cards.h.HarvesterDruid.class));
        cards.add(new SetCardInfo("Hunting Grounds", 138, Rarity.RARE, mage.cards.h.HuntingGrounds.class));
        cards.add(new SetCardInfo("Infectious Rage", 92, Rarity.UNCOMMON, mage.cards.i.InfectiousRage.class));
        cards.add(new SetCardInfo("Ironshell Beetle", 121, Rarity.COMMON, mage.cards.i.IronshellBeetle.class));
        cards.add(new SetCardInfo("Jeska, Warrior Adept", 93, Rarity.RARE, mage.cards.j.JeskaWarriorAdept.class));
        cards.add(new SetCardInfo("Keep Watch", 43, Rarity.COMMON, mage.cards.k.KeepWatch.class));
        cards.add(new SetCardInfo("Krosan Reclamation", 122, Rarity.UNCOMMON, mage.cards.k.KrosanReclamation.class));
        cards.add(new SetCardInfo("Krosan Verge", 141, Rarity.UNCOMMON, mage.cards.k.KrosanVerge.class));
        cards.add(new SetCardInfo("Krosan Wayfarer", 123, Rarity.COMMON, mage.cards.k.KrosanWayfarer.class));
        cards.add(new SetCardInfo("Laquatus's Disdain", 44, Rarity.UNCOMMON, mage.cards.l.LaquatussDisdain.class));
        cards.add(new SetCardInfo("Lava Dart", 94, Rarity.COMMON, mage.cards.l.LavaDart.class));
        cards.add(new SetCardInfo("Lead Astray", 14, Rarity.COMMON, mage.cards.l.LeadAstray.class));
        cards.add(new SetCardInfo("Liberated Dwarf", 95, Rarity.COMMON, mage.cards.l.LiberatedDwarf.class));
        cards.add(new SetCardInfo("Lightning Surge", 96, Rarity.RARE, mage.cards.l.LightningSurge.class));
        cards.add(new SetCardInfo("Living Wish", 124, Rarity.RARE, mage.cards.l.LivingWish.class));
        cards.add(new SetCardInfo("Lost in Thought", 45, Rarity.COMMON, mage.cards.l.LostInThought.class));
        cards.add(new SetCardInfo("Masked Gorgon", 69, Rarity.RARE, mage.cards.m.MaskedGorgon.class));
        cards.add(new SetCardInfo("Mental Note", 46, Rarity.COMMON, mage.cards.m.MentalNote.class));
        cards.add(new SetCardInfo("Mirari's Wake", 139, Rarity.RARE, mage.cards.m.MirarisWake.class));
        cards.add(new SetCardInfo("Mirror Wall", 47, Rarity.COMMON, mage.cards.m.MirrorWall.class));
        cards.add(new SetCardInfo("Mist of Stagnation", 48, Rarity.RARE, mage.cards.m.MistOfStagnation.class));
        cards.add(new SetCardInfo("Morality Shift", 70, Rarity.RARE, mage.cards.m.MoralityShift.class));
        cards.add(new SetCardInfo("Nantuko Monastery", 142, Rarity.UNCOMMON, mage.cards.n.NantukoMonastery.class));
        cards.add(new SetCardInfo("Nantuko Tracer", 125, Rarity.COMMON, mage.cards.n.NantukoTracer.class));
        cards.add(new SetCardInfo("Nomad Mythmaker", 15, Rarity.RARE, mage.cards.n.NomadMythmaker.class));
        cards.add(new SetCardInfo("Nullmage Advocate", 126, Rarity.COMMON, mage.cards.n.NullmageAdvocate.class));
        cards.add(new SetCardInfo("Phantom Centaur", 127, Rarity.UNCOMMON, mage.cards.p.PhantomCentaur.class));
        cards.add(new SetCardInfo("Phantom Flock", 16, Rarity.UNCOMMON, mage.cards.p.PhantomFlock.class));
        cards.add(new SetCardInfo("Phantom Nantuko", 128, Rarity.RARE, mage.cards.p.PhantomNantuko.class));
        cards.add(new SetCardInfo("Phantom Nishoba", 140, Rarity.RARE, mage.cards.p.PhantomNishoba.class));
        cards.add(new SetCardInfo("Phantom Nomad", 17, Rarity.COMMON, mage.cards.p.PhantomNomad.class));
        cards.add(new SetCardInfo("Phantom Tiger", 129, Rarity.COMMON, mage.cards.p.PhantomTiger.class));
        cards.add(new SetCardInfo("Planar Chaos", 97, Rarity.UNCOMMON, mage.cards.p.PlanarChaos.class));
        cards.add(new SetCardInfo("Prismatic Strands", 18, Rarity.COMMON, mage.cards.p.PrismaticStrands.class));
        cards.add(new SetCardInfo("Pulsemage Advocate", 19, Rarity.RARE, mage.cards.p.PulsemageAdvocate.class));
        cards.add(new SetCardInfo("Quiet Speculation", 49, Rarity.UNCOMMON, mage.cards.q.QuietSpeculation.class));
        cards.add(new SetCardInfo("Rats' Feast", 71, Rarity.COMMON, mage.cards.r.RatsFeast.class));
        cards.add(new SetCardInfo("Ray of Revelation", 20, Rarity.COMMON, mage.cards.r.RayOfRevelation.class));
        cards.add(new SetCardInfo("Riftstone Portal", 143, Rarity.UNCOMMON, mage.cards.r.RiftstonePortal.class));
        cards.add(new SetCardInfo("Scalpelexis", 50, Rarity.RARE, mage.cards.s.Scalpelexis.class));
        cards.add(new SetCardInfo("Seedtime", 130, Rarity.RARE, mage.cards.s.Seedtime.class));
        cards.add(new SetCardInfo("Selfless Exorcist", 21, Rarity.RARE, mage.cards.s.SelflessExorcist.class));
        cards.add(new SetCardInfo("Serene Sunset", 131, Rarity.UNCOMMON, mage.cards.s.SereneSunset.class));
        cards.add(new SetCardInfo("Shieldmage Advocate", 22, Rarity.COMMON, mage.cards.s.ShieldmageAdvocate.class));
        cards.add(new SetCardInfo("Silver Seraph", 23, Rarity.RARE, mage.cards.s.SilverSeraph.class));
        cards.add(new SetCardInfo("Solitary Confinement", 24, Rarity.RARE, mage.cards.s.SolitaryConfinement.class));
        cards.add(new SetCardInfo("Soulcatchers' Aerie", 25, Rarity.UNCOMMON, mage.cards.s.SoulcatchersAerie.class));
        cards.add(new SetCardInfo("Soulgorger Orgg", 99, Rarity.UNCOMMON, mage.cards.s.SoulgorgerOrgg.class));
        cards.add(new SetCardInfo("Spellgorger Barbarian", 100, Rarity.COMMON, mage.cards.s.SpellgorgerBarbarian.class));
        cards.add(new SetCardInfo("Spelljack", 51, Rarity.RARE, mage.cards.s.Spelljack.class));
        cards.add(new SetCardInfo("Spirit Cairn", 26, Rarity.UNCOMMON, mage.cards.s.SpiritCairn.class));
        cards.add(new SetCardInfo("Spurnmage Advocate", 27, Rarity.UNCOMMON, mage.cards.s.SpurnmageAdvocate.class));
        cards.add(new SetCardInfo("Stitch Together", 72, Rarity.UNCOMMON, mage.cards.s.StitchTogether.class));
        cards.add(new SetCardInfo("Sudden Strength", 132, Rarity.COMMON, mage.cards.s.SuddenStrength.class));
        cards.add(new SetCardInfo("Suntail Hawk", 28, Rarity.COMMON, mage.cards.s.SuntailHawk.class));
        cards.add(new SetCardInfo("Sutured Ghoul", 73, Rarity.RARE, mage.cards.s.SuturedGhoul.class));
        cards.add(new SetCardInfo("Swelter", 101, Rarity.UNCOMMON, mage.cards.s.Swelter.class));
        cards.add(new SetCardInfo("Swirling Sandstorm", 102, Rarity.COMMON, mage.cards.s.SwirlingSandstorm.class));
        cards.add(new SetCardInfo("Sylvan Safekeeper", 133, Rarity.RARE, mage.cards.s.SylvanSafekeeper.class));
        cards.add(new SetCardInfo("Telekinetic Bonds", 52, Rarity.RARE, mage.cards.t.TelekineticBonds.class));
        cards.add(new SetCardInfo("Test of Endurance", 29, Rarity.RARE, mage.cards.t.TestOfEndurance.class));
        cards.add(new SetCardInfo("Thriss, Nantuko Primus", 134, Rarity.RARE, mage.cards.t.ThrissNantukoPrimus.class));
        cards.add(new SetCardInfo("Toxic Stench", 74, Rarity.COMMON, mage.cards.t.ToxicStench.class));
        cards.add(new SetCardInfo("Trained Pronghorn", 30, Rarity.COMMON, mage.cards.t.TrainedPronghorn.class));
        cards.add(new SetCardInfo("Treacherous Vampire", 75, Rarity.UNCOMMON, mage.cards.t.TreacherousVampire.class));
        cards.add(new SetCardInfo("Treacherous Werewolf", 76, Rarity.COMMON, mage.cards.t.TreacherousWerewolf.class));
        cards.add(new SetCardInfo("Tunneler Wurm", 135, Rarity.UNCOMMON, mage.cards.t.TunnelerWurm.class));
        cards.add(new SetCardInfo("Unquestioned Authority", 31, Rarity.UNCOMMON, mage.cards.u.UnquestionedAuthority.class));
        cards.add(new SetCardInfo("Valor", 32, Rarity.UNCOMMON, mage.cards.v.Valor.class));
        cards.add(new SetCardInfo("Venomous Vines", 136, Rarity.COMMON, mage.cards.v.VenomousVines.class));
        cards.add(new SetCardInfo("Vigilant Sentry", 33, Rarity.COMMON, mage.cards.v.VigilantSentry.class));
        cards.add(new SetCardInfo("Web of Inertia", 53, Rarity.UNCOMMON, mage.cards.w.WebOfInertia.class));
        cards.add(new SetCardInfo("Wonder", 54, Rarity.UNCOMMON, mage.cards.w.Wonder.class));
        cards.add(new SetCardInfo("Worldgorger Dragon", 103, Rarity.RARE, mage.cards.w.WorldgorgerDragon.class));
        cards.add(new SetCardInfo("Wormfang Behemoth", 55, Rarity.RARE, mage.cards.w.WormfangBehemoth.class));
        cards.add(new SetCardInfo("Wormfang Crab", 56, Rarity.UNCOMMON, mage.cards.w.WormfangCrab.class));
        cards.add(new SetCardInfo("Wormfang Drake", 57, Rarity.COMMON, mage.cards.w.WormfangDrake.class));
        cards.add(new SetCardInfo("Wormfang Manta", 58, Rarity.RARE, mage.cards.w.WormfangManta.class));
        cards.add(new SetCardInfo("Wormfang Newt", 59, Rarity.COMMON, mage.cards.w.WormfangNewt.class));
        cards.add(new SetCardInfo("Wormfang Turtle", 60, Rarity.UNCOMMON, mage.cards.w.WormfangTurtle.class));
    }

    @Override
    public BoosterCollator createCollator() {
        return new JudgmentCollator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/jud.html
// Using US collation - commons only
class JudgmentCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "22", "59", "118", "100", "4", "76", "126", "59", "95", "17", "57", "120", "28", "63", "106", "4", "91", "129", "42", "22", "89", "125", "47", "63", "57", "123", "86", "43", "5", "106", "33", "89", "30", "104", "43", "17", "34", "6", "123", "79", "76", "125", "42", "95", "126", "33", "100", "129", "28", "79", "120", "30", "86", "47", "118", "6", "91", "5", "104", "34");
    private final CardRun commonB = new CardRun(true, "121", "102", "108", "45", "71", "132", "14", "87", "41", "109", "13", "115", "39", "78", "18", "74", "20", "65", "46", "10", "121", "38", "20", "80", "132", "10", "87", "71", "41", "102", "136", "74", "7", "80", "109", "39", "13", "136", "46", "108", "7", "94", "18", "45", "115", "78", "65", "38", "94", "14");
    private final CardRun uncommon = new CardRun(false, "1", "77", "105", "2", "3", "107", "82", "62", "36", "8", "85", "111", "114", "66", "88", "40", "116", "67", "119", "92", "122", "141", "44", "142", "127", "16", "97", "49", "143", "131", "25", "99", "26", "27", "72", "101", "75", "135", "31", "32", "53", "54", "56", "60");
    // Shaman's Trance (rare, #98) not implemented, so has been omitted
    private final CardRun rare = new CardRun(false, "137", "61", "81", "83", "35", "9", "110", "37", "64", "84", "112", "113", "90", "117", "11", "12", "68", "138", "93", "96", "124", "69", "139", "48", "70", "15", "128", "140", "19", "50", "130", "21", "23", "24", "51", "73", "133", "52", "29", "134", "103", "55", "58");

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
