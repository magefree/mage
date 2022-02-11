package mage.abilities.keyword;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author Plopman
 */
public class FlankingAbility extends TriggeredAbilityImpl {

    public FlankingAbility() {
        super(Zone.BATTLEFIELD, new BoostTargetEffect(-1, -1, Duration.EndOfTurn), false);
    }

    public FlankingAbility(final FlankingAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null) {
                boolean hasFlankingAbility
                        = permanent.getAbilities().stream().anyMatch(ability -> ability instanceof FlankingAbility);

                if (!hasFlankingAbility) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getSourceId(), game));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "flanking";
    }

    @Override
    public FlankingAbility copy() {
        return new FlankingAbility(this);
    }

}
