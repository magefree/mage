
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.UntapLandsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Plopman
 */
public final class FranticSearch extends CardImpl {

    public FranticSearch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");


        // Draw two cards, then discard two cards. Untap up to three lands.
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(2, 2));
        this.getSpellAbility().addEffect(new UntapLandsEffect(3));
    }

    private FranticSearch(final FranticSearch card) {
        super(card);
    }

    @Override
    public FranticSearch copy() {
        return new FranticSearch(this);
    }
}
