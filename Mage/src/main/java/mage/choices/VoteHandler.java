package mage.choices;

import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.VoteEvent;
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
            VoteEvent event = new VoteEvent(playerId, source);
            game.replaceEvent(event);
            Player player = game.getPlayer(event.getTargetId());
            Player decidingPlayer = game.getPlayer(event.getPlayerId());
            if (player == null || decidingPlayer == null) {
                continue;
            }
            int voteCount = event.getExtraVotes() + event.getOptionalExtraVotes() + 1;
            for (int i = 0; i < voteCount; i++) {
                if (i > event.getExtraVotes() && !player.chooseUse(
                        Outcome.Neutral, "Use an extra vote?", source, game
                )) {
                    continue;
                }
                T vote = playerChoose(player, decidingPlayer, source, game); // TODO: add vote step info as sub message
                if (vote == null) {
                    continue;
                }
                String message = player.getName() + " voted for " + voteName(vote);
                if (!Objects.equals(player, decidingPlayer)) {
                    message += " (chosen by " + decidingPlayer.getName() + ')';
                }
                game.informPlayers(message);
                playerMap.computeIfAbsent(playerId, x -> new ArrayList<>()).add(vote);
            }
        }

        // show final results to players

        Map<T, Integer> totalVotes = new LinkedHashMap<>();
        // fill by possible choices
        this.getPossibleVotes(source, game).forEach(vote -> {
            totalVotes.putIfAbsent(vote, 0);
        });
        // fill by real choices
        playerMap.entrySet()
                .stream()
                .flatMap(votesList -> votesList.getValue().stream())
                .forEach(vote -> {
                    totalVotes.compute(vote, (u, i) -> i == null ? 1 : Integer.sum(i, 1));
                });

        Set<T> winners = this.getMostVoted();
        String totalVotesStr = totalVotes.entrySet()
                .stream()
                .map(entry -> (winners.contains(entry.getKey()) ? " -win- " : " -lose- ") + voteName(entry.getKey()) + ": " + entry.getValue())
                .sorted()
                .collect(Collectors.joining("<br>"));
        game.informPlayers("Vote results:<br>" + totalVotesStr);

        game.fireEvent(new VotedEvent(source, this));
    }

    /**
     * Return possible votes. Uses for info only (final results).
     *
     * @param source
     * @param game
     * @return
     */
    protected abstract Set<T> getPossibleVotes(Ability source, Game game);

    /**
     * Choose dialog for voting. Another player can choose it (example: Illusion of Choice)
     *
     * @param player
     * @param decidingPlayer
     * @param source
     * @param game
     * @return
     */
    protected abstract T playerChoose(Player player, Player decidingPlayer, Ability source, Game game);

    /**
     * Show readable choice name
     *
     * @param vote
     * @return
     */
    protected abstract String voteName(T vote);

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
                .forEach(t -> map.compute(t, (s, i) -> i == null ? 1 : Integer.sum(i, 1)));
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
