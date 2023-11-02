
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author Susucr
 */
public final class JurassicWorldCollection extends ExpansionSet {

    private static final JurassicWorldCollection instance = new JurassicWorldCollection();

    public static JurassicWorldCollection getInstance() {
        return instance;
    }

    private JurassicWorldCollection() {
        super("Jurassic World Collection", "REX", ExpansionSet.buildDate(2023, 11, 17), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Command Tower", 26, Rarity.COMMON, mage.cards.c.CommandTower.class));
    }
}
