package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ControllerSpeedCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class MaxSpeedGainAbilityEffect extends ContinuousEffectImpl {

    private final Ability ability;

    public MaxSpeedGainAbilityEffect(Effect effect) {
        this(new SimpleStaticAbility(effect));
    }

    public MaxSpeedGainAbilityEffect(Ability ability) {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability;
    }

    private MaxSpeedGainAbilityEffect(final MaxSpeedGainAbilityEffect effect) {
        super(effect);
        this.ability = effect.ability;
    }

    @Override
    public MaxSpeedGainAbilityEffect copy() {
        return new MaxSpeedGainAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || ControllerSpeedCount.instance.calculate(game, source, null) < 4) {
            return false;
        }
        permanent.addAbility(ability, source.getSourceId(), game);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "Max speed &mdash; " + ability.getRule();
    }
}
