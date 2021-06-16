package mage.player.ai.RLAgent;

public class ActionTarget {
    public Permanent attacker;
    public Permanent blocker;
    public Boolean isTarget;
    public ActionTarget(Permanent attack,Permanent block, boolean isTarget){
        this.isTarget=isTarget;
        if(isBlock){
            name="Block:"+attack.getName()+" with "+blocker.getName()+":"+isBlock;
        }else{
            name="Block:none";
        }

        
        this.isBlock=isBlock;
    }
}
