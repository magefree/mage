/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
public class ManaInAnyCombinationEffect extends ManaEffect <ManaInAnyCombinationEffect> {

    private ArrayList<ColoredManaSymbol> manaSymbols = new ArrayList();
    private final DynamicValue amount;

    public ManaInAnyCombinationEffect(int amount, ColoredManaSymbol... coloredManaSymbols) {
        this(new StaticValue(amount), coloredManaSymbols);
    }

    public ManaInAnyCombinationEffect(DynamicValue amount, ColoredManaSymbol... coloredManaSymbols) {
        super();
        this.manaSymbols.addAll(Arrays.asList(coloredManaSymbols));
        this.amount = amount;
        this.staticText = setText();
    }

    public ManaInAnyCombinationEffect(final ManaInAnyCombinationEffect effect) {
        super(effect);
        this.manaSymbols = effect.manaSymbols;
        this.amount = effect.amount;
    }

    @Override
    public ManaInAnyCombinationEffect copy() {
        return new ManaInAnyCombinationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null){
            Mana mana = new Mana();
            int amountOfManaLeft = amount.calculate(game, source);

            while (amountOfManaLeft > 0 && player.isInGame()) {
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
            player.getManaPool().addMana(mana, game, source);
            return true;
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("Add ");
        sb.append(CardUtil.numberToText(amount.toString()));
        sb.append(" mana in any combination of ");
        int i = 0;
        for (ColoredManaSymbol coloredManaSymbol: manaSymbols) {
            i++;
            if (i > 1) {
                sb.append(" and/or ");
            }
            sb.append("{").append(coloredManaSymbol.toString()).append("}");
        }
        sb.append(" to your mana pool");
        return sb.toString();
    }
}
