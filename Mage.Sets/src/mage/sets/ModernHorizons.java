package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class ModernHorizons extends ExpansionSet {

    private static final ModernHorizons instance = new ModernHorizons();

    public static ModernHorizons getInstance() {
        return instance;
    }

    private ModernHorizons() {
        // TODO: update the set type closer to release (no point right now, the cards won't be legal for a while)
        super("Modern Horizons", "MH1", ExpansionSet.buildDate(2019, 6, 14), SetType.SUPPLEMENTAL_MODERN_LEGAL);
        this.blockName = "Modern Horizons";
        this.hasBasicLands = false;
        this.hasBoosters = false; // TODO: enable after more cards will be available
        this.numBoosterLands = 0;
        this.numBoosterCommon = 11;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;

        cards.add(new SetCardInfo("Abominable Treefolk", 194, Rarity.UNCOMMON, mage.cards.a.AbominableTreefolk.class));
        cards.add(new SetCardInfo("Cabal Therapist", 80, Rarity.RARE, mage.cards.c.CabalTherapist.class));
        cards.add(new SetCardInfo("Choking Tethers", 44, Rarity.COMMON, mage.cards.c.ChokingTethers.class));
        cards.add(new SetCardInfo("Deep Forest Hermit", 161, Rarity.RARE, mage.cards.d.DeepForestHermit.class));
        cards.add(new SetCardInfo("Diabolic Edict", 87, Rarity.COMMON, mage.cards.d.DiabolicEdict.class));
        cards.add(new SetCardInfo("Elvish Fury", 162, Rarity.COMMON, mage.cards.e.ElvishFury.class));
        cards.add(new SetCardInfo("Exclude", 48, Rarity.UNCOMMON, mage.cards.e.Exclude.class));
        cards.add(new SetCardInfo("Fact or Fiction", 50, Rarity.UNCOMMON, mage.cards.f.FactOrFiction.class));
        cards.add(new SetCardInfo("Firebolt", 122, Rarity.UNCOMMON, mage.cards.f.Firebolt.class));
        cards.add(new SetCardInfo("Flusterstorm", 255, Rarity.RARE, mage.cards.f.Flusterstorm.class));
        cards.add(new SetCardInfo("Goblin Matron", 129, Rarity.UNCOMMON, mage.cards.g.GoblinMatron.class));
        cards.add(new SetCardInfo("Goblin War Party", 131, Rarity.COMMON, mage.cards.g.GoblinWarParty.class));
        cards.add(new SetCardInfo("Headless Specter", 95, Rarity.COMMON, mage.cards.h.HeadlessSpecter.class));
        cards.add(new SetCardInfo("Ice-Fang Coatl", 203, Rarity.RARE, mage.cards.i.IceFangCoatl.class));
        cards.add(new SetCardInfo("Impostor of the Sixth Pride", 14, Rarity.COMMON, mage.cards.i.ImpostorOfTheSixthPride.class));
        cards.add(new SetCardInfo("Lava Dart", 134, Rarity.COMMON, mage.cards.l.LavaDart.class));
        cards.add(new SetCardInfo("Martyr's Soul", 19, Rarity.COMMON, mage.cards.m.MartyrsSoul.class));
        cards.add(new SetCardInfo("Munitions Expert", 209, Rarity.UNCOMMON, mage.cards.m.MunitionsExpert.class));
        cards.add(new SetCardInfo("Prismatic View", 244, Rarity.RARE, mage.cards.p.PrismaticView.class));
        cards.add(new SetCardInfo("Prohibit", 64, Rarity.COMMON, mage.cards.p.Prohibit.class));
        cards.add(new SetCardInfo("Seasoned Pyromancer", 145, Rarity.MYTHIC, mage.cards.s.SeasonedPyromancer.class));
        cards.add(new SetCardInfo("Serra the Benevolent", 26, Rarity.MYTHIC, mage.cards.s.SerraTheBenevolent.class));
        cards.add(new SetCardInfo("Snow-Covered Forest", 254, Rarity.COMMON, mage.cards.s.SnowCoveredForest.class));
        cards.add(new SetCardInfo("Snow-Covered Island", 251, Rarity.COMMON, mage.cards.s.SnowCoveredIsland.class));
        cards.add(new SetCardInfo("Snow-Covered Mountain", 253, Rarity.COMMON, mage.cards.s.SnowCoveredMountain.class));
        cards.add(new SetCardInfo("Snow-Covered Plains", 250, Rarity.COMMON, mage.cards.s.SnowCoveredPlains.class));
        cards.add(new SetCardInfo("Snow-Covered Swamp", 252, Rarity.COMMON, mage.cards.s.SnowCoveredSwamp.class));
        cards.add(new SetCardInfo("Stream of Thought", 71, Rarity.COMMON, mage.cards.s.StreamOfThought.class));
        cards.add(new SetCardInfo("Undead Augur", 112, Rarity.UNCOMMON, mage.cards.u.UndeadAugur.class));
        cards.add(new SetCardInfo("Venomous Changeling", 114, Rarity.COMMON, mage.cards.v.VenomousChangeling.class));
        cards.add(new SetCardInfo("Wall of One Thousand Cuts", 36, Rarity.COMMON, mage.cards.w.WallOfOneThousandCuts.class));
        cards.add(new SetCardInfo("Wing Shards", 38, Rarity.UNCOMMON, mage.cards.w.WingShards.class));
    }
}
