

package mage.abilities.effects;

import mage.constants.EffectType;
import mage.constants.Outcome;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class OneShotEffect extends EffectImpl {

    public OneShotEffect(Outcome outcome) {
        super(outcome);
        this.effectType = EffectType.ONESHOT;
    }

    protected OneShotEffect(final OneShotEffect effect) {
        super(effect);
    }

    @Override
    public OneShotEffect setText(String staticText) {
        super.setText(staticText);
        return this;
    }
}
