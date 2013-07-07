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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class NayaSojourners extends CardImpl<NayaSojourners> {

    public NayaSojourners(UUID ownerId) {
        super(ownerId, 122, "Naya Sojourners", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{R}{G}{W}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Elf");
        this.subtype.add("Shaman");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // When you cycle Naya Sojourners or it dies, you may put a +1/+1 counter on target creature.
        Ability ability1 = new CycleTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        Ability ability2 = new DiesTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability1.addTarget(new TargetCreaturePermanent());
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability1);
        this.addAbility(ability2);
        
        // Cycling {2}{G}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{2}{G}")));
    }

    public NayaSojourners(final NayaSojourners card) {
        super(card);
    }

    @Override
    public NayaSojourners copy() {
        return new NayaSojourners(this);
    }
}
