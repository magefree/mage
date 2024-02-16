package mage.abilities.condition.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public enum DifferentManaValuesInGraveCondition implements Condition {
    FIVE(5);
    private final int amount;

    DifferentManaValuesInGraveCondition(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null
                && player
                .getGraveyard()
                .getCards(game)
                .stream()
                .mapToInt(MageObject::getManaValue)
                .distinct()
                .count() >= amount;
    }

    @Override
    public String toString() {
        return "there are " + CardUtil.numberToText(amount) + " or more mana values among cards in your graveyard";
    }
}
