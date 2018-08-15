
package mage.filter.predicate.other;

import mage.MageObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.StackObject;

/**
 *
 */
public class StackObjectWithArtifactSourcePredicate implements Predicate<StackObject> {

    @Override
    public boolean apply(StackObject input, Game game) {
        MageObject sourceObject = game.getObject(input.getSourceId());
        return sourceObject != null && sourceObject.isArtifact();
    }

    @Override
    public String toString() {
        return "Source(Artifact)";
    }
}
