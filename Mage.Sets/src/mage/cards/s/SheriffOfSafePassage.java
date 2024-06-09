package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.PlotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SheriffOfSafePassage extends CardImpl {

    private static final DynamicValue value = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_OTHER_CONTROLLED_CREATURES);
    private static final DynamicValue xValue = new IntPlusDynamicValue(1, value);
    private static final Hint hint = new ValueHint("Other creatures you control", value);

    public SheriffOfSafePassage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Sheriff of Safe Passage enters the battlefield with a +1/+1 counter on it plus an additional +1/+1 counter on it for each other creature you control.
        this.addAbility(
                new EntersBattlefieldAbility(
                        new AddCountersSourceEffect(
                                CounterType.P1P1.createInstance(),
                                xValue, false
                        ),
                        "with a +1/+1 counter on it plus an additional +1/+1 counter on it for each other creature you control"
                ).addHint(hint)
        );

        // Plot {1}{W}
        this.addAbility(new PlotAbility("{1}{W}"));
    }

    private SheriffOfSafePassage(final SheriffOfSafePassage card) {
        super(card);
    }

    @Override
    public SheriffOfSafePassage copy() {
        return new SheriffOfSafePassage(this);
    }
}
