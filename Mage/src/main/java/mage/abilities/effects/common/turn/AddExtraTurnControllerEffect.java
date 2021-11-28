
package mage.abilities.effects.common.turn;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseGameSourceControllerEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.turn.TurnMod;
import mage.players.Player;

import java.util.UUID;

/**
 * @author noxx
 */
public class AddExtraTurnControllerEffect extends OneShotEffect {

    @FunctionalInterface
    public static interface TurnModApplier {
        void apply(UUID turnId, Ability source, Game game);
    }

    private final boolean loseGameAtEnd;
    private final TurnModApplier turnModApplier;

    public AddExtraTurnControllerEffect() {
        this(false);
    }

    public AddExtraTurnControllerEffect(boolean loseGameAtEnd) {
        this(loseGameAtEnd, null);
    }

    public AddExtraTurnControllerEffect(boolean loseGameAtEnd, TurnModApplier turnModApplier) {
        super(loseGameAtEnd ? Outcome.AIDontUseIt : Outcome.ExtraTurn);
        this.loseGameAtEnd = loseGameAtEnd;
        this.turnModApplier = turnModApplier;
    }

    public AddExtraTurnControllerEffect(final AddExtraTurnControllerEffect effect) {
        super(effect);
        this.loseGameAtEnd = effect.loseGameAtEnd;
        this.turnModApplier = effect.turnModApplier;
    }

    @Override
    public AddExtraTurnControllerEffect copy() {
        return new AddExtraTurnControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return true;
        }
        TurnMod extraTurn = new TurnMod(player.getId(), false);
        game.getState().getTurnMods().add(extraTurn);
        if (loseGameAtEnd) {
            game.addDelayedTriggeredAbility(new LoseGameDelayedTriggeredAbility(extraTurn.getId()), source);
        }
        if (turnModApplier != null) {
            turnModApplier.apply(extraTurn.getId(), source, game);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "take an extra turn after this one"
                + (loseGameAtEnd ? ". At the beginning of that turn's end step, you lose the game" : "");
    }
}

class LoseGameDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final UUID connectedTurnMod;

    public LoseGameDelayedTriggeredAbility(UUID connectedTurnMod) {
        super(new LoseGameSourceControllerEffect(), Duration.EndOfGame);
        this.connectedTurnMod = connectedTurnMod;
    }

    public LoseGameDelayedTriggeredAbility(final LoseGameDelayedTriggeredAbility ability) {
        super(ability);
        this.connectedTurnMod = ability.connectedTurnMod;
    }

    @Override
    public LoseGameDelayedTriggeredAbility copy() {
        return new LoseGameDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_TURN_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return connectedTurnMod != null && connectedTurnMod.equals(game.getState().getTurnId());
    }

    @Override
    public String getRule() {
        return "At the beginning of that turn's end step, you lose the game";
    }
}
