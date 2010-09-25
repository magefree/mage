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

package mage.sets.magic2011;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FireServant extends CardImpl<FireServant> {

	public FireServant(UUID ownerId) {
		super(ownerId, "Fire Servant", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
		this.expansionSetCode = "M11";
		this.subtype.add("Elemental");
		this.color.setRed(true);
		this.power = new MageInt(4);
		this.toughness = new MageInt(3);

		this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new FireServantEffect()));
	}

	public FireServant(final FireServant card) {
		super(card);
	}

	@Override
	public FireServant copy() {
		return new FireServant(this);
	}

	@Override
	public String getArt() {
		return "129085_typ_reg_sty_010.jpg";
	}

}

class FireServantEffect extends ReplacementEffectImpl<FireServantEffect> {

	public FireServantEffect() {
		super(Duration.WhileOnBattlefield, Outcome.Damage);
	}

	public FireServantEffect(final FireServantEffect effect) {
		super(effect);
	}

	@Override
	public FireServantEffect copy() {
		return new FireServantEffect(this);
	}

	@Override
	public boolean applies(GameEvent event, Ability source, Game game) {
		switch (event.getType()) {
			case DAMAGE_CREATURE:
			case DAMAGE_PLAYER:
			case DAMAGE_PLANESWALKER:
				StackObject spell = game.getStack().getStackObject(event.getSourceId());
				if (spell != null && spell.getControllerId().equals(source.getControllerId()) && spell.getColor().isRed() &&
						(spell.getCardType().contains(CardType.INSTANT) || spell.getCardType().contains(CardType.SORCERY))) {
					event.setAmount(event.getAmount() * 2);
				}
		}
		return false;
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return true;
	}

	@Override
	public boolean replaceEvent(GameEvent event, Ability source, Game game) {
		return apply(game, source);
	}

	@Override
	public String getText(Ability source) {
		return "If a red instant or sorcery spell you control would deal damage, it deals double that damage instead";
	}
}