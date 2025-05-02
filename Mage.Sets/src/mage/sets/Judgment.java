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

        cards.add(new SetCardInfo("Ancestor's Chosen", 1, Rarity.UNCOMMON, mage.cards.a.AncestorsChosen.class, RETRO_ART));
        cards.add(new SetCardInfo("Anger", 77, Rarity.UNCOMMON, mage.cards.a.Anger.class, RETRO_ART));
        cards.add(new SetCardInfo("Anurid Barkripper", 104, Rarity.COMMON, mage.cards.a.AnuridBarkripper.class, RETRO_ART));
        cards.add(new SetCardInfo("Anurid Brushhopper", 137, Rarity.RARE, mage.cards.a.AnuridBrushhopper.class, RETRO_ART));
        cards.add(new SetCardInfo("Anurid Swarmsnapper", 105, Rarity.UNCOMMON, mage.cards.a.AnuridSwarmsnapper.class, RETRO_ART));
        cards.add(new SetCardInfo("Arcane Teachings", 78, Rarity.COMMON, mage.cards.a.ArcaneTeachings.class, RETRO_ART));
        cards.add(new SetCardInfo("Aven Fogbringer", 34, Rarity.COMMON, mage.cards.a.AvenFogbringer.class, RETRO_ART));
        cards.add(new SetCardInfo("Aven Warcraft", 2, Rarity.UNCOMMON, mage.cards.a.AvenWarcraft.class, RETRO_ART));
        cards.add(new SetCardInfo("Balthor the Defiled", 61, Rarity.RARE, mage.cards.b.BalthorTheDefiled.class, RETRO_ART));
        cards.add(new SetCardInfo("Barbarian Bully", 79, Rarity.COMMON, mage.cards.b.BarbarianBully.class, RETRO_ART));
        cards.add(new SetCardInfo("Battle Screech", 3, Rarity.UNCOMMON, mage.cards.b.BattleScreech.class, RETRO_ART));
        cards.add(new SetCardInfo("Battlefield Scrounger", 106, Rarity.COMMON, mage.cards.b.BattlefieldScrounger.class, RETRO_ART));
        cards.add(new SetCardInfo("Battlewise Aven", 4, Rarity.COMMON, mage.cards.b.BattlewiseAven.class, RETRO_ART));
        cards.add(new SetCardInfo("Benevolent Bodyguard", 5, Rarity.COMMON, mage.cards.b.BenevolentBodyguard.class, RETRO_ART));
        cards.add(new SetCardInfo("Book Burning", 80, Rarity.COMMON, mage.cards.b.BookBurning.class, RETRO_ART));
        cards.add(new SetCardInfo("Border Patrol", 6, Rarity.COMMON, mage.cards.b.BorderPatrol.class, RETRO_ART));
        cards.add(new SetCardInfo("Brawn", 107, Rarity.UNCOMMON, mage.cards.b.Brawn.class, RETRO_ART));
        cards.add(new SetCardInfo("Breaking Point", 81, Rarity.RARE, mage.cards.b.BreakingPoint.class, RETRO_ART));
        cards.add(new SetCardInfo("Browbeat", 82, Rarity.UNCOMMON, mage.cards.b.Browbeat.class, RETRO_ART));
        cards.add(new SetCardInfo("Burning Wish", 83, Rarity.RARE, mage.cards.b.BurningWish.class, RETRO_ART));
        cards.add(new SetCardInfo("Cabal Therapy", 62, Rarity.UNCOMMON, mage.cards.c.CabalTherapy.class, RETRO_ART));
        cards.add(new SetCardInfo("Cabal Trainee", 63, Rarity.COMMON, mage.cards.c.CabalTrainee.class, RETRO_ART));
        cards.add(new SetCardInfo("Cagemail", 7, Rarity.COMMON, mage.cards.c.Cagemail.class, RETRO_ART));
        cards.add(new SetCardInfo("Canopy Claws", 108, Rarity.COMMON, mage.cards.c.CanopyClaws.class, RETRO_ART));
        cards.add(new SetCardInfo("Centaur Rootcaster", 109, Rarity.COMMON, mage.cards.c.CentaurRootcaster.class, RETRO_ART));
        cards.add(new SetCardInfo("Cephalid Constable", 35, Rarity.RARE, mage.cards.c.CephalidConstable.class, RETRO_ART));
        cards.add(new SetCardInfo("Cephalid Inkshrouder", 36, Rarity.UNCOMMON, mage.cards.c.CephalidInkshrouder.class, RETRO_ART));
        cards.add(new SetCardInfo("Chastise", 8, Rarity.UNCOMMON, mage.cards.c.Chastise.class, RETRO_ART));
        cards.add(new SetCardInfo("Commander Eesha", 9, Rarity.RARE, mage.cards.c.CommanderEesha.class, RETRO_ART));
        cards.add(new SetCardInfo("Crush of Wurms", 110, Rarity.RARE, mage.cards.c.CrushOfWurms.class, RETRO_ART));
        cards.add(new SetCardInfo("Cunning Wish", 37, Rarity.RARE, mage.cards.c.CunningWish.class, RETRO_ART));
        cards.add(new SetCardInfo("Death Wish", 64, Rarity.RARE, mage.cards.d.DeathWish.class, RETRO_ART));
        cards.add(new SetCardInfo("Defy Gravity", 38, Rarity.COMMON, mage.cards.d.DefyGravity.class, RETRO_ART));
        cards.add(new SetCardInfo("Dwarven Bloodboiler", 84, Rarity.RARE, mage.cards.d.DwarvenBloodboiler.class, RETRO_ART));
        cards.add(new SetCardInfo("Dwarven Driller", 85, Rarity.UNCOMMON, mage.cards.d.DwarvenDriller.class, RETRO_ART));
        cards.add(new SetCardInfo("Dwarven Scorcher", 86, Rarity.COMMON, mage.cards.d.DwarvenScorcher.class, RETRO_ART));
        cards.add(new SetCardInfo("Earsplitting Rats", 65, Rarity.COMMON, mage.cards.e.EarsplittingRats.class, RETRO_ART));
        cards.add(new SetCardInfo("Elephant Guide", 111, Rarity.UNCOMMON, mage.cards.e.ElephantGuide.class, RETRO_ART));
        cards.add(new SetCardInfo("Ember Shot", 87, Rarity.COMMON, mage.cards.e.EmberShot.class, RETRO_ART));
        cards.add(new SetCardInfo("Envelop", 39, Rarity.COMMON, mage.cards.e.Envelop.class, RETRO_ART));
        cards.add(new SetCardInfo("Epic Struggle", 112, Rarity.RARE, mage.cards.e.EpicStruggle.class, RETRO_ART));
        cards.add(new SetCardInfo("Erhnam Djinn", 113, Rarity.RARE, mage.cards.e.ErhnamDjinn.class, RETRO_ART));
        cards.add(new SetCardInfo("Exoskeletal Armor", 114, Rarity.UNCOMMON, mage.cards.e.ExoskeletalArmor.class, RETRO_ART));
        cards.add(new SetCardInfo("Filth", 66, Rarity.UNCOMMON, mage.cards.f.Filth.class, RETRO_ART));
        cards.add(new SetCardInfo("Firecat Blitz", 88, Rarity.UNCOMMON, mage.cards.f.FirecatBlitz.class, RETRO_ART));
        cards.add(new SetCardInfo("Flaring Pain", 89, Rarity.COMMON, mage.cards.f.FlaringPain.class, RETRO_ART));
        cards.add(new SetCardInfo("Flash of Insight", 40, Rarity.UNCOMMON, mage.cards.f.FlashOfInsight.class, RETRO_ART));
        cards.add(new SetCardInfo("Fledgling Dragon", 90, Rarity.RARE, mage.cards.f.FledglingDragon.class, RETRO_ART));
        cards.add(new SetCardInfo("Folk Medicine", 115, Rarity.COMMON, mage.cards.f.FolkMedicine.class, RETRO_ART));
        cards.add(new SetCardInfo("Forcemage Advocate", 116, Rarity.UNCOMMON, mage.cards.f.ForcemageAdvocate.class, RETRO_ART));
        cards.add(new SetCardInfo("Funeral Pyre", 10, Rarity.COMMON, mage.cards.f.FuneralPyre.class, RETRO_ART));
        cards.add(new SetCardInfo("Genesis", 117, Rarity.RARE, mage.cards.g.Genesis.class, RETRO_ART));
        cards.add(new SetCardInfo("Giant Warthog", 118, Rarity.COMMON, mage.cards.g.GiantWarthog.class, RETRO_ART));
        cards.add(new SetCardInfo("Glory", 11, Rarity.RARE, mage.cards.g.Glory.class, RETRO_ART));
        cards.add(new SetCardInfo("Golden Wish", 12, Rarity.RARE, mage.cards.g.GoldenWish.class, RETRO_ART));
        cards.add(new SetCardInfo("Goretusk Firebeast", 91, Rarity.COMMON, mage.cards.g.GoretuskFirebeast.class, RETRO_ART));
        cards.add(new SetCardInfo("Grave Consequences", 67, Rarity.UNCOMMON, mage.cards.g.GraveConsequences.class, RETRO_ART));
        cards.add(new SetCardInfo("Grip of Amnesia", 41, Rarity.COMMON, mage.cards.g.GripOfAmnesia.class, RETRO_ART));
        cards.add(new SetCardInfo("Grizzly Fate", 119, Rarity.UNCOMMON, mage.cards.g.GrizzlyFate.class, RETRO_ART));
        cards.add(new SetCardInfo("Guided Strike", 13, Rarity.COMMON, mage.cards.g.GuidedStrike.class, RETRO_ART));
        cards.add(new SetCardInfo("Guiltfeeder", 68, Rarity.RARE, mage.cards.g.Guiltfeeder.class, RETRO_ART));
        cards.add(new SetCardInfo("Hapless Researcher", 42, Rarity.COMMON, mage.cards.h.HaplessResearcher.class, RETRO_ART));
        cards.add(new SetCardInfo("Harvester Druid", 120, Rarity.COMMON, mage.cards.h.HarvesterDruid.class, RETRO_ART));
        cards.add(new SetCardInfo("Hunting Grounds", 138, Rarity.RARE, mage.cards.h.HuntingGrounds.class, RETRO_ART));
        cards.add(new SetCardInfo("Infectious Rage", 92, Rarity.UNCOMMON, mage.cards.i.InfectiousRage.class, RETRO_ART));
        cards.add(new SetCardInfo("Ironshell Beetle", 121, Rarity.COMMON, mage.cards.i.IronshellBeetle.class, RETRO_ART));
        cards.add(new SetCardInfo("Jeska, Warrior Adept", 93, Rarity.RARE, mage.cards.j.JeskaWarriorAdept.class, RETRO_ART));
        cards.add(new SetCardInfo("Keep Watch", 43, Rarity.COMMON, mage.cards.k.KeepWatch.class, RETRO_ART));
        cards.add(new SetCardInfo("Krosan Reclamation", 122, Rarity.UNCOMMON, mage.cards.k.KrosanReclamation.class, RETRO_ART));
        cards.add(new SetCardInfo("Krosan Verge", 141, Rarity.UNCOMMON, mage.cards.k.KrosanVerge.class, RETRO_ART));
        cards.add(new SetCardInfo("Krosan Wayfarer", 123, Rarity.COMMON, mage.cards.k.KrosanWayfarer.class, RETRO_ART));
        cards.add(new SetCardInfo("Laquatus's Disdain", 44, Rarity.UNCOMMON, mage.cards.l.LaquatussDisdain.class, RETRO_ART));
        cards.add(new SetCardInfo("Lava Dart", 94, Rarity.COMMON, mage.cards.l.LavaDart.class, RETRO_ART));
        cards.add(new SetCardInfo("Lead Astray", 14, Rarity.COMMON, mage.cards.l.LeadAstray.class, RETRO_ART));
        cards.add(new SetCardInfo("Liberated Dwarf", 95, Rarity.COMMON, mage.cards.l.LiberatedDwarf.class, RETRO_ART));
        cards.add(new SetCardInfo("Lightning Surge", 96, Rarity.RARE, mage.cards.l.LightningSurge.class, RETRO_ART));
        cards.add(new SetCardInfo("Living Wish", 124, Rarity.RARE, mage.cards.l.LivingWish.class, RETRO_ART));
        cards.add(new SetCardInfo("Lost in Thought", 45, Rarity.COMMON, mage.cards.l.LostInThought.class, RETRO_ART));
        cards.add(new SetCardInfo("Masked Gorgon", 69, Rarity.RARE, mage.cards.m.MaskedGorgon.class, RETRO_ART));
        cards.add(new SetCardInfo("Mental Note", 46, Rarity.COMMON, mage.cards.m.MentalNote.class, RETRO_ART));
        cards.add(new SetCardInfo("Mirari's Wake", 139, Rarity.RARE, mage.cards.m.MirarisWake.class, RETRO_ART));
        cards.add(new SetCardInfo("Mirror Wall", 47, Rarity.COMMON, mage.cards.m.MirrorWall.class, RETRO_ART));
        cards.add(new SetCardInfo("Mist of Stagnation", 48, Rarity.RARE, mage.cards.m.MistOfStagnation.class, RETRO_ART));
        cards.add(new SetCardInfo("Morality Shift", 70, Rarity.RARE, mage.cards.m.MoralityShift.class, RETRO_ART));
        cards.add(new SetCardInfo("Nantuko Monastery", 142, Rarity.UNCOMMON, mage.cards.n.NantukoMonastery.class, RETRO_ART));
        cards.add(new SetCardInfo("Nantuko Tracer", 125, Rarity.COMMON, mage.cards.n.NantukoTracer.class, RETRO_ART));
        cards.add(new SetCardInfo("Nomad Mythmaker", 15, Rarity.RARE, mage.cards.n.NomadMythmaker.class, RETRO_ART));
        cards.add(new SetCardInfo("Nullmage Advocate", 126, Rarity.COMMON, mage.cards.n.NullmageAdvocate.class, RETRO_ART));
        cards.add(new SetCardInfo("Phantom Centaur", 127, Rarity.UNCOMMON, mage.cards.p.PhantomCentaur.class, RETRO_ART));
        cards.add(new SetCardInfo("Phantom Flock", 16, Rarity.UNCOMMON, mage.cards.p.PhantomFlock.class, RETRO_ART));
        cards.add(new SetCardInfo("Phantom Nantuko", 128, Rarity.RARE, mage.cards.p.PhantomNantuko.class, RETRO_ART));
        cards.add(new SetCardInfo("Phantom Nishoba", 140, Rarity.RARE, mage.cards.p.PhantomNishoba.class, RETRO_ART));
        cards.add(new SetCardInfo("Phantom Nomad", 17, Rarity.COMMON, mage.cards.p.PhantomNomad.class, RETRO_ART));
        cards.add(new SetCardInfo("Phantom Tiger", 129, Rarity.COMMON, mage.cards.p.PhantomTiger.class, RETRO_ART));
        cards.add(new SetCardInfo("Planar Chaos", 97, Rarity.UNCOMMON, mage.cards.p.PlanarChaos.class, RETRO_ART));
        cards.add(new SetCardInfo("Prismatic Strands", 18, Rarity.COMMON, mage.cards.p.PrismaticStrands.class, RETRO_ART));
        cards.add(new SetCardInfo("Pulsemage Advocate", 19, Rarity.RARE, mage.cards.p.PulsemageAdvocate.class, RETRO_ART));
        cards.add(new SetCardInfo("Quiet Speculation", 49, Rarity.UNCOMMON, mage.cards.q.QuietSpeculation.class, RETRO_ART));
        cards.add(new SetCardInfo("Rats' Feast", 71, Rarity.COMMON, mage.cards.r.RatsFeast.class, RETRO_ART));
        cards.add(new SetCardInfo("Ray of Revelation", 20, Rarity.COMMON, mage.cards.r.RayOfRevelation.class, RETRO_ART));
        cards.add(new SetCardInfo("Riftstone Portal", 143, Rarity.UNCOMMON, mage.cards.r.RiftstonePortal.class, RETRO_ART));
        cards.add(new SetCardInfo("Scalpelexis", 50, Rarity.RARE, mage.cards.s.Scalpelexis.class, RETRO_ART));
        cards.add(new SetCardInfo("Seedtime", 130, Rarity.RARE, mage.cards.s.Seedtime.class, RETRO_ART));
        cards.add(new SetCardInfo("Selfless Exorcist", 21, Rarity.RARE, mage.cards.s.SelflessExorcist.class, RETRO_ART));
        cards.add(new SetCardInfo("Serene Sunset", 131, Rarity.UNCOMMON, mage.cards.s.SereneSunset.class, RETRO_ART));
        cards.add(new SetCardInfo("Shieldmage Advocate", 22, Rarity.COMMON, mage.cards.s.ShieldmageAdvocate.class, RETRO_ART));
        cards.add(new SetCardInfo("Silver Seraph", 23, Rarity.RARE, mage.cards.s.SilverSeraph.class, RETRO_ART));
        cards.add(new SetCardInfo("Solitary Confinement", 24, Rarity.RARE, mage.cards.s.SolitaryConfinement.class, RETRO_ART));
        cards.add(new SetCardInfo("Soulcatchers' Aerie", 25, Rarity.UNCOMMON, mage.cards.s.SoulcatchersAerie.class, RETRO_ART));
        cards.add(new SetCardInfo("Soulgorger Orgg", 99, Rarity.UNCOMMON, mage.cards.s.SoulgorgerOrgg.class, RETRO_ART));
        cards.add(new SetCardInfo("Spellgorger Barbarian", 100, Rarity.COMMON, mage.cards.s.SpellgorgerBarbarian.class, RETRO_ART));
        cards.add(new SetCardInfo("Spelljack", 51, Rarity.RARE, mage.cards.s.Spelljack.class, RETRO_ART));
        cards.add(new SetCardInfo("Spirit Cairn", 26, Rarity.UNCOMMON, mage.cards.s.SpiritCairn.class, RETRO_ART));
        cards.add(new SetCardInfo("Spurnmage Advocate", 27, Rarity.UNCOMMON, mage.cards.s.SpurnmageAdvocate.class, RETRO_ART));
        cards.add(new SetCardInfo("Stitch Together", 72, Rarity.UNCOMMON, mage.cards.s.StitchTogether.class, RETRO_ART));
        cards.add(new SetCardInfo("Sudden Strength", 132, Rarity.COMMON, mage.cards.s.SuddenStrength.class, RETRO_ART));
        cards.add(new SetCardInfo("Suntail Hawk", 28, Rarity.COMMON, mage.cards.s.SuntailHawk.class, RETRO_ART));
        cards.add(new SetCardInfo("Sutured Ghoul", 73, Rarity.RARE, mage.cards.s.SuturedGhoul.class, RETRO_ART));
        cards.add(new SetCardInfo("Swelter", 101, Rarity.UNCOMMON, mage.cards.s.Swelter.class, RETRO_ART));
        cards.add(new SetCardInfo("Swirling Sandstorm", 102, Rarity.COMMON, mage.cards.s.SwirlingSandstorm.class, RETRO_ART));
        cards.add(new SetCardInfo("Sylvan Safekeeper", 133, Rarity.RARE, mage.cards.s.SylvanSafekeeper.class, RETRO_ART));
        cards.add(new SetCardInfo("Telekinetic Bonds", 52, Rarity.RARE, mage.cards.t.TelekineticBonds.class, RETRO_ART));
        cards.add(new SetCardInfo("Test of Endurance", 29, Rarity.RARE, mage.cards.t.TestOfEndurance.class, RETRO_ART));
        cards.add(new SetCardInfo("Thriss, Nantuko Primus", 134, Rarity.RARE, mage.cards.t.ThrissNantukoPrimus.class, RETRO_ART));
        cards.add(new SetCardInfo("Toxic Stench", 74, Rarity.COMMON, mage.cards.t.ToxicStench.class, RETRO_ART));
        cards.add(new SetCardInfo("Trained Pronghorn", 30, Rarity.COMMON, mage.cards.t.TrainedPronghorn.class, RETRO_ART));
        cards.add(new SetCardInfo("Treacherous Vampire", 75, Rarity.UNCOMMON, mage.cards.t.TreacherousVampire.class, RETRO_ART));
        cards.add(new SetCardInfo("Treacherous Werewolf", 76, Rarity.COMMON, mage.cards.t.TreacherousWerewolf.class, RETRO_ART));
        cards.add(new SetCardInfo("Tunneler Wurm", 135, Rarity.UNCOMMON, mage.cards.t.TunnelerWurm.class, RETRO_ART));
        cards.add(new SetCardInfo("Unquestioned Authority", 31, Rarity.UNCOMMON, mage.cards.u.UnquestionedAuthority.class, RETRO_ART));
        cards.add(new SetCardInfo("Valor", 32, Rarity.UNCOMMON, mage.cards.v.Valor.class, RETRO_ART));
        cards.add(new SetCardInfo("Venomous Vines", 136, Rarity.COMMON, mage.cards.v.VenomousVines.class, RETRO_ART));
        cards.add(new SetCardInfo("Vigilant Sentry", 33, Rarity.COMMON, mage.cards.v.VigilantSentry.class, RETRO_ART));
        cards.add(new SetCardInfo("Web of Inertia", 53, Rarity.UNCOMMON, mage.cards.w.WebOfInertia.class, RETRO_ART));
        cards.add(new SetCardInfo("Wonder", 54, Rarity.UNCOMMON, mage.cards.w.Wonder.class, RETRO_ART));
        cards.add(new SetCardInfo("Worldgorger Dragon", 103, Rarity.RARE, mage.cards.w.WorldgorgerDragon.class, RETRO_ART));
        cards.add(new SetCardInfo("Wormfang Behemoth", 55, Rarity.RARE, mage.cards.w.WormfangBehemoth.class, RETRO_ART));
        cards.add(new SetCardInfo("Wormfang Crab", 56, Rarity.UNCOMMON, mage.cards.w.WormfangCrab.class, RETRO_ART));
        cards.add(new SetCardInfo("Wormfang Drake", 57, Rarity.COMMON, mage.cards.w.WormfangDrake.class, RETRO_ART));
        cards.add(new SetCardInfo("Wormfang Manta", 58, Rarity.RARE, mage.cards.w.WormfangManta.class, RETRO_ART));
        cards.add(new SetCardInfo("Wormfang Newt", 59, Rarity.COMMON, mage.cards.w.WormfangNewt.class, RETRO_ART));
        cards.add(new SetCardInfo("Wormfang Turtle", 60, Rarity.UNCOMMON, mage.cards.w.WormfangTurtle.class, RETRO_ART));
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
