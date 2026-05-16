package mage.cards.decks.analysis;

import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Descriptive deck profile intended for AI scoring and player-facing deck insights.
 */
public final class DeckProfile implements Serializable {

    private final int cardCount;
    private final int landCount;
    private final int nonLandCount;
    private final double averageManaValue;
    private final Map<Integer, Integer> manaCurve;
    private final Map<ColoredManaSymbol, Integer> colorPips;
    private final Map<CardType, Integer> cardTypeCounts;
    private final Map<String, Integer> featureCounts;
    private final Map<String, Map<String, Integer>> featureCardCounts;
    private final Map<String, Map<String, Integer>> cardFeatures;
    private final Map<String, Integer> mechanicCounts;
    private final Map<String, Map<String, Integer>> mechanicCardCounts;
    private final Map<String, Map<String, Integer>> cardMechanics;
    private final Map<DeckRole, Integer> roleCounts;
    private final Map<DeckRole, Map<String, Integer>> roleCardCounts;
    private final Map<DeckSynergy, Double> synergyScores;

    DeckProfile(int cardCount, int landCount, int nonLandCount, double averageManaValue,
                Map<Integer, Integer> manaCurve,
                Map<ColoredManaSymbol, Integer> colorPips,
                Map<CardType, Integer> cardTypeCounts,
                Map<String, Integer> featureCounts,
                Map<String, Map<String, Integer>> featureCardCounts,
                Map<String, Map<String, Integer>> cardFeatures,
                Map<String, Integer> mechanicCounts,
                Map<String, Map<String, Integer>> mechanicCardCounts,
                Map<String, Map<String, Integer>> cardMechanics,
                Map<DeckRole, Integer> roleCounts,
                Map<DeckRole, Map<String, Integer>> roleCardCounts,
                Map<DeckSynergy, Double> synergyScores) {
        this.cardCount = cardCount;
        this.landCount = landCount;
        this.nonLandCount = nonLandCount;
        this.averageManaValue = averageManaValue;
        this.manaCurve = Collections.unmodifiableMap(new LinkedHashMap<>(manaCurve));
        EnumMap<ColoredManaSymbol, Integer> colorPipsCopy = new EnumMap<>(ColoredManaSymbol.class);
        colorPipsCopy.putAll(colorPips);
        this.colorPips = Collections.unmodifiableMap(colorPipsCopy);
        EnumMap<CardType, Integer> cardTypeCountsCopy = new EnumMap<>(CardType.class);
        cardTypeCountsCopy.putAll(cardTypeCounts);
        this.cardTypeCounts = Collections.unmodifiableMap(cardTypeCountsCopy);
        this.featureCounts = Collections.unmodifiableMap(new LinkedHashMap<>(featureCounts));
        this.featureCardCounts = copyFeatureCardCounts(featureCardCounts);
        this.cardFeatures = copyFeatureCardCounts(cardFeatures);
        this.mechanicCounts = Collections.unmodifiableMap(new LinkedHashMap<>(mechanicCounts));
        this.mechanicCardCounts = copyFeatureCardCounts(mechanicCardCounts);
        this.cardMechanics = copyFeatureCardCounts(cardMechanics);
        EnumMap<DeckRole, Integer> roleCountsCopy = new EnumMap<>(DeckRole.class);
        roleCountsCopy.putAll(roleCounts);
        this.roleCounts = Collections.unmodifiableMap(roleCountsCopy);
        this.roleCardCounts = copyRoleCardCounts(roleCardCounts);
        EnumMap<DeckSynergy, Double> synergyScoresCopy = new EnumMap<>(DeckSynergy.class);
        synergyScoresCopy.putAll(synergyScores);
        this.synergyScores = Collections.unmodifiableMap(synergyScoresCopy);
    }

    public int getCardCount() {
        return cardCount;
    }

    public int getLandCount() {
        return landCount;
    }

    public int getNonLandCount() {
        return nonLandCount;
    }

    public double getAverageManaValue() {
        return averageManaValue;
    }

    public Map<Integer, Integer> getManaCurve() {
        return manaCurve;
    }

    public Map<ColoredManaSymbol, Integer> getColorPips() {
        return colorPips;
    }

    public Map<CardType, Integer> getCardTypeCounts() {
        return cardTypeCounts;
    }

    public Map<String, Integer> getFeatureCounts() {
        return featureCounts;
    }

    public Map<String, Map<String, Integer>> getFeatureCardCounts() {
        return featureCardCounts;
    }

    public Map<String, Map<String, Integer>> getCardFeatures() {
        return cardFeatures;
    }

    public Map<String, Integer> getMechanicCounts() {
        return mechanicCounts;
    }

    public Map<String, Map<String, Integer>> getMechanicCardCounts() {
        return mechanicCardCounts;
    }

    public Map<String, Map<String, Integer>> getCardMechanics() {
        return cardMechanics;
    }

    public Map<String, Integer> getCardFeatures(String cardName) {
        return cardFeatures.getOrDefault(cardName, Collections.emptyMap());
    }

    public Map<String, Integer> getFeatureCardCounts(String feature) {
        return featureCardCounts.getOrDefault(feature, Collections.emptyMap());
    }

    public Map<String, Integer> getCardMechanics(String cardName) {
        return cardMechanics.getOrDefault(cardName, Collections.emptyMap());
    }

    public Map<String, Integer> getMechanicCardCounts(String mechanic) {
        return mechanicCardCounts.getOrDefault(mechanic, Collections.emptyMap());
    }

    public Map<DeckRole, Integer> getRoleCounts() {
        return roleCounts;
    }

    public Map<DeckRole, Map<String, Integer>> getRoleCardCounts() {
        return roleCardCounts;
    }

    public Map<String, Integer> getRoleCardCounts(DeckRole role) {
        return roleCardCounts.getOrDefault(role, Collections.emptyMap());
    }

    public Map<DeckSynergy, Double> getSynergyScores() {
        return synergyScores;
    }

    public int getRoleCount(DeckRole role) {
        return roleCounts.getOrDefault(role, 0);
    }

    public double getRoleDensity(DeckRole role) {
        return nonLandCount == 0 ? 0.0 : getRoleCount(role) / (double) nonLandCount;
    }

    public int getFeatureCount(String feature) {
        return featureCounts.getOrDefault(feature, 0);
    }

    public int getMechanicCount(String mechanic) {
        return mechanicCounts.getOrDefault(mechanic, 0);
    }

    public double getFeatureDensity(String feature) {
        return cardCount == 0 ? 0.0 : getFeatureCount(feature) / (double) cardCount;
    }

    public double getMechanicDensity(String mechanic) {
        return cardCount == 0 ? 0.0 : getMechanicCount(mechanic) / (double) cardCount;
    }

    public double getSynergyScore(DeckSynergy synergy) {
        return synergyScores.getOrDefault(synergy, 0.0);
    }

    private static Map<DeckRole, Map<String, Integer>> copyRoleCardCounts(Map<DeckRole, Map<String, Integer>> source) {
        EnumMap<DeckRole, Map<String, Integer>> copy = new EnumMap<>(DeckRole.class);
        for (Map.Entry<DeckRole, Map<String, Integer>> entry : source.entrySet()) {
            copy.put(entry.getKey(), Collections.unmodifiableMap(new LinkedHashMap<>(entry.getValue())));
        }
        return Collections.unmodifiableMap(copy);
    }

    private static Map<String, Map<String, Integer>> copyFeatureCardCounts(Map<String, Map<String, Integer>> source) {
        Map<String, Map<String, Integer>> copy = new LinkedHashMap<>();
        for (Map.Entry<String, Map<String, Integer>> entry : source.entrySet()) {
            copy.put(entry.getKey(), Collections.unmodifiableMap(new LinkedHashMap<>(entry.getValue())));
        }
        return Collections.unmodifiableMap(copy);
    }
}
