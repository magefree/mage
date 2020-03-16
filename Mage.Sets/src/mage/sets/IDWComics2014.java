package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pi14
 */
public class IDWComics2014 extends ExpansionSet {

    private static final IDWComics2014 instance = new IDWComics2014();

    public static IDWComics2014 getInstance() {
        return instance;
    }

    private IDWComics2014() {
        super("IDW Comics 2014", "PI14", ExpansionSet.buildDate(2014, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Acquire", 16, Rarity.RARE, mage.cards.a.Acquire.class));
        cards.add(new SetCardInfo("Duress", 17, Rarity.RARE, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Wash Out", 15, Rarity.RARE, mage.cards.w.WashOut.class));
     }
}
