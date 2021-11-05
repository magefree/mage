package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author L_J
 */
public final class GoblinGame extends CardImpl {

    public GoblinGame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}{R}");

        // Each player hides at least one item, then all players reveal them simultaneously. Each player loses life equal to the number of items they revealed. The player who revealed the fewest items then loses half their life, rounded up. If two or more players are tied for fewest, each loses half their life, rounded up.
        // Reinterpreted as: Each player secretly chooses a number greater than 0. Then those numbers are revealed. Each player loses life equal to their chosen number. The player who revealed the lowest number then loses half their life, rounded up. If two or more players are tied for lowest, each loses half their life, rounded up.
        this.getSpellAbility().addEffect(new GoblinGameEffect());
    }

    private GoblinGame(final GoblinGame card) {
        super(card);
    }

    @Override
    public GoblinGame copy() {
        return new GoblinGame(this);
    }
}

class GoblinGameEffect extends OneShotEffect {

    public GoblinGameEffect() {
        super(Outcome.Detriment);
        this.staticText = "Each player hides at least one item, then all players reveal them simultaneously. " +
                "Each player loses life equal to the number of items they revealed. " +
                "The player who revealed the fewest items then loses half their life, rounded up. " +
                "If two or more players are tied for fewest, each loses half their life, rounded up.";
    }

    public GoblinGameEffect(final GoblinGameEffect effect) {
        super(effect);
    }

    @Override
    public GoblinGameEffect copy() {
        return new GoblinGameEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Integer> numberChosen = new HashMap<>();

        // players choose numbers
        List<Player> players = game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        for (Player player : players) {
            // TODO: consider changing 1000 to another cap, or even Integer.MAX_VALUE if the Volcano Hellion binary wraparound gets addressed (although hiding over two billions of items would be rather difficult IRL)
            numberChosen.put(player.getId(), player.getAmount(1, 1000, "Choose a number of objects to hide.", game));
        }

        // get lowest number
        int lowestNumber = numberChosen
                .values()
                .stream()
                .mapToInt(x -> x)
                .min()
                .orElse(0);

        // reveal numbers to players and follow through with effects
        for (Player player : players) {
            game.informPlayers(player.getLogName() + " chose " + numberChosen.get(player.getId()));
            player.loseLife(numberChosen.get(player.getId()), game, source, false);
        }
        for (Player player : players) {
            if (numberChosen.get(player.getId()) > lowestNumber) {
                continue;
            }
            game.informPlayers(player.getLogName() + " chose the lowest number");
            Integer amount = (int) Math.ceil(player.getLife() / 2f);
            if (amount > 0) {
                player.loseLife(amount, game, source, false);
            }
        }
        return true;
    }
}
