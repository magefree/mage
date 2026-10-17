package mage.game.events;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbility;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Raise events for normal triggers, ignore state based triggers from StateTriggeredAbility
 *
 * @author BetaSteward_at_googlemail.com
 */
public class NumberOfTriggersEvent extends GameEvent {

    private final GameEvent sourceEvent;
    private final TriggeredAbility sourceTrigger;

    public NumberOfTriggersEvent(TriggeredAbility triggeredAbility, GameEvent sourceEvent) {
        super(GameEvent.EventType.NUMBER_OF_TRIGGERS, null, triggeredAbility, triggeredAbility.getControllerId());
        this.sourceEvent = sourceEvent;
        this.sourceTrigger = triggeredAbility;
        this.amount = 1; // Number of times to trigger. Panharmonicon can change this.
    }

    public GameEvent getSourceEvent() {
        return sourceEvent;
    }

    public TriggeredAbility getSourceTrigger() {
        return sourceTrigger;
    }

    /**
     * Find all events that caused the trigger:
     * - single event: that event;
     * - batch event: only events that pass the trigger's filter (e.g. "one or more creatures you control die");
     */
    private List<GameEvent> getSourceEvents(Game game) {
        if (sourceEvent == null) {
            return Collections.emptyList();
        }
        if (sourceEvent instanceof BatchEvent) {
            if (sourceTrigger instanceof BatchTriggeredAbility) {
                return new ArrayList<>(((BatchTriggeredAbility<?>) sourceTrigger).getFilteredEvents((BatchEvent) sourceEvent, game));
            } else {
                return new ArrayList<>(((BatchEvent<?>) sourceEvent).getEvents());
            }
        }
        return Collections.singletonList(sourceEvent);
    }

    /**
     * Find all permanents whose entering the battlefield caused the trigger,
     * supports single and batch events (e.g. "one or more tokens enter"),
     * can be used by doublers like Panharmonicon
     */
    public List<Permanent> getEnteredPermanents(Game game) {
        List<Permanent> res = new ArrayList<>();
        for (GameEvent event : getSourceEvents(game)) {
            if (event.getType() == EventType.ENTERS_THE_BATTLEFIELD && event instanceof EntersTheBattlefieldEvent) {
                res.add(((EntersTheBattlefieldEvent) event).getTarget());
            } else if (event instanceof ZoneChangeEvent && ((ZoneChangeEvent) event).getToZone() == Zone.BATTLEFIELD) {
                res.add(((ZoneChangeEvent) event).getTarget());
            }
        }
        return res.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * Find all permanents whose leaving the battlefield caused the trigger (permanents info from the battlefield),
     * supports single and batch events (e.g. "one or more creatures die"),
     * can be used by doublers like Teysa Karlov
     */
    public List<Permanent> getLeftPermanents(Game game) {
        return getLeftEvents(game)
                .stream()
                .map(ZoneChangeEvent::getTarget)
                .collect(Collectors.toList());
    }

    /**
     * Same as getLeftPermanents, but for died permanents only
     */
    public List<Permanent> getDiedPermanents(Game game) {
        return getLeftEvents(game)
                .stream()
                .filter(ZoneChangeEvent::isDiesEvent)
                .map(ZoneChangeEvent::getTarget)
                .collect(Collectors.toList());
    }

    private List<ZoneChangeEvent> getLeftEvents(Game game) {
        return getSourceEvents(game)
                .stream()
                .filter(ZoneChangeEvent.class::isInstance)
                .map(ZoneChangeEvent.class::cast)
                .filter(event -> event.getFromZone() == Zone.BATTLEFIELD && event.getToZone() != Zone.BATTLEFIELD)
                .filter(ZoneChangeEvent::isPermanentMoved)
                .collect(Collectors.toList());
    }
}
