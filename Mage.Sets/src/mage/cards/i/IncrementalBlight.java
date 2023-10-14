
 /*  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class IncrementalBlight extends CardImpl {

    public IncrementalBlight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");


        // Put a -1/-1 counter on target creature, two -1/-1 counters on another target creature, and three -1/-1 counters on a third target creature.
        this.getSpellAbility().addEffect(new IncrementalBlightEffect());
        
        FilterCreaturePermanent filter1 = new FilterCreaturePermanent("creature (gets a -1/-1 counter)");
        TargetCreaturePermanent target1 = new TargetCreaturePermanent(filter1);
        target1.setTargetTag(1);
        this.getSpellAbility().addTarget(target1);
        
        FilterCreaturePermanent filter2 = new FilterCreaturePermanent("another creature (gets two -1/-1 counters)");
        filter2.add(new AnotherTargetPredicate(2));
        TargetCreaturePermanent target2 = new TargetCreaturePermanent(filter2);
        target2.setTargetTag(2);
        this.getSpellAbility().addTarget(target2);
        
        FilterCreaturePermanent filter3 = new FilterCreaturePermanent("another creature (gets three -1/-1 counters)");
        filter3.add(new AnotherTargetPredicate(3));
        TargetCreaturePermanent target3 = new TargetCreaturePermanent(filter3);
        target3.setTargetTag(3);
        this.getSpellAbility().addTarget(target3);
    }

    private IncrementalBlight(final IncrementalBlight card) {
        super(card);
    }

    @Override
    public IncrementalBlight copy() {
        return new IncrementalBlight(this);
    }
}
class IncrementalBlightEffect extends OneShotEffect {
   
    public IncrementalBlightEffect() {
        super(Outcome.UnboostCreature);
        this.staticText = "Put a -1/-1 counter on target creature, two -1/-1 counters on another target creature, and three -1/-1 counters on a third target creature";
    }
   
    private IncrementalBlightEffect(final IncrementalBlightEffect effect) {
        super(effect);
    }
   
    @Override
    public IncrementalBlightEffect copy() {
        return new IncrementalBlightEffect(this);
    }
   
    @Override
    public boolean apply(Game game, Ability source) {
        int i = 0;
        for (Target target : source.getTargets()) {
            i++;
            Permanent creature = game.getPermanent(target.getFirstTarget());
            if (creature != null) {
                creature.addCounters(CounterType.M1M1.createInstance(i), source.getControllerId(), source, game);
            }
        }
        return false;
    }
}