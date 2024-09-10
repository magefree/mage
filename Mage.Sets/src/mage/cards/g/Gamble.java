
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class Gamble extends CardImpl {

    public Gamble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}");


        // Search your library for a card, put that card into your hand, discard a card at random, then shuffle your library.
        Effect effect = new SearchLibraryPutInHandEffect(new TargetCardInLibrary(), false);
        effect.setText("Search your library for a card, put that card into your hand");        
        this.getSpellAbility().addEffect(effect);
        effect = new DiscardControllerEffect(1, true);
        effect.setText(", discard a card at random, then shuffle");
        this.getSpellAbility().addEffect(effect);

    }

    private Gamble(final Gamble card) {
        super(card);
    }

    @Override
    public Gamble copy() {
        return new Gamble(this);
    }
}
