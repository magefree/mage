package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.players.Player;


/**
 * Condition for -
 *  Controller has X or more cards in his or her graveyard
 *  @author LevelX2
 */
public class CardsInControllerGraveCondition implements Condition {
    private int value;

    public CardsInControllerGraveCondition(int value) {
        this.value = value;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player p = game.getPlayer(source.getControllerId());
        if (p != null && p.getGraveyard().size() >= value)
        {
                    return true;
        }
        return false;
    }
}
