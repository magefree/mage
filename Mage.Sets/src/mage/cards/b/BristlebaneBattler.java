package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.WardAbility;
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
public final class BristlebaneBattler extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.M1M1);

    public BristlebaneBattler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // This creature enters with five -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.M1M1.createInstance(5)));

        // Whenever another creature you control enters while this creature has a -1/-1 counter on it, remove a -1/-1 counter from this creature.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new RemoveCounterSourceEffect(CounterType.M1M1.createInstance()),
                StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL
        ).withTriggerCondition(condition));
    }

    private BristlebaneBattler(final BristlebaneBattler card) {
        super(card);
    }

    @Override
    public BristlebaneBattler copy() {
        return new BristlebaneBattler(this);
    }
}
