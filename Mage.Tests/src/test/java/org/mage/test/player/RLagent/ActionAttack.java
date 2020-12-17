package org.mage.test.player.RLagent;


import mage.game.permanent.Permanent;


public class ActionAttack extends RLAction{
    public Permanent perm;
    public Boolean isAttack;
    public ActionAttack(Permanent p, Boolean attck){
        perm=p;
        isAttack=attck;
    }
}
