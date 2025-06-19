package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class PlayLandOrCastSpellFromExileTriggeredAbility extends TriggeredAbilityImpl {

    public PlayLandOrCastSpellFromExileTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        setTriggerPhrase("Whenever you play a land from exile or cast a spell from exile, ");
    }

    private PlayLandOrCastSpellFromExileTriggeredAbility(final PlayLandOrCastSpellFromExileTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PlayLandOrCastSpellFromExileTriggeredAbility copy() {
        return new PlayLandOrCastSpellFromExileTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LAND_PLAYED
                || event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId()) && event.getZone() == Zone.EXILED;
    }
}
