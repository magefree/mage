
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author Backfir3
 */
public final class UrzasDestiny extends ExpansionSet {

    private static final UrzasDestiny instance = new UrzasDestiny();

    public static UrzasDestiny getInstance() {
        return instance;
    }

    private UrzasDestiny() {
        super("Urza's Destiny", "UDS", ExpansionSet.buildDate(1999, 6, 7), SetType.EXPANSION);
        this.blockName = "Urza";
        this.parentSet = UrzasSaga.getInstance();
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;

        cards.add(new SetCardInfo("Academy Rector", 1, Rarity.RARE, mage.cards.a.AcademyRector.class, RETRO_ART));
        cards.add(new SetCardInfo("Aether Sting", 76, Rarity.UNCOMMON, mage.cards.a.AetherSting.class, RETRO_ART));
        cards.add(new SetCardInfo("Ancient Silverback", 101, Rarity.RARE, mage.cards.a.AncientSilverback.class, RETRO_ART));
        cards.add(new SetCardInfo("Apprentice Necromancer", 51, Rarity.RARE, mage.cards.a.ApprenticeNecromancer.class, RETRO_ART));
        cards.add(new SetCardInfo("Archery Training", 2, Rarity.UNCOMMON, mage.cards.a.ArcheryTraining.class, RETRO_ART));
        cards.add(new SetCardInfo("Attrition", 52, Rarity.RARE, mage.cards.a.Attrition.class, RETRO_ART));
        cards.add(new SetCardInfo("Aura Thief", 26, Rarity.RARE, mage.cards.a.AuraThief.class, RETRO_ART));
        cards.add(new SetCardInfo("Blizzard Elemental", 27, Rarity.RARE, mage.cards.b.BlizzardElemental.class, RETRO_ART));
        cards.add(new SetCardInfo("Bloodshot Cyclops", 77, Rarity.RARE, mage.cards.b.BloodshotCyclops.class, RETRO_ART));
        cards.add(new SetCardInfo("Body Snatcher", 53, Rarity.RARE, mage.cards.b.BodySnatcher.class, RETRO_ART));
        cards.add(new SetCardInfo("Braidwood Cup", 126, Rarity.UNCOMMON, mage.cards.b.BraidwoodCup.class, RETRO_ART));
        cards.add(new SetCardInfo("Braidwood Sextant", 127, Rarity.UNCOMMON, mage.cards.b.BraidwoodSextant.class, RETRO_ART));
        cards.add(new SetCardInfo("Brass Secretary", 128, Rarity.UNCOMMON, mage.cards.b.BrassSecretary.class, RETRO_ART));
        cards.add(new SetCardInfo("Brine Seer", 28, Rarity.UNCOMMON, mage.cards.b.BrineSeer.class, RETRO_ART));
        cards.add(new SetCardInfo("Bubbling Beebles", 29, Rarity.COMMON, mage.cards.b.BubblingBeebles.class, RETRO_ART));
        cards.add(new SetCardInfo("Bubbling Muck", 54, Rarity.COMMON, mage.cards.b.BubblingMuck.class, RETRO_ART));
        cards.add(new SetCardInfo("Caltrops", 129, Rarity.UNCOMMON, mage.cards.c.Caltrops.class, RETRO_ART));
        cards.add(new SetCardInfo("Capashen Knight", 3, Rarity.COMMON, mage.cards.c.CapashenKnight.class, RETRO_ART));
        cards.add(new SetCardInfo("Capashen Standard", 4, Rarity.COMMON, mage.cards.c.CapashenStandard.class, RETRO_ART));
        cards.add(new SetCardInfo("Capashen Templar", 5, Rarity.COMMON, mage.cards.c.CapashenTemplar.class, RETRO_ART));
        cards.add(new SetCardInfo("Carnival of Souls", 55, Rarity.RARE, mage.cards.c.CarnivalOfSouls.class, RETRO_ART));
        cards.add(new SetCardInfo("Chime of Night", 56, Rarity.COMMON, mage.cards.c.ChimeOfNight.class, RETRO_ART));
        cards.add(new SetCardInfo("Cinder Seer", 78, Rarity.UNCOMMON, mage.cards.c.CinderSeer.class, RETRO_ART));
        cards.add(new SetCardInfo("Colos Yearling", 79, Rarity.COMMON, mage.cards.c.ColosYearling.class, RETRO_ART));
        cards.add(new SetCardInfo("Compost", 102, Rarity.UNCOMMON, mage.cards.c.Compost.class, RETRO_ART));
        cards.add(new SetCardInfo("Covetous Dragon", 80, Rarity.RARE, mage.cards.c.CovetousDragon.class, RETRO_ART));
        cards.add(new SetCardInfo("Disappear", 30, Rarity.UNCOMMON, mage.cards.d.Disappear.class, RETRO_ART));
        cards.add(new SetCardInfo("Disease Carriers", 57, Rarity.COMMON, mage.cards.d.DiseaseCarriers.class, RETRO_ART));
        cards.add(new SetCardInfo("Donate", 31, Rarity.RARE, mage.cards.d.Donate.class, RETRO_ART));
        cards.add(new SetCardInfo("Dying Wail", 58, Rarity.COMMON, mage.cards.d.DyingWail.class, RETRO_ART));
        cards.add(new SetCardInfo("Elvish Lookout", 103, Rarity.COMMON, mage.cards.e.ElvishLookout.class, RETRO_ART));
        cards.add(new SetCardInfo("Elvish Piper", 104, Rarity.RARE, mage.cards.e.ElvishPiper.class, RETRO_ART));
        cards.add(new SetCardInfo("Emperor Crocodile", 105, Rarity.RARE, mage.cards.e.EmperorCrocodile.class, RETRO_ART));
        cards.add(new SetCardInfo("Encroach", 59, Rarity.UNCOMMON, mage.cards.e.Encroach.class, RETRO_ART));
        cards.add(new SetCardInfo("Eradicate", 60, Rarity.UNCOMMON, mage.cards.e.Eradicate.class, RETRO_ART));
        cards.add(new SetCardInfo("Extruder", 130, Rarity.UNCOMMON, mage.cards.e.Extruder.class, RETRO_ART));
        cards.add(new SetCardInfo("False Prophet", 6, Rarity.RARE, mage.cards.f.FalseProphet.class, RETRO_ART));
        cards.add(new SetCardInfo("Fatigue", 32, Rarity.COMMON, mage.cards.f.Fatigue.class, RETRO_ART));
        cards.add(new SetCardInfo("Fend Off", 7, Rarity.COMMON, mage.cards.f.FendOff.class, RETRO_ART));
        cards.add(new SetCardInfo("Festering Wound", 61, Rarity.UNCOMMON, mage.cards.f.FesteringWound.class, RETRO_ART));
        cards.add(new SetCardInfo("Field Surgeon", 8, Rarity.COMMON, mage.cards.f.FieldSurgeon.class, RETRO_ART));
        cards.add(new SetCardInfo("Flame Jet", 81, Rarity.COMMON, mage.cards.f.FlameJet.class, RETRO_ART));
        cards.add(new SetCardInfo("Fledgling Osprey", 33, Rarity.COMMON, mage.cards.f.FledglingOsprey.class, RETRO_ART));
        cards.add(new SetCardInfo("Flicker", 9, Rarity.RARE, mage.cards.f.Flicker.class, RETRO_ART));
        cards.add(new SetCardInfo("Fodder Cannon", 131, Rarity.UNCOMMON, mage.cards.f.FodderCannon.class, RETRO_ART));
        cards.add(new SetCardInfo("Gamekeeper", 106, Rarity.UNCOMMON, mage.cards.g.Gamekeeper.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Berserker", 82, Rarity.UNCOMMON, mage.cards.g.GoblinBerserker.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Festival", 83, Rarity.RARE, mage.cards.g.GoblinFestival.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Gardener", 84, Rarity.COMMON, mage.cards.g.GoblinGardener.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Marshal", 85, Rarity.RARE, mage.cards.g.GoblinMarshal.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Masons", 86, Rarity.COMMON, mage.cards.g.GoblinMasons.class, RETRO_ART));
        cards.add(new SetCardInfo("Goliath Beetle", 107, Rarity.COMMON, mage.cards.g.GoliathBeetle.class, RETRO_ART));
        cards.add(new SetCardInfo("Heart Warden", 108, Rarity.COMMON, mage.cards.h.HeartWarden.class, RETRO_ART));
        cards.add(new SetCardInfo("Hulking Ogre", 87, Rarity.COMMON, mage.cards.h.HulkingOgre.class, RETRO_ART));
        cards.add(new SetCardInfo("Hunting Moa", 109, Rarity.UNCOMMON, mage.cards.h.HuntingMoa.class, RETRO_ART));
        cards.add(new SetCardInfo("Illuminated Wings", 34, Rarity.COMMON, mage.cards.i.IlluminatedWings.class, RETRO_ART));
        cards.add(new SetCardInfo("Impatience", 88, Rarity.RARE, mage.cards.i.Impatience.class, RETRO_ART));
        cards.add(new SetCardInfo("Incendiary", 89, Rarity.UNCOMMON, mage.cards.i.Incendiary.class, RETRO_ART));
        cards.add(new SetCardInfo("Iridescent Drake", 35, Rarity.UNCOMMON, mage.cards.i.IridescentDrake.class, RETRO_ART));
        cards.add(new SetCardInfo("Ivy Seer", 110, Rarity.UNCOMMON, mage.cards.i.IvySeer.class, RETRO_ART));
        cards.add(new SetCardInfo("Jasmine Seer", 10, Rarity.UNCOMMON, mage.cards.j.JasmineSeer.class, RETRO_ART));
        cards.add(new SetCardInfo("Junk Diver", 132, Rarity.RARE, mage.cards.j.JunkDiver.class, RETRO_ART));
        cards.add(new SetCardInfo("Keldon Champion", 90, Rarity.UNCOMMON, mage.cards.k.KeldonChampion.class, RETRO_ART));
        cards.add(new SetCardInfo("Keldon Vandals", 91, Rarity.COMMON, mage.cards.k.KeldonVandals.class, RETRO_ART));
        cards.add(new SetCardInfo("Kingfisher", 36, Rarity.COMMON, mage.cards.k.Kingfisher.class, RETRO_ART));
        cards.add(new SetCardInfo("Landslide", 92, Rarity.UNCOMMON, mage.cards.l.Landslide.class, RETRO_ART));
        cards.add(new SetCardInfo("Lurking Jackals", 62, Rarity.UNCOMMON, mage.cards.l.LurkingJackals.class, RETRO_ART));
        cards.add(new SetCardInfo("Magnify", 111, Rarity.COMMON, mage.cards.m.Magnify.class, RETRO_ART));
        cards.add(new SetCardInfo("Mantis Engine", 133, Rarity.UNCOMMON, mage.cards.m.MantisEngine.class, RETRO_ART));
        cards.add(new SetCardInfo("Mark of Fury", 93, Rarity.COMMON, mage.cards.m.MarkOfFury.class, RETRO_ART));
        cards.add(new SetCardInfo("Marker Beetles", 112, Rarity.COMMON, mage.cards.m.MarkerBeetles.class, RETRO_ART));
        cards.add(new SetCardInfo("Mask of Law and Grace", 11, Rarity.COMMON, mage.cards.m.MaskOfLawAndGrace.class, RETRO_ART));
        cards.add(new SetCardInfo("Master Healer", 12, Rarity.RARE, mage.cards.m.MasterHealer.class, RETRO_ART));
        cards.add(new SetCardInfo("Masticore", 134, Rarity.RARE, mage.cards.m.Masticore.class, RETRO_ART));
        cards.add(new SetCardInfo("Mental Discipline", 37, Rarity.COMMON, mage.cards.m.MentalDiscipline.class, RETRO_ART));
        cards.add(new SetCardInfo("Metalworker", 135, Rarity.RARE, mage.cards.m.Metalworker.class, RETRO_ART));
        cards.add(new SetCardInfo("Metathran Elite", 38, Rarity.UNCOMMON, mage.cards.m.MetathranElite.class, RETRO_ART));
        cards.add(new SetCardInfo("Metathran Soldier", 39, Rarity.COMMON, mage.cards.m.MetathranSoldier.class, RETRO_ART));
        cards.add(new SetCardInfo("Momentum", 113, Rarity.UNCOMMON, mage.cards.m.Momentum.class, RETRO_ART));
        cards.add(new SetCardInfo("Multani's Decree", 114, Rarity.COMMON, mage.cards.m.MultanisDecree.class, RETRO_ART));
        cards.add(new SetCardInfo("Nightshade Seer", 63, Rarity.UNCOMMON, mage.cards.n.NightshadeSeer.class, RETRO_ART));
        cards.add(new SetCardInfo("Opalescence", 13, Rarity.RARE, mage.cards.o.Opalescence.class, RETRO_ART));
        cards.add(new SetCardInfo("Opposition", 40, Rarity.RARE, mage.cards.o.Opposition.class, RETRO_ART));
        cards.add(new SetCardInfo("Pattern of Rebirth", 115, Rarity.RARE, mage.cards.p.PatternOfRebirth.class, RETRO_ART));
        cards.add(new SetCardInfo("Phyrexian Monitor", 64, Rarity.COMMON, mage.cards.p.PhyrexianMonitor.class, RETRO_ART));
        cards.add(new SetCardInfo("Phyrexian Negator", 65, Rarity.RARE, mage.cards.p.PhyrexianNegator.class, RETRO_ART));
        cards.add(new SetCardInfo("Plague Dogs", 66, Rarity.UNCOMMON, mage.cards.p.PlagueDogs.class, RETRO_ART));
        cards.add(new SetCardInfo("Plated Spider", 116, Rarity.COMMON, mage.cards.p.PlatedSpider.class, RETRO_ART));
        cards.add(new SetCardInfo("Plow Under", 117, Rarity.RARE, mage.cards.p.PlowUnder.class, RETRO_ART));
        cards.add(new SetCardInfo("Powder Keg", 136, Rarity.RARE, mage.cards.p.PowderKeg.class, RETRO_ART));
        cards.add(new SetCardInfo("Private Research", 41, Rarity.UNCOMMON, mage.cards.p.PrivateResearch.class, RETRO_ART));
        cards.add(new SetCardInfo("Quash", 42, Rarity.UNCOMMON, mage.cards.q.Quash.class, RETRO_ART));
        cards.add(new SetCardInfo("Rapid Decay", 67, Rarity.RARE, mage.cards.r.RapidDecay.class, RETRO_ART));
        cards.add(new SetCardInfo("Ravenous Rats", 68, Rarity.COMMON, mage.cards.r.RavenousRats.class, RETRO_ART));
        cards.add(new SetCardInfo("Rayne, Academy Chancellor", 43, Rarity.RARE, mage.cards.r.RayneAcademyChancellor.class, RETRO_ART));
        cards.add(new SetCardInfo("Reckless Abandon", 94, Rarity.COMMON, mage.cards.r.RecklessAbandon.class, RETRO_ART));
        cards.add(new SetCardInfo("Reliquary Monk", 14, Rarity.COMMON, mage.cards.r.ReliquaryMonk.class, RETRO_ART));
        cards.add(new SetCardInfo("Repercussion", 95, Rarity.RARE, mage.cards.r.Repercussion.class, RETRO_ART));
        cards.add(new SetCardInfo("Replenish", 15, Rarity.RARE, mage.cards.r.Replenish.class, RETRO_ART));
        cards.add(new SetCardInfo("Rescue", 44, Rarity.COMMON, mage.cards.r.Rescue.class, RETRO_ART));
        cards.add(new SetCardInfo("Rofellos's Gift", 119, Rarity.COMMON, mage.cards.r.RofellossGift.class, RETRO_ART));
        cards.add(new SetCardInfo("Rofellos, Llanowar Emissary", 118, Rarity.RARE, mage.cards.r.RofellosLlanowarEmissary.class, RETRO_ART));
        cards.add(new SetCardInfo("Sanctimony", 16, Rarity.UNCOMMON, mage.cards.s.Sanctimony.class, RETRO_ART));
        cards.add(new SetCardInfo("Scent of Brine", 45, Rarity.COMMON, mage.cards.s.ScentOfBrine.class, RETRO_ART));
        cards.add(new SetCardInfo("Scent of Cinder", 96, Rarity.COMMON, mage.cards.s.ScentOfCinder.class, RETRO_ART));
        cards.add(new SetCardInfo("Scent of Ivy", 120, Rarity.COMMON, mage.cards.s.ScentOfIvy.class, RETRO_ART));
        cards.add(new SetCardInfo("Scent of Jasmine", 17, Rarity.COMMON, mage.cards.s.ScentOfJasmine.class, RETRO_ART));
        cards.add(new SetCardInfo("Scent of Nightshade", 69, Rarity.COMMON, mage.cards.s.ScentOfNightshade.class, RETRO_ART));
        cards.add(new SetCardInfo("Scour", 18, Rarity.UNCOMMON, mage.cards.s.Scour.class, RETRO_ART));
        cards.add(new SetCardInfo("Scrying Glass", 137, Rarity.RARE, mage.cards.s.ScryingGlass.class, RETRO_ART));
        cards.add(new SetCardInfo("Serra Advocate", 19, Rarity.UNCOMMON, mage.cards.s.SerraAdvocate.class, RETRO_ART));
        cards.add(new SetCardInfo("Sigil of Sleep", 46, Rarity.COMMON, mage.cards.s.SigilOfSleep.class, RETRO_ART));
        cards.add(new SetCardInfo("Skittering Horror", 70, Rarity.COMMON, mage.cards.s.SkitteringHorror.class, RETRO_ART));
        cards.add(new SetCardInfo("Slinking Skirge", 71, Rarity.COMMON, mage.cards.s.SlinkingSkirge.class, RETRO_ART));
        cards.add(new SetCardInfo("Solidarity", 20, Rarity.COMMON, mage.cards.s.Solidarity.class, RETRO_ART));
        cards.add(new SetCardInfo("Soul Feast", 72, Rarity.UNCOMMON, mage.cards.s.SoulFeast.class, RETRO_ART));
        cards.add(new SetCardInfo("Sowing Salt", 97, Rarity.UNCOMMON, mage.cards.s.SowingSalt.class, RETRO_ART));
        cards.add(new SetCardInfo("Splinter", 121, Rarity.UNCOMMON, mage.cards.s.Splinter.class, RETRO_ART));
        cards.add(new SetCardInfo("Squirming Mass", 73, Rarity.COMMON, mage.cards.s.SquirmingMass.class, RETRO_ART));
        cards.add(new SetCardInfo("Storage Matrix", 138, Rarity.RARE, mage.cards.s.StorageMatrix.class, RETRO_ART));
        cards.add(new SetCardInfo("Taunting Elf", 122, Rarity.COMMON, mage.cards.t.TauntingElf.class, RETRO_ART));
        cards.add(new SetCardInfo("Telepathic Spies", 47, Rarity.COMMON, mage.cards.t.TelepathicSpies.class, RETRO_ART));
        cards.add(new SetCardInfo("Temporal Adept", 48, Rarity.RARE, mage.cards.t.TemporalAdept.class, RETRO_ART));
        cards.add(new SetCardInfo("Tethered Griffin", 21, Rarity.RARE, mage.cards.t.TetheredGriffin.class, RETRO_ART));
        cards.add(new SetCardInfo("Thieving Magpie", 49, Rarity.UNCOMMON, mage.cards.t.ThievingMagpie.class, RETRO_ART));
        cards.add(new SetCardInfo("Thorn Elemental", 123, Rarity.RARE, mage.cards.t.ThornElemental.class, RETRO_ART));
        cards.add(new SetCardInfo("Thran Dynamo", 139, Rarity.UNCOMMON, mage.cards.t.ThranDynamo.class, RETRO_ART));
        cards.add(new SetCardInfo("Thran Foundry", 140, Rarity.UNCOMMON, mage.cards.t.ThranFoundry.class, RETRO_ART));
        cards.add(new SetCardInfo("Thran Golem", 141, Rarity.RARE, mage.cards.t.ThranGolem.class, RETRO_ART));
        cards.add(new SetCardInfo("Tormented Angel", 22, Rarity.COMMON, mage.cards.t.TormentedAngel.class, RETRO_ART));
        cards.add(new SetCardInfo("Treachery", 50, Rarity.RARE, mage.cards.t.Treachery.class, RETRO_ART));
        cards.add(new SetCardInfo("Trumpet Blast", 98, Rarity.COMMON, mage.cards.t.TrumpetBlast.class, RETRO_ART));
        cards.add(new SetCardInfo("Twisted Experiment", 74, Rarity.COMMON, mage.cards.t.TwistedExperiment.class, RETRO_ART));
        cards.add(new SetCardInfo("Urza's Incubator", 142, Rarity.RARE, mage.cards.u.UrzasIncubator.class, RETRO_ART));
        cards.add(new SetCardInfo("Voice of Duty", 23, Rarity.UNCOMMON, mage.cards.v.VoiceOfDuty.class, RETRO_ART));
        cards.add(new SetCardInfo("Voice of Reason", 24, Rarity.UNCOMMON, mage.cards.v.VoiceOfReason.class, RETRO_ART));
        cards.add(new SetCardInfo("Wake of Destruction", 99, Rarity.RARE, mage.cards.w.WakeOfDestruction.class, RETRO_ART));
        cards.add(new SetCardInfo("Wall of Glare", 25, Rarity.COMMON, mage.cards.w.WallOfGlare.class, RETRO_ART));
        cards.add(new SetCardInfo("Wild Colos", 100, Rarity.COMMON, mage.cards.w.WildColos.class, RETRO_ART));
        cards.add(new SetCardInfo("Yavimaya Elder", 124, Rarity.COMMON, mage.cards.y.YavimayaElder.class, RETRO_ART));
        cards.add(new SetCardInfo("Yavimaya Enchantress", 125, Rarity.UNCOMMON, mage.cards.y.YavimayaEnchantress.class, RETRO_ART));
        cards.add(new SetCardInfo("Yavimaya Hollow", 143, Rarity.RARE, mage.cards.y.YavimayaHollow.class, RETRO_ART));
        cards.add(new SetCardInfo("Yawgmoth's Bargain", 75, Rarity.RARE, mage.cards.y.YawgmothsBargain.class, RETRO_ART));
    }
}
