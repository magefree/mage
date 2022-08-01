
package mage.abilities.common;

import java.util.Objects;
import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LoneFox
 */
public class AttacksAloneSourceTriggeredAbility extends TriggeredAbilityImpl {

    public AttacksAloneSourceTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        setTriggerPhrase("Whenever {this} attacks alone, ");
    }

    public AttacksAloneSourceTriggeredAbility(final AttacksAloneSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AttacksAloneSourceTriggeredAbility copy() {
        return new AttacksAloneSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if(game.isActivePlayer(this.controllerId) ) {
            UUID creatureId = this.getSourceId();
            if(creatureId != null) {
                if(game.getCombat().attacksAlone() && Objects.equals(creatureId, game.getCombat().getAttackers().get(0))) {
                    UUID defender = game.getCombat().getDefenderId(creatureId);
                    if(defender != null) {
                        for(Effect effect: getEffects()) {
                            effect.setTargetPointer(new FixedTarget(defender));
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
