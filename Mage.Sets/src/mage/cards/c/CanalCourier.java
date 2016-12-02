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
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class CanalCourier extends CardImpl {

    public CanalCourier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.subtype.add("Human");
        this.subtype.add("Rogue");
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // When Canal Courier enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect(), false));

        // Whenever Canal Courier and another creature attack different players, Canal Courier can't be blocked this combat.
        Effect effect = new CantBeBlockedSourceEffect(Duration.EndOfCombat);
        effect.setText("{this} can't be blocked this combat");
        this.addAbility(new CanalCourierTriggeredAbility(effect));

    }

    public CanalCourier(final CanalCourier card) {
        super(card);
    }

    @Override
    public CanalCourier copy() {
        return new CanalCourier(this);
    }
}

class CanalCourierTriggeredAbility extends TriggeredAbilityImpl {

    public CanalCourierTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public CanalCourierTriggeredAbility(final CanalCourierTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CanalCourierTriggeredAbility copy() {
        return new CanalCourierTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // Attacking a planeswalker isn't the same thing as attacking a player.
        // Both Canal Courier and the other creature must attack different players for the last ability to trigger.
        Permanent permanent = game.getPermanent(getSourceId());
        if (permanent != null && permanent.isAttacking()) {
            UUID sourceDefenderId = game.getCombat().getDefenderId(permanent.getId());
            Player attackedPlayer = game.getPlayer(sourceDefenderId);
            if (attackedPlayer != null) {
                for (UUID attacker : game.getCombat().getAttackers()) {
                    if (attacker != permanent.getId()) {
                        UUID defenderId = game.getCombat().getDefenderId(attacker);
                        Player attackedPlayer2 = game.getPlayer(defenderId);
                        if (attackedPlayer2 != null && attackedPlayer.getId().equals(attackedPlayer2.getId())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} and another creature attack different players, " + super.getRule();
    }

}
