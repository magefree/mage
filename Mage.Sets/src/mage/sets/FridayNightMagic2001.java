package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/f01
 */
public class FridayNightMagic2001 extends ExpansionSet {

    private static final FridayNightMagic2001 instance = new FridayNightMagic2001();

    public static FridayNightMagic2001 getInstance() {
        return instance;
    }

    private FridayNightMagic2001() {
        super("Friday Night Magic 2001", "F01", ExpansionSet.buildDate(2001, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Carnophage", 10, Rarity.RARE, mage.cards.c.Carnophage.class));
        cards.add(new SetCardInfo("Fireblast", 12, Rarity.RARE, mage.cards.f.Fireblast.class));
        cards.add(new SetCardInfo("Impulse", 11, Rarity.RARE, mage.cards.i.Impulse.class));
        cards.add(new SetCardInfo("Jackal Pup", 8, Rarity.RARE, mage.cards.j.JackalPup.class));
        cards.add(new SetCardInfo("Ophidian", 7, Rarity.RARE, mage.cards.o.Ophidian.class));
        cards.add(new SetCardInfo("Quirion Ranger", 9, Rarity.RARE, mage.cards.q.QuirionRanger.class));
        cards.add(new SetCardInfo("Swords to Plowshares", 6, Rarity.RARE, mage.cards.s.SwordsToPlowshares.class));
     }
}
