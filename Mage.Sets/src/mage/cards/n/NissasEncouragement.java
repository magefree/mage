package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.assignment.common.NameAssignment;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Set;
import java.util.UUID;

/**
 * @author spjspj
 */
public final class NissasEncouragement extends CardImpl {

    public NissasEncouragement(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        // Search your library and graveyard for a card named Forest, a card named Brambleweft Behemoth, and a card named Nissa, Genesis Mage. Reveal those cards, put them into your hand, then shuffle your library.
        this.getSpellAbility().addEffect(new NissasEncouragementEffect());
    }

    private NissasEncouragement(final NissasEncouragement card) {
        super(card);
    }

    @Override
    public NissasEncouragement copy() {
        return new NissasEncouragement(this);
    }
}

class NissasEncouragementEffect extends OneShotEffect {

    public NissasEncouragementEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Search your library and graveyard for a card named Forest, " +
                "a card named Brambleweft Behemoth, and a card named Nissa, Genesis Mage. " +
                "Reveal those cards, put them into your hand, then shuffle.";
    }

    private NissasEncouragementEffect(final NissasEncouragementEffect effect) {
        super(effect);
    }

    @Override
    public NissasEncouragementEffect copy() {
        return new NissasEncouragementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new NissasEncouragementLibraryTarget();
        player.searchLibrary(target, source, game);
        Cards cards = new CardsImpl();
        for (UUID targetId : target.getTargets()) {
            cards.add(player.getLibrary().getCard(targetId, game));
        }
        if (cards.size() < 3) {
            TargetCard graveyardTarget = new NissasEncouragementGraveyardTarget(cards);
            player.choose(outcome, target, source, game);
            cards.addAll(graveyardTarget.getTargets());
        }
        if (!cards.isEmpty()) {
            player.revealCards(source, cards, game);
            player.moveCards(cards, Zone.HAND, source, game);
        }
        player.shuffleLibrary(source, game);
        return true;
    }
}

class NissasEncouragementLibraryTarget extends TargetCardInLibrary {

    private static final FilterCard filter
            = new FilterCard("card named Forest, a card named Brambleweft Behemoth, and a card named Nissa, Genesis Mage");

    static {
        filter.add(Predicates.or(
                new NamePredicate("Forest"),
                new NamePredicate("Brambleweft Behemoth"),
                new NamePredicate("Nissa, Genesis Mage")
        ));
    }

    private static final NameAssignment nameAssigner = new NameAssignment(
            "Forest", "Brambleweft Behemoth", "Nissa, Genesis Mage"
    );

    public NissasEncouragementLibraryTarget() {
        super(0, 3, filter);
    }

    private NissasEncouragementLibraryTarget(final NissasEncouragementLibraryTarget target) {
        super(target);
    }

    @Override
    public NissasEncouragementLibraryTarget copy() {
        return new NissasEncouragementLibraryTarget(this);
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
        return nameAssigner.getRoleCount(cards, game) >= cards.size();
    }
}

class NissasEncouragementGraveyardTarget extends TargetCardInYourGraveyard {

    private static final FilterCard filter
            = new FilterCard("card named Forest, a card named Brambleweft Behemoth, and a card named Nissa, Genesis Mage");

    static {
        filter.add(Predicates.or(
                new NamePredicate("Forest"),
                new NamePredicate("Brambleweft Behemoth"),
                new NamePredicate("Nissa, Genesis Mage")
        ));
    }

    private final Cards cardsAlreadyFound = new CardsImpl();
    private static final NameAssignment nameAssigner = new NameAssignment(
            "Forest", "Brambleweft Behemoth", "Nissa, Genesis Mage"
    );

    public NissasEncouragementGraveyardTarget(Cards cardsAlreadyFound) {
        super(0, 3, filter, true);
        this.cardsAlreadyFound.addAll(cardsAlreadyFound);
    }

    private NissasEncouragementGraveyardTarget(final NissasEncouragementGraveyardTarget target) {
        super(target);
        this.cardsAlreadyFound.addAll(target.cardsAlreadyFound);
    }

    @Override
    public NissasEncouragementGraveyardTarget copy() {
        return new NissasEncouragementGraveyardTarget(this);
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
        cards.addAll(this.cardsAlreadyFound);
        cards.add(card);
        return nameAssigner.getRoleCount(cards, game) >= cards.size();
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);
        if (this.getTargets().isEmpty()) {
            return possibleTargets;
        }
        possibleTargets.removeIf(uuid -> {
            Cards cards = new CardsImpl(this.getTargets());
            cards.addAll(this.cardsAlreadyFound);
            cards.add(game.getCard(uuid));
            return nameAssigner.getRoleCount(cards, game) < cards.size();
        });
        return possibleTargets;
    }
}
