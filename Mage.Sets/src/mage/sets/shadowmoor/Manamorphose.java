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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class Manamorphose extends CardImpl<Manamorphose> {

    public Manamorphose(UUID ownerId) {
        super(ownerId, 211, "Manamorphose", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{R/G}");
        this.expansionSetCode = "SHM";

        this.color.setRed(true);
        this.color.setGreen(true);

        // Add two mana in any combination of colors to your mana pool.
        this.getSpellAbility().addEffect(new ManamorphoseEffect());
        this.getSpellAbility().addChoice(new ChoiceColor());
        this.getSpellAbility().addChoice(new ChoiceColor());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardControllerEffect(1));
    }

    public Manamorphose(final Manamorphose card) {
        super(card);
    }

    @Override
    public Manamorphose copy() {
        return new Manamorphose(this);
    }
}

class ManamorphoseEffect extends ManaEffect<ManamorphoseEffect> {

    public ManamorphoseEffect() {
        super();
        this.staticText = "Add two mana in any combination of colors to your mana pool";
    }

    public ManamorphoseEffect(final ManamorphoseEffect effect) {
        super(effect);
    }

    @Override
    public ManamorphoseEffect copy() {
        return new ManamorphoseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        boolean result = false;
        for (int i = 0; i < 2; i++) {
            ChoiceColor choice = (ChoiceColor) source.getChoices().get(i);
            Mana mana = null;
            if (choice.getColor().isBlack()) {
                mana = Mana.BlackMana(1);
            } else if (choice.getColor().isBlue()) {
                mana = Mana.BlueMana(1);
            } else if (choice.getColor().isRed()) {
                mana = Mana.RedMana(1);
            } else if (choice.getColor().isGreen()) {
                mana = Mana.GreenMana(1);
            } else if (choice.getColor().isWhite()) {
                mana = Mana.WhiteMana(1);
            }

            if (mana != null) {
                player.getManaPool().addMana(mana, game, source);
                result = true;
            }
        }
        return result;
    }
}
