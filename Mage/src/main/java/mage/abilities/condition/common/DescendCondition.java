package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public enum DescendCondition implements Condition {
    FOUR(4),
    EIGHT(8);
    private static final Hint hint = new ValueHint(
            "Permanent cards in your graveyard",
            new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_PERMANENT)
    );

    public static Hint getHint() {
        return hint;
    }

    private final int amount;

    DescendCondition(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.getGraveyard().count(StaticFilters.FILTER_CARD_PERMANENT, game) >= amount;
    }

    @Override
    public String toString() {
        return "there are " + CardUtil.numberToText(amount) + " or more permanent cards in your graveyard";
    }
}
