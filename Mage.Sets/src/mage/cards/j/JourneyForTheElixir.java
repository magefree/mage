package mage.cards.j;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JourneyForTheElixir extends CardImpl {

    public JourneyForTheElixir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Search your library and graveyard for a basic land card and a card named Jiang Yanggu, reveal them, put them into your hand, then shuffle your library.
        this.getSpellAbility().addEffect(new JourneyForTheElixirEffect());
    }

    private JourneyForTheElixir(final JourneyForTheElixir card) {
        super(card);
    }

    @Override
    public JourneyForTheElixir copy() {
        return new JourneyForTheElixir(this);
    }
}

class JourneyForTheElixirEffect extends OneShotEffect {

    JourneyForTheElixirEffect() {
        super(Outcome.Benefit);
        staticText = "Search your library and graveyard for a basic land card and a card named Jiang Yanggu, " +
                "reveal them, put them into your hand, then shuffle.";
    }

    private JourneyForTheElixirEffect(final JourneyForTheElixirEffect effect) {
        super(effect);
    }

    @Override
    public JourneyForTheElixirEffect copy() {
        return new JourneyForTheElixirEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary targetCardInLibrary = new JourneyForTheElixirLibraryTarget();
        player.searchLibrary(targetCardInLibrary, source, game);
        Cards cards = new CardsImpl(targetCardInLibrary.getTargets());
        TargetCard target = new JourneyForTheElixirGraveyardTarget(cards);
        player.choose(outcome, target, source, game);
        cards.addAll(target.getTargets());
        player.revealCards(source, cards, game);
        player.moveCards(cards, Zone.HAND, source, game);
        player.shuffleLibrary(source, game);
        return true;
    }
}

class JourneyForTheElixirLibraryTarget extends TargetCardInLibrary {

    private static final String name = "Jiang Yanggu";
    private static final FilterCard filter
            = new FilterCard("a basic land card and a card named Jiang Yanggu");

    static {
        filter.add(Predicates.or(
                Predicates.and(
                        SuperType.BASIC.getPredicate(),
                        CardType.LAND.getPredicate()
                ),
                new NamePredicate(name)
        ));
    }

    JourneyForTheElixirLibraryTarget() {
        super(0, 2, filter);
    }

    private JourneyForTheElixirLibraryTarget(final JourneyForTheElixirLibraryTarget target) {
        super(target);
    }

    @Override
    public JourneyForTheElixirLibraryTarget copy() {
        return new JourneyForTheElixirLibraryTarget(this);
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
        if (card.isBasic()
                && card.isLand(game)
                && cards
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .filter(MageObject::isBasic)
                .anyMatch(card1 -> card1.isLand(game))) {
            return false;
        }
        if (name.equals(card.getName())
                && cards
                .getCards(game)
                .stream()
                .map(MageObject::getName)
                .anyMatch(name::equals)) {
            return false;
        }
        return true;
    }
}

class JourneyForTheElixirGraveyardTarget extends TargetCardInYourGraveyard {

    private static final String name = "Jiang Yanggu";
    private static final FilterCard filter
            = new FilterCard("a basic land card and a card named Jiang Yanggu");

    static {
        filter.add(Predicates.or(
                Predicates.and(
                        SuperType.BASIC.getPredicate(),
                        CardType.LAND.getPredicate()
                ),
                new NamePredicate(name)
        ));
    }

    private final Cards cards = new CardsImpl();

    JourneyForTheElixirGraveyardTarget(Cards cards) {
        super(0, Integer.MAX_VALUE, filter, true);
        this.cards.addAll(cards);
    }

    private JourneyForTheElixirGraveyardTarget(final JourneyForTheElixirGraveyardTarget target) {
        super(target);
        this.cards.addAll(target.cards);
    }

    @Override
    public JourneyForTheElixirGraveyardTarget copy() {
        return new JourneyForTheElixirGraveyardTarget(this);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);
        Cards alreadyTargeted = new CardsImpl(this.getTargets());
        alreadyTargeted.addAll(cards);
        boolean hasBasic = alreadyTargeted
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .filter(card1 -> card1.isLand(game))
                .anyMatch(MageObject::isBasic);
        possibleTargets.removeIf(uuid -> {
            Card card = game.getCard(uuid);
            return card != null
                    && hasBasic
                    && card.isLand(game)
                    && card.isBasic();
        });
        boolean hasYanggu = alreadyTargeted
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .map(MageObject::getName)
                .anyMatch(name::equals);
        possibleTargets.removeIf(uuid -> {
            Card card = game.getCard(uuid);
            return card != null
                    && hasYanggu
                    && name.equals(card.getName());
        });
        return possibleTargets;
    }
}
