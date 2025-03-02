package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Jmlundeen
 */
public final class SpireMechcycle extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another untapped Mount or Vehicle you control");
    private static final FilterControlledPermanent mountAndVehicleFilter = new FilterControlledPermanent("Mount and/or Vehicle you control other than this Vehicle");
    private static final DynamicValue mountAndVehicleCount = new PermanentsOnBattlefieldCount(mountAndVehicleFilter);

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(Predicates.or(
                SubType.MOUNT.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
        mountAndVehicleFilter.add(AnotherPredicate.instance);
        mountAndVehicleFilter.add(Predicates.or(
                SubType.MOUNT.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public SpireMechcycle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{R}");
        
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Exhaust -- Tap another untapped Mount or Vehicle you control: This Vehicle becomes an artifact creature. Put a +1/+1 counter on it for each Mount and/or Vehicle you control other than this Vehicle.
        Effect effect = new AddCardTypeSourceEffect(Duration.WhileOnBattlefield, CardType.ARTIFACT, CardType.CREATURE)
                .setText("This Vehicle becomes an artifact creature");
        Ability ability = new ExhaustAbility(effect, new TapTargetCost(new TargetControlledPermanent(filter)));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(), mountAndVehicleCount)
                .setText("Put a +1/+1 counter on it for each Mount and/or Vehicle you control other than this Vehicle"));
        this.addAbility(ability);
        // Crew 2
        this.addAbility(new CrewAbility(2));

    }

    private SpireMechcycle(final SpireMechcycle card) {
        super(card);
    }

    @Override
    public SpireMechcycle copy() {
        return new SpireMechcycle(this);
    }
}
