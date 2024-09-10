package mage.game.turn;

import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CombatDamageStep extends Step {

    private final boolean first;

    /**
     * @param first if true, then it is the FirstCombatDamageStep
     */
    public CombatDamageStep(boolean first) {
        super(first ? PhaseStep.FIRST_COMBAT_DAMAGE : PhaseStep.COMBAT_DAMAGE, true);
        this.stepEvent = EventType.COMBAT_DAMAGE_STEP;
        this.preStepEvent = EventType.COMBAT_DAMAGE_STEP_PRE;
        this.postStepEvent = EventType.COMBAT_DAMAGE_STEP_POST;
        this.first = first;
    }

    protected CombatDamageStep(final CombatDamageStep step) {
        super(step);
        this.first = step.first;
    }

    @Override
    public void priority(Game game, UUID activePlayerId, boolean resuming) {
        game.fireEvent(new GameEvent(GameEvent.EventType.COMBAT_DAMAGE_STEP_PRIORITY, null, null, activePlayerId));
        super.priority(game, activePlayerId, resuming);
    }

    @Override
    public boolean skipStep(Game game, UUID activePlayerId) {
        // 508.8
        if (game.getCombat().noAttackers()) {
            return true;
        }
        // 510.4
        if (first && !game.getCombat().hasFirstOrDoubleStrike(game)) {
            return true;
        }
        return super.skipStep(game, activePlayerId);
    }

    @Override
    public void beginStep(Game game, UUID activePlayerId) {
        super.beginStep(game, activePlayerId);
        for (CombatGroup group : game.getCombat().getGroups()) {
            group.assignDamageToBlockers(first, game);
        }
        for (CombatGroup group : game.getCombat().getBlockingGroups()) {
            group.assignDamageToAttackers(first, game);
        }
        for (CombatGroup group : game.getCombat().getGroups()) {
            group.applyDamage(game);
        }
        for (CombatGroup group : game.getCombat().getBlockingGroups()) {
            group.applyDamage(game);
        }

        // Even if no damage was dealt, some watchers need a reset. For instance PhantomPreventionWatcher.
        game.getState().addBatchDamageCouldHaveBeenFired(true, game);
        // Must fire damage batch events now, before SBA (https://github.com/magefree/mage/issues/9129)
        game.getState().handleSimultaneousEvent(game);
    }

    @Override
    public CombatDamageStep copy() {
        return new CombatDamageStep(this);
    }

}
