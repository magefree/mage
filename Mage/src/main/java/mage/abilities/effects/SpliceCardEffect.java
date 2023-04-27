package mage.abilities.effects;

import mage.abilities.Ability;
import mage.game.Game;

/**
 * Represents a {@link ContinuousEffect} that will modify the cost and abilities of a spell
 * on the stack (splice a card).  {@link mage.abilities.Ability Abilities} with this type of effect will be
 * called once and only once from the {@link mage.abilities.AbilityImpl#activate(mage.game.Game,
 * boolean) Ability.activate} method before alternative or additional costs are paid.
 *
 * @author levelX2
 */

public interface SpliceCardEffect extends ContinuousEffect {
    /**
     * Called by the {@link ContinuousEffects#costModification(Ability abilityToModify, Game game) ContinuousEffects.costModification}
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
     * @param game The game for which this effect shoul dbe applied.
     * @return
     */
    boolean applies(Ability abilityToModify, Ability source, Game game);
}
