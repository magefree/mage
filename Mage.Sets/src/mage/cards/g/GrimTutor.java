
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author emerald000
 */
public final class GrimTutor extends CardImpl {

    public GrimTutor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}{B}");


        // Search your library for a card and put that card into your hand, then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary();
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(target).setText("search your library for a card and put that card into your hand, then shuffle"));
        // You lose 3 life.
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(3));

    }

    private GrimTutor(final GrimTutor card) {
        super(card);
    }

    @Override
    public GrimTutor copy() {
        return new GrimTutor(this);
    }
}
