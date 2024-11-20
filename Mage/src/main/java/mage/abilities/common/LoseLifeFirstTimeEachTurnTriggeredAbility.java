package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.common.LifeLostThisTurnWatcher;

/**
 * @author Susucr
 */
public class LoseLifeFirstTimeEachTurnTriggeredAbility extends LoseLifeTriggeredAbility {

    public LoseLifeFirstTimeEachTurnTriggeredAbility(Effect effect) {
        this(effect, TargetController.YOU, false, false);
    }

    public LoseLifeFirstTimeEachTurnTriggeredAbility(Effect effect, TargetController targetController, boolean optional, boolean setTargetPointer) {
        super(effect, targetController, optional, setTargetPointer);
        addWatcher(new LifeLostThisTurnWatcher());
    }

    protected LoseLifeFirstTimeEachTurnTriggeredAbility(final LoseLifeFirstTimeEachTurnTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LoseLifeFirstTimeEachTurnTriggeredAbility copy() {
        return new LoseLifeFirstTimeEachTurnTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        LifeLostThisTurnWatcher watcher = game.getState().getWatcher(LifeLostThisTurnWatcher.class);
        return watcher != null
                && watcher.timesLostLifeThisTurn(event.getTargetId()) <= 1
                && super.checkTrigger(event, game);
    }

    @Override
    protected String generateTriggerPhrase() {
        switch (targetController) {
            case YOU:
                return "Whenever you lose life for the first time each turn, ";
            case OPPONENT:
                return "Whenever an opponent loses life for the first time each turn, ";
            default:
                throw new IllegalArgumentException("Wrong code usage: not supported targetController: " + targetController);
        }
    }
}
