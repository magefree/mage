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

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class PutIntoGraveFromBattlefieldAllTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterPermanent filter;
    private boolean setTargetPointer;
    private boolean onlyToControllerGraveyard;

    public PutIntoGraveFromBattlefieldAllTriggeredAbility(Effect effect, boolean optional, FilterPermanent filter, boolean setTargetPointer) {
        this(effect, optional, filter, setTargetPointer, false);
    }
    public PutIntoGraveFromBattlefieldAllTriggeredAbility(Effect effect, boolean optional, FilterPermanent filter, boolean setTargetPointer, boolean onlyToControllerGraveyard) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        this.onlyToControllerGraveyard = onlyToControllerGraveyard;
    }

    public PutIntoGraveFromBattlefieldAllTriggeredAbility(final PutIntoGraveFromBattlefieldAllTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.onlyToControllerGraveyard = ability.onlyToControllerGraveyard;        
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() == Zone.BATTLEFIELD
                && zEvent.getToZone() == Zone.GRAVEYARD) {                
            if (filter.match(zEvent.getTarget(), this.getSourceId(), this.getControllerId(), game)) {
                if (setTargetPointer) {
                    for (Effect effect :this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever " + filter.getMessage() + " is put into " + (onlyToControllerGraveyard ? "your":"a") +
                " graveyard from the battlefield, " + super.getRule();
    }

    @Override
    public PutIntoGraveFromBattlefieldAllTriggeredAbility copy() {
        return new PutIntoGraveFromBattlefieldAllTriggeredAbility(this);
    }
}
