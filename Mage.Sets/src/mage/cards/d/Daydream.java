package mage.cards.d;

import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class Daydream extends CardImpl {

    public Daydream(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}");

        // Exile target creature you control, then return that card to the battlefield under its owner's control with a +1/+1 counter on it.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(
            new ExileThenReturnTargetEffect(false, false).withEnterWithCounters(CounterType.P1P1.createInstance())
        );

        // Flashback {2}{W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{2}{W}")));
    }

    private Daydream(final Daydream card) {
        super(card);
    }

    @Override
    public Daydream copy() {
        return new Daydream(this);
    }
}
