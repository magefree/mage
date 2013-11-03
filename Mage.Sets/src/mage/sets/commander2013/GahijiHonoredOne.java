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
package mage.sets.commander2013;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class GahijiHonoredOne extends CardImpl<GahijiHonoredOne> {

    public GahijiHonoredOne(UUID ownerId) {
        super(ownerId, 191, "Gahiji, Honored One", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{R}{G}{W}");
        this.expansionSetCode = "C13";
        this.supertype.add("Legendary");
        this.subtype.add("Beast");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever a creature attacks one of your opponents or a planeswalker an opponent controls, that creature gets +2/+0 until end of turn.
        this.addAbility(new GahijiHonoredOneTriggeredAbility());

    }

    public GahijiHonoredOne(final GahijiHonoredOne card) {
        super(card);
    }

    @Override
    public GahijiHonoredOne copy() {
        return new GahijiHonoredOne(this);
    }
}

class GahijiHonoredOneTriggeredAbility extends TriggeredAbilityImpl<GahijiHonoredOneTriggeredAbility> {

    public GahijiHonoredOneTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostTargetEffect(2,0, Duration.EndOfTurn), false);
    }

    public GahijiHonoredOneTriggeredAbility(Effect effect, boolean optional, String text) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public GahijiHonoredOneTriggeredAbility(final GahijiHonoredOneTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType().equals(EventType.ATTACKER_DECLARED)) {
            Player defender = game.getPlayer(event.getTargetId());
            if (defender == null) {
                Permanent planeswalker = game.getPermanent(event.getTargetId());
                if (planeswalker != null) {
                    defender = game.getPlayer(planeswalker.getControllerId());
                }
            }
            if (defender != null) {
                Set<UUID> opponents = game.getOpponents(this.getControllerId());
                if (opponents != null && opponents.contains(defender.getId())) {
                    for (Effect effect: this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getSourceId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature attacks one of your opponents or a planeswalker an opponent controls, that creature gets +2/+0 until end of turn.";
    }

    @Override
    public GahijiHonoredOneTriggeredAbility copy() {
        return new GahijiHonoredOneTriggeredAbility(this);
    }

}
