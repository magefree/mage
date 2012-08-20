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
import mage.choices.ChoiceColor;
import mage.game.Game;
import mage.players.Player;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class AddManaOfAnyColorEffect extends ManaEffect<AddManaOfAnyColorEffect> {

    protected int amount;

    public AddManaOfAnyColorEffect() {
        this(1);
    }

    public AddManaOfAnyColorEffect(final int amount) {
        super();
        this.amount = amount;
        this.staticText = "add " + amount + " mana of any color to your mana pool";
    }

    public AddManaOfAnyColorEffect(final AddManaOfAnyColorEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public AddManaOfAnyColorEffect copy() {
        return new AddManaOfAnyColorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ChoiceColor choice = (ChoiceColor) source.getChoices().get(0);
        Player player = game.getPlayer(source.getControllerId());

        Mana mana = null;
        if (choice.getColor().isBlack()) {
            mana = Mana.BlackMana(amount);
        } else if (choice.getColor().isBlue()) {
            mana = Mana.BlueMana(amount);
        } else if (choice.getColor().isRed()) {
            mana = Mana.RedMana(amount);
        } else if (choice.getColor().isGreen()) {
            mana = Mana.GreenMana(amount);
        } else if (choice.getColor().isWhite()) {
            mana = Mana.WhiteMana(amount);
        }

        if (player != null && mana != null) {
            player.getManaPool().addMana(mana, game, source);
            return true;
        }

        return false;
    }
}
