
package mage.filter.predicate.ability;

import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;

/**
 *
 * @author LevelX2
 */
public class ArtifactSourcePredicate implements Predicate<StackObject> {

    public ArtifactSourcePredicate() {
    }

    @Override
    public boolean apply(StackObject input, Game game) {
        if (input instanceof StackAbility) {
            StackAbility ability = (StackAbility) input;
            return ability.getSourceObject(game).isArtifact();
        }
        return false;
    }

    @Override
    public String toString() {
        return "Source(Artifact)";
    }
}
