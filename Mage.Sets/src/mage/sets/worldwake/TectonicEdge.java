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
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.sets.Worldwake;
import mage.target.common.TargetNonBasicLandPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TectonicEdge extends CardImpl<TectonicEdge> {

	public TectonicEdge(UUID ownerId) {
		super(ownerId, "Tectonic Edge", new CardType[]{CardType.LAND}, null);
		this.expansionSetId = Worldwake.getInstance().getId();
		this.addAbility(new ColorlessManaAbility());
		Costs costs = new CostsImpl();
		costs.add(new TapSourceCost());
		costs.add(new SacrificeSourceCost());
		costs.add(new TectonicEdgeCost());
		Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), costs);
		ability.addTarget(new TargetNonBasicLandPermanent());
		ability.addManaCost(new GenericManaCost(1));
		this.addAbility(ability);
	}

	public TectonicEdge(final TectonicEdge card) {
		super(card);
	}

	@Override
	public TectonicEdge copy() {
		return new TectonicEdge(this);
	}

	@Override
	public String getArt() {
		return "126492_typ_reg_sty_010.jpg";
	}

}

class TectonicEdgeCost extends CostImpl<TectonicEdgeCost> {

	FilterLandPermanent filter = new FilterLandPermanent();

	public TectonicEdgeCost() {
		this.text = "Activate this ability only if an opponent controls four or more lands";
	}

	public TectonicEdgeCost(final TectonicEdgeCost cost) {
		super(cost);
	}

	@Override
	public TectonicEdgeCost copy() {
		return new TectonicEdgeCost(this);
	}

	@Override
	public boolean canPay(Ability source, Game game) {
		for (UUID opponentId: game.getOpponents(source.getControllerId())) {
			if (game.getBattlefield().countAll(filter, opponentId) > 3) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean pay(Game game, Ability source, boolean noMana) {
		this.paid = true;
		return paid;
	}

}
