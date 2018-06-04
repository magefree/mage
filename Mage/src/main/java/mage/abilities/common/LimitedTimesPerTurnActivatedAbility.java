
package mage.abilities.common;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class LimitedTimesPerTurnActivatedAbility extends ActivatedAbilityImpl {

    public LimitedTimesPerTurnActivatedAbility(Zone zone, Effect effect, Cost cost) {
        this(zone, effect, cost, 1);
    }

    public LimitedTimesPerTurnActivatedAbility(Zone zone, Effect effect, Cost cost, int maxActivationsPerTurn) {
        this(zone, effect, cost, maxActivationsPerTurn, null);
    }

    public LimitedTimesPerTurnActivatedAbility(Zone zone, Effect effect, Cost cost, int maxActivationsPerTurn, Condition condition) {
        super(zone, effect, cost);
        this.maxActivationsPerTurn = maxActivationsPerTurn;
        this.condition = condition;
    }

    public LimitedTimesPerTurnActivatedAbility(final LimitedTimesPerTurnActivatedAbility ability) {
        super(ability);
        this.maxActivationsPerTurn = ability.maxActivationsPerTurn;
        this.condition = ability.condition;
    }

    @Override
    public boolean resolve(Game game) {
        return super.resolve(game);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder(super.getRule()).append(" Activate this ability ");
        if (condition != null) {
            sb.append("only ").append(condition.toString()).append(" and ");
        }
        if (getTiming() == TimingRule.SORCERY) {
            sb.append("only any time you could cast a sorcery and ");
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
