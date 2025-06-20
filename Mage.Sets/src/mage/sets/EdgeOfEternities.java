package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class EdgeOfEternities extends ExpansionSet {

    private static final EdgeOfEternities instance = new EdgeOfEternities();

    public static EdgeOfEternities getInstance() {
        return instance;
    }

    private EdgeOfEternities() {
        super("Edge of Eternities", "EOE", ExpansionSet.buildDate(2025, 8, 1), SetType.EXPANSION);
        this.blockName = "Edge of Eternities"; // for sorting in GUI

        cards.add(new SetCardInfo("Tezzeret, Cruel Captain", 2, Rarity.MYTHIC, mage.cards.t.TezzeretCruelCaptain.class));
    }
}
