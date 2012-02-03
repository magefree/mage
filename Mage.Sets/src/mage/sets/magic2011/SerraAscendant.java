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
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SerraAscendant extends CardImpl<SerraAscendant> {

	public SerraAscendant(UUID ownerId) {
		super(ownerId, 28, "Serra Ascendant", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{W}");
		this.expansionSetCode = "M11";
		this.subtype.add("Human");
		this.subtype.add("Monk");
		this.color.setWhite(true);
		this.power = new MageInt(1);
		this.toughness = new MageInt(1);

		this.addAbility(LifelinkAbility.getInstance());
		this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SerraAscendantEffect()));
	}

	public SerraAscendant(final SerraAscendant card) {
		super(card);
	}

	@Override
	public SerraAscendant copy() {
		return new SerraAscendant(this);
	}

}

class SerraAscendantEffect extends ContinuousEffectImpl<SerraAscendantEffect> {

	public SerraAscendantEffect() {
		super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
		staticText = "As long as you have 30 or more life, {this} gets +5/+5 and has flying";
	}

	public SerraAscendantEffect(final SerraAscendantEffect effect) {
		super(effect);
	}

	@Override
	public SerraAscendantEffect copy() {
		return new SerraAscendantEffect(this);
	}

	@Override
	public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
		Permanent creature = game.getPermanent(source.getSourceId());
		if (creature != null) {
			Player player = game.getPlayer(creature.getControllerId());
			if (player != null && player.getLife() >= 30) {
				switch (layer) {
					case PTChangingEffects_7:
						if (sublayer == SubLayer.ModifyPT_7c) {
							creature.addPower(5);
							creature.addToughness(5);
						}
						break;
					case AbilityAddingRemovingEffects_6:
						if (sublayer == SubLayer.NA) {
							creature.addAbility(FlyingAbility.getInstance(), game);
						}
						break;
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return false;
	}

	@Override
	public boolean hasLayer(Layer layer) {
		return layer == Layer.AbilityAddingRemovingEffects_6 || layer == layer.PTChangingEffects_7;
	}

}