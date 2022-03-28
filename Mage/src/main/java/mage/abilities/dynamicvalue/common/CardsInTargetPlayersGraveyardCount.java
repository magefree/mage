
package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LoneFox
 */
public class CardsInTargetPlayersGraveyardCount implements DynamicValue {

    private final FilterCard filter;

    public CardsInTargetPlayersGraveyardCount() {
        this.filter = null;
    }

    public CardsInTargetPlayersGraveyardCount(FilterCard filter) {
        this.filter = filter;
    }

    public CardsInTargetPlayersGraveyardCount(final CardsInTargetPlayersGraveyardCount dynamicValue) {
        this.filter = dynamicValue.filter;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(effect.getTargetPointer().getFirst(game, sourceAbility));
        if (player != null) {
            if (filter == null) {
                return player.getGraveyard().size();
            } else {
                return player.getGraveyard().count(filter, sourceAbility.getSourceId(), sourceAbility, game);
            }
        }
        return 0;
    }

    @Override
    public CardsInTargetPlayersGraveyardCount copy() {
        return new CardsInTargetPlayersGraveyardCount(this);
    }

    @Override
    public String getMessage() {
        return (filter == null ? "cards" : filter.getMessage()) + " in target player's graveyard";
    }

}
