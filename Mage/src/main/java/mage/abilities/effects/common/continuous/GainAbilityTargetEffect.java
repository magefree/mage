package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.constants.Duration;

/**
 * Grant abilities without boosting. All the work is in the superclass.
 *
 * @author JayDi85
 */
public class GainAbilityTargetEffect extends BoostGainAbilityGenericEffect {

    public GainAbilityTargetEffect(Ability ability) {
        this(ability, Duration.EndOfTurn);
    }

    public GainAbilityTargetEffect(Ability ability, Duration duration) {
        this(ability, duration, null);
    }

    public GainAbilityTargetEffect(Ability ability, Duration duration, String rule) {
        this(ability, duration, rule, false);
    }

    public GainAbilityTargetEffect(Ability ability, Duration duration, boolean useOnCard) {
        this(ability, duration, null, useOnCard);
    }

    public GainAbilityTargetEffect(Ability ability, Duration duration, String rule, boolean useOnCard) {
        this(duration, rule, useOnCard, ability);
    }

    public GainAbilityTargetEffect(Duration duration, Ability... abilities) {
        this(duration, null, false, abilities);
    }

    public GainAbilityTargetEffect(Duration duration, String rule, boolean useOnCard, Ability... abilities) {
        super(null, null, duration, rule, useOnCard, abilities);
    }

    protected GainAbilityTargetEffect(final GainAbilityTargetEffect effect) {
        super(effect);
    }

    @Override
    public GainAbilityTargetEffect copy() {
        return new GainAbilityTargetEffect(this);
    }

    @Override
    public GainAbilityTargetEffect withDurationRuleAtStart(boolean durationRuleAtStart) {
        super.withDurationRuleAtStart(durationRuleAtStart);
        return this;
    }
}
