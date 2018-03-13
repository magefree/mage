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
import mage.abilities.Ability;import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.DeathtouchRatToken;
import mage.target.Target;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Saga
 */
public class HungryLynx extends CardImpl {
    
    private final static FilterControlledCreaturePermanent filterCat = new FilterControlledCreaturePermanent("Cats");
    static {
        filterCat.add(new SubtypePredicate(SubType.CAT));
    }
    
    private static final FilterCard filterProRat = new FilterCard("Rats");
    static {
        filterProRat.add(new SubtypePredicate(SubType.RAT));
    }
    
    private final static FilterCreaturePermanent filterRat = new FilterCreaturePermanent("a Rat");
    static {
        filterRat.add(new SubtypePredicate(SubType.RAT));
    }
    
    public HungryLynx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Cats you control have protection from Rats.
        Effect effect = new GainAbilityAllEffect(new ProtectionAbility(filterProRat), Duration.WhileOnBattlefield, filterCat);
        effect.setText("Cats you control have protection from Rats. <i>(They can't be blocked, targeted, or dealt damage by Rats.)</i>");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        
        // At the beginning of your end step, target opponent creates a 1/1 black Rat creature token with deathtouch. 
        Ability ability = new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new CreateTokenTargetEffect(new DeathtouchRatToken()), TargetController.YOU, null, false);
        Target target = new TargetOpponent();
        ability.addTarget(target);
        this.addAbility(ability);
        
        // Whenever a Rat dies, put a +1/+1 counter on each Cat you control. 
        Effect effect2 = new AddCountersAllEffect(CounterType.P1P1.createInstance(), filterCat);
        effect2.setText("put a +1/+1 counter on each Cat you control");
        this.addAbility(new DiesCreatureTriggeredAbility(effect2, false, filterRat));
    }

    public HungryLynx(final HungryLynx card) {
        super(card);
    }

    @Override
    public HungryLynx copy() {
        return new HungryLynx(this);
    }
}
