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

import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.game.Game;


import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author Quercitron
 */
public class CantBeBlockedByMoreThanOneSourceEffect extends ContinuousEffectImpl<CantBeBlockedByMoreThanOneSourceEffect> {

    protected int amount;

    public CantBeBlockedByMoreThanOneSourceEffect() {
        this(1);
    }

    public CantBeBlockedByMoreThanOneSourceEffect(int amount) {
        this(amount, Duration.WhileOnBattlefield);
    }

    public CantBeBlockedByMoreThanOneSourceEffect(int amount, Duration duration) {
        super(duration, Outcome.Benefit);
        this.amount = amount;
        staticText = "{this} can't be blocked by more than " + CardUtil.numberToText(amount) + " creature" + (amount > 1 ? "s" : "");
    }

    public CantBeBlockedByMoreThanOneSourceEffect(final CantBeBlockedByMoreThanOneSourceEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public CantBeBlockedByMoreThanOneSourceEffect copy() {
        return new CantBeBlockedByMoreThanOneSourceEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent perm = game.getPermanent(source.getSourceId());
        if (perm != null) {
            switch (layer) {
                case RulesEffects:
                    perm.setMaxBlockedBy(amount);
                    break;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
