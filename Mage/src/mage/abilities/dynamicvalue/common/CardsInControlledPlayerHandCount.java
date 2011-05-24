package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.game.Game;
import mage.players.Player;

public class CardsInControlledPlayerHandCount implements DynamicValue {
    @Override
    public int calculate(Game game, Ability sourceAbility) {
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (controller != null) {
            return controller.getHand().size();
        }
        return 0;
    }

    @Override
    public DynamicValue clone() {
        return new CardsInControlledPlayerHandCount();
    }
}
