package mage.abilities.dynamicvalue.common;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.MonocoloredPredicate;
import mage.game.Game;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public enum ColorsAmongControlledPermanentsCount implements DynamicValue {
    ALL_PERMANENTS(StaticFilters.FILTER_CONTROLLED_PERMANENTS),
    MONOCOLORED_PERMANENTS(PermanentFilters.MONOCOLORED_PERMANENTS),
    OTHER_LEGENDARY(PermanentFilters.OTHER_LEGENDARY),
    ALLIES(new FilterControlledPermanent(SubType.ALLY, "Allies you control"));
    private final FilterPermanent filter;
    private final Hint hint;

    ColorsAmongControlledPermanentsCount(FilterPermanent filter) {
        this.filter = filter;
        this.hint = new ColorsAmongControlledPermanentsHint(this);
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return getAllControlledColors(game, sourceAbility).getColorCount();
    }

    @Override
    public ColorsAmongControlledPermanentsCount copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "for each color among " + filter.getMessage();
    }

    @Override
    public String toString() {
        return "1";
    }

    public Hint getHint() {
        return hint;
    }

    public FilterPermanent getFilter() {
        return filter;
    }

    public ObjectColor getAllControlledColors(Game game, Ability source) {
        return game
                .getBattlefield()
                .getActivePermanents(filter, source.getControllerId(), source, game)
                .stream()
                .map(permanent -> permanent.getColor(game))
                .reduce(new ObjectColor(), (c1, c2) -> c1.union(c2));
    }
}

class ColorsAmongControlledPermanentsHint implements Hint {

    private final ColorsAmongControlledPermanentsCount count;

    ColorsAmongControlledPermanentsHint(ColorsAmongControlledPermanentsCount count) {
        this.count = count;
    }

    @Override
    public String getText(Game game, Ability ability) {
        List<String> colors = this
                .count
                .getAllControlledColors(game, ability)
                .getColors()
                .stream()
                .map(ObjectColor::getDescription)
                .collect(Collectors.toList());
        return "Colors among " + this.count.getFilter().getMessage() + ": " + colors.size()
                + (colors.size() > 0 ? " (" + String.join(", ", colors) + ')' : "");
    }

    @Override
    public Hint copy() {
        return this;
    }
}

class PermanentFilters {
    static final FilterPermanent MONOCOLORED_PERMANENTS = new FilterControlledPermanent("monocolored permanents you control");

    static {
        MONOCOLORED_PERMANENTS.add(MonocoloredPredicate.instance);
    }

    static final FilterPermanent OTHER_LEGENDARY = new FilterControlledPermanent("other legendary permanents you control");

    static {
        OTHER_LEGENDARY.add(AnotherPredicate.instance);
        OTHER_LEGENDARY.add(SuperType.LEGENDARY.getPredicate());
    }
}
