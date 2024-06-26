package mage.cards.c;

import mage.abilities.effects.common.DrawCardTargetControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class CallToHeel extends CardImpl {

    public CallToHeel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Return target creature to its owner's hand. Its controller draws a card.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addEffect(new DrawCardTargetControllerEffect(1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private CallToHeel(final CallToHeel card) {
        super(card);
    }

    @Override
    public CallToHeel copy() {
        return new CallToHeel(this);
    }
}
