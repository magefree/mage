package mage.abilities.effects.common;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ShuffleHandGraveyardAllEffect extends OneShotEffect {

    public ShuffleHandGraveyardAllEffect() {
        super(Outcome.Neutral);
        staticText = "each player shuffles their hand and graveyard into their library";
    }

    protected ShuffleHandGraveyardAllEffect(final ShuffleHandGraveyardAllEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.moveCards(player.getHand(), Zone.LIBRARY, source, game);
                player.moveCards(player.getGraveyard(), Zone.LIBRARY, source, game);
                player.shuffleLibrary(source, game);
            }
        }
        return true;
    }

    @Override
    public ShuffleHandGraveyardAllEffect copy() {
        return new ShuffleHandGraveyardAllEffect(this);
    }

}
