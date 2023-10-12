package mage.abilities.decorator;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.constants.EffectType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;

/**
 * @author LevelX
 */
public class ConditionalActivatedAbility extends ActivatedAbilityImpl {

    private static final Effects emptyEffects = new Effects();

    private String ruleText = null;

    public ConditionalActivatedAbility(Effect effect, Cost cost, Condition condition) {
        this(Zone.BATTLEFIELD, effect, cost, condition);
    }

    public ConditionalActivatedAbility(Zone zone, Effect effect, Cost cost, Condition condition) {
        super(zone, effect, cost);
        this.condition = condition;
    }

    public ConditionalActivatedAbility(Zone zone, Effect effect, Cost cost, Condition condition, String rule) {
        this(zone, effect, cost, condition);
        this.ruleText = rule;
    }

    protected ConditionalActivatedAbility(final ConditionalActivatedAbility ability) {
        super(ability);
        this.ruleText = ability.ruleText;
    }

    @Override
    public Effects getEffects(Game game, EffectType effectType) {
        if (!condition.apply(game, this)) {
            return emptyEffects;
        }
        return super.getEffects(game, effectType);
    }

    @Override
    public ConditionalActivatedAbility copy() {
        return new ConditionalActivatedAbility(this);
    }

    @Override
    public String getRule() {
        if (ruleText != null && !ruleText.isEmpty()) {
            return ruleText;
        }
        StringBuilder sb = new StringBuilder(super.getRule());
        sb.append(" Activate only ");
        if (timing == TimingRule.SORCERY) {
            sb.append("as a sorcery and only ");
        }
        String conditionText = condition.toString();
        if (!conditionText.startsWith("during") && !conditionText.startsWith("before") && !conditionText.startsWith("if")) {
            sb.append("if ");
        }
        sb.append(conditionText);
        sb.append('.');
        return sb.toString();
    }
}
