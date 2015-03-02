
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
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;

/**
*
* @author BetaSteward_at_googlemail.com
*/
public class TriggeredAbilities extends ConcurrentHashMap<String, TriggeredAbility> {

    private final Map<String, List<UUID>> sources = new HashMap<>();

    public TriggeredAbilities() {}

    public TriggeredAbilities(final TriggeredAbilities abilities) {
        for (Map.Entry<String, TriggeredAbility> entry: abilities.entrySet()) {
            this.put(entry.getKey(), entry.getValue().copy());
        }
        for (Map.Entry<String, List<UUID>> entry : abilities.sources.entrySet()) {
            sources.put(entry.getKey(), entry.getValue());
        }
    }

    public void checkTriggers(GameEvent event, Game game) {
        for (Iterator<TriggeredAbility> it = this.values().iterator(); it.hasNext();) {
            TriggeredAbility ability = it.next();
            if (!ability.checkEventType(event, game)) {
                continue;
            }
            // for effects like when leaves battlefield or destroyed use ShortLKI to check if permanent was in the correct zone before (e.g. Oblivion Ring or Karmic Justice)
            if (ability.isInUseableZone(game, null, event.getType().equals(EventType.ZONE_CHANGE) || event.getType().equals(EventType.DESTROYED_PERMANENT))) {
                if (!game.getContinuousEffects().preventedByRuleModification(event, ability, game, false)) {

                    MageObject object = null;
                    if (!ability.getZone().equals(Zone.COMMAND) && !game.getState().getZone(ability.getSourceId()).equals(ability.getZone())) {
                        object = game.getShortLivingLKI(ability.getSourceId(), ability.getZone());
                    }
                    if (object == null) {
                        object = getMageObject(event, game, ability);
                    }

                    if (object != null) {
                        if (checkAbilityStillExists(ability, event, object)) {
                            if (object instanceof Permanent) {
                                ability.setControllerId(((Permanent) object).getControllerId());
                            }
                            ability.setSourceObject(object);
                            if (ability.checkTrigger(event, game)) {
                                UUID controllerId = ability.getControllerId();
                                ability.trigger(game, controllerId);
                            }
                        }
                    }
                }
            }
        }
    }
  
    private boolean checkAbilityStillExists(TriggeredAbility ability, GameEvent event, MageObject object) {
        boolean exists = true;
        
        if (!object.getAbilities().contains(ability)) {
            exists = false;
            if (object instanceof PermanentCard) {
                PermanentCard permanent = (PermanentCard)object;
                if (permanent.canTransform() && event.getType() == GameEvent.EventType.TRANSFORMED) {
                    exists = permanent.getCard().getAbilities().contains(ability);
                }                
            }            
        } else {
            if (object instanceof PermanentCard) {
                PermanentCard permanent = (PermanentCard)object;
                if (permanent.isFaceDown()) {
                    exists = ability.getWorksFaceDown();
                }
            }            
        }
        return exists;
    }

    private MageObject getMageObject(GameEvent event, Game game, TriggeredAbility ability) {
        MageObject object = game.getPermanent(ability.getSourceId());
        if (object == null) {
            object = game.getLastKnownInformation(ability.getSourceId(), event.getZone());
            if (object == null) {
                object = game.getObject(ability.getSourceId());
            }
        }
        return object;
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
        }
        else {
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
        for (String key: this.keySet()) {
            if(key.endsWith(sourceId.toString())) {
                keysToRemove.add(key);
            }
        }
        for(String key: keysToRemove) {
            remove(key);
        }
    }

    public void removeAllGainedAbilities() {
        for(String key: sources.keySet()) {
            this.remove(key);
        }
        sources.clear();
    }

    public TriggeredAbilities copy() {
        return new TriggeredAbilities(this);
    }

}