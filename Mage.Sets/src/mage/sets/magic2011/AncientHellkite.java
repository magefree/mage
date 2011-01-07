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
import mage.Constants.ColoredManaSymbol;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class AncientHellkite extends CardImpl<AncientHellkite> {

	public AncientHellkite(UUID ownerId) {
		super(ownerId, 122, "Ancient Hellkite", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{R}{R}{R}");
		this.expansionSetCode = "M11";
		this.subtype.add("Dragon");
		this.color.setRed(true);
		this.power = new MageInt(6);
		this.toughness = new MageInt(6);

		this.addAbility(FlyingAbility.getInstance());
		this.addAbility(new AncientHellkiteAbility());
	}

	public AncientHellkite(final AncientHellkite card) {
		super(card);
	}

	@Override
	public AncientHellkite copy() {
		return new AncientHellkite(this);
	}

	@Override
	public String getArt() {
		return "129075_typ_reg_sty_010.jpg";
	}

}

class AncientHellkiteAbility extends ActivatedAbilityImpl<AncientHellkiteAbility> {

	private FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");

	public AncientHellkiteAbility() {
		super(Zone.BATTLEFIELD, new DamageTargetEffect(1));
		addCost(new AncientHellkiteCost());
		addTarget(new TargetCreaturePermanent(filter));
		addManaCost(new ColoredManaCost(ColoredManaSymbol.R));
	}

	public AncientHellkiteAbility(final AncientHellkiteAbility ability) {
		super(ability);
		this.filter = ability.filter;
	}

	@Override
	public AncientHellkiteAbility copy() {
		return new AncientHellkiteAbility(this);
	}

	@Override
	public boolean activate(Game game, boolean noMana) {
		UUID defenderId = game.getCombat().getDefendingPlayer(sourceId);
		if (defenderId != null) {
			filter.getControllerId().clear();
			filter.getControllerId().add(defenderId);
			return super.activate(game, noMana);
		}
		return false;
	}
}

class AncientHellkiteCost extends CostImpl<AncientHellkiteCost> {

	public AncientHellkiteCost() {
		this.text = "Activate this ability only if Ancient Hellkite is attacking";
	}

	public AncientHellkiteCost(final AncientHellkiteCost cost) {
		super(cost);
	}

	@Override
	public AncientHellkiteCost copy() {
		return new AncientHellkiteCost(this);
	}

	@Override
	public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
		Permanent permanent = game.getPermanent(sourceId);
		if (permanent != null && permanent.isAttacking()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean pay(Game game, UUID sourceId, UUID controllerId, boolean noMana) {
		this.paid = true;
		return paid;
	}

}