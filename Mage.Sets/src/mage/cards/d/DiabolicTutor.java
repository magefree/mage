

package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Loki
 */
public final class DiabolicTutor extends CardImpl {

    public DiabolicTutor(UUID ownerId, CardSetInfo setInfo){
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{B}");

        TargetCardInLibrary target = new TargetCardInLibrary();
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(target, false, true));
    }

    private DiabolicTutor(final DiabolicTutor card) {
        super(card);
    }

    @Override
    public DiabolicTutor copy() {
        return new DiabolicTutor(this);
    }
}
