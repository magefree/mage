package mage.cards.u;

import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnexpectedFangs extends CardImpl {

    public UnexpectedFangs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Put a +1/+1 counter and a lifelink counter on target creature.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                .setText("put a +1/+1 counter"));
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.LIFELINK.createInstance())
                .setText("and a lifelink counter on target creature"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private UnexpectedFangs(final UnexpectedFangs card) {
        super(card);
    }

    @Override
    public UnexpectedFangs copy() {
        return new UnexpectedFangs(this);
    }
}
