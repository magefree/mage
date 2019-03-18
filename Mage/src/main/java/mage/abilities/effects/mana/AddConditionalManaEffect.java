/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class AddConditionalManaEffect extends ManaEffect {

    private final Mana mana;
    private final ConditionalManaBuilder manaBuilder;

    public AddConditionalManaEffect(Mana mana, ConditionalManaBuilder manaBuilder) {
        super();
        this.mana = mana;
        this.manaBuilder = manaBuilder;
        staticText = "Add " + this.mana.toString() + ". " + manaBuilder.getRule();
    }

    public AddConditionalManaEffect(final AddConditionalManaEffect effect) {
        super(effect);
        this.mana = effect.mana;
        this.manaBuilder = effect.manaBuilder;
    }

    @Override
    public AddConditionalManaEffect copy() {
        return new AddConditionalManaEffect(this);
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
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        return manaBuilder.setMana(mana, source, game).build();
    }

    public Mana getMana() {
        return mana;
    }
}
