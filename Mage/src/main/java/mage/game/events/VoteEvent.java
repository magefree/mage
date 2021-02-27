package mage.game.events;

import mage.abilities.Ability;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class VoteEvent extends GameEvent {

    private int extraVotes = 0; // example: you get an additional vote
    private int optionalExtraVotes = 0; // example: you may vote an additional time

    public VoteEvent(UUID playerId, Ability source) {
        super(EventType.VOTE, playerId, source, playerId);
    }

    public void incrementExtraVotes() {
        extraVotes++;
    }

    public void incrementOptionalExtraVotes() {
        optionalExtraVotes++;
    }

    public int getExtraVotes() {
        return extraVotes;
    }

    public int getOptionalExtraVotes() {
        return optionalExtraVotes;
    }
}
