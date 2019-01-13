package mage.cards.s;

import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StonyStrength extends CardImpl {

    public StonyStrength(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Put a +1/+1 counter on target creature you control. Untap that creature.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap that creature"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private StonyStrength(final StonyStrength card) {
        super(card);
    }

    @Override
    public StonyStrength copy() {
        return new StonyStrength(this);
    }
}
