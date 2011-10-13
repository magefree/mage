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
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.KickerManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.game.permanent.token.Token;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ConquerorsPledge extends CardImpl<ConquerorsPledge> {

	public ConquerorsPledge(UUID ownerId) {
		super(ownerId, 8, "Conqueror's Pledge", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{W}{W}{W}");
		this.expansionSetCode = "ZEN";
		this.color.setWhite(true);
        this.getSpellAbility().addOptionalCost(new KickerManaCost("{6}"));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new CreateTokenEffect(new KorSoldierToken(), 12),
                new CreateTokenEffect(new KorSoldierToken(), 6), KickedCondition.getInstance(),
                "Put six 1/1 white Kor Soldier creature tokens onto the battlefield. If {this} was kicked, put twelve of those tokens onto the battlefield instead"));
	}

	public ConquerorsPledge(final ConquerorsPledge card) {
		super(card);
	}

	@Override
	public ConquerorsPledge copy() {
		return new ConquerorsPledge(this);
	}

}

class KorSoldierToken extends Token {

	public KorSoldierToken() {
		super("Kor Soldier", "1/1 white Kor Soldier creature token");
		cardType.add(CardType.CREATURE);
		color.setWhite(true);
		subtype.add("Soldier");
		power = new MageInt(1);
		toughness = new MageInt(1);
	}

}
