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
package mage.abilities.decorator;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.choices.ChoiceColor;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ConditionalManaEffect extends ManaEffect {

    private BasicManaEffect effect;
    private BasicManaEffect otherwiseEffect;
    private Condition condition;

    public ConditionalManaEffect(BasicManaEffect effect, Condition condition, String text) {
        this(effect, null, condition, text);
    }

    public ConditionalManaEffect(BasicManaEffect effect, BasicManaEffect otherwiseEffect, Condition condition, String text) {
        super();
        this.effect = effect;
        this.otherwiseEffect = otherwiseEffect;
        this.condition = condition;
        this.staticText = text;
    }

    public ConditionalManaEffect(ConditionalManaEffect effect) {
        super(effect);
        this.effect = effect.effect.copy();
        if (effect.otherwiseEffect != null) {
            this.otherwiseEffect = effect.otherwiseEffect.copy();
        }
        this.condition = effect.condition;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.getManaPool().addMana(getMana(game, source), game, source);
        return true;
    }

    @Override
    public ConditionalManaEffect copy() {
        return new ConditionalManaEffect(this);
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Mana mana = new Mana();
        if (condition.apply(game, source)) {
            mana = effect.getManaTemplate().copy();
        } else if (otherwiseEffect != null) {
            mana = otherwiseEffect.getManaTemplate().copy();
        }
        if (mana.getAny() > 0) {
            int amount = mana.getAny();
            Player controller = game.getPlayer(source.getControllerId());
            if (controller == null) {
                return mana;
            }
            ChoiceColor choice = new ChoiceColor(true);
            if (controller.choose(outcome, choice, game)) {
                mana.setAny(0);
                mana.add(choice.getMana(amount));
            }
            checkToFirePossibleEvents(mana, game, source);
        }
        return mana;
    }
}
