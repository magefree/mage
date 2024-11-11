package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.OneOrMoreCountersAddedTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
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
public final class GenerousPup extends CardImpl {

    public GenerousPup(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever one or more +1/+1 counters are put on Generous Pup, put a +1/+1 counter on each other creature you control. This ability triggers only once each turn.
        this.addAbility(new OneOrMoreCountersAddedTriggeredAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_OTHER_CONTROLLED_CREATURE
        )).setTriggersLimitEachTurn(1));
    }

    private GenerousPup(final GenerousPup card) {
        super(card);
    }

    @Override
    public GenerousPup copy() {
        return new GenerousPup(this);
    }
}
