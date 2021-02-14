package mage.choices;

import mage.abilities.Ability;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.VotedEvent;
import mage.players.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public abstract class VoteHandler<T> {

    protected final Map<UUID, List<T>> playerMap = new HashMap<>();

    public void doVotes(Ability source, Game game) {
        playerMap.clear();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            GameEvent event = GameEvent.getEvent(GameEvent.EventType.VOTE, playerId, source, playerId, 1);
            game.replaceEvent(event);
            Player player = game.getPlayer(event.getTargetId());
            Player decidingPlayer = game.getPlayer(event.getPlayerId());
            if (player == null || decidingPlayer == null) {
                continue;
            }
            int voteCount = event.getAmount();
            for (int i = 0; i < voteCount; i++) {
                T vote = playerChoose(player, decidingPlayer, source, game);
                if (vote == null) {
                    continue;
                }
                playerMap.computeIfAbsent(playerId, x -> new ArrayList<>()).add(vote);
            }
        }
        game.fireEvent(new VotedEvent(source, this));
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

    public Set<T> getMostVoted() {
        Map<T, Integer> map = new HashMap<>();
        playerMap
                .values()
                .stream()
                .flatMap(Collection::stream)
                .map(t -> map.compute(t, (s, i) -> i == null ? 1 : Integer.sum(i, 1)));
        int max = map.values().stream().mapToInt(x -> x).max().orElse(0);
        return map
                .entrySet()
                .stream()
                .filter(e -> e.getValue() >= max)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public Set<UUID> getDidntVote(UUID playerId) {
        if (playerMap.computeIfAbsent(playerId, x -> new ArrayList<>()).isEmpty()) {
            return playerMap.keySet();
        }
        return playerMap
                .entrySet()
                .stream()
                .filter(e -> e.getValue() != null && !e.getValue().isEmpty())
                .filter(e -> !e.getValue().stream().allMatch(playerMap.get(playerId)::contains))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}
