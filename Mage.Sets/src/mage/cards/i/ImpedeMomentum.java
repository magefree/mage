package mage.cards.i;

import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImpedeMomentum extends CardImpl {

    public ImpedeMomentum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Tap target creature and put three stun counters on it.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(
                CounterType.STUN.createInstance(3)
        ).setText("and put three stun counters on it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Scry 1.
        this.getSpellAbility().addEffect(new ScryEffect(1).concatBy("<br>"));
    }

    private ImpedeMomentum(final ImpedeMomentum card) {
        super(card);
    }

    @Override
    public ImpedeMomentum copy() {
        return new ImpedeMomentum(this);
    }
}
