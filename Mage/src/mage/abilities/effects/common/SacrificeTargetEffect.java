/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author ayratn
 */
public class SacrificeTargetEffect extends OneShotEffect {

    protected UUID playerIdThatHasToSacrifice;

    public SacrificeTargetEffect() {
        this("");
    }

    public SacrificeTargetEffect(String text) {
        this(text, null);
    }

    /**
     *
     * @param text use this text as rule text for the effect
     * @param playerIdThatHasToSacrifice only this playerId has to sacrifice
     * (others can't)
     */
    public SacrificeTargetEffect(String text, UUID playerIdThatHasToSacrifice) {
        super(Outcome.Sacrifice);
        this.playerIdThatHasToSacrifice = playerIdThatHasToSacrifice;
        staticText = text;
    }

    public SacrificeTargetEffect(final SacrificeTargetEffect effect) {
        super(effect);
        this.playerIdThatHasToSacrifice = effect.playerIdThatHasToSacrifice;
    }

    @Override
    public SacrificeTargetEffect copy() {
        return new SacrificeTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        for (UUID permanentId : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null && (playerIdThatHasToSacrifice == null || playerIdThatHasToSacrifice.equals(permanent.getControllerId()))) {
                permanent.sacrifice(source.getSourceId(), game);
                affectedTargets++;
            }
        }
        return affectedTargets > 0;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText.isEmpty() && !mode.getTargets().isEmpty()) {
            if (mode.getTargets().get(0).getNumberOfTargets() == 1) {
                return "The controller of target " + mode.getTargets().get(0).getTargetName() + " sacrifices it";
            } else {
                return "The controller of " + mode.getTargets().get(0).getNumberOfTargets() + " target " + mode.getTargets().get(0).getTargetName() + " sacrifices it";
            }
        }
        return staticText;
    }

}
