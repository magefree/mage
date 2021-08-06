package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
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
public final class ChampionOfThePerished extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ZOMBIE, "another Zombie");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ChampionOfThePerished(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever another Zombie enters the battlefield under your control, put a +1/+1 counter on Champion of the Perished.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter));
    }

    private ChampionOfThePerished(final ChampionOfThePerished card) {
        super(card);
    }

    @Override
    public ChampionOfThePerished copy() {
        return new ChampionOfThePerished(this);
    }
}
