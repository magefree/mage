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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author stravant
 */
public class ShedWeakness extends CardImpl {

    public ShedWeakness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Target creature gets +2/+2 until end of turn. You may remove a -1/-1 counter from it.
        getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        getSpellAbility().addEffect(new MayRemoveM1M1CouterTargetEffect());
        getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public ShedWeakness(final ShedWeakness card) {
        super(card);
    }

    @Override
    public ShedWeakness copy() {
        return new ShedWeakness(this);
    }
}

class MayRemoveM1M1CouterTargetEffect extends OneShotEffect {

    public MayRemoveM1M1CouterTargetEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "You may remove a -1/-1 counter from it.";
    }

    public MayRemoveM1M1CouterTargetEffect(final MayRemoveM1M1CouterTargetEffect effect) {
        super(effect);
        this.staticText = "You may remove a -1/-1 counter from it.";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Permanent target = game.getPermanent(source.getTargets().getFirstTarget());
        if (target == null) {
            return false;
        }

        if (target.getCounters(game).getCount(CounterType.M1M1) > 0) {
            if (controller.chooseUse(outcome, "Remove a -1/-1 counter from " + target.getIdName() + "?", source, game)) {
                Effect effect = new RemoveCounterTargetEffect(CounterType.M1M1.createInstance());
                effect.setTargetPointer(new FixedTarget(target.getId()));
                effect.apply(game, source);
            }
        }

        return true;
    }

    @Override
    public MayRemoveM1M1CouterTargetEffect copy() {
        return new MayRemoveM1M1CouterTargetEffect(this);
    }
}

