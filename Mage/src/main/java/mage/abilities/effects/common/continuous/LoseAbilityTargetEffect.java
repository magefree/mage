
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author jeffwadsworth
 */
public class LoseAbilityTargetEffect extends ContinuousEffectImpl {

    protected Ability ability;

    public LoseAbilityTargetEffect(Ability ability) {
        this(ability, Duration.WhileOnBattlefield);
    }

    public LoseAbilityTargetEffect(Ability ability, Duration duration) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.LoseAbility);
        this.ability = ability;
    }

    protected LoseAbilityTargetEffect(final LoseAbilityTargetEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
    }

    @Override
    public LoseAbilityTargetEffect copy() {
        return new LoseAbilityTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            permanent.removeAbility(ability, source.getSourceId(), game);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return getTargetPointer().describeTargets(mode.getTargets(), "it")
                + " loses " + CardUtil.stripReminderText(ability.getRule())
                + (duration.toString().isEmpty() ? "" : ' ' + duration.toString());
    }
}
