

package mage.abilities;

import mage.constants.AbilityType;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class EvasionAbility extends StaticAbility {

    public EvasionAbility() {
        super(AbilityType.EVASION, Zone.ALL); 
    }

    public EvasionAbility(final EvasionAbility ability) {
        super(ability);
    }

}
