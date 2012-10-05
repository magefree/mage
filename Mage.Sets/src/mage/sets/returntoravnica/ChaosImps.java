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
package mage.sets.returntoravnica;
 
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.HasCounterCondition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.UnleashAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
 
/**
 *
 * @author LevelX2
 */
public class ChaosImps extends CardImpl<ChaosImps> {
 
    public ChaosImps(UUID ownerId) {
        super(ownerId, 90, "Chaos Imps", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Imp");
 
        this.color.setRed(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);
 
        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Unleash (You may have this creature enter the battlefield with a +1/+1 counter on it. It can't block as long as it has a +1/+1 counter on it.)
        this.addAbility(new UnleashAbility());
        
        // Chaos Imps has trample as long as it has a +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, 
                new ConditionalContinousEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance()), 
                new HasCounterCondition(CounterType.P1P1),"Chaos Imps has trample as long as it has a +1/+1 counter on it")));
        
    }
 
    public ChaosImps(final ChaosImps card) {
        super(card);
    }
 
    @Override
    public ChaosImps copy() {
        return new ChaosImps(this);
    }
}