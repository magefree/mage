package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author JayDi85
 */
public final class ArenaNewPlayerExperience extends ExpansionSet {

    private static final ArenaNewPlayerExperience instance = new ArenaNewPlayerExperience();

    public static ArenaNewPlayerExperience getInstance() {
        return instance;
    }

    private ArenaNewPlayerExperience() {
        super("Arena New Player Experience", "ANA", ExpansionSet.buildDate(2018, 7, 14), SetType.MAGIC_ONLINE);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Angelic Reward", 1, Rarity.UNCOMMON, mage.cards.a.AngelicReward.class));
        cards.add(new SetCardInfo("Confront the Assault", 3, Rarity.UNCOMMON, mage.cards.c.ConfrontTheAssault.class));
        cards.add(new SetCardInfo("Feral Roar", 46, Rarity.COMMON, mage.cards.f.FeralRoar.class));
        cards.add(new SetCardInfo("Forest", 55, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 60, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 65, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Inspiring Commander", 5, Rarity.RARE, mage.cards.i.InspiringCommander.class));
        cards.add(new SetCardInfo("Island", 52, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 57, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 62, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Knight's Pledge", 6, Rarity.COMMON, mage.cards.k.KnightsPledge.class));
        cards.add(new SetCardInfo("Mountain", 54, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 59, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 64, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 51, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 56, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 61, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Raging Goblin", 43, Rarity.COMMON, mage.cards.r.RagingGoblin.class));
        cards.add(new SetCardInfo("Rise from the Grave", 34, Rarity.UNCOMMON, mage.cards.r.RiseFromTheGrave.class));
        cards.add(new SetCardInfo("River's Favor", 17, Rarity.COMMON, mage.cards.r.RiversFavor.class));
        cards.add(new SetCardInfo("Sanctuary Cat", 8, Rarity.COMMON, mage.cards.s.SanctuaryCat.class));
        cards.add(new SetCardInfo("Serra Angel", 9, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Shorecomber Crab", 18, Rarity.COMMON, mage.cards.s.ShorecomberCrab.class));
        cards.add(new SetCardInfo("Shrine Keeper", 10, Rarity.COMMON, mage.cards.s.ShrineKeeper.class));
        cards.add(new SetCardInfo("Spiritual Guardian", 11, Rarity.COMMON, mage.cards.s.SpiritualGuardian.class));
        cards.add(new SetCardInfo("Swamp", 53, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 58, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 63, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tactical Advantage", 12, Rarity.COMMON, mage.cards.t.TacticalAdvantage.class));
        cards.add(new SetCardInfo("Treetop Warden", 48, Rarity.COMMON, mage.cards.t.TreetopWarden.class));
        cards.add(new SetCardInfo("Zephyr Gull", 23, Rarity.COMMON, mage.cards.z.ZephyrGull.class));
    }
}