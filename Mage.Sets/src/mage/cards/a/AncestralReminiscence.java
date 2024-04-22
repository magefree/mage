package mage.cards.a;

import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AncestralReminiscence extends CardImpl {

    public AncestralReminiscence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Draw three cards, then discard a card.
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(3, 1));
    }

    private AncestralReminiscence(final AncestralReminiscence card) {
        super(card);
    }

    @Override
    public AncestralReminiscence copy() {
        return new AncestralReminiscence(this);
    }
}
