
package mage.cards.b;

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
 * @author JRHerlehy
 */
public final class BlinkOfAnEye extends CardImpl {

    public BlinkOfAnEye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Kicker {1}{U}
        this.addAbility(new KickerAbility("{1}{U}"));

        // Return target nonland permanent to its owner's hand. If this spell was kicked, draw a card.
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new DrawCardSourceControllerEffect(1), KickedCondition.ONCE,
                                                                      "If this spell was kicked, draw a card"));
    }

    private BlinkOfAnEye(final BlinkOfAnEye card) {
        super(card);
    }

    @Override
    public BlinkOfAnEye copy() {
        return new BlinkOfAnEye(this);
    }
}
