package mage.game.events;

import mage.abilities.Ability;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class VoteEvent extends GameEvent {

    private int extraVotes = 0;
    private int optionalVotes = 0;

    public VoteEvent(UUID playerId, Ability source) {
        super(EventType.VOTE, playerId, source, playerId);
    }

    public void incrementExtraVotes() {
        extraVotes++;
    }

    public void incrementOptionalVotes() {
        optionalVotes++;
    }

    public int getExtraVotes() {
        return extraVotes;
    }

    public int getOptionalVotes() {
        return optionalVotes;
    }
}
