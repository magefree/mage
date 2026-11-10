package mage.cards.b;

import mage.abilities.effects.OneShotNonTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodCurdle extends CardImpl {

    public BloodCurdle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Destroy target creature. Put a menace counter on a creature you control.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new OneShotNonTargetEffect(
                new AddCountersTargetEffect(CounterType.MENACE.createInstance())
                        .setText("Put a menace counter on a creature you control. " +
                                "<i>(It can't be blocked except by two or more creatures.)</i>"),
                new TargetControlledCreaturePermanent()));
    }

    private BloodCurdle(final BloodCurdle card) {
        super(card);
    }

    @Override
    public BloodCurdle copy() {
        return new BloodCurdle(this);
    }
}
