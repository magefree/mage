package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LuxknightBreacher extends CardImpl {

    private static final DynamicValue xValue
            = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE_OR_ARTIFACT);
    private static final Hint hint = new ValueHint("Other creatures and artifacts you control", xValue);

    public LuxknightBreacher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // This creature enters with a +1/+1 counter on it for each other creature and/or artifact you control.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(0), xValue, true
        ), "with a +1/+1 counter on it for each other creature and/or artifact you control").addHint(hint));
    }

    private LuxknightBreacher(final LuxknightBreacher card) {
        super(card);
    }

    @Override
    public LuxknightBreacher copy() {
        return new LuxknightBreacher(this);
    }
}
