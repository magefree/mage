package mage.cards.s;

import java.util.UUID;

import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class SoldierOn extends CardImpl {

    public SoldierOn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Put two +1/+1 counters on target creature. Untap it.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SoldierOn(final SoldierOn card) {
        super(card);
    }

    @Override
    public SoldierOn copy() {
        return new SoldierOn(this);
    }
}
