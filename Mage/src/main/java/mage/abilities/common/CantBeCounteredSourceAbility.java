

package mage.abilities.common;

import mage.abilities.StaticAbility;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CantBeCounteredSourceAbility extends StaticAbility {

    public CantBeCounteredSourceAbility() {
        super(Zone.STACK, new CantBeCounteredSourceEffect());
    }

    public CantBeCounteredSourceAbility(CantBeCounteredSourceAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return  "This spell can't be countered.";
    }

    @Override
    public CantBeCounteredSourceAbility copy() {
        return new CantBeCounteredSourceAbility(this);
    }
}
