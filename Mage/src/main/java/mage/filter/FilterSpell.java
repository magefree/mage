
package mage.filter;

import mage.cards.Card;
import mage.game.stack.Spell;

/**
 * @author North, Quercitron
 */
public class FilterSpell extends FilterStackObject {

    public FilterSpell() {
        super("spell");
    }

    public FilterSpell(String name) {
        super(name);
    }

    protected FilterSpell(final FilterSpell filter) {
        super(filter);
    }

    @Override
    public boolean checkObjectClass(Object object) {
        return object instanceof Spell
                || object instanceof Card; // TODO: investigate. Is sometimes used for checking a spell's characteristic before cast
    }

    @Override
    public FilterSpell copy() {
        return new FilterSpell(this);
    }
}
