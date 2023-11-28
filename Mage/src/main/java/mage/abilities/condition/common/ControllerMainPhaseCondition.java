/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

/**
 *
 * @author jeffwadsworth
 */
public enum ControllerMainPhaseCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.isActivePlayer(source.getControllerId())
                && game.isMainPhase();
    }

    @Override
    public String toString() {
        return "If this is your main phase,";
    }
}
