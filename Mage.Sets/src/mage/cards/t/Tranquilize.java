package mage.cards.t;

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
public final class Tranquilize extends CardImpl {

    public Tranquilize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Tap target creature an opponent controls and put three stun counters on it.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance(3))
                .setText("and put three stun counters on it"));
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());
    }

    private Tranquilize(final Tranquilize card) {
        super(card);
    }

    @Override
    public Tranquilize copy() {
        return new Tranquilize(this);
    }
}
