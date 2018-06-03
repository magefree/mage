
package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ReturnToHandFromGraveyardAllEffect extends OneShotEffect {

    private final FilterCard filter;

    public ReturnToHandFromGraveyardAllEffect(FilterCard filter) {
        super(Outcome.ReturnToHand);
        this.filter = filter;
        staticText = "Each player returns all " + filter.getMessage() + " from their graveyard to their hand";
    }

    public ReturnToHandFromGraveyardAllEffect(final ReturnToHandFromGraveyardAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    for (Card card : player.getGraveyard().getCards(filter, source.getSourceId(), source.getControllerId(), game)) {
                        card.moveToZone(Zone.HAND, playerId, game, false);
                    }
                }

            }
            return true;
        }
        return false;
    }

    @Override
    public ReturnToHandFromGraveyardAllEffect copy() {
        return new ReturnToHandFromGraveyardAllEffect(this);
    }
}
