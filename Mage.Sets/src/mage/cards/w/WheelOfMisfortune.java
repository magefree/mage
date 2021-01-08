package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WheelOfMisfortune extends CardImpl {

    public WheelOfMisfortune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Each player secretly chooses a number 0 or greater, then all players reveal those numbers simultaneously and determine the highest and lowest numbers revealed this way. Wheel of Misfortune deals damage equal to the highest number to each player who chose that number. Each player who didn't choose the lowest number discards their hand, then draws seven cards.
        this.getSpellAbility().addEffect(new WheelOfMisfortuneEffect());
    }

    private WheelOfMisfortune(final WheelOfMisfortune card) {
        super(card);
    }

    @Override
    public WheelOfMisfortune copy() {
        return new WheelOfMisfortune(this);
    }
}

class WheelOfMisfortuneEffect extends OneShotEffect {

    WheelOfMisfortuneEffect() {
        super(Outcome.Benefit);
        staticText = "Each player secretly chooses a number 0 or greater, " +
                "then all players reveal those numbers simultaneously " +
                "and determine the highest and lowest numbers revealed this way. " +
                "{this} deals damage equal to the highest number to each player who chose that number. " +
                "Each player who didn't choose the lowest number discards their hand, then draws seven cards.";
    }

    private WheelOfMisfortuneEffect(final WheelOfMisfortuneEffect effect) {
        super(effect);
    }

    @Override
    public WheelOfMisfortuneEffect copy() {
        return new WheelOfMisfortuneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Integer> playerMap = new HashMap<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            playerMap.put(playerId, player.getAmount(0, 1000, "Choose a number", game));
        }
        for (Map.Entry<UUID, Integer> entry : playerMap.entrySet()) {
            Player player = game.getPlayer(entry.getKey());
            if (player == null) {
                continue;
            }
            game.informPlayers(player.getLogName() + " chose " + entry.getValue());
        }
        int maxValue = playerMap.values().stream().mapToInt(x -> x).max().orElse(0);
        game.informPlayers("The highest number chosen was " + maxValue);
        int minValue = playerMap.values().stream().mapToInt(x -> x).min().orElse(0);
        game.informPlayers("The lowest number chosen was " + minValue);
        for (Map.Entry<UUID, Integer> entry : playerMap.entrySet()) {
            Player player = game.getPlayer(entry.getKey());
            if (player == null) {
                continue;
            }
            if (entry.getValue() == maxValue) {
                player.damage(maxValue, source.getSourceId(), source, game);
            }
            if (entry.getValue() != minValue) {
                player.discard(player.getHand(), false, source, game);
                player.drawCards(7, source, game);
            }
        }
        return true;
    }
}
