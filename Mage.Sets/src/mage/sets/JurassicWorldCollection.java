
package mage.sets;

import mage.cards.ExpansionSet;
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
    }
}
