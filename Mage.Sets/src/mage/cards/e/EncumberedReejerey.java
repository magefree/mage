package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EncumberedReejerey extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.M1M1);

    public EncumberedReejerey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // This creature enters with three -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.M1M1.createInstance(3)),
                "with three -1/-1 counters on it"
        ));

        // Whenever this creature becomes tapped while it has a -1/-1 counter on it, remove a -1/-1 counter from it.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(
                new RemoveCounterSourceEffect(CounterType.M1M1.createInstance())
        ).withTriggerCondition(condition));
    }

    private EncumberedReejerey(final EncumberedReejerey card) {
        super(card);
    }

    @Override
    public EncumberedReejerey copy() {
        return new EncumberedReejerey(this);
    }
}
