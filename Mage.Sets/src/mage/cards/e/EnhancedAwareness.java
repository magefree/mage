
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author fireshoes
 */
public final class EnhancedAwareness extends CardImpl {

    public EnhancedAwareness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{U}");

        // Draw three cards, then discard a card.
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(3,1));

    }

    private EnhancedAwareness(final EnhancedAwareness card) {
        super(card);
    }

    @Override
    public EnhancedAwareness copy() {
        return new EnhancedAwareness(this);
    }
}
