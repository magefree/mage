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
package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class CanBlockAdditionalCreatureEffect extends ContinuousEffectImpl {

    protected int amount;

    public CanBlockAdditionalCreatureEffect() {
        this(1);
    }

    /**
     * Changes the number of creatures source creature can block
     *
     * @param amount 0 = any number, 1-x = n additional blocks
     */
    public CanBlockAdditionalCreatureEffect(int amount) {
        this(Duration.WhileOnBattlefield, amount);
    }

    public CanBlockAdditionalCreatureEffect(Duration duration, int amount) {
        super(duration, Outcome.Benefit);
        this.amount = amount;
        staticText = setText();
    }

    public CanBlockAdditionalCreatureEffect(final CanBlockAdditionalCreatureEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public CanBlockAdditionalCreatureEffect copy() {
        return new CanBlockAdditionalCreatureEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            // maxBlocks = 0 equals to "can block any number of creatures"
            if (amount > 0) {
                permanent.setMaxBlocks(permanent.getMaxBlocks() + amount);
            } else {
                permanent.setMaxBlocks(0);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    private String setText() {
        String text = "{this} can block ";
        switch (amount) {
            case 0:
                text += "any number of creatures";
                break;
            default:
                text += CardUtil.numberToText(amount, "an") + " additional creature" + (amount > 1 ? "s" : "");
        }
        if (duration.equals(Duration.EndOfTurn)) {
            text += " this turn";
        }
        if (duration.equals(Duration.WhileOnBattlefield)) {
            text += " each combat";
        }
        return text;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }

}
