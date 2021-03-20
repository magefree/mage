
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author fireshoes
 */
public final class RidgescaleTusker extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("other creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public RidgescaleTusker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.PANGOLIN);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Ridgescale Tusker enters the battlefield, put a +1/+1 counter on each other creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(1), filter)));
    }

    private RidgescaleTusker(final RidgescaleTusker card) {
        super(card);
    }

    @Override
    public RidgescaleTusker copy() {
        return new RidgescaleTusker(this);
    }
}
