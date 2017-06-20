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
package mage.cards.s;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public class ShowOfDominance extends CardImpl {

    public ShowOfDominance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Put four +1/+1 counters on the creature with the highest power. If two or more creatures are tied for the greatest power, you choose one of them. That creature gains trample.
        this.getSpellAbility().addEffect(new ShowOfDominanceEffect());
    }

    public ShowOfDominance(final ShowOfDominance card) {
        super(card);
    }

    @Override
    public ShowOfDominance copy() {
        return new ShowOfDominance(this);
    }
}

class ShowOfDominanceEffect extends OneShotEffect {

    public ShowOfDominanceEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Put four +1/+1 counters on the creature with the highest power. If two or more creatures are tied for the greatest power, you choose one of them. That creature gains trample until end of turn";
    }

    public ShowOfDominanceEffect(final ShowOfDominanceEffect effect) {
        super(effect);
    }

    @Override
    public ShowOfDominanceEffect copy() {
        return new ShowOfDominanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int highestPower = Integer.MIN_VALUE;
            Permanent selectedCreature = null;
            for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(), controller.getId(), game)) {
                if (highestPower < permanent.getPower().getValue()) {
                    highestPower = permanent.getPower().getValue();
                    selectedCreature = permanent;
                } else if (highestPower == permanent.getPower().getValue()) {
                    highestPower = permanent.getPower().getValue();
                    selectedCreature = null;
                }
            }
            if (highestPower != Integer.MIN_VALUE) {
                if (selectedCreature == null) {
                    FilterPermanent filter = new FilterCreaturePermanent("creature with power " + highestPower);
                    filter.add(new PowerPredicate(ComparisonType.EQUAL_TO, highestPower));
                    Target target = new TargetPermanent(1, 1, filter, true);
                    if (controller.chooseTarget(outcome, target, source, game)) {
                        selectedCreature = game.getPermanent(target.getFirstTarget());
                    }
                }
                if (selectedCreature != null) {
                    FixedTarget target = new FixedTarget(selectedCreature.getId());

                    Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(4));
                    effect.setTargetPointer(target);
                    effect.apply(game, source);

                    ContinuousEffect continuousEffect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
                    continuousEffect.setTargetPointer(target);
                    game.addEffect(continuousEffect, source);
                    return true;
                }
            }
            return true;
        }
        return false;
    }

}
