

package mage.abilities;

import mage.MageObject;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author BetaSteward_at_googlemail.com
 * <p>
 * This class uses ConcurrentHashMap to avoid ConcurrentModificationExceptions.
 * See ticket https://github.com/magefree/mage/issues/966 and
 * https://github.com/magefree/mage/issues/473
 */
public class TriggeredAbilities extends ConcurrentHashMap<String, TriggeredAbility> {

    private static final Logger logger = Logger.getLogger(TriggeredAbilities.class);

    private final Map<String, List<UUID>> sources = new HashMap<>();

    public TriggeredAbilities() {
    }

    public TriggeredAbilities(final TriggeredAbilities abilities) {
        for (Map.Entry<String, TriggeredAbility> entry : abilities.entrySet()) {
            this.put(entry.getKey(), entry.getValue().copy());
        }
        for (Map.Entry<String, List<UUID>> entry : abilities.sources.entrySet()) {
            sources.put(entry.getKey(), entry.getValue());
        }
    }

    public void checkStateTriggers(Game game) {
        for (Iterator<TriggeredAbility> it = this.values().iterator(); it.hasNext(); ) {
            TriggeredAbility ability = it.next();
            if (ability instanceof StateTriggeredAbility && ((StateTriggeredAbility) ability).canTrigger(game)) {
                checkTrigger(ability, null, game);
            }
        }
    }

    public void checkTriggers(GameEvent event, Game game) {
        for (Iterator<TriggeredAbility> it = this.values().iterator(); it.hasNext(); ) {
            TriggeredAbility ability = it.next();
            if (ability.checkEventType(event, game)) {
                checkTrigger(ability, event, game);
            }
        }
    }

    private void checkTrigger(TriggeredAbility ability, GameEvent event, Game game) {
        // for effects like when leaves battlefield or destroyed use ShortLKI to check if permanent was in the correct zone before (e.g. Oblivion Ring or Karmic Justice)
        MageObject object = game.getObject(ability.getSourceId());
        if (ability.isInUseableZone(game, object, event)) {
            if (event == null || !game.getContinuousEffects().preventedByRuleModification(event, ability, game, false)) {
                if (object != null) {
                    boolean controllerSet = false;
                    if (ability.getZone() != Zone.COMMAND && event != null
                            && event.getTargetId() != null
                            && ability.isLeavesTheBattlefieldTrigger()
                            && game.getLKI().get(Zone.BATTLEFIELD) != null && game.getLKI().get(Zone.BATTLEFIELD).containsKey(ability.getSourceId())) {
                        // need to check if object was face down for dies and destroy events because the ability triggers in the new zone, zone counter -1 is used
                        Permanent permanent = (Permanent) game.getLastKnownInformation(ability.getSourceId(), Zone.BATTLEFIELD, ability.getSourceObjectZoneChangeCounter() - 1);
                        if (permanent != null) {
                            if (permanent.isFaceDown(game)
                                    && !isGainedAbility(ability, permanent) // the face down creature got the ability from an effect => so it should work
                                    && !ability.getWorksFaceDown()) { // the ability is declared to work also face down
                                // Not all triggered abilities of face down creatures work if they are faced down
                                return;
                            }
                            controllerSet = true;
                            ability.setControllerId(permanent.getControllerId());
                        }
                    }
                    if (!controllerSet) {
                        if (object instanceof Permanent) {
                            ability.setControllerId(((Permanent) object).getControllerId());
                        } else if (object instanceof Spell) {
                            // needed so that cast triggered abilities have to correct controller (e.g. Ulamog, the Infinite Gyre).
                            ability.setControllerId(((Spell) object).getControllerId());
                        }
                    }
                }

                if (ability.checkTrigger(event, game) && ability.checkTriggeredAlready(game) && ability.checkUsedAlready(game)) {
                    NumberOfTriggersEvent numberOfTriggersEvent = new NumberOfTriggersEvent(ability, event);
                    if (!game.replaceEvent(numberOfTriggersEvent)) {
                        for (int i = 0; i < numberOfTriggersEvent.getAmount(); i++) {
                            ability.trigger(game, ability.getControllerId(), event);
                        }
                    }
                }
            }
        }
    }

    /**
     * Adds a by sourceId gained triggered ability
     *
     * @param ability    - the gained ability
     * @param sourceId   - the source that assigned the ability
     * @param attachedTo - the object that gained the ability
     */
    public void add(TriggeredAbility ability, UUID sourceId, MageObject attachedTo) {
        if (sourceId == null) {
            add(ability, attachedTo);
        } else if (attachedTo == null) {
            this.put(ability.getId() + "_" + sourceId, ability);
        } else {
            this.add(ability, attachedTo);
            List<UUID> uuidList = new LinkedList<>();
            uuidList.add(sourceId);
            // if the object that gained the ability moves from zone then the triggered ability must be removed
            uuidList.add(attachedTo.getId());
            sources.put(getKey(ability, attachedTo), uuidList);
        }
    }

    public void add(TriggeredAbility ability, MageObject attachedTo) {
        this.put(getKey(ability, attachedTo), ability);
    }

    private String getKey(TriggeredAbility ability, MageObject target) {
        String key = ability.getId() + "_";
        if (target != null) {
            key += target.getId();
        }
        return key;
    }

    public void removeAbilitiesOfSource(UUID sourceId) {
        keySet().removeIf(key -> key.endsWith(sourceId.toString()));
    }

    public void removeAllGainedAbilities() {
        this.keySet().removeAll(sources.keySet());
        sources.clear();
    }

    public boolean isGainedAbility(TriggeredAbility abilityToCheck, MageObject attachedTo) {
        return sources.containsKey(getKey(abilityToCheck, attachedTo));
    }

    public void removeAbilitiesOfNonExistingSources(Game game) {
        // e.g. Token that had triggered abilities

        entrySet().removeIf(entry -> game.getObject(entry.getValue().getSourceId()) == null
                && game.getState().getDesignations().stream().noneMatch(designation -> designation.getId().equals(entry.getValue().getSourceId())));

    }

    public TriggeredAbilities copy() {
        return new TriggeredAbilities(this);
    }

}
