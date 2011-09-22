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

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayVariableLoyaltyCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.DamageAllControlledTargetEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com, nantuko
 */
public class ChandraNalaar extends CardImpl<ChandraNalaar> {

	public ChandraNalaar(UUID ownerId) {
		super(ownerId, 132, "Chandra Nalaar", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{3}{R}{R}");
		this.expansionSetCode = "M10";
		this.subtype.add("Chandra");
		this.color.setRed(true);
		this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(6)), ""));


		LoyaltyAbility ability1 = new LoyaltyAbility(new DamageTargetEffect(1), 1);
		ability1.addTarget(new TargetPlayer());
		this.addAbility(ability1);

		LoyaltyAbility ability2 = new LoyaltyAbility(new DamageTargetEffect(ChandraNalaarXValue.getDefault()));
		ability2.addTarget(new TargetCreaturePermanent());
		this.addAbility(ability2);

		Effects effects1 = new Effects();
		effects1.add(new DamageTargetEffect(10));
		effects1.add(new DamageAllControlledTargetEffect(10, FilterCreaturePermanent.getDefault()));
		LoyaltyAbility ability3 = new LoyaltyAbility(effects1, -8);
		ability3.addTarget(new TargetPlayer());
		this.addAbility(ability3);
	}

	public ChandraNalaar(final ChandraNalaar card) {
		super(card);
	}

	@Override
	public ChandraNalaar copy() {
		return new ChandraNalaar(this);
	}
}

class ChandraNalaarXValue implements DynamicValue {

    private static final ChandraNalaarXValue defaultValue = new ChandraNalaarXValue();

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost instanceof PayVariableLoyaltyCost) {
                return ((PayVariableLoyaltyCost)cost).getAmount();
            }
        }
        return 0;
    }

    @Override
    public DynamicValue clone() {
        return defaultValue;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "X";
    }

    public static ChandraNalaarXValue getDefault() {
        return defaultValue;
    }
}

