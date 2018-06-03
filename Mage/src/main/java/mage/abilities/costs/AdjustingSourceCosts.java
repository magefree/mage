package mage.abilities.costs;

import mage.abilities.Ability;
import mage.game.Game;

/**
 * Interface for abilities that adjust source and only source costs. For the
 * cases when some permanent adjusts costs of other spells use
 * {@link mage.abilities.effects.CostModificationEffect}.
 *
 * Example of such source costs adjusting:
 * {@link mage.abilities.keyword.AffinityForArtifactsAbility}
 *
 * @author nantuko
 */
@FunctionalInterface
public interface AdjustingSourceCosts {

    void adjustCosts(Ability ability, Game game);
}
