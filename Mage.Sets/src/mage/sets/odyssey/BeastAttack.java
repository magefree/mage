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
package mage.sets.odyssey;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TimingRule;
import mage.game.permanent.token.Token;

/**
 *
 * @author cbt33
 */
public class BeastAttack extends CardImpl<BeastAttack> {

    public BeastAttack(UUID ownerId) {
        super(ownerId, 230, "Beast Attack", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{G}{G}{G}");
        this.expansionSetCode = "ODY";

        this.color.setGreen(true);

        // Put a 4/4 green Beast creature token onto the battlefield.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BeastToken_44()));
        
        // Flashback {2}{G}{G}{G}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{2}{G}{G}{G}"), TimingRule.INSTANT));
    }

    public BeastAttack(final BeastAttack card) {
        super(card);
    }

    @Override
    public BeastAttack copy() {
        return new BeastAttack(this);
    }
}

class BeastToken_44 extends Token {
    
  public BeastToken_44() {
        super("Beast", "4/4 green Beast creature token");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.GREEN;
        subtype.add("Beast");
        power = new MageInt(4);
        toughness = new MageInt(4);
        // to get an image for the token
        this.setOriginalExpansionSetCode("ZEN");
    }
        
}