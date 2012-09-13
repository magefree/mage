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
package mage.sets.scourge;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.game.permanent.token.Token;


/**
 *
 * @author Plopman
 */
public class HuntingPack extends CardImpl<HuntingPack> {

    public HuntingPack(UUID ownerId) {
        super(ownerId, 121, "Hunting Pack", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{5}{G}{G}");
        this.expansionSetCode = "SCG";

        this.color.setGreen(true);

        // Put a 4/4 green Beast creature token onto the battlefield.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new HuntingPackToken(), 1));
        // Storm
        this.addAbility(new StormAbility());
    }

    public HuntingPack(final HuntingPack card) {
        super(card);
    }

    @Override
    public HuntingPack copy() {
        return new HuntingPack(this);
    }
}

class HuntingPackToken extends Token {

    public HuntingPackToken() {
        super("Beast", "4/4 green Beast creature token");
        cardType.add(CardType.CREATURE);
        subtype.add("Beast");
        this.color.setGreen(true);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }
}
