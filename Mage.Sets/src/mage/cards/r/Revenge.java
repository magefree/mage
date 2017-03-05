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
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.PlayerLostLifeWatcher;

/**
 *
 * @author Styxo
 */
public class Revenge extends CardImpl {

    public Revenge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Target creature you control gets +4/+0 until end of turn before it fights if you lost life this turn.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new RevengeEffect(),
                LostLifeCondition.getInstance(),
                "Target creature you control gets +4/+0 until end of turn before it fights if you lost life this turn"));

        // Target creature you control fights target creature an opponent controls.
        this.getSpellAbility().addEffect(new FightTargetsEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());

        this.getSpellAbility().addWatcher(new PlayerLostLifeWatcher());

    }

    public Revenge(final Revenge card) {
        super(card);
    }

    @Override
    public Revenge copy() {
        return new Revenge(this);
    }
}

class LostLifeCondition implements Condition {

    private static LostLifeCondition fInstance = null;

    public static Condition getInstance() {
        if (fInstance == null) {
            fInstance = new LostLifeCondition();
        }
        return fInstance;
    }

    private LostLifeCondition() {
    }

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerLostLifeWatcher watcher = (PlayerLostLifeWatcher) game.getState().getWatchers().get("PlayerLostLifeWatcher");
        UUID player = source.getControllerId();
        if (watcher != null && player != null) {
            return watcher.getLiveLost(player) > 0;
        }
        return false;
    }

}

class RevengeEffect extends OneShotEffect {

    public RevengeEffect() {
        super(Outcome.BoostCreature);
    }

    public RevengeEffect(final RevengeEffect effect) {
        super(effect);
    }

    @Override
    public RevengeEffect copy() {
        return new RevengeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(targetPointer.getFirst(game, source));
        if (target != null && target.isCreature()) {
            ContinuousEffect effect = new BoostTargetEffect(4, 0, Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(target.getId()));
            game.addEffect(effect, source);
            return true;
        }

        return false;
    }

}
