
package mage.cards.g;

import java.util.UUID;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public final class GrowFromTheAshes extends CardImpl {

    public GrowFromTheAshes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Kicker {2} (You may pay an additional {2} as you cast this spell.)
        this.addAbility(new KickerAbility("{2}"));

        // Search you library for a basic land card, put it onto the battlefield, then shuffle your library. If this spell was kicked, instead search your library for two basic land cards, put them onto the battlefield, then shuffle your library.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LAND), false, true),
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD_BASIC_LAND), false, true),
                KickedCondition.ONCE,
                "Search your library for a basic land card, put it onto the battlefield, then shuffle. If this spell was kicked, instead search your library for two basic land cards, put them onto the battlefield, then shuffle."));
    }

    private GrowFromTheAshes(final GrowFromTheAshes card) {
        super(card);
    }

    @Override
    public GrowFromTheAshes copy() {
        return new GrowFromTheAshes(this);
    }
}
