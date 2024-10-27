package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

/**
 * @author TheElk801
 */
public class BeginningOfSecondMainTriggeredAbility extends TriggeredAbilityImpl {

    private final TargetController targetController;
    private final boolean setTargetPointer;

    public BeginningOfSecondMainTriggeredAbility(Effect effect, TargetController targetController, boolean isOptional) {
        this(Zone.BATTLEFIELD, effect, targetController, isOptional, false);
    }

    public BeginningOfSecondMainTriggeredAbility(Zone zone, Effect effect, TargetController targetController, boolean isOptional, boolean setTargetPointer) {
        super(zone, effect, isOptional);
        this.targetController = targetController;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase(generateTriggerPhrase());
        this.addWatcher(new MainPhaseWatcher());
    }

    protected BeginningOfSecondMainTriggeredAbility(final BeginningOfSecondMainTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public BeginningOfSecondMainTriggeredAbility copy() {
        return new BeginningOfSecondMainTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case PRECOMBAT_MAIN_PHASE_PRE:
            case POSTCOMBAT_MAIN_PHASE_PRE:
                return true;
        }
        return false;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!MainPhaseWatcher.checkCount(game)) {
            return false;
        }
        switch (targetController) {
            case YOU:
                if (!isControlledBy(event.getPlayerId())) {
                    return false;
                }
                if (setTargetPointer && getTargets().isEmpty()) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                }
                return true;
            case OPPONENT:
                if (!game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
                    return false;
                }
                if (setTargetPointer) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                }
                return true;
            case ANY:
                if (setTargetPointer) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                }
                return true;
            case ENCHANTED:
                Permanent permanent = getSourcePermanentIfItStillExists(game);
                if (permanent == null || !game.isActivePlayer(permanent.getAttachedTo())) {
                    return false;
                }
                if (getTargets().isEmpty()) {
                    this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return true;
        }
        return false;
    }

    private String generateTriggerPhrase() {
        switch (targetController) {
            case YOU:
                return "At the beginning of your second main phase, ";
            case OPPONENT:
                return "At the beginning of each opponent's second main phase, ";
            case ANY:
                return "At the beginning of each player's second main phase, ";
            case ENCHANTED:
                return "At the beginning of enchanted player's second main phase, ";
        }
        return "";
    }

}

class MainPhaseWatcher extends Watcher {

    private int mainPhaseCount = 0;

    MainPhaseWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case PRECOMBAT_MAIN_PHASE_PRE:
            case POSTCOMBAT_MAIN_PHASE_PRE:
                mainPhaseCount++;
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.mainPhaseCount = 0;
    }

    static boolean checkCount(Game game) {
        return game
                .getState()
                .getWatcher(MainPhaseWatcher.class)
                .mainPhaseCount == 2;
    }
}
