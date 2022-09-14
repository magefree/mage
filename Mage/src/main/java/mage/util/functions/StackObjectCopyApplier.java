package mage.util.functions;

import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.stack.StackObject;

import java.io.Serializable;

/**
 * @author TheElk801
 */
public interface StackObjectCopyApplier extends Serializable {

    void modifySpell(StackObject stackObject, Game game);

    /**
     * For multi copies: allows change new target filter for each next copy (e.g. add some restict)
     * Return null to use same target type as original spell
     *
     * @param copyNumber current number of copy, starts with 1
     * @return
     */
    MageObjectReferencePredicate getNextNewTargetType();
}
