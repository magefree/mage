package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.players.Player;

public enum CardsInControllerHandCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (sourceAbility != null) {
            Player controller = game.getPlayer(sourceAbility.getControllerId());
            if (controller != null) {
                return controller.getHand().size();
            }
        }
        return 0;
    }

    @Override
    public CardsInControllerHandCount copy() {
        return CardsInControllerHandCount.instance;
    }

    @Override
    public String getMessage() {
        return "cards in your hand";
    }

    @Override
    public String toString() {
        return "1";
    }
}
