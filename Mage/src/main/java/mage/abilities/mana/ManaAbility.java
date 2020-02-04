/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.mana;

import java.util.List;
import mage.Mana;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public interface ManaAbility {

    /**
     * Used to check the possible mana production to determine which spells
     * and/or abilities can be used. (player.getPlayable()).
     *
     * @param game
     * @return
     */
    List<Mana> getNetMana(Game game);

    /**
     * Used to check if the ability itself defines mana types it can produce.
     *
     * @param game
     * @return
     */
    boolean definesMana(Game game);
}
