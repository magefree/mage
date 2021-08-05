package mage.cards.u;

import mage.abilities.Mode;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnboundedPotential extends CardImpl {

    public UnboundedPotential(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Choose one —
        // • Put a +1/+1 counter on each of up to two target creatures.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                .setText("put a +1/+1 counter on each of up to two target creatures"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));

        // • Proliferate.
        this.getSpellAbility().addMode(new Mode(new ProliferateEffect()));

        // Entwine {3}{W}
        this.addAbility(new EntwineAbility("{3}{W}"));
    }

    private UnboundedPotential(final UnboundedPotential card) {
        super(card);
    }

    @Override
    public UnboundedPotential copy() {
        return new UnboundedPotential(this);
    }
}
