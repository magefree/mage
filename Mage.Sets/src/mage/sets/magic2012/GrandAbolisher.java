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
package mage.sets.magic2012;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 * @author nantuko
 */
public class GrandAbolisher extends CardImpl<GrandAbolisher> {

	public GrandAbolisher(UUID ownerId) {
		super(ownerId, 19, "Grand Abolisher", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{W}{W}");
		this.expansionSetCode = "M12";
		this.subtype.add("Human");
		this.subtype.add("Cleric");

		this.color.setWhite(true);
		this.power = new MageInt(2);
		this.toughness = new MageInt(2);

		// During your turn, your opponents can't cast spells or activate abilities of artifacts, creatures, or enchantments.
		this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GrandAbolisherEffect()));
	}

	public GrandAbolisher(final GrandAbolisher card) {
		super(card);
	}

	@Override
	public GrandAbolisher copy() {
		return new GrandAbolisher(this);
	}
}

class GrandAbolisherEffect extends ReplacementEffectImpl<GrandAbolisherEffect> {

	public GrandAbolisherEffect() {
		super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Benefit);
	}

	public GrandAbolisherEffect(final GrandAbolisherEffect effect) {
		super(effect);
		staticText = "During your turn, your opponents can't cast spells or activate abilities of artifacts, creatures, or enchantments";
	}

	@Override
	public GrandAbolisherEffect copy() {
		return new GrandAbolisherEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return true;
	}

	@Override
	public boolean replaceEvent(GameEvent event, Ability source, Game game) {
		return true;
	}

	@Override
	public boolean applies(GameEvent event, Ability source, Game game) {
		boolean spell = event.getType() == GameEvent.EventType.CAST_SPELL;
		boolean activated = event.getType() == GameEvent.EventType.ACTIVATE_ABILITY;
		if ((spell || activated) && game.getActivePlayerId().equals(source.getControllerId()) && game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {

			if (spell) {
				return true;
			}

			// check source of activated ability
			Permanent permanent = game.getPermanent(event.getTargetId());
			if (permanent != null) {
				return permanent.getCardType().contains(CardType.ARTIFACT) || permanent.getCardType().contains(CardType.CREATURE)
						|| permanent.getCardType().contains(CardType.ENCHANTMENT);
			} else {
				MageObject object = game.getObject(event.getTargetId());
				if (object != null) {
					return object.getCardType().contains(CardType.ARTIFACT) || object.getCardType().contains(CardType.CREATURE)
						|| object.getCardType().contains(CardType.ENCHANTMENT);
				}
			}
		}

		return false;
	}

}
