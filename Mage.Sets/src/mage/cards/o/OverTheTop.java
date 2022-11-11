package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.players.Player;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class OverTheTop extends CardImpl {

    public OverTheTop(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}{R}");

        // Each player reveals a number of cards from the top of their library equal to the number of nonland permanents they control, puts all permanent cards they revealed this way onto the battlefield, and puts the rest into their graveyard.
        this.getSpellAbility().addEffect(new OverTheTopEffect());
    }

    private OverTheTop(final OverTheTop card) {
        super(card);
    }

    @Override
    public OverTheTop copy() {
        return new OverTheTop(this);
    }
}

class OverTheTopEffect extends OneShotEffect {

    OverTheTopEffect() {
        super(Outcome.Benefit);
        staticText = "each player reveals a number of cards from the top of their library equal to the number of " +
                "nonland permanents they control, puts all permanent cards they revealed this way " +
                "onto the battlefield, and puts the rest into their graveyard";
    }

    private OverTheTopEffect(final OverTheTopEffect effect) {
        super(effect);
    }

    @Override
    public OverTheTopEffect copy() {
        return new OverTheTopEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Integer> map = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_PERMANENT_NON_LAND,
                        source.getControllerId(), source, game
                )
                .stream()
                .collect(Collectors.toMap(Controllable::getControllerId, x -> 1, Integer::sum));
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            int nonlands = map.getOrDefault(playerId, 0);
            if (player == null || nonlands < 1) {
                continue;
            }
            Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, nonlands));
            player.revealCards(source, cards, game);
            Cards toGrave = cards.copy();
            cards.removeIf(uuid -> !game.getCard(uuid).isPermanent(game));
            player.moveCards(cards, Zone.BATTLEFIELD, source, game);
            toGrave.retainZone(Zone.LIBRARY, game);
            player.moveCards(toGrave, Zone.GRAVEYARD, source, game);
        }
        return true;
    }
}
