package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.WaterbendCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FoggySwampVinebender extends CardImpl {

    public FoggySwampVinebender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // This creature can't be blocked by creatures with power 2 or less.
        this.addAbility(new DauntAbility());

        // Waterbend {5}: Put a +1/+1 counter on this creature. Activate only during your turn.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new WaterbendCost(5), MyTurnCondition.instance
        ));
    }

    private FoggySwampVinebender(final FoggySwampVinebender card) {
        super(card);
    }

    @Override
    public FoggySwampVinebender copy() {
        return new FoggySwampVinebender(this);
    }
}
