package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;

import java.util.UUID;

/**
 * @author muz
 */
public final class McCoyNotABricklayer extends CardImpl {

    private static final FilterPermanent doctorFilter = new FilterPermanent(SubType.DOCTOR);
    private static final FilterCreaturePermanent counterFilter = new FilterCreaturePermanent("creatures you control with counters on them");

    static {
        counterFilter.add(CounterAnyPredicate.instance);
    }

    public McCoyNotABricklayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Whenever McCoy or another Doctor you control enters, put a +1/+1 counter on each creature you control.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
            new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE),
            doctorFilter, false, true
        ));

        // Creatures you control with counters on them have lifelink.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, counterFilter)
        ));
    }

    private McCoyNotABricklayer(final McCoyNotABricklayer card) {
        super(card);
    }

    @Override
    public McCoyNotABricklayer copy() {
        return new McCoyNotABricklayer(this);
    }
}
