package mage.cards.w;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Whoosh extends CardImpl {

    public Whoosh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Kicker {1}{U}
        this.addAbility(new KickerAbility("{1}{U}"));

        // Return target nonland permanent to its owner's hand. If this spell was kicked, draw a card.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1),
                KickedCondition.ONCE, "if this spell was kicked, draw a card"
        ));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private Whoosh(final Whoosh card) {
        super(card);
    }

    @Override
    public Whoosh copy() {
        return new Whoosh(this);
    }
}
