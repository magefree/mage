package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoubleCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AscendantAcolyte extends CardImpl {

    public AscendantAcolyte(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Ascendant Acolyte enters the battlefield with a +1/+1 counter on it for each +1/+1 counter among other creatures you control.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(), AscendantAcolyteValue.instance, true
        ), "with a +1/+1 counter on it for each +1/+1 counter among other creatures you control").addHint(AscendantAcolyteValue.getHint()));

        // At the beginning of your upkeep, double the number of +1/+1 counters on Ascendant Acolyte.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DoubleCountersSourceEffect(CounterType.P1P1), TargetController.YOU, false
        ));
    }

    private AscendantAcolyte(final AscendantAcolyte card) {
        super(card);
    }

    @Override
    public AscendantAcolyte copy() {
        return new AscendantAcolyte(this);
    }
}

enum AscendantAcolyteValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint(
            "Total +1/+1 counters on other creatures you control", instance
    );

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE,
                        sourceAbility.getControllerId(),
                        sourceAbility.getSourceId(), game
                ).stream()
                .mapToInt(permanent -> permanent.getCounters(game).getCount(CounterType.P1P1))
                .sum();
    }

    @Override
    public AscendantAcolyteValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}