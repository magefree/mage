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
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.AsTurnedFaceUpEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.token.SnakeToken;

/**
 *
 * @author LevelX2
 */
public class HoodedHydra extends CardImpl {

    public HoodedHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{X}{G}{G}");
        this.subtype.add("Snake");
        this.subtype.add("Hydra");

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Hooded Hydra enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));

        // When Hooded Hydra dies, create a 1/1 green Snake creature token for each +1/+1 counter on it.
        this.addAbility(new DiesTriggeredAbility(new CreateTokenEffect(new SnakeToken("KTK"), new CountersSourceCount(CounterType.P1P1)), false));

        // Morph {3}{G}{G}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{3}{G}{G}")));

        // As Hooded Hydra is turned face up, put five +1/+1 counters on it.
        Effect effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance(5));
        effect.setText("put five +1/+1 counters on it");
        // TODO: Does not work because the ability is still removed from permanent while the effect checks if the ability still exists.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new AsTurnedFaceUpEffect(effect, false));
        ability.setWorksFaceDown(true);
        this.addAbility(ability);
    }

    public HoodedHydra(final HoodedHydra card) {
        super(card);
    }

    @Override
    public HoodedHydra copy() {
        return new HoodedHydra(this);
    }
}
