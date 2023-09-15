package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpecialAction;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CreateSpecialActionEffect extends OneShotEffect {

    private final SpecialAction action;
    private final UUID playerId; // If set, that player can activate the special action. If null, use the source controller instead.

    public CreateSpecialActionEffect(SpecialAction action) {
        this(action, null);
    }

    public CreateSpecialActionEffect(SpecialAction action, UUID playerId) {
        super(action.getEffects().getOutcome(action));
        this.action = action;
        this.playerId = playerId;
    }

    protected CreateSpecialActionEffect(final CreateSpecialActionEffect effect) {
        super(effect);
        this.action = (SpecialAction) effect.action.copy();
        this.playerId = effect.playerId;
    }

    @Override
    public CreateSpecialActionEffect copy() {
        return new CreateSpecialActionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SpecialAction newAction = (SpecialAction) action.copy();
        newAction.setSourceId(source.getSourceId());
        newAction.setControllerId(playerId == null ? source.getControllerId() : playerId);
        newAction.getTargets().addAll(source.getTargets());
        game.getState().getSpecialActions().add(newAction);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return action.getRule();
    }

}
