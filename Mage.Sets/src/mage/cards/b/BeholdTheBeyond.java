
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author fireshoes
 */
public final class BeholdTheBeyond extends CardImpl {

    public BeholdTheBeyond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{B}{B}");

        // Discard your hand. Search your library for three cards and put those cards into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new DiscardHandControllerEffect());
        TargetCardInLibrary target = new TargetCardInLibrary(0, 3, new FilterCard("cards"));
        Effect effect = new SearchLibraryPutInHandEffect(target, false);
        effect.setText("Search your library for three cards, put those cards into your hand, then shuffle");
        this.getSpellAbility().addEffect(effect);
    }

    private BeholdTheBeyond(final BeholdTheBeyond card) {
        super(card);
    }

    @Override
    public BeholdTheBeyond copy() {
        return new BeholdTheBeyond(this);
    }
}
