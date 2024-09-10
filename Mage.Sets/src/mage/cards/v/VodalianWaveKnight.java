package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VodalianWaveKnight extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("other Merfolk and/or Knight you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                SubType.MERFOLK.getPredicate(),
                SubType.KNIGHT.getPredicate()
        ));
    }

    public VodalianWaveKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you draw a card, put a +1/+1 counter on each other Merfolk and/or Knight you control.
        this.addAbility(new DrawCardControllerTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), false
        ));
    }

    private VodalianWaveKnight(final VodalianWaveKnight card) {
        super(card);
    }

    @Override
    public VodalianWaveKnight copy() {
        return new VodalianWaveKnight(this);
    }
}
