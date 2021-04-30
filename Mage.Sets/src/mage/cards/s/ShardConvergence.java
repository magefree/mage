
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.SubTypeAssignment;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Loki
 */
public final class ShardConvergence extends CardImpl {

    public ShardConvergence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Search your library for a Plains card, an Island card, a Swamp card, and a Mountain card. Reveal those cards and put them into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(
                new ShardConvergenceTarget(), true
        ).setText("search your library for a Plains card, an Island card, a Swamp card, and a Mountain card. " +
                "Reveal those cards, put them into your hand, then shuffle"));
    }

    private ShardConvergence(final ShardConvergence card) {
        super(card);
    }

    @Override
    public ShardConvergence copy() {
        return new ShardConvergence(this);
    }
}

class ShardConvergenceTarget extends TargetCardInLibrary {

    private static final FilterCard filter
            = new FilterCard("a Plains card, an Island card, a Swamp card, and a Mountain card");

    static {
        filter.add(Predicates.or(
                SubType.PLAINS.getPredicate(),
                SubType.ISLAND.getPredicate(),
                SubType.SWAMP.getPredicate(),
                SubType.MOUNTAIN.getPredicate()
        ));
    }

    private static final SubTypeAssignment subTypeAssigner = new SubTypeAssignment(
            SubType.PLAINS,
            SubType.ISLAND,
            SubType.SWAMP,
            SubType.MOUNTAIN
    );

    ShardConvergenceTarget() {
        super(0, 4, filter);
    }

    private ShardConvergenceTarget(final ShardConvergenceTarget target) {
        super(target);
    }

    @Override
    public ShardConvergenceTarget copy() {
        return new ShardConvergenceTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        if (this.getTargets().isEmpty()) {
            return true;
        }
        Cards cards = new CardsImpl(this.getTargets());
        cards.add(card);
        return subTypeAssigner.getRoleCount(cards, game) >= cards.size();
    }
}
