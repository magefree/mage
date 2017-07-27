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
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Styxo
 */
public class Dismantle extends CardImpl {

    public Dismantle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Destroy target artifact. If that artifact had counters on it, put that many +1/+1 counters or charge counters on an artifact you control.
        this.getSpellAbility().addEffect(new DismantleEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());

    }

    public Dismantle(final Dismantle card) {
        super(card);
    }

    @Override
    public Dismantle copy() {
        return new Dismantle(this);
    }

}

class DismantleEffect extends OneShotEffect {

    public DismantleEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target artifact. If that artifact had counters on it, put that many +1/+1 counters or charge counters on an artifact you control";

    }

    public DismantleEffect(final DismantleEffect effect) {
        super(effect);
    }

    @Override
    public DismantleEffect copy() {
        return new DismantleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null) {
            if (permanent != null) {
                int counterCount = 0;
                counterCount = permanent.getCounters(game).values().stream().map((counter) -> counter.getCount()).reduce(counterCount, Integer::sum);
                permanent.destroy(source.getSourceId(), game, false);
                if (counterCount > 0) {
                    Target target = new TargetControlledPermanent(1, 1, new FilterControlledArtifactPermanent("an artifact you control"), true);
                    if (target.canChoose(controller.getId(), game)) {
                        controller.chooseTarget(Outcome.Benefit, target, source, game);
                        Permanent artifact = game.getPermanent(target.getFirstTarget());
                        Counter counter;
                        if (controller.chooseUse(Outcome.BoostCreature, "What kind of counters do you want to add?", null, "+1/+1 counters", "Charge counters", source, game)) {
                            counter = CounterType.P1P1.createInstance(counterCount);
                        } else {
                            counter = CounterType.CHARGE.createInstance(counterCount);
                        }
                        if (artifact != null) {
                            artifact.addCounters(counter, source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

}
