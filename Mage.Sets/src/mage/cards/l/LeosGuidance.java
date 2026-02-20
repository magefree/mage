package mage.cards.l;

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
public final class LeosGuidance extends CardImpl {

    public LeosGuidance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Put a +1/+1 counter on each of up to three target creatures. Untap them.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance()
        ).setText("Put a +1/+1 counter on each of up to three target creatures"));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap them"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 3));
    }

    private LeosGuidance(final LeosGuidance card) {
        super(card);
    }

    @Override
    public LeosGuidance copy() {
        return new LeosGuidance(this);
    }
}
