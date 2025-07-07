package mage.abilities.common;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.constants.EffectType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public class ActivateIfConditionActivatedAbility extends ActivatedAbilityImpl {

    private static final Effects emptyEffects = new Effects();

    private String conditionText = null;

    public ActivateIfConditionActivatedAbility(Effect effect, Cost cost, Condition condition) {
        this(Zone.BATTLEFIELD, effect, cost, condition);
    }

    public ActivateIfConditionActivatedAbility(Zone zone, Effect effect, Cost cost, Condition condition) {
        super(zone, effect, cost);
        this.condition = condition;
    }

    protected ActivateIfConditionActivatedAbility(final ActivateIfConditionActivatedAbility ability) {
        super(ability);
        this.conditionText = ability.conditionText;
    }

    @Override
    public Effects getEffects(Game game, EffectType effectType) {
        if (!condition.apply(game, this)) {
            return emptyEffects;
        }
        return super.getEffects(game, effectType);
    }

    public ActivateIfConditionActivatedAbility hideCondition() {
        return withConditionText("");
    }

    public ActivateIfConditionActivatedAbility withConditionText(String conditionText) {
        this.conditionText = conditionText;
        return this;
    }

    @Override
    public String getRule() {
        if (conditionText != null) {
            if (conditionText.isEmpty()) {
                return super.getRule();
            }
            return super.getRule() + ' ' + CardUtil.getTextWithFirstCharUpperCase(conditionText) + '.';
        }
        String conditionText = condition.toString();
        if (conditionText.startsWith("You may also")) {
            return super.getRule() + ' ' + conditionText + '.';
        }
        StringBuilder sb = new StringBuilder(super.getRule());
        if (condition instanceof InvertCondition) {
            sb.append(" You can't activate this ability ");
        } else {
            sb.append(" Activate only ");
        }
        if (!conditionText.startsWith("during")
                && !conditionText.startsWith("before")
                && !conditionText.startsWith("if")) {
            sb.append("if ");
        }
        sb.append(conditionText);
        if (timing == TimingRule.SORCERY) {
            sb.append(" and only as a sorcery");
        }
        sb.append('.');
        return sb.toString();
    }

    @Override
    public ActivateIfConditionActivatedAbility copy() {
        return new ActivateIfConditionActivatedAbility(this);
    }
}
