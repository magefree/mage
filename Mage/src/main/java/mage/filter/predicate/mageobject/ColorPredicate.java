
package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.ObjectColor;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 *
 * @author North
 */
public class ColorPredicate implements Predicate<MageObject> {

    private final ObjectColor color;

    public ColorPredicate(ObjectColor color) {
        this.color = color;
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        return color != null && input.getColor(game).contains(color);
    }

    @Override
    public String toString() {
        return "Color(" + color.toString() + ')';
    }
}
