package mage.cards.decks.analysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Explainable view of one deck synergy and the cards that support each component.
 */
public final class DeckSynergyPackage implements Serializable {

    private final DeckSynergy synergy;
    private final List<DeckRole> componentRoles;
    private final Map<DeckRole, Map<String, Integer>> mainDeckCards;
    private final Map<DeckRole, Map<String, Integer>> commandZoneCards;

    DeckSynergyPackage(DeckSynergy synergy,
                       List<DeckRole> componentRoles,
                       Map<DeckRole, Map<String, Integer>> mainDeckCards,
                       Map<DeckRole, Map<String, Integer>> commandZoneCards) {
        this.synergy = synergy;
        this.componentRoles = Collections.unmodifiableList(new ArrayList<>(componentRoles));
        this.mainDeckCards = copyRoleCards(mainDeckCards);
        this.commandZoneCards = copyRoleCards(commandZoneCards);
    }

    public DeckSynergy getSynergy() {
        return synergy;
    }

    public List<DeckRole> getComponentRoles() {
        return componentRoles;
    }

    public Map<DeckRole, Map<String, Integer>> getMainDeckCards() {
        return mainDeckCards;
    }

    public Map<DeckRole, Map<String, Integer>> getCommandZoneCards() {
        return commandZoneCards;
    }

    public Map<String, Integer> getMainDeckCards(DeckRole role) {
        return mainDeckCards.getOrDefault(role, Collections.emptyMap());
    }

    public Map<String, Integer> getCommandZoneCards(DeckRole role) {
        return commandZoneCards.getOrDefault(role, Collections.emptyMap());
    }

    public boolean hasMainDeckEvidence(DeckRole role) {
        return !getMainDeckCards(role).isEmpty();
    }

    public boolean hasCommandZoneEvidence(DeckRole role) {
        return !getCommandZoneCards(role).isEmpty();
    }

    public boolean isCommanderAnchored() {
        for (DeckRole role : componentRoles) {
            if (hasCommandZoneEvidence(role)) {
                return true;
            }
        }
        return false;
    }

    public int getPresentComponentCount() {
        int count = 0;
        for (DeckRole role : componentRoles) {
            if (hasMainDeckEvidence(role) || hasCommandZoneEvidence(role)) {
                count++;
            }
        }
        return count;
    }

    public int getComponentCount() {
        return componentRoles.size();
    }

    public double getComponentCoverage() {
        return componentRoles.isEmpty() ? 0.0 : getPresentComponentCount() / (double) componentRoles.size();
    }

    public boolean hasAnyEvidence() {
        return getPresentComponentCount() > 0;
    }

    private static Map<DeckRole, Map<String, Integer>> copyRoleCards(Map<DeckRole, Map<String, Integer>> source) {
        EnumMap<DeckRole, Map<String, Integer>> copy = new EnumMap<>(DeckRole.class);
        for (Map.Entry<DeckRole, Map<String, Integer>> entry : source.entrySet()) {
            copy.put(entry.getKey(), Collections.unmodifiableMap(new java.util.LinkedHashMap<>(entry.getValue())));
        }
        return Collections.unmodifiableMap(copy);
    }
}
