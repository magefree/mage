package mage.filter.predicate.mageobject;

import mage.MageObject;
import mage.abilities.text.TextPartSubType;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class TextPartSubtypePredicate implements Predicate<MageObject> {

    private final TextPartSubType textPartSubtype;

    public TextPartSubtypePredicate(TextPartSubType textPartSubtype) {
        this.textPartSubtype = textPartSubtype;
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        return input.hasSubtype(textPartSubtype.getCurrentValue(), game);
    }

    @Override
    public String toString() {
        return "Subtype(" + textPartSubtype.getCurrentValue() + ')';
    }
}
