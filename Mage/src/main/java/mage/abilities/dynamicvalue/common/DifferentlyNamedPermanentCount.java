package mage.abilities.dynamicvalue.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.filter.FilterPermanent;
import mage.game.Game;

/**
 * @author TheElk801
 */
public class DifferentlyNamedPermanentCount implements DynamicValue {

    private final FilterPermanent filter;
    private final Hint hint;

    public DifferentlyNamedPermanentCount(FilterPermanent filter) {
        this.filter = filter;
        this.hint = new ValueHint("Differently named " + filter.getMessage(), this);
    }

    private DifferentlyNamedPermanentCount(final DifferentlyNamedPermanentCount dynamicValue) {
        this.filter = dynamicValue.filter;
        this.hint = dynamicValue.hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(filter, sourceAbility.getControllerId(), sourceAbility, game)
                .stream()
                .map(MageObject::getName)
                .filter(s -> !s.isEmpty())
                .distinct()
                .mapToInt(x -> 1)
                .sum();
    }

    @Override
    public DifferentlyNamedPermanentCount copy() {
        return new DifferentlyNamedPermanentCount(this);
    }

    @Override
    public String getMessage() {
        return "the number of differently named " + filter.getMessage();
    }

    @Override
    public String toString() {
        return "X";
    }

    public Hint getHint() {
        return hint;
    }
}
