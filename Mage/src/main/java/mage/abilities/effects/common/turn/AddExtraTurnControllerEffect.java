
package mage.abilities.effects.common.turn;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseGameSourceControllerEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.turn.TurnMod;
import mage.players.Player;

/**
 * @author noxx
 */
public class AddExtraTurnControllerEffect extends OneShotEffect {

    private final boolean loseGameAtEnd;

    public AddExtraTurnControllerEffect() {
        this(false);
    }

    public AddExtraTurnControllerEffect(boolean loseGameAtEnd) {
        super(loseGameAtEnd ? Outcome.AIDontUseIt : Outcome.ExtraTurn);
        this.loseGameAtEnd = loseGameAtEnd;
        staticText = "take an extra turn after this one";
        if (loseGameAtEnd) {
            staticText += ". At the beginning of that turn's end step, you lose the game";
        }
    }

    public AddExtraTurnControllerEffect(final AddExtraTurnControllerEffect effect) {
        super(effect);
        this.loseGameAtEnd = effect.loseGameAtEnd;
    }

    @Override
    public AddExtraTurnControllerEffect copy() {
        return new AddExtraTurnControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            TurnMod extraTurn = new TurnMod(player.getId(), false);
            game.getState().getTurnMods().add(extraTurn);
            if (loseGameAtEnd) {
                LoseGameDelayedTriggeredAbility delayedTriggeredAbility = new LoseGameDelayedTriggeredAbility();
                delayedTriggeredAbility.setConnectedTurnMod(extraTurn.getId());
                game.addDelayedTriggeredAbility(delayedTriggeredAbility, source);
            }
        }
        return true;
    }

}

class LoseGameDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private UUID connectedTurnMod;

    public LoseGameDelayedTriggeredAbility() {
        super(new LoseGameSourceControllerEffect(), Duration.EndOfGame);
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
        return event.getType() == EventType.END_TURN_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return connectedTurnMod != null && connectedTurnMod.equals(game.getState().getTurnId());
    }

    public void setConnectedTurnMod(UUID connectedTurnMod) {
        this.connectedTurnMod = connectedTurnMod;
    }

    @Override
    public String getRule() {
        return "At the beginning of that turn's end step, you lose the game";
    }
}
