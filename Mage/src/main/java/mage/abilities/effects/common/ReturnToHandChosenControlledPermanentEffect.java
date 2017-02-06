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

import mage.abilities.Ability;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author Quercitron
 */
public class ReturnToHandChosenControlledPermanentEffect extends ReturnToHandChosenPermanentEffect {

    public ReturnToHandChosenControlledPermanentEffect(FilterControlledPermanent filter) {
        super(filter);
    }

    public ReturnToHandChosenControlledPermanentEffect(FilterControlledPermanent filter, int number) {
        super(filter, number);
    }

    public ReturnToHandChosenControlledPermanentEffect(ReturnToHandChosenControlledPermanentEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        this.targetPointer = new FixedTarget(source.getControllerId());
        return super.apply(game, source);
    }

    @Override
    public ReturnToHandChosenControlledPermanentEffect copy() {
        return new ReturnToHandChosenControlledPermanentEffect(this);
    }

    @Override
    protected String getText() {
        StringBuilder sb = new StringBuilder("return ");
        if (!filter.getMessage().startsWith("another")) {
            sb.append(CardUtil.numberToText(number, "a"));
        }
        sb.append(' ').append(filter.getMessage());
        if (number > 1) {
            sb.append(" to their owner's hand");
        } else {
            sb.append(" to its owner's hand");
        }
        return sb.toString();
    }

}
