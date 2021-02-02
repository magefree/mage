
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardHandDrawSameNumberSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author emerald000
 */
public final class TolarianWinds extends CardImpl {

    public TolarianWinds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Discard all the cards in your hand, then draw that many cards.
        this.getSpellAbility().addEffect(new DiscardHandDrawSameNumberSourceEffect());
    }

    private TolarianWinds(final TolarianWinds card) {
        super(card);
    }

    @Override
    public TolarianWinds copy() {
        return new TolarianWinds(this);
    }
}
