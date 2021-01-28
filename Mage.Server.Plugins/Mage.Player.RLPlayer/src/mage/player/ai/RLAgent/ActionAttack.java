package mage.player.ai.RLAgent;


import mage.game.permanent.Permanent;


public class ActionAttack extends RLAction{
    public Permanent perm;
    public Boolean isAttack;
    public ActionAttack(Permanent p, Boolean attck){
        perm=p;
        name="Attack:"+p.getName()+":"+attck;
        isAttack=attck;
    }
}
