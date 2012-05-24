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

package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.Filter.ComparisonScope;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ElspethKnightErrant extends CardImpl<ElspethKnightErrant> {

	private static SoldierToken soldierToken = new SoldierToken();

	public ElspethKnightErrant(UUID ownerId) {
		super(ownerId, 9, "Elspeth, Knight-Errant", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{W}");
		this.expansionSetCode = "ALA";
		this.subtype.add("Elspeth");
		this.color.setWhite(true);
		this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(4)), ""));

		this.addAbility(new LoyaltyAbility(new CreateTokenEffect(soldierToken), 1));

		Effects effects1 = new Effects();
		effects1.add(new BoostTargetEffect(3, 3, Duration.EndOfTurn));
		effects1.add(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn));
		LoyaltyAbility ability1 = new LoyaltyAbility(effects1, 1);
		ability1.addTarget(new TargetCreaturePermanent());
		this.addAbility(ability1);

		this.addAbility(new LoyaltyAbility(new ElspethKnightErrantEffect(), -8));

	}

	public ElspethKnightErrant(final ElspethKnightErrant card) {
		super(card);
	}

	@Override
	public ElspethKnightErrant copy() {
		return new ElspethKnightErrant(this);
	}

}

class ElspethKnightErrantEffect extends ContinuousEffectImpl<ElspethKnightErrantEffect> {

	private static final FilterPermanent filter = new FilterPermanent("artifacts, creatures, enchantments and lands");

	static {
		filter.getCardType().add(CardType.ARTIFACT);
		filter.getCardType().add(CardType.CREATURE);
		filter.getCardType().add(CardType.ENCHANTMENT);
		filter.getCardType().add(CardType.LAND);
		filter.setScopeCardType(ComparisonScope.Any);
	}

	public ElspethKnightErrantEffect() {
		super(Duration.EndOfGame, Layer.RulesEffects, SubLayer.NA, Outcome.AddAbility);
		staticText = "For the rest of the game artifacts, creature, enchantments and lands you control are indestructible";
	}

	public ElspethKnightErrantEffect(final ElspethKnightErrantEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		for (Permanent perm: game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
			perm.addAbility(IndestructibleAbility.getInstance(), game);
		}
		return true;
	}

	@Override
	public ElspethKnightErrantEffect copy() {
		return new ElspethKnightErrantEffect(this);
	}

}