
package mage.cards.b;

import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class BlurOfBlades extends CardImpl {

    public BlurOfBlades(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");
        
        // Put a -1/-1 counter on target creature. Blur of Blades deals 2 damage to that creature's controller.
        getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.M1M1.createInstance(1)));
        getSpellAbility().addEffect(new DamageTargetControllerEffect(2));
        getSpellAbility().addTarget(new TargetCreaturePermanent());
        
    }

    private BlurOfBlades(final BlurOfBlades card) {
        super(card);
    }

    @Override
    public BlurOfBlades copy() {
        return new BlurOfBlades(this);
    }
}
