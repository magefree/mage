package mage.abilities.triggers;

import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 * @author TheElk801
 */
public class BeginningOfSecondMainTriggeredAbility extends AtStepTriggeredAbility {

    /**
     * At the beginning of your second main phase
     */
    public BeginningOfSecondMainTriggeredAbility(Effect effect, boolean optional) {
        this(Zone.BATTLEFIELD, TargetController.YOU, effect, optional);
    }

    public BeginningOfSecondMainTriggeredAbility(Zone zone, TargetController targetController, Effect effect, boolean optional) {
        super(zone, targetController, effect, optional);
        setTriggerPhrase(generateTriggerPhrase());
        this.addWatcher(new MainPhaseWatcher());
    }

    protected BeginningOfSecondMainTriggeredAbility(final BeginningOfSecondMainTriggeredAbility ability) {
        super(ability);
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
            default:
                return false;
        }
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return MainPhaseWatcher.checkCount(game) && super.checkTrigger(event, game);
    }

    @Override
    protected String generateTriggerPhrase() {
        switch (targetController) {
            case YOU:
                return "At the beginning of your second main phase, ";
            case OPPONENT:
                return "At the beginning of each opponent's second main phase, ";
            case ANY:
                return "At the beginning of each player's second main phase, ";
            case ENCHANTED:
                return "At the beginning of enchanted player's second main phase, ";
            default:
                throw new UnsupportedOperationException("Unsupported TargetController in BeginningOfSecondMainTriggeredAbility: " + targetController);
        }
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
