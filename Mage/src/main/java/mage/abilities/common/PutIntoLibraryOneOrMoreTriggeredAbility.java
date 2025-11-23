package mage.abilities.common;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.game.events.ZoneChangeEvent;

import java.util.Objects;

/**
 * @author TheElk801
 */
public class PutIntoLibraryOneOrMoreTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<ZoneChangeEvent> {

    public PutIntoLibraryOneOrMoreTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public PutIntoLibraryOneOrMoreTriggeredAbility(Effect effect, boolean optional) {
        this(Zone.BATTLEFIELD, effect, optional);
    }

    public PutIntoLibraryOneOrMoreTriggeredAbility(Zone zone, Effect effect, boolean optional) {
        super(zone, effect, optional);
        this.setTriggerPhrase("Whenever one or more cards are put into a library from anywhere, ");
    }

    private PutIntoLibraryOneOrMoreTriggeredAbility(final PutIntoLibraryOneOrMoreTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PutIntoLibraryOneOrMoreTriggeredAbility copy() {
        return new PutIntoLibraryOneOrMoreTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_BATCH;
    }

    @Override
    public boolean checkEvent(ZoneChangeEvent event, Game game) {
        return Zone.LIBRARY.match(event.getToZone());
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return this
                .getFilteredEvents((ZoneChangeBatchEvent) event, game)
                .stream()
                .map(GameEvent::getTargetId)
                .map(game::getCard)
                .anyMatch(Objects::nonNull);
    }
}
