
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;

/**
 *
 * @author nantuko
 */
public final class ChampionOfTheParish extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("another Human");
    static {
        filter.add(new SubtypePredicate(SubType.HUMAN));
        filter.add(AnotherPredicate.instance);
    }

    public ChampionOfTheParish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever another Human enters the battlefield under your control, put a +1/+1 counter on Champion of the Parish.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter));
    }

    public ChampionOfTheParish(final ChampionOfTheParish card) {
        super(card);
    }

    @Override
    public ChampionOfTheParish copy() {
        return new ChampionOfTheParish(this);
    }
}
