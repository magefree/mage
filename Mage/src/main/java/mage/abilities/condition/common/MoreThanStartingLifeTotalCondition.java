package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Controllable;
import mage.game.Game;
import mage.players.Player;

import java.util.Optional;

/**
 * @author TheElk801
 */
public enum MoreThanStartingLifeTotalCondition implements Condition {
    SEVEN(7),
    TEN(10),
    FIFTEEN(10);
    private final int amount;

    MoreThanStartingLifeTotalCondition(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .map(Player::getLife)
                .map(life -> life >= game.getStartingLife() + amount)
                .orElse(false);
    }

    @Override
    public String toString() {
        return "you have at least " + amount + " life more than your starting life total";
    }
}
