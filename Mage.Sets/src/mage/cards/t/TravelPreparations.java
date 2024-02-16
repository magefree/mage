
package mage.cards.t;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class TravelPreparations extends CardImpl {

    public TravelPreparations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");

        // Put a +1/+1 counter on each of up to two target creatures.
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
        effect.setText("Put a +1/+1 counter on each of up to two target creatures");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));

        // Flashback {1}{W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{1}{W}")));
    }

    private TravelPreparations(final TravelPreparations card) {
        super(card);
    }

    @Override
    public TravelPreparations copy() {
        return new TravelPreparations(this);
    }
}
