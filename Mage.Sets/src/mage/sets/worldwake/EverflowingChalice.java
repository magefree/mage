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

package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.keyword.MultikickerAbility;
import mage.abilities.mana.ManaAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.counters.common.ChargeCounter;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class EverflowingChalice extends CardImpl<EverflowingChalice> {

	public EverflowingChalice(UUID ownerId) {
		super(ownerId, 123, "Everflowing Chalice", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{0}");
		this.expansionSetCode = "WWK";
		Ability ability1 = new EntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(new ChargeCounter()));
		MultikickerAbility ability = new MultikickerAbility(new GainAbilitySourceEffect(ability1, Duration.WhileOnBattlefield), false);
		ability.addManaCost(new GenericManaCost(2));
		this.addAbility(ability);
		this.addAbility(new EverflowingChaliceAbility());
	}

	public EverflowingChalice(final EverflowingChalice card) {
		super(card);
	}

	@Override
	public EverflowingChalice copy() {
		return new EverflowingChalice(this);
	}

}

class EverflowingChaliceAbility extends ManaAbility<EverflowingChaliceAbility> {

	public EverflowingChaliceAbility() {
		super(Zone.BATTLEFIELD, new EverflowingChaliceEffect(), new TapSourceCost());
	}

	public EverflowingChaliceAbility(final EverflowingChaliceAbility ability) {
		super(ability);
	}

	@Override
	public EverflowingChaliceAbility copy() {
		return new EverflowingChaliceAbility(this);
	}

	@Override
	public Mana getNetMana(Game game) {
		if (game == null)
			return new Mana();
		return Mana.ColorlessMana(game.getPermanent(this.getSourceId()).getCounters().getCount(CounterType.CHARGE));
	}

}

class EverflowingChaliceEffect extends ManaEffect {

	public EverflowingChaliceEffect() {
		super(new Mana());
	}

	public EverflowingChaliceEffect(final EverflowingChaliceEffect effect) {
		super(effect);
	}

	@Override
	public EverflowingChaliceEffect copy() {
		return new EverflowingChaliceEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		this.mana.clear();
		this.mana.setColorless(game.getPermanent(source.getSourceId()).getCounters().getCount(CounterType.CHARGE));
		return super.apply(game, source);
	}

	@Override
	public String getText(Ability source) {
		return "Add {1} to your mana pool for each charge counter on {this}";
	}

}