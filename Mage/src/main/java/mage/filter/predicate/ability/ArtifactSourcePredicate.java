
package mage.filter.predicate.ability;

import mage.MageObject;
import mage.abilities.Ability;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class ArtifactSourcePredicate implements Predicate<Ability> {

    @Override
    public boolean apply(Ability input, Game game) {
        MageObject sourceObject = input.getSourceObject(game);
        return sourceObject != null && sourceObject.isArtifact();
    }

    @Override
    public String toString() {
        return "Source(Artifact)";
    }
}
