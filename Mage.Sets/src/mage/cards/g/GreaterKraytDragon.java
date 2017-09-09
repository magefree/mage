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
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author Styxo
 */
public class GreaterKraytDragon extends CardImpl {

    public GreaterKraytDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{G}{W}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // {X}{X}{R}{G}{W}: Monstrosity X.
        this.addAbility(new MonstrosityAbility("{X}{X}{R}{G}{W}", Integer.MAX_VALUE));

        //  When Greater Krayt Dragon becomes monstrous, draw a card for each +1/+1 counter on creatures you control.
        this.addAbility(new BecomesMonstrousSourceTriggeredAbility(new DrawCardSourceControllerEffect(new CountersCount(CounterType.P1P1, new FilterControlledCreaturePermanent()))));

    }

    public GreaterKraytDragon(final GreaterKraytDragon card) {
        super(card);
    }

    @Override
    public GreaterKraytDragon copy() {
        return new GreaterKraytDragon(this);
    }
}
