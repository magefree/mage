
package mage.cards.p;

import java.util.UUID;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class PrimalGrowth extends CardImpl {

    public PrimalGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Kicker-Sacrifice a creature.
        this.addAbility(new KickerAbility(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT))));

        // Search your library for a basic land card, put that card onto the battlefield, then shuffle your library. If Primal Growth was kicked, instead search your library for up to two basic land cards, put them onto the battlefield, then shuffle your library.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LAND), false, true),
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD_BASIC_LAND), false, true),
                KickedCondition.instance,
                "Search your library for a basic land card, put that card onto the battlefield, then shuffle your library. If this spell was kicked, instead search your library for up to two basic land cards, put them onto the battlefield, then shuffle your library"));
    }

    public PrimalGrowth(final PrimalGrowth card) {
        super(card);
    }

    @Override
    public PrimalGrowth copy() {
        return new PrimalGrowth(this);
    }
}
