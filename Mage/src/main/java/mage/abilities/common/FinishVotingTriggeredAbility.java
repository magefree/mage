package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.VotedEvent;

/**
 * @author TheElk801
 */
public class FinishVotingTriggeredAbility extends TriggeredAbilityImpl {

    public FinishVotingTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        this.setTriggerPhrase("Whenever players finish voting, ");
    }

    private FinishVotingTriggeredAbility(final FinishVotingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FinishVotingTriggeredAbility copy() {
        return new FinishVotingTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.VOTED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        this.getEffects().setValue("votedAgainst", ((VotedEvent) event).getDidntVote(getControllerId()));
        return true;
    }
}
