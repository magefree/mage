package mage.cards.l;

import java.util.UUID;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author NinthWorld
 */
public final class Liftoff extends CardImpl {

    public Liftoff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");
        

        // Return target permanent you control to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetControlledPermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public Liftoff(final Liftoff card) {
        super(card);
    }

    @Override
    public Liftoff copy() {
        return new Liftoff(this);
    }
}
