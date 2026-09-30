package mage.cards.p;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class PredictivePreparations extends CardImpl {

    public PredictivePreparations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Put a +1/+1 counter on each of one or two target creatures.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(1, 2));

        // Flashback {3}{W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{3}{W}")));
    }

    private PredictivePreparations(final PredictivePreparations card) {
        super(card);
    }

    @Override
    public PredictivePreparations copy() {
        return new PredictivePreparations(this);
    }
}
