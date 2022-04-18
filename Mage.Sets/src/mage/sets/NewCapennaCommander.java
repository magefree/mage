package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class NewCapennaCommander extends ExpansionSet {

    private static final NewCapennaCommander instance = new NewCapennaCommander();

    public static NewCapennaCommander getInstance() {
        return instance;
    }

    private NewCapennaCommander() {
        super("New Capenna Commander", "NCC", ExpansionSet.buildDate(2022, 4, 29), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Kitt Kanto, Mayhem Diva", 4, Rarity.MYTHIC, mage.cards.k.KittKantoMayhemDiva.class));
    }
}
