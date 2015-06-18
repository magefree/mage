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

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class Bioshift extends CardImpl {

    public Bioshift(UUID ownerId) {
        super(ownerId, 214, "Bioshift", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{G/U}");
        this.expansionSetCode = "GTC";

        // Move any number of +1/+1 counters from target creature onto another target creature with the same controller.
        getSpellAbility().addEffect(new MoveCounterFromTargetToTargetEffect());
        getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature (you take counters from)")));
        getSpellAbility().addTarget(new BioshiftSecondTargetPermanent());
    }
    

    public Bioshift(final Bioshift card) {
        super(card);
    }

    @Override
    public Bioshift copy() {
        return new Bioshift(this);
    }
}

class MoveCounterFromTargetToTargetEffect extends OneShotEffect {

    public MoveCounterFromTargetToTargetEffect() {
        super(Outcome.Detriment);
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
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent fromPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            Permanent toPermanent = null;
            if (source.getTargets().size() > 1) {
                toPermanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
            }
            if (fromPermanent == null || toPermanent == null || !fromPermanent.getControllerId().equals(toPermanent.getControllerId())) {
                return false;
            }
            int amountCounters = fromPermanent.getCounters().getCount(CounterType.P1P1);
            if (amountCounters > 0) {
                int amountToMove = controller.getAmount(0, amountCounters, "How many counters do you want to move?", game);
                if (amountToMove > 0) {
                    fromPermanent.removeCounters(CounterType.P1P1.createInstance(amountToMove), game);
                    toPermanent.addCounters(CounterType.P1P1.createInstance(amountToMove), game);
                }
            }
            return true;
        }
        return false;

    }
}

class BioshiftSecondTargetPermanent extends TargetPermanent {
    
    BioshiftSecondTargetPermanent() {
        super();
        this.filter = new FilterCreaturePermanent("another target creature with the same controller (counters go to)");
    }

    BioshiftSecondTargetPermanent(final BioshiftSecondTargetPermanent target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        Permanent firstPermanent = game.getPermanent(source.getTargets().getFirstTarget());
        Permanent secondPermanent = game.getPermanent(id);
        if (firstPermanent != null && secondPermanent != null) {
            if (!firstPermanent.getId().equals(id) && firstPermanent.getControllerId().equals(secondPermanent.getControllerId())) {
                return super.canTarget(controllerId, id, source, game);
            }
        }
        return false;
    }

    @Override
    public BioshiftSecondTargetPermanent copy() {
        return new BioshiftSecondTargetPermanent(this);
    }
}
