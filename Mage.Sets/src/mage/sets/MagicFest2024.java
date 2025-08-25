package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pf24
 *
 * @author resech
 */
public class MagicFest2024 extends ExpansionSet {

    private static final MagicFest2024 instance = new MagicFest2024();

    public static MagicFest2024 getInstance() {
        return instance;
    }

    private MagicFest2024() {
        super("MagicFest 2024", "PF24", ExpansionSet.buildDate(2024, 1, 1), SetType.PROMOTIONAL);
        hasBasicLands = false;

        cards.add(new SetCardInfo("Counterspell", 1, Rarity.RARE, mage.cards.c.Counterspell.class, FULL_ART));
    }
}
