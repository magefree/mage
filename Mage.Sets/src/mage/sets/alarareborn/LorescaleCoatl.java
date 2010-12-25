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

package mage.sets.alarareborn;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.AddPlusOneCountersSourceEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author Loki
 */
public class LorescaleCoatl extends CardImpl<LorescaleCoatl> {

    public LorescaleCoatl (UUID ownerId) {
        super(ownerId, 101, "Lorescale Coatl", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Snake");
		this.color.setGreen(true);
		this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new LorescaleCoatlAbility());
    }

    public LorescaleCoatl (final LorescaleCoatl card) {
        super(card);
    }

    @Override
    public LorescaleCoatl copy() {
        return new LorescaleCoatl(this);
    }
}

class LorescaleCoatlAbility extends TriggeredAbilityImpl<LorescaleCoatlAbility> {
    public LorescaleCoatlAbility() {
        super(Zone.BATTLEFIELD, new AddPlusOneCountersSourceEffect(1), true);
    }

    public LorescaleCoatlAbility(final LorescaleCoatlAbility ability) {
        super(ability);
    }

    @Override
    public LorescaleCoatlAbility copy() {
        return new LorescaleCoatlAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DREW_CARD && event.getPlayerId().equals(controllerId)) {
			return true;
		}
		return false;
    }

    @Override
    public String getRule() {
        return "Whenever you draw a card, you may put a +1/+1 counter on Lorescale Coatl.";
    }
}