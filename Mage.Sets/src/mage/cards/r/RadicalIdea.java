package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.JumpStartAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class RadicalIdea extends CardImpl {

    public RadicalIdea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));

        // Jump-start
        this.addAbility(new JumpStartAbility(this));
    }

    private RadicalIdea(final RadicalIdea card) {
        super(card);
    }

    @Override
    public RadicalIdea copy() {
        return new RadicalIdea(this);
    }
}
