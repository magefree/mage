package mage.abilities.effects.common;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

public class DiscardEachPlayerEffect extends OneShotEffect<DiscardEachPlayerEffect> {
    public DiscardEachPlayerEffect() {
        super(Constants.Outcome.Discard);
        staticText = "each player discards a card";
    }

    public DiscardEachPlayerEffect(final DiscardEachPlayerEffect effect) {
        super(effect);
    }


    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getPlayerList()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.discard(1, source, game);
            }
        }
        return true;
    }

    @Override
    public DiscardEachPlayerEffect copy() {
        return new DiscardEachPlayerEffect(this);
    }

}
