package mage.cards.a;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Atomize extends CardImpl {

    public Atomize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{G}");

        // Destroy target nonland permanent. Proliferate.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
        this.getSpellAbility().addEffect(new ProliferateEffect());
    }

    private Atomize(final Atomize card) {
        super(card);
    }

    @Override
    public Atomize copy() {
        return new Atomize(this);
    }
}
