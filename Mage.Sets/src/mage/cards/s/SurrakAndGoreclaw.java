package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SurrakAndGoreclaw extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another nontoken creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public SurrakAndGoreclaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BEAR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Other creatures you control have trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES, true
        )));

        // Whenever another nontoken creature enters the battlefield under your control, put a +1/+1 counter on it. It gains haste until end of turn.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter on it"),
                filter, false, SetTargetPointer.PERMANENT, null
        );
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setText("it gains haste until end of turn"));
        this.addAbility(ability);
    }

    private SurrakAndGoreclaw(final SurrakAndGoreclaw card) {
        super(card);
    }

    @Override
    public SurrakAndGoreclaw copy() {
        return new SurrakAndGoreclaw(this);
    }
}
