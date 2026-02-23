package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pf26
 *
 * @author muz
 */
public class MagicFest2026 extends ExpansionSet {

    private static final MagicFest2026 instance = new MagicFest2026();

    public static MagicFest2026 getInstance() {
        return instance;
    }

    private MagicFest2026() {
        super("MagicFest 2026", "PF26", ExpansionSet.buildDate(2026, 1, 1), SetType.PROMOTIONAL);
        hasBasicLands = false;

        cards.add(new SetCardInfo("Wayfarer's Bauble", "1F", Rarity.RARE, mage.cards.w.WayfarersBauble.class, FULL_ART));
    }
}
