package mage.abilities.common;

import mage.MageObjectReference;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedBatchForOnePlayerEvent;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A triggered ability for whenever one or more creatures deal combat damage to
 * you. Has an optional component for setting the target pointer to the opponent
 * whose creatures dealt combat damage to you or for getting the creatures which
 * dealt damage to you.
 *
 * @author alexander-novo
 */
public class CombatDamageDealtToYouTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPlayerEvent>  {

    private final SetTargetPointer setTargetPointer;

    public CombatDamageDealtToYouTriggeredAbility(Effect effect) {
        this(effect, SetTargetPointer.NONE);
    }

    public CombatDamageDealtToYouTriggeredAbility(Effect effect, SetTargetPointer setTargetPointer) {
        this(Zone.BATTLEFIELD, effect, setTargetPointer, false);
    }

    public CombatDamageDealtToYouTriggeredAbility(Zone zone, Effect effect, SetTargetPointer setTargetPointer, boolean optional) {
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
        List<DamagedPlayerEvent> events = getFilteredEvents((DamagedBatchForOnePlayerEvent) event, game);
        switch (setTargetPointer) {
            case PLAYER:
                // attacking player is active player
                this.getEffects().setTargetPointer(new FixedTarget(game.getActivePlayerId()));
                break;
            case PERMANENT:
                Set<MageObjectReference> attackerSet = events
                        .stream()
                        .map(GameEvent::getSourceId)
                        .map(game::getPermanent)
                        .filter(Objects::nonNull)
                        .map(permanent -> new MageObjectReference(permanent, game))
                        .collect(Collectors.toSet());
                this.getAllEffects().setTargetPointer(new FixedTargets(attackerSet));
            case NONE:
                break;
            default:
                throw new IllegalArgumentException("Unsupported SetTargetPointer in CombatDamageDealtToYouTriggeredAbility");
        }
        return true;

    }

    private String generateTriggerPhrase() {
        if (setTargetPointer == SetTargetPointer.PLAYER) {
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
