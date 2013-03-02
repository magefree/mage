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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class Bioshift extends CardImpl<Bioshift> {

    public Bioshift(UUID ownerId) {
        super(ownerId, 214, "Bioshift", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{G/U}");
        this.expansionSetCode = "GTC";

        this.color.setGreen(true);
        this.color.setBlue(true);

        // Move any number of +1/+1 counters from target creature onto another target creature with the same controller.
        getSpellAbility().addEffect(new MoveCounterFromTargetToTargetEffect());
        getSpellAbility().addTarget(new TargetCreaturePermanentSameController(2,2,new FilterCreaturePermanent(),false));
    }

    public Bioshift(final Bioshift card) {
        super(card);
    }

    @Override
    public Bioshift copy() {
        return new Bioshift(this);
    }
}

class TargetCreaturePermanentSameController extends TargetCreaturePermanent {

    public TargetCreaturePermanentSameController(int minNumTargets, int maxNumTargets, FilterCreaturePermanent filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
        this.targetName = filter.getMessage();
    }

    public TargetCreaturePermanentSameController(final TargetCreaturePermanentSameController target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        UUID firstTarget = this.getFirstTarget();
        if (firstTarget != null) {
            Permanent permanent = game.getPermanent(firstTarget);
            Permanent targetPermanent = game.getPermanent(id);
            if (permanent == null || targetPermanent == null
                    || !permanent.getControllerId().equals(targetPermanent.getOwnerId())) {
                return false;
            }
        }
        return super.canTarget(id, source, game);
    }

    @Override
    public TargetCreaturePermanentSameController copy() {
        return new TargetCreaturePermanentSameController(this);
    }
}

class MoveCounterFromTargetToTargetEffect extends OneShotEffect<MoveCounterFromTargetToTargetEffect> {

    public MoveCounterFromTargetToTargetEffect() {
        super(Constants.Outcome.Detriment);
        this.staticText = "Move any number of +1/+1 counters from target creature onto another target creature with the same controller";
    }

    public MoveCounterFromTargetToTargetEffect(final MoveCounterFromTargetToTargetEffect effect) {
        super(effect);
    }

    @Override
    public MoveCounterFromTargetToTargetEffect copy() {
        return new MoveCounterFromTargetToTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent fromPermanent = game.getPermanent(targetPointer.getFirst(game, source));
        Permanent toPermanent = null;
        if (targetPointer.getTargets(game, source).size() > 1) {
            toPermanent = game.getPermanent(targetPointer.getTargets(game, source).get(1));
        }
        if (fromPermanent == null || toPermanent == null || !fromPermanent.getControllerId().equals(toPermanent.getControllerId())) {
            return false;
        }
        int amountCounters = fromPermanent.getCounters().getCount(CounterType.P1P1);
        if (amountCounters > 0) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller.getAmount(0, amountCounters, "How many counters do you want to move?", game) > 0){
                fromPermanent.getCounters().removeCounter(CounterType.P1P1, amountCounters);
                toPermanent.addCounters(CounterType.P1P1.createInstance(amountCounters), game);
            }
        }
        return true;
    }
}
