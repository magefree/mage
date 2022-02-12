package mage.cards.y;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YoshimaruEverFaithful extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("another legendary permanent");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public YoshimaruEverFaithful(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever another legendary permanent enters the battlefield under your control, put a +1/+1 counter on Yoshimaru, Ever Faithful.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter
        ));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private YoshimaruEverFaithful(final YoshimaruEverFaithful card) {
        super(card);
    }

    @Override
    public YoshimaruEverFaithful copy() {
        return new YoshimaruEverFaithful(this);
    }
}
