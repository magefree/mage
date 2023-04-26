package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.SubTypeAssignment;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GaeasBalance extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledLandPermanent("lands");

    public GaeasBalance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // As an additional cost to cast Gaea's Balance, sacrifice five lands.
        this.getSpellAbility().addCost(new SacrificeTargetCost(
                new TargetControlledPermanent(5, filter)
        ));

        // Search your library for a land card of each basic land type and put them onto the battlefield. Then shuffle your library.
        String ruleText = "Search your library for a land card of each basic land type, put those cards onto the battlefield, then shuffle.";
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new GaeasBalanceTarget()).setText(ruleText));
    }

    private GaeasBalance(final GaeasBalance card) {
        super(card);
    }

    @Override
    public GaeasBalance copy() {
        return new GaeasBalance(this);
    }
}

class GaeasBalanceTarget extends TargetCardInLibrary {

    private static final FilterCard filter = new FilterLandCard("a land card of each basic land type");

    static {
        filter.add(Predicates.or(
                SubType.PLAINS.getPredicate(),
                SubType.ISLAND.getPredicate(),
                SubType.SWAMP.getPredicate(),
                SubType.MOUNTAIN.getPredicate(),
                SubType.FOREST.getPredicate()
        ));
    }

    private static final SubTypeAssignment subTypeAssigner = new SubTypeAssignment(
            SubType.PLAINS,
            SubType.ISLAND,
            SubType.SWAMP,
            SubType.MOUNTAIN,
            SubType.FOREST
    );

    GaeasBalanceTarget() {
        super(0, 5, filter);
    }

    private GaeasBalanceTarget(final GaeasBalanceTarget target) {
        super(target);
    }

    @Override
    public GaeasBalanceTarget copy() {
        return new GaeasBalanceTarget(this);
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
