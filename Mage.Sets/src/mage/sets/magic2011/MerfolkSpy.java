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
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.LandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.Filter.ComparisonScope;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MerfolkSpy extends CardImpl<MerfolkSpy> {

	private static FilterLandPermanent filter = new FilterLandPermanent("Island");

	static {
		filter.getSubtype().add("Island");
		filter.setScopeSubtype(ComparisonScope.Any);
	}

	public MerfolkSpy(UUID ownerId) {
		super(ownerId, 66, "Merfolk Spy", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{U}");
		this.expansionSetCode = "M11";
		this.subtype.add("Merfolk");
		this.subtype.add("Rogue");
		this.color.setBlue(true);
		this.power = new MageInt(1);
		this.toughness = new MageInt(1);

		this.addAbility(new LandwalkAbility(filter));
		this.addAbility(new MerfolkSpyAbility());
	}

	public MerfolkSpy(final MerfolkSpy card) {
		super(card);
	}

	@Override
	public MerfolkSpy copy() {
		return new MerfolkSpy(this);
	}

	@Override
	public String getArt() {
		return "129100_typ_reg_sty_010.jpg";
	}

}

class MerfolkSpyAbility extends TriggeredAbilityImpl<MerfolkSpyAbility> {

	public MerfolkSpyAbility() {
		super(Zone.BATTLEFIELD, new MerfolkSpyEffect(), false);
	}

	public MerfolkSpyAbility(final MerfolkSpyAbility ability) {
		super(ability);
	}

	@Override
	public MerfolkSpyAbility copy() {
		return new MerfolkSpyAbility(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == EventType.DAMAGED_PLAYER && event.getSourceId().equals(this.sourceId)) {
			this.addTarget(new TargetPlayer());
			this.targets.get(0).addTarget(event.getPlayerId(), this, game);
			trigger(game, event.getPlayerId());
			return true;
		}
		return false;
	}

	@Override
	public String getRule() {
		return "Whenever Merfolk Spy deals combat damage to a player, that player reveals a card at random from his or her hand";
	}

}

class MerfolkSpyEffect extends OneShotEffect<MerfolkSpyEffect> {

	public MerfolkSpyEffect() {
		super(Outcome.Detriment);
	}

	public MerfolkSpyEffect(final MerfolkSpyEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(source.getFirstTarget());
		if (player != null && player.getHand().size() > 0) {
			Cards revealed = new CardsImpl();
			revealed.add(player.getHand().getRandom(game));
			player.revealCards(revealed, game);
			return true;
		}
		return false;
	}

	@Override
	public MerfolkSpyEffect copy() {
		return new MerfolkSpyEffect(this);
	}

	@Override
	public String getText(Ability source) {
		return "";
	}
}