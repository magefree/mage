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

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class FlipCoinEffect extends OneShotEffect {

    protected Effects executingEffectsWon = new Effects();
    protected Effects executingEffectsLost = new Effects();

    public FlipCoinEffect(Effect effectWon) {
        this(effectWon, null);
    }

    public FlipCoinEffect(Effect effectWon, Effect effectLost) {
        this(effectWon, effectLost, Outcome.Benefit);

    }

    public FlipCoinEffect(Effect effectWon, Effect effectLost, Outcome outcome) {
        super(outcome);
        addEffectWon(effectWon);
        addEffectLost(effectLost);
    }

    public FlipCoinEffect(final FlipCoinEffect effect) {
        super(effect);
        this.executingEffectsWon = effect.executingEffectsWon.copy();
        this.executingEffectsLost = effect.executingEffectsLost.copy();
    }

    public void addEffectWon(Effect effect) {
        if (effect != null) {
            executingEffectsWon.add(effect);
        }
    }

    public void addEffectLost(Effect effect) {
        if (effect != null) {
            executingEffectsLost.add(effect);
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source.getSourceId());
        if (controller != null && mageObject != null) {
            boolean result = true;
            for (Effect effect : controller.flipCoin(game) ? executingEffectsWon : executingEffectsLost) {
                effect.setTargetPointer(this.targetPointer);
                if (effect instanceof OneShotEffect) {
                    result &= effect.apply(game, source);
                } else {
                    game.addEffect((ContinuousEffect) effect, source);
                }
            }
            return result;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("Flip a coin. If you win the flip, ").append(executingEffectsWon.getText(mode));
        if (!executingEffectsLost.isEmpty()) {
            sb.append(". If you lose the flip, ").append(executingEffectsLost.getText(mode));
        }
        return sb.toString();
    }

    @Override
    public FlipCoinEffect copy() {
        return new FlipCoinEffect(this);
    }
}
