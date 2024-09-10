package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
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
public class GainClassAbilitySourceEffect extends ContinuousEffectImpl {

    private final Ability ability;
    private final int level;

    public GainClassAbilitySourceEffect(Effect effect, int level) {
        this(new SimpleStaticAbility(effect), level);
    }

    public GainClassAbilitySourceEffect(Ability ability, int level) {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = ability.getRule();
        this.ability = ability;
        this.level = level;
        this.ability.setRuleVisible(false);
        generateGainAbilityDependencies(ability, null);
    }

    private GainClassAbilitySourceEffect(final GainClassAbilitySourceEffect effect) {
        super(effect);
        this.level = effect.level;
        this.ability = effect.ability;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || permanent.getClassLevel() < level) {
            return false;
        }
        permanent.addAbility(ability, source.getSourceId(), game);
        return true;
    }

    @Override
    public GainClassAbilitySourceEffect copy() {
        return new GainClassAbilitySourceEffect(this);
    }
}
