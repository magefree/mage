package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class CardsInControllerGraveyardCount implements DynamicValue {

    private FilterCard filter;
    private Integer amount;

    public CardsInControllerGraveyardCount() {
        this(new FilterCard(), 1);
    }

    public CardsInControllerGraveyardCount(FilterCard filter) {
        this(filter, 1);
    }

    public CardsInControllerGraveyardCount(FilterCard filter, Integer amount) {
        this.filter = filter;
        this.amount = amount;
    }

    public CardsInControllerGraveyardCount(final CardsInControllerGraveyardCount dynamicValue) {
        this.filter = dynamicValue.filter;
        this.amount = dynamicValue.amount;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player != null) {
            return amount * player.getGraveyard().count(filter, game);
        }
        return 0;
    }

    @Override
    public DynamicValue clone() {
        return new CardsInControllerGraveyardCount(this);
    }

    @Override
    public String toString() {
        return amount.toString();
    }

    @Override
    public String getMessage() {
        return " for each " + filter.getMessage() + " in your graveyard";
    }
}
