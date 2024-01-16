package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class MurdersAtKarlovManorCommander extends ExpansionSet {

    private static final MurdersAtKarlovManorCommander instance = new MurdersAtKarlovManorCommander();

    public static MurdersAtKarlovManorCommander getInstance() {
        return instance;
    }

    private MurdersAtKarlovManorCommander() {
        super("Murders at Karlov Manor Commander", "MKC", ExpansionSet.buildDate(2024, 2, 9), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Morska, Undersea Sleuth", 3, Rarity.MYTHIC, mage.cards.m.MorskaUnderseaSleuth.class));
    }
}
