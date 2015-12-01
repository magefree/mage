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
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.effects.common.ManaEffect;
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
        if (condition.apply(game, source)) {
            effect.setTargetPointer(this.targetPointer);
        } else if (otherwiseEffect != null) {
            otherwiseEffect.setTargetPointer(this.targetPointer);
        }
        Mana mana = getMana(game, source);

        if (mana != null && mana.getAny() > 0) {
            int amount = mana.getAny();

            ChoiceColor choice = new ChoiceColor(true);
            Mana createdMana = null;
            if (controller.choose(outcome, choice, game)) {
                if (choice.getColor() == null) {
                    return false; // it happens, don't know how
                }

                if (choice.getColor().isBlack()) {
                    createdMana = Mana.BlackMana(amount);
                } else if (choice.getColor().isBlue()) {
                    createdMana = Mana.BlueMana(amount);
                } else if (choice.getColor().isRed()) {
                    createdMana = Mana.RedMana(amount);
                } else if (choice.getColor().isGreen()) {
                    createdMana = Mana.GreenMana(amount);
                } else if (choice.getColor().isWhite()) {
                    createdMana = Mana.WhiteMana(amount);
                }
            }
            mana = createdMana;
        }

        if (mana != null) {
            controller.getManaPool().addMana(mana, game, source);
        }
        return true;
    }

    @Override
    public ConditionalManaEffect copy() {
        return new ConditionalManaEffect(this);
    }

    @Override
    public Mana getMana(Game game, Ability source
    ) {
        Mana mana = null;
        if (condition.apply(game, source)) {
            mana = effect.getMana();
        } else if (otherwiseEffect != null) {
            mana = otherwiseEffect.getMana();
        }
        if (mana != null) {
            checkToFirePossibleEvents(mana, game, source);
        }
        return mana;
    }
}
