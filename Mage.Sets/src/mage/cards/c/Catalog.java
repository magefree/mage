
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Backfir3
 */
public final class Catalog extends CardImpl {

    public Catalog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");


        // Draw two cards, then discard a card.
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(2,1));
    }

    private Catalog(final Catalog card) {
        super(card);
    }

    @Override
    public Catalog copy() {
        return new Catalog(this);
    }
}
