package mage.player.ai.RLAgent;
import mage.game.permanent.Permanent;

public class ActionBlock extends RLAction{
    public Permanent attacker;
    public Permanent blocker;
    public Boolean isBlock;
    public ActionBlock(Permanent attack,Permanent block, boolean isBlock){
        attacker=attack;
        blocker=block;
        if(isBlock){
            name="Block:"+attack.getName()+" with "+blocker.getName()+":"+isBlock;
        }else{
            name="Block:none";
        }

        
        this.isBlock=isBlock;
    }

}
