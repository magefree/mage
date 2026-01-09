package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
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
public final class ReluctantDounguard extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.M1M1);

    public ReluctantDounguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // This creature enters with two -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.M1M1.createInstance(2)),
                "with two -1/-1 counters on it"
        ));

        // Whenever another creature you control enters while this creature has a -1/-1 counter on it, remove a -1/-1 counter from this creature.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new RemoveCounterSourceEffect(CounterType.M1M1.createInstance()),
                StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL
        ).withTriggerCondition(condition));
    }

    private ReluctantDounguard(final ReluctantDounguard card) {
        super(card);
    }

    @Override
    public ReluctantDounguard copy() {
        return new ReluctantDounguard(this);
    }
}
