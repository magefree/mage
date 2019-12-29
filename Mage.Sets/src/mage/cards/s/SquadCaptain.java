package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
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
public final class SquadCaptain extends CardImpl {

    private static final DynamicValue xValue
            = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE);

    public SquadCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Squad Captain enters the battlefield with a +1/+1 counter on it for each other creature you control.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(), xValue, false
        ).setText("with a +1/+1 counter on it for each other creature you control")));
    }

    private SquadCaptain(final SquadCaptain card) {
        super(card);
    }

    @Override
    public SquadCaptain copy() {
        return new SquadCaptain(this);
    }
}
