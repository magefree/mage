package mage.choices;

import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public class TwoChoiceVote implements VoteHandler<Boolean> {

    private final String choice1;
    private final String choice2;
    private final Map<UUID, List<Boolean>> playerMap = new HashMap<>();

    public TwoChoiceVote(String choice1, String choice2) {
        this.choice1 = choice1;
        this.choice2 = choice2;
    }

    @Override
    public void doVotes(Ability source, Game game) {
        playerMap.clear();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            boolean vote = player.chooseUse(Outcome.Neutral, "Vote", null, choice1, choice2, source, game);
            game.informPlayers(player.getName() + " voted for " + (vote ? choice1 : choice2));
            playerMap.computeIfAbsent(playerId, x -> new ArrayList<>()).add(vote);
        }
    }

    @Override
    public List<Boolean> getVotes(UUID playerId) {
        return playerMap.computeIfAbsent(playerId, x -> new ArrayList<>());
    }

    @Override
    public int getVoteCount(Boolean vote) {
        return playerMap
                .values()
                .stream()
                .flatMap(Collection::stream)
                .filter(vote::equals)
                .mapToInt(x -> x ? 1 : 0)
                .sum();
    }

    @Override
    public List<UUID> getVotedFor(Boolean vote) {
        return playerMap
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() != null && entry.getValue().contains(vote))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
