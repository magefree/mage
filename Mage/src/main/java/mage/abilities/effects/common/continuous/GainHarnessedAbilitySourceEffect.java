package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.Mode;
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
 * @author Jmlundeen
 */
public class GainHarnessedAbilitySourceEffect extends ContinuousEffectImpl {

    private final Ability ability;

    public GainHarnessedAbilitySourceEffect(Effect effect) {
        this(new SimpleStaticAbility(effect));
    }

    public GainHarnessedAbilitySourceEffect(Ability ability) {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = ability.getRule();
        this.ability = ability;
        this.ability.setRuleVisible(false);
        generateGainAbilityDependencies(ability, null);
    }

    private GainHarnessedAbilitySourceEffect(final GainHarnessedAbilitySourceEffect effect) {
        super(effect);
        this.ability = effect.ability;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || !permanent.isHarnessed()) {
            return false;
        }
        permanent.addAbility(ability, source.getSourceId(), game);
        return true;
    }

    @Override
    public GainHarnessedAbilitySourceEffect copy() {
        return new GainHarnessedAbilitySourceEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        return "∞ &mdash; " + super.getText(mode);
    }
}
