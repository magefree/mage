
package mage.cards.e;

import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 *
 * @author FenrisulfrX
 */
public final class EdgeOfAutumn extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("a land");

    public EdgeOfAutumn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // If you control four or fewer lands, search your library for a basic land card, put it onto the battlefield tapped, then shuffle your library.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true),
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.FEWER_THAN, 5),
                "If you control four or fewer lands, search your library for a basic land card, put it onto the battlefield tapped, then shuffle."));

        // Cycling-Sacrifice a land.
        this.addAbility(new CyclingAbility(new SacrificeTargetCost(new TargetControlledPermanent(filter))));
    }

    private EdgeOfAutumn(final EdgeOfAutumn card) {
        super(card);
    }

    @Override
    public EdgeOfAutumn copy() {
        return new EdgeOfAutumn(this);
    }
}
