package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.players.Player;

/**
 * Condition for - Controller has X or more cards in his or her graveyard
 *
 * @author LevelX2
 */
public class CardsInControllerGraveCondition implements Condition {

    private final int value;

    public CardsInControllerGraveCondition(int value) {
        this.value = value;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.getGraveyard().size() >= value;
    }
}
