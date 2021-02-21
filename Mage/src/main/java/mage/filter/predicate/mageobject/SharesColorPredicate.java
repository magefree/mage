package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.ObjectColor;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 *
 * @author noahg
 */

public class SharesColorPredicate implements Predicate<MageObject> {

    private final ObjectColor color;

    public SharesColorPredicate(ObjectColor color) {
        this.color = color;
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        return color != null && input.getColor(game).shares(color);

    }

    @Override
    public String toString() {
        return "shares a color";
    }
}