
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author North
 */
public final class Nemesis extends ExpansionSet {

    private static final Nemesis instance = new Nemesis();

    public static Nemesis getInstance() {
        return instance;
    }

    private Nemesis() {
        super("Nemesis", "NEM", ExpansionSet.buildDate(2000, 1, 5), SetType.EXPANSION);
        this.blockName = "Masques";
        this.parentSet = MercadianMasques.getInstance();
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;

        cards.add(new SetCardInfo("Accumulated Knowledge", 26, Rarity.COMMON, mage.cards.a.AccumulatedKnowledge.class, RETRO_ART));
        cards.add(new SetCardInfo("Aether Barrier", 27, Rarity.RARE, mage.cards.a.AetherBarrier.class, RETRO_ART));
        cards.add(new SetCardInfo("Air Bladder", 28, Rarity.COMMON, mage.cards.a.AirBladder.class, RETRO_ART));
        cards.add(new SetCardInfo("Ancient Hydra", 76, Rarity.UNCOMMON, mage.cards.a.AncientHydra.class, RETRO_ART));
        cards.add(new SetCardInfo("Angelic Favor", 1, Rarity.UNCOMMON, mage.cards.a.AngelicFavor.class, RETRO_ART));
        cards.add(new SetCardInfo("Animate Land", 101, Rarity.UNCOMMON, mage.cards.a.AnimateLand.class, RETRO_ART));
        cards.add(new SetCardInfo("Arc Mage", 77, Rarity.UNCOMMON, mage.cards.a.ArcMage.class, RETRO_ART));
        cards.add(new SetCardInfo("Ascendant Evincar", 51, Rarity.RARE, mage.cards.a.AscendantEvincar.class, RETRO_ART));
        cards.add(new SetCardInfo("Avenger en-Dal", 2, Rarity.RARE, mage.cards.a.AvengerEnDal.class, RETRO_ART));
        cards.add(new SetCardInfo("Battlefield Percher", 52, Rarity.UNCOMMON, mage.cards.b.BattlefieldPercher.class, RETRO_ART));
        cards.add(new SetCardInfo("Belbe's Armor", 126, Rarity.UNCOMMON, mage.cards.b.BelbesArmor.class, RETRO_ART));
        cards.add(new SetCardInfo("Belbe's Percher", 53, Rarity.COMMON, mage.cards.b.BelbesPercher.class, RETRO_ART));
        cards.add(new SetCardInfo("Belbe's Portal", 127, Rarity.RARE, mage.cards.b.BelbesPortal.class, RETRO_ART));
        cards.add(new SetCardInfo("Blastoderm", 102, Rarity.COMMON, mage.cards.b.Blastoderm.class, RETRO_ART));
        cards.add(new SetCardInfo("Blinding Angel", 3, Rarity.RARE, mage.cards.b.BlindingAngel.class, RETRO_ART));
        cards.add(new SetCardInfo("Bola Warrior", 78, Rarity.COMMON, mage.cards.b.BolaWarrior.class, RETRO_ART));
        cards.add(new SetCardInfo("Carrion Wall", 54, Rarity.UNCOMMON, mage.cards.c.CarrionWall.class, RETRO_ART));
        cards.add(new SetCardInfo("Chieftain en-Dal", 4, Rarity.UNCOMMON, mage.cards.c.ChieftainEnDal.class, RETRO_ART));
        cards.add(new SetCardInfo("Cloudskate", 29, Rarity.COMMON, mage.cards.c.Cloudskate.class, RETRO_ART));
        cards.add(new SetCardInfo("Coiling Woodworm", 103, Rarity.UNCOMMON, mage.cards.c.CoilingWoodworm.class, RETRO_ART));
        cards.add(new SetCardInfo("Complex Automaton", 128, Rarity.RARE, mage.cards.c.ComplexAutomaton.class, RETRO_ART));
        cards.add(new SetCardInfo("Dark Triumph", 55, Rarity.UNCOMMON, mage.cards.d.DarkTriumph.class, RETRO_ART));
        cards.add(new SetCardInfo("Daze", 30, Rarity.COMMON, mage.cards.d.Daze.class, RETRO_ART));
        cards.add(new SetCardInfo("Death Pit Offering", 56, Rarity.RARE, mage.cards.d.DeathPitOffering.class, RETRO_ART));
        cards.add(new SetCardInfo("Defender en-Vec", 5, Rarity.COMMON, mage.cards.d.DefenderEnVec.class, RETRO_ART));
        cards.add(new SetCardInfo("Defiant Falcon", 6, Rarity.COMMON, mage.cards.d.DefiantFalcon.class, RETRO_ART));
        cards.add(new SetCardInfo("Defiant Vanguard", 7, Rarity.UNCOMMON, mage.cards.d.DefiantVanguard.class, RETRO_ART));
        cards.add(new SetCardInfo("Divining Witch", 57, Rarity.RARE, mage.cards.d.DiviningWitch.class, RETRO_ART));
        cards.add(new SetCardInfo("Dominate", 31, Rarity.UNCOMMON, mage.cards.d.Dominate.class, RETRO_ART));
        cards.add(new SetCardInfo("Downhill Charge", 79, Rarity.COMMON, mage.cards.d.DownhillCharge.class, RETRO_ART));
        cards.add(new SetCardInfo("Ensnare", 32, Rarity.UNCOMMON, mage.cards.e.Ensnare.class, RETRO_ART));
        cards.add(new SetCardInfo("Eye of Yawgmoth", 129, Rarity.RARE, mage.cards.e.EyeOfYawgmoth.class, RETRO_ART));
        cards.add(new SetCardInfo("Fanatical Devotion", 8, Rarity.COMMON, mage.cards.f.FanaticalDevotion.class, RETRO_ART));
        cards.add(new SetCardInfo("Flame Rift", 80, Rarity.COMMON, mage.cards.f.FlameRift.class, RETRO_ART));
        cards.add(new SetCardInfo("Flint Golem", 130, Rarity.UNCOMMON, mage.cards.f.FlintGolem.class, RETRO_ART));
        cards.add(new SetCardInfo("Flowstone Armor", 131, Rarity.UNCOMMON, mage.cards.f.FlowstoneArmor.class, RETRO_ART));
        cards.add(new SetCardInfo("Flowstone Crusher", 81, Rarity.COMMON, mage.cards.f.FlowstoneCrusher.class, RETRO_ART));
        cards.add(new SetCardInfo("Flowstone Overseer", 82, Rarity.RARE, mage.cards.f.FlowstoneOverseer.class, RETRO_ART));
        cards.add(new SetCardInfo("Flowstone Slide", 83, Rarity.RARE, mage.cards.f.FlowstoneSlide.class, RETRO_ART));
        cards.add(new SetCardInfo("Flowstone Strike", 84, Rarity.COMMON, mage.cards.f.FlowstoneStrike.class, RETRO_ART));
        cards.add(new SetCardInfo("Flowstone Surge", 85, Rarity.UNCOMMON, mage.cards.f.FlowstoneSurge.class, RETRO_ART));
        cards.add(new SetCardInfo("Flowstone Thopter", 132, Rarity.UNCOMMON, mage.cards.f.FlowstoneThopter.class, RETRO_ART));
        cards.add(new SetCardInfo("Flowstone Wall", 86, Rarity.COMMON, mage.cards.f.FlowstoneWall.class, RETRO_ART));
        cards.add(new SetCardInfo("Fog Patch", 104, Rarity.COMMON, mage.cards.f.FogPatch.class, RETRO_ART));
        cards.add(new SetCardInfo("Harvest Mage", 105, Rarity.COMMON, mage.cards.h.HarvestMage.class, RETRO_ART));
        cards.add(new SetCardInfo("Infiltrate", 33, Rarity.COMMON, mage.cards.i.Infiltrate.class, RETRO_ART));
        cards.add(new SetCardInfo("Jolting Merfolk", 34, Rarity.UNCOMMON, mage.cards.j.JoltingMerfolk.class, RETRO_ART));
        cards.add(new SetCardInfo("Kill Switch", 133, Rarity.RARE, mage.cards.k.KillSwitch.class, RETRO_ART));
        cards.add(new SetCardInfo("Kor Haven", 141, Rarity.RARE, mage.cards.k.KorHaven.class, RETRO_ART));
        cards.add(new SetCardInfo("Laccolith Grunt", 87, Rarity.COMMON, mage.cards.l.LaccolithGrunt.class, RETRO_ART));
        cards.add(new SetCardInfo("Laccolith Rig", 88, Rarity.COMMON, mage.cards.l.LaccolithRig.class, RETRO_ART));
        cards.add(new SetCardInfo("Laccolith Titan", 89, Rarity.RARE, mage.cards.l.LaccolithTitan.class, RETRO_ART));
        cards.add(new SetCardInfo("Laccolith Warrior", 90, Rarity.UNCOMMON, mage.cards.l.LaccolithWarrior.class, RETRO_ART));
        cards.add(new SetCardInfo("Laccolith Whelp", 91, Rarity.COMMON, mage.cards.l.LaccolithWhelp.class, RETRO_ART));
        cards.add(new SetCardInfo("Lashknife", 9, Rarity.COMMON, mage.cards.l.Lashknife.class, RETRO_ART));
        cards.add(new SetCardInfo("Lawbringer", 10, Rarity.COMMON, mage.cards.l.Lawbringer.class, RETRO_ART));
        cards.add(new SetCardInfo("Lightbringer", 11, Rarity.COMMON, mage.cards.l.Lightbringer.class, RETRO_ART));
        cards.add(new SetCardInfo("Lin Sivvi, Defiant Hero", 12, Rarity.RARE, mage.cards.l.LinSivviDefiantHero.class, RETRO_ART));
        cards.add(new SetCardInfo("Mana Cache", 92, Rarity.RARE, mage.cards.m.ManaCache.class, RETRO_ART));
        cards.add(new SetCardInfo("Massacre", 58, Rarity.UNCOMMON, mage.cards.m.Massacre.class, RETRO_ART));
        cards.add(new SetCardInfo("Mind Slash", 59, Rarity.UNCOMMON, mage.cards.m.MindSlash.class, RETRO_ART));
        cards.add(new SetCardInfo("Mind Swords", 60, Rarity.COMMON, mage.cards.m.MindSwords.class, RETRO_ART));
        cards.add(new SetCardInfo("Mogg Alarm", 93, Rarity.UNCOMMON, mage.cards.m.MoggAlarm.class, RETRO_ART));
        cards.add(new SetCardInfo("Mogg Salvage", 94, Rarity.UNCOMMON, mage.cards.m.MoggSalvage.class, RETRO_ART));
        cards.add(new SetCardInfo("Mogg Toady", 95, Rarity.COMMON, mage.cards.m.MoggToady.class, RETRO_ART));
        cards.add(new SetCardInfo("Moggcatcher", 96, Rarity.RARE, mage.cards.m.Moggcatcher.class, RETRO_ART));
        cards.add(new SetCardInfo("Mossdog", 106, Rarity.COMMON, mage.cards.m.Mossdog.class, RETRO_ART));
        cards.add(new SetCardInfo("Murderous Betrayal", 61, Rarity.RARE, mage.cards.m.MurderousBetrayal.class, RETRO_ART));
        cards.add(new SetCardInfo("Nesting Wurm", 107, Rarity.UNCOMMON, mage.cards.n.NestingWurm.class, RETRO_ART));
        cards.add(new SetCardInfo("Netter en-Dal", 13, Rarity.COMMON, mage.cards.n.NetterEnDal.class, RETRO_ART));
        cards.add(new SetCardInfo("Noble Stand", 14, Rarity.UNCOMMON, mage.cards.n.NobleStand.class, RETRO_ART));
        cards.add(new SetCardInfo("Off Balance", 15, Rarity.COMMON, mage.cards.o.OffBalance.class, RETRO_ART));
        cards.add(new SetCardInfo("Oracle's Attendants", 16, Rarity.RARE, mage.cards.o.OraclesAttendants.class, RETRO_ART));
        cards.add(new SetCardInfo("Oraxid", 35, Rarity.COMMON, mage.cards.o.Oraxid.class, RETRO_ART));
        cards.add(new SetCardInfo("Overlaid Terrain", 108, Rarity.RARE, mage.cards.o.OverlaidTerrain.class, RETRO_ART));
        cards.add(new SetCardInfo("Pack Hunt", 109, Rarity.RARE, mage.cards.p.PackHunt.class, RETRO_ART));
        cards.add(new SetCardInfo("Pale Moon", 36, Rarity.RARE, mage.cards.p.PaleMoon.class, RETRO_ART));
        cards.add(new SetCardInfo("Parallax Dementia", 62, Rarity.COMMON, mage.cards.p.ParallaxDementia.class, RETRO_ART));
        cards.add(new SetCardInfo("Parallax Inhibitor", 134, Rarity.RARE, mage.cards.p.ParallaxInhibitor.class, RETRO_ART));
        cards.add(new SetCardInfo("Parallax Nexus", 63, Rarity.RARE, mage.cards.p.ParallaxNexus.class, RETRO_ART));
        cards.add(new SetCardInfo("Parallax Tide", 37, Rarity.RARE, mage.cards.p.ParallaxTide.class, RETRO_ART));
        cards.add(new SetCardInfo("Parallax Wave", 17, Rarity.RARE, mage.cards.p.ParallaxWave.class, RETRO_ART));
        cards.add(new SetCardInfo("Phyrexian Driver", 64, Rarity.COMMON, mage.cards.p.PhyrexianDriver.class, RETRO_ART));
        cards.add(new SetCardInfo("Phyrexian Prowler", 65, Rarity.UNCOMMON, mage.cards.p.PhyrexianProwler.class, RETRO_ART));
        cards.add(new SetCardInfo("Plague Witch", 66, Rarity.COMMON, mage.cards.p.PlagueWitch.class, RETRO_ART));
        cards.add(new SetCardInfo("Predator, Flagship", 135, Rarity.RARE, mage.cards.p.PredatorFlagship.class, RETRO_ART));
        cards.add(new SetCardInfo("Rackling", 136, Rarity.UNCOMMON, mage.cards.r.Rackling.class, RETRO_ART));
        cards.add(new SetCardInfo("Rath's Edge", 142, Rarity.RARE, mage.cards.r.RathsEdge.class, RETRO_ART));
        cards.add(new SetCardInfo("Rathi Assassin", 67, Rarity.RARE, mage.cards.r.RathiAssassin.class, RETRO_ART));
        cards.add(new SetCardInfo("Rathi Fiend", 68, Rarity.UNCOMMON, mage.cards.r.RathiFiend.class, RETRO_ART));
        cards.add(new SetCardInfo("Rathi Intimidator", 69, Rarity.COMMON, mage.cards.r.RathiIntimidator.class, RETRO_ART));
        cards.add(new SetCardInfo("Refreshing Rain", 110, Rarity.UNCOMMON, mage.cards.r.RefreshingRain.class, RETRO_ART));
        cards.add(new SetCardInfo("Rejuvenation Chamber", 137, Rarity.UNCOMMON, mage.cards.r.RejuvenationChamber.class, RETRO_ART));
        cards.add(new SetCardInfo("Reverent Silence", 111, Rarity.COMMON, mage.cards.r.ReverentSilence.class, RETRO_ART));
        cards.add(new SetCardInfo("Rhox", 112, Rarity.RARE, mage.cards.r.Rhox.class, RETRO_ART));
        cards.add(new SetCardInfo("Rising Waters", 38, Rarity.RARE, mage.cards.r.RisingWaters.class, RETRO_ART));
        cards.add(new SetCardInfo("Rootwater Commando", 39, Rarity.COMMON, mage.cards.r.RootwaterCommando.class, RETRO_ART));
        cards.add(new SetCardInfo("Rootwater Thief", 40, Rarity.RARE, mage.cards.r.RootwaterThief.class, RETRO_ART));
        cards.add(new SetCardInfo("Rupture", 97, Rarity.UNCOMMON, mage.cards.r.Rupture.class, RETRO_ART));
        cards.add(new SetCardInfo("Rusting Golem", 138, Rarity.UNCOMMON, mage.cards.r.RustingGolem.class, RETRO_ART));
        cards.add(new SetCardInfo("Saproling Burst", 113, Rarity.RARE, mage.cards.s.SaprolingBurst.class, RETRO_ART));
        cards.add(new SetCardInfo("Saproling Cluster", 114, Rarity.RARE, mage.cards.s.SaprolingCluster.class, RETRO_ART));
        cards.add(new SetCardInfo("Seahunter", 41, Rarity.RARE, mage.cards.s.Seahunter.class, RETRO_ART));
        cards.add(new SetCardInfo("Seal of Cleansing", 18, Rarity.COMMON, mage.cards.s.SealOfCleansing.class, RETRO_ART));
        cards.add(new SetCardInfo("Seal of Doom", 70, Rarity.COMMON, mage.cards.s.SealOfDoom.class, RETRO_ART));
        cards.add(new SetCardInfo("Seal of Fire", 98, Rarity.COMMON, mage.cards.s.SealOfFire.class, RETRO_ART));
        cards.add(new SetCardInfo("Seal of Removal", 42, Rarity.COMMON, mage.cards.s.SealOfRemoval.class, RETRO_ART));
        cards.add(new SetCardInfo("Seal of Strength", 115, Rarity.COMMON, mage.cards.s.SealOfStrength.class, RETRO_ART));
        cards.add(new SetCardInfo("Shrieking Mogg", 99, Rarity.RARE, mage.cards.s.ShriekingMogg.class, RETRO_ART));
        cards.add(new SetCardInfo("Silkenfist Fighter", 19, Rarity.COMMON, mage.cards.s.SilkenfistFighter.class, RETRO_ART));
        cards.add(new SetCardInfo("Silkenfist Order", 20, Rarity.UNCOMMON, mage.cards.s.SilkenfistOrder.class, RETRO_ART));
        cards.add(new SetCardInfo("Sivvi's Ruse", 21, Rarity.UNCOMMON, mage.cards.s.SivvisRuse.class, RETRO_ART));
        cards.add(new SetCardInfo("Sivvi's Valor", 22, Rarity.RARE, mage.cards.s.SivvisValor.class, RETRO_ART));
        cards.add(new SetCardInfo("Skyshroud Behemoth", 116, Rarity.RARE, mage.cards.s.SkyshroudBehemoth.class, RETRO_ART));
        cards.add(new SetCardInfo("Skyshroud Claim", 117, Rarity.COMMON, mage.cards.s.SkyshroudClaim.class, RETRO_ART));
        cards.add(new SetCardInfo("Skyshroud Cutter", 118, Rarity.COMMON, mage.cards.s.SkyshroudCutter.class, RETRO_ART));
        cards.add(new SetCardInfo("Skyshroud Poacher", 119, Rarity.RARE, mage.cards.s.SkyshroudPoacher.class, RETRO_ART));
        cards.add(new SetCardInfo("Skyshroud Ridgeback", 120, Rarity.COMMON, mage.cards.s.SkyshroudRidgeback.class, RETRO_ART));
        cards.add(new SetCardInfo("Skyshroud Sentinel", 121, Rarity.COMMON, mage.cards.s.SkyshroudSentinel.class, RETRO_ART));
        cards.add(new SetCardInfo("Sliptide Serpent", 43, Rarity.RARE, mage.cards.s.SliptideSerpent.class, RETRO_ART));
        cards.add(new SetCardInfo("Sneaky Homunculus", 44, Rarity.COMMON, mage.cards.s.SneakyHomunculus.class, RETRO_ART));
        cards.add(new SetCardInfo("Spineless Thug", 71, Rarity.COMMON, mage.cards.s.SpinelessThug.class, RETRO_ART));
        cards.add(new SetCardInfo("Spiritual Asylum", 23, Rarity.RARE, mage.cards.s.SpiritualAsylum.class, RETRO_ART));
        cards.add(new SetCardInfo("Spiteful Bully", 72, Rarity.COMMON, mage.cards.s.SpitefulBully.class, RETRO_ART));
        cards.add(new SetCardInfo("Stampede Driver", 122, Rarity.UNCOMMON, mage.cards.s.StampedeDriver.class, RETRO_ART));
        cards.add(new SetCardInfo("Stronghold Biologist", 45, Rarity.UNCOMMON, mage.cards.s.StrongholdBiologist.class, RETRO_ART));
        cards.add(new SetCardInfo("Stronghold Discipline", 73, Rarity.COMMON, mage.cards.s.StrongholdDiscipline.class, RETRO_ART));
        cards.add(new SetCardInfo("Stronghold Gambit", 100, Rarity.RARE, mage.cards.s.StrongholdGambit.class, RETRO_ART));
        cards.add(new SetCardInfo("Stronghold Machinist", 46, Rarity.UNCOMMON, mage.cards.s.StrongholdMachinist.class, RETRO_ART));
        cards.add(new SetCardInfo("Stronghold Zeppelin", 47, Rarity.UNCOMMON, mage.cards.s.StrongholdZeppelin.class, RETRO_ART));
        cards.add(new SetCardInfo("Submerge", 48, Rarity.UNCOMMON, mage.cards.s.Submerge.class, RETRO_ART));
        cards.add(new SetCardInfo("Tangle Wire", 139, Rarity.RARE, mage.cards.t.TangleWire.class, RETRO_ART));
        cards.add(new SetCardInfo("Terrain Generator", 143, Rarity.UNCOMMON, mage.cards.t.TerrainGenerator.class, RETRO_ART));
        cards.add(new SetCardInfo("Topple", 24, Rarity.COMMON, mage.cards.t.Topple.class, RETRO_ART));
        cards.add(new SetCardInfo("Treetop Bracers", 123, Rarity.COMMON, mage.cards.t.TreetopBracers.class, RETRO_ART));
        cards.add(new SetCardInfo("Trickster Mage", 49, Rarity.COMMON, mage.cards.t.TricksterMage.class, RETRO_ART));
        cards.add(new SetCardInfo("Vicious Hunger", 74, Rarity.COMMON, mage.cards.v.ViciousHunger.class, RETRO_ART));
        cards.add(new SetCardInfo("Viseling", 140, Rarity.UNCOMMON, mage.cards.v.Viseling.class, RETRO_ART));
        cards.add(new SetCardInfo("Voice of Truth", 25, Rarity.UNCOMMON, mage.cards.v.VoiceOfTruth.class, RETRO_ART));
        cards.add(new SetCardInfo("Volrath the Fallen", 75, Rarity.RARE, mage.cards.v.VolrathTheFallen.class, RETRO_ART));
        cards.add(new SetCardInfo("Wandering Eye", 50, Rarity.COMMON, mage.cards.w.WanderingEye.class, RETRO_ART));
        cards.add(new SetCardInfo("Wild Mammoth", 124, Rarity.UNCOMMON, mage.cards.w.WildMammoth.class, RETRO_ART));
        cards.add(new SetCardInfo("Woodripper", 125, Rarity.UNCOMMON, mage.cards.w.Woodripper.class, RETRO_ART));
    }
}
