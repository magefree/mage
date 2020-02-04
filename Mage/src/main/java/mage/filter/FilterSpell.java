
package mage.filter;

import java.util.UUID;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

/**
 *
 * @author North, Quercitron
 */
public class FilterSpell extends FilterStackObject {

    public FilterSpell() {
        super("spell");
    }

    public FilterSpell(String name) {
        super(name);
    }

    public FilterSpell(final FilterSpell filter) {
        super(filter);
    }

    @Override
    public boolean match(StackObject stackObject, UUID sourceId, UUID playerId, Game game) {
        if (!(stackObject instanceof Spell)) {
            return false;
        }
        return super.match(stackObject, sourceId, playerId, game);
    }

    @Override
    public FilterSpell copy() {
        return new FilterSpell(this);
    }
}
