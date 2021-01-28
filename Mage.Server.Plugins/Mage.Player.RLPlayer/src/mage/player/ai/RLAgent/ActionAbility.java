package mage.player.ai.RLAgent;
import mage.game.Game;
import mage.abilities.*;
import mage.abilities.common.PassAbility;
import mage.MageObject;


public class ActionAbility extends RLAction {
    public Ability ability;
    public ActionAbility(Game game, Ability abil){
        ability=abil;
        MageObject source=ability.getSourceObjectIfItStillExists(game);
        if(ability instanceof PassAbility){
            name="Ability:Pass";
        }
        else if(source==null){
            //logger.info("source is NULL!");
            name="Ability:NULL";
            //logger.info(ability.getRule());
        }
        else{
            name="Ability:"+source.getName();
        }
    }
}