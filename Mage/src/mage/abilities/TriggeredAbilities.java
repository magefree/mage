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

import mage.MageObject;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TriggeredAbilities extends HashMap<String, TriggeredAbility> {

    public TriggeredAbilities() {}

    public TriggeredAbilities(final TriggeredAbilities abilities) {
        for (Map.Entry<String, TriggeredAbility> entry: abilities.entrySet()) {
            this.put(entry.getKey(), entry.getValue().copy());
        }
    }

    public void checkTriggers(GameEvent event, Game game) {
        for (TriggeredAbility ability: this.values()) {
            if (ability.isInUseableZone(game, true)) {
                MageObject object = getMageObject(event, game, ability);
                if (object != null) {
                    if (checkAbilityStillExists(ability, object)) {
                        if (object instanceof Permanent) {
                            ability.setControllerId(((Permanent) object).getControllerId());
                        }
                        if (ability.checkTrigger(event, game)) {
                            UUID controllerId = ability.getControllerId();
                            ability.trigger(game, controllerId);
                        }
                    }
                }
            }
        }
    }

    private boolean checkAbilityStillExists(TriggeredAbility ability, MageObject object) {
        boolean exists = true;
        if (!object.getAbilities().contains(ability)) {
            exists = false;
            if (object instanceof PermanentCard) {
                PermanentCard permanent = (PermanentCard)object;
                if (permanent.canTransform()) {
                    exists = permanent.getCard().getAbilities().contains(ability);
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

    public TriggeredAbilities copy() {
        return new TriggeredAbilities(this);
    }

}
