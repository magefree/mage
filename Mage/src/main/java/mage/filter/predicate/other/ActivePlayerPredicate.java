package mage.filter.predicate.other;

import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.players.Player;

public enum ActivePlayerPredicate implements Predicate<Player> {
    instance;

    @Override
    public boolean apply(Player input, Game game) {
        return game.isActivePlayer(input.getId());
    }

}
