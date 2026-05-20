package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Controllable;
import mage.game.Game;
import mage.players.Player;

import java.util.Optional;
import java.util.Set;

/**
 * @author TheElk801
 */
public enum OpponentHasMoreCardsInHandCondition implements Condition {
    instance;

    private static final Hint hint = new ConditionHint(instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int cardsInHand = Optional
                .ofNullable(source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .map(Player::getHand)
                .map(Set::size)
                .orElse(0);
        return game.getOpponents(source.getControllerId())
                .stream()
                .map(game::getPlayer)
                .map(Player::getHand)
                .mapToInt(Set::size)
                .anyMatch(x -> x > cardsInHand);
    }

    @Override
    public String toString() {
        return "an opponent has more cards in hand than you";
    }
}
