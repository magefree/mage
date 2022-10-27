package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class ChaosMutation extends CardImpl {

    public ChaosMutation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{R}");

        // Exile any number of target creatures controlled by different players. For each creature exiled this way, its controller reveals cards from the top of their library until they reveal a creature card, puts that card onto the battlefield, then puts the rest on the bottom of their library in a random order.
        this.getSpellAbility().addEffect(new ChaosMutationEffect());
        this.getSpellAbility().addTarget(new ChaosMutationTarget());
    }

    private ChaosMutation(final ChaosMutation card) {
        super(card);
    }

    @Override
    public ChaosMutation copy() {
        return new ChaosMutation(this);
    }
}

class ChaosMutationEffect extends OneShotEffect {

    ChaosMutationEffect() {
        super(Outcome.Benefit);
        staticText = "exile any number of target creatures controlled by different players. " +
                "For each creature exiled this way, its controller reveals cards from the top of their library " +
                "until they reveal a creature card, puts that card onto the battlefield, " +
                "then puts the rest on the bottom of their library in a random order";
    }

    private ChaosMutationEffect(final ChaosMutationEffect effect) {
        super(effect);
    }

    @Override
    public ChaosMutationEffect copy() {
        return new ChaosMutationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Set<Permanent> permanents = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (controller == null || permanents.isEmpty()) {
            return false;
        }
        List<Player> players = permanents
                .stream()
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        controller.moveCards(permanents, Zone.EXILED, source, game);
        for (Player player : players) {
            Cards cards = new CardsImpl();
            Card card = this.getCreatureCard(player, cards, game);
            player.revealCards(source, cards, game);
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
            cards.retainZone(Zone.LIBRARY, game);
            player.putCardsOnBottomOfLibrary(cards, game, source, false);
        }
        return true;
    }

    private static Card getCreatureCard(Player player, Cards cards, Game game) {
        for (Card card : player.getLibrary().getCards(game)) {
            cards.add(card);
            if (card.isCreature(game)) {
                return card;
            }
        }
        return null;
    }
}

class ChaosMutationTarget extends TargetPermanent {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creatures controlled by different players");

    ChaosMutationTarget() {
        super(0, Integer.MAX_VALUE, filter, false);
    }

    private ChaosMutationTarget(final ChaosMutationTarget target) {
        super(target);
    }

    @Override
    public ChaosMutationTarget copy() {
        return new ChaosMutationTarget(this);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(controllerId, id, source, game)) {
            return false;
        }
        Permanent creature = game.getPermanent(id);
        if (creature == null) {
            return false;
        }
        return this.getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .noneMatch(permanent -> !creature.getId().equals(permanent.getId())
                        && creature.isControlledBy(permanent.getControllerId())
                );
    }
}
