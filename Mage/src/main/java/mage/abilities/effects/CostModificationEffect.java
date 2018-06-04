

package mage.abilities.effects;

import mage.abilities.Ability;
import mage.constants.CostModificationType;
import mage.game.Game;

/**
 * Represents a {@link ContinuousEffect} that will modify the cost of a spell or
 * ability on the stack.  {@link mage.abilities.Ability Abilities} with this type
 * of effect will be called once and only once from the
 * {@link mage.abilities.AbilityImpl#activate(mage.game.Game, boolean) Ability.activate}
 * method before costs are paid.  After this time the costs are locked in and
 * should not be modified further.
 *
 * @author maurer.it_at_gmail.com
 */
//20101001 - 601.2e/613.10
public interface CostModificationEffect extends ContinuousEffect {
    /**
     * Called by the {@link ContinuousEffects#costModification(java.util.UUID, mage.abilities.Ability, mage.game.Game) ContinuousEffects.costModification}
     * method.
     *
     * @param game The game for which this effect should be applied.
     * @param source The source ability of this effect.
     * @param abilityToModify The {@link mage.abilities.SpellAbility} or {@link Ability} which should be modified.
     * @return
     */
    boolean apply ( Game game, Ability source, Ability abilityToModify );

    /**
     * Called by the {@link ContinuousEffects#costModification(mage.abilities.Ability, mage.game.Game) ContinuousEffects.costModification}
     * method.
     * 
     * @param abilityToModify The ability to possibly modify.
     * @param source The source ability of this effect.
     * @param game The game for which this effect should be applied.
     * @return
     */
    boolean applies(Ability abilityToModify, Ability source, Game game);
    
    /**
     * Return the type of modification
     * @return 
     */
    CostModificationType getModificationType();
}
