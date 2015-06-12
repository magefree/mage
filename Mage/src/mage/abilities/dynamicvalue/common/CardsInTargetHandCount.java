package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author cbrianhill
 */
public class CardsInTargetHandCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getFirstTarget());
        return player.getHand().size();
    }

    @Override
    public DynamicValue copy() {
        return new CardsInTargetHandCount();
    }

    @Override
    public String getMessage() {
        return "cards in target player's hand";
    }
    
}
