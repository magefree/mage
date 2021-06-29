package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author JayDi85
 */
public final class ArenaNewPlayerExperienceCards extends ExpansionSet {

    private static final ArenaNewPlayerExperienceCards instance = new ArenaNewPlayerExperienceCards();

    public static ArenaNewPlayerExperienceCards getInstance() {
        return instance;
    }

    private ArenaNewPlayerExperienceCards() {
        super("Arena New Player Experience Cards", "OANA", ExpansionSet.buildDate(2018, 7, 14), SetType.MAGIC_ONLINE);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Angelic Reward", 1, Rarity.UNCOMMON, mage.cards.a.AngelicReward.class));
        cards.add(new SetCardInfo("Confront the Assault", 3, Rarity.UNCOMMON, mage.cards.c.ConfrontTheAssault.class));
        cards.add(new SetCardInfo("Feral Roar", 46, Rarity.COMMON, mage.cards.f.FeralRoar.class));
        cards.add(new SetCardInfo("Inspiring Commander", 4, Rarity.RARE, mage.cards.i.InspiringCommander.class));
        cards.add(new SetCardInfo("Knight's Pledge", 5, Rarity.COMMON, mage.cards.k.KnightsPledge.class));
        cards.add(new SetCardInfo("Raging Goblin", 43, Rarity.COMMON, mage.cards.r.RagingGoblin.class));
        cards.add(new SetCardInfo("River's Favor", 19, Rarity.COMMON, mage.cards.r.RiversFavor.class));
        cards.add(new SetCardInfo("Sanctuary Cat", 8, Rarity.COMMON, mage.cards.s.SanctuaryCat.class));
        cards.add(new SetCardInfo("Serra Angel", 9, Rarity.UNCOMMON, mage.cards.s.SerraAngel.class));
        cards.add(new SetCardInfo("Shrine Keeper", 10, Rarity.COMMON, mage.cards.s.ShrineKeeper.class));
        cards.add(new SetCardInfo("Spiritual Guardian", 11, Rarity.COMMON, mage.cards.s.SpiritualGuardian.class));
        cards.add(new SetCardInfo("Tactical Advantage", 12, Rarity.COMMON, mage.cards.t.TacticalAdvantage.class));
        cards.add(new SetCardInfo("Treetop Warden", 48, Rarity.COMMON, mage.cards.t.TreetopWarden.class));
        cards.add(new SetCardInfo("Zephyr Gull", 23, Rarity.COMMON, mage.cards.z.ZephyrGull.class));
    }
}