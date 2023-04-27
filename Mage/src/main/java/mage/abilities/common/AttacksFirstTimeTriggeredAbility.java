
package mage.abilities.common;

import mage.MageObjectReference;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author TheElk801
 */
public class AttacksFirstTimeTriggeredAbility extends TriggeredAbilityImpl {

    public AttacksFirstTimeTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.addWatcher(new AttackedThisTurnWatcher());
        setTriggerPhrase("Whenever {this} attacks for the first time each turn, ");
    }

    public AttacksFirstTimeTriggeredAbility(final AttacksFirstTimeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(this.getSourceId())) {
            return false;
        }
        AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        if (watcher == null) {
            return false;
        }
        Permanent sourcePerm = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (sourcePerm == null) {
            return false;
        }
        for (MageObjectReference mor : watcher.getAttackedThisTurnCreaturesCounts().keySet()) {
            if (mor.refersTo(sourcePerm, game)
                    && watcher.getAttackedThisTurnCreaturesCounts().get(mor) > 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public AttacksFirstTimeTriggeredAbility copy() {
        return new AttacksFirstTimeTriggeredAbility(this);
    }
}
