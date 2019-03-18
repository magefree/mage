
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author fireshoes
 */
public final class SuperSeries extends ExpansionSet {

    private static final SuperSeries instance = new SuperSeries();

    public static SuperSeries getInstance() {
        return instance;
    }

    private SuperSeries() {
        super("Super Series", "SUS", ExpansionSet.buildDate(1996, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("City of Brass", 6, Rarity.SPECIAL, mage.cards.c.CityOfBrass.class));
        cards.add(new SetCardInfo("Crusade", 4, Rarity.SPECIAL, mage.cards.c.Crusade.class));
        cards.add(new SetCardInfo("Elvish Champion", 17, Rarity.SPECIAL, mage.cards.e.ElvishChampion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elvish Champion", 26, Rarity.SPECIAL, mage.cards.e.ElvishChampion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elvish Champion", 32, Rarity.SPECIAL, mage.cards.e.ElvishChampion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elvish Lyrist", 5, Rarity.COMMON, mage.cards.e.ElvishLyrist.class));
        cards.add(new SetCardInfo("Giant Growth", 8, Rarity.COMMON, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Glorious Anthem", 16, Rarity.SPECIAL, mage.cards.g.GloriousAnthem.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Glorious Anthem", 25, Rarity.SPECIAL, mage.cards.g.GloriousAnthem.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Glorious Anthem", 31, Rarity.SPECIAL, mage.cards.g.GloriousAnthem.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lord of Atlantis", 3, Rarity.SPECIAL, mage.cards.l.LordOfAtlantis.class));
        cards.add(new SetCardInfo("Mad Auntie", 18, Rarity.SPECIAL, mage.cards.m.MadAuntie.class));
        cards.add(new SetCardInfo("Royal Assassin", 20, Rarity.SPECIAL, mage.cards.r.RoyalAssassin.class));
        cards.add(new SetCardInfo("Sakura-Tribe Elder", 12, Rarity.COMMON, mage.cards.s.SakuraTribeElder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sakura-Tribe Elder", 21, Rarity.COMMON, mage.cards.s.SakuraTribeElder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sakura-Tribe Elder", 27, Rarity.COMMON, mage.cards.s.SakuraTribeElder.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Serra Avatar", 2, Rarity.SPECIAL, mage.cards.s.SerraAvatar.class));
        cards.add(new SetCardInfo("Shard Phoenix", 13, Rarity.SPECIAL, mage.cards.s.ShardPhoenix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shard Phoenix", 22, Rarity.SPECIAL, mage.cards.s.ShardPhoenix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shard Phoenix", 28, Rarity.SPECIAL, mage.cards.s.ShardPhoenix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Slith Firewalker", 19, Rarity.SPECIAL, mage.cards.s.SlithFirewalker.class));
        cards.add(new SetCardInfo("Soltari Priest", 14, Rarity.SPECIAL, mage.cards.s.SoltariPriest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Soltari Priest", 23, Rarity.SPECIAL, mage.cards.s.SoltariPriest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Soltari Priest", 29, Rarity.SPECIAL, mage.cards.s.SoltariPriest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thran Quarry", 1, Rarity.SPECIAL, mage.cards.t.ThranQuarry.class));
        cards.add(new SetCardInfo("Two-Headed Dragon", 9, Rarity.SPECIAL, mage.cards.t.TwoHeadedDragon.class));
        cards.add(new SetCardInfo("Volcanic Hammer", 7, Rarity.COMMON, mage.cards.v.VolcanicHammer.class));
        cards.add(new SetCardInfo("Whirling Dervish", 15, Rarity.COMMON, mage.cards.w.WhirlingDervish.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Whirling Dervish", 24, Rarity.COMMON, mage.cards.w.WhirlingDervish.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Whirling Dervish", 30, Rarity.COMMON, mage.cards.w.WhirlingDervish.class, NON_FULL_USE_VARIOUS));
    }
}
