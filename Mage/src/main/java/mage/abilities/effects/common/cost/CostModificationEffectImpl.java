package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.CostModificationEffect;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.EffectType;
import mage.constants.Outcome;
import mage.game.Game;

/**
 * Simple implementation of a {@link CostModificationEffect} offering simplified
 * construction to setup the object for use by the mage framework.
 *
 * @author maurer.it_at_gmail.com
 */
public abstract class CostModificationEffectImpl extends ContinuousEffectImpl implements CostModificationEffect {

    private final CostModificationType modificationType;

    // if effect need real stack object to check then mark it as stack only (example: apply cost reduction if you target human creature)
    private boolean worksOnStackOnly = false;

    public CostModificationEffectImpl(Duration duration, Outcome outcome, CostModificationType type) {
        super(duration, outcome);
        this.effectType = EffectType.COSTMODIFICATION;
        this.modificationType = type;
    }

    public CostModificationEffectImpl(final CostModificationEffectImpl effect) {
        super(effect);
        this.modificationType = effect.modificationType;
        this.worksOnStackOnly = effect.worksOnStackOnly;
    }

    /**
     * Overridden and 'no-op' implementation put in place.
     *
     * @param game
     * @param source
     * @return
     * @see #apply(mage.game.Game, mage.abilities.Ability, mage.abilities.Ability)
     */
    @Override
    public final boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public CostModificationType getModificationType() {
        return this.modificationType;
    }

    public CostModificationEffectImpl setCanWorksOnStackOnly(boolean worksOnStackOnly) {
        this.worksOnStackOnly = worksOnStackOnly;
        return this;
    }

    public boolean canWorksOnStackOnly() {
        return this.worksOnStackOnly;
    }
}
