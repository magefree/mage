package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.IntCompareCondition;
import mage.constants.ComparisonType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.Optional;

/**
 * Don't use ComparisonType.OR_GREATER with value 0
 *
 * @author TheElk801
 */
public class SourceHasCounterCondition extends IntCompareCondition {

    private final CounterType counterType;

    public SourceHasCounterCondition(CounterType counterType) {
        this(counterType, 1);
    }

    public SourceHasCounterCondition(CounterType counterType, int amount) {
        this(counterType, ComparisonType.OR_GREATER, amount);
    }

    public SourceHasCounterCondition(CounterType counterType, ComparisonType type, int value) {
        super(type, value);
        this.counterType = counterType;
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent != null) {
            return permanent.getCounters(game).getCount(counterType);
        }
        return Optional.ofNullable(source)
                .map(Ability::getSourceId)
                .map(game::getCard)
                .map(card -> card.getCounters(game).getCount(counterType))
                .orElse(0);
    }

    @Override
    public String toString() {
        switch (type) {
            case EQUAL_TO:
                StringBuilder sb = new StringBuilder("there ");
                switch (value) {
                    case 0:
                        sb.append("are no ");
                        break;
                    case 1:
                        sb.append("is exactly one ");
                        break;
                    default:
                        sb.append("are exactly ");
                        sb.append(CardUtil.numberToText(value));
                        sb.append(' ');
                }
                sb.append(counterType.getName());
                sb.append(" counter");
                if (value != 1) {
                    sb.append('s');
                }
                sb.append(" on {this}");
                return sb.toString();
            case OR_GREATER:
                if (value == 0) {
                    throw new IllegalArgumentException("0 or greater should not be used");
                }
                return "there are " + CardUtil.numberToText(value) + " or more " + counterType.getName() + " counters on {this}";
            case OR_LESS:
                return "{this} has " + CardUtil.numberToText(value) + " or fewer " + counterType.getName() + " counters on it";
            case FEWER_THAN:
                return "{this} has fewer than " + CardUtil.numberToText(value) + ' ' + counterType.getName() + " counters on it";
            case MORE_THAN:
                return "{this} has more than " + CardUtil.numberToText(value) + ' ' + counterType.getName() + " counters on it";
            default:
                throw new UnsupportedOperationException("There should be a comparison type");
        }
    }
}
