package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pjas
 */
public class JuniorAPACSeries extends ExpansionSet {

    private static final JuniorAPACSeries instance = new JuniorAPACSeries();

    public static JuniorAPACSeries getInstance() {
        return instance;
    }

    private JuniorAPACSeries() {
        super("Junior APAC Series", "PJAS", ExpansionSet.buildDate(2008, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Elvish Champion", "2U08", Rarity.RARE, mage.cards.e.ElvishChampion.class));
        cards.add(new SetCardInfo("Glorious Anthem", "1U08", Rarity.RARE, mage.cards.g.GloriousAnthem.class));
        cards.add(new SetCardInfo("Sakura-Tribe Elder", "1U06", Rarity.RARE, mage.cards.s.SakuraTribeElder.class));
        cards.add(new SetCardInfo("Shard Phoenix", "2U06", Rarity.RARE, mage.cards.s.ShardPhoenix.class));
        cards.add(new SetCardInfo("Soltari Priest", "1U07", Rarity.RARE, mage.cards.s.SoltariPriest.class));
        cards.add(new SetCardInfo("Whirling Dervish", "2U07", Rarity.RARE, mage.cards.w.WhirlingDervish.class));
     }
}
