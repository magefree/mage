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
package mage.sets.timeshifted;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author Loki
 */
public class SolkanarTheSwampKing extends CardImpl<SolkanarTheSwampKing> {

    public SolkanarTheSwampKing(UUID ownerId) {
        super(ownerId, 100, "Sol'kanar the Swamp King", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{U}{B}{R}");
        this.expansionSetCode = "TSB";
        this.supertype.add("Legendary");
        this.subtype.add("Demon");

        this.color.setRed(true);
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(new SwampwalkAbility());
        // Whenever a player casts a black spell, you gain 1 life.
        this.addAbility(new SolkanarTheSwampKingAbility());

    }

    public SolkanarTheSwampKing(final SolkanarTheSwampKing card) {
        super(card);
    }

    @Override
    public SolkanarTheSwampKing copy() {
        return new SolkanarTheSwampKing(this);
    }
}

class SolkanarTheSwampKingAbility extends TriggeredAbilityImpl<SolkanarTheSwampKingAbility> {

	public SolkanarTheSwampKingAbility() {
		super(Constants.Zone.BATTLEFIELD, new GainLifeEffect(1), false);
	}

	public SolkanarTheSwampKingAbility(final SolkanarTheSwampKingAbility ability) {
		super(ability);
	}

	@Override
	public SolkanarTheSwampKingAbility copy() {
		return new SolkanarTheSwampKingAbility(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == GameEvent.EventType.SPELL_CAST) {
			Spell spell = game.getStack().getSpell(event.getTargetId());
			if (spell != null && spell.getColor().isBlack()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getRule() {
		return "Whenever a player casts a black spell, you gain 1 life.";
	}

}
