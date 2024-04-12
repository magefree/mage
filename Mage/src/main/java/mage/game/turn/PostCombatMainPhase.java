

package mage.game.turn;

import mage.constants.TurnPhase;
import mage.game.events.GameEvent.EventType;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class PostCombatMainPhase extends Phase {

    public PostCombatMainPhase() {
        this.type = TurnPhase.POSTCOMBAT_MAIN;
        this.event = EventType.POSTCOMBAT_MAIN_PHASE;
        this.preEvent = EventType.POSTCOMBAT_MAIN_PHASE_PRE;
        this.postEvent = EventType.POSTCOMBAT_MAIN_STEP_POST;
        this.steps.add(new PostCombatMainStep());
    }

    protected PostCombatMainPhase(final PostCombatMainPhase phase) {
        super(phase);
    }

    @Override
    public PostCombatMainPhase copy() {
        return new PostCombatMainPhase(this);
    }

}
