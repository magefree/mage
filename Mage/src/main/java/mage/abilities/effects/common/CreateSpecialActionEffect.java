package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpecialAction;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CreateSpecialActionEffect extends OneShotEffect {

    protected SpecialAction action;

    public CreateSpecialActionEffect(SpecialAction action) {
        super(action.getEffects().getOutcome(action));
        this.action = action;
    }

    public CreateSpecialActionEffect(final CreateSpecialActionEffect effect) {
        super(effect);
        this.action = (SpecialAction) effect.action.copy();
    }

    @Override
    public CreateSpecialActionEffect copy() {
        return new CreateSpecialActionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SpecialAction newAction = (SpecialAction) action.copy();
        newAction.setSourceId(source.getSourceId());
        newAction.setControllerId(source.getControllerId());
        newAction.getTargets().addAll(source.getTargets());
        game.getState().getSpecialActions().add(newAction);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return action.getRule();
    }

}
