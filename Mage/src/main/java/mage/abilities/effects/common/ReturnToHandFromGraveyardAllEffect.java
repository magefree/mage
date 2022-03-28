package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class ReturnToHandFromGraveyardAllEffect extends OneShotEffect {

    private final FilterCard filter;
    private final TargetController targetController;

    public ReturnToHandFromGraveyardAllEffect(FilterCard filter) {
        this(filter, TargetController.EACH_PLAYER);
    }

    public ReturnToHandFromGraveyardAllEffect(FilterCard filter, TargetController targetController) {
        super(Outcome.ReturnToHand);
        this.filter = filter;
        this.targetController = targetController;
    }

    private ReturnToHandFromGraveyardAllEffect(final ReturnToHandFromGraveyardAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.targetController = effect.targetController;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            switch (targetController) {
                case ANY:
                case EACH_PLAYER:
                    break;
                case OPPONENT:
                    if (!controller.hasOpponent(playerId, game)) {
                        continue;
                    }
                case YOU:
                    if (!controller.getId().equals(playerId)) {
                        continue;
                    }
            }
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            player.moveCards(player.getGraveyard().getCards(
                    filter, player.getId(), source, game
            ), Zone.HAND, source, game);
        }
        return true;
    }

    @Override
    public ReturnToHandFromGraveyardAllEffect copy() {
        return new ReturnToHandFromGraveyardAllEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        switch (targetController) {
            case OPPONENT:
                return "each opponent returns each " + filter.getMessage() + " from their graveyard to their hand";
            case YOU:
                return "return each " + filter.getMessage() + " from your graveyard to your hand";
            default:
                return "each player returns each " + filter.getMessage() + " from their graveyard to their hand";
        }
    }
}
