package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GixianInfiltrator extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public GixianInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you sacrifice another permanent, put a +1/+1 counter on Gixian Infiltrator.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter
        ));
    }

    private GixianInfiltrator(final GixianInfiltrator card) {
        super(card);
    }

    @Override
    public GixianInfiltrator copy() {
        return new GixianInfiltrator(this);
    }
}
