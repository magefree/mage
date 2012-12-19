
package mage.abilities.keyword;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;


/**
 *
 * @author Plopman
 */


public class FlankingAbility extends TriggeredAbilityImpl<FlankingAbility> {

    public FlankingAbility() {
        super(Constants.Zone.BATTLEFIELD, new BoostTargetEffect(-1, -1, Constants.Duration.EndOfTurn), false);
    }

    public FlankingAbility(final FlankingAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED && event.getTargetId().equals(this.getSourceId())) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if(permanent != null)
            {
                boolean hasFlankingAbility = false;
                for(Ability ability : permanent.getAbilities()){
                    if(ability instanceof FlankingAbility){
                        hasFlankingAbility = true;
                    }
                }
                
                if(!hasFlankingAbility){
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getSourceId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Flanking";
    }

    @Override
    public FlankingAbility copy() {
        return new FlankingAbility(this);
    }

    
}
