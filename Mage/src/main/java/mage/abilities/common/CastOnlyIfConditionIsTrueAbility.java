
package mage.abilities.common;

import mage.abilities.condition.Condition;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public class CastOnlyIfConditionIsTrueAbility extends SimpleStaticAbility {

    public CastOnlyIfConditionIsTrueAbility(Condition condition) {
        this(condition, null);
    }

    public CastOnlyIfConditionIsTrueAbility(Condition condition, String effectText) {
        super(Zone.ALL, new CastOnlyIfConditionIsTrueEffect(condition));
        this.setRuleAtTheTop(true);
        if (effectText != null) {
            getEffects().get(0).setText(effectText);
        }
    }

    private CastOnlyIfConditionIsTrueAbility(final CastOnlyIfConditionIsTrueAbility ability) {
        super(ability);
    }

    @Override
    public CastOnlyIfConditionIsTrueAbility copy() {
        return new CastOnlyIfConditionIsTrueAbility(this);
    }
}
