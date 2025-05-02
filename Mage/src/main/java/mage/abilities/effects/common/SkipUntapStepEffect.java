package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 * @author jeffwadsworth
 */

public class SkipUntapStepEffect extends ContinuousRuleModifyingEffectImpl {

    public SkipUntapStepEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral, false, false);
        staticText = "Players skip their untap steps";
    }

    protected SkipUntapStepEffect(final SkipUntapStepEffect effect) {
        super(effect);
    }

    @Override
    public SkipUntapStepEffect copy() {
        return new SkipUntapStepEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP_STEP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        return controller != null
                && game.getState().getPlayersInRange(controller.getId(), game).contains(event.getPlayerId());
    }
}
