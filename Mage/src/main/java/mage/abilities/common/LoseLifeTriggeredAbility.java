package mage.abilities.common;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.SavedLifeLossValue;
import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.LifeLostEvent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public class LoseLifeTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<LifeLostEvent> {

    protected final TargetController targetController;
    private final boolean setTargetPointer;

    public LoseLifeTriggeredAbility(Effect effect) {
        this(effect, TargetController.YOU, false, false);
    }

    public LoseLifeTriggeredAbility(Effect effect, TargetController targetController, boolean optional, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.targetController = targetController;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase(generateTriggerPhrase());
    }

    protected LoseLifeTriggeredAbility(final LoseLifeTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public LoseLifeTriggeredAbility copy() {
        return new LoseLifeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_LIFE_BATCH_FOR_ONE_PLAYER;
    }

    private boolean filterPlayer(UUID playerId, Game game) {
        switch (targetController) {
            case YOU:
                return isControlledBy(playerId);
            case OPPONENT:
                return game.getOpponents(getControllerId()).contains(playerId);
            default:
                throw new IllegalArgumentException("Wrong code usage: not supported targetController: " + targetController);
        }
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!filterPlayer(event.getTargetId(), game)) {
            return false;
        }
        // if target id matches, all events in the batch are relevant
        this.getEffects().setValue(SavedLifeLossValue.getValueKey(), event.getAmount());
        if (setTargetPointer) {
            this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
        }
        return true;
    }

    protected String generateTriggerPhrase() {
        switch (targetController) {
            case YOU:
                return "Whenever you lose life, ";
            case OPPONENT:
                return "Whenever an opponent loses life, ";
            default:
                throw new IllegalArgumentException("Wrong code usage: not supported targetController: " + targetController);
        }
    }
}
