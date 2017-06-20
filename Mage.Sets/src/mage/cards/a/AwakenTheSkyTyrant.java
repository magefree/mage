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
package mage.cards.a;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.DragonToken2;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class AwakenTheSkyTyrant extends CardImpl {

    public AwakenTheSkyTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}");

        // When a source an opponent controls deals damage to you, sacrifice Awaken the Sky Tyrant. If you do, create a 5/5 red Dragon creature token with flying.
        this.addAbility(new AwakenTheSkyTyrantTriggeredAbility());
    }

    public AwakenTheSkyTyrant(final AwakenTheSkyTyrant card) {
        super(card);
    }

    @Override
    public AwakenTheSkyTyrant copy() {
        return new AwakenTheSkyTyrant(this);
    }
}

class AwakenTheSkyTyrantTriggeredAbility extends TriggeredAbilityImpl {

    public AwakenTheSkyTyrantTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new CreateTokenEffect(new DragonToken2(), 1), new SacrificeSourceCost(), null, false), false);
    }

    public AwakenTheSkyTyrantTriggeredAbility(final AwakenTheSkyTyrantTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AwakenTheSkyTyrantTriggeredAbility copy() {
        return new AwakenTheSkyTyrantTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(controllerId)) {
            UUID sourceControllerId = game.getControllerId(event.getSourceId());
            if (sourceControllerId != null && game.getOpponents(getControllerId()).contains(sourceControllerId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When a source an opponent controls deals damage to you, " + super.getRule();
    }
}
