

package mage.game.turn;

import mage.constants.TurnPhase;
import mage.game.events.GameEvent.EventType;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class PreCombatMainPhase extends Phase {

    public PreCombatMainPhase() {
        this.type = TurnPhase.PRECOMBAT_MAIN;
        this.event = EventType.PRECOMBAT_MAIN_PHASE;
        this.preEvent = EventType.PRECOMBAT_MAIN_PHASE_PRE;
        this.postEvent = EventType.PRECOMBAT_MAIN_PHASE_POST;
        this.steps.add(new PreCombatMainStep());
    }

    protected PreCombatMainPhase(final PreCombatMainPhase phase) {
        super(phase);
    }

    @Override
    public PreCombatMainPhase copy() {
        return new PreCombatMainPhase(this);
    }

}
