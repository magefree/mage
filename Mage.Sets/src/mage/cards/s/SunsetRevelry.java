package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.token.HumanToken;
import mage.players.Player;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class SunsetRevelry extends CardImpl {

    public SunsetRevelry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // If an opponent has more life than you, you gain 4 life.
        // If an opponent controls more creatures than you, create two 1/1 white Human creature tokens.
        // If an opponent has more cards in hand than you, draw a card.
        // TODO: add hints to this
        this.getSpellAbility().addEffect(new SunsetRevelryEffect());
    }

    private SunsetRevelry(final SunsetRevelry card) {
        super(card);
    }

    @Override
    public SunsetRevelry copy() {
        return new SunsetRevelry(this);
    }
}

class SunsetRevelryEffect extends OneShotEffect {

    SunsetRevelryEffect() {
        super(Outcome.Benefit);
        staticText = "If an opponent has more life than you, you gain 4 life." +
                "<br>If an opponent controls more creatures than you, create two 1/1 white Human creature tokens." +
                "<br>If an opponent has more cards in hand than you, draw a card.";
    }

    private SunsetRevelryEffect(final SunsetRevelryEffect effect) {
        super(effect);
    }

    @Override
    public SunsetRevelryEffect copy() {
        return new SunsetRevelryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        if (opponents.stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .mapToInt(Player::getLife)
                .anyMatch(x -> x > player.getLife())) {
            player.gainLife(4, game, source);
        }
        Map<UUID, Integer> map = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_PERMANENT_CREATURE,
                        source.getControllerId(), game
                ).stream()
                .filter(Objects::nonNull)
                .map(Controllable::getControllerId)
                .filter(uuid -> opponents.contains(uuid) || source.getControllerId().equals(uuid))
                .collect(Collectors.toMap(Function.identity(), x -> 1, Integer::sum));
        if (map.getOrDefault(
                source.getControllerId(), 0
        ) < map.values().stream().mapToInt(x -> x).max().orElse(0)) {
            new HumanToken().putOntoBattlefield(2, game, source, source.getControllerId());
        }
        if (opponents
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getHand)
                .mapToInt(Set::size)
                .anyMatch(x -> x > player.getHand().size())) {
            player.drawCards(1, source, game);
        }
        return true;
    }
}
