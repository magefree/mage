package mage.players;

import mage.abilities.ActivatedAbility;
import mage.util.Copyable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Contains stats with all playable cards for the player
 *
 * @author JayDi85
 */
public class PlayableObjectsList implements Serializable, Copyable<PlayableObjectsList> {

    Map<UUID, PlayableObjectStats> objects = new HashMap<>();

    public PlayableObjectsList() {
    }

    public PlayableObjectsList(Map<UUID, List<ActivatedAbility>> playableObjects) {
        load(playableObjects);
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

    public void load(Map<UUID, List<ActivatedAbility>> playableObjects) {
        objects.clear();
        playableObjects.forEach((objectId, list) -> {
            objects.put(objectId, new PlayableObjectStats(list));
        });
    }

    public boolean containsObject(UUID objectId) {
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
}