package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/psus
 */
public class JuniorSuperSeries extends ExpansionSet {

    private static final JuniorSuperSeries instance = new JuniorSuperSeries();

    public static JuniorSuperSeries getInstance() {
        return instance;
    }

    private JuniorSuperSeries() {
        super("Junior Super Series", "PSUS", ExpansionSet.buildDate(2008, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("City of Brass", 6, Rarity.RARE, mage.cards.c.CityOfBrass.class));
        cards.add(new SetCardInfo("Crusade", 4, Rarity.RARE, mage.cards.c.Crusade.class));
        cards.add(new SetCardInfo("Elvish Champion", 17, Rarity.RARE, mage.cards.e.ElvishChampion.class));
        cards.add(new SetCardInfo("Elvish Lyrist", 5, Rarity.RARE, mage.cards.e.ElvishLyrist.class));
        cards.add(new SetCardInfo("Giant Growth", 8, Rarity.RARE, mage.cards.g.GiantGrowth.class));
        cards.add(new SetCardInfo("Glorious Anthem", 16, Rarity.RARE, mage.cards.g.GloriousAnthem.class));
        cards.add(new SetCardInfo("Lord of Atlantis", 3, Rarity.RARE, mage.cards.l.LordOfAtlantis.class));
        cards.add(new SetCardInfo("Mad Auntie", 18, Rarity.RARE, mage.cards.m.MadAuntie.class));
        cards.add(new SetCardInfo("Royal Assassin", 11, Rarity.RARE, mage.cards.r.RoyalAssassin.class));
        cards.add(new SetCardInfo("Sakura-Tribe Elder", 12, Rarity.RARE, mage.cards.s.SakuraTribeElder.class));
        cards.add(new SetCardInfo("Serra Avatar", 2, Rarity.RARE, mage.cards.s.SerraAvatar.class));
        cards.add(new SetCardInfo("Shard Phoenix", 13, Rarity.RARE, mage.cards.s.ShardPhoenix.class));
        cards.add(new SetCardInfo("Slith Firewalker", 10, Rarity.RARE, mage.cards.s.SlithFirewalker.class));
        cards.add(new SetCardInfo("Soltari Priest", 14, Rarity.RARE, mage.cards.s.SoltariPriest.class));
        cards.add(new SetCardInfo("Thran Quarry", 1, Rarity.RARE, mage.cards.t.ThranQuarry.class));
        cards.add(new SetCardInfo("Two-Headed Dragon", 9, Rarity.RARE, mage.cards.t.TwoHeadedDragon.class));
        cards.add(new SetCardInfo("Volcanic Hammer", 7, Rarity.RARE, mage.cards.v.VolcanicHammer.class));
        cards.add(new SetCardInfo("Whirling Dervish", 15, Rarity.RARE, mage.cards.w.WhirlingDervish.class));
     }
}
