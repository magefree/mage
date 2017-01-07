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
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class HarnessedLightning extends CardImpl {

    public HarnessedLightning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        // Choose target creature.  You get {E}{E}{E}, then you may pay any amount of {E}. Harnessed Lightning deals that much damage to that creature.
        this.getSpellAbility().addEffect(new HarnessedLightningEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public HarnessedLightning(final HarnessedLightning card) {
        super(card);
    }

    @Override
    public HarnessedLightning copy() {
        return new HarnessedLightning(this);
    }
}

class HarnessedLightningEffect extends OneShotEffect {

    public HarnessedLightningEffect() {
        super(Outcome.UnboostCreature);
        this.staticText = "Choose target creature.  You get {E}{E}{E}, then you may pay any amount of {E}. Harnessed Lightning deals that much damage to that creature";
    }

    public HarnessedLightningEffect(final HarnessedLightningEffect effect) {
        super(effect);
    }

    @Override
    public HarnessedLightningEffect copy() {
        return new HarnessedLightningEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            new GetEnergyCountersControllerEffect(3).apply(game, source);
            int numberToPay = controller.getAmount(0, controller.getCounters().getCount(CounterType.ENERGY), "How many {E} do you like to pay?", game);
            if (numberToPay > 0) {
                Cost cost = new PayEnergyCost(numberToPay);
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), true)) {
                    Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
                    if (targetCreature != null) {
                        targetCreature.damage(numberToPay, source.getSourceId(), game, false, true);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
