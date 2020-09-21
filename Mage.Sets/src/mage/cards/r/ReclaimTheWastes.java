package mage.cards.r;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReclaimTheWastes extends CardImpl {

    public ReclaimTheWastes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Kicker {3}
        this.addAbility(new KickerAbility("{3}"));

        // Search your library for a basic land card, reveal it, put it into your hand, then shuffle your library. If this spell was kicked, search your library for two basic land cards instead of one.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(
                        0, 2,
                        StaticFilters.FILTER_CARD_BASIC_LAND
                ), true),
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(
                        0, 1,
                        StaticFilters.FILTER_CARD_BASIC_LAND
                ), true), KickedCondition.instance, "search your library for a basic land card, " +
                "reveal it, put it into your hand, then shuffle your library. If this spell was kicked, " +
                "search your library for two basic land cards instead of one"
        ));
    }

    private ReclaimTheWastes(final ReclaimTheWastes card) {
        super(card);
    }

    @Override
    public ReclaimTheWastes copy() {
        return new ReclaimTheWastes(this);
    }
}
