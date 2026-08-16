package mage.cards.c;

import java.util.UUID;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
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
public final class CryogenicStasis extends CardImpl {

    public CryogenicStasis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");


        // Tap target creature and put a stun counter on it.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addEffect(
            new AddCountersTargetEffect(CounterType.STUN.createInstance())
                .withTargetDescription("it").concatBy("and")
        );
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private CryogenicStasis(final CryogenicStasis card) {
        super(card);
    }

    @Override
    public CryogenicStasis copy() {
        return new CryogenicStasis(this);
    }
}
