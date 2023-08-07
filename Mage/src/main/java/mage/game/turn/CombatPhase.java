

package mage.game.turn;

import mage.constants.TurnPhase;
import mage.game.events.GameEvent.EventType;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CombatPhase extends Phase {

    public CombatPhase() {
        this.type = TurnPhase.COMBAT;
        this.event = EventType.COMBAT_PHASE;
        this.preEvent = EventType.COMBAT_PHASE_PRE;
        this.postEvent = EventType.COMBAT_PHASE_POST;
        this.steps.add(new BeginCombatStep());
        this.steps.add(new DeclareAttackersStep());
        this.steps.add(new DeclareBlockersStep());
        this.steps.add(new FirstCombatDamageStep());
        this.steps.add(new CombatDamageStep());
        this.steps.add(new EndOfCombatStep());
    }

    protected CombatPhase(final CombatPhase phase) {
        super(phase);
    }

    @Override
    public CombatPhase copy() {
        return new CombatPhase(this);
    }

}
