/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.common.ManaEffect;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class AddManaToManaPoolTargetControllerEffect extends ManaEffect {

    protected Mana mana;
    protected boolean emptyOnlyOnTurnsEnd;

    public AddManaToManaPoolTargetControllerEffect(Mana mana, String textManaPoolOwner) {
        this(mana, textManaPoolOwner, false);
    }

    /**
     * Adds mana to the mana pool of target pointer player
     *
     * @param mana mana that will be added to the pool
     * @param textManaPoolOwner text that references to the mana pool owner
     * (e.g. "damaged player's")
     * @param emptyOnTurnsEnd if set, the mana will empty only on end of
     * turnstep
     *
     */
    public AddManaToManaPoolTargetControllerEffect(Mana mana, String textManaPoolOwner, boolean emptyOnTurnsEnd) {
        super();
        this.mana = mana;
        this.emptyOnlyOnTurnsEnd = emptyOnTurnsEnd;
        this.staticText = (textManaPoolOwner.equals("their") ? "that player adds " : "add ") + mana.toString();
    }

    public AddManaToManaPoolTargetControllerEffect(final AddManaToManaPoolTargetControllerEffect effect) {
        super(effect);
        this.mana = effect.mana;
        this.emptyOnlyOnTurnsEnd = effect.emptyOnlyOnTurnsEnd;
    }

    @Override
    public AddManaToManaPoolTargetControllerEffect copy() {
        return new AddManaToManaPoolTargetControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null) {
            player.getManaPool().addMana(getMana(game, source), game, source, emptyOnlyOnTurnsEnd);
            return true;
        }
        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        if (netMana) {
            return null;
        }
        return mana.copy();
    }
}
