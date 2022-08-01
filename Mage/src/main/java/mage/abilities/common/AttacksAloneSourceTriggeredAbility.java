
package mage.abilities.common;

import java.util.Objects;
import java.util.UUID;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;

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

    private static final String staticTriggerPhrase = "Whenever {this} attacks alone, ";

    public AttacksAloneSourceTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
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
        UUID creatureId = this.getSourceId();
        if(!game.isActivePlayer(this.controllerId) || creatureId == null) {
            return false;
        }

        if(!game.getCombat().attacksAlone() || !Objects.equals(creatureId, game.getCombat().getAttackers().get(0))) {
            return false;
        }

        UUID defender = game.getCombat().getDefenderId(creatureId);
        if (defender != null) {
            for (Effect effect: getEffects()) {
                effect.setTargetPointer(new FixedTarget(defender));
            }
        }
        return true;
    }

    @Override
    public String getStaticTriggerPhrase() {
        return staticTriggerPhrase;
    }
}
