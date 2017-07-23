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
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public class HunterOfEyeblights extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("creature you don't control");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature with a counter on it");

    static {
        filter1.add(new ControllerPredicate(TargetController.NOT_YOU));
        filter2.add(new CounterAnyPredicate());

    }

    public HunterOfEyeblights(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add("Elf");
        this.subtype.add("Assassin");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Hunter of Eyeblights enters the battlefield, put a +1/+1 counter on target creature you don't control
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent(filter1));
        this.addAbility(ability);

        //{B}{2},{T}: Destroy target creature with a counter on it.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl("{2}{B}"));
        ability2.addCost(new TapSourceCost());
        ability2.addTarget(new TargetCreaturePermanent(filter2));
        this.addAbility(ability2);
    }

    public HunterOfEyeblights(final HunterOfEyeblights card) {
        super(card);
    }

    @Override
    public HunterOfEyeblights copy() {
        return new HunterOfEyeblights(this);
    }
}
