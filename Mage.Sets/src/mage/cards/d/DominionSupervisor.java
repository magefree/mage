package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.target.common.TargetCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class DominionSupervisor extends CardImpl {

    private static final FilterCreaturePermanent filter =
        new FilterCreaturePermanent("creatures you control with counters on them");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public DominionSupervisor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.VORTA);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When this creature enters, put a +1/+1 counter on target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Creatures you control with counters on them have trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ).setText("creatures you control with counters on them have trample")));
    }

    private DominionSupervisor(final DominionSupervisor card) {
        super(card);
    }

    @Override
    public DominionSupervisor copy() {
        return new DominionSupervisor(this);
    }
}
