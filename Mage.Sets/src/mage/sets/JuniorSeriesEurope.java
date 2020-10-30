package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pjse
 */
public class JuniorSeriesEurope extends ExpansionSet {

    private static final JuniorSeriesEurope instance = new JuniorSeriesEurope();

    public static JuniorSeriesEurope getInstance() {
        return instance;
    }

    private JuniorSeriesEurope() {
        super("Junior Series Europe", "PJSE", ExpansionSet.buildDate(2008, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Elvish Champion", "2E08", Rarity.RARE, mage.cards.e.ElvishChampion.class));
        cards.add(new SetCardInfo("Glorious Anthem", "1E08", Rarity.RARE, mage.cards.g.GloriousAnthem.class));
        cards.add(new SetCardInfo("Royal Assassin", "2E05", Rarity.RARE, mage.cards.r.RoyalAssassin.class));
        cards.add(new SetCardInfo("Sakura-Tribe Elder", "1E06", Rarity.RARE, mage.cards.s.SakuraTribeElder.class));
        cards.add(new SetCardInfo("Shard Phoenix", "2E06", Rarity.RARE, mage.cards.s.ShardPhoenix.class));
        cards.add(new SetCardInfo("Slith Firewalker", "1E05", Rarity.RARE, mage.cards.s.SlithFirewalker.class));
        cards.add(new SetCardInfo("Soltari Priest", "1E07", Rarity.RARE, mage.cards.s.SoltariPriest.class));
        cards.add(new SetCardInfo("Whirling Dervish", "2E07", Rarity.RARE, mage.cards.w.WhirlingDervish.class));
     }
}
