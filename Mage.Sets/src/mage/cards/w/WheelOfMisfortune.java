package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.RandomUtil;

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
            // AI hint
            int number = player.isComputer()
                    ? chooseNumberAI(player, source, game)
                    : player.getAmount(0, 1000, "Choose a number", source, game);
            playerMap.put(playerId, number);
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

    /**
     * Damage only hits whoever chose the highest number, and 0 is always among the lowest, so a big number is all
     * downside: 0 keeps the hand at no risk, and a small number buys a new one. 2 is the usual pick for that: if
     * nobody chooses 0, a table of 1s all tie for lowest and nobody gets a new hand, and 2 clears them at a cost of
     * at most 2 damage.
     */
    static int chooseNumberAI(Player player, Ability source, Game game) {
        if (!wantsNewHand(player, source, game)) {
            return 0;
        }
        // 1, 2, 2, 3: mostly 2, but not always, so the pick can't be read
        int roll = RandomUtil.nextInt(4);
        int number = roll == 0 ? 1 : roll == 3 ? 3 : 2;
        // never the damage that kills it; at 1 life that is 0, and the hand stays
        return Math.max(0, Math.min(number, player.getLife() - 1));
    }

    private static boolean wantsNewHand(Player player, Ability source, Game game) {
        // seven cards from a thin library can lose the game
        if (player.getLibrary().size() < 10) {
            return false;
        }
        // assume the caster cast it to refill its hand (a wheel effect)
        if (player.getId().equals(source.getControllerId())) {
            return true;
        }
        if (player.getHand().size() <= 2) {
            return true;
        }
        // a hand with at most one spell it could cast by next turn is worth trading for seven
        int lands = game.getBattlefield().countAll(StaticFilters.FILTER_LAND, player.getId(), game);
        long castable = player.getHand().getCards(game).stream()
                .filter(card -> !card.isLand(game) && card.getManaValue() <= lands + 1)
                .count();
        return castable <= 1;
    }
}
