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
package mage.sets.lorwyn;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public class EyesOfTheWisent extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a blue spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public EyesOfTheWisent(UUID ownerId) {
        super(ownerId, 210, "Eyes of the Wisent", Rarity.RARE, new CardType[]{CardType.TRIBAL, CardType.ENCHANTMENT}, "{1}{G}");
        this.expansionSetCode = "LRW";
        this.subtype.add("Elemental");

        // Whenever an opponent casts a blue spell during your turn, you may put a 4/4 green Elemental creature token onto the battlefield.
        this.addAbility(new ConditionalTriggeredAbility(
                new SpellCastOpponentTriggeredAbility(new CreateTokenEffect(new EyesOfTheWisentElementalToken()), filter, true),
                new MyTurnCondition(),
                "Whenever an opponent casts a blue spell during your turn, you may put a 4/4 green Elemental creature token onto the battlefield."
        ));
    }

    public EyesOfTheWisent(final EyesOfTheWisent card) {
        super(card);
    }

    @Override
    public EyesOfTheWisent copy() {
        return new EyesOfTheWisent(this);
    }
}

class EyesOfTheWisentElementalToken extends Token {

    public EyesOfTheWisentElementalToken() {
        super("Elemental", "4/4 green Elemental creature token");
        this.setOriginalExpansionSetCode("MMA");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add("Elemental");
        power = new MageInt(4);
        toughness = new MageInt(4);
        setTokenType(1);
    }

}
