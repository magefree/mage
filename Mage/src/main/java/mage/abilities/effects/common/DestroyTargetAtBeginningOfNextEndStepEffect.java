
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LoneFox
 */
public class DestroyTargetAtBeginningOfNextEndStepEffect extends OneShotEffect {

    public DestroyTargetAtBeginningOfNextEndStepEffect() {
        super(Outcome.Detriment);
        this.staticText = "Destroy that creature at the beginning of the next end step";
    }

    public DestroyTargetAtBeginningOfNextEndStepEffect(final DestroyTargetAtBeginningOfNextEndStepEffect effect) {
        super(effect);
    }

    @Override
    public DestroyTargetAtBeginningOfNextEndStepEffect copy() {
        return new DestroyTargetAtBeginningOfNextEndStepEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DestroyTargetEffect effect = new DestroyTargetEffect();
        effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
        AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }
}
