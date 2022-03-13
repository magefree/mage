package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FractalToken;
import mage.game.permanent.token.Token;
import mage.players.Player;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class Oversimplify extends CardImpl {

    public Oversimplify(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{U}");

        // Exile all creatures. Each player creates a 0/0 green and blue Fractal creature token and puts a number of +1/+1 counters on it equal to the total power of creatures they controlled that were exiled this way.
        this.getSpellAbility().addEffect(new OversimplifyEffect());
    }

    private Oversimplify(final Oversimplify card) {
        super(card);
    }

    @Override
    public Oversimplify copy() {
        return new Oversimplify(this);
    }
}

class OversimplifyEffect extends OneShotEffect {

    OversimplifyEffect() {
        super(Outcome.Benefit);
        staticText = "exile all creatures. Each player creates a 0/0 green and blue Fractal creature token " +
                "and puts a number of +1/+1 counters on it equal to " +
                "the total power of creatures they controlled that were exiled this way";
    }

    private OversimplifyEffect(final OversimplifyEffect effect) {
        super(effect);
    }

    @Override
    public OversimplifyEffect copy() {
        return new OversimplifyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE,
                source.getControllerId(), source, game
        );
        Map<UUID, Integer> playerMap = permanents
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        Controllable::getControllerId,
                        p -> p.getPower().getValue(),
                        Integer::sum
                ));
        controller.moveCards(new CardsImpl(permanents), Zone.EXILED, source, game);
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Token token = new FractalToken();
            token.putOntoBattlefield(1, game, source, playerId);
            int counter = playerMap.getOrDefault(playerId, 0);
            for (UUID tokenId : token.getLastAddedTokenIds()) {
                Permanent permanent = game.getPermanent(tokenId);
                if (permanent != null) {
                    permanent.addCounters(CounterType.P1P1.createInstance(counter), playerId, source, game);
                }
            }
        }
        return true;
    }
}
