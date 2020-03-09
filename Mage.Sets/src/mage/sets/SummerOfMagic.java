package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/psum
 * This is not the "Summer Magic" Revised printing.
 */
public class SummerOfMagic extends ExpansionSet {

    private static final SummerOfMagic instance = new SummerOfMagic();

    public static SummerOfMagic getInstance() {
        return instance;
    }

    private SummerOfMagic() {
        super("Summer of Magic", "PSUM", ExpansionSet.buildDate(2007, 7, 21), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Faerie Conclave", 1, Rarity.RARE, mage.cards.f.FaerieConclave.class));
        cards.add(new SetCardInfo("Treetop Village", 2, Rarity.RARE, mage.cards.t.TreetopVillage.class));
     }
}
