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
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */

public class DoIfClashWonEffect extends OneShotEffect {
    protected final Effect executingEffect;
    private String chooseUseText;
    private boolean setTargetPointerToClashedOpponent;

    public DoIfClashWonEffect(Effect effect) {
        this(effect, false, null);
    }

    public DoIfClashWonEffect(Effect effect, boolean setTargetPointerToClashedOpponent, String chooseUseText) {
        super(Outcome.Benefit);
        this.executingEffect = effect;
        this.chooseUseText = chooseUseText;        
        this.setTargetPointerToClashedOpponent = setTargetPointerToClashedOpponent;
    }

    public DoIfClashWonEffect(final DoIfClashWonEffect effect) {
        super(effect);
        this.executingEffect = effect.executingEffect.copy();
        this.chooseUseText = effect.chooseUseText;
        this.setTargetPointerToClashedOpponent = effect.setTargetPointerToClashedOpponent;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = getPayingPlayer(game, source);
        MageObject mageObject = game.getObject(source.getSourceId());
        if (player != null && mageObject != null) {
            String message = null;
            if (chooseUseText != null) {
                message = chooseUseText;
                message = CardUtil.replaceSourceName(message, mageObject.getLogName());
            }
            
            if (chooseUseText == null || player.chooseUse(executingEffect.getOutcome(), message, game)) {
                if (ClashEffect.getInstance().apply(game, source)) {
                    if (setTargetPointerToClashedOpponent) {
                        Object opponent = getValue("clashOpponent");
                        if (opponent instanceof Player) {
                            executingEffect.setTargetPointer(new FixedTarget(((Player)opponent).getId()));
                        }                        
                    } else {
                        executingEffect.setTargetPointer(this.targetPointer);
                    }
                    if (executingEffect instanceof OneShotEffect) {
                        return executingEffect.apply(game, source);
                    }
                    else {
                        game.addEffect((ContinuousEffect) executingEffect, source);
                    }
                }
            }
            return true;
        }
        return false;
    }

    protected Player getPayingPlayer(Game game, Ability source) {
        return game.getPlayer(source.getControllerId());
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }
        return new StringBuilder("clash with an opponent. If you win, ").append(executingEffect.getText(mode)).toString();
    }

    @Override
    public DoIfClashWonEffect copy() {
        return new DoIfClashWonEffect(this);
    }
}
