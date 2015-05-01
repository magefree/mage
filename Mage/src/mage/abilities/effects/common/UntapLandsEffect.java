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

package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;

/**
 * "Untap up to X lands" effect
 */
public class UntapLandsEffect extends OneShotEffect {
    private final int amount;

    public UntapLandsEffect(int amount) {
        super(Outcome.Untap);
        this.amount = amount;
        staticText = "Untap up to " + amount + " lands";
    }

    public UntapLandsEffect(final UntapLandsEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetLandPermanent target = new TargetLandPermanent(0, amount, new FilterLandPermanent(), true);
            if (target.canChoose(source.getSourceId(), source.getControllerId(), game)) {
                if (target.choose(Outcome.Untap, source.getControllerId(), source.getSourceId(), game)) {
                    for (Object targetId : target.getTargets()) {
                        Permanent p = game.getPermanent((UUID) targetId);
                        if (p != null) {
                            p.untap(game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public UntapLandsEffect copy() {
        return new UntapLandsEffect(this);
    }
}
