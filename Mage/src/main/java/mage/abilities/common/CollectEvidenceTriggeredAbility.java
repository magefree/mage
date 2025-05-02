package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class CollectEvidenceTriggeredAbility extends TriggeredAbilityImpl {

    public CollectEvidenceTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever you collect evidence, ");
    }

    private CollectEvidenceTriggeredAbility(final CollectEvidenceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CollectEvidenceTriggeredAbility copy() {
        return new CollectEvidenceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.EVIDENCE_COLLECTED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId());
    }
}
