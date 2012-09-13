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

package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.game.permanent.token.Token;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class RampagingBaloths extends CardImpl<RampagingBaloths> {

    public RampagingBaloths(UUID ownerId) {
        super(ownerId, 178, "Rampaging Baloths", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Beast");
        this.color.setGreen(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(new LandfallAbility(new CreateTokenEffect(new RampagingBalothsToken()), true));
    }

    public RampagingBaloths(final RampagingBaloths card) {
        super(card);
    }

    @Override
    public RampagingBaloths copy() {
        return new RampagingBaloths(this);
    }

}

class RampagingBalothsToken extends Token {

    public RampagingBalothsToken() {
        super("Beast", "4/4 green Beast creature token");
        cardType.add(CardType.CREATURE);
        subtype.add("Beast");
        this.color.setGreen(true);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }
}