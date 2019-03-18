
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author LevelX2
 */
public final class Starter2000 extends ExpansionSet {

    private static final Starter2000 instance = new Starter2000();

    /**
     *
     * @return
     */
    public static Starter2000 getInstance() {
        return instance;
    }

    private Starter2000() {
        super("Starter 2000", "S00", ExpansionSet.buildDate(2000, 7, 1), SetType.SUPPLEMENTAL);
        this.blockName = "Beginner";
        this.hasBasicLands = true;
        this.hasBoosters = false;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 0;
        cards.add(new SetCardInfo("Angelic Blessing", 1, Rarity.COMMON, mage.cards.a.AngelicBlessing.class));
        cards.add(new SetCardInfo("Armored Pegasus", 2, Rarity.COMMON, mage.cards.a.ArmoredPegasus.class));
        cards.add(new SetCardInfo("Bog Imp", 22, Rarity.COMMON, mage.cards.b.BogImp.class));
        cards.add(new SetCardInfo("Breath of Life", 3, Rarity.UNCOMMON, mage.cards.b.BreathOfLife.class));
        cards.add(new SetCardInfo("Coercion", 23, Rarity.COMMON, mage.cards.c.Coercion.class));
        cards.add(new SetCardInfo("Counterspell", 12, Rarity.COMMON, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Disenchant", 4, Rarity.COMMON, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Drudge Skeletons", 24, Rarity.COMMON, mage.cards.d.DrudgeSkeletons.class));
        cards.add(new SetCardInfo("Durkwood Boars", 38, Rarity.COMMON, mage.cards.d.DurkwoodBoars.class));
        cards.add(new SetCardInfo("Eager Cadet", 5, Rarity.COMMON, mage.cards.e.EagerCadet.class));
        cards.add(new SetCardInfo("Flame Spirit", 29, Rarity.COMMON, mage.cards.f.FlameSpirit.class));
        cards.add(new SetCardInfo("Flight", 13, Rarity.COMMON, mage.cards.f.Flight.class));
        cards.add(new SetCardInfo("Forest", 49, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 50, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Giant Growth", 39, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Giant Octopus", 14, Rarity.COMMON, mage.cards.g.GiantOctopus.class));
        cards.add(new SetCardInfo("Goblin Hero", 30, Rarity.COMMON, mage.cards.g.GoblinHero.class));
        cards.add(new SetCardInfo("Hand of Death", 25, Rarity.COMMON, mage.cards.h.HandOfDeath.class));
        cards.add(new SetCardInfo("Hero's Resolve", 6, Rarity.COMMON, mage.cards.h.HerosResolve.class));
        cards.add(new SetCardInfo("Inspiration", 15, Rarity.COMMON, mage.cards.i.Inspiration.class));
        cards.add(new SetCardInfo("Island", 51, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 52, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Knight Errant", 7, Rarity.COMMON, mage.cards.k.KnightErrant.class));
        cards.add(new SetCardInfo("Lava Axe", 31, Rarity.COMMON, mage.cards.l.LavaAxe.class));
        cards.add(new SetCardInfo("Llanowar Elves", 40, Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Merfolk of the Pearl Trident", 16, Rarity.COMMON, mage.cards.m.MerfolkOfThePearlTrident.class));
        cards.add(new SetCardInfo("Mons's Goblin Raiders", 32, Rarity.COMMON, mage.cards.m.MonssGoblinRaiders.class));
        cards.add(new SetCardInfo("Monstrous Growth", 41, Rarity.COMMON, mage.cards.m.MonstrousGrowth.class));
        cards.add(new SetCardInfo("Moon Sprite", 42, Rarity.UNCOMMON, mage.cards.m.MoonSprite.class));
        cards.add(new SetCardInfo("Mountain", 53, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 54, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Obsianus Golem", 46, Rarity.UNCOMMON, mage.cards.o.ObsianusGolem.class));
        cards.add(new SetCardInfo("Ogre Warrior", 33, Rarity.COMMON, mage.cards.o.OgreWarrior.class));
        cards.add(new SetCardInfo("Orcish Oriflamme", 34, Rarity.UNCOMMON, mage.cards.o.OrcishOriflamme.class));
        cards.add(new SetCardInfo("Plains", 55, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 56, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prodigal Sorcerer", 17, Rarity.COMMON, mage.cards.p.ProdigalSorcerer.class));
        cards.add(new SetCardInfo("Python", 26, Rarity.COMMON, mage.cards.p.Python.class));
        cards.add(new SetCardInfo("Rhox", 43, Rarity.RARE, mage.cards.r.Rhox.class));
        cards.add(new SetCardInfo("Rod of Ruin", 47, Rarity.UNCOMMON, mage.cards.r.RodOfRuin.class));
        cards.add(new SetCardInfo("Royal Falcon", 8, Rarity.COMMON, mage.cards.r.RoyalFalcon.class));
        cards.add(new SetCardInfo("Samite Healer", 9, Rarity.COMMON, mage.cards.s.SamiteHealer.class));
        cards.add(new SetCardInfo("Scathe Zombies", 27, Rarity.COMMON, mage.cards.s.ScatheZombies.class));
        cards.add(new SetCardInfo("Sea Eagle", 18, Rarity.COMMON, mage.cards.s.SeaEagle.class));
        cards.add(new SetCardInfo("Shock", 35, Rarity.COMMON, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Soul Net", 48, Rarity.UNCOMMON, mage.cards.s.SoulNet.class));
        cards.add(new SetCardInfo("Spined Wurm", 44, Rarity.COMMON, mage.cards.s.SpinedWurm.class));
        cards.add(new SetCardInfo("Stone Rain", 36, Rarity.COMMON, mage.cards.s.StoneRain.class));
        cards.add(new SetCardInfo("Swamp", 57, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 58, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terror", 28, Rarity.COMMON, mage.cards.t.Terror.class));
        cards.add(new SetCardInfo("Time Ebb", 19, Rarity.COMMON, mage.cards.t.TimeEbb.class));
        cards.add(new SetCardInfo("Trained Orgg", 37, Rarity.RARE, mage.cards.t.TrainedOrgg.class));
        cards.add(new SetCardInfo("Venerable Monk", 10, Rarity.COMMON, mage.cards.v.VenerableMonk.class));
        cards.add(new SetCardInfo("Vizzerdrix", 20, Rarity.RARE, mage.cards.v.Vizzerdrix.class));
        cards.add(new SetCardInfo("Wild Griffin", 11, Rarity.COMMON, mage.cards.w.WildGriffin.class));
        cards.add(new SetCardInfo("Willow Elf", 45, Rarity.COMMON, mage.cards.w.WillowElf.class));
        cards.add(new SetCardInfo("Wind Drake", 21, Rarity.COMMON, mage.cards.w.WindDrake.class));
    }
}
