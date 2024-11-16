package mage.abilities;

import mage.abilities.effects.Effect;
import mage.constants.AbilityType;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class StaticAbility extends AbilityImpl {

    protected StaticAbility(AbilityType abilityType, Zone zone) {
        super(abilityType, zone);
    }

    public StaticAbility(Zone zone, Effect effect) {
        super(AbilityType.STATIC, zone);
        if (effect != null) {
            this.addEffect(effect);
        }
    }

    protected StaticAbility(final StaticAbility ability) {
        super(ability);
    }
}
