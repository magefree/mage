package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CreateDelayedTriggeredAbilityEffect extends OneShotEffect {

    protected DelayedTriggeredAbility ability;
    protected boolean copyTargets;
    protected boolean initAbility;

    public CreateDelayedTriggeredAbilityEffect(DelayedTriggeredAbility ability) {
        this(ability, true);
    }

    public CreateDelayedTriggeredAbilityEffect(DelayedTriggeredAbility ability, boolean copyTargets) {
        this(ability, copyTargets, false);
    }

    public CreateDelayedTriggeredAbilityEffect(DelayedTriggeredAbility ability, boolean copyTargets, boolean initAbility) {
        super(ability.getEffects().getOutcome(ability));
        this.ability = ability;
        this.copyTargets = copyTargets;
        this.initAbility = initAbility;
    }

    public CreateDelayedTriggeredAbilityEffect(final CreateDelayedTriggeredAbilityEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        this.copyTargets = effect.copyTargets;
        this.initAbility = effect.initAbility;
    }

    @Override
    public CreateDelayedTriggeredAbilityEffect copy() {
        return new CreateDelayedTriggeredAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility delayedAbility = ability.copy();
        if (this.copyTargets) {
            if (source.getTargets().isEmpty()) {
                delayedAbility.getEffects().setTargetPointer(targetPointer);
            } else {
                delayedAbility.getTargets().addAll(source.getTargets());
                for (Effect effect : delayedAbility.getEffects()) {
                    effect.getTargetPointer().init(game, source);
                }
            }
        }
        if (initAbility) {
            delayedAbility.init(game);
        }
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        if (ability.getRuleVisible()) {
            return ability.getRule();
        } else {
            return "";
        }
    }

    @Override
    public void setValue(String key, Object value) {
        ability.getEffects().setValue(key, value);
        super.setValue(key, value);
    }
}
