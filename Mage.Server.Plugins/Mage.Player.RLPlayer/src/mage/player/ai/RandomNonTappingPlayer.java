package mage.player.ai;

import mage.abilities.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.MageObject;
import java.util.*;

public class RandomNonTappingPlayer extends RandomPlayer{
    public RandomNonTappingPlayer(String name) {
        super(name);
    }

    public RandomNonTappingPlayer(final RandomPlayer player) {
        super(player);
    }
    protected List<ActivatedAbility> getFilteredPlayableAbilities(Game game){
        List<ActivatedAbility> playables=getPlayableAbilities(game);
        List<ActivatedAbility> filtered=new ArrayList<ActivatedAbility>();
        for(int i=0;i<playables.size();i++){
            MageObject source=playables.get(i).getSourceObjectIfItStillExists(game);
            if(source!=null && source instanceof Permanent && source.isLand()){
                //Don't allow just tapping a land to be an action
                //May break lands with activated abilities
                continue;
            }
            filtered.add(playables.get(i));
        }
        return filtered;
    }
}
