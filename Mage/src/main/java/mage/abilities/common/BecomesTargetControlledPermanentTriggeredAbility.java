package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;

import java.util.*;

/**
 * @author weirddan455
 */
public class BecomesTargetControlledPermanentTriggeredAbility extends TriggeredAbilityImpl {

    public BecomesTargetControlledPermanentTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever a permanent you control becomes the target of a spell or ability an opponent controls, ");
    }

    private BecomesTargetControlledPermanentTriggeredAbility(final BecomesTargetControlledPermanentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BecomesTargetControlledPermanentTriggeredAbility copy() {
        return new BecomesTargetControlledPermanentTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        StackObject sourceObject = game.getStack().getStackObject(event.getSourceId());
        if (sourceObject == null) {
            return false;
        }
        Player targetter = game.getPlayer(event.getPlayerId());
        if (targetter == null || !targetter.hasOpponent(this.controllerId, game)) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null || !permanent.isControlledBy(this.getControllerId())) {
            return false;
        }
        // If a spell or ability an opponent controls targets a single permanent you control more than once,
        // Battle Mammoth’s triggered ability will trigger only once.
        // However, if a spell or ability an opponent controls targets multiple permanents you control,
        // Battle Mammoth’s triggered ability will trigger once for each of those permanents.

        // targetMap - key - targetId, value - Set of stackObject Ids
        Map<UUID, Set<UUID>> targetMap = (Map<UUID, Set<UUID>>) game.getState().getValue("targetMap" + this.id);
        if (targetMap == null) {
            targetMap = new HashMap<>();
        }
        Set<UUID> sourceObjects = targetMap.get(event.getTargetId());
        if (sourceObjects == null) {
            sourceObjects = new HashSet<>();
        }
        if (!sourceObjects.add(sourceObject.getId())) {
            return false;
        }
        targetMap.put(event.getTargetId(), sourceObjects);
        game.getState().setValue("targetMap" + this.id, targetMap);
        return true;
    }
}
