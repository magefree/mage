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
 * <p>
 * WARNING, if you implement custom effect and it can works on stack only (e.g. it need spell's targets to check) then
 * use different apply code:
 * - one for get playable mode before spell puts on stack (apply maximum possible cost reduction, use game.inCheckPlayableState()).
 * - one for normal mode after spell puts on stack (apply real cost reduction)
 * Example: TorgaarFamineIncarnate
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

    protected CostModificationEffectImpl(final CostModificationEffectImpl effect) {
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
