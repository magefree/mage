
/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import mage.MageObject;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

/**
 *
 * @author BetaSteward_at_googlemail.com
 *
 * This class uses ConcurrentHashMap to avoid ConcurrentModificationExceptions.
 * See ticket https://github.com/magefree/mage/issues/966 and
 * https://github.com/magefree/mage/issues/473
 */
public class TriggeredAbilities extends ConcurrentHashMap<String, TriggeredAbility> {

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
        for (Iterator<TriggeredAbility> it = this.values().iterator(); it.hasNext();) {
            TriggeredAbility ability = it.next();
            if (ability instanceof StateTriggeredAbility && ((StateTriggeredAbility) ability).canTrigger(game)) {
                checkTrigger(ability, null, game);
            }
        }
    }

    public void checkTriggers(GameEvent event, Game game) {
        for (Iterator<TriggeredAbility> it = this.values().iterator(); it.hasNext();) {
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
                    if (!ability.getZone().equals(Zone.COMMAND) && event != null && event.getTargetId() != null && event.getTargetId().equals(ability.getSourceId())
                            && (event.getType().equals(EventType.ZONE_CHANGE) || event.getType().equals(EventType.DESTROYED_PERMANENT))) {
                        // need to check if object was face down for dies and destroy events because the ability triggers in the new zone, zone counter -1 is used
                        Permanent permanent = (Permanent) game.getLastKnownInformation(ability.getSourceId(), Zone.BATTLEFIELD, ability.getSourceObjectZoneChangeCounter() - 1);
                        if (permanent != null) {
                            if (!ability.getWorksFaceDown() && permanent.isFaceDown(game)) {
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
                        } else if (object instanceof Card) {
                            ability.setControllerId(((Card) object).getOwnerId());
                        }
                    }
                }

                if (ability.checkTrigger(event, game)) {
                    ability.trigger(game, ability.getControllerId());
                }
            }
        }
    }

    /**
     * Adds a by sourceId gained triggered ability
     *
     * @param ability - the gained ability
     * @param sourceId - the source that assigned the ability
     * @param attachedTo - the object that gained the ability
     */
    public void add(TriggeredAbility ability, UUID sourceId, MageObject attachedTo) {
        if (sourceId == null) {
            add(ability, attachedTo);
        } else {
            this.add(ability, attachedTo);
            List<UUID> uuidList = new LinkedList<>();
            uuidList.add(sourceId);
            // if the object that gained the ability moves zone, also then the triggered ability must be removed
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
        List<String> keysToRemove = new ArrayList<>();
        for (String key : this.keySet()) {
            if (key.endsWith(sourceId.toString())) {
                keysToRemove.add(key);
            }
        }
        for (String key : keysToRemove) {
            remove(key);
        }
    }

    public void removeAllGainedAbilities() {
        for (String key : sources.keySet()) {
            this.remove(key);
        }
        sources.clear();
    }

    public void removeAbilitiesOfNonExistingSources(Game game) {
        // e.g. Token that had triggered abilities
        List<String> keysToRemove = new ArrayList<>();
        for (Entry<String, TriggeredAbility> entry : this.entrySet()) {
            if (game.getObject(entry.getValue().getSourceId()) == null) {
                keysToRemove.add(entry.getKey());
            }
        }
        for (String key : keysToRemove) {
            remove(key);
        }
    }

    public TriggeredAbilities copy() {
        return new TriggeredAbilities(this);
    }

}
