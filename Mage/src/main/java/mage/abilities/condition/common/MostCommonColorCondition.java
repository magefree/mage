package mage.abilities.condition.common;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public class MostCommonColorCondition implements Condition {

    protected final ObjectColor compareColor;
    protected final boolean isMono;
    protected final FilterPermanent filter;

    public MostCommonColorCondition(ObjectColor color) {
        this(color, false, null);
    }

    // Use this one if you don't want a tie for most common and want to restrict to a player (literally only Call to Arms)
    public MostCommonColorCondition(ObjectColor color, boolean isMono, Predicate predicate) {
        this.compareColor = color;
        this.isMono = isMono;
        if (predicate == null) {
            this.filter = StaticFilters.FILTER_PERMANENT;
        } else {
            this.filter = new FilterPermanent();
            this.filter.add(predicate);
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<String, Integer> colorMap = new HashMap<>();
        for (Permanent permanent : game
                .getBattlefield()
                .getActivePermanents(filter, source.getControllerId(), source, game)) {
            for (char c : permanent.getColor(game).toString().toCharArray()) {
                colorMap.compute("" + c, CardUtil::setOrIncrementValue);
            }
        }
        int most = colorMap
                .values()
                .stream()
                .mapToInt(x -> x)
                .max()
                .orElse(0);
        ObjectColor common = new ObjectColor(
                colorMap.entrySet()
                        .stream()
                        .filter(e -> e.getValue() == most)
                        .map(Map.Entry::getKey)
                        .collect(Collectors.joining())
        );
        return common.shares(compareColor) && (!isMono || common.getColorCount() == 1);
    }

    @Override
    public String toString() {
        if (!compareColor.isMulticolored()) {
            return compareColor.getDescription() + " is the most common color among all permanents or is tied for most common";
        } else {
            return "it shares a color with the most common color among all permanents or a color tied for most common";
        }
    }
}
