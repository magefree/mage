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
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.GraftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author emerald000
 */
public class CytoplastRootKin extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("other creature you control that has a +1/+1 counter on it");
    static {
        filter.add(new AnotherPredicate());
        filter.add(new CounterPredicate(CounterType.P1P1));
    }

    public CytoplastRootKin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add("Elemental");
        this.subtype.add("Mutant");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Graft 4
        this.addAbility(new GraftAbility(this, 4));
        
        // When Cytoplast Root-Kin enters the battlefield, put a +1/+1 counter on each other creature you control that has a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)));
        
        // {2}: Move a +1/+1 counter from target creature you control onto Cytoplast Root-Kin.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CytoplastRootKinEffect(), new GenericManaCost(2));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    public CytoplastRootKin(final CytoplastRootKin card) {
        super(card);
    }

    @Override
    public CytoplastRootKin copy() {
        return new CytoplastRootKin(this);
    }
}

class CytoplastRootKinEffect extends OneShotEffect {

    CytoplastRootKinEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Move a +1/+1 counter from target creature you control onto Cytoplast Root-Kin";
    }

    CytoplastRootKinEffect(final CytoplastRootKinEffect effect) {
        super(effect);
    }

    @Override
    public CytoplastRootKinEffect copy() {
        return new CytoplastRootKinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent targetPermanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (sourcePermanent != null
                && targetPermanent != null
                && !sourcePermanent.getId().equals(targetPermanent.getId())
                && targetPermanent.getCounters(game).getCount(CounterType.P1P1) > 0) {
            targetPermanent.removeCounters(CounterType.P1P1.createInstance(), game);
            sourcePermanent.addCounters(CounterType.P1P1.createInstance(), game);
            return true;
        }
        return false;
    }
}
