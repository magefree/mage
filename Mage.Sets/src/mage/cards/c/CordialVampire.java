package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CordialVampire extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.VAMPIRE, "Vampire you control");

    public CordialVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Cordial Vampire or another creature dies, put a +1/+1 counter on each vampire creature you control.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), false
        ));
    }

    private CordialVampire(final CordialVampire card) {
        super(card);
    }

    @Override
    public CordialVampire copy() {
        return new CordialVampire(this);
    }
}
