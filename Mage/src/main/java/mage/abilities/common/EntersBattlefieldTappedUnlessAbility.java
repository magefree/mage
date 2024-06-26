
package mage.abilities.common;

import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TapSourceEffect;

/**
 * @author Susucr
 */
public class EntersBattlefieldTappedUnlessAbility extends EntersBattlefieldAbility {

    public EntersBattlefieldTappedUnlessAbility(Condition condition) {
        this(condition, condition.toString());
    }

    public EntersBattlefieldTappedUnlessAbility(Condition condition, String conditionText) {
        super(
                new ConditionalOneShotEffect(null, new TapSourceEffect(true), condition, null),
                "tapped unless " + conditionText
        );
    }

    private EntersBattlefieldTappedUnlessAbility(final EntersBattlefieldTappedUnlessAbility ability) {
        super(ability);
    }

    @Override
    public EntersBattlefieldTappedUnlessAbility copy() {
        return new EntersBattlefieldTappedUnlessAbility(this);
    }
}
