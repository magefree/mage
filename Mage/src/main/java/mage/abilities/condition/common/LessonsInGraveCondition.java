package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public enum LessonsInGraveCondition implements Condition {
    ONE(1),
    THREE(3);
    private final int amount;
    private static final FilterCard filter = new FilterCard(SubType.LESSON);
    private static final Hint hint = new ValueHint("Lesson cards in your graveyard", new CardsInControllerGraveyardCount(filter));

    public static Hint getHint() {
        return hint;
    }

    LessonsInGraveCondition(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.getGraveyard().count(filter, game) >= this.amount;
    }

    @Override
    public String toString() {
        if (amount == 1) {
            return "there's a Lesson card in your graveyard";
        }
        return "there are " + CardUtil.numberToText(this.amount) + " or more Lesson cards in your graveyard";
    }
}
