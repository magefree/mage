package mage.game.events;

import mage.MageObjectReference;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public class DefenderAttackedEvent extends GameEvent {

    private final Set<MageObjectReference> morSet = new HashSet<>();

    public DefenderAttackedEvent(UUID targetId, UUID playerId) {
        super(EventType.DEFENDER_ATTACKED, targetId, null, playerId);

    }

    public static void makeAddEvents(Map<UUID, Set<MageObjectReference>> morMapSet, UUID attackingPlayerId, Game game) {
        for (Map.Entry<UUID, Set<MageObjectReference>> entry : morMapSet.entrySet()) {
            DefenderAttackedEvent event = new DefenderAttackedEvent(entry.getKey(), attackingPlayerId);
            event.morSet.addAll(entry.getValue());
            game.addSimultaneousEvent(event);
        }
    }

    public List<Permanent> getAttackers(Game game) {
        return morSet
                .stream()
                .map(mor -> mor.getPermanent(game))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
