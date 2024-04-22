

package mage.abilities.effects.common;

import java.util.UUID;

import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class RemoveDelayedTriggeredAbilityEffect extends OneShotEffect {

    protected UUID abilityId;

    public RemoveDelayedTriggeredAbilityEffect(UUID abilityId) {
        super(Outcome.Neutral);
        this.abilityId = abilityId;
        staticText = "remove triggered ability";
    }

    protected RemoveDelayedTriggeredAbilityEffect(final RemoveDelayedTriggeredAbilityEffect effect) {
        super(effect);
        this.abilityId = effect.abilityId;
    }

    @Override
    public RemoveDelayedTriggeredAbilityEffect copy() {
        return new RemoveDelayedTriggeredAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getState().removeDelayedTriggeredAbility(abilityId);
        return true;
    }

}
