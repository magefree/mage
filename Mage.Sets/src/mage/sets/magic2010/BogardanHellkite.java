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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.target.common.TargetCreatureOrPlayerAmount;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class BogardanHellkite extends CardImpl<BogardanHellkite> {

	public BogardanHellkite(UUID ownerId) {
		super(ownerId, "Bogardan Hellkite", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{6}{R}{R}");
		this.expansionSetCode = "M10";
		this.subtype.add("Dragon");
		this.color.setRed(true);
		this.power = new MageInt(5);
		this.toughness = new MageInt(5);

		this.addAbility(FlashAbility.getInstance());
		this.addAbility(FlyingAbility.getInstance());
		Ability ability = new EntersBattlefieldTriggeredAbility(new DamageMultiEffect(5), false);
		ability.addTarget(new TargetCreatureOrPlayerAmount(5));
		this.addAbility(ability);
	}

	public BogardanHellkite(final BogardanHellkite card) {
		super(card);
	}

	@Override
	public BogardanHellkite copy() {
		return new BogardanHellkite(this);
	}

	@Override
	public String getArt() {
		return "97381_typ_reg_sty_010.jpg";
	}

}
