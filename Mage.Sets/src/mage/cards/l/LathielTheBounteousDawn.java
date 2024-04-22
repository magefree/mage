package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetAmount;
import mage.target.common.TargetCreaturePermanentAmount;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LathielTheBounteousDawn extends CardImpl {

    private static final Condition condition = new YouGainedLifeCondition(ComparisonType.MORE_THAN, 0);

    public LathielTheBounteousDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.UNICORN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // At the beginning of each end step, if you gained life this turn, distribute up to that many +1/+1 counters among any number of other target creatures.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(new DistributeCountersEffect(
                        CounterType.P1P1, 1, false, ""
                ), TargetController.ANY, false),
                condition, "At the beginning of each end step, if you gained life this turn, " +
                "distribute up to that many +1/+1 counters among any number of other target creatures."
        );
        TargetAmount target = new TargetCreaturePermanentAmount(
                LathielTheBounteousDawnValue.instance,
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        );
        target.setMinNumberOfTargets(0);
        ability.addTarget(target);
        this.addAbility(ability.addHint(LathielTheBounteousDawnValue.getHint()), new PlayerGainedLifeWatcher());
    }

    private LathielTheBounteousDawn(final LathielTheBounteousDawn card) {
        super(card);
    }

    @Override
    public LathielTheBounteousDawn copy() {
        return new LathielTheBounteousDawn(this);
    }
}

enum LathielTheBounteousDawnValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("Life gained this turn", instance);

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        PlayerGainedLifeWatcher watcher = game.getState().getWatcher(PlayerGainedLifeWatcher.class);
        return watcher == null ? 0 : watcher.getLifeGained(sourceAbility.getControllerId());
    }

    @Override
    public LathielTheBounteousDawnValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }

    public static Hint getHint() {
        return hint;
    }
}