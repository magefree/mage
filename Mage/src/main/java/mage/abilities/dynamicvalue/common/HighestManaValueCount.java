package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author nigelzor
 */
public class HighestManaValueCount implements DynamicValue {

    private final FilterPermanent filter;

    public HighestManaValueCount() {
        this(StaticFilters.FILTER_PERMANENTS);
    }

    public HighestManaValueCount(FilterPermanent filter) {
        this.filter = filter;
    }

    public HighestManaValueCount(final HighestManaValueCount dynamicValue){
        super();
        this.filter = dynamicValue.filter.copy();
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (controller == null) {
            return 0;
        }
        int highCMC = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, controller.getId(), game)) {
            int cmc = permanent.getManaValue();
            highCMC = Math.max(highCMC, cmc);
        }
        return highCMC;
    }

    @Override
    public DynamicValue copy() {
        return new HighestManaValueCount(this);
    }

    @Override
    public String getMessage() {
        return "the highest mana value among " + filter.getMessage() + " you control";
    }

    @Override
    public String toString() {
        return "X";
    }
}
