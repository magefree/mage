
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class Sift extends CardImpl {

    public Sift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}");


        // Draw three cards, then discard a card.
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(3,1));
    }

    private Sift(final Sift card) {
        super(card);
    }

    @Override
    public Sift copy() {
        return new Sift(this);
    }
}
