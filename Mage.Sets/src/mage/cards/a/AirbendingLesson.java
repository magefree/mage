package mage.cards.a;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.AirbendTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AirbendingLesson extends CardImpl {

    public AirbendingLesson(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        this.subtype.add(SubType.LESSON);

        // Airbend target nonland permanent.
        this.getSpellAbility().addEffect(new AirbendTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private AirbendingLesson(final AirbendingLesson card) {
        super(card);
    }

    @Override
    public AirbendingLesson copy() {
        return new AirbendingLesson(this);
    }
}
