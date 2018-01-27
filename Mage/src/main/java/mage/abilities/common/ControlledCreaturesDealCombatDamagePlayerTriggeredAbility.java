/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class ControlledCreaturesDealCombatDamagePlayerTriggeredAbility extends TriggeredAbilityImpl {

    private boolean madeDamage = false;
    private Set<UUID> damagedPlayerIds = new HashSet<>();

    public ControlledCreaturesDealCombatDamagePlayerTriggeredAbility(Effect effect) {
        this(Zone.BATTLEFIELD, effect);
    }

    public ControlledCreaturesDealCombatDamagePlayerTriggeredAbility(Zone zone, Effect effect) {
        super(zone, effect, false);
    }

    public ControlledCreaturesDealCombatDamagePlayerTriggeredAbility(final ControlledCreaturesDealCombatDamagePlayerTriggeredAbility ability) {
        super(ability);
        this.madeDamage = ability.madeDamage;
        this.damagedPlayerIds = new HashSet<>();
        this.damagedPlayerIds.addAll(ability.damagedPlayerIds);
    }

    @Override
    public ControlledCreaturesDealCombatDamagePlayerTriggeredAbility copy() {
        return new ControlledCreaturesDealCombatDamagePlayerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER
                || event.getType() == EventType.COMBAT_DAMAGE_STEP_PRIORITY
                || event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.DAMAGED_PLAYER) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
            Permanent p = game.getPermanent(event.getSourceId());
            if (damageEvent.isCombatDamage() && p != null && p.getControllerId().equals(this.getControllerId())) {
                madeDamage = true;
                damagedPlayerIds.add(event.getPlayerId());
            }
        }
        if (event.getType() == EventType.COMBAT_DAMAGE_STEP_PRIORITY) {
            if (madeDamage) {
                Set<UUID> damagedPlayersCopy = new HashSet<>();
                damagedPlayersCopy.addAll(damagedPlayerIds);
                for (Effect effect : this.getEffects()) {
                    effect.setValue("damagedPlayers", damagedPlayersCopy);
                }
                damagedPlayerIds.clear();
                madeDamage = false;
                return true;
            }
        }
        if (event.getType() == EventType.ZONE_CHANGE && event.getTargetId().equals(getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.GRAVEYARD) {
                damagedPlayerIds.clear();
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures you control deal combat damage to a player, " + super.getRule();
    }
}
