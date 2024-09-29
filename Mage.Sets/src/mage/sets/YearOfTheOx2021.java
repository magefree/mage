package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pl21
 */
public class YearOfTheOx2021 extends ExpansionSet {

    private static final YearOfTheOx2021 instance = new YearOfTheOx2021();

    public static YearOfTheOx2021 getInstance() {
        return instance;
    }

    private YearOfTheOx2021() {
        super("Year of the Ox 2021", "PL21", ExpansionSet.buildDate(2021, 1, 25), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Angrath, the Flame-Chained", 3, Rarity.MYTHIC, mage.cards.a.AngrathTheFlameChained.class));
        cards.add(new SetCardInfo("Moraug, Fury of Akoum", 1, Rarity.MYTHIC, mage.cards.m.MoraugFuryOfAkoum.class));
        cards.add(new SetCardInfo("Ox of Agonas", 2, Rarity.MYTHIC, mage.cards.o.OxOfAgonas.class));
        cards.add(new SetCardInfo("Sethron, Hurloon General", "1*", Rarity.RARE, mage.cards.s.SethronHurloonGeneral.class));
        cards.add(new SetCardInfo("Tahngarth, First Mate", 4, Rarity.RARE, mage.cards.t.TahngarthFirstMate.class));
    }
}
