
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

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
}
