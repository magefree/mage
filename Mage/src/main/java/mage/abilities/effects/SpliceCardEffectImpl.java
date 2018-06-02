

package mage.abilities.effects;

import mage.abilities.Ability;
import mage.constants.Duration;
import mage.constants.EffectType;
import mage.constants.Outcome;
import mage.game.Game;


/**
 * Simple implementation of a {@link SpliceCardEffect} offering simplified
 * construction to setup the object for use by the mage framework.

 * @author LevelX2
 */
public abstract class SpliceCardEffectImpl extends ContinuousEffectImpl implements SpliceCardEffect {

    public SpliceCardEffectImpl ( Duration duration, Outcome outcome ) {
        super(duration, outcome);
        this.effectType = EffectType.SPLICE;
    }

    public SpliceCardEffectImpl(final SpliceCardEffectImpl effect) {
        super(effect);
    }

    /**
     * Overridden and 'no-op' implementation put in place.
     *
     * @see #apply(mage.game.Game, mage.abilities.Ability, mage.abilities.Ability)
     *
     * @param game
     * @param source
     * @return
     */
    @Override
    public final boolean apply ( Game game, Ability source ) { return false; }
}
