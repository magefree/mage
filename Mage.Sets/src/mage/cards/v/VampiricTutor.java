
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Quercitron
 */
public final class VampiricTutor extends CardImpl {

    public VampiricTutor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");


        // Search your library for a card, then shuffle your library and put that card on top of it. You lose 2 life.
        this.getSpellAbility().addEffect(new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(), false));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2));
    }

    private VampiricTutor(final VampiricTutor card) {
        super(card);
    }

    @Override
    public VampiricTutor copy() {
        return new VampiricTutor(this);
    }
}
