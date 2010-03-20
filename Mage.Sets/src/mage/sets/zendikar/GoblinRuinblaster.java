/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.sets.zendikar;


import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.ColoredManaSymbol;
import mage.MageInt;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.target.common.TargetNonBasicLandPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GoblinRuinblaster extends CardImpl {

	public GoblinRuinblaster(UUID ownerId) {
		super(ownerId, "Goblin Ruinblaster", new CardType[]{CardType.CREATURE}, "{2}{R}");
		this.color.setRed(true);
		this.subtype.add("Goblin");
		this.subtype.add("Shaman");
		this.art = "123599_typ_reg_sty_010.jpg";
		this.power = new MageInt(2);
		this.toughness = new MageInt(1);
		this.addAbility(HasteAbility.getInstance());
		KickerAbility ability = new KickerAbility(new EntersBattlefieldEffect(new DestroyTargetEffect()), false);
		ability.getTargets().add(new TargetNonBasicLandPermanent());
		ability.getCosts().add(new ColoredManaCost(ColoredManaSymbol.R));
		this.addAbility(ability);

	}
}

