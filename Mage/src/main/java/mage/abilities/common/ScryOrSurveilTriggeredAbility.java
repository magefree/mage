package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author muz
 */
public class ScryOrSurveilTriggeredAbility extends TriggeredAbilityImpl {

    public ScryOrSurveilTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public ScryOrSurveilTriggeredAbility(Effect effect, boolean optional) {
        this(Zone.BATTLEFIELD, effect, optional);
    }

    public ScryOrSurveilTriggeredAbility(Zone zone, Effect effect, boolean optional) {
        super(zone, effect, optional);
        setTriggerPhrase("Whenever you scry or surveil, ");
    }

    private ScryOrSurveilTriggeredAbility(final ScryOrSurveilTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ScryOrSurveilTriggeredAbility copy() {
        return new ScryOrSurveilTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SCRIED
                || event.getType() == GameEvent.EventType.SURVEILED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (isControlledBy(event.getPlayerId())) {
            this.getEffects().setValue("amount", event.getAmount());
            return true;
        }
        return false;
    }
}
