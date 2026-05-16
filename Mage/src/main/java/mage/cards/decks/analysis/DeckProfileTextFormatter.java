package mage.cards.decks.analysis;

import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;

import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Plain text renderer for deck profile diagnostics.
 */
public final class DeckProfileTextFormatter {

    private DeckProfileTextFormatter() {
    }

    public static String format(String title, DeckProfile profile) {
        StringBuilder sb = new StringBuilder();
        sb.append(title).append('\n');
        sb.append("Cards: ").append(profile.getCardCount())
                .append(" | Lands: ").append(profile.getLandCount())
                .append(" | Nonlands: ").append(profile.getNonLandCount())
                .append(" | Avg MV: ").append(String.format(Locale.US, "%.2f", profile.getAverageManaValue()))
                .append('\n');
        appendMap(sb, "Mana curve", profile.getManaCurve());
        appendMap(sb, "Color pips", profile.getColorPips());
        appendMap(sb, "Card types", profile.getCardTypeCounts());
        appendTopFeatures(sb, profile);
        appendTopMechanics(sb, profile);
        appendRoles(sb, profile);
        appendSynergies(sb, profile);
        appendFeatureClusters(sb, profile);
        appendNearestFeatureMatches(sb, profile);
        return sb.toString();
    }

    public static String formatCommanderDeck(DeckProfile mainDeck, DeckProfile commandZone) {
        StringBuilder sb = new StringBuilder();
        sb.append(format("Main deck", mainDeck));
        sb.append('\n').append(format("Sideboard / command zone", commandZone));
        appendCommanderAnchors(sb, commandZone);
        appendCommanderFeatureMatches(sb, mainDeck, commandZone);
        appendSynergyPackages(sb, mainDeck, commandZone);
        return sb.toString();
    }

    private static void appendMap(StringBuilder sb, String label, Map<?, Integer> values) {
        sb.append(label).append(": ");
        if (values.isEmpty()) {
            sb.append("(none)").append('\n');
            return;
        }
        sb.append(values.entrySet()
                .stream()
                .sorted(Comparator.comparing(entry -> sortKey(entry.getKey())))
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(", ")))
                .append('\n');
    }

    private static String sortKey(Object key) {
        if (key instanceof Integer) {
            return String.format("%03d", key);
        }
        if (key instanceof ColoredManaSymbol) {
            return String.valueOf(((ColoredManaSymbol) key).ordinal());
        }
        if (key instanceof CardType) {
            return ((CardType) key).name();
        }
        return String.valueOf(key);
    }

    private static void appendRoles(StringBuilder sb, DeckProfile profile) {
        sb.append("Roles: ");
        if (profile.getRoleCounts().isEmpty()) {
            sb.append("(none)").append('\n');
            return;
        }
        sb.append(profile.getRoleCounts().entrySet()
                .stream()
                .sorted(Map.Entry.<DeckRole, Integer>comparingByValue().reversed())
                .map(entry -> entry.getKey() + "=" + entry.getValue() + " (" + percent(profile.getRoleDensity(entry.getKey())) + ")")
                .collect(Collectors.joining(", ")))
                .append('\n');
    }

    private static void appendTopFeatures(StringBuilder sb, DeckProfile profile) {
        sb.append("Top features: ");
        if (profile.getFeatureCounts().isEmpty()) {
            sb.append("(none)").append('\n');
            return;
        }
        sb.append(profile.getFeatureCounts().entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey(String.CASE_INSENSITIVE_ORDER)))
                .limit(24)
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(", ")))
                .append('\n');
    }

    private static void appendTopMechanics(StringBuilder sb, DeckProfile profile) {
        sb.append("Top mechanics: ");
        if (profile.getMechanicCounts().isEmpty()) {
            sb.append("(none)").append('\n');
            return;
        }
        sb.append(profile.getMechanicCounts().entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey(String.CASE_INSENSITIVE_ORDER)))
                .limit(24)
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(", ")))
                .append('\n');
    }

    private static void appendSynergies(StringBuilder sb, DeckProfile profile) {
        sb.append("Synergies: ");
        if (profile.getSynergyScores().isEmpty()) {
            sb.append("(none)").append('\n');
            return;
        }
        sb.append(profile.getSynergyScores().entrySet()
                .stream()
                .sorted(Map.Entry.<DeckSynergy, Double>comparingByValue().reversed())
                .map(entry -> entry.getKey() + "=" + String.format(Locale.US, "%.4f", entry.getValue()))
                .collect(Collectors.joining(", ")))
                .append('\n');
    }

    private static void appendNearestFeatureMatches(StringBuilder sb, DeckProfile profile) {
        java.util.List<DeckFeatureSimilarity.CardMatch> matches = DeckFeatureSimilarity.nearestCards(profile, 8);
        if (matches.isEmpty()) {
            return;
        }
        sb.append("Nearest feature matches").append('\n');
        for (DeckFeatureSimilarity.CardMatch match : matches) {
            sb.append("- ").append(match.format()).append('\n');
        }
    }

    private static void appendFeatureClusters(StringBuilder sb, DeckProfile profile) {
        java.util.List<DeckFeatureSimilarity.CardCluster> clusters = DeckFeatureSimilarity.clusters(profile, 8);
        if (clusters.isEmpty()) {
            return;
        }
        sb.append("Feature clusters").append('\n');
        for (DeckFeatureSimilarity.CardCluster cluster : clusters) {
            sb.append("- ").append(cluster.format()).append('\n');
        }
    }

    private static void appendCommanderAnchors(StringBuilder sb, DeckProfile commandZone) {
        if (commandZone.getRoleCounts().isEmpty()) {
            return;
        }
        sb.append('\n').append("Commander anchors: ");
        sb.append(commandZone.getRoleCounts().entrySet()
                .stream()
                .sorted(Map.Entry.<DeckRole, Integer>comparingByValue().reversed())
                .map(entry -> entry.getKey() + "=" + formatCardCounts(commandZone.getRoleCardCounts(entry.getKey()), 4))
                .collect(Collectors.joining("; ")))
                .append('\n');
    }

    private static void appendCommanderFeatureMatches(StringBuilder sb, DeckProfile mainDeck, DeckProfile commandZone) {
        java.util.List<DeckFeatureSimilarity.CardMatch> matches = DeckFeatureSimilarity.commanderMatches(mainDeck, commandZone, 8);
        if (matches.isEmpty()) {
            return;
        }
        sb.append('\n').append("Commander feature matches").append('\n');
        for (DeckFeatureSimilarity.CardMatch match : matches) {
            sb.append("- ").append(match.format()).append('\n');
        }
    }

    private static void appendSynergyPackages(StringBuilder sb, DeckProfile mainDeck, DeckProfile commandZone) {
        sb.append('\n').append("Synergy packages").append('\n');
        DeckSynergyPackageService.buildPackages(mainDeck, commandZone).values()
                .stream()
                .filter(DeckSynergyPackage::hasAnyEvidence)
                .sorted(Comparator
                        .comparing(DeckSynergyPackage::isCommanderAnchored).reversed()
                        .thenComparing(DeckSynergyPackage::getComponentCoverage, Comparator.reverseOrder())
                        .thenComparing(packageInfo -> packageInfo.getSynergy().name()))
                .forEach(packageInfo -> appendSynergyPackage(sb, packageInfo));
    }

    private static void appendSynergyPackage(StringBuilder sb, DeckSynergyPackage packageInfo) {
        sb.append("- ")
                .append(packageInfo.getSynergy())
                .append(" coverage ")
                .append(packageInfo.getPresentComponentCount())
                .append('/')
                .append(packageInfo.getComponentCount());
        if (packageInfo.isCommanderAnchored()) {
            sb.append(" | commander anchored");
        }
        sb.append('\n');
        for (DeckRole role : packageInfo.getComponentRoles()) {
            if (!packageInfo.hasMainDeckEvidence(role) && !packageInfo.hasCommandZoneEvidence(role)) {
                continue;
            }
            sb.append("  ")
                    .append(role)
                    .append(": ");
            if (packageInfo.hasCommandZoneEvidence(role)) {
                sb.append("command zone ")
                        .append(formatCardCounts(packageInfo.getCommandZoneCards(role), 4));
                if (packageInfo.hasMainDeckEvidence(role)) {
                    sb.append("; ");
                }
            }
            if (packageInfo.hasMainDeckEvidence(role)) {
                sb.append("main deck ")
                        .append(formatCardCounts(packageInfo.getMainDeckCards(role), 8));
            }
            sb.append('\n');
        }
    }

    private static String formatCardCounts(Map<String, Integer> cardCounts, int limit) {
        String text = cardCounts.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey(String.CASE_INSENSITIVE_ORDER)))
                .limit(limit)
                .map(entry -> entry.getValue() > 1 ? entry.getKey() + " x" + entry.getValue() : entry.getKey())
                .collect(Collectors.joining(", "));
        if (cardCounts.size() > limit) {
            text += ", +" + (cardCounts.size() - limit) + " more";
        }
        return text;
    }

    private static String percent(double value) {
        return String.format(Locale.US, "%.1f%%", value * 100.0);
    }
}
