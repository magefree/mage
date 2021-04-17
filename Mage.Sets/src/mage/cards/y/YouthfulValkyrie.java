package mage.cards.y;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YouthfulValkyrie extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.ANGEL, "another Angel");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public YouthfulValkyrie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever another Angel enters the battlefield under your control, put a +1/+1 counter on Youthful Valyrie.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter
        ));
    }

    private YouthfulValkyrie(final YouthfulValkyrie card) {
        super(card);
    }

    @Override
    public YouthfulValkyrie copy() {
        return new YouthfulValkyrie(this);
    }
}
