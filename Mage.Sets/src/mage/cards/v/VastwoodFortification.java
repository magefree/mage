package mage.cards.v;

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
public final class VastwoodFortification extends CardImpl {

    public VastwoodFortification(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.v.VastwoodThicket.class;

        // Put a +1/+1 counter on target creature.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private VastwoodFortification(final VastwoodFortification card) {
        super(card);
    }

    @Override
    public VastwoodFortification copy() {
        return new VastwoodFortification(this);
    }
}
