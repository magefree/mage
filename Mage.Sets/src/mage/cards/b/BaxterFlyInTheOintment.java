package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BaxterFlyInTheOintment extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("each creature you control with a counter on it");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public BaxterFlyInTheOintment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Baxter enters or attacks, each creature you control with a counter on it gains flying until end of turn.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new GainAbilityAllEffect(FlyingAbility.getInstance(), Duration.EndOfTurn, filter)
        ));

        // Whenever you draw a card, put a +1/+1 counter on Baxter.
        this.addAbility(new DrawCardControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false
        ));
    }

    private BaxterFlyInTheOintment(final BaxterFlyInTheOintment card) {
        super(card);
    }

    @Override
    public BaxterFlyInTheOintment copy() {
        return new BaxterFlyInTheOintment(this);
    }
}
