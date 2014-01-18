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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public class SatyrNyxSmith extends CardImpl<SatyrNyxSmith> {

    public SatyrNyxSmith(UUID ownerId) {
        super(ownerId, 109, "Satyr Nyx-Smith", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Satyr");
        this.subtype.add("Shaman");

        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // <i>Inspired</i> - Whenever Satyr Nyx-Smith becomes untapped, you may pay {2}{R}. If you do, put a 3/1 red Elemental enchantment creature token with haste onto the battlefield.
        this.addAbility(new InspiredAbility(new DoIfCostPaid(new CreateTokenEffect(new SatyrNyxSmithElementalToken()), new ManaCostsImpl("{2}{R}"))));

    }

    public SatyrNyxSmith(final SatyrNyxSmith card) {
        super(card);
    }

    @Override
    public SatyrNyxSmith copy() {
        return new SatyrNyxSmith(this);
    }
}

class SatyrNyxSmithElementalToken extends Token {

    public SatyrNyxSmithElementalToken() {
        super("Elemental", "3/1 red Elemental enchantment creature token with haste");
        cardType.add(CardType.ENCHANTMENT);
        cardType.add(CardType.CREATURE);
        color = ObjectColor.RED;
        subtype.add("Elemental");
        power = new MageInt(3);
        toughness = new MageInt(1);
        this.addAbility(HasteAbility.getInstance());
        this.setOriginalExpansionSetCode("BNG");
    }
}
