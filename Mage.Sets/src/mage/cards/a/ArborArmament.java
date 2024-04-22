
package mage.cards.a;

import java.util.UUID;

import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author JRHerlehy
 */
public final class ArborArmament extends CardImpl {

    public ArborArmament(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Put a +1/+1 counter on target creature. That creature gains reach until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(ReachAbility.getInstance(), Duration.EndOfTurn)
                .setText("That creature gains reach until end of turn"));
    }

    private ArborArmament(final ArborArmament card) {
        super(card);
    }

    @Override
    public ArborArmament copy() {
        return new ArborArmament(this);
    }
}
