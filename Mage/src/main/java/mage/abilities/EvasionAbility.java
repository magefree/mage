package mage.abilities;

import mage.constants.AbilityType;
import mage.constants.Zone;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class EvasionAbility extends StaticAbility {

    public EvasionAbility() {
        this(Zone.BATTLEFIELD);
    }

    public EvasionAbility(Zone zone) {
        super(AbilityType.EVASION, zone);
    }

    protected EvasionAbility(final EvasionAbility ability) {
        super(ability);
    }

}
