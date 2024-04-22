
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * Custom version of the official cards.
 * Similar to Alchemy tweaks of cards.
 * @author Susucr
 */
public final class CustomAlchemy extends ExpansionSet {

    private static final CustomAlchemy instance = new CustomAlchemy();

    public static CustomAlchemy getInstance() {
        return instance;
    }

    private CustomAlchemy() {
        super("Custom Alchemy", "CALC", ExpansionSet.buildDate(2023, 9, 24), SetType.CUSTOM_SET);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("C-Pillar of the Paruns", 1, Rarity.SPECIAL, mage.cards.p.PillarOfTheParunsCustom.class));
    }

}
