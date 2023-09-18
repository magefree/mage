

package mage.cards.i;

import java.util.UUID;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author Viserion
 */
public final class IntoTheRoil extends CardImpl {

    public IntoTheRoil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // Kicker {1}{U} (You may pay an additional {1}{U} as you cast this spell.)
        this.addAbility(new KickerAbility("{1}{U}"));

        // Return target nonland permanent to its owner's hand. If Into the Roil was kicked, draw a card.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1),
                KickedCondition.ONCE,
                "if this spell was kicked, draw a card"));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private IntoTheRoil(final IntoTheRoil card) {
        super(card);
    }

    @Override
    public IntoTheRoil copy() {
        return new IntoTheRoil(this);
    }

}
