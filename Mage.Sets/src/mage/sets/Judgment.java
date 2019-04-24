
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

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
        cards.add(new SetCardInfo("Grizzly Fate", 119, Rarity.UNCOMMON, mage.cards.g.GrizzlyFate.class));
        cards.add(new SetCardInfo("Guided Strike", 13, Rarity.COMMON, mage.cards.g.GuidedStrike.class));
        cards.add(new SetCardInfo("Guiltfeeder", 68, Rarity.RARE, mage.cards.g.Guiltfeeder.class));
        cards.add(new SetCardInfo("Hapless Researcher", 42, Rarity.COMMON, mage.cards.h.HaplessResearcher.class));
        cards.add(new SetCardInfo("Harvester Druid", 120, Rarity.COMMON, mage.cards.h.HarvesterDruid.class));
        cards.add(new SetCardInfo("Hunting Grounds", 138, Rarity.RARE, mage.cards.h.HuntingGrounds.class));
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
        cards.add(new SetCardInfo("Ray of Revelation", 20, Rarity.COMMON, mage.cards.r.RayOfRevelation.class));
        cards.add(new SetCardInfo("Riftstone Portal", 143, Rarity.UNCOMMON, mage.cards.r.RiftstonePortal.class));
        cards.add(new SetCardInfo("Scalpelexis", 50, Rarity.RARE, mage.cards.s.Scalpelexis.class));
        cards.add(new SetCardInfo("Seedtime", 130, Rarity.RARE, mage.cards.s.Seedtime.class));
        cards.add(new SetCardInfo("Shieldmage Advocate", 22, Rarity.COMMON, mage.cards.s.ShieldmageAdvocate.class));
        cards.add(new SetCardInfo("Silver Seraph", 23, Rarity.RARE, mage.cards.s.SilverSeraph.class));
        cards.add(new SetCardInfo("Solitary Confinement", 24, Rarity.RARE, mage.cards.s.SolitaryConfinement.class));
        cards.add(new SetCardInfo("Soulcatchers' Aerie", 25, Rarity.UNCOMMON, mage.cards.s.SoulcatchersAerie.class));
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
        cards.add(new SetCardInfo("Wormfang Drake", 57, Rarity.COMMON, mage.cards.w.WormfangDrake.class));
        cards.add(new SetCardInfo("Wormfang Manta", 58, Rarity.RARE, mage.cards.w.WormfangManta.class));
        cards.add(new SetCardInfo("Wormfang Newt", 59, Rarity.COMMON, mage.cards.w.WormfangNewt.class));
        cards.add(new SetCardInfo("Wormfang Turtle", 60, Rarity.UNCOMMON, mage.cards.w.WormfangTurtle.class));
    }
}
