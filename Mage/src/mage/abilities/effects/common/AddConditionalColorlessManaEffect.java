/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mage.abilities.effects.common;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */

public class AddConditionalColorlessManaEffect extends ManaEffect {

    private final int amount;
    private final ConditionalManaBuilder manaBuilder;

    public AddConditionalColorlessManaEffect(int amount, ConditionalManaBuilder manaBuilder) {
        super();
        this.amount = amount;
        this.manaBuilder = manaBuilder;
        staticText = "Add {" + amount + "} to your mana pool. "  + manaBuilder.getRule();
    }

    public AddConditionalColorlessManaEffect(final AddConditionalColorlessManaEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.manaBuilder = effect.manaBuilder;
    }

    @Override
    public AddConditionalColorlessManaEffect copy() {
        return new AddConditionalColorlessManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.getManaPool().addMana(getMana(game, source), game, source);
            return true;
        }
        return false;
    }

    @Override
    public Mana getMana(Game game, Ability source) {
        return manaBuilder.setMana(Mana.ColorlessMana(amount), source, game).build();
    }
}
