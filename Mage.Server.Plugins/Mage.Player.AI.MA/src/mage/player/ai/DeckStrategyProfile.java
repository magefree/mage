package mage.player.ai;

import mage.cards.decks.analysis.DeckProfile;
import mage.cards.decks.analysis.DeckFeatureSimilarity;
import mage.cards.decks.analysis.DeckProfileService;
import mage.cards.decks.analysis.DeckRole;
import mage.cards.decks.analysis.DeckSynergy;
import mage.cards.decks.analysis.DeckSynergyPackage;
import mage.cards.decks.analysis.DeckSynergyPackageService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Numeric deck intent derived from generic deck profile signals.
 */
public final class DeckStrategyProfile {

    private static final int MAX_ROLE_WEIGHT = 180;
    private static final double MIN_PRIMARY_PACKAGE_COVERAGE = 0.5;
    private static final double MIN_COMMANDER_FEATURE_MATCH_SCORE = 0.45;
    private static final int MAX_PROFILE_FEATURE_CLUSTERS = 12;
    private static final int MAX_COMMANDER_MATCHES_PER_COMMANDER = 16;

    private final DeckProfile mainDeck;
    private final DeckProfile commandZone;
    private final Map<DeckRole, Integer> roleWeights;
    private final Map<DeckRole, List<String>> roleReasons;
    private final Map<DeckSynergy, DeckSynergyPackage> packages;
    private final Map<DeckSynergy, Integer> packageWeights;
    private final Map<DeckSynergy, String> packageDetails;
    private final Map<String, Integer> mechanicWeights;
    private final Map<String, String> mechanicDetails;
    private final Map<String, List<FeatureSignal>> featureSignalsByCard;

    private DeckStrategyProfile(DeckProfile mainDeck,
                                DeckProfile commandZone,
                                Map<DeckRole, Integer> roleWeights,
                                Map<DeckRole, List<String>> roleReasons,
                                Map<DeckSynergy, DeckSynergyPackage> packages,
                                Map<DeckSynergy, Integer> packageWeights,
                                Map<DeckSynergy, String> packageDetails,
                                Map<String, Integer> mechanicWeights,
                                Map<String, String> mechanicDetails,
                                Map<String, List<FeatureSignal>> featureSignalsByCard) {
        this.mainDeck = mainDeck;
        this.commandZone = commandZone;
        this.roleWeights = Collections.unmodifiableMap(new EnumMap<>(roleWeights));
        this.roleReasons = copyReasons(roleReasons);
        this.packages = Collections.unmodifiableMap(new EnumMap<>(packages));
        this.packageWeights = Collections.unmodifiableMap(new EnumMap<>(packageWeights));
        this.packageDetails = Collections.unmodifiableMap(new EnumMap<>(packageDetails));
        this.mechanicWeights = Collections.unmodifiableMap(new LinkedHashMap<>(mechanicWeights));
        this.mechanicDetails = Collections.unmodifiableMap(new LinkedHashMap<>(mechanicDetails));
        this.featureSignalsByCard = copyFeatureSignals(featureSignalsByCard);
    }

    public static DeckStrategyProfile fromProfiles(DeckProfile mainDeck, DeckProfile commandZone) {
        DeckProfile safeMainDeck = mainDeck == null ? DeckProfileService.analyze(Collections.emptyList()) : mainDeck;
        DeckProfile safeCommandZone = commandZone == null ? DeckProfileService.analyze(Collections.emptyList()) : commandZone;
        Map<DeckSynergy, DeckSynergyPackage> packages = DeckSynergyPackageService.buildPackages(safeMainDeck, safeCommandZone);

        Map<DeckRole, Integer> roleWeights = new EnumMap<>(DeckRole.class);
        Map<DeckRole, List<String>> roleReasons = new EnumMap<>(DeckRole.class);
        for (DeckRole role : DeckRole.values()) {
            int mainCount = safeMainDeck.getRoleCount(role);
            int commandCount = safeCommandZone.getRoleCount(role);
            if (mainCount == 0 && commandCount == 0) {
                continue;
            }
            double density = safeMainDeck.getNonLandCount() == 0 ? 0.0 : mainCount / (double) safeMainDeck.getNonLandCount();
            int baseWeight = role == DeckRole.THREAT
                    ? 8 + (int) Math.round(density * 55)
                    : 12 + (int) Math.round(density * 190);
            if (commandCount > 0) {
                baseWeight += 28;
                addReason(roleReasons, role, "commander role");
            }
            if (mainCount > 0) {
                addReason(roleReasons, role, "deck density " + mainCount + "/" + Math.max(1, safeMainDeck.getNonLandCount()));
            }
            roleWeights.put(role, baseWeight);
        }

        Map<DeckSynergy, Integer> packageWeights = new EnumMap<>(DeckSynergy.class);
        Map<DeckSynergy, String> packageDetails = new EnumMap<>(DeckSynergy.class);
        for (DeckSynergyPackage deckPackage : packages.values()) {
            if (!deckPackage.hasAnyEvidence() || deckPackage.getComponentCoverage() < MIN_PRIMARY_PACKAGE_COVERAGE) {
                continue;
            }
            int packageWeight = 18
                    + (int) Math.round(deckPackage.getComponentCoverage() * 42)
                    + (deckPackage.isCommanderAnchored() ? 20 : 0);
            packageWeights.put(deckPackage.getSynergy(), packageWeight);
            String detail = deckPackage.getPresentComponentCount() + "/" + deckPackage.getComponentCount()
                    + " components" + (deckPackage.isCommanderAnchored() ? ", commander anchored" : "");
            packageDetails.put(deckPackage.getSynergy(), detail);

            for (DeckRole role : deckPackage.getComponentRoles()) {
                if (safeMainDeck.getRoleCount(role) == 0 && safeCommandZone.getRoleCount(role) == 0) {
                    continue;
                }
                int rolePackageBonus = 18
                        + (int) Math.round(deckPackage.getComponentCoverage() * 26)
                        + (deckPackage.isCommanderAnchored() ? 10 : 0);
                roleWeights.put(role, roleWeights.getOrDefault(role, 0) + rolePackageBonus);
                addReason(roleReasons, role, deckPackage.getSynergy().name() + " " + detail);
            }
        }

        Map<String, Integer> mechanicWeights = new LinkedHashMap<>();
        Map<String, String> mechanicDetails = new LinkedHashMap<>();
        addMechanicWeights(safeMainDeck, safeCommandZone, mechanicWeights, mechanicDetails);

        for (Map.Entry<DeckRole, Integer> entry : new ArrayList<>(roleWeights.entrySet())) {
            roleWeights.put(entry.getKey(), Math.min(MAX_ROLE_WEIGHT, entry.getValue()));
        }
        Map<String, List<FeatureSignal>> featureSignalsByCard = buildFeatureSignals(safeMainDeck, safeCommandZone);
        return new DeckStrategyProfile(
                safeMainDeck,
                safeCommandZone,
                roleWeights,
                roleReasons,
                packages,
                packageWeights,
                packageDetails,
                mechanicWeights,
                mechanicDetails,
                featureSignalsByCard
        );
    }

    public DeckProfile getMainDeck() {
        return mainDeck;
    }

    public DeckProfile getCommandZone() {
        return commandZone;
    }

    public int getRoleWeight(DeckRole role) {
        return roleWeights.getOrDefault(role, 0);
    }

    public Map<DeckRole, Integer> getRoleWeights() {
        return roleWeights;
    }

    public String describeRole(DeckRole role) {
        return roleReasons.getOrDefault(role, Collections.emptyList()).stream()
                .collect(Collectors.joining("; "));
    }

    public int getPackageWeight(DeckSynergy synergy) {
        return packageWeights.getOrDefault(synergy, 0);
    }

    public String describePackage(DeckSynergy synergy) {
        return packageDetails.getOrDefault(synergy, "");
    }

    public int getMechanicWeight(String mechanic) {
        return mechanicWeights.getOrDefault(mechanic, 0);
    }

    public Map<String, Integer> getMechanicWeights() {
        return mechanicWeights;
    }

    public String describeMechanic(String mechanic) {
        return mechanicDetails.getOrDefault(mechanic, "");
    }

    public List<DeckRole> getPackageComponentRoles(DeckSynergy synergy) {
        DeckSynergyPackage deckPackage = packages.get(synergy);
        return deckPackage == null ? Collections.emptyList() : deckPackage.getComponentRoles();
    }

    public Set<DeckSynergy> getPrimarySynergiesAdvancedBy(Set<DeckRole> actionRoles) {
        if (actionRoles == null || actionRoles.isEmpty()) {
            return Collections.emptySet();
        }
        Set<DeckSynergy> result = EnumSet.noneOf(DeckSynergy.class);
        for (Map.Entry<DeckSynergy, Integer> entry : packageWeights.entrySet()) {
            DeckSynergyPackage deckPackage = packages.get(entry.getKey());
            if (deckPackage == null) {
                continue;
            }
            for (DeckRole role : deckPackage.getComponentRoles()) {
                if (actionRoles.contains(role)) {
                    result.add(entry.getKey());
                    break;
                }
            }
        }
        return result;
    }

    public List<FeatureSignal> getFeatureSignalsForCard(String cardName) {
        if (cardName == null || cardName.isEmpty()) {
            return Collections.emptyList();
        }
        return featureSignalsByCard.getOrDefault(cardName, Collections.emptyList());
    }

    private static Map<String, List<FeatureSignal>> buildFeatureSignals(DeckProfile mainDeck, DeckProfile commandZone) {
        Map<String, List<FeatureSignal>> signalsByCard = new LinkedHashMap<>();
        for (DeckFeatureSimilarity.CardCluster cluster : DeckFeatureSimilarity.clusters(mainDeck, MAX_PROFILE_FEATURE_CLUSTERS)) {
            String labels = String.join(" + ", cluster.getLabelFeatures());
            if (labels.isEmpty()) {
                continue;
            }
            int value = Math.max(5, Math.min(24,
                    5
                            + (int) Math.round(cluster.getAverageScore() * 12.0)
                            + Math.min(7, cluster.getCardCount() / 4)));
            String detail = labels
                    + " | " + cluster.getCardCount() + " cards"
                    + " | avg " + String.format(Locale.US, "%.2f", cluster.getAverageScore());
            for (String cardName : cluster.getCards()) {
                addFeatureSignal(signalsByCard, cardName, new FeatureSignal("profile:cluster", value, detail));
            }
        }

        for (DeckFeatureSimilarity.CardMatch match : DeckFeatureSimilarity.commanderMatches(
                mainDeck,
                commandZone,
                MAX_COMMANDER_MATCHES_PER_COMMANDER
        )) {
            if (match.getScore() < MIN_COMMANDER_FEATURE_MATCH_SCORE) {
                continue;
            }
            int value = Math.max(6, Math.min(28, (int) Math.round(match.getScore() * 24.0)));
            String detail = match.getLeft()
                    + " shared " + String.join(", ", match.getSharedFeatures())
                    + " | score " + String.format(Locale.US, "%.2f", match.getScore());
            addFeatureSignal(signalsByCard, match.getRight(), new FeatureSignal("profile:commander-match", value, detail));
        }
        return signalsByCard;
    }

    private static void addMechanicWeights(DeckProfile mainDeck,
                                           DeckProfile commandZone,
                                           Map<String, Integer> mechanicWeights,
                                           Map<String, String> mechanicDetails) {
        for (Map.Entry<String, Integer> entry : mainDeck.getMechanicCounts().entrySet()) {
            String mechanic = entry.getKey();
            int mainCount = entry.getValue();
            int commandCount = commandZone.getMechanicCount(mechanic);
            int value = mechanicWeight(mainDeck, mechanic, mainCount, commandCount);
            if (value <= 0) {
                continue;
            }
            mechanicWeights.put(mechanic, value);
            mechanicDetails.put(mechanic, "deck mechanic density " + mainCount + "/" + Math.max(1, mainDeck.getCardCount())
                    + (commandCount > 0 ? "; commander mechanic" : ""));
        }
        for (Map.Entry<String, Integer> entry : commandZone.getMechanicCounts().entrySet()) {
            String mechanic = entry.getKey();
            if (mechanicWeights.containsKey(mechanic)) {
                continue;
            }
            int value = mechanicWeight(mainDeck, mechanic, 0, entry.getValue());
            if (value <= 0) {
                continue;
            }
            mechanicWeights.put(mechanic, value);
            mechanicDetails.put(mechanic, "commander mechanic");
        }
    }

    private static int mechanicWeight(DeckProfile mainDeck, String mechanic, int mainCount, int commandCount) {
        if (mainCount <= 0 && commandCount <= 0) {
            return 0;
        }
        int cardCount = Math.max(1, mainDeck.getCardCount());
        int base = Math.min(18, 4 + (int) Math.round((mainCount / (double) cardCount) * 80.0));
        if (commandCount > 0) {
            base += 4;
        }
        if (mechanic.startsWith("SUBTYPE:")) {
            base = subtypeMechanicWeight(mainCount, commandCount);
        } else if (mechanic.startsWith("TRIBAL:")) {
            base = tribalMechanicWeight(mainCount, commandCount);
        }
        return Math.max(0, Math.min(24, base));
    }

    private static int subtypeMechanicWeight(int mainCount, int commandCount) {
        int value;
        if (mainCount >= 10) {
            value = 18;
        } else if (mainCount >= 6) {
            value = 14;
        } else if (mainCount >= 3) {
            value = 9;
        } else {
            value = 0;
        }
        return commandCount > 0 ? value + 4 : value;
    }

    private static int tribalMechanicWeight(int mainCount, int commandCount) {
        int value;
        if (mainCount >= 5) {
            value = 14;
        } else if (mainCount >= 2) {
            value = 9;
        } else {
            value = 0;
        }
        return commandCount > 0 ? value + 5 : value;
    }

    private static void addFeatureSignal(Map<String, List<FeatureSignal>> signalsByCard,
                                         String cardName,
                                         FeatureSignal signal) {
        signalsByCard.computeIfAbsent(cardName, key -> new ArrayList<>()).add(signal);
    }

    private static void addReason(Map<DeckRole, List<String>> reasons, DeckRole role, String reason) {
        reasons.computeIfAbsent(role, key -> new ArrayList<>()).add(reason);
    }

    private static Map<DeckRole, List<String>> copyReasons(Map<DeckRole, List<String>> source) {
        Map<DeckRole, List<String>> copy = new EnumMap<>(DeckRole.class);
        for (Map.Entry<DeckRole, List<String>> entry : source.entrySet()) {
            copy.put(entry.getKey(), Collections.unmodifiableList(new ArrayList<>(entry.getValue())));
        }
        return Collections.unmodifiableMap(copy);
    }

    private static Map<String, List<FeatureSignal>> copyFeatureSignals(Map<String, List<FeatureSignal>> source) {
        Map<String, List<FeatureSignal>> copy = new LinkedHashMap<>();
        for (Map.Entry<String, List<FeatureSignal>> entry : source.entrySet()) {
            copy.put(entry.getKey(), Collections.unmodifiableList(new ArrayList<>(entry.getValue())));
        }
        return Collections.unmodifiableMap(copy);
    }

    public static final class FeatureSignal {
        private final String label;
        private final int value;
        private final String detail;

        private FeatureSignal(String label, int value, String detail) {
            this.label = label;
            this.value = value;
            this.detail = detail;
        }

        public String getLabel() {
            return label;
        }

        public int getValue() {
            return value;
        }

        public String getDetail() {
            return detail;
        }
    }
}
