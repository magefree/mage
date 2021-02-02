
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterBasicLandCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Loki
 */
public final class ExplosiveVegetation extends CardImpl {

    public ExplosiveVegetation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Search your library for up to two basic land cards and put them onto the battlefield tapped. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 2, new FilterBasicLandCard("basic land cards")), true));
    }

    private ExplosiveVegetation(final ExplosiveVegetation card) {
        super(card);
    }

    @Override
    public ExplosiveVegetation copy() {
        return new ExplosiveVegetation(this);
    }
}
