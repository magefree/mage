package mage.cards.g;

import mage.abilities.effects.common.DoubleCountersTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GrowthCurve extends CardImpl {

    public GrowthCurve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}{U}");

        // Put a +1/+1 counter on target creature you control, then double the number of +1/+1 counters on that creature.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new DoubleCountersTargetEffect(CounterType.P1P1)
                .withTargetDescription("that creature")
                .concatBy(", then"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private GrowthCurve(final GrowthCurve card) {
        super(card);
    }

    @Override
    public GrowthCurve copy() {
        return new GrowthCurve(this);
    }
}
