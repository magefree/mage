package mage.player.ai.RLAgent;

import mage.abilities.*;


public class ActionAbility extends RLAction {
    public Ability ability;
    public ActionAbility(Ability abil){
        ability=abil;
    }
}