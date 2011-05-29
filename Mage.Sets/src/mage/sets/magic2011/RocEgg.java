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
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.permanent.token.Token;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class RocEgg extends CardImpl<RocEgg> {

	private static RocEggToken rocEggToken = new RocEggToken();

	public RocEgg(UUID ownerId) {
		super(ownerId, 25, "Roc Egg", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{W}");
		this.expansionSetCode = "M11";
		this.subtype.add("Bird");
		this.color.setWhite(true);
		this.power = new MageInt(0);
		this.toughness = new MageInt(3);

		this.addAbility(DefenderAbility.getInstance());
		this.addAbility(new PutIntoGraveFromBattlefieldTriggeredAbility(new CreateTokenEffect(rocEggToken, 1), false));
	}

	public RocEgg(final RocEgg card) {
		super(card);
	}

	@Override
	public RocEgg copy() {
		return new RocEgg(this);
	}

}
class RocEggToken extends Token {

	public RocEggToken() {
		super("Bird", "3/3 white Bird creature token with flying");
		cardType.add(CardType.CREATURE);
		subtype.add("Bird");
		color.setWhite(true);
		power = new MageInt(3);
		toughness = new MageInt(3);
		addAbility(FlyingAbility.getInstance());
	}
}
