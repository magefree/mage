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

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class AttacksAllTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterCreaturePermanent filter;
    protected boolean attacksYouOrYourPlaneswalker;
    protected SetTargetPointer setTargetPointer;
    protected boolean controller;

    public AttacksAllTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, new FilterCreaturePermanent(), SetTargetPointer.NONE, false);
    }

    public AttacksAllTriggeredAbility(Effect effect, boolean optional, boolean attacksYouOrYourPlaneswalker) {
        this(effect, optional, new FilterCreaturePermanent(), SetTargetPointer.NONE, attacksYouOrYourPlaneswalker);
    }

    public AttacksAllTriggeredAbility(Effect effect, boolean optional, FilterCreaturePermanent filter, SetTargetPointer setTargetPointer, boolean attacksYouOrYourPlaneswalker) {
        this(effect, optional, filter, setTargetPointer, attacksYouOrYourPlaneswalker, false);
    }

    public AttacksAllTriggeredAbility(Effect effect, boolean optional, FilterCreaturePermanent filter, SetTargetPointer setTargetPointer, boolean attacksYouOrYourPlaneswalker, boolean controller) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        this.attacksYouOrYourPlaneswalker = attacksYouOrYourPlaneswalker;
        this.setTargetPointer = setTargetPointer;
        this.controller = controller;
    }

    public AttacksAllTriggeredAbility(final AttacksAllTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter.copy();
        this.attacksYouOrYourPlaneswalker = ability.attacksYouOrYourPlaneswalker;
        this.setTargetPointer = ability.setTargetPointer;
        this.controller = ability.controller;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (filter.match(permanent, getSourceId(), getControllerId(), game)) {
            if (attacksYouOrYourPlaneswalker) {
                boolean check = false;
                if (event.getTargetId().equals(getControllerId())) {
                    check = true;
                } else {
                    Permanent planeswalker = game.getPermanent(event.getTargetId());
                    if (planeswalker != null && planeswalker.isPlaneswalker() && planeswalker.getControllerId().equals(getControllerId())) {
                        check = true;
                    }
                }
                if (!check) {
                    return false;
                }
            }
            switch (setTargetPointer) {
                case PERMANENT:
                    for (Effect effect : getEffects()) {
                        effect.setTargetPointer(new FixedTarget(permanent.getId()));
                    }
                    break;
                case PLAYER:
                    UUID playerId = controller ? permanent.getControllerId() : game.getCombat().getDefendingPlayerId(permanent.getId(), game);
                    if (playerId != null) {
                        for (Effect effect : getEffects()) {
                            effect.setTargetPointer(new FixedTarget(playerId));
                        }
                    }
                    break;
            }
            return true;
        }
        return false;
    }

    @Override
    public AttacksAllTriggeredAbility copy() {
        return new AttacksAllTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a " + filter.getMessage() + " attacks" + (attacksYouOrYourPlaneswalker ? " you or a planeswalker you control" : "") + ", " + super.getRule();
    }

}
