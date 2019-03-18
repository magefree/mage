
package mage.abilities.effects.common.discard;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author mluds
 */
public class DiscardHandAllEffect extends OneShotEffect {

    public DiscardHandAllEffect() {
        super(Outcome.Discard);
        this.staticText = "Each player discards their hand";
    }

    public DiscardHandAllEffect(final DiscardHandAllEffect effect) {
        super(effect);
    }

    @Override
    public DiscardHandAllEffect copy() {
        return new DiscardHandAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                Set<Card> cards = player.getHand().getCards(game);
                for (Card card : cards) {
                    player.discard(card, source, game);
                }
            }
        }
        return true;
    }
}
