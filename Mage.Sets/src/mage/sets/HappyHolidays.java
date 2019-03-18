package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author spjspj
 */
public final class HappyHolidays extends ExpansionSet {

    private static final HappyHolidays instance = new HappyHolidays();

    public static HappyHolidays getInstance() {
        return instance;
    }

    private HappyHolidays() {
        super("Happy Holidays", "HHO", ExpansionSet.buildDate(2006, 12, 31), SetType.JOKESET);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Fruitcake Elemental", 6, Rarity.RARE, mage.cards.f.FruitcakeElemental.class));
        cards.add(new SetCardInfo("Season's Beatings", 9, Rarity.RARE, mage.cards.s.SeasonsBeatings.class));
        cards.add(new SetCardInfo("Snow Mercy", 10, Rarity.RARE, mage.cards.s.SnowMercy.class));
    }
}
