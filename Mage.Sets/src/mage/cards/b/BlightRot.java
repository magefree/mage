package mage.cards.b;

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
public final class BlightRot extends CardImpl {

    public BlightRot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Put four -1/-1 counters on target creature.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(4)));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BlightRot(final BlightRot card) {
        super(card);
    }

    @Override
    public BlightRot copy() {
        return new BlightRot(this);
    }
}
