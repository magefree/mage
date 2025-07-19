package mage.cards.e;

import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Loki
 */
public final class ExplosiveVegetation extends CardImpl {

    public ExplosiveVegetation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Search your library for up to two basic land cards and put them onto the battlefield tapped. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(2, StaticFilters.FILTER_CARD_BASIC_LANDS), true
        ));
    }

    private ExplosiveVegetation(final ExplosiveVegetation card) {
        super(card);
    }

    @Override
    public ExplosiveVegetation copy() {
        return new ExplosiveVegetation(this);
    }
}
