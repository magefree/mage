package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetOpponent;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class BindingNegotiation extends CardImpl {

    public BindingNegotiation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Target opponent reveals their hand. You may choose a nonland card from it. If you do, they discard it. Otherwise, you may put a face-up exiled card they own into their graveyard.
        this.getSpellAbility().addEffect(new BindingNegotiationEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private BindingNegotiation(final BindingNegotiation card) {
        super(card);
    }

    @Override
    public BindingNegotiation copy() {
        return new BindingNegotiation(this);
    }
}

class BindingNegotiationEffect extends OneShotEffect {

    BindingNegotiationEffect() {
        super(Outcome.Discard);
        staticText = "Target opponent reveals their hand. You may choose a nonland card from it. "
                + "If you do, they discard it. Otherwise, you may put a face-up exiled card they own into their graveyard";
    }

    private BindingNegotiationEffect(final BindingNegotiationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (player == null || controller == null) {
            return false;
        }
        // Target opponent reveals their hand
        Cards revealedCards = new CardsImpl();
        revealedCards.addAll(player.getHand());
        player.revealCards(source, revealedCards, game);

        // You may choose a nonland card from it.
        TargetCard target = new TargetCard(0, 1, Zone.HAND, new FilterNonlandCard());
        controller.choose(outcome, revealedCards, target, source, game);

        UUID chosenId = target.getFirstTarget();
        if (chosenId != null) {
            // If you do, they discard it.
            Card card = revealedCards.get(target.getFirstTarget(), game);
            player.discard(card, false, source, game);
        } else {
            // Otherwise, you may put a face-up exiled card they own into their graveyard.
            FilterCard filter = new FilterCard("face-up exiled card owned by " + player.getName());
            filter.add(Predicates.not(FaceDownPredicate.instance));
            filter.add(new OwnerIdPredicate(player.getId()));
            TargetCard targetExiled = new TargetCardInExile(0, 1, filter);
            controller.choose(outcome, targetExiled, source, game);
            Set<Card> chosenExiledCard = targetExiled
                    .getTargets()
                    .stream()
                    .map(game::getCard)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            if (!chosenExiledCard.isEmpty()) {
                player.moveCards(chosenExiledCard, Zone.GRAVEYARD, source, game);
            }
        }
        return true;
    }

    @Override
    public BindingNegotiationEffect copy() {
        return new BindingNegotiationEffect(this);
    }

}
