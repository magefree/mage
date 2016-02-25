/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class OnOpponentsTurnCondition implements Condition {

    private static final OnOpponentsTurnCondition F_INSTANCE = new OnOpponentsTurnCondition();

    public static Condition getInstance() {
        return F_INSTANCE;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getOpponents(source.getControllerId()).contains(game.getActivePlayerId());
    }

    @Override
    public String toString() {
        return "on an opponent's turn";
    }
}
