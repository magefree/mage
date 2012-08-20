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

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.choices.ChoiceColor;
import mage.game.Game;
import mage.players.Player;

/**
 * @author noxx
 */
public class AddConditionalManaOfAnyColorEffect extends ManaEffect<AddConditionalManaOfAnyColorEffect> {

    private int amount;
    private ConditionalManaBuilder manaBuilder;

    public AddConditionalManaOfAnyColorEffect(int amount, ConditionalManaBuilder manaBuilder) {
        super();
        this.amount = amount;
        this.manaBuilder = manaBuilder;
        staticText = "Add " + amount + " mana of any one color to your mana pool. "  + manaBuilder.getRule();
    }

    public AddConditionalManaOfAnyColorEffect(final AddConditionalManaOfAnyColorEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.manaBuilder = effect.manaBuilder;
    }

    @Override
    public AddConditionalManaOfAnyColorEffect copy() {
        return new AddConditionalManaOfAnyColorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        boolean result = false;
        for (int i = 0; i < amount; i++) {
            ChoiceColor choice = (ChoiceColor) source.getChoices().get(i);

            Mana mana = null;
            if (choice.getColor().isBlack()) {
                mana = manaBuilder.setMana(Mana.BlackMana(1)).build();
            } else if (choice.getColor().isBlue()) {
                mana = manaBuilder.setMana(Mana.BlueMana(1)).build();
            } else if (choice.getColor().isRed()) {
                mana = manaBuilder.setMana(Mana.RedMana(1)).build();
            } else if (choice.getColor().isGreen()) {
                mana = manaBuilder.setMana(Mana.GreenMana(1)).build();
            } else if (choice.getColor().isWhite()) {
                mana = manaBuilder.setMana(Mana.WhiteMana(1)).build();
            }

            if (mana != null) {
                player.getManaPool().addMana(mana, game, source);
                result = true;
            }
        }

        return result;
    }
}
