package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
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
 * @author North
 */
public final class GemOfBecoming extends CardImpl {

    public GemOfBecoming(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {3}, {tap}, Sacrifice Gem of Becoming: Search your library for an Island card, a Swamp card, and a Mountain card.
        // Reveal those cards and put them into your hand. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(
                new SearchLibraryPutInHandEffect(
                        new GemOfBecomingTarget(), true
                ).setText("search your library for an Island card, a Swamp card, and a Mountain card. " +
                        "Reveal those cards, put them into your hand, then shuffle"), new GenericManaCost(3)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private GemOfBecoming(final GemOfBecoming card) {
        super(card);
    }

    @Override
    public GemOfBecoming copy() {
        return new GemOfBecoming(this);
    }
}

class GemOfBecomingTarget extends TargetCardInLibrary {

    private static final FilterCard filter
            = new FilterCard("an Island card, a Swamp card, and a Mountain card");

    static {
        filter.add(Predicates.or(
                SubType.ISLAND.getPredicate(),
                SubType.SWAMP.getPredicate(),
                SubType.MOUNTAIN.getPredicate()
        ));
    }

    private static final SubTypeAssignment subTypeAssigner = new SubTypeAssignment(
            SubType.ISLAND,
            SubType.SWAMP,
            SubType.MOUNTAIN
    );

    GemOfBecomingTarget() {
        super(0, 3, filter);
    }

    private GemOfBecomingTarget(final GemOfBecomingTarget target) {
        super(target);
    }

    @Override
    public GemOfBecomingTarget copy() {
        return new GemOfBecomingTarget(this);
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
