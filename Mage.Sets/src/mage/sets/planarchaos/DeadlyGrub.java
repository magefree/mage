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
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.LastTimeCounterRemovedCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.abilities.keyword.VanishingSacrificeAbility;
import mage.abilities.keyword.VanishingUpkeepAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.game.permanent.token.Token;

/**
 *
 * @author LoneFox
 */
public class DeadlyGrub extends CardImpl {

    public DeadlyGrub(UUID ownerId) {
        super(ownerId, 69, "Deadly Grub", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "PLC";
        this.subtype.add("Insect");
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Vanishing 3
        Ability ability = new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.TIME.createInstance(3)));
        ability.setRuleVisible(false);
        this.addAbility(ability);
        this.addAbility(new VanishingUpkeepAbility(3));
        this.addAbility(new VanishingSacrificeAbility());
        // When Deadly Grub dies, if it had no time counters on it, put a 6/1 green Insect creature token with shroud onto the battlefield.
        this.addAbility(new ConditionalTriggeredAbility(new DiesTriggeredAbility(new CreateTokenEffect(new DeadlyGrubToken(), 1)),
            new LastTimeCounterRemovedCondition(), "When {this} dies, if it had no time counters on it, put a 6/1 green Insect creature token with shroud onto the battlefield."));
    }

    public DeadlyGrub(final DeadlyGrub card) {
        super(card);
    }

    @Override
    public DeadlyGrub copy() {
        return new DeadlyGrub(this);
    }
}

class DeadlyGrubToken extends Token {
    DeadlyGrubToken() {
        super("Insect", "6/1 green Insect creature token with shroud");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add("Insect");
        power = new MageInt(6);
        toughness = new MageInt(1);
        this.addAbility(ShroudAbility.getInstance());
    }
}
