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
import mage.choices.ChoiceColor;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class DynamicManaEffect extends BasicManaEffect {

    private final Mana computedMana;
    private final DynamicValue amount;
    private final DynamicValue netAmount;
    private String text = null;
    private boolean oneChoice;

    public DynamicManaEffect(Mana mana, DynamicValue amount) {
        this(mana, amount, null);
    }

    public DynamicManaEffect(Mana mana, DynamicValue amount, String text) {
        this(mana, amount, text, false);
    }

    public DynamicManaEffect(Mana mana, DynamicValue amount, String text, boolean oneChoice) {
        this(mana, amount, text, oneChoice, null);
    }

    /**
     *
     * @param mana
     * @param amount
     * @param text
     * @param oneChoice is all mana from the same colour or if false the player
     * can choose different colours
     * @param netAmount a dynamic value that calculates the possible available
     * mana (e.g. if you have to pay by removing counters from source)
     */
    public DynamicManaEffect(Mana mana, DynamicValue amount, String text, boolean oneChoice, DynamicValue netAmount) {
        super(mana);
        this.amount = amount;
        computedMana = new Mana();
        this.text = text;
        this.oneChoice = oneChoice;
        this.netAmount = netAmount;
    }

    public DynamicManaEffect(final DynamicManaEffect effect) {
        super(effect);
        this.computedMana = effect.computedMana.copy();
        this.amount = effect.amount.copy();
        this.text = effect.text;
        this.oneChoice = effect.oneChoice;
        if (effect.netAmount != null) {
            this.netAmount = effect.netAmount.copy();
        } else {
            this.netAmount = null;
        }
    }

    @Override
    public DynamicManaEffect copy() {
        return new DynamicManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        computeMana(false, game, source);
        checkToFirePossibleEvents(computedMana, game, source);
        game.getPlayer(source.getControllerId()).getManaPool().addMana(computedMana, game, source);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (text != null && !text.isEmpty()) {
            return text;
        }
        return super.getText(mode) + " for each " + amount.getMessage();
    }

    @Override
    public Mana getMana(Game game, Ability source) {
        return null;
    }

    public Mana computeMana(boolean netMana, Game game, Ability source) {
        this.computedMana.clear();
        int count;
        if (netMana && netAmount != null) {
            // calculate the maximum available mana
            count = netAmount.calculate(game, source, this);
        } else {
            count = amount.calculate(game, source, this);
        }

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
        } else if (mana.getAny() > 0) {
            if (netMana) {
                computedMana.setAny(count);
            } else {
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {
                    ChoiceColor choiceColor = new ChoiceColor();
                    for (int i = 0; i < count; i++) {
                        if (!choiceColor.isChosen()) {
                            while (!controller.choose(Outcome.Benefit, choiceColor, game)) {
                                if (!controller.canRespond()) {
                                    return computedMana;
                                }
                            }
                        }
                        if (choiceColor.getColor().isBlack()) {
                            computedMana.increaseBlack();
                        } else if (choiceColor.getColor().isBlue()) {
                            computedMana.increaseBlue();
                        } else if (choiceColor.getColor().isRed()) {
                            computedMana.increaseRed();
                        } else if (choiceColor.getColor().isGreen()) {
                            computedMana.increaseGreen();
                        } else if (choiceColor.getColor().isWhite()) {
                            computedMana.increaseWhite();
                        }
                        if (!oneChoice) {
                            choiceColor.clearChoice();
                        }
                    }
                }
            }
        } else {
            computedMana.setColorless(count);
        }
        return computedMana;
    }

}
