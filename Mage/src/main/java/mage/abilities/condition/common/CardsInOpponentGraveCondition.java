package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;
import mage.game.Graveyard;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * Condition for -
 * Any opponent has X or more cards in their graveyard
 *
 * @author TheElk801
 */
public enum CardsInOpponentGraveCondition implements Condition {
    SEVEN(7),
    EIGHT(8),
    TEN(10);

    private final int value;
    private final Hint hint;

    CardsInOpponentGraveCondition(int value) {
        this.value = value;
        this.hint = new ConditionHint(this, "O" + this.makeStem());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getOpponents(source.getControllerId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .mapToInt(Graveyard::size)
                .anyMatch(i -> i >= this.value);
    }

    @Override
    public String toString() {
        return "an o" + this.makeStem();
    }

    private String makeStem() {
        return "pponent has " + CardUtil.numberToText(value) + " or more cards in their graveyard";
    }

    public Hint getHint() {
        return hint;
    }
}
