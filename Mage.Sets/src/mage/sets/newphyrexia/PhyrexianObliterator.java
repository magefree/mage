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
package mage.sets.newphyrexia;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Loki
 */
public class PhyrexianObliterator extends CardImpl<PhyrexianObliterator> {

    public PhyrexianObliterator(UUID ownerId) {
        super(ownerId, 68, "Phyrexian Obliterator", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{B}{B}{B}{B}");
        this.expansionSetCode = "NPH";
        this.subtype.add("Horror");

        this.color.setBlack(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(new PhyrexianObliteratorTriggeredAbility());
    }

    public PhyrexianObliterator(final PhyrexianObliterator card) {
        super(card);
    }

    @Override
    public PhyrexianObliterator copy() {
        return new PhyrexianObliterator(this);
    }
}

class PhyrexianObliteratorTriggeredAbility extends TriggeredAbilityImpl<PhyrexianObliteratorTriggeredAbility> {
    PhyrexianObliteratorTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new SacrificeEffect(new FilterPermanent(), 0, ""));
    }

    PhyrexianObliteratorTriggeredAbility(final PhyrexianObliteratorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PhyrexianObliteratorTriggeredAbility copy() {
        return new PhyrexianObliteratorTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_CREATURE && event.getTargetId().equals(this.sourceId)) {
			UUID controller = game.getControllerId(event.getSourceId());
			if (controller != null) {
				Player player = game.getPlayer(controller);
				if (player != null) {
					getEffects().get(0).setTargetPointer(new FixedTarget(player.getId()));
					((SacrificeEffect) getEffects().get(0)).setAmount(new StaticValue(event.getAmount()));
					return true;
				}
			}
		}
		return false;
    }

    @Override
    public String getRule() {
        return "Whenever a source deals damage to {this}, that source's controller sacrifices that many permanents";
    }
}
