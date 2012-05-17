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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.condition.common.OneControlledCreatureCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.permanent.token.Token;

import java.util.UUID;

/**
 * @author noxx
 */
public class DemonicRising extends CardImpl<DemonicRising> {

    private static final String ruleText = "At the beginning of your end step, if you control exactly one creature, put a 5/5 black Demon creature token with flying onto the battlefield";

    public DemonicRising(UUID ownerId) {
        super(ownerId, 94, "Demonic Rising", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");
        this.expansionSetCode = "AVR";

        this.color.setBlack(true);

        // At the beginning of your end step, if you control exactly one creature, put a 5/5 black Demon creature token with flying onto the battlefield.
        TriggeredAbility ability = new BeginningOfYourEndStepTriggeredAbility(new CreateTokenEffect(new DemonToken()), false);
        this.addAbility(new ConditionalTriggeredAbility(ability, OneControlledCreatureCondition.getInstance(), ruleText));
    }

    public DemonicRising(final DemonicRising card) {
        super(card);
    }

    @Override
    public DemonicRising copy() {
        return new DemonicRising(this);
    }
}

class DemonToken extends Token {
    public DemonToken() {
        super("Demon", "a 5/5 black Demon creature token with flying");
        cardType.add(Constants.CardType.CREATURE);
        color = ObjectColor.BLACK;
        subtype.add("Demon");
        power = new MageInt(5);
        toughness = new MageInt(5);
        addAbility(FlyingAbility.getInstance());
    }
}
