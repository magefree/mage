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

import java.util.ArrayList;
import java.util.Arrays;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.constants.ColoredManaSymbol;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class AddManaInAnyCombinationEffect extends ManaEffect {

    private ArrayList<ColoredManaSymbol> manaSymbols = new ArrayList<>();
    private final DynamicValue amount;

    public AddManaInAnyCombinationEffect(int amount) {
        this(new StaticValue(amount), ColoredManaSymbol.B, ColoredManaSymbol.U, ColoredManaSymbol.R, ColoredManaSymbol.W, ColoredManaSymbol.G);
    }

    public AddManaInAnyCombinationEffect(int amount, ColoredManaSymbol... coloredManaSymbols) {
        this(new StaticValue(amount), coloredManaSymbols);
    }

    public AddManaInAnyCombinationEffect(DynamicValue amount, ColoredManaSymbol... coloredManaSymbols) {
        super();
        this.manaSymbols.addAll(Arrays.asList(coloredManaSymbols));
        this.amount = amount;
        this.staticText = setText();
    }
    
    public AddManaInAnyCombinationEffect(int amount, String text) {
        this(amount);
        this.staticText = text;
    }
    
    public AddManaInAnyCombinationEffect(int amount, String text, ColoredManaSymbol... coloredManaSymbols) {
        this(amount, coloredManaSymbols);
        this.staticText = text;
    }
    
    public AddManaInAnyCombinationEffect(DynamicValue amount, String text, ColoredManaSymbol... coloredManaSymbols) {
        this(amount, coloredManaSymbols);
        this.staticText = text;
    }

    public AddManaInAnyCombinationEffect(final AddManaInAnyCombinationEffect effect) {
        super(effect);
        this.manaSymbols = effect.manaSymbols;
        this.amount = effect.amount;
    }

    @Override
    public AddManaInAnyCombinationEffect copy() {
        return new AddManaInAnyCombinationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null){
            Mana mana = new Mana();
            int amountOfManaLeft = amount.calculate(game, source, this);

            while (amountOfManaLeft > 0 && player.canRespond()) {
                for (ColoredManaSymbol coloredManaSymbol: manaSymbols) {
                    int number = player.getAmount(0, amountOfManaLeft, new StringBuilder("How many ").append(coloredManaSymbol.name()).append(" mana?").toString(), game);
                    if (number > 0) {
                        for (int i = 0; i < number; i++) {
                            mana.add(new Mana(coloredManaSymbol));
                        }
                        amountOfManaLeft -= number;
                    }
                    if (amountOfManaLeft == 0) {
                        break;
                    }
                }
            }
            checkToFirePossibleEvents(mana, game, source);
            player.getManaPool().addMana(mana, game, source);            
            return true;
        }
        return false;
    }

    @Override
    public Mana getMana(Game game, Ability source) {
        return null;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("Add ");
        sb.append(CardUtil.numberToText(amount.toString()));
        sb.append(" mana in any combination of ");
        if (manaSymbols.size() == 5) {
            sb.append("colors");
        } else {
            int i = 0;
            for (ColoredManaSymbol coloredManaSymbol: manaSymbols) {
                i++;
                if (i > 1) {
                    sb.append(" and/or ");
                }
                sb.append("{").append(coloredManaSymbol.toString()).append("}");
            }
        }
        sb.append(" to your mana pool");
        return sb.toString();
    }
}
