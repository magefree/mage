package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GaeasBalance extends CardImpl {

    public GaeasBalance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // As an additional cost to cast Gaea's Balance, sacrifice five lands.
        this.getSpellAbility().addCost(new SacrificeTargetCost(
                new TargetControlledPermanent(5, StaticFilters.FILTER_CONTROLLED_LAND_SHORT_TEXT)
        ));

        // Search your library for a land card of each basic land type and put them onto the battlefield. Then shuffle your library.
        this.getSpellAbility().addEffect(new GaeasBalanceEffect());
    }

    private GaeasBalance(final GaeasBalance card) {
        super(card);
    }

    @Override
    public GaeasBalance copy() {
        return new GaeasBalance(this);
    }
}

class GaeasBalanceEffect extends OneShotEffect {

    private static final FilterCard plainsFilter = new FilterLandCard("a Plains land card");
    private static final FilterCard islandFilter = new FilterLandCard("an Island land card");
    private static final FilterCard swampFilter = new FilterLandCard("a Swamp land card");
    private static final FilterCard mountainFilter = new FilterLandCard("a Mountain land card");
    private static final FilterCard forestFilter = new FilterLandCard("a Forest land card");

    static {
        plainsFilter.add(new SubtypePredicate(SubType.PLAINS));
        islandFilter.add(new SubtypePredicate(SubType.ISLAND));
        swampFilter.add(new SubtypePredicate(SubType.SWAMP));
        mountainFilter.add(new SubtypePredicate(SubType.MOUNTAIN));
        forestFilter.add(new SubtypePredicate(SubType.FOREST));
    }

    private static final List<FilterCard> filterList = Arrays.asList(
            plainsFilter, islandFilter, swampFilter, mountainFilter, forestFilter
    );

    GaeasBalanceEffect() {
        super(Outcome.Benefit);
        staticText = "Search your library for a land card of each basic land type " +
                "and put them onto the battlefield. Then shuffle your library.";
    }

    private GaeasBalanceEffect(final GaeasBalanceEffect effect) {
        super(effect);
    }

    @Override
    public GaeasBalanceEffect copy() {
        return new GaeasBalanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        filterList.stream().map(TargetCardInLibrary::new).forEachOrdered(target -> {
            player.searchLibrary(target, source, game, target.getFilter().getMessage().contains("Forest"));
            cards.add(target.getFirstTarget());
        });
        player.moveCards(cards, Zone.BATTLEFIELD, source, game);
        player.shuffleLibrary(source, game);
        return true;
    }
}