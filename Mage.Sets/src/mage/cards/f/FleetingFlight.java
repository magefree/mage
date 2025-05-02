package mage.cards.f;

import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class FleetingFlight extends CardImpl {

    public FleetingFlight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Put a +1/+1 counter on target creature. It gains flying until end of turn. Prevent all combat damage that would be dealt to it this turn.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn)
                .setText("It gains flying until end of turn"));
        this.getSpellAbility().addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn, true)
                .setText("Prevent all combat damage that would be dealt to it this turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FleetingFlight(final FleetingFlight card) {
        super(card);
    }

    @Override
    public FleetingFlight copy() {
        return new FleetingFlight(this);
    }
}
