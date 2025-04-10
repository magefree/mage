package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.ModeChoice;
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
        return new ChooseModeEffect(ModeChoice.LEFT, ModeChoice.RIGHT);
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
        boolean left;
        if (ModeChoice.LEFT.checkMode(game, source)) {
            left = true;
        } else if (ModeChoice.RIGHT.checkMode(game, source)) {
            left = false;
        } else {
            return false; // If no choice was made, the ability has no effect.
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
        return (!left || playerList.getNext().equals(playerDefending.getId()))
                && (left || playerList.getPrevious().equals(playerDefending.getId()));
    }
}
