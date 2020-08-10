package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pwor
 */
public class Worlds extends ExpansionSet {

    private static final Worlds instance = new Worlds();

    public static Worlds getInstance() {
        return instance;
    }

    private Worlds() {
        super("Worlds", "PWOR", ExpansionSet.buildDate(1999, 8, 4), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Balduvian Horde", 1, Rarity.RARE, mage.cards.b.BalduvianHorde.class));
     }
}
