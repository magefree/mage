package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;

public class TotalPermanentsManaValue implements DynamicValue {

    private final String message;
    private final FilterPermanent filter;

    public TotalPermanentsManaValue(FilterPermanent filter) {
        this.filter = filter.copy();
        this.message = "the total mana value of " + filter.getMessage();
    }

    private TotalPermanentsManaValue(final TotalPermanentsManaValue dynamicValue) {
        this.filter = dynamicValue.filter.copy();
        this.message = dynamicValue.message;
    }

    @Override
    public DynamicValue copy() {
        return new TotalPermanentsManaValue(this);
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int totalCMC = 0;
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(
            filter,
            sourceAbility.getControllerId(),
            sourceAbility,
            game);
        for (Permanent permanent : permanents) {
            totalCMC += permanent.getManaValue();
        }
        return totalCMC;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "X";
    }
}
