package mage.cards.s;

import mage.abilities.effects.common.PhaseOutTargetEffect;
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
public final class SlipOutTheBack extends CardImpl {

    public SlipOutTheBack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Put a +1/+1 counter on target creature. It phases out.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new PhaseOutTargetEffect("it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SlipOutTheBack(final SlipOutTheBack card) {
        super(card);
    }

    @Override
    public SlipOutTheBack copy() {
        return new SlipOutTheBack(this);
    }
}
