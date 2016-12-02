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
public class MonarchIsSourceControllerCondition implements Condition {

    private final static MonarchIsSourceControllerCondition fInstance = new MonarchIsSourceControllerCondition();

    public static Condition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return source.getControllerId().equals(game.getMonarchId());
    }

    @Override
    public String toString() {
        return "if you're the monarch";
    }
}
