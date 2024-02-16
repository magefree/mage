package mage.abilities.dynamicvalue;

import mage.cards.Card;
import mage.cards.Cards;
import mage.game.Game;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Used for when you need to know how attributes are assigned among cards, such as for party count
 * Can be adapted for various card attributes
 *
 * @author TheElk801
 */
public abstract class RoleAssignment<T> implements Serializable {

    protected final List<T> attributes = new ArrayList<>();

    protected RoleAssignment(T... attributes) {
        for (T attribute : attributes) {
            this.attributes.add(attribute);
        }
    }

    protected abstract Set<T> makeSet(Card card, Game game);

    private boolean attemptRearrange(T attribute, UUID uuid, Set<T> attributes, Map<T, UUID> attributeUUIDMap, Map<UUID, Set<T>> attributeSetMap) {
        UUID uuid1 = attributeUUIDMap.get(attribute);
        if (uuid1 == null) {
            return false;
        }
        Set<T> attributes1 = attributeSetMap.get(uuid1);
        for (T attribute1 : attributes1) {
            if (attribute == attribute1) {
                continue;
            }
            if (!attributeUUIDMap.containsKey(attribute1)) {
                attributeUUIDMap.put(attribute, uuid);
                attributeUUIDMap.put(attribute1, uuid1);
                return true;
            }
        }
        for (T attribute1 : attributes1) {
            if (attribute == attribute1) {
                continue;
            }
            if (attemptRearrange(attribute1, uuid1, attributes, attributeUUIDMap, attributeSetMap)) {
                attributeUUIDMap.put(attribute, uuid);
                attributeUUIDMap.put(attribute1, uuid1);
                return true;
            }
        }
        return false;
    }

    public int getRoleCount(Cards cards, Game game) {
        return getRoleCount(cards.getCards(game), game);
    }

    public int getRoleCount(Set<? extends Card> cards, Game game) {
        Map<UUID, Set<T>> attributeMap = new HashMap<>();
        cards.forEach(card -> attributeMap.put(card.getId(), this.makeSet(card, game)));
        if (attributeMap.size() < 2) {
            return attributeMap.size();
        }
        Set<T> availableTypes = attributeMap
                .values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        if (attributeMap.size() == 2) {
            return Math.min(2, availableTypes.size());
        }
        Map<T, UUID> attributeUUIDMap = new HashMap<>();
        for (Map.Entry<UUID, Set<T>> entry : attributeMap.entrySet()) {
            for (T attribute : entry.getValue()) {
                if (!attributeUUIDMap.containsKey(attribute)) {
                    attributeUUIDMap.put(attribute, entry.getKey());
                    break;
                }
            }
            if (attributeUUIDMap.size() >= availableTypes.size()) {
                return attributeUUIDMap.size();
            } else if (attributeUUIDMap.containsValue(entry.getKey())) {
                continue;
            } else {
                for (T attribute : entry.getValue()) {
                    if (attemptRearrange(attribute, entry.getKey(), entry.getValue(), attributeUUIDMap, attributeMap)) {
                        break;
                    }
                }
            }
        }
        return attributeUUIDMap.keySet().size();
    }
}
