package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 * @author Cguy7777
 */
public class DiesOneOrMoreCreaturesTriggeredAbility extends TriggeredAbilityImpl {

    public DiesOneOrMoreCreaturesTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public DiesOneOrMoreCreaturesTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever one or more creatures die, ");
    }

    private DiesOneOrMoreCreaturesTriggeredAbility(final DiesOneOrMoreCreaturesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DiesOneOrMoreCreaturesTriggeredAbility copy() {
        return new DiesOneOrMoreCreaturesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        for (ZoneChangeEvent zEvent : ((ZoneChangeBatchEvent) event).getEvents()) {
            if (!zEvent.isDiesEvent()) {
                continue;
            }
            Permanent permanent = game.getPermanentOrLKIBattlefield(zEvent.getTargetId());
            if (permanent != null && permanent.isCreature(game)) {
                return true;
            }
        }
        return false;
    }
}
