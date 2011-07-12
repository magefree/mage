/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.common;

import mage.Constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North, Loki
 */
public class BlocksOrBecomesBlockedTriggeredAbility extends TriggeredAbilityImpl<BlocksOrBecomesBlockedTriggeredAbility> {

    public BlocksOrBecomesBlockedTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public BlocksOrBecomesBlockedTriggeredAbility(final BlocksOrBecomesBlockedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.BLOCKER_DECLARED) {
            if (event.getSourceId().equals(this.getSourceId())) {
                for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
            if (event.getTargetId().equals(this.getSourceId())) {
                for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getSourceId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} blocks or becomes blocked, " + super.getRule();
    }

    @Override
    public BlocksOrBecomesBlockedTriggeredAbility copy() {
        return new BlocksOrBecomesBlockedTriggeredAbility(this);
    }
}
