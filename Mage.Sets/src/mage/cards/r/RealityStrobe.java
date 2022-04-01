
package mage.cards.r;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class RealityStrobe extends CardImpl {

    public RealityStrobe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");


        // Return target permanent to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        // Exile Reality Strobe
        this.getSpellAbility().addEffect(new ExileSpellEffect());
        // with three time counters on it.
        Effect effect = new AddCountersSourceEffect(CounterType.TIME.createInstance(), StaticValue.get(3), false, true);
        effect.setText("with three time counters on it");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPermanent());
        
        // Suspend 3-{2}{U}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl<>("{2}{U}"), this));
    }

    private RealityStrobe(final RealityStrobe card) {
        super(card);
    }

    @Override
    public RealityStrobe copy() {
        return new RealityStrobe(this);
    }
}
