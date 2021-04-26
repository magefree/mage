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

    MageObjectReferencePredicate getNextPredicate();
}
