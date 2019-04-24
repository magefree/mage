package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.players.Player;

public class CardsInTargetHandCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (sourceAbility != null) {
            Player player = game.getPlayer(sourceAbility.getFirstTarget());
            if (player != null) {
                return player.getHand().size();
            }
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return new CardsInTargetHandCount();
    }

    @Override
    public String getMessage() {
        return "cards in that player's hand";
    }

    @Override
    public String toString() {
        return "";
    }
}
