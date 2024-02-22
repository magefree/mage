

package mage.abilities.effects;

import mage.constants.EffectType;
import mage.constants.Outcome;
import mage.target.targetpointer.TargetPointer;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class OneShotEffect extends EffectImpl {

    public OneShotEffect(Outcome outcome) {
        super(outcome);
        this.effectType = EffectType.ONESHOT;
    }

    @Override
    public final void initNewTargetPointer() {
        // one short effects don't use init logic
        this.getTargetPointer().setInitialized();
    }

    protected OneShotEffect(final OneShotEffect effect) {
        super(effect);
    }

    @Override
    public OneShotEffect setText(String staticText) {
        super.setText(staticText);
        return this;
    }

    @Override
    public Effect setTargetPointer(TargetPointer targetPointer) {
        super.setTargetPointer(targetPointer);
        return this;
    }
}
