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

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DemonOfDeathsGate extends CardImpl<DemonOfDeathsGate> {

	public DemonOfDeathsGate(UUID ownerId) {
		super(ownerId, 92, "Demon of Death's Gate", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{6}{B}{B}{B}");
		this.expansionSetCode = "M11";
		this.subtype.add("Demon");
		this.color.setBlack(true);
		this.power = new MageInt(9);
		this.toughness = new MageInt(9);
		this.getSpellAbility().addAlternativeCost(new DemonOfDeathsGateAlternativeCost());

		this.addAbility(FlyingAbility.getInstance());
		this.addAbility(TrampleAbility.getInstance());
	}

	public DemonOfDeathsGate(final DemonOfDeathsGate card) {
		super(card);
	}

	@Override
	public DemonOfDeathsGate copy() {
		return new DemonOfDeathsGate(this);
	}

}

class DemonOfDeathsGateAlternativeCost extends AlternativeCostImpl<DemonOfDeathsGateAlternativeCost> {
	private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("black creature");

	static {
		filter.getColor().setBlack(true);
		filter.setUseColor(true);
	}

	public DemonOfDeathsGateAlternativeCost() {
		super("pay 6 life and sacrifice three black creatures");
		this.add(new PayLifeCost(6));
		this.add(new SacrificeTargetCost(new TargetControlledPermanent(3, 3, filter, false)));
	}

	public DemonOfDeathsGateAlternativeCost(final DemonOfDeathsGateAlternativeCost cost) {
		super(cost);
	}

	@Override
	public DemonOfDeathsGateAlternativeCost copy() {
		return new DemonOfDeathsGateAlternativeCost(this);
	}

	@Override
	public String getText() {
		return "You may pay 6 life and sacrifice three black creatures rather than pay Demon of Death's Gate's mana cost";
	}

}