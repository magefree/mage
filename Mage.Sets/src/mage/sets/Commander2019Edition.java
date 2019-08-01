package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Commander2019Edition extends ExpansionSet {

    private static final Commander2019Edition instance = new Commander2019Edition();

    public static Commander2019Edition getInstance() {
        return instance;
    }

    private Commander2019Edition() {
        super("Commander 2019 Edition", "C19", ExpansionSet.buildDate(2019, 8, 23), SetType.SUPPLEMENTAL);
        this.blockName = "Command Zone";

        cards.add(new SetCardInfo("Seedborn Muse", 179, Rarity.RARE, mage.cards.s.SeedbornMuse.class));
    }
}
