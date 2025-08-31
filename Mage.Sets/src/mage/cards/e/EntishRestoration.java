package mage.cards.e;

import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class EntishRestoration extends CardImpl {

    private static final String rule = "Search your library for up to two basic land cards, " +
            "put them onto the battlefield tapped, then shuffle. " +
            "If " + FerociousCondition.instance + ", instead search your library for up " +
            "to three basic land cards, put them onto the battlefield tapped, then shuffle.";

    public EntishRestoration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Sacrifice a land. Search your library for up to two basic land cards,
        // put them onto the battlefield tapped, then shuffle. If you control a
        // creature with power 4 or greater, instead search your library for up
        // to three basic land cards, put them onto the battlefield tapped, then shuffle.
        this.getSpellAbility().addEffect(new SacrificeControllerEffect(
                StaticFilters.FILTER_LAND, 1, null
        ).setText("Sacrifice a land."));
        this.getSpellAbility().addEffect(
                new ConditionalOneShotEffect(
                        new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 3, StaticFilters.FILTER_CARD_BASIC_LANDS), true),
                        new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LANDS), true),
                        FerociousCondition.instance, rule
                )
        );
    }

    private EntishRestoration(final EntishRestoration card) {
        super(card);
    }

    @Override
    public EntishRestoration copy() {
        return new EntishRestoration(this);
    }
}
