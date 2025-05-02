package mage.cards.s;

import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SuccumbToTheCold extends CardImpl {

    public SuccumbToTheCold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Tap one or two target creatures an opponent controls. Put a stun counter on each of them.
        this.getSpellAbility().addEffect(new TapTargetEffect("tap one or two target creatures an opponent controls"));
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance()).setText("put a stun counter on each of them"));
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent(1, 2));
    }

    private SuccumbToTheCold(final SuccumbToTheCold card) {
        super(card);
    }

    @Override
    public SuccumbToTheCold copy() {
        return new SuccumbToTheCold(this);
    }
}
