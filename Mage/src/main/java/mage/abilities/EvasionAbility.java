package mage.abilities;

import mage.constants.AbilityType;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class EvasionAbility extends StaticAbility {

    public EvasionAbility() {
        this(Zone.ALL);
    }

    public EvasionAbility(Zone zone) {
        super(AbilityType.EVASION, zone);
    }

    public EvasionAbility(final EvasionAbility ability) {
        super(ability);
    }

}
