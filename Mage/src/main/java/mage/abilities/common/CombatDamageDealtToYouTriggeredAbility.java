package mage.abilities.common;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedBatchForOnePlayerEvent;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 * A triggered ability for whenever one or more creatures deal combat damage to
 * you. Has an optional component for setting the target pointer to the opponent
 * whose creatures dealt combat damage to you.
 *
 * @author alexander-novo
 */
public class CombatDamageDealtToYouTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPlayerEvent>  {

    // Whether the ability should set a target targeting the opponent who
    // controls the creatures who dealt damage to you
    private final boolean setTargetPointer;

    public CombatDamageDealtToYouTriggeredAbility(Effect effect) {
        this(Zone.BATTLEFIELD, effect, false, false);
    }

    public CombatDamageDealtToYouTriggeredAbility(Zone zone, Effect effect, boolean setTargetPointer, boolean optional) {
        super(zone, effect, optional);
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase(generateTriggerPhrase());
    }

    private CombatDamageDealtToYouTriggeredAbility(final CombatDamageDealtToYouTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getTargetId()) || !((DamagedBatchForOnePlayerEvent) event).isCombatDamage()) {
            return false;
        }
        if (setTargetPointer) {
            // attacking player is active player
            this.getEffects().setTargetPointer(new FixedTarget(game.getActivePlayerId()));
        }
        return true;

    }

    private String generateTriggerPhrase() {
        if (setTargetPointer) {
            return "Whenever one or more creatures an opponent controls deal combat damage to you, ";
        } else {
            return "Whenever one or more creatures deal combat damage to you, ";
        }
    }

    @Override
    public CombatDamageDealtToYouTriggeredAbility copy() {
        return new CombatDamageDealtToYouTriggeredAbility(this);
    }
}
