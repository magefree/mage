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

package mage.sets.magic2010;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.filter.Filter.ComparisonScope;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LokiX
 */
public class RoyalAssassin extends CardImpl<RoyalAssassin> {

    public RoyalAssassin(UUID onwerId){
        super(onwerId,"Royal Assassin", Rarity.RARE, new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.expansionSetCode = "M10";
        this.color.setBlack(true);
        this.subtype.add("Human");
        this.subtype.add("Assassin");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new RoyalAssassinAbility());
    }

	public RoyalAssassin(final RoyalAssassin card) {
		super(card);
	}

	@Override
	public RoyalAssassin copy() {
		return new RoyalAssassin(this);
	}

	@Override
	public String getArt() {
		return "48786_typ_reg_sty_010.jpg";
	}
}

class RoyalAssassinAbility extends ActivatedAbilityImpl<RoyalAssassinAbility> {

    private static FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creature");

	static {
		filter.setUseTapped(true);
		filter.setScopeColor(ComparisonScope.Any);
		filter.setTapped(true);
	}

    public RoyalAssassinAbility(){
        super(Zone.BATTLEFIELD, null);
        addTarget(new TargetCreaturePermanent(1, 1, filter, TargetController.ANY, false));
        addCost(new TapSourceCost());
        addEffect(new DestroyTargetEffect());
    }

	public RoyalAssassinAbility(final RoyalAssassinAbility ability) {
		super(ability);
	}

	@Override
	public RoyalAssassinAbility copy() {
		return new RoyalAssassinAbility(this);
	}

}
