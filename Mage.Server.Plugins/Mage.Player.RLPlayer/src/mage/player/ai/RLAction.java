package mage.player.ai;

import java.util.ArrayList;
import java.util.*;
import mage.player.ai.RLAgent.HParams;

public class RLAction {
    List<String> names;
    public RLAction(String actName){
        names=new ArrayList<String>();
        names.add(actName);
        names.add("None");
    }
    public RLAction(String p1, String p2){
        names=new ArrayList<String>();
        names.add(p1);
        names.add(p2);
    }
    public List<String> getNames(){
        return names;
    }
}
