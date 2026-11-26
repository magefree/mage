package mage.players;

import mage.abilities.ActivatedAbility;
import mage.util.Copyable;

import java.io.Serializable;
import java.util.*;

/**
 * Contains stats with all playable cards for the player
 *
 * @author JayDi85
 */
public class PlayableObjectsList implements Serializable, Copyable<PlayableObjectsList> {

    Map<UUID, PlayableObjectStats> objects = new HashMap<>();

    public PlayableObjectsList() {
    }

    public PlayableObjectsList(Map<UUID, List<ActivatedAbility>> playableObjects, Map<UUID, List<String>> costsToPay) {
        load(playableObjects, costsToPay);
    }

    protected PlayableObjectsList(final PlayableObjectsList source) {
        source.objects.entrySet().forEach(entry -> {
            this.objects.put(entry.getKey(), entry.getValue().copy());
        });
    }

    @Override
    public PlayableObjectsList copy() {
        return new PlayableObjectsList(this);
    }

    /**
     * @param costsToPay real costs to pay instead printed costs (object id -> mana cost texts)
     */
    public void load(Map<UUID, List<ActivatedAbility>> playableObjects, Map<UUID, List<String>> costsToPay) {
        objects.clear();
        playableObjects.forEach((objectId, list) -> {
            objects.put(objectId, new PlayableObjectStats(list, costsToPay.getOrDefault(objectId, Collections.emptyList())));
        });

        // objects with a real cost to pay, but without any playable ability
        // (example: not enough mana or sorcery on opponent's turn)
        costsToPay.forEach((objectId, costs) -> {
            if (!objects.containsKey(objectId)) {
                objects.put(objectId, new PlayableObjectStats(Collections.emptyList(), costs));
            }
        });
    }

    /**
     * Object has playable abilities, so it can be played by the player
     */
    public boolean containsObject(UUID objectId) {
        return getPlayableAmount(objectId) > 0;
    }

    /**
     * Object has any info for GUI: playable abilities, real costs to pay or both
     * (a card can have a real cost to pay without being playable, example: not enough mana)
     */
    public boolean hasObject(UUID objectId) {
        return objects.containsKey(objectId);
    }

    public boolean isEmpty() {
        return objects.isEmpty();
    }

    public PlayableObjectStats getStats(UUID objectId) {
        if (objects.containsKey(objectId)) {
            return objects.get(objectId).copy();
        } else {
            return new PlayableObjectStats();
        }
    }

    public int getPlayableAmount(UUID objectId) {
        if (objects.containsKey(objectId)) {
            return objects.get(objectId).getPlayableAmount();
        } else {
            return 0;
        }
    }

    public Map<UUID, PlayableObjectStats> getObjects() {
        return objects;
    }
}