

package mage.abilities.common;

import mage.abilities.StaticAbility;
import mage.abilities.effects.Effect;
import mage.constants.Zone;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class SimpleStaticAbility extends StaticAbility {

    public SimpleStaticAbility(Effect effect) {
        this(Zone.BATTLEFIELD, effect);
    }

    public SimpleStaticAbility(Zone zone, Effect effect) {
        super(zone, effect);
    }

    protected SimpleStaticAbility(final SimpleStaticAbility ability) {
        super(ability);
    }

    @Override
    public SimpleStaticAbility copy() {
        return new SimpleStaticAbility(this);
    }

}
