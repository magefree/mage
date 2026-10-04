package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.constants.Duration;
import mage.target.targetpointer.SourceTargetPointer;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class GainAbilitySourceEffect extends GainAbilityTargetEffect {

    /**
     * Add ability with Duration.WhileOnBattlefield
     */
    public GainAbilitySourceEffect(Ability ability) {
        this(ability, Duration.WhileOnBattlefield);
    }

    public GainAbilitySourceEffect(Ability ability, Duration duration) {
        this(ability, duration, false);
    }

    public GainAbilitySourceEffect(Ability ability, Duration duration, boolean onCard) {
        super(ability, duration, onCard);
        this.setTargetPointer(new SourceTargetPointer(onCard));
    }

    protected GainAbilitySourceEffect(final GainAbilitySourceEffect effect) {
        super(effect);
    }

    @Override
    public GainAbilitySourceEffect copy() {
        return new GainAbilitySourceEffect(this);
    }

}
