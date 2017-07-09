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
package mage.cards.j;

import java.util.UUID;

import mage.constants.*;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MultikickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author jeffwadsworth
 */
public class JoragaWarcaller extends CardImpl {
    
    private static final String rule = "Other Elf creatures you control get +1/+1 for each +1/+1 counter on Joraga Warcaller";
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("elf creatures you control");
    
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new SubtypePredicate(SubType.ELF));
        filter.add(new AnotherPredicate());
    }

    public JoragaWarcaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add("Elf");
        this.subtype.add("Warrior");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Multikicker {1}{G}
        this.addAbility(new MultikickerAbility("{1}{G}"));
        
        // Joraga Warcaller enters the battlefield with a +1/+1 counter on it for each time it was kicked.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(0), new MultikickerCount(), true),
                "with a +1/+1 counter on it for each time it was kicked"));

        
        // Other Elf creatures you control get +1/+1 for each +1/+1 counter on Joraga Warcaller.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, 
                new BoostAllEffect( new CountersSourceCount(CounterType.P1P1), new CountersSourceCount(CounterType.P1P1), Duration.WhileOnBattlefield, filter, true, rule)));
        
    }

    public JoragaWarcaller(final JoragaWarcaller card) {
        super(card);
    }

    @Override
    public JoragaWarcaller copy() {
        return new JoragaWarcaller(this);
    }
}