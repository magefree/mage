package mage.choices;

import mage.abilities.Ability;
import mage.game.Game;
import mage.players.Player;

import java.util.*;

/**
 * @author TheElk801
 */
public abstract class VoteHandler<T> {

    protected final Map<UUID, List<T>> playerMap = new HashMap<>();

    public void doVotes(Ability source, Game game) {
        playerMap.clear();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            T vote = playerChoose(player, player, source, game);
            if (vote == null) {
                continue;
            }
            playerMap.computeIfAbsent(playerId, x -> new ArrayList<>()).add(vote);
        }
    }

    public abstract T playerChoose(Player player, Player decidingPlayer, Ability source, Game game);

    public List<T> getVotes(UUID playerId) {
        return playerMap.computeIfAbsent(playerId, x -> new ArrayList<>());
    }

    public int getVoteCount(T vote) {
        return playerMap
                .values()
                .stream()
                .flatMap(Collection::stream)
                .map(vote::equals)
                .mapToInt(x -> x ? 1 : 0)
                .sum();
    }

    public List<UUID> getVotedFor(T vote) {
        return playerMap
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() != null && entry.getValue().contains(vote))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
