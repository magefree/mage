
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author NinthWorld
 */
public final class StarCraft extends ExpansionSet {

    private static final StarCraft instance = new StarCraft();

    public static StarCraft getInstance() {
        return instance;
    }

    private StarCraft() {
        super("Duel Decks: StarCraft", "DDSC", ExpansionSet.buildDate(2016, 3, 23), SetType.CUSTOM_SET);
        this.blockName = "Duel Decks";
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        cards.add(new SetCardInfo("Aggressive Mutation", 68, Rarity.UNCOMMON, mage.cards.a.AggressiveMutation.class));
        cards.add(new SetCardInfo("Arbiter", 85, Rarity.UNCOMMON, mage.cards.a.Arbiter.class));
        cards.add(new SetCardInfo("Archon", 86, Rarity.UNCOMMON, mage.cards.a.Archon.class));
        cards.add(new SetCardInfo("Auto-Turret", 107, Rarity.UNCOMMON, mage.cards.a.AutoTurret.class));
        cards.add(new SetCardInfo("Baneling", 41, Rarity.COMMON, mage.cards.b.Baneling.class));
        cards.add(new SetCardInfo("Battlecruiser", 55, Rarity.UNCOMMON, mage.cards.b.Battlecruiser.class));
        cards.add(new SetCardInfo("Brood Lord", 87, Rarity.RARE, mage.cards.b.BroodLord.class));
        cards.add(new SetCardInfo("Bunker", 1, Rarity.COMMON, mage.cards.b.Bunker.class));
        cards.add(new SetCardInfo("Calldown", 88, Rarity.UNCOMMON, mage.cards.c.Calldown.class));
        cards.add(new SetCardInfo("Carrier", 26, Rarity.RARE, mage.cards.c.Carrier.class));
        cards.add(new SetCardInfo("Changeling", 42, Rarity.RARE, mage.cards.c.Changeling.class));
        cards.add(new SetCardInfo("Chrono Boost", 27, Rarity.COMMON, mage.cards.c.ChronoBoost.class));
        cards.add(new SetCardInfo("Command Center", 108, Rarity.RARE, mage.cards.c.CommandCenter.class));
        cards.add(new SetCardInfo("Consume", 69, Rarity.COMMON, mage.cards.c.Consume.class));
        cards.add(new SetCardInfo("Contaminate", 70, Rarity.COMMON, mage.cards.c.Contaminate.class));
        cards.add(new SetCardInfo("Corruptor", 43, Rarity.UNCOMMON, mage.cards.c.Corruptor.class));
        cards.add(new SetCardInfo("Corsair", 28, Rarity.COMMON, mage.cards.c.Corsair.class));
        cards.add(new SetCardInfo("Covert Ops", 104, Rarity.RARE, mage.cards.c.CovertOps.class));
        cards.add(new SetCardInfo("Creep Colony", 109, Rarity.COMMON, mage.cards.c.CreepColony.class));
        cards.add(new SetCardInfo("Dark Swarm", 71, Rarity.COMMON, mage.cards.d.DarkSwarm.class));
        cards.add(new SetCardInfo("Dark Templar", 29, Rarity.UNCOMMON, mage.cards.d.DarkTemplar.class));
        cards.add(new SetCardInfo("Defense Matrix", 2, Rarity.COMMON, mage.cards.d.DefenseMatrix.class));
        cards.add(new SetCardInfo("Defiler", 44, Rarity.UNCOMMON, mage.cards.d.Defiler.class));
        cards.add(new SetCardInfo("Disruption Web", 3, Rarity.COMMON, mage.cards.d.DisruptionWeb.class));
        cards.add(new SetCardInfo("Disruptor", 4, Rarity.UNCOMMON, mage.cards.d.Disruptor.class));
        cards.add(new SetCardInfo("Dragoon", 89, Rarity.COMMON, mage.cards.d.Dragoon.class));
        cards.add(new SetCardInfo("Drone", 72, Rarity.COMMON, mage.cards.d.Drone.class));
        cards.add(new SetCardInfo("EMP Round", 5, Rarity.RARE, mage.cards.e.EMPRound.class));
        cards.add(new SetCardInfo("Envision", 30, Rarity.COMMON, mage.cards.e.Envision.class));
        cards.add(new SetCardInfo("Evolution Chamber", 73, Rarity.UNCOMMON, mage.cards.e.EvolutionChamber.class));
        cards.add(new SetCardInfo("Firebat", 56, Rarity.UNCOMMON, mage.cards.f.Firebat.class));
        cards.add(new SetCardInfo("Force Field", 31, Rarity.UNCOMMON, mage.cards.f.ForceField2.class));
        cards.add(new SetCardInfo("Forest", 172, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 173, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 179, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 180, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gamete Meiosis", 45, Rarity.UNCOMMON, mage.cards.g.GameteMeiosis.class));
        cards.add(new SetCardInfo("Gauss Rifle", 57, Rarity.COMMON, mage.cards.g.GaussRifle.class));
        cards.add(new SetCardInfo("Ghost", 6, Rarity.UNCOMMON, mage.cards.g.Ghost.class));
        cards.add(new SetCardInfo("Ghost Academy", 111, Rarity.UNCOMMON, mage.cards.g.GhostAcademy.class));
        cards.add(new SetCardInfo("Hallucination", 32, Rarity.RARE, mage.cards.h.Hallucination.class));
        cards.add(new SetCardInfo("Hellbat", 58, Rarity.COMMON, mage.cards.h.Hellbat.class));
        cards.add(new SetCardInfo("Hellion", 59, Rarity.COMMON, mage.cards.h.Hellion.class));
        cards.add(new SetCardInfo("High Templar", 7, Rarity.UNCOMMON, mage.cards.h.HighTemplar.class));
        cards.add(new SetCardInfo("Hive Cluster", 112, Rarity.RARE, mage.cards.h.HiveCluster.class));
        cards.add(new SetCardInfo("Hydralisk", 74, Rarity.COMMON, mage.cards.h.Hydralisk.class));
        cards.add(new SetCardInfo("Immortal", 8, Rarity.UNCOMMON, mage.cards.i.Immortal.class));
        cards.add(new SetCardInfo("Infested Terran", 46, Rarity.COMMON, mage.cards.i.InfestedTerran.class));
        cards.add(new SetCardInfo("Infestor", 47, Rarity.RARE, mage.cards.i.Infestor.class));
        cards.add(new SetCardInfo("Irradiation", 60, Rarity.COMMON, mage.cards.i.Irradiation.class));
        cards.add(new SetCardInfo("Island", 143, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 144, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 145, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 146, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kerrigan, Queen of Blades", 90, Rarity.MYTHIC, mage.cards.k.KerriganQueenOfBlades.class));
        cards.add(new SetCardInfo("Larva", 76, Rarity.COMMON, mage.cards.l.Larva.class));
        cards.add(new SetCardInfo("Liftoff", 9, Rarity.COMMON, mage.cards.l.Liftoff.class));
        cards.add(new SetCardInfo("Lockdown", 10, Rarity.UNCOMMON, mage.cards.l.Lockdown.class));
        cards.add(new SetCardInfo("Marauder", 11, Rarity.COMMON, mage.cards.m.Marauder.class));
        cards.add(new SetCardInfo("Marine", 61, Rarity.COMMON, mage.cards.m.Marine.class));
        cards.add(new SetCardInfo("Mass Recall", 33, Rarity.UNCOMMON, mage.cards.m.MassRecall.class));
        cards.add(new SetCardInfo("Medic", 12, Rarity.UNCOMMON, mage.cards.m.Medic.class));
        cards.add(new SetCardInfo("Metabolic Boost", 77, Rarity.COMMON, mage.cards.m.MetabolicBoost.class));
        cards.add(new SetCardInfo("Mind Control", 34, Rarity.COMMON, mage.cards.m.MindControl.class));
        cards.add(new SetCardInfo("Mineral Field", 122, Rarity.COMMON, mage.cards.m.MineralField.class));
        cards.add(new SetCardInfo("Mothership", 91, Rarity.RARE, mage.cards.m.Mothership.class));
        cards.add(new SetCardInfo("Mountain", 167, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 168, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 169, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 171, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mutalisk", 48, Rarity.COMMON, mage.cards.m.Mutalisk.class));
        cards.add(new SetCardInfo("Nexus", 113, Rarity.RARE, mage.cards.n.Nexus.class));
        cards.add(new SetCardInfo("Nuclear Strike", 13, Rarity.RARE, mage.cards.n.NuclearStrike.class));
        cards.add(new SetCardInfo("Nydus Network", 114, Rarity.COMMON, mage.cards.n.NydusNetwork.class));
        cards.add(new SetCardInfo("Nydus Worm", 78, Rarity.COMMON, mage.cards.n.NydusWorm.class));
        cards.add(new SetCardInfo("Observatory", 115, Rarity.COMMON, mage.cards.o.Observatory.class));
        cards.add(new SetCardInfo("Overlord", 49, Rarity.UNCOMMON, mage.cards.o.Overlord.class));
        cards.add(new SetCardInfo("Parasite", 50, Rarity.COMMON, mage.cards.p.Parasite.class));
        cards.add(new SetCardInfo("Photon Overcharge", 14, Rarity.COMMON, mage.cards.p.PhotonOvercharge.class));
        cards.add(new SetCardInfo("Plains", 130, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 131, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 132, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 133, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 134, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 135, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Protoss Probe", 35, Rarity.COMMON, mage.cards.p.ProtossProbe.class));
        cards.add(new SetCardInfo("Psionic Feedback", 36, Rarity.COMMON, mage.cards.p.PsionicFeedback.class));
        cards.add(new SetCardInfo("Psionic Storm", 15, Rarity.RARE, mage.cards.p.PsionicStorm.class));
        cards.add(new SetCardInfo("Pylon", 116, Rarity.COMMON, mage.cards.p.Pylon.class));
        cards.add(new SetCardInfo("Queen", 79, Rarity.UNCOMMON, mage.cards.q.Queen.class));
        cards.add(new SetCardInfo("Raven", 16, Rarity.UNCOMMON, mage.cards.r.Raven.class));
        cards.add(new SetCardInfo("Raynor, Rebel Commander", 92, Rarity.MYTHIC, mage.cards.r.RaynorRebelCommander.class));
        cards.add(new SetCardInfo("Reaper", 63, Rarity.COMMON, mage.cards.r.Reaper.class));
        cards.add(new SetCardInfo("Reaver", 17, Rarity.UNCOMMON, mage.cards.r.Reaver.class));
        cards.add(new SetCardInfo("Recharge", 93, Rarity.UNCOMMON, mage.cards.r.Recharge.class));
        cards.add(new SetCardInfo("Repair", 18, Rarity.COMMON, mage.cards.r.Repair.class));
        cards.add(new SetCardInfo("Roach", 51, Rarity.COMMON, mage.cards.r.Roach.class));
        cards.add(new SetCardInfo("SCV", 19, Rarity.COMMON, mage.cards.s.SCV.class));
        cards.add(new SetCardInfo("Scanner Sweep", 64, Rarity.COMMON, mage.cards.s.ScannerSweep.class));
        cards.add(new SetCardInfo("Scourge", 52, Rarity.COMMON, mage.cards.s.Scourge.class));
        cards.add(new SetCardInfo("Sensor Array", 94, Rarity.UNCOMMON, mage.cards.s.SensorArray.class));
        cards.add(new SetCardInfo("Sentry", 37, Rarity.COMMON, mage.cards.s.Sentry.class));
        cards.add(new SetCardInfo("Siege Tank", 96, Rarity.UNCOMMON, mage.cards.s.SiegeTank.class));
        cards.add(new SetCardInfo("Spawn Broodling", 97, Rarity.UNCOMMON, mage.cards.s.SpawnBroodling.class));
        cards.add(new SetCardInfo("Spore Crawler", 118, Rarity.UNCOMMON, mage.cards.s.SporeCrawler.class));
        cards.add(new SetCardInfo("Stalker", 38, Rarity.COMMON, mage.cards.s.Stalker.class));
        cards.add(new SetCardInfo("Stasis Ward", 98, Rarity.UNCOMMON, mage.cards.s.StasisWard.class));
        cards.add(new SetCardInfo("Stimpack", 65, Rarity.COMMON, mage.cards.s.Stimpack.class));
        cards.add(new SetCardInfo("Strike Cannon", 66, Rarity.UNCOMMON, mage.cards.s.StrikeCannon.class));
        cards.add(new SetCardInfo("Supply Depot", 119, Rarity.COMMON, mage.cards.s.SupplyDepot.class));
        cards.add(new SetCardInfo("Swamp", 154, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 155, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 156, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swarm Host", 80, Rarity.UNCOMMON, mage.cards.s.SwarmHost.class));
        cards.add(new SetCardInfo("Templar Archives", 121, Rarity.COMMON, mage.cards.t.TemplarArchives.class));
        cards.add(new SetCardInfo("Terran Banshee", 67, Rarity.UNCOMMON, mage.cards.t.TerranBanshee.class));
        cards.add(new SetCardInfo("Thor", 20, Rarity.RARE, mage.cards.t.Thor.class));
        cards.add(new SetCardInfo("Ultralisk", 81, Rarity.RARE, mage.cards.u.Ultralisk.class));
        cards.add(new SetCardInfo("Unburrow", 99, Rarity.UNCOMMON, mage.cards.u.Unburrow.class));
        cards.add(new SetCardInfo("Viking", 21, Rarity.COMMON, mage.cards.v.Viking.class));
        cards.add(new SetCardInfo("Viper", 100, Rarity.UNCOMMON, mage.cards.v.Viper.class));
        cards.add(new SetCardInfo("Void Ray", 39, Rarity.UNCOMMON, mage.cards.v.VoidRay.class));
        cards.add(new SetCardInfo("Volatile Burst", 53, Rarity.COMMON, mage.cards.v.VolatileBurst.class));
        cards.add(new SetCardInfo("Vortex", 22, Rarity.COMMON, mage.cards.v.Vortex.class));
        cards.add(new SetCardInfo("Vulture", 24, Rarity.COMMON, mage.cards.v.Vulture.class));
        cards.add(new SetCardInfo("Warp Gate", 40, Rarity.COMMON, mage.cards.w.WarpGate.class));
        cards.add(new SetCardInfo("Warp Prism", 105, Rarity.UNCOMMON, mage.cards.w.WarpPrism.class));
        cards.add(new SetCardInfo("Widow Mine", 106, Rarity.COMMON, mage.cards.w.WidowMine.class));
        cards.add(new SetCardInfo("Wraith", 25, Rarity.COMMON, mage.cards.w.Wraith.class));
        cards.add(new SetCardInfo("Zealot", 102, Rarity.COMMON, mage.cards.z.Zealot.class));
        cards.add(new SetCardInfo("Zeratul, Nezarim Prelate", 103, Rarity.MYTHIC, mage.cards.z.ZeratulNezarimPrelate.class));
        cards.add(new SetCardInfo("Zerg Egg", 82, Rarity.COMMON, mage.cards.z.ZergEgg.class));
        cards.add(new SetCardInfo("Zerg Lurker", 54, Rarity.UNCOMMON, mage.cards.z.ZergLurker.class));
        cards.add(new SetCardInfo("Zergling", 83, Rarity.COMMON, mage.cards.z.Zergling.class));
    }
}
