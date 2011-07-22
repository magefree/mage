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
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.counters.Counter;
import mage.filter.Filter.ComparisonScope;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PhylacteryLich extends CardImpl<PhylacteryLich> {

	public PhylacteryLich(UUID ownerId) {
		super(ownerId, 110, "Phylactery Lich", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{B}{B}{B}");
		this.expansionSetCode = "M11";
		this.subtype.add("Zombie");
		this.color.setBlack(true);
		this.power = new MageInt(5);
		this.toughness = new MageInt(5);

		this.addAbility(new EntersBattlefieldAbility(new PhylacteryLichEffect(), "put a phylactery counter on an artifact you control"));
		this.addAbility(IndestructibleAbility.getInstance());
		this.addAbility(new PhylacteryLichAbility());
	}

	public PhylacteryLich(final PhylacteryLich card) {
		super(card);
	}

	@Override
	public PhylacteryLich copy() {
		return new PhylacteryLich(this);
	}

	class PhylacteryLichAbility extends StateTriggeredAbility<PhylacteryLichAbility> {

		public PhylacteryLichAbility() {
			super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
		}

		public PhylacteryLichAbility(final PhylacteryLichAbility ability) {
			super(ability);
		}

		@Override
		public PhylacteryLichAbility copy() {
			return new PhylacteryLichAbility(this);
		}

		@Override
		public boolean checkTrigger(GameEvent event, Game game) {
			for (Permanent perm: game.getBattlefield().getAllActivePermanents(controllerId)) {
				if (perm.getCounters().getCount("phylactery") > 0)
					return false;
			}
			return true;
		}
		
		@Override
		public String getRule() {
			return "When you control no permanents with phylactery counters on them, sacrifice {this}.";
		}

	}

}

class PhylacteryLichEffect extends OneShotEffect<PhylacteryLichEffect> {

	private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifact");

	static {
		filter.getCardType().add(CardType.ARTIFACT);
		filter.setScopeCardType(ComparisonScope.Any);
	}

	public PhylacteryLichEffect() {
		super(Outcome.Neutral);
		staticText = "put a phylactery counter on an artifact you control";
	}

	public PhylacteryLichEffect(final PhylacteryLichEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(source.getControllerId());
		if (player != null) {
			TargetControlledPermanent target = new TargetControlledPermanent(filter);
			if (target.canChoose(source.getControllerId(), game)) {
				target.setRequired(true);
				if (player.choose(Outcome.Neutral, target, game)) {
					Permanent permanent = game.getPermanent(target.getFirstTarget());
					if (permanent != null) {
						permanent.addCounters(new Counter("phylactery"));
					}
				}
			}
		}
		return false;
	}

	@Override
	public PhylacteryLichEffect copy() {
		return new PhylacteryLichEffect(this);
	}

}