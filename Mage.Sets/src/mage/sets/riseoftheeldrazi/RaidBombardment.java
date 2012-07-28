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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class RaidBombardment extends CardImpl<RaidBombardment> {

    public RaidBombardment(UUID ownerId) {
        super(ownerId, 161, "Raid Bombardment", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.expansionSetCode = "ROE";

        this.color.setRed(true);

        // Whenever a creature you control with power 2 or less attacks, Raid Bombardment deals 1 damage to defending player.
        this.addAbility(new RaidBombardmentTriggeredAbility());
    }

    public RaidBombardment(final RaidBombardment card) {
        super(card);
    }

    @Override
    public RaidBombardment copy() {
        return new RaidBombardment(this);
    }
}

class RaidBombardmentTriggeredAbility extends TriggeredAbilityImpl<RaidBombardmentTriggeredAbility> {

    public RaidBombardmentTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new DamageTargetEffect(1));
    }

    public RaidBombardmentTriggeredAbility(final RaidBombardmentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RaidBombardmentTriggeredAbility copy() {
        return new RaidBombardmentTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED && game.getActivePlayerId().equals(this.controllerId) ) {
            Permanent attacker = game.getPermanent(event.getSourceId());
            if (attacker != null) {
                if (attacker.getPower().getValue() <= 2) {
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getTargetId()));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control with power 2 or less attacks, {this} deals 1 damage to defending player.";
    }

}

