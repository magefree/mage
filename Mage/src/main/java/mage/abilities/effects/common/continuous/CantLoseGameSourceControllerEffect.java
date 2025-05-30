package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class CantLoseGameSourceControllerEffect extends ContinuousRuleModifyingEffectImpl {

    public CantLoseGameSourceControllerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, false, false);
        staticText = "you can't lose the game and your opponents can't win the game";
    }

    private CantLoseGameSourceControllerEffect(final CantLoseGameSourceControllerEffect effect) {
        super(effect);
    }

    @Override
    public CantLoseGameSourceControllerEffect copy() {
        return new CantLoseGameSourceControllerEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case WINS:
            case LOSES:
                return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        switch (event.getType()) {
            case WINS:
                return game.getOpponents(source.getControllerId()).contains(event.getPlayerId());
            case LOSES:
                return source.isControlledBy(event.getPlayerId());
        }
        return false;
    }
}
