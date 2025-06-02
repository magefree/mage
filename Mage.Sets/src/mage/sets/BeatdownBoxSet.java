package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/btd
 */
public class BeatdownBoxSet extends ExpansionSet {

    private static final BeatdownBoxSet instance = new BeatdownBoxSet();

    public static BeatdownBoxSet getInstance() {
        return instance;
    }

    private BeatdownBoxSet() {
        super("Beatdown Box Set", "BTD", ExpansionSet.buildDate(2000, 10, 1), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Air Elemental", 1, Rarity.UNCOMMON, mage.cards.a.AirElemental.class, RETRO_ART));
        cards.add(new SetCardInfo("Balduvian Horde", 34, Rarity.RARE, mage.cards.b.BalduvianHorde.class, RETRO_ART));
        cards.add(new SetCardInfo("Ball Lightning", 35, Rarity.RARE, mage.cards.b.BallLightning.class, RETRO_ART));
        cards.add(new SetCardInfo("Blizzard Elemental", 2, Rarity.RARE, mage.cards.b.BlizzardElemental.class, RETRO_ART));
        cards.add(new SetCardInfo("Bloodrock Cyclops", 36, Rarity.COMMON, mage.cards.b.BloodrockCyclops.class, RETRO_ART));
        cards.add(new SetCardInfo("Bone Harvest", 19, Rarity.COMMON, mage.cards.b.BoneHarvest.class, RETRO_ART));
        cards.add(new SetCardInfo("Brainstorm", 3, Rarity.COMMON, mage.cards.b.Brainstorm.class, RETRO_ART));
        cards.add(new SetCardInfo("Clockwork Avian", 69, Rarity.RARE, mage.cards.c.ClockworkAvian.class, RETRO_ART));
        cards.add(new SetCardInfo("Clockwork Beast", 70, Rarity.RARE, mage.cards.c.ClockworkBeast.class, RETRO_ART));
        cards.add(new SetCardInfo("Cloud Djinn", 4, Rarity.UNCOMMON, mage.cards.c.CloudDjinn.class, RETRO_ART));
        cards.add(new SetCardInfo("Cloud Elemental", 5, Rarity.COMMON, mage.cards.c.CloudElemental.class, RETRO_ART));
        cards.add(new SetCardInfo("Coercion", 20, Rarity.COMMON, mage.cards.c.Coercion.class, RETRO_ART));
        cards.add(new SetCardInfo("Counterspell", 6, Rarity.COMMON, mage.cards.c.Counterspell.class, RETRO_ART));
        cards.add(new SetCardInfo("Crash of Rhinos", 51, Rarity.COMMON, mage.cards.c.CrashOfRhinos.class, RETRO_ART));
        cards.add(new SetCardInfo("Crashing Boars", 52, Rarity.UNCOMMON, mage.cards.c.CrashingBoars.class, RETRO_ART));
        cards.add(new SetCardInfo("Dark Ritual", 21, Rarity.COMMON, mage.cards.d.DarkRitual.class, RETRO_ART));
        cards.add(new SetCardInfo("Deadly Insect", 53, Rarity.COMMON, mage.cards.d.DeadlyInsect.class, RETRO_ART));
        cards.add(new SetCardInfo("Death Stroke", 22, Rarity.COMMON, mage.cards.d.DeathStroke.class, RETRO_ART));
        cards.add(new SetCardInfo("Diabolic Edict", 23, Rarity.COMMON, mage.cards.d.DiabolicEdict.class, RETRO_ART));
        cards.add(new SetCardInfo("Diabolic Vision", 67, Rarity.UNCOMMON, mage.cards.d.DiabolicVision.class, RETRO_ART));
        cards.add(new SetCardInfo("Drain Life", 24, Rarity.COMMON, mage.cards.d.DrainLife.class, RETRO_ART));
        cards.add(new SetCardInfo("Dwarven Ruins", 71, Rarity.UNCOMMON, mage.cards.d.DwarvenRuins.class, RETRO_ART));
        cards.add(new SetCardInfo("Ebon Stronghold", 72, Rarity.UNCOMMON, mage.cards.e.EbonStronghold.class, RETRO_ART));
        cards.add(new SetCardInfo("Erhnam Djinn", 54, Rarity.UNCOMMON, mage.cards.e.ErhnamDjinn.class, RETRO_ART));
        cards.add(new SetCardInfo("Fallen Angel", 25, Rarity.RARE, mage.cards.f.FallenAngel.class, RETRO_ART));
        cards.add(new SetCardInfo("Feral Shadow", 26, Rarity.COMMON, mage.cards.f.FeralShadow.class, RETRO_ART));
        cards.add(new SetCardInfo("Fireball", 37, Rarity.COMMON, mage.cards.f.Fireball.class, RETRO_ART));
        cards.add(new SetCardInfo("Fog Elemental", 7, Rarity.COMMON, mage.cards.f.FogElemental.class, RETRO_ART));
        cards.add(new SetCardInfo("Fog", 55, Rarity.COMMON, mage.cards.f.Fog.class, RETRO_ART));
        cards.add(new SetCardInfo("Force of Nature", 56, Rarity.RARE, mage.cards.f.ForceOfNature.class, RETRO_ART));
        cards.add(new SetCardInfo("Forest", 88, Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 89, Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 90, Rarity.LAND, mage.cards.basiclands.Forest.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Gaseous Form", 8, Rarity.COMMON, mage.cards.g.GaseousForm.class, RETRO_ART));
        cards.add(new SetCardInfo("Giant Crab", 9, Rarity.COMMON, mage.cards.g.GiantCrab.class, RETRO_ART));
        cards.add(new SetCardInfo("Giant Growth", 57, Rarity.COMMON, mage.cards.g.GiantGrowth.class, RETRO_ART));
        cards.add(new SetCardInfo("Gravedigger", 27, Rarity.COMMON, mage.cards.g.Gravedigger.class, RETRO_ART));
        cards.add(new SetCardInfo("Havenwood Battleground", 73, Rarity.UNCOMMON, mage.cards.h.HavenwoodBattleground.class, RETRO_ART));
        cards.add(new SetCardInfo("Hollow Dogs", 28, Rarity.COMMON, mage.cards.h.HollowDogs.class, RETRO_ART));
        cards.add(new SetCardInfo("Hulking Cyclops", 38, Rarity.UNCOMMON, mage.cards.h.HulkingCyclops.class, RETRO_ART));
        cards.add(new SetCardInfo("Impulse", 10, Rarity.COMMON, mage.cards.i.Impulse.class, RETRO_ART));
        cards.add(new SetCardInfo("Island", 79, Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 80, Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 81, Rarity.LAND, mage.cards.basiclands.Island.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Killer Whale", 11, Rarity.UNCOMMON, mage.cards.k.KillerWhale.class, RETRO_ART));
        cards.add(new SetCardInfo("Kird Ape", 39, Rarity.COMMON, mage.cards.k.KirdApe.class, RETRO_ART));
        cards.add(new SetCardInfo("Lava Axe", 40, Rarity.COMMON, mage.cards.l.LavaAxe.class, RETRO_ART));
        cards.add(new SetCardInfo("Leviathan", 12, Rarity.RARE, mage.cards.l.Leviathan.class, RETRO_ART));
        cards.add(new SetCardInfo("Lightning Bolt", 41, Rarity.COMMON, mage.cards.l.LightningBolt.class, RETRO_ART));
        cards.add(new SetCardInfo("Llanowar Elves", 58, Rarity.COMMON, mage.cards.l.LlanowarElves.class, RETRO_ART));
        cards.add(new SetCardInfo("Lowland Giant", 42, Rarity.COMMON, mage.cards.l.LowlandGiant.class, RETRO_ART));
        cards.add(new SetCardInfo("Mahamoti Djinn", 13, Rarity.RARE, mage.cards.m.MahamotiDjinn.class, RETRO_ART));
        cards.add(new SetCardInfo("Mountain", 85, Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 86, Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 87, Rarity.LAND, mage.cards.basiclands.Mountain.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Plated Spider", 59, Rarity.COMMON, mage.cards.p.PlatedSpider.class, RETRO_ART));
        cards.add(new SetCardInfo("Polluted Mire", 74, Rarity.COMMON, mage.cards.p.PollutedMire.class, RETRO_ART));
        cards.add(new SetCardInfo("Power Sink", 14, Rarity.COMMON, mage.cards.p.PowerSink.class, RETRO_ART));
        cards.add(new SetCardInfo("Quirion Elves", 60, Rarity.COMMON, mage.cards.q.QuirionElves.class, RETRO_ART));
        cards.add(new SetCardInfo("Raging Goblin", 43, Rarity.COMMON, mage.cards.r.RagingGoblin.class, RETRO_ART));
        cards.add(new SetCardInfo("Rampant Growth", 61, Rarity.COMMON, mage.cards.r.RampantGrowth.class, RETRO_ART));
        cards.add(new SetCardInfo("Remote Isle", 75, Rarity.COMMON, mage.cards.r.RemoteIsle.class, RETRO_ART));
        cards.add(new SetCardInfo("Scaled Wurm", 62, Rarity.COMMON, mage.cards.s.ScaledWurm.class, RETRO_ART));
        cards.add(new SetCardInfo("Segmented Wurm", 68, Rarity.UNCOMMON, mage.cards.s.SegmentedWurm.class, RETRO_ART));
        cards.add(new SetCardInfo("Sengir Vampire", 29, Rarity.UNCOMMON, mage.cards.s.SengirVampire.class, RETRO_ART));
        cards.add(new SetCardInfo("Shambling Strider", 63, Rarity.COMMON, mage.cards.s.ShamblingStrider.class, RETRO_ART));
        cards.add(new SetCardInfo("Shivan Dragon", 44, Rarity.RARE, mage.cards.s.ShivanDragon.class, RETRO_ART));
        cards.add(new SetCardInfo("Shock", 45, Rarity.COMMON, mage.cards.s.Shock.class, RETRO_ART));
        cards.add(new SetCardInfo("Skittering Horror", 30, Rarity.COMMON, mage.cards.s.SkitteringHorror.class, RETRO_ART));
        cards.add(new SetCardInfo("Skittering Skirge", 31, Rarity.COMMON, mage.cards.s.SkitteringSkirge.class, RETRO_ART));
        cards.add(new SetCardInfo("Slippery Karst", 76, Rarity.COMMON, mage.cards.s.SlipperyKarst.class, RETRO_ART));
        cards.add(new SetCardInfo("Smoldering Crater", 77, Rarity.COMMON, mage.cards.s.SmolderingCrater.class, RETRO_ART));
        cards.add(new SetCardInfo("Snapping Drake", 15, Rarity.COMMON, mage.cards.s.SnappingDrake.class, RETRO_ART));
        cards.add(new SetCardInfo("Sonic Burst", 46, Rarity.COMMON, mage.cards.s.SonicBurst.class, RETRO_ART));
        cards.add(new SetCardInfo("Svyelunite Temple", 78, Rarity.UNCOMMON, mage.cards.s.SvyeluniteTemple.class, RETRO_ART));
        cards.add(new SetCardInfo("Swamp", 82, Rarity.LAND, mage.cards.basiclands.Swamp.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 83, Rarity.LAND, mage.cards.basiclands.Swamp.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 84, Rarity.LAND, mage.cards.basiclands.Swamp.class, RETRO_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Talruum Minotaur", 47, Rarity.COMMON, mage.cards.t.TalruumMinotaur.class, RETRO_ART));
        cards.add(new SetCardInfo("Tar Pit Warrior", 32, Rarity.COMMON, mage.cards.t.TarPitWarrior.class, RETRO_ART));
        cards.add(new SetCardInfo("Terror", 33, Rarity.COMMON, mage.cards.t.Terror.class, RETRO_ART));
        cards.add(new SetCardInfo("Thunderbolt", 48, Rarity.COMMON, mage.cards.t.Thunderbolt.class, RETRO_ART));
        cards.add(new SetCardInfo("Thundering Giant", 49, Rarity.UNCOMMON, mage.cards.t.ThunderingGiant.class, RETRO_ART));
        cards.add(new SetCardInfo("Tolarian Winds", 16, Rarity.COMMON, mage.cards.t.TolarianWinds.class, RETRO_ART));
        cards.add(new SetCardInfo("Viashino Warrior", 50, Rarity.COMMON, mage.cards.v.ViashinoWarrior.class, RETRO_ART));
        cards.add(new SetCardInfo("Vigilant Drake", 17, Rarity.COMMON, mage.cards.v.VigilantDrake.class, RETRO_ART));
        cards.add(new SetCardInfo("Wayward Soul", 18, Rarity.COMMON, mage.cards.w.WaywardSoul.class, RETRO_ART));
        cards.add(new SetCardInfo("Wild Growth", 64, Rarity.COMMON, mage.cards.w.WildGrowth.class, RETRO_ART));
        cards.add(new SetCardInfo("Woolly Spider", 65, Rarity.COMMON, mage.cards.w.WoollySpider.class, RETRO_ART));
        cards.add(new SetCardInfo("Yavimaya Wurm", 66, Rarity.COMMON, mage.cards.y.YavimayaWurm.class, RETRO_ART));
    }
}
