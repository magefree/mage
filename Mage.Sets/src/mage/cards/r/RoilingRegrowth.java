package mage.cards.r;

import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterBasicLandCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RoilingRegrowth extends CardImpl {

    private static final FilterCard filter = new FilterBasicLandCard("basic land cards");

    public RoilingRegrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Sacrifice a land. Search your library for up to two basic land cards, put them onto the battlefield tapped, then shuffle your library.
        this.getSpellAbility().addEffect(new SacrificeControllerEffect(
                StaticFilters.FILTER_LAND, 1, null
        ).setText("Sacrifice a land."));
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(0, 2, filter), true
        ));
    }

    private RoilingRegrowth(final RoilingRegrowth card) {
        super(card);
    }

    @Override
    public RoilingRegrowth copy() {
        return new RoilingRegrowth(this);
    }
}
