package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pf27
 *
 * @author muz
 */
public class MagicFest2027 extends ExpansionSet {

    private static final MagicFest2027 instance = new MagicFest2027();

    public static MagicFest2027 getInstance() {
        return instance;
    }

    private MagicFest2027() {
        super("MagicFest 2027", "PF27", ExpansionSet.buildDate(2027, 1, 1), SetType.PROMOTIONAL);
        hasBasicLands = false;

        cards.add(new SetCardInfo("Farseek", "1", Rarity.RARE, mage.cards.f.Farseek.class, FULL_ART));
    }
}
