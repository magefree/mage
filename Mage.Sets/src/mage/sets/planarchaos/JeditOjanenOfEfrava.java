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
package mage.sets.planarchaos;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.game.permanent.token.Token;

/**
 *
 * @author fireshoes
 */
public class JeditOjanenOfEfrava extends CardImpl {

    public JeditOjanenOfEfrava(UUID ownerId) {
        super(ownerId, 131, "Jedit Ojanen of Efrava", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{G}{G}{G}");
        this.expansionSetCode = "PLC";
        this.supertype.add("Legendary");
        this.subtype.add("Cat");
        this.subtype.add("Warrior");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Forestwalk
        this.addAbility(new ForestwalkAbility());
        
        // Whenever Jedit Ojanen of Efrava attacks or blocks, put a 2/2 green Cat Warrior creature token with forestwalk onto the battlefield.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new CreateTokenEffect(new CatWarriorToken()), false));
    }

    public JeditOjanenOfEfrava(final JeditOjanenOfEfrava card) {
        super(card);
    }

    @Override
    public JeditOjanenOfEfrava copy() {
        return new JeditOjanenOfEfrava(this);
    }
}

class CatWarriorToken extends Token {

    CatWarriorToken() {
        super("Cat Warrior", "2/2 green Cat Warrior creature token with forestwalk");
        this.setOriginalExpansionSetCode("PLC");
        this.getPower().initValue(2);
        this.getToughness().initValue(2);
        this.color.setGreen(true);
        this.getSubtype().add("Cat");
        this.getSubtype().add("Warrior");
        this.getCardType().add(CardType.CREATURE);
        this.addAbility(new ForestwalkAbility());
    }
}