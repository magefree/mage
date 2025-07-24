package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
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
public final class TwistedSpiderClone extends CardImpl {

    public TwistedSpiderClone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When this creature enters, put a +1/+1 counter on each creature you control with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE_P1P1
        )));
    }

    private TwistedSpiderClone(final TwistedSpiderClone card) {
        super(card);
    }

    @Override
    public TwistedSpiderClone copy() {
        return new TwistedSpiderClone(this);
    }
}
