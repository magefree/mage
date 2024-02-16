package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.other.PlayerWithTheMostLifePredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public enum SourceAttackingPlayerWithMostLifeCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        UUID defenderId = game.getCombat().getDefenderId(source.getSourceId());
        if (defenderId == null) {
            return false;
        }

        Player attackedPlayer = game.getPlayer(defenderId);
        return PlayerWithTheMostLifePredicate.instance.apply(
                new ObjectSourcePlayer<>(attackedPlayer, source.getControllerId(), source),
                game
        );
    }

    @Override
    public String toString() {
        return "{this} is attacking the player with the most life or tied for most life";
    }
}
