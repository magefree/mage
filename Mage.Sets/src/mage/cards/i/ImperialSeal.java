
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author emerald000
 */
public final class ImperialSeal extends CardImpl {

    public ImperialSeal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");


        // Search your library for a card, then shuffle your library and put that card on top of it. You lose 2 life.
        this.getSpellAbility().addEffect(new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(), false));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2));
    }

    private ImperialSeal(final ImperialSeal card) {
        super(card);
    }

    @Override
    public ImperialSeal copy() {
        return new ImperialSeal(this);
    }
}
