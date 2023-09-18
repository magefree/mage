package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CollisionOfRealms extends CardImpl {

    public CollisionOfRealms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{R}");

        // Each player shuffles all creatures they own into their library. Each player who shuffled a nontoken creature into their library this way reveals cards from the top of their library until they reveal a creature card, then puts that card onto the battlefield and the rest on the bottom of their library in a random order.
        this.getSpellAbility().addEffect(new CollisionOfRealmsEffect());
    }

    private CollisionOfRealms(final CollisionOfRealms card) {
        super(card);
    }

    @Override
    public CollisionOfRealms copy() {
        return new CollisionOfRealms(this);
    }
}

class CollisionOfRealmsEffect extends OneShotEffect {

    CollisionOfRealmsEffect() {
        super(Outcome.Benefit);
        staticText = "each player shuffles all creatures they own into their library. " +
                "Each player who shuffled a nontoken creature into their library this way reveals cards " +
                "from the top of their library until they reveal a creature card, then puts that card " +
                "onto the battlefield and the rest on the bottom of their library in a random order";
    }

    private CollisionOfRealmsEffect(final CollisionOfRealmsEffect effect) {
        super(effect);
    }

    @Override
    public CollisionOfRealmsEffect copy() {
        return new CollisionOfRealmsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Player> players = new ArrayList<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            List<Permanent> permanents = game.getBattlefield().getActivePermanents(
                    StaticFilters.FILTER_CONTROLLED_CREATURE, playerId, game
            );
            boolean hasNonToken = permanents
                    .stream()
                    .filter(permanent -> !(permanent instanceof PermanentToken))
                    .anyMatch(permanent -> permanent.isCreature(game));
            player.putCardsOnBottomOfLibrary(new CardsImpl(permanents), game, source, false);
            player.shuffleLibrary(source, game);
            if (hasNonToken) {
                players.add(player);
            }
        }
        for (Player player : players) {
            Cards cards = new CardsImpl();
            Card creature = revealUntilCreature(cards, player, game);
            player.revealCards(source, cards, game);
            if (creature != null) {
                player.moveCards(creature, Zone.BATTLEFIELD, source, game);
            }
            cards.retainZone(Zone.LIBRARY, game);
            player.putCardsOnBottomOfLibrary(cards, game, source, false);
        }
        return true;
    }

    private static Card revealUntilCreature(Cards cards, Player player, Game game) {
        for (Card card : player.getLibrary().getCards(game)) {
            cards.add(card);
            if (card.isCreature(game)) {
                return card;
            }
        }
        return null;
    }
}
