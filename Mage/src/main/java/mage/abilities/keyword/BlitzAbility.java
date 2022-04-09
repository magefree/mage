package mage.abilities.keyword;

import mage.abilities.StaticAbility;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public class BlitzAbility extends StaticAbility {

    public BlitzAbility(String manaString) {
        // TODO: Implement this
        super(Zone.ALL, null);
    }

    private BlitzAbility(final BlitzAbility ability) {
        super(ability);
    }

    @Override
    public BlitzAbility copy() {
        return new BlitzAbility(this);
    }
}
