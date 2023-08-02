package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;

/**
 * @author TheElk801, Susucr
 */
public class PlayerCanOnlyAttackInDirectionReplacementEffect extends ReplacementEffectImpl {

    static final String ALLOW_ATTACKING_LEFT = "Allow attacking left";
    static final String ALLOW_ATTACKING_RIGHT = "Allow attacking right";

    public PlayerCanOnlyAttackInDirectionReplacementEffect(Duration duration, String directionText) {
        super(duration, Outcome.Neutral);
        staticText = duration + (duration.toString().isEmpty() ? "" : ", ")
                + "each player may attack only the nearest opponent "
                + "in " + directionText + " and planeswalkers controlled by that opponent";
    }

    private PlayerCanOnlyAttackInDirectionReplacementEffect(PlayerCanOnlyAttackInDirectionReplacementEffect effect) {
        super(effect);
    }

    @Override
    public PlayerCanOnlyAttackInDirectionReplacementEffect copy() {
        return new PlayerCanOnlyAttackInDirectionReplacementEffect(this);
    }

    public static Effect choiceEffect() {
        return new ChooseModeEffect(
                "Choose a direction to allow attacking in.",
                ALLOW_ATTACKING_LEFT, ALLOW_ATTACKING_RIGHT
        ).setText("choose left or right");
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARE_ATTACKER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getPlayers().size() > 2) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller == null) {
                return false;
            }
            if (!game.getState().getPlayersInRange(controller.getId(), game).contains(event.getPlayerId())) {
                return false;
            }
            String allowedDirection = (String) game.getState().getValue(source.getSourceId() + "_modeChoice");
            if (allowedDirection == null) {
                return false;
            }
            Player defender = game.getPlayer(event.getTargetId());
            if (defender == null) {
                Permanent planeswalker = game.getPermanent(event.getTargetId());
                if (planeswalker != null && planeswalker.getCardType(game).contains(CardType.PLANESWALKER)) {
                    defender = game.getPlayer(planeswalker.getControllerId());
                }
            }
            if (defender == null) {
                return false;
            }
            PlayerList playerList = game.getState().getPlayerList(event.getPlayerId());
            if (allowedDirection.equals(ALLOW_ATTACKING_LEFT)
                    && !playerList.getNext().equals(defender.getId())) {
                // the defender is not the player to the left
                Player attacker = game.getPlayer(event.getPlayerId());
                if (attacker != null) {
                    game.informPlayer(attacker, "You can only attack to the left!");
                }
                return true;
            }
            if (allowedDirection.equals(ALLOW_ATTACKING_RIGHT)
                    && !playerList.getPrevious().equals(defender.getId())) {
                // the defender is not the player to the right
                Player attacker = game.getPlayer(event.getPlayerId());
                if (attacker != null) {
                    game.informPlayer(attacker, "You can only attack to the right!");
                }
                return true;
            }
        }
        return false;
    }

}
