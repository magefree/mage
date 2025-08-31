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
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        // search your library and graveyard for 2 cards
        Cards allCards = new CardsImpl();
        allCards.addAll(controller.getLibrary().getCardList());
        allCards.addAll(controller.getGraveyard());
        TargetCard target = new JourneyForTheElixirTarget();
        if (controller.choose(Outcome.Benefit, allCards, target, source, game)) {
            Cards cards = new CardsImpl(target.getTargets());
            controller.revealCards(source, cards, game);
            controller.moveCards(cards, Zone.HAND, source, game);
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}

class JourneyForTheElixirTarget extends TargetCard {

    private static final String name = "Jiang Yanggu";
    private static final FilterCard filter = new FilterCard("a basic land card and a card named Jiang Yanggu");

    static {
        filter.add(Predicates.or(
                Predicates.and(
                        SuperType.BASIC.getPredicate(),
                        CardType.LAND.getPredicate()
                ),
                new NamePredicate(name)
        ));
    }

    JourneyForTheElixirTarget() {
        super(2, 2, Zone.ALL, filter);
    }

    private JourneyForTheElixirTarget(final JourneyForTheElixirTarget target) {
        super(target);
    }

    @Override
    public JourneyForTheElixirTarget copy() {
        return new JourneyForTheElixirTarget(this);
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

        Cards cards = new CardsImpl(this.getTargets());
        boolean hasLand = cards
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .filter(c -> c.isBasic(game))
                .anyMatch(c -> c.isLand(game));
        boolean hasJiang = cards
                .getCards(game)
                .stream()
                .map(MageObject::getName)
                .anyMatch(name::equals);

        if (!hasLand && card.isBasic(game) && card.isLand(game)) {
            return true;
        }

        if (!hasJiang && name.equals(card.getName())) {
            return true;
        }

        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);
        possibleTargets.removeIf(uuid -> !this.canTarget(sourceControllerId, uuid, source, game));
        return possibleTargets;
    }
}
