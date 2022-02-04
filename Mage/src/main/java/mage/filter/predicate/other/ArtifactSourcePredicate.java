package mage.filter.predicate.other;

import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;

/**
 * @author LevelX2
 */
public enum ArtifactSourcePredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return input instanceof StackAbility
                && ((StackAbility) input).getSourceObject(game).isArtifact(game);
    }

    @Override
    public String toString() {
        return "Source(Artifact)";
    }
}
