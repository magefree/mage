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

package mage.sets.scarsofmirrodin;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public class VigilForTheLost extends CardImpl<VigilForTheLost> {

    public VigilForTheLost (UUID ownerId) {
        super(ownerId, 26, "Vigil for the Lost", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        this.expansionSetCode = "SOM";
		this.color.setWhite(true);
        this.addAbility(new VigilForTheLostTriggeredAbility());
    }

    public VigilForTheLost (final VigilForTheLost card) {
        super(card);
    }

    @Override
    public VigilForTheLost copy() {
        return new VigilForTheLost(this);
    }

}

class VigilForTheLostTriggeredAbility extends TriggeredAbilityImpl<VigilForTheLostTriggeredAbility> {
    VigilForTheLostTriggeredAbility() {
        super(Zone.BATTLEFIELD, new VigilForTheLostEffect());
    }

    VigilForTheLostTriggeredAbility(final VigilForTheLostTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VigilForTheLostTriggeredAbility copy() {
        return new VigilForTheLostTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE &&
                ((ZoneChangeEvent)event).getToZone() == Zone.GRAVEYARD &&
                ((ZoneChangeEvent)event).getFromZone() == Zone.BATTLEFIELD) {
            Permanent p = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (p.getControllerId().equals(this.getControllerId()) && p.getCardType().contains(CardType.CREATURE)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control is put into a graveyard from the battlefield, you may pay {X}. If you do, you gain X life.";
    }
}

class VigilForTheLostEffect extends OneShotEffect<VigilForTheLostEffect> {
    VigilForTheLostEffect() {
        super(Constants.Outcome.GainLife);
        staticText = "you may pay {X}. If you do, you gain X life";
    }

    VigilForTheLostEffect(final VigilForTheLostEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ManaCostsImpl cost = new ManaCostsImpl("{X}");
        cost.clearPaid();
        if (cost.pay(source, game, source.getId(), source.getControllerId(), false)) {
            Player player = game.getPlayer(source.getControllerId());
            player.gainLife(cost.getX(), game);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public VigilForTheLostEffect copy() {
        return new VigilForTheLostEffect(this);
    }

}