package mage.choices;

import mage.abilities.Ability;
import mage.game.Game;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public interface VoteHandler<T> {

    public void doVotes(Ability source, Game game);

    public List<T> getVotes(UUID playerId);

    public int getVoteCount(T vote);

    public List<UUID> getVotedFor(T vote);
}
