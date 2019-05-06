package mage.cards.c;

import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CourageInCrisis extends CardImpl {

    public CourageInCrisis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Put a +1/+1 counter on target creature, then proliferate. (Choose any number of permanents and/or players, then give each another counter of each kind already there.)
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new ProliferateEffect().concatBy(", then"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private CourageInCrisis(final CourageInCrisis card) {
        super(card);
    }

    @Override
    public CourageInCrisis copy() {
        return new CourageInCrisis(this);
    }
}
