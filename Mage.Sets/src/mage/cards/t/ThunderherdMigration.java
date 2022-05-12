
package mage.cards.t;

import java.util.UUID;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class ThunderherdMigration extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Dinosaur card from your hand");

    static {
        filter.add(SubType.DINOSAUR.getPredicate());
    }

    public ThunderherdMigration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // As an additional cost to cast Thunderherd Migration, reveal a Dinosaur card from your hand or pay {1}.
        this.getSpellAbility().addCost(new OrCost(
                "reveal a Dinosaur card from your hand or pay {1}", new RevealTargetFromHandCost(new TargetCardInHand(filter)),
                new GenericManaCost(1)
        ));

        // Search your library for a basic land card, put it onto the battlefield tapped, then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true, true));
    }

    private ThunderherdMigration(final ThunderherdMigration card) {
        super(card);
    }

    @Override
    public ThunderherdMigration copy() {
        return new ThunderherdMigration(this);
    }
}
