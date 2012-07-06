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
package mage.sets.magic2013;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.ControllerLifeCount;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public class AjaniCallerOfThePride extends CardImpl<AjaniCallerOfThePride> {

    public AjaniCallerOfThePride(UUID ownerId) {
        super(ownerId, 1, "Ajani, Caller of the Pride", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{1}{W}{W}");
        this.expansionSetCode = "M13";
        this.subtype.add("Ajani");

        this.color.setWhite(true);
    }

    @Override
    public void build() {
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(4)), ""));
        // +1: Put a +1/+1 counter on up to one target creature.
        Ability ability = new LoyaltyAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), 1);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        // -3: Target creature gains flying and double strike until end of turn.
        Effects effects = new Effects();
        effects.add(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Constants.Duration.EndOfTurn));
        effects.add(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Constants.Duration.EndOfTurn));
        ability = new LoyaltyAbility(effects, -3);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        // -8: Put X 2/2 white Cat creature tokens onto the battlefield, where X is your life total.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new CatToken(), new ControllerLifeCount()), -8));
    }

    public AjaniCallerOfThePride(final AjaniCallerOfThePride card) {
        super(card);
    }

    @Override
    public AjaniCallerOfThePride copy() {
        return new AjaniCallerOfThePride(this);
    }
}

class CatToken extends Token {

    public CatToken() {
        super("Cat", "2/2 white Cat creature tokens");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.WHITE;
        subtype.add("Cat");
        power = new MageInt(2);
        toughness = new MageInt(2);
    }
}