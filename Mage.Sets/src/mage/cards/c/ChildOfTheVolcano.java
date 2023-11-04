package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.DescendedThisTurnCondition;
import mage.abilities.dynamicvalue.common.DescendedThisTurnCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.watchers.common.DescendedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChildOfTheVolcano extends CardImpl {

    public ChildOfTheVolcano(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your end step, if you descended this turn, put a +1/+1 counter on Child of the Volcano.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                TargetController.YOU, DescendedThisTurnCondition.instance, false
        ).addHint(DescendedThisTurnCount.getHint()), new DescendedWatcher());
    }

    private ChildOfTheVolcano(final ChildOfTheVolcano card) {
        super(card);
    }

    @Override
    public ChildOfTheVolcano copy() {
        return new ChildOfTheVolcano(this);
    }
}
