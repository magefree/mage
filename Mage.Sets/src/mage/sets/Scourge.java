
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;

/**
 *
 * @author North
 */
public final class Scourge extends ExpansionSet {

    private static final Scourge instance = new Scourge();

    public static Scourge getInstance() {
        return instance;
    }

    private Scourge() {
        super("Scourge", "SCG", ExpansionSet.buildDate(2003, 5, 17), SetType.EXPANSION);
        this.blockName = "Onslaught";
        this.parentSet = Onslaught.getInstance();
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;

        cards.add(new SetCardInfo("Accelerated Mutation", 109, Rarity.COMMON, mage.cards.a.AcceleratedMutation.class, RETRO_ART));
        cards.add(new SetCardInfo("Ageless Sentinels", 1, Rarity.RARE, mage.cards.a.AgelessSentinels.class, RETRO_ART));
        cards.add(new SetCardInfo("Alpha Status", 110, Rarity.UNCOMMON, mage.cards.a.AlphaStatus.class, RETRO_ART));
        cards.add(new SetCardInfo("Ambush Commander", 111, Rarity.RARE, mage.cards.a.AmbushCommander.class, RETRO_ART));
        cards.add(new SetCardInfo("Ancient Ooze", 112, Rarity.RARE, mage.cards.a.AncientOoze.class, RETRO_ART));
        cards.add(new SetCardInfo("Aphetto Runecaster", 28, Rarity.UNCOMMON, mage.cards.a.AphettoRunecaster.class, RETRO_ART));
        cards.add(new SetCardInfo("Ark of Blight", 140, Rarity.UNCOMMON, mage.cards.a.ArkOfBlight.class, RETRO_ART));
        cards.add(new SetCardInfo("Astral Steel", 2, Rarity.COMMON, mage.cards.a.AstralSteel.class, RETRO_ART));
        cards.add(new SetCardInfo("Aven Farseer", 3, Rarity.COMMON, mage.cards.a.AvenFarseer.class, RETRO_ART));
        cards.add(new SetCardInfo("Aven Liberator", 4, Rarity.COMMON, mage.cards.a.AvenLiberator.class, RETRO_ART));
        cards.add(new SetCardInfo("Bladewing the Risen", 136, Rarity.RARE, mage.cards.b.BladewingTheRisen.class, RETRO_ART));
        cards.add(new SetCardInfo("Bladewing's Thrall", 55, Rarity.UNCOMMON, mage.cards.b.BladewingsThrall.class, RETRO_ART));
        cards.add(new SetCardInfo("Bonethorn Valesk", 82, Rarity.COMMON, mage.cards.b.BonethornValesk.class, RETRO_ART));
        cards.add(new SetCardInfo("Brain Freeze", 29, Rarity.UNCOMMON, mage.cards.b.BrainFreeze.class, RETRO_ART));
        cards.add(new SetCardInfo("Break Asunder", 113, Rarity.COMMON, mage.cards.b.BreakAsunder.class, RETRO_ART));
        cards.add(new SetCardInfo("Cabal Conditioning", 56, Rarity.RARE, mage.cards.c.CabalConditioning.class, RETRO_ART));
        cards.add(new SetCardInfo("Cabal Interrogator", 57, Rarity.UNCOMMON, mage.cards.c.CabalInterrogator.class, RETRO_ART));
        cards.add(new SetCardInfo("Call to the Grave", 58, Rarity.RARE, mage.cards.c.CallToTheGrave.class, RETRO_ART));
        cards.add(new SetCardInfo("Carbonize", 83, Rarity.UNCOMMON, mage.cards.c.Carbonize.class, RETRO_ART));
        cards.add(new SetCardInfo("Carrion Feeder", 59, Rarity.COMMON, mage.cards.c.CarrionFeeder.class, RETRO_ART));
        cards.add(new SetCardInfo("Chartooth Cougar", 84, Rarity.COMMON, mage.cards.c.ChartoothCougar.class, RETRO_ART));
        cards.add(new SetCardInfo("Chill Haunting", 60, Rarity.UNCOMMON, mage.cards.c.ChillHaunting.class, RETRO_ART));
        cards.add(new SetCardInfo("Claws of Wirewood", 114, Rarity.UNCOMMON, mage.cards.c.ClawsOfWirewood.class, RETRO_ART));
        cards.add(new SetCardInfo("Clutch of Undeath", 61, Rarity.COMMON, mage.cards.c.ClutchOfUndeath.class, RETRO_ART));
        cards.add(new SetCardInfo("Coast Watcher", 30, Rarity.COMMON, mage.cards.c.CoastWatcher.class, RETRO_ART));
        cards.add(new SetCardInfo("Consumptive Goo", 62, Rarity.RARE, mage.cards.c.ConsumptiveGoo.class, RETRO_ART));
        cards.add(new SetCardInfo("Daru Spiritualist", 5, Rarity.COMMON, mage.cards.d.DaruSpiritualist.class, RETRO_ART));
        cards.add(new SetCardInfo("Daru Warchief", 6, Rarity.UNCOMMON, mage.cards.d.DaruWarchief.class, RETRO_ART));
        cards.add(new SetCardInfo("Dawn Elemental", 7, Rarity.RARE, mage.cards.d.DawnElemental.class, RETRO_ART));
        cards.add(new SetCardInfo("Day of the Dragons", 31, Rarity.RARE, mage.cards.d.DayOfTheDragons.class, RETRO_ART));
        cards.add(new SetCardInfo("Death's-Head Buzzard", 63, Rarity.COMMON, mage.cards.d.DeathsHeadBuzzard.class, RETRO_ART));
        cards.add(new SetCardInfo("Decree of Annihilation", 85, Rarity.RARE, mage.cards.d.DecreeOfAnnihilation.class, RETRO_ART));
        cards.add(new SetCardInfo("Decree of Justice", 8, Rarity.RARE, mage.cards.d.DecreeOfJustice.class, RETRO_ART));
        cards.add(new SetCardInfo("Decree of Pain", 64, Rarity.RARE, mage.cards.d.DecreeOfPain.class, RETRO_ART));
        cards.add(new SetCardInfo("Decree of Savagery", 115, Rarity.RARE, mage.cards.d.DecreeOfSavagery.class, RETRO_ART));
        cards.add(new SetCardInfo("Decree of Silence", 32, Rarity.RARE, mage.cards.d.DecreeOfSilence.class, RETRO_ART));
        cards.add(new SetCardInfo("Dimensional Breach", 9, Rarity.RARE, mage.cards.d.DimensionalBreach.class, RETRO_ART));
        cards.add(new SetCardInfo("Dispersal Shield", 33, Rarity.COMMON, mage.cards.d.DispersalShield.class, RETRO_ART));
        cards.add(new SetCardInfo("Divergent Growth", 116, Rarity.COMMON, mage.cards.d.DivergentGrowth.class, RETRO_ART));
        cards.add(new SetCardInfo("Dragon Breath", 86, Rarity.COMMON, mage.cards.d.DragonBreath.class, RETRO_ART));
        cards.add(new SetCardInfo("Dragon Fangs", 117, Rarity.COMMON, mage.cards.d.DragonFangs.class, RETRO_ART));
        cards.add(new SetCardInfo("Dragon Mage", 87, Rarity.RARE, mage.cards.d.DragonMage.class, RETRO_ART));
        cards.add(new SetCardInfo("Dragon Scales", 10, Rarity.COMMON, mage.cards.d.DragonScales.class, RETRO_ART));
        cards.add(new SetCardInfo("Dragon Shadow", 65, Rarity.COMMON, mage.cards.d.DragonShadow.class, RETRO_ART));
        cards.add(new SetCardInfo("Dragon Tyrant", 88, Rarity.RARE, mage.cards.d.DragonTyrant.class, RETRO_ART));
        cards.add(new SetCardInfo("Dragon Wings", 34, Rarity.COMMON, mage.cards.d.DragonWings.class, RETRO_ART));
        cards.add(new SetCardInfo("Dragonspeaker Shaman", 89, Rarity.UNCOMMON, mage.cards.d.DragonspeakerShaman.class, RETRO_ART));
        cards.add(new SetCardInfo("Dragonstalker", 11, Rarity.UNCOMMON, mage.cards.d.Dragonstalker.class, RETRO_ART));
        cards.add(new SetCardInfo("Dragonstorm", 90, Rarity.RARE, mage.cards.d.Dragonstorm.class, RETRO_ART));
        cards.add(new SetCardInfo("Edgewalker", 137, Rarity.UNCOMMON, mage.cards.e.Edgewalker.class, RETRO_ART));
        cards.add(new SetCardInfo("Elvish Aberration", 118, Rarity.UNCOMMON, mage.cards.e.ElvishAberration.class, RETRO_ART));
        cards.add(new SetCardInfo("Enrage", 91, Rarity.UNCOMMON, mage.cards.e.Enrage.class, RETRO_ART));
        cards.add(new SetCardInfo("Eternal Dragon", 12, Rarity.RARE, mage.cards.e.EternalDragon.class, RETRO_ART));
        cards.add(new SetCardInfo("Exiled Doomsayer", 13, Rarity.RARE, mage.cards.e.ExiledDoomsayer.class, RETRO_ART));
        cards.add(new SetCardInfo("Extra Arms", 92, Rarity.UNCOMMON, mage.cards.e.ExtraArms.class, RETRO_ART));
        cards.add(new SetCardInfo("Faces of the Past", 35, Rarity.RARE, mage.cards.f.FacesOfThePast.class, RETRO_ART));
        cards.add(new SetCardInfo("Fatal Mutation", 66, Rarity.UNCOMMON, mage.cards.f.FatalMutation.class, RETRO_ART));
        cards.add(new SetCardInfo("Fierce Empath", 119, Rarity.COMMON, mage.cards.f.FierceEmpath.class, RETRO_ART));
        cards.add(new SetCardInfo("Final Punishment", 67, Rarity.RARE, mage.cards.f.FinalPunishment.class, RETRO_ART));
        cards.add(new SetCardInfo("Force Bubble", 14, Rarity.RARE, mage.cards.f.ForceBubble.class, RETRO_ART));
        cards.add(new SetCardInfo("Forgotten Ancient", 120, Rarity.RARE, mage.cards.f.ForgottenAncient.class, RETRO_ART));
        cards.add(new SetCardInfo("Form of the Dragon", 93, Rarity.RARE, mage.cards.f.FormOfTheDragon.class, RETRO_ART));
        cards.add(new SetCardInfo("Frontline Strategist", 15, Rarity.COMMON, mage.cards.f.FrontlineStrategist.class, RETRO_ART));
        cards.add(new SetCardInfo("Frozen Solid", 36, Rarity.COMMON, mage.cards.f.FrozenSolid.class, RETRO_ART));
        cards.add(new SetCardInfo("Gilded Light", 16, Rarity.UNCOMMON, mage.cards.g.GildedLight.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Brigand", 94, Rarity.COMMON, mage.cards.g.GoblinBrigand.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Psychopath", 95, Rarity.UNCOMMON, mage.cards.g.GoblinPsychopath.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin War Strike", 96, Rarity.COMMON, mage.cards.g.GoblinWarStrike.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Warchief", 97, Rarity.UNCOMMON, mage.cards.g.GoblinWarchief.class, RETRO_ART));
        cards.add(new SetCardInfo("Grip of Chaos", 98, Rarity.RARE, mage.cards.g.GripOfChaos.class, RETRO_ART));
        cards.add(new SetCardInfo("Guilty Conscience", 17, Rarity.COMMON, mage.cards.g.GuiltyConscience.class, RETRO_ART));
        cards.add(new SetCardInfo("Hindering Touch", 37, Rarity.COMMON, mage.cards.h.HinderingTouch.class, RETRO_ART));
        cards.add(new SetCardInfo("Hunting Pack", 121, Rarity.UNCOMMON, mage.cards.h.HuntingPack.class, RETRO_ART));
        cards.add(new SetCardInfo("Karona's Zealot", 18, Rarity.UNCOMMON, mage.cards.k.KaronasZealot.class, RETRO_ART));
        cards.add(new SetCardInfo("Karona, False God", 138, Rarity.RARE, mage.cards.k.KaronaFalseGod.class, RETRO_ART));
        cards.add(new SetCardInfo("Krosan Drover", 122, Rarity.COMMON, mage.cards.k.KrosanDrover.class, RETRO_ART));
        cards.add(new SetCardInfo("Krosan Warchief", 123, Rarity.UNCOMMON, mage.cards.k.KrosanWarchief.class, RETRO_ART));
        cards.add(new SetCardInfo("Kurgadon", 124, Rarity.UNCOMMON, mage.cards.k.Kurgadon.class, RETRO_ART));
        cards.add(new SetCardInfo("Lethal Vapors", 68, Rarity.RARE, mage.cards.l.LethalVapors.class, RETRO_ART));
        cards.add(new SetCardInfo("Lingering Death", 69, Rarity.COMMON, mage.cards.l.LingeringDeath.class, RETRO_ART));
        cards.add(new SetCardInfo("Long-Term Plans", 38, Rarity.UNCOMMON, mage.cards.l.LongTermPlans.class, RETRO_ART));
        cards.add(new SetCardInfo("Mercurial Kite", 39, Rarity.COMMON, mage.cards.m.MercurialKite.class, RETRO_ART));
        cards.add(new SetCardInfo("Metamorphose", 40, Rarity.UNCOMMON, mage.cards.m.Metamorphose.class, RETRO_ART));
        cards.add(new SetCardInfo("Mind's Desire", 41, Rarity.RARE, mage.cards.m.MindsDesire.class, RETRO_ART));
        cards.add(new SetCardInfo("Mischievous Quanar", 42, Rarity.RARE, mage.cards.m.MischievousQuanar.class, RETRO_ART));
        cards.add(new SetCardInfo("Misguided Rage", 99, Rarity.COMMON, mage.cards.m.MisguidedRage.class, RETRO_ART));
        cards.add(new SetCardInfo("Mistform Warchief", 43, Rarity.UNCOMMON, mage.cards.m.MistformWarchief.class, RETRO_ART));
        cards.add(new SetCardInfo("Nefashu", 70, Rarity.RARE, mage.cards.n.Nefashu.class, RETRO_ART));
        cards.add(new SetCardInfo("Noble Templar", 19, Rarity.COMMON, mage.cards.n.NobleTemplar.class, RETRO_ART));
        cards.add(new SetCardInfo("One with Nature", 125, Rarity.UNCOMMON, mage.cards.o.OneWithNature.class, RETRO_ART));
        cards.add(new SetCardInfo("Parallel Thoughts", 44, Rarity.RARE, mage.cards.p.ParallelThoughts.class, RETRO_ART));
        cards.add(new SetCardInfo("Pemmin's Aura", 45, Rarity.UNCOMMON, mage.cards.p.PemminsAura.class, RETRO_ART));
        cards.add(new SetCardInfo("Primitive Etchings", 126, Rarity.RARE, mage.cards.p.PrimitiveEtchings.class, RETRO_ART));
        cards.add(new SetCardInfo("Proteus Machine", 141, Rarity.UNCOMMON, mage.cards.p.ProteusMachine.class, RETRO_ART));
        cards.add(new SetCardInfo("Putrid Raptor", 71, Rarity.UNCOMMON, mage.cards.p.PutridRaptor.class, RETRO_ART));
        cards.add(new SetCardInfo("Pyrostatic Pillar", 100, Rarity.UNCOMMON, mage.cards.p.PyrostaticPillar.class, RETRO_ART));
        cards.add(new SetCardInfo("Rain of Blades", 20, Rarity.UNCOMMON, mage.cards.r.RainOfBlades.class, RETRO_ART));
        cards.add(new SetCardInfo("Raven Guild Initiate", 46, Rarity.COMMON, mage.cards.r.RavenGuildInitiate.class, RETRO_ART));
        cards.add(new SetCardInfo("Raven Guild Master", 47, Rarity.RARE, mage.cards.r.RavenGuildMaster.class, RETRO_ART));
        cards.add(new SetCardInfo("Reaping the Graves", 72, Rarity.COMMON, mage.cards.r.ReapingTheGraves.class, RETRO_ART));
        cards.add(new SetCardInfo("Recuperate", 21, Rarity.COMMON, mage.cards.r.Recuperate.class, RETRO_ART));
        cards.add(new SetCardInfo("Reward the Faithful", 22, Rarity.UNCOMMON, mage.cards.r.RewardTheFaithful.class, RETRO_ART));
        cards.add(new SetCardInfo("Riptide Survivor", 48, Rarity.UNCOMMON, mage.cards.r.RiptideSurvivor.class, RETRO_ART));
        cards.add(new SetCardInfo("Rock Jockey", 101, Rarity.COMMON, mage.cards.r.RockJockey.class, RETRO_ART));
        cards.add(new SetCardInfo("Root Elemental", 127, Rarity.RARE, mage.cards.r.RootElemental.class, RETRO_ART));
        cards.add(new SetCardInfo("Rush of Knowledge", 49, Rarity.COMMON, mage.cards.r.RushOfKnowledge.class, RETRO_ART));
        cards.add(new SetCardInfo("Scattershot", 102, Rarity.COMMON, mage.cards.s.Scattershot.class, RETRO_ART));
        cards.add(new SetCardInfo("Scornful Egotist", 50, Rarity.COMMON, mage.cards.s.ScornfulEgotist.class, RETRO_ART));
        cards.add(new SetCardInfo("Shoreline Ranger", 51, Rarity.COMMON, mage.cards.s.ShorelineRanger.class, RETRO_ART));
        cards.add(new SetCardInfo("Siege-Gang Commander", 103, Rarity.RARE, mage.cards.s.SiegeGangCommander.class, RETRO_ART));
        cards.add(new SetCardInfo("Silver Knight", 23, Rarity.UNCOMMON, mage.cards.s.SilverKnight.class, RETRO_ART));
        cards.add(new SetCardInfo("Skirk Volcanist", 104, Rarity.UNCOMMON, mage.cards.s.SkirkVolcanist.class, RETRO_ART));
        cards.add(new SetCardInfo("Skulltap", 73, Rarity.COMMON, mage.cards.s.Skulltap.class, RETRO_ART));
        cards.add(new SetCardInfo("Sliver Overlord", 139, Rarity.RARE, mage.cards.s.SliverOverlord.class, RETRO_ART));
        cards.add(new SetCardInfo("Soul Collector", 74, Rarity.RARE, mage.cards.s.SoulCollector.class, RETRO_ART));
        cards.add(new SetCardInfo("Spark Spray", 105, Rarity.COMMON, mage.cards.s.SparkSpray.class, RETRO_ART));
        cards.add(new SetCardInfo("Sprouting Vines", 128, Rarity.COMMON, mage.cards.s.SproutingVines.class, RETRO_ART));
        cards.add(new SetCardInfo("Stabilizer", 142, Rarity.RARE, mage.cards.s.Stabilizer.class, RETRO_ART));
        cards.add(new SetCardInfo("Stifle", 52, Rarity.RARE, mage.cards.s.Stifle.class, RETRO_ART));
        cards.add(new SetCardInfo("Sulfuric Vortex", 106, Rarity.RARE, mage.cards.s.SulfuricVortex.class, RETRO_ART));
        cards.add(new SetCardInfo("Temple of the False God", 143, Rarity.UNCOMMON, mage.cards.t.TempleOfTheFalseGod.class, RETRO_ART));
        cards.add(new SetCardInfo("Temporal Fissure", 53, Rarity.COMMON, mage.cards.t.TemporalFissure.class, RETRO_ART));
        cards.add(new SetCardInfo("Tendrils of Agony", 75, Rarity.UNCOMMON, mage.cards.t.TendrilsOfAgony.class, RETRO_ART));
        cards.add(new SetCardInfo("Thundercloud Elemental", 54, Rarity.UNCOMMON, mage.cards.t.ThundercloudElemental.class, RETRO_ART));
        cards.add(new SetCardInfo("Titanic Bulvox", 129, Rarity.COMMON, mage.cards.t.TitanicBulvox.class, RETRO_ART));
        cards.add(new SetCardInfo("Torrent of Fire", 107, Rarity.COMMON, mage.cards.t.TorrentOfFire.class, RETRO_ART));
        cards.add(new SetCardInfo("Trap Digger", 24, Rarity.RARE, mage.cards.t.TrapDigger.class, RETRO_ART));
        cards.add(new SetCardInfo("Treetop Scout", 130, Rarity.COMMON, mage.cards.t.TreetopScout.class, RETRO_ART));
        cards.add(new SetCardInfo("Twisted Abomination", 76, Rarity.COMMON, mage.cards.t.TwistedAbomination.class, RETRO_ART));
        cards.add(new SetCardInfo("Unburden", 77, Rarity.COMMON, mage.cards.u.Unburden.class, RETRO_ART));
        cards.add(new SetCardInfo("Uncontrolled Infestation", 108, Rarity.COMMON, mage.cards.u.UncontrolledInfestation.class, RETRO_ART));
        cards.add(new SetCardInfo("Undead Warchief", 78, Rarity.UNCOMMON, mage.cards.u.UndeadWarchief.class, RETRO_ART));
        cards.add(new SetCardInfo("Unspeakable Symbol", 79, Rarity.UNCOMMON, mage.cards.u.UnspeakableSymbol.class, RETRO_ART));
        cards.add(new SetCardInfo("Upwelling", 131, Rarity.RARE, mage.cards.u.Upwelling.class, RETRO_ART));
        cards.add(new SetCardInfo("Vengeful Dead", 80, Rarity.COMMON, mage.cards.v.VengefulDead.class, RETRO_ART));
        cards.add(new SetCardInfo("Wing Shards", 25, Rarity.UNCOMMON, mage.cards.w.WingShards.class, RETRO_ART));
        cards.add(new SetCardInfo("Wipe Clean", 26, Rarity.COMMON, mage.cards.w.WipeClean.class, RETRO_ART));
        cards.add(new SetCardInfo("Wirewood Guardian", 132, Rarity.COMMON, mage.cards.w.WirewoodGuardian.class, RETRO_ART));
        cards.add(new SetCardInfo("Wirewood Symbiote", 133, Rarity.UNCOMMON, mage.cards.w.WirewoodSymbiote.class, RETRO_ART));
        cards.add(new SetCardInfo("Woodcloaker", 134, Rarity.COMMON, mage.cards.w.Woodcloaker.class, RETRO_ART));
        cards.add(new SetCardInfo("Xantid Swarm", 135, Rarity.RARE, mage.cards.x.XantidSwarm.class, RETRO_ART));
        cards.add(new SetCardInfo("Zealous Inquisitor", 27, Rarity.COMMON, mage.cards.z.ZealousInquisitor.class, RETRO_ART));
        cards.add(new SetCardInfo("Zombie Cutthroat", 81, Rarity.COMMON, mage.cards.z.ZombieCutthroat.class, RETRO_ART));
    }

    @Override
    public BoosterCollator createCollator() {
        return new ScourgeCollator();
    }
}

// Booster collation info from https://vm1.substation33.com/tiera/t/lethe/scg.html
class ScourgeCollator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "46", "77", "82", "10", "59", "113", "34", "105", "129", "5", "86", "53", "117", "82", "21", "39", "73", "50", "122", "15", "37", "72", "21", "108", "116", "53", "99", "10", "63", "105", "26", "113", "77", "96", "5", "65", "46", "134", "59", "17", "73", "116", "96", "39", "15", "129", "72", "34", "134", "86", "26", "108", "117", "63", "50", "99", "37", "17", "65", "122");
    private final CardRun commonB = new CardRun(true, "4", "69", "128", "49", "102", "27", "80", "132", "36", "84", "2", "61", "119", "33", "102", "3", "69", "130", "51", "94", "19", "81", "109", "49", "101", "4", "76", "128", "30", "107", "27", "81", "130", "36", "94", "2", "80", "119", "30", "101", "3", "61", "109", "51", "84", "19", "76", "132", "33", "107");
    private final CardRun uncommonA = new CardRun(true, "133", "137", "110", "95", "28", "140", "20", "100", "125", "16", "38", "60", "22", "29", "75", "110", "89", "28", "133", "66", "40", "95", "121", "137", "6", "79", "91", "140", "66", "95", "29", "125", "75", "22", "100", "110", "38", "89", "121", "28", "79", "137", "91", "40", "16", "60", "20", "6", "133", "140", "16", "100", "66", "28", "125", "22", "91", "20", "110", "29", "79", "38", "95", "133", "60", "89", "40", "137", "75", "6", "121", "140", "29", "91", "38", "66", "110", "16", "79", "125", "6", "89", "75", "133", "22", "28", "100", "121", "20", "137", "95", "60", "40", "140", "38", "100", "79", "22", "121", "29", "60", "91", "6", "66", "125", "20", "89", "40", "75", "16");
    private final CardRun uncommonB = new CardRun(true, "141", "57", "114", "97", "43", "23", "71", "83", "123", "143", "92", "18", "54", "78", "25", "124", "104", "48", "55", "45", "11", "118", "141", "48", "97", "18", "43", "78", "83", "118", "143", "71", "124", "54", "114", "11", "57", "25", "104", "45", "123", "92", "23", "55", "141", "123", "18", "71", "25", "114", "143", "55", "54", "97", "23", "78", "48", "124", "11", "92", "43", "83", "45", "118", "57", "104", "141", "124", "43", "57", "123", "48", "92", "45", "71", "114", "83", "143", "23", "54", "25", "97", "78", "118", "18", "43", "55", "11", "141", "54", "123", "55", "25", "83", "48", "143", "57", "92", "118", "23", "104", "78", "18", "45", "124", "104", "71", "114", "97", "11");
    private final CardRun rare = new CardRun(false, "1", "7", "8", "9", "12", "13", "14", "24", "31", "32", "35", "41", "42", "44", "47", "52", "56", "58", "62", "64", "67", "68", "70", "74", "85", "87", "88", "90", "93", "98", "103", "106", "111", "112", "115", "120", "126", "127", "131", "135", "136", "138", "139", "142");

    private final BoosterStructure AAAAAABBBBB = new BoosterStructure(
            commonA, commonA, commonA, commonA, commonA, commonA,
            commonB, commonB, commonB, commonB, commonB
    );

    private final BoosterStructure AAB = new BoosterStructure(
            uncommonA, uncommonA,
            uncommonB
    );
    private final BoosterStructure BBA = new BoosterStructure(
            uncommonB, uncommonB,
            uncommonA
    );

    private final BoosterStructure R1 = new BoosterStructure(rare);

    private final RarityConfiguration commonRuns = new RarityConfiguration(AAAAAABBBBB);

    private final RarityConfiguration uncommonRuns = new RarityConfiguration(
            AAB, BBA
    );

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
