package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pl22
 */
public class YearOfTheTiger2022 extends ExpansionSet {

    private static final YearOfTheTiger2022 instance = new YearOfTheTiger2022();

    public static YearOfTheTiger2022 getInstance() {
        return instance;
    }

    private YearOfTheTiger2022() {
        super("Year of the Tiger 2022", "PL22", ExpansionSet.buildDate(2022, 2, 25), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Herald's Horn", 5, Rarity.RARE, mage.cards.h.HeraldsHorn.class));
        cards.add(new SetCardInfo("Jedit Ojanen", 2, Rarity.RARE, mage.cards.j.JeditOjanen.class));
//        cards.add(new SetCardInfo("Snapdax, Apex of the Hunt", 3, Rarity.RARE, mage.cards.s.SnapdaxApexOfTheHunt.class));
        cards.add(new SetCardInfo("Temur Sabertooth", 1, Rarity.RARE, mage.cards.t.TemurSabertooth.class));
        cards.add(new SetCardInfo("Yuriko, the Tiger's Shadow", 4, Rarity.RARE, mage.cards.y.YurikoTheTigersShadow.class));
    }
}
