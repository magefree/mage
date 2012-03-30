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
package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author North
 */
public class PunishingFire extends CardImpl<PunishingFire> {

    public PunishingFire(UUID ownerId) {
        super(ownerId, 142, "Punishing Fire", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{R}");
        this.expansionSetCode = "ZEN";

        this.color.setRed(true);

        // Punishing Fire deals 2 damage to target creature or player.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
        // Whenever an opponent gains life, you may pay {R}. If you do, return Punishing Fire from your graveyard to your hand.
        this.addAbility(new PunishingFireTriggeredAbility());
    }

    public PunishingFire(final PunishingFire card) {
        super(card);
    }

    @Override
    public PunishingFire copy() {
        return new PunishingFire(this);
    }
}

class PunishingFireTriggeredAbility extends TriggeredAbilityImpl<PunishingFireTriggeredAbility> {

    public PunishingFireTriggeredAbility() {
        super(Zone.GRAVEYARD, new DoIfCostPaid(new ReturnToHandSourceEffect(), new ManaCostsImpl("{R}")));
    }

    public PunishingFireTriggeredAbility(final PunishingFireTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PunishingFireTriggeredAbility copy() {
        return new PunishingFireTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.GAINED_LIFE && game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent gains life, " + super.getRule();
    }
}
