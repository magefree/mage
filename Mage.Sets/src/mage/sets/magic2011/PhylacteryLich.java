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
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.AddCountersTargetEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.filter.Filter.ComparisonScope;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PhylacteryLich extends CardImpl<PhylacteryLich> {

	private static FilterControlledPermanent filter = new FilterControlledPermanent("artifact");

	static {
		filter.getCardType().add(CardType.ARTIFACT);
		filter.setScopeCardType(ComparisonScope.Any);
	}

	public PhylacteryLich(UUID ownerId) {
		super(ownerId, 110, "Phylactery Lich", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{B}{B}{B}");
		this.expansionSetCode = "M11";
		this.subtype.add("Zombie");
		this.color.setBlack(true);
		this.power = new MageInt(5);
		this.toughness = new MageInt(5);

		Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect("phylactery", 1), false);
		ability.addTarget(new TargetControlledPermanent(filter));
		this.addAbility(ability);
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

	@Override
	public String getArt() {
		return "129123_typ_reg_sty_010.jpg";
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
					return true;
			}
			return false;
		}
		
		@Override
		public String getRule() {
			return "When you control no permanents with phylactery counters on them, sacrifice Phylactery Lich.";
		}

	}

}