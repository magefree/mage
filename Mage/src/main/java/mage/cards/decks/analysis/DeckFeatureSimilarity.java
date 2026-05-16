package mage.cards.decks.analysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Deck-local similarity over sparse card feature vectors.
 */
public final class DeckFeatureSimilarity {

    private static final double CLUSTER_EDGE_THRESHOLD = 0.65;

    private DeckFeatureSimilarity() {
    }

    public static List<CardMatch> nearestCards(DeckProfile profile, int limit) {
        List<Map.Entry<String, Map<String, Integer>>> cards = nonLandCards(profile);
        List<CardMatch> matches = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            for (int j = i + 1; j < cards.size(); j++) {
                Map.Entry<String, Map<String, Integer>> left = cards.get(i);
                Map.Entry<String, Map<String, Integer>> right = cards.get(j);
                double score = cosine(left.getValue(), right.getValue());
                List<String> sharedFeatures = sharedFeatures(left.getValue(), right.getValue(), 5);
                if (score > 0.0 && hasMeaningfulSharedFeatures(sharedFeatures)) {
                    matches.add(new CardMatch(left.getKey(), right.getKey(), score, sharedFeatures));
                }
            }
        }
        return top(matches, limit);
    }

    public static List<CardMatch> commanderMatches(DeckProfile mainDeck, DeckProfile commandZone, int limitPerCommander) {
        List<CardMatch> matches = new ArrayList<>();
        for (Map.Entry<String, Map<String, Integer>> commander : nonLandCards(commandZone)) {
            for (CardMatch match : nearestCards(commander.getKey(), commander.getValue(), mainDeck, limitPerCommander)) {
                matches.add(match);
            }
        }
        return matches;
    }

    public static List<CardCluster> clusters(DeckProfile profile, int limit) {
        List<Map.Entry<String, Map<String, Integer>>> cards = nonLandCards(profile);
        if (cards.size() < 2) {
            return Collections.emptyList();
        }

        int[] parent = new int[cards.size()];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        double[][] scores = new double[cards.size()][cards.size()];
        for (int i = 0; i < cards.size(); i++) {
            for (int j = i + 1; j < cards.size(); j++) {
                double score = cosine(cards.get(i).getValue(), cards.get(j).getValue());
                List<String> sharedFeatures = sharedFeatures(cards.get(i).getValue(), cards.get(j).getValue(), 5);
                if (score >= CLUSTER_EDGE_THRESHOLD && hasClusterSharedFeatures(sharedFeatures)) {
                    scores[i][j] = score;
                    union(parent, i, j);
                }
            }
        }

        Map<Integer, List<Integer>> components = new LinkedHashMap<>();
        for (int i = 0; i < cards.size(); i++) {
            components.computeIfAbsent(find(parent, i), key -> new ArrayList<>()).add(i);
        }

        return components.values()
                .stream()
                .filter(component -> component.size() >= 3)
                .map(component -> toCluster(cards, scores, component))
                .filter(cluster -> !cluster.getLabelFeatures().isEmpty())
                .sorted(Comparator
                        .comparing(CardCluster::getCardCount).reversed()
                        .thenComparing(CardCluster::getAverageScore, Comparator.reverseOrder()))
                .limit(Math.max(0, limit))
                .collect(Collectors.toList());
    }

    public static List<CardMatch> nearestCards(String anchorName, Map<String, Integer> anchorFeatures, DeckProfile profile, int limit) {
        List<CardMatch> matches = new ArrayList<>();
        for (Map.Entry<String, Map<String, Integer>> card : nonLandCards(profile)) {
            double score = cosine(anchorFeatures, card.getValue());
            List<String> sharedFeatures = sharedFeatures(anchorFeatures, card.getValue(), 5);
            if (score > 0.0 && hasMeaningfulSharedFeatures(sharedFeatures)) {
                matches.add(new CardMatch(anchorName, card.getKey(), score, sharedFeatures));
            }
        }
        return top(matches, limit);
    }

    public static double cosine(Map<String, Integer> left, Map<String, Integer> right) {
        if (left.isEmpty() || right.isEmpty()) {
            return 0.0;
        }
        double dot = 0.0;
        double leftNorm = 0.0;
        double rightNorm = 0.0;
        for (Map.Entry<String, Integer> entry : left.entrySet()) {
            double value = entry.getValue() * featureWeight(entry.getKey());
            leftNorm += value * value;
            Integer rightValue = right.get(entry.getKey());
            if (rightValue != null) {
                dot += value * rightValue * featureWeight(entry.getKey());
            }
        }
        for (Map.Entry<String, Integer> entry : right.entrySet()) {
            double value = entry.getValue() * featureWeight(entry.getKey());
            rightNorm += value * value;
        }
        if (leftNorm == 0.0 || rightNorm == 0.0) {
            return 0.0;
        }
        return dot / (Math.sqrt(leftNorm) * Math.sqrt(rightNorm));
    }

    private static List<Map.Entry<String, Map<String, Integer>>> nonLandCards(DeckProfile profile) {
        return profile.getCardFeatures().entrySet()
                .stream()
                .filter(entry -> !entry.getValue().containsKey("type:land"))
                .collect(Collectors.toList());
    }

    private static List<CardMatch> top(List<CardMatch> matches, int limit) {
        return matches.stream()
                .sorted(Comparator
                        .comparing(CardMatch::getScore).reversed()
                        .thenComparing(CardMatch::getLeft, String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(CardMatch::getRight, String.CASE_INSENSITIVE_ORDER))
                .limit(Math.max(0, limit))
                .collect(Collectors.toList());
    }

    private static List<String> sharedFeatures(Map<String, Integer> left, Map<String, Integer> right, int limit) {
        Map<String, Double> shared = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : left.entrySet()) {
            Integer rightValue = right.get(entry.getKey());
            if (rightValue != null && isExplainable(entry.getKey())) {
                shared.put(entry.getKey(), Math.min(entry.getValue(), rightValue) * featureWeight(entry.getKey()));
            }
        }
        return shared.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey(String.CASE_INSENSITIVE_ORDER)))
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private static CardCluster toCluster(List<Map.Entry<String, Map<String, Integer>>> cards, double[][] scores, List<Integer> component) {
        List<String> cardNames = component.stream()
                .map(index -> cards.get(index).getKey())
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.toList());
        Map<String, Double> featureScores = new LinkedHashMap<>();
        for (int index : component) {
            for (Map.Entry<String, Integer> feature : cards.get(index).getValue().entrySet()) {
                if (isClusterLabelFeature(feature.getKey())) {
                    featureScores.put(feature.getKey(), featureScores.getOrDefault(feature.getKey(), 0.0)
                            + feature.getValue() * featureWeight(feature.getKey()));
                }
            }
        }
        List<String> labels = featureScores.entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 1.0)
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey(String.CASE_INSENSITIVE_ORDER)))
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        double total = 0.0;
        int count = 0;
        for (int i = 0; i < component.size(); i++) {
            for (int j = i + 1; j < component.size(); j++) {
                int left = component.get(i);
                int right = component.get(j);
                double score = left < right ? scores[left][right] : scores[right][left];
                if (score > 0.0) {
                    total += score;
                    count++;
                }
            }
        }
        return new CardCluster(labels, cardNames, count == 0 ? 0.0 : total / count);
    }

    private static boolean isExplainable(String feature) {
        return !feature.startsWith("manaValue:")
                && !feature.startsWith("manaValueBand:")
                && !feature.startsWith("costSymbol:")
                && !feature.startsWith("color:");
    }

    private static boolean hasMeaningfulSharedFeatures(List<String> sharedFeatures) {
        return sharedFeatures.stream().anyMatch(feature -> !feature.startsWith("type:"));
    }

    private static boolean hasClusterSharedFeatures(List<String> sharedFeatures) {
        long specificCount = sharedFeatures.stream().filter(DeckFeatureSimilarity::isSpecificSharedFeature).count();
        return specificCount >= 2;
    }

    private static boolean isSpecificSharedFeature(String feature) {
        return isClusterLabelFeature(feature)
                && !"verb:add".equals(feature)
                && !feature.startsWith("type:");
    }

    private static boolean isClusterLabelFeature(String feature) {
        return isExplainable(feature)
                && !"object:card".equals(feature)
                && !"object:creature".equals(feature)
                && !"object:mana".equals(feature)
                && !"object:permanent".equals(feature)
                && !"object:player".equals(feature);
    }

    private static int find(int[] parent, int index) {
        if (parent[index] != index) {
            parent[index] = find(parent, parent[index]);
        }
        return parent[index];
    }

    private static void union(int[] parent, int left, int right) {
        int leftRoot = find(parent, left);
        int rightRoot = find(parent, right);
        if (leftRoot != rightRoot) {
            parent[rightRoot] = leftRoot;
        }
    }

    private static double featureWeight(String feature) {
        if (feature.startsWith("costSymbol:")) {
            return 0.15;
        }
        if (feature.startsWith("color:")) {
            return 0.25;
        }
        if (feature.startsWith("manaValue:")) {
            return 0.20;
        }
        if (feature.startsWith("manaValueBand:")) {
            return 0.30;
        }
        if (feature.startsWith("type:")) {
            return 0.45;
        }
        if ("object:card".equals(feature)
                || "object:creature".equals(feature)
                || "object:mana".equals(feature)
                || "object:permanent".equals(feature)
                || "object:player".equals(feature)) {
            return 0.50;
        }
        return 1.0;
    }

    public static final class CardMatch implements Serializable {
        private final String left;
        private final String right;
        private final double score;
        private final List<String> sharedFeatures;

        private CardMatch(String left, String right, double score, List<String> sharedFeatures) {
            this.left = left;
            this.right = right;
            this.score = score;
            this.sharedFeatures = Collections.unmodifiableList(new ArrayList<>(sharedFeatures));
        }

        public String getLeft() {
            return left;
        }

        public String getRight() {
            return right;
        }

        public double getScore() {
            return score;
        }

        public List<String> getSharedFeatures() {
            return sharedFeatures;
        }

        public String format() {
            String detail = sharedFeatures.isEmpty()
                    ? ""
                    : " | shared " + String.join(", ", sharedFeatures);
            return left + " -> " + right + " (" + String.format(Locale.US, "%.2f", score) + ")" + detail;
        }
    }

    public static final class CardCluster implements Serializable {
        private final List<String> labelFeatures;
        private final List<String> cards;
        private final double averageScore;

        private CardCluster(List<String> labelFeatures, List<String> cards, double averageScore) {
            this.labelFeatures = Collections.unmodifiableList(new ArrayList<>(labelFeatures));
            this.cards = Collections.unmodifiableList(new ArrayList<>(cards));
            this.averageScore = averageScore;
        }

        public List<String> getLabelFeatures() {
            return labelFeatures;
        }

        public List<String> getCards() {
            return cards;
        }

        public int getCardCount() {
            return cards.size();
        }

        public double getAverageScore() {
            return averageScore;
        }

        public String format() {
            String label = String.join(" + ", labelFeatures);
            String cardText = cards.stream().limit(10).collect(Collectors.joining(", "));
            if (cards.size() > 10) {
                cardText += ", +" + (cards.size() - 10) + " more";
            }
            return label + " | " + cards.size() + " cards | avg "
                    + String.format(Locale.US, "%.2f", averageScore)
                    + " | " + cardText;
        }
    }
}
