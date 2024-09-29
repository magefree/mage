package mage.cards.f;

import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FreezeInPlace extends CardImpl {

    public FreezeInPlace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Tap target creature an opponent controls and put three stun counters on it. Scry 2.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance(3))
                .setText("and put three stun counters on it"));
        this.getSpellAbility().addEffect(new ScryEffect(2));
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());
    }

    private FreezeInPlace(final FreezeInPlace card) {
        super(card);
    }

    @Override
    public FreezeInPlace copy() {
        return new FreezeInPlace(this);
    }
}
