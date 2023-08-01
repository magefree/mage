/*
 *  
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
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
 * 
 */
package mage.abilities.effects.common;

import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class SkipNextPlayerUntapStepEffect extends OneShotEffect {

    public SkipNextPlayerUntapStepEffect() {
        this("");
    }

    public SkipNextPlayerUntapStepEffect(String text) {
        super(Outcome.Detriment);
        this.staticText = text;
    }

    public SkipNextPlayerUntapStepEffect(SkipNextPlayerUntapStepEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = null;
        if (targetPointer != null) {
            if (!this.targetPointer.getTargets(game, source).isEmpty()) {
                player = game.getPlayer(targetPointer.getFirst(game, source));
            } else {
                player = game.getPlayer(source.getControllerId());
            }
        }
        if (player != null) {
            game.getState().getTurnMods().add(new TurnMod(player.getId()).withSkipStep(PhaseStep.UNTAP));
            return true;
        }
        return false;
    }

    @Override
    public SkipNextPlayerUntapStepEffect copy() {
        return new SkipNextPlayerUntapStepEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        if (!staticText.isEmpty()) {
            sb.append(staticText).append(" player skips their next untap step");
        } else {
            sb.append("You skip your next untap step");
        }
        return sb.toString();
    }
}
