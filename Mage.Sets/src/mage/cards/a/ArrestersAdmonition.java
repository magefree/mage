package mage.cards.a;

import mage.abilities.condition.common.AddendumCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArrestersAdmonition extends CardImpl {

    public ArrestersAdmonition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Return target creature to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Addendum — If you cast this spell during your main phase, draw a card.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), AddendumCondition.instance,
                "<br><i>Addendum</i> &mdash; If you cast this spell during your main phase, draw a card."
        ));
    }

    private ArrestersAdmonition(final ArrestersAdmonition card) {
        super(card);
    }

    @Override
    public ArrestersAdmonition copy() {
        return new ArrestersAdmonition(this);
    }
}
