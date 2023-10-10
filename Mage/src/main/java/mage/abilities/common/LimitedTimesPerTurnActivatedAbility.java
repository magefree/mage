package mage.abilities.common;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class LimitedTimesPerTurnActivatedAbility extends ActivatedAbilityImpl {

    public LimitedTimesPerTurnActivatedAbility(Zone zone, Effect effect, Cost cost) {
        this(zone, effect, cost, 1);
    }

    public LimitedTimesPerTurnActivatedAbility(Zone zone, Effect effect, Cost cost, int maxActivationsPerTurn) {
        this(zone, effect, cost, maxActivationsPerTurn, null);
    }

    // TODO: add card hint about times activated, see https://github.com/magefree/mage/issues/5497
    public LimitedTimesPerTurnActivatedAbility(Zone zone, Effect effect, Cost cost, int maxActivationsPerTurn, Condition condition) {
        super(zone, effect, cost);
        this.maxActivationsPerTurn = maxActivationsPerTurn;
        this.condition = condition;
    }

    protected LimitedTimesPerTurnActivatedAbility(final LimitedTimesPerTurnActivatedAbility ability) {
        super(ability);
        this.maxActivationsPerTurn = ability.maxActivationsPerTurn;
        this.condition = ability.condition;
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder(super.getRule()).append(" Activate ");
        if (condition != null) {
            String message = condition.toString();
            sb.append("only ").append(message.startsWith("if ") || message.startsWith("during") ? message : "if " + message).append(" and ");
        }
        if (getTiming() == TimingRule.SORCERY) {
            sb.append("only as a sorcery and ");
        }
        switch (maxActivationsPerTurn) {
            case 1:
                sb.append("only once");
                break;
            case 2:
                sb.append("no more than twice");
                break;
            default:
                sb.append("no more than ").append(CardUtil.numberToText(maxActivationsPerTurn)).append(" times");
        }
        sb.append(" each turn.");
        return sb.toString();
    }

    @Override
    public LimitedTimesPerTurnActivatedAbility copy() {
        return new LimitedTimesPerTurnActivatedAbility(this);
    }
}
