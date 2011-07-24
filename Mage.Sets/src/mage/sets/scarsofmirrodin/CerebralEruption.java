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
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.PostResolveEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CerebralEruption extends CardImpl<CerebralEruption> {

	public CerebralEruption(UUID ownerId) {
		super(ownerId, 86, "Cerebral Eruption", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");
		this.expansionSetCode = "SOM";
		this.color.setRed(true);
		this.getSpellAbility().addTarget(new TargetOpponent());
		this.getSpellAbility().addEffect(new CerebralEruptionEffect1());
		this.getSpellAbility().addEffect(new CerebralEruptionEffect2());
	}

	public CerebralEruption(final CerebralEruption card) {
		super(card);
	}

	@Override
	public CerebralEruption copy() {
		return new CerebralEruption(this);
	}

}

class CerebralEruptionEffect1 extends OneShotEffect<CerebralEruptionEffect1> {

	private static FilterPermanent filter = new FilterCreaturePermanent();
	
	public CerebralEruptionEffect1() {
		super(Outcome.Damage);
		staticText = "Target opponent reveals the top card of his or her library. {this} deals damage equal to the revealed card's converted mana cost to that player and each creature he or she controls.";
	}
	
	public CerebralEruptionEffect1(final CerebralEruptionEffect1 effect) {
		super(effect);
	}
	
	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(source.getFirstTarget());
		if (player != null && player.getLibrary().size() > 0) {
			Card card = player.getLibrary().getFromTop(game);
			Cards cards = new CardsImpl();
			cards.add(card);
			player.revealCards("Cerebral Eruption", cards, game);
			game.getState().setValue(source.getId().toString(), card);
			int damage = card.getManaCost().convertedManaCost();
			player.damage(damage, source.getId(), game, false, true);
			for (Permanent perm: game.getBattlefield().getAllActivePermanents(filter, player.getId())) {
				perm.damage(damage, source.getId(), game, true, false);
			}
			return true;
		}
		return false;
	}

	@Override
	public CerebralEruptionEffect1 copy() {
		return new CerebralEruptionEffect1(this);
	}
	
}

class CerebralEruptionEffect2 extends PostResolveEffect<CerebralEruptionEffect2> {

	public CerebralEruptionEffect2() {
		staticText = "If a land card is revealed this way, return {this} to its owner's hand";
	}
	
	public CerebralEruptionEffect2(final CerebralEruptionEffect2 effect) {
		super(effect);
	}
	
	@Override
	public void postResolve(Card card, Ability source, UUID controllerId, Game game) {
		Card revealed = (Card) game.getState().getValue(source.getId().toString());
		if (revealed != null && revealed.getCardType().contains(CardType.LAND)) {
			card.moveToZone(Zone.HAND, source.getId(), game, false);
		}
		else {
			card.moveToZone(Zone.GRAVEYARD, source.getId(), game, false);
		}
	}

	@Override
	public CerebralEruptionEffect2 copy() {
		return new CerebralEruptionEffect2(this);
	}
	
}