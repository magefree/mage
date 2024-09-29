package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.card.FaceDownPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SumalaSentry extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("a face-down permanent you control");

    static {
        filter.add(FaceDownPredicate.instance);
    }

    public SumalaSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever a face-down permanent you control is turned face up, put a +1/+1 counter on it and a +1/+1 counter on Sumala Sentry.
        Ability ability = new TurnedFaceUpAllTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter on it"),
                filter, true
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                .setText("and a +1/+1 counter on {this}"));
        this.addAbility(ability);
    }

    private SumalaSentry(final SumalaSentry card) {
        super(card);
    }

    @Override
    public SumalaSentry copy() {
        return new SumalaSentry(this);
    }
}
