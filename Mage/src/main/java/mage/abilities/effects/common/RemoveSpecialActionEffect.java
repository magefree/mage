

package mage.abilities.effects.common;

import java.util.UUID;

import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class RemoveSpecialActionEffect extends OneShotEffect {

    protected UUID actionId;

    public RemoveSpecialActionEffect(UUID actionId) {
        super(Outcome.Neutral);
        this.actionId = actionId;
    }

    protected RemoveSpecialActionEffect(final RemoveSpecialActionEffect effect) {
        super(effect);
        this.actionId = effect.actionId;
    }

    @Override
    public RemoveSpecialActionEffect copy() {
        return new RemoveSpecialActionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (SpecialAction action : game.getState().getSpecialActions()) {
            if (action.getId().equals(actionId)) {
                game.getState().getSpecialActions().remove(action);
                break;
            }
        }
        return true;
    }

}
