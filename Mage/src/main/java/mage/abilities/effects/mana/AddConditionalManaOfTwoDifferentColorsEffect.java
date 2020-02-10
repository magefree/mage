/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.mana;

import java.util.ArrayList;
import java.util.List;
import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.choices.ManaChoice;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class AddConditionalManaOfTwoDifferentColorsEffect extends ManaEffect {
    
    private final ConditionalManaBuilder manaBuilder;
    
    public AddConditionalManaOfTwoDifferentColorsEffect(ConditionalManaBuilder manaBuilder) {
        super();
        this.manaBuilder = manaBuilder;
        staticText = "Add two mana of different colors. " + manaBuilder.getRule();
    }

    private AddConditionalManaOfTwoDifferentColorsEffect(final AddConditionalManaOfTwoDifferentColorsEffect effect) {
        super(effect);
        this.manaBuilder = effect.manaBuilder;
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        netMana.add(Mana.AnyMana(2));
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Player player = getPlayer(game, source);
        Mana mana = new ConditionalMana(manaBuilder.setMana(
                ManaChoice.chooseTwoDifferentColors(
                        player, game), source, game).build());
        return mana;
    }

    @Override
    public AddConditionalManaOfTwoDifferentColorsEffect copy() {
        return new AddConditionalManaOfTwoDifferentColorsEffect(this);
    }
}
