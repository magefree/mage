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
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public class ShatteredAngel extends CardImpl<ShatteredAngel> {

    public ShatteredAngel (UUID ownerId) {
        super(ownerId, 23, "Shattered Angel", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.expansionSetCode = "NPH";
        this.subtype.add("Angel");
		this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new ShatteredAngelTriggeredAbility());
    }

    public ShatteredAngel (final ShatteredAngel card) {
        super(card);
    }

    @Override
    public ShatteredAngel copy() {
        return new ShatteredAngel(this);
    }

}

class ShatteredAngelTriggeredAbility extends TriggeredAbilityImpl<ShatteredAngelTriggeredAbility> {
    ShatteredAngelTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new GainLifeEffect(3), true);
    }

    ShatteredAngelTriggeredAbility(final ShatteredAngelTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ShatteredAngelTriggeredAbility copy() {
        return new ShatteredAngelTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent)event).getToZone() == Constants.Zone.BATTLEFIELD) {
			Permanent permanent = game.getPermanent(event.getTargetId());
			if (permanent != null && permanent.getCardType().contains(CardType.LAND) && game.getOpponents(this.controllerId).contains(permanent.getControllerId())) {
				return true;
			}
		}
		return false;
    }

    @Override
    public String getRule() {
        return "Whenever a land enters the battlefield under an opponent's control, you may gain 3 life.";
    }
}