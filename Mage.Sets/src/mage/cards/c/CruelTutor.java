
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author dustinconrad
 */
public final class CruelTutor extends CardImpl {

    public CruelTutor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");


        // Search your library for a card, then shuffle your library and put that card on top of it. You lose 2 life.
        TargetCardInLibrary target = new TargetCardInLibrary();
        this.getSpellAbility().addEffect(new SearchLibraryPutOnLibraryEffect(target, false));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2));
    }

    private CruelTutor(final CruelTutor card) {
        super(card);
    }

    @Override
    public CruelTutor copy() {
        return new CruelTutor(this);
    }
}
