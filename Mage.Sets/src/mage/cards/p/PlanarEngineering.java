package mage.cards.p;

import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PlanarEngineering extends CardImpl {

    public PlanarEngineering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Sacrifice two lands. Search your library for four basic land cards, put them onto the battlefield tapped, then shuffle.
        this.getSpellAbility().addEffect(new SacrificeControllerEffect(StaticFilters.FILTER_LAND, 2, ""));
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(
                0, 4, StaticFilters.FILTER_CARD_BASIC_LANDS
        ), true));
    }

    private PlanarEngineering(final PlanarEngineering card) {
        super(card);
    }

    @Override
    public PlanarEngineering copy() {
        return new PlanarEngineering(this);
    }
}
