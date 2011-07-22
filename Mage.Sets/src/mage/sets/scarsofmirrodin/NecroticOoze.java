/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.StaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class NecroticOoze extends CardImpl<NecroticOoze> {

	public NecroticOoze(UUID ownerId) {
		super(ownerId, 72, "Necrotic Ooze", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
		this.expansionSetCode = "SOM";
		this.subtype.add("Ooze");
		this.color.setBlack(true);
		this.power = new MageInt(4);
		this.toughness = new MageInt(3);

		this.addAbility(new NecroticOozeAbility());
	}

	public NecroticOoze(final NecroticOoze card) {
		super(card);
	}

	@Override
	public NecroticOoze copy() {
		return new NecroticOoze(this);
	}

}

class NecroticOozeAbility extends StaticAbility<NecroticOozeAbility> {

	public NecroticOozeAbility() {
		super(Zone.BATTLEFIELD, new NecroticOozeEffect());
	}
	
	public NecroticOozeAbility(final NecroticOozeAbility ability) {
		super(ability);
	}
	
	@Override
	public NecroticOozeAbility copy() {
		return new NecroticOozeAbility(this);
	}
	
}

class NecroticOozeEffect extends ContinuousEffectImpl<NecroticOozeEffect> {

	public NecroticOozeEffect() {
		super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
		staticText = "As long as {this} is on the battlefield, it has all activated abilities of all creature cards in all graveyards";
	}
	
	public NecroticOozeEffect(final NecroticOozeEffect effect) {
		super(effect);
	}
	
	@Override
	public boolean apply(Game game, Ability source) {
		Permanent perm = game.getPermanent(source.getSourceId());
		if (perm != null) {
			for (Player player: game.getPlayers().values()) {
				for (Card card: player.getGraveyard().getCards(game)) {
					if (card.getCardType().contains(CardType.CREATURE)) {
						for (Ability ability: card.getAbilities()) {
							if (ability instanceof ActivatedAbility) {
								perm.addAbility(ability.copy());
							}
						}
					}
				}
			}
		}
		return true;
	}

	@Override
	public NecroticOozeEffect copy() {
		return new NecroticOozeEffect(this);
	}
	
}