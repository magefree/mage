package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pgtw
 */
public class Gateway2006 extends ExpansionSet {

    private static final Gateway2006 instance = new Gateway2006();

    public static Gateway2006 getInstance() {
        return instance;
    }

    private Gateway2006() {
        super("Gateway 2006", "PGTW", ExpansionSet.buildDate(2006, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Fiery Temper", 3, Rarity.RARE, mage.cards.f.FieryTemper.class));
        cards.add(new SetCardInfo("Icatian Javelineers", 2, Rarity.RARE, mage.cards.i.IcatianJavelineers.class));
        cards.add(new SetCardInfo("Wood Elves", 1, Rarity.RARE, mage.cards.w.WoodElves.class));
     }
}
