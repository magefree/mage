package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AeronautCavalry extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.SOLDIER, "another target Soldier you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AeronautCavalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Aeronaut Cavalry enters the battlefield, put a +1/+1 counter on another target Soldier you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private AeronautCavalry(final AeronautCavalry card) {
        super(card);
    }

    @Override
    public AeronautCavalry copy() {
        return new AeronautCavalry(this);
    }
}
