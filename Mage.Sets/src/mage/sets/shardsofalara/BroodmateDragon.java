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

package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.permanent.token.DragonToken;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class BroodmateDragon extends CardImpl<BroodmateDragon> {

	private static DragonToken dragonToken = new DragonToken();

	public BroodmateDragon(UUID ownerId) {
		super(ownerId, 160, "Broodmate Dragon", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}{R}{G}");
		this.expansionSetCode = "ALA";
		this.subtype.add("Dragon");
		this.color.setRed(true);
		this.color.setGreen(true);
		this.color.setBlack(true);
		this.power = new MageInt(4);
		this.toughness = new MageInt(4);

		this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(dragonToken), false));
		this.addAbility(FlyingAbility.getInstance());
	}

	public BroodmateDragon(final BroodmateDragon card) {
		super(card);
	}

	@Override
	public BroodmateDragon copy() {
		return new BroodmateDragon(this);
	}

}
