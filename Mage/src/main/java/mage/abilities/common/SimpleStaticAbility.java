

package mage.abilities.common;

import mage.constants.Zone;
import mage.abilities.StaticAbility;
import mage.abilities.effects.Effect;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SimpleStaticAbility extends StaticAbility {

    public SimpleStaticAbility(Zone zone, Effect effect) {
        super(zone, effect);
    }

    public SimpleStaticAbility(SimpleStaticAbility ability) {
        super(ability);
    }

    @Override
    public SimpleStaticAbility copy() {
        return new SimpleStaticAbility(this);
    }

}
