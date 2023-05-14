
package mage.cards.c;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Plopman
 */
public final class CropRotation extends CardImpl {

    public CropRotation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");


        // As an additional cost to cast Crop Rotation, sacrifice a land.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_LAND_SHORT_TEXT)));

        // Search your library for a land card and put that card onto the battlefield. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(new FilterLandCard()), false, true));
    }

    private CropRotation(final CropRotation card) {
        super(card);
    }

    @Override
    public CropRotation copy() {
        return new CropRotation(this);
    }
}
