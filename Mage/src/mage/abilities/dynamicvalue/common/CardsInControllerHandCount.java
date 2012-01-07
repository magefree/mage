package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.game.Game;
import mage.players.Player;

public class CardsInControllerHandCount implements DynamicValue {
    @Override
    public int calculate(Game game, Ability sourceAbility) {
        if (sourceAbility != null) {
            Player controller = game.getPlayer(sourceAbility.getControllerId());
            if (controller != null) {
                return controller.getHand().size();
            }
        }
        return 0;
    }

    @Override
    public DynamicValue clone() {
        return new CardsInControllerHandCount();
    }

    @Override
    public String getMessage() {
        return "card in your hand";
    }

	@Override
	public String toString() {
		return "1";
	}
}
