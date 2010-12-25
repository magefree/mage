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
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author Loki
 */
public class GloryscaleViashino extends CardImpl<GloryscaleViashino> {

    public GloryscaleViashino (UUID ownerId) {
        super(ownerId, 120, "Gloryscale Viashino", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{R}{G}{W}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Viashino");
        this.subtype.add("Soldier");
		this.color.setRed(true);
		this.color.setGreen(true);
		this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(new GloryscaleViashinoAbility());
    }

    public GloryscaleViashino (final GloryscaleViashino card) {
        super(card);
    }

    @Override
    public GloryscaleViashino copy() {
        return new GloryscaleViashino(this);
    }

}

class GloryscaleViashinoAbility extends TriggeredAbilityImpl<GloryscaleViashinoAbility> {
    public GloryscaleViashinoAbility() {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(3, 3, Duration.EndOfTurn));
    }

    public GloryscaleViashinoAbility(final GloryscaleViashinoAbility ability) {
        super(ability);
    }

    @Override
    public GloryscaleViashinoAbility copy() {
        return new GloryscaleViashinoAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
         if (event.getType() == GameEvent.EventType.SPELL_CAST) {
			Spell spell = game.getStack().getSpell(event.getTargetId());
			if (spell != null && spell.getColor().isMulticolored() && event.getPlayerId().equals(getControllerId())) {
				return true;
            }
		}
		return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a multicolored spell, Gloryscale Viashino gets +3/+3 until end of turn.";
    }
}