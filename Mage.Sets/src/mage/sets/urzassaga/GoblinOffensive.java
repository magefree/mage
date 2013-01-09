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
package mage.sets.urzassaga;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.game.permanent.token.Token;

/**
 *
 * @author jonubuu
 */
public class GoblinOffensive extends CardImpl<GoblinOffensive> {

    public GoblinOffensive(UUID ownerId) {
        super(ownerId, 192, "Goblin Offensive", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{X}{1}{R}{R}");
        this.expansionSetCode = "USG";

        this.color.setRed(true);

        // Put X 1/1 red Goblin creature tokens onto the battlefield.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new GoblinToken(), new ManacostVariableValue()));
    }

    public GoblinOffensive(final GoblinOffensive card) {
        super(card);
    }

    @Override
    public GoblinOffensive copy() {
        return new GoblinOffensive(this);
    }
}

class GoblinToken extends Token {
    public GoblinToken() {
        super("Goblin", "1/1 red Goblin creature token");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.RED;
        subtype.add("Goblin");
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}