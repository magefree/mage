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
public class CardsInTargetPlayerHandCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(effect.getTargetPointer().getFirst(game, sourceAbility));
        if (player != null) {
            return player.getHand().size();
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return new CardsInTargetPlayerHandCount();
    }

    @Override
    public String getMessage() {
        return "cards in target player's hand";
    }
    
}
