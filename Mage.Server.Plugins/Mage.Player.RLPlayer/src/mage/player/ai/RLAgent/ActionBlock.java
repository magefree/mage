package mage.player.ai.RLAgent;
import mage.game.permanent.Permanent;

public class ActionBlock extends RLAction{
    public Permanent attacker;
    public Permanent blocker;
    public Boolean isBlock;
    public ActionBlock(Permanent attack,Permanent block, Boolean IsBlock){
        attacker=attack;
        blocker=block;
        name="Block:"+attack.getName()+" with "+blocker.getName()+":"+isBlock;
        isBlock=IsBlock;
    }

}
