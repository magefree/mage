package mage.cards.e;

import java.util.UUID;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author muz
 */
public final class EntrustTheSpark extends CardImpl {

    private static final FilterCard filter = new FilterCard("a planeswalker card");

    static {
        filter.add(CardType.PLANESWALKER.getPredicate());
    }

    public EntrustTheSpark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{U}");

        // You may sacrifice a planeswalker. If you do, search your library for a planeswalker card, put it onto the battlefield, then shuffle.
        this.getSpellAbility().addEffect(new DoIfCostPaid(
            new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)),
            new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_PLANESWALKER)
        ));
    }

    private EntrustTheSpark(final EntrustTheSpark card) {
        super(card);
    }

    @Override
    public EntrustTheSpark copy() {
        return new EntrustTheSpark(this);
    }
}
