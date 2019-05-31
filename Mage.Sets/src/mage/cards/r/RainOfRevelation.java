package mage.cards.r;

import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RainOfRevelation extends CardImpl {

    public RainOfRevelation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Draw three cards, then discard a card.
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(3, 1));
    }

    private RainOfRevelation(final RainOfRevelation card) {
        super(card);
    }

    @Override
    public RainOfRevelation copy() {
        return new RainOfRevelation(this);
    }
}
