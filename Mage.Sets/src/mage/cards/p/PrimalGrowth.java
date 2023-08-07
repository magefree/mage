package mage.cards.p;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class PrimalGrowth extends CardImpl {

    public PrimalGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Kicker-Sacrifice a creature.
        this.addAbility(new KickerAbility(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));

        // Search your library for a basic land card, put that card onto the battlefield, then shuffle your library.
        // If Primal Growth was kicked, instead search your library for up to two basic land cards, put them onto the battlefield, then shuffle your library.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LAND), false),
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD_BASIC_LAND), false),
                KickedCondition.ONCE,
                "Search your library for a basic land card, put that card onto the battlefield, then shuffle. If this spell was kicked, instead search your library for up to two basic land cards, put them onto the battlefield, then shuffle"));
    }

    private PrimalGrowth(final PrimalGrowth card) {
        super(card);
    }

    @Override
    public PrimalGrowth copy() {
        return new PrimalGrowth(this);
    }
}
