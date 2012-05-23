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
package mage.sets.avacynrestored;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author Loki
 */
public class VigilanteJustice extends CardImpl<VigilanteJustice> {

    public VigilanteJustice(UUID ownerId) {
        super(ownerId, 165, "Vigilante Justice", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");
        this.expansionSetCode = "AVR";

        this.color.setRed(true);

        // Whenever a Human enters the battlefield under your control, Vigilante Justice deals 1 damage to target creature or player.
        this.addAbility(new VigilanteJusticeTriggeredAbility());
    }

    public VigilanteJustice(final VigilanteJustice card) {
        super(card);
    }

    @Override
    public VigilanteJustice copy() {
        return new VigilanteJustice(this);
    }
}

class VigilanteJusticeTriggeredAbility extends ZoneChangeTriggeredAbility<VigilanteJusticeTriggeredAbility> {

    public VigilanteJusticeTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new DamageTargetEffect(1), "Whenever a Human enters the battlefield under your control, ", false);
        this.addTarget(new TargetCreatureOrPlayer());
    }

    public VigilanteJusticeTriggeredAbility(VigilanteJusticeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getToZone() == Constants.Zone.BATTLEFIELD) {
                Permanent permanent = game.getPermanent(event.getTargetId());
                if (permanent != null && permanent.getSubtype().contains("Human") && permanent.getControllerId().equals(this.getControllerId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public VigilanteJusticeTriggeredAbility copy() {
        return new VigilanteJusticeTriggeredAbility(this);
    }

}

