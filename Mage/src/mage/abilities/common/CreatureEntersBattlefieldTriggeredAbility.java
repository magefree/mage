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

import mage.Constants.CardType;
import mage.Constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class CreatureEntersBattlefieldTriggeredAbility extends TriggeredAbilityImpl<CreatureEntersBattlefieldTriggeredAbility> {

    private boolean opponentController;

    /**
     * optional = false<br>
     * opponentController = false
     * 
     * @param effect
     */
    public CreatureEntersBattlefieldTriggeredAbility(Effect effect) {
        this(effect, false, false);
    }

    /**
     * opponentController = false
     * 
     * @param effect
     * @param optional
     */
    public CreatureEntersBattlefieldTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false);
    }

    /**
     * 
     * @param effect
     * @param optional
     * @param opponentController
     */
    public CreatureEntersBattlefieldTriggeredAbility(Effect effect, boolean optional, boolean opponentController) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.opponentController = opponentController;
    }

    public CreatureEntersBattlefieldTriggeredAbility(CreatureEntersBattlefieldTriggeredAbility ability) {
        super(ability);
        this.opponentController = ability.opponentController;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (((ZoneChangeEvent) event).getToZone() == Zone.BATTLEFIELD
                    && permanent.getCardType().contains(CardType.CREATURE)
                    && (permanent.getControllerId().equals(this.controllerId) ^ opponentController)) {
                if (!this.getTargets().isEmpty()) {
                    Target target = this.getTargets().get(0);
                    if (target instanceof TargetPlayer) {
                        target.add(permanent.getControllerId(), game);
                    }
                    if (target instanceof TargetCreaturePermanent) {
                        target.add(event.getTargetId(), game);
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature enters the battlefield under "
                + (opponentController ? "an opponent's control, " : "your control, ")
                + super.getRule();
    }

    @Override
    public CreatureEntersBattlefieldTriggeredAbility copy() {
        return new CreatureEntersBattlefieldTriggeredAbility(this);
    }
}
