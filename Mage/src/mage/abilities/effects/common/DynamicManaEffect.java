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

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.counters.CounterType;
import mage.game.Game;

/**
 *
 * @author North
 */
public class DynamicManaEffect extends BasicManaEffect {

    private Mana computedMana;
    private DynamicValue amount;

    public DynamicManaEffect(Mana mana, DynamicValue amount) {
        super(mana);
        this.amount = amount;
        computedMana = new Mana();
    }

    public DynamicManaEffect(final DynamicManaEffect effect) {
        super(effect);
        this.computedMana = effect.computedMana.copy();
        this.amount = effect.amount.clone();
    }

    @Override
    public DynamicManaEffect copy() {
        return new DynamicManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        computeMana(game, source);
        game.getPlayer(source.getControllerId()).getManaPool().changeMana(computedMana, game, source);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return super.getText(mode) + " for each " + amount.getMessage();
    }

    public Mana computeMana(Game game, Ability source){
        this.computedMana.clear();
        int count = amount.calculate(game, source);
        if (mana.getBlack() > 0) {
            computedMana.setBlack(count);
        } else if (mana.getBlue() > 0) {
            computedMana.setBlue(count);
        } else if (mana.getGreen() > 0) {
            computedMana.setGreen(count);
        } else if (mana.getRed() > 0) {
            computedMana.setRed(count);
        } else if (mana.getWhite() > 0) {
            computedMana.setWhite(count);
        } else {
            computedMana.setColorless(count);
        }
        return computedMana;
    }
}
