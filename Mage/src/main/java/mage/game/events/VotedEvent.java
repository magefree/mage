package mage.game.events;

import mage.abilities.Ability;
import mage.choices.VoteHandler;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class VotedEvent extends GameEvent {

    private final VoteHandler voteHandler;

    public VotedEvent(Ability source, VoteHandler voteHandler) {
        super(EventType.VOTED, source.getSourceId(), source, source.getControllerId());
        this.voteHandler = voteHandler;
    }

    public Set<UUID> getDidntVote(UUID playerId) {
        return voteHandler.getDidntVote(playerId);
    }
}
