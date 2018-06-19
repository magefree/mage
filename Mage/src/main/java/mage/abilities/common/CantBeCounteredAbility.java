

package mage.abilities.common;

import mage.abilities.StaticAbility;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CantBeCounteredAbility extends StaticAbility {

    public CantBeCounteredAbility() {
        super(Zone.STACK, new CantBeCounteredSourceEffect());
    }

    public CantBeCounteredAbility(CantBeCounteredAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "This spell can't be countered.";
    }

    @Override
    public CantBeCounteredAbility copy() {
        return new CantBeCounteredAbility(this);
    }
}
