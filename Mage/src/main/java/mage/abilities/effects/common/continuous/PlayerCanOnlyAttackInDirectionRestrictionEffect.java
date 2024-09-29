package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;

import java.util.UUID;

/**
 * @author TheElk801, Susucr
 */
public class PlayerCanOnlyAttackInDirectionRestrictionEffect extends RestrictionEffect {

    public static final String ALLOW_ATTACKING_LEFT = "Allow attacking left";
    public static final String ALLOW_ATTACKING_RIGHT = "Allow attacking right";

    public PlayerCanOnlyAttackInDirectionRestrictionEffect(Duration duration, String directionText) {
        super(duration, Outcome.Neutral);
        staticText = duration + (duration.toString().isEmpty() ? "" : ", ")
                + "each player may attack only the nearest opponent "
                + "in " + directionText + " and planeswalkers controlled by that opponent";
    }

    private PlayerCanOnlyAttackInDirectionRestrictionEffect(PlayerCanOnlyAttackInDirectionRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public PlayerCanOnlyAttackInDirectionRestrictionEffect copy() {
        return new PlayerCanOnlyAttackInDirectionRestrictionEffect(this);
    }

    public static Effect choiceEffect() {
        return new ChooseModeEffect(
                "Choose a direction to allow attacking in.",
                ALLOW_ATTACKING_LEFT, ALLOW_ATTACKING_RIGHT
        ).setText("choose left or right");
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null) {
            return true;
        }

        String allowedDirection = (String) game.getState().getValue(source.getSourceId() + "_modeChoice");
        if (allowedDirection == null) {
            return true; // If no choice was made, the ability has no effect.
        }

        Player playerAttacking = game.getPlayer(attacker.getControllerId());
        if (playerAttacking == null) {
            return true;
        }

        // The attacking player should be in range of the source's controller
        if (!game.getState().getPlayersInRange(source.getControllerId(), game).contains(playerAttacking.getId())) {
            return true;
        }

        Player playerDefending = game.getPlayer(defenderId);
        if (playerDefending == null) {
            Permanent planeswalker = game.getPermanent(defenderId);
            if (planeswalker != null && planeswalker.getCardType(game).contains(CardType.PLANESWALKER)) {
                playerDefending = game.getPlayer(planeswalker.getControllerId());
            }
        }
        if (playerDefending == null) {
            return false; // not a planeswalker, either a battle or not on battelfield/game anymore.
        }

        PlayerList playerList = game.getState().getPlayerList(playerAttacking.getId());
        if (allowedDirection.equals(ALLOW_ATTACKING_LEFT)
                && !playerList.getNext().equals(playerDefending.getId())) {
            // the defender is not the player to the left
            return false;
        }
        if (allowedDirection.equals(ALLOW_ATTACKING_RIGHT)
                && !playerList.getPrevious().equals(playerDefending.getId())) {
            // the defender is not the player to the right
            return false;
        }

        return true;
    }

}
