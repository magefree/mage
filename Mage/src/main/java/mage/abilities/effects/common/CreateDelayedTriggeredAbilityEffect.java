package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.target.targetpointer.TargetPointer;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CreateDelayedTriggeredAbilityEffect extends OneShotEffect {

    private final DelayedTriggeredAbility ability;
    private final boolean copyTargets;
    private final String rulePrefix;
    private boolean copyToPointer = false;

    public CreateDelayedTriggeredAbilityEffect(DelayedTriggeredAbility ability) {
        this(ability, true);
    }

    public CreateDelayedTriggeredAbilityEffect(DelayedTriggeredAbility ability, boolean copyTargets) {
        this(ability, copyTargets, "");
    }

    public CreateDelayedTriggeredAbilityEffect(DelayedTriggeredAbility ability, boolean copyTargets, String rulePrefix) {
        super(ability.getEffects().getOutcome(ability));
        this.ability = ability;
        this.copyTargets = copyTargets;
        this.rulePrefix = rulePrefix;
    }

    protected CreateDelayedTriggeredAbilityEffect(final CreateDelayedTriggeredAbilityEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        this.copyTargets = effect.copyTargets;
        this.rulePrefix = effect.rulePrefix;
        this.copyToPointer = effect.copyToPointer;
    }

    @Override
    public CreateDelayedTriggeredAbilityEffect copy() {
        return new CreateDelayedTriggeredAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility delayedAbility = ability.copy();
        if (this.copyTargets) {
            if (copyToPointer || source.getTargets().isEmpty()) {
                delayedAbility.getEffects().setTargetPointer(this.getTargetPointer().copy());
            } else {
                delayedAbility.getTargets().addAll(source.getTargets());
                for (Effect effect : delayedAbility.getEffects()) {
                    effect.getTargetPointer().init(game, source);
                }
            }
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
            if (rulePrefix == null || rulePrefix.isEmpty()) {
                return CardUtil.getTextWithFirstCharLowerCase(ability.getRule());
            }
            return rulePrefix + ability.getRule();
        } else {
            return "";
        }
    }

    @Override
    public void setValue(String key, Object value) {
        ability.getEffects().setValue(key, value);
        super.setValue(key, value);
    }

    @Override
    public CreateDelayedTriggeredAbilityEffect setTargetPointer(TargetPointer targetPointer) {
        ability.getEffects().setTargetPointer(targetPointer);
        super.setTargetPointer(targetPointer);
        return this;
    }

    public CreateDelayedTriggeredAbilityEffect withCopyToPointer(boolean copyToPointer) {
        this.copyToPointer = copyToPointer;
        return this;
    }

    @Override
    public CreateDelayedTriggeredAbilityEffect withTargetDescription(String target) {
        ability.getEffects().forEach(effect -> effect.withTargetDescription(target));
        super.withTargetDescription(target);
        return this;
    }
}
