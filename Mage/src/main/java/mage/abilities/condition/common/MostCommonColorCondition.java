package mage.abilities.condition.common;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;

/**
 *
 * @author TheElk801
 */
public class MostCommonColorCondition implements Condition {

    protected final ObjectColor compareColor;
    protected final boolean isMono;
    protected final Predicate predicate;

    public MostCommonColorCondition(ObjectColor color) {
        this(color, false, null);
    }

    //Use this one if you don't want a tie for most common and want to restrict to a player (literally only Call to Arms)
    public MostCommonColorCondition(ObjectColor color, boolean isMono, Predicate predicate) {
        this.compareColor = color;
        this.isMono = isMono;
        this.predicate = predicate;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterPermanent[] colorFilters = new FilterPermanent[6];
        int i = 0;
        for (ObjectColor color : ObjectColor.getAllColors()) {
            colorFilters[i] = new FilterPermanent();
            colorFilters[i].add(new ColorPredicate(color));
            if (predicate != null) {
                colorFilters[i].add(predicate);
            }
            i++;
        }
        int[] colorCounts = new int[6];
        i = 0;
        for (ObjectColor color : ObjectColor.getAllColors()) {
            colorFilters[i].add(new ColorPredicate(color));
            colorCounts[i] = game.getBattlefield().count(colorFilters[i], source.getControllerId(), source, game);
            i++;
        }
        int max = 0;
        for (i = 0; i < 5; i++) {
            if (colorCounts[i] > max) {
                max = colorCounts[i] * 1;
            }
        }
        i = 0;
        ObjectColor commonest = new ObjectColor();
        for (ObjectColor color : ObjectColor.getAllColors()) {
            if (colorCounts[i] == max) {
                commonest.addColor(color);
            }
            i++;
        }
        if (compareColor.shares(commonest)) {
            if (isMono) {
                return !commonest.isMulticolored();
            } else {
                return true;
            }
        }
        return false;
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
