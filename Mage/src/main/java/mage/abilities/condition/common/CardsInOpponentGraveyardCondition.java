package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.hint.Hint;
import mage.abilities.hint.HintUtils;
import mage.game.Game;
import mage.game.Graveyard;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Objects;

/**
 * Condition for -
 * Any opponent has X or more cards in their graveyard
 *
 * @author TheElk801
 */
public enum CardsInOpponentGraveyardCondition implements Condition {
    SEVEN(7),
    EIGHT(8),
    TEN(10);

    private final int value;
    private final Hint hint;

    CardsInOpponentGraveyardCondition(int value) {
        this.value = value;
        this.hint = new CardsInOpponentGraveyardConditionHint(value);
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
        return "an opponent has " + CardUtil.numberToText(value) + " or more cards in their graveyard";
    }

    public Hint getHint() {
        return hint;
    }

    private static final class CardsInOpponentGraveyardConditionHint implements Hint {

        private final int value;

        private CardsInOpponentGraveyardConditionHint(int value) {
            this.value = value;
        }

        private CardsInOpponentGraveyardConditionHint(final CardsInOpponentGraveyardConditionHint hint) {
            this.value = hint.value;
        }

        @Override
        public String getText(Game game, Ability ability) {
            int maxGraveSize = game
                    .getOpponents(ability.getControllerId())
                    .stream()
                    .map(game::getPlayer)
                    .filter(Objects::nonNull)
                    .map(Player::getGraveyard)
                    .mapToInt(Graveyard::size)
                    .max()
                    .orElse(0);
            String text = "Opponent graveyard size: " + maxGraveSize
                    + " (need: " + value + ")";
            return HintUtils.prepareText(text, null, maxGraveSize >= this.value ? HintUtils.HINT_ICON_GOOD : HintUtils.HINT_ICON_BAD);
        }

        @Override
        public CardsInOpponentGraveyardConditionHint copy() {
            return new CardsInOpponentGraveyardConditionHint(this);
        }
    }
}
