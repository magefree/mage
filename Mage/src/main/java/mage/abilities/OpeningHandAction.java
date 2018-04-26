/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities;

import mage.cards.Card;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public interface OpeningHandAction {

    boolean askUseOpeningHandAction(Card card, Player player, Game game);

    boolean isOpeningHandActionAllowed(Card card, Player player, Game game);

    void doOpeningHandAction(Card card, Player player, Game game);

}
