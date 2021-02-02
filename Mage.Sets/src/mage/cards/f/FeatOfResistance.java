
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainProtectionFromColorTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class FeatOfResistance extends CardImpl {

    public FeatOfResistance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");


        // Put a +1/+1 counter on target creature you control. It gains protection from the color of your choice until end of turn.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        Effect effect = new GainProtectionFromColorTargetEffect(Duration.EndOfTurn);
        effect.setText("It gains protection from the color of your choice until end of turn");
        this.getSpellAbility().addEffect(effect);
    }

    private FeatOfResistance(final FeatOfResistance card) {
        super(card);
    }

    @Override
    public FeatOfResistance copy() {
        return new FeatOfResistance(this);
    }
}
