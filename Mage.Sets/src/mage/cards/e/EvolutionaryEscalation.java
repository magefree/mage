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
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author spjspj
 */
public class EvolutionaryEscalation extends CardImpl {
    private static final FilterCreaturePermanent filterOpponentCreature = new FilterCreaturePermanent("creature an opponent controls");
    
    static {
        filterOpponentCreature.add(new ControllerPredicate(TargetController.OPPONENT));
    }    

    public EvolutionaryEscalation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // At the beginning of your upkeep, put three +1/+1 counters on target creature you control and three +1/+1 counters on target creature an opponent controls.
        EvolutionaryEscalationEffect effect = new EvolutionaryEscalationEffect();
        Ability ability = new BeginningOfUpkeepTriggeredAbility(effect, TargetController.YOU, false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addTarget(new TargetCreaturePermanent(filterOpponentCreature));
        this.addAbility(ability);
    }

    public EvolutionaryEscalation(final EvolutionaryEscalation card) {
        super(card);
    }

    @Override
    public EvolutionaryEscalation copy() {
        return new EvolutionaryEscalation(this);
    }
}

class EvolutionaryEscalationEffect extends OneShotEffect {

    public EvolutionaryEscalationEffect() {
        super(Outcome.BoostCreature);
        staticText = "put three +1/+1 counters on target creature you control and three +1/+1 counters on target creature an opponent controls";
    }

    public EvolutionaryEscalationEffect(final EvolutionaryEscalationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {        
        Counter counter = CounterType.P1P1.createInstance(3);
        boolean addedCounters = false;
        for (Target target: source.getTargets()) {
            Permanent targetPermanent = game.getPermanent(target.getFirstTarget());
            if (targetPermanent != null) {
                targetPermanent.addCounters(counter.copy(), source, game);
                addedCounters = true;
            }
        }
        return addedCounters;
    }

    @Override
    public EvolutionaryEscalationEffect copy() {
        return new EvolutionaryEscalationEffect(this);
    }


}
