package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pwos
 */
public class WizardsOfTheCoastOnlineStore extends ExpansionSet {

    private static final WizardsOfTheCoastOnlineStore instance = new WizardsOfTheCoastOnlineStore();

    public static WizardsOfTheCoastOnlineStore getInstance() {
        return instance;
    }

    private WizardsOfTheCoastOnlineStore() {
        super("Wizards of the Coast Online Store", "PWOS", ExpansionSet.buildDate(1999, 9, 4), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Serra Angel", 1, Rarity.RARE, mage.cards.s.SerraAngel.class));
     }
}
