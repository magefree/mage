package mage.cards.decks.analysis;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.SubType;

import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Builds generic, explainable deck profiles from cards.
 */
public final class DeckProfileService {

    private static final int HIGH_MANA_VALUE_BUCKET = 7;
    private static final Pattern OWN_SACRIFICE_PATTERN = Pattern.compile(
            "\\bsacrifice\\s+(?:x|all|any number of|one or more|another|a|an|one|two|three|four|five|six|seven|eight|nine|ten)\\b"
    );
    private static final Pattern OPPONENT_SACRIFICE_PATTERN = Pattern.compile(
            "\\b(?:target |each |any number of target )?opponents?\\b[^.]*\\bsacrifices?\\b"
                    + "|\\bsacrifices?\\b[^.]*\\bopponents?\\b"
                    + "|\\beach player sacrifices?\\b"
    );
    private static final Pattern SACRIFICE_SYNERGY_OBJECT_PATTERN = Pattern.compile(
            "\\b(?:creature|permanent|artifact|enchantment|token|treasure|clue|food|blood)s?\\b"
    );

    private DeckProfileService() {
    }

    public static DeckProfile analyze(Deck deck) {
        return analyze(deck.getMaindeckCards());
    }

    public static DeckProfile analyze(Collection<? extends Card> cards) {
        Map<Integer, Integer> manaCurve = new LinkedHashMap<>();
        Map<ColoredManaSymbol, Integer> colorPips = new EnumMap<>(ColoredManaSymbol.class);
        Map<CardType, Integer> cardTypeCounts = new EnumMap<>(CardType.class);
        Map<String, Integer> featureCounts = new LinkedHashMap<>();
        Map<String, Map<String, Integer>> featureCardCounts = new LinkedHashMap<>();
        Map<String, Map<String, Integer>> cardFeatures = new LinkedHashMap<>();
        Map<String, Integer> mechanicCounts = new LinkedHashMap<>();
        Map<String, Map<String, Integer>> mechanicCardCounts = new LinkedHashMap<>();
        Map<String, Map<String, Integer>> cardMechanics = new LinkedHashMap<>();
        Map<DeckRole, Integer> roleCounts = new EnumMap<>(DeckRole.class);
        Map<DeckRole, Map<String, Integer>> roleCardCounts = new EnumMap<>(DeckRole.class);

        int cardCount = 0;
        int landCount = 0;
        int nonLandCount = 0;
        int totalManaValue = 0;

        for (Card card : cards) {
            cardCount++;
            for (CardType cardType : card.getCardType()) {
                increment(cardTypeCounts, cardType);
            }
            if (card.isLand()) {
                landCount++;
            } else {
                nonLandCount++;
                totalManaValue += card.getManaValue();
                increment(manaCurve, Math.min(card.getManaValue(), HIGH_MANA_VALUE_BUCKET));
            }
            addColorPips(colorPips, card);
            CardFeatureVector featureVector = CardFeatureVector.from(card);
            addFeatures(featureCounts, featureCardCounts, card, featureVector);
            cardFeatures.putIfAbsent(card.getName(), new LinkedHashMap<>(featureVector.getFeatures()));
            Set<String> mechanics = classifyMechanics(card);
            addMechanics(mechanicCounts, mechanicCardCounts, card, mechanics);
            cardMechanics.putIfAbsent(card.getName(), asCountMap(mechanics));
            for (DeckRole role : classifyWithRepository(card)) {
                increment(roleCounts, role);
                incrementRoleCard(roleCardCounts, role, card.getName());
            }
        }

        double averageManaValue = nonLandCount == 0 ? 0.0 : totalManaValue / (double) nonLandCount;
        return new DeckProfile(
                cardCount,
                landCount,
                nonLandCount,
                averageManaValue,
                manaCurve,
                colorPips,
                cardTypeCounts,
                featureCounts,
                featureCardCounts,
                cardFeatures,
                mechanicCounts,
                mechanicCardCounts,
                cardMechanics,
                roleCounts,
                roleCardCounts,
                buildSynergyScores(roleCounts, nonLandCount)
        );
    }

    public static Set<DeckRole> classify(Card card) {
        return classifyWithRepository(card);
    }

    public static Set<String> classifyMechanics(Card card) {
        Set<String> generatedMechanics = CardRoleRepository.getInstance().lookupMechanics(card);
        Set<String> inferredMechanics = classifyMechanicsFromCard(card);
        Set<String> mechanics;
        if (generatedMechanics.isEmpty()) {
            mechanics = inferredMechanics;
        } else if (inferredMechanics.isEmpty()) {
            mechanics = generatedMechanics;
        } else {
            mechanics = new java.util.TreeSet<>(generatedMechanics);
            mechanics.addAll(inferredMechanics);
        }
        return normalizeMechanics(card, mechanics);
    }

    static Set<DeckRole> classifyFromCard(Card card) {
        EnumSet<DeckRole> roles = EnumSet.noneOf(DeckRole.class);
        String rules = String.join(" ", card.getRules()).toLowerCase(Locale.ROOT);
        String costs = getCostsText(card).toLowerCase(Locale.ROOT);

        if (card.isCreature() || card.isPlaneswalker()) {
            roles.add(DeckRole.THREAT);
        }
        boolean hasXInManaCost = hasXInManaCost(card);
        if (card.isCreature() && card.getManaValue() <= 2 && !hasXInManaCost) {
            roles.add(DeckRole.CHEAP_THREAT);
        }
        if (card.isCreature()
                && (card.getManaValue() >= 5
                || card.getPower().getValue() >= 4)) {
            roles.add(DeckRole.LARGE_THREAT);
        }
        if (card.isInstantOrSorcery() && isCombatTrickText(rules)) {
            roles.add(DeckRole.COMBAT_TRICK);
        }
        if (hasXInManaCost) {
            roles.add(DeckRole.X_SPELL);
        }
        if (isBoardWipeText(rules)) {
            roles.add(DeckRole.BOARD_WIPE);
            roles.add(DeckRole.REMOVAL);
        }
        if (isTokenMakerText(rules)) {
            roles.add(DeckRole.TOKEN_MAKER);
            roles.add(DeckRole.SACRIFICE_FODDER_PROVIDER);
        }
        if (isSacrificeOutletText(rules, costs)) {
            roles.add(DeckRole.SACRIFICE_OUTLET);
        }
        if (isDeathPayoffText(rules)) {
            roles.add(DeckRole.DEATH_PAYOFF);
        }
        if (isGraveyardRecursionText(rules)) {
            roles.add(DeckRole.GRAVEYARD_RECURSION);
        }
        if (isDiscardOrSelfMillText(rules, costs)) {
            roles.add(DeckRole.DISCARD_OR_SELF_MILL);
        }
        if (isLifeGainPayoffText(rules)) {
            roles.add(DeckRole.LIFE_GAIN_PAYOFF);
        }
        if (isArtifactOrEnchantmentPayoffText(rules)) {
            roles.add(DeckRole.ARTIFACT_OR_ENCHANTMENT_PAYOFF);
        }
        if (isPlusOneCounterMakerText(rules)) {
            roles.add(DeckRole.PLUS_ONE_COUNTER_MAKER);
        }
        if (isPlusOneCounterPayoffText(rules)) {
            roles.add(DeckRole.PLUS_ONE_COUNTER_PAYOFF);
        }
        if (isXSpellPayoffText(rules)) {
            roles.add(DeckRole.X_SPELL_PAYOFF);
        }
        if (isProtectionText(rules)) {
            roles.add(DeckRole.COUNTER_OR_PROTECTION);
        }
        if (isManaFixingText(rules) || (card.isLand() && producesMultipleColors(card))) {
            roles.add(DeckRole.MANA_FIXING);
        }

        for (Ability ability : card.getAbilities()) {
            for (Effect effect : ability.getAllEffects()) {
                addRoleForOutcome(roles, effect.getOutcome(), card.isLand(), rules);
            }
        }

        if (!card.isLand()
                && rules.contains("add ")
                && (rules.contains("mana") || rules.contains("{c}") || rules.contains("{w}") || rules.contains("{u}") || rules.contains("{b}") || rules.contains("{r}") || rules.contains("{g}"))) {
            roles.add(DeckRole.RAMP);
        }

        return roles;
    }

    private static Set<String> normalizeMechanics(Card card, Set<String> mechanics) {
        if (mechanics.isEmpty()) {
            return mechanics;
        }
        Set<String> normalized = new java.util.TreeSet<>(mechanics);
        if (normalized.contains("SACRIFICE") && isSelfSacrificeUtilityCard(card)) {
            normalized.remove("SACRIFICE");
        }
        return normalized;
    }

    static Set<String> classifyMechanicsFromCard(Card card) {
        Set<String> mechanics = new java.util.TreeSet<>();
        String rules = String.join(" ", card.getRules()).toLowerCase(Locale.ROOT);

        if (card.isCreature() || card.isKindred()) {
            for (SubType subType : SubType.getCreatureTypes()) {
                if (card.getSubtype().contains(subType)) {
                    mechanics.add("SUBTYPE:" + subType);
                }
                if (referencesSubtype(rules, subType)) {
                    mechanics.add("TRIBAL:" + subType);
                }
            }
        } else {
            for (SubType subType : SubType.getCreatureTypes()) {
                if (referencesSubtype(rules, subType)) {
                    mechanics.add("TRIBAL:" + subType);
                }
            }
        }

        if (rules.contains("choose a creature type")) {
            mechanics.add("TRIBAL:ANY");
        }
        if (isTokenMakerText(rules)) {
            mechanics.add("TOKENS");
        }
        if (isSacrificeOutletText(rules, getCostsText(card).toLowerCase(Locale.ROOT)) || rules.contains("sacrifice ")) {
            mechanics.add("SACRIFICE");
        }
        if (rules.contains("+1/+1 counter")) {
            mechanics.add("PLUS_ONE_COUNTERS");
        }
        if (rules.contains("graveyard")) {
            mechanics.add("GRAVEYARD");
        }
        if (rules.contains("gain life") || rules.contains("gains life") || rules.contains("gained life")) {
            mechanics.add("LIFE_GAIN");
        }
        if (rules.contains("artifact") || rules.contains("treasure") || rules.contains("clue") || rules.contains("food")) {
            mechanics.add("ARTIFACTS");
        }
        return mechanics;
    }

    private static boolean referencesSubtype(String rules, SubType subType) {
        String singular = Pattern.quote(subType.toString().toLowerCase(Locale.ROOT));
        String plural = Pattern.quote(subType.getPluralName().toLowerCase(Locale.ROOT));
        return Pattern.compile("\\b(?:" + singular + "|" + plural + ")\\b").matcher(rules).find();
    }

    private static Set<DeckRole> classifyWithRepository(Card card) {
        Set<DeckRole> generatedRoles = CardRoleRepository.getInstance().lookup(card);
        Set<DeckRole> inferredRoles = classifyFromCard(card);
        Set<DeckRole> roles;
        if (generatedRoles.isEmpty()) {
            roles = inferredRoles;
        } else if (inferredRoles.isEmpty()) {
            roles = generatedRoles;
        } else {
            roles = EnumSet.copyOf(generatedRoles);
            roles.addAll(inferredRoles);
        }
        return normalizeRoles(card, roles);
    }

    private static Set<DeckRole> normalizeRoles(Card card, Set<DeckRole> roles) {
        if (roles.isEmpty()) {
            return roles;
        }
        EnumSet<DeckRole> normalized = EnumSet.copyOf(roles);
        String rules = String.join(" ", card.getRules()).toLowerCase(Locale.ROOT);
        if (normalized.contains(DeckRole.REMOVAL) && !hasRemovalText(rules)) {
            normalized.remove(DeckRole.REMOVAL);
        }
        if (normalized.contains(DeckRole.TOKEN_MAKER) || isTokenMakerText(rules)) {
            normalized.add(DeckRole.SACRIFICE_FODDER_PROVIDER);
        }
        if (normalized.contains(DeckRole.SACRIFICE_FODDER) && !isActualSacrificeFodderText(rules)) {
            normalized.remove(DeckRole.SACRIFICE_FODDER);
        }
        return normalized;
    }

    private static void addRoleForOutcome(Set<DeckRole> roles, Outcome outcome, boolean sourceIsLand, String rules) {
        switch (outcome) {
            case DrawCard:
                roles.add(DeckRole.CARD_DRAW);
                break;
            case DestroyPermanent:
            case Exile:
            case Removal:
            case Damage:
                roles.add(DeckRole.REMOVAL);
                break;
            case PutCreatureInPlay:
                if (isTokenMakerText(rules)) {
                    roles.add(DeckRole.TOKEN_MAKER);
                    roles.add(DeckRole.SACRIFICE_FODDER_PROVIDER);
                }
                break;
            case PutManaInPool:
            case PutLandInPlay:
                if (!sourceIsLand) {
                    roles.add(DeckRole.RAMP);
                }
                break;
            case GainLife:
                roles.add(DeckRole.LIFE_GAIN);
                break;
            case Protect:
            case PreventDamage:
            case Regenerate:
                roles.add(DeckRole.COUNTER_OR_PROTECTION);
                break;
            default:
                break;
        }
    }

    private static Map<DeckSynergy, Double> buildSynergyScores(Map<DeckRole, Integer> roleCounts, int nonLandCount) {
        Map<DeckSynergy, Double> scores = new EnumMap<>(DeckSynergy.class);
        if (nonLandCount <= 0) {
            return scores;
        }
        putSynergy(scores, DeckSynergy.SACRIFICE, nonLandCount,
                count(roleCounts, DeckRole.SACRIFICE_OUTLET),
                count(roleCounts, DeckRole.SACRIFICE_FODDER) + count(roleCounts, DeckRole.SACRIFICE_FODDER_PROVIDER),
                count(roleCounts, DeckRole.DEATH_PAYOFF));
        putSynergy(scores, DeckSynergy.GRAVEYARD, nonLandCount,
                count(roleCounts, DeckRole.DISCARD_OR_SELF_MILL),
                count(roleCounts, DeckRole.GRAVEYARD_RECURSION));
        putSynergy(scores, DeckSynergy.SPELLS_MATTER, nonLandCount,
                count(roleCounts, DeckRole.CARD_DRAW),
                count(roleCounts, DeckRole.COMBAT_TRICK));
        putSynergy(scores, DeckSynergy.GO_WIDE, nonLandCount,
                count(roleCounts, DeckRole.TOKEN_MAKER),
                count(roleCounts, DeckRole.COMBAT_TRICK));
        putSynergy(scores, DeckSynergy.LIFE_GAIN, nonLandCount,
                count(roleCounts, DeckRole.LIFE_GAIN),
                count(roleCounts, DeckRole.LIFE_GAIN_PAYOFF));
        putSynergy(scores, DeckSynergy.PLUS_ONE_COUNTERS, nonLandCount,
                count(roleCounts, DeckRole.PLUS_ONE_COUNTER_MAKER),
                count(roleCounts, DeckRole.PLUS_ONE_COUNTER_PAYOFF));
        putSynergy(scores, DeckSynergy.X_SPELLS, nonLandCount,
                count(roleCounts, DeckRole.X_SPELL),
                count(roleCounts, DeckRole.X_SPELL_PAYOFF));
        return scores;
    }

    private static void putSynergy(Map<DeckSynergy, Double> scores, DeckSynergy synergy, int nonLandCount, int... counts) {
        double score = 1.0;
        for (int count : counts) {
            score *= Math.min(1.0, count / (double) Math.max(1, nonLandCount));
        }
        scores.put(synergy, score);
    }

    private static String getCostsText(Card card) {
        StringBuilder sb = new StringBuilder();
        for (Ability ability : card.getAbilities()) {
            for (Cost cost : ability.getCosts()) {
                sb.append(' ').append(cost.getText());
            }
        }
        return sb.toString();
    }

    private static boolean isTokenMakerText(String rules) {
        return rules.contains("create ") && rules.contains(" token");
    }

    private static boolean isBoardWipeText(String rules) {
        return rules.contains("destroy all")
                || rules.contains("exile all")
                || rules.contains("deals ") && rules.contains("damage to each creature")
                || rules.contains("-") && rules.contains("/-") && rules.contains("all creatures");
    }

    private static boolean hasRemovalText(String rules) {
        return isBoardWipeText(rules)
                || rules.contains("destroy target")
                || rules.contains("exile target")
                || rules.contains("deals damage to target")
                || rules.contains("deal damage to target")
                || rules.contains("fight target")
                || rules.contains("fights target")
                || rules.contains("target creature gets -")
                || rules.contains("target opponent sacrifices")
                || rules.contains("target opponents each sacrifice")
                || rules.contains("target player sacrifices")
                || rules.contains("each opponent sacrifices")
                || rules.contains("opponents each sacrifice")
                || rules.contains("each player sacrifices")
                || rules.contains("damage to each creature");
    }

    private static boolean isActualSacrificeFodderText(String rules) {
        return isGraveyardRecursionText(rules)
                || rules.contains("when ") && rules.contains("dies")
                || rules.contains("whenever ") && rules.contains("dies")
                || rules.contains("when ") && rules.contains("is put into a graveyard")
                || rules.contains("whenever ") && rules.contains("is put into a graveyard");
    }

    private static boolean isSacrificeOutletText(String rules, String costs) {
        return hasOwnSacrificePhrase(costs) || hasOwnSacrificeEffectPhrase(rules);
    }

    private static boolean hasOwnSacrificePhrase(String text) {
        return text.contains("sacrifice")
                && OWN_SACRIFICE_PATTERN.matcher(text).find()
                && SACRIFICE_SYNERGY_OBJECT_PATTERN.matcher(text).find();
    }

    private static boolean hasOwnSacrificeEffectPhrase(String text) {
        if (!text.contains("sacrifice")) {
            return false;
        }
        for (String clause : text.split("(?:<br>|\\.|;)")) {
            if (OPPONENT_SACRIFICE_PATTERN.matcher(clause).find()) {
                continue;
            }
            if (hasOwnSacrificePhrase(clause)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSelfSacrificeUtilityCard(Card card) {
        String text = (String.join(" ", card.getRules()) + " " + getCostsText(card)).toLowerCase(Locale.ROOT);
        if (!text.contains("sacrifice")) {
            return false;
        }
        boolean selfSacrifice = text.contains("sacrifice {this}") || text.contains("sacrifice this");
        if (!selfSacrifice) {
            return false;
        }
        return !text.contains("sacrifice another")
                && !text.contains("sacrifice a creature")
                && !text.contains("sacrifice any number")
                && !text.contains("sacrifice x")
                && !text.contains("sacrifice a permanent")
                && !text.contains("sacrifice an artifact")
                && !text.contains("sacrifice an enchantment");
    }

    private static boolean isDeathPayoffText(String rules) {
        return (rules.contains("dies") || rules.contains("is put into a graveyard"))
                && (rules.contains("whenever") || rules.contains("whenever another") || rules.contains("each opponent") || rules.contains("draw"));
    }

    private static boolean isGraveyardRecursionText(String rules) {
        return (rules.contains("from your graveyard") || rules.contains("from a graveyard"))
                && (rules.contains("return") || rules.contains("cast") || rules.contains("put"));
    }

    private static boolean isDiscardOrSelfMillText(String rules, String costs) {
        return rules.contains("mill ")
                || rules.contains("surveil")
                || rules.contains("discard a card")
                || costs.contains("discard");
    }

    private static boolean isLifeGainPayoffText(String rules) {
        return rules.contains("whenever you gain life") || rules.contains("if you gained life");
    }

    private static boolean isArtifactOrEnchantmentPayoffText(String rules) {
        return rules.contains("artifact you control")
                || rules.contains("artifacts you control")
                || rules.contains("enchantment you control")
                || rules.contains("enchantments you control");
    }

    private static boolean isPlusOneCounterMakerText(String rules) {
        return rules.contains("+1/+1 counter")
                && (rules.contains("put ")
                || rules.contains("add ")
                || rules.contains("move ")
                || rules.contains("double ")
                || rules.contains("proliferate"));
    }

    private static boolean isPlusOneCounterPayoffText(String rules) {
        return rules.contains("+1/+1 counter")
                && (rules.contains("for each")
                || rules.contains("with a +1/+1 counter")
                || rules.contains("with one or more +1/+1 counters")
                || rules.contains("has a +1/+1 counter")
                || rules.contains("remove a +1/+1 counter"));
    }

    private static boolean isXSpellPayoffText(String rules) {
        return rules.contains("spell with {x}")
                || rules.contains("spells with {x}")
                || rules.contains("{x} in its mana cost")
                || rules.contains("x in its mana cost");
    }

    private static boolean isProtectionText(String rules) {
        return rules.contains("counter target")
                || grantsKeyword(rules, "hexproof")
                || grantsKeyword(rules, "indestructible")
                || grantsProtectionFrom(rules)
                || rules.contains("prevent all")
                || rules.contains("regenerate ");
    }

    private static boolean grantsKeyword(String rules, String keyword) {
        return !rules.contains("lose " + keyword)
                && !rules.contains("loses " + keyword)
                && !rules.contains("can't have " + keyword)
                && !rules.contains("can't gain " + keyword)
                && (rules.contains("gain " + keyword)
                || rules.contains("gains " + keyword)
                || rules.contains("have " + keyword)
                || rules.contains("has " + keyword));
    }

    private static boolean grantsProtectionFrom(String rules) {
        return !rules.contains("lose protection from")
                && !rules.contains("loses protection from")
                && !rules.contains("can't have protection from")
                && (rules.contains("gain protection from")
                || rules.contains("gains protection from")
                || rules.contains("have protection from")
                || rules.contains("has protection from")
                || rules.contains("protection from"));
    }

    private static boolean isCombatTrickText(String rules) {
        return rules.contains("gets +")
                || rules.contains("gains first strike")
                || rules.contains("gains double strike")
                || rules.contains("gains trample")
                || rules.contains("gains deathtouch")
                || rules.contains("gains lifelink")
                || rules.contains("fight");
    }

    private static boolean isManaFixingText(String rules) {
        return rules.contains("add one mana of any color")
                || rules.contains("add one mana of any one color")
                || rules.contains("add one mana of any type")
                || rules.contains("search your library for a basic land")
                || rules.contains("search your library for a land");
    }

    private static boolean producesMultipleColors(Card card) {
        int colors = 0;
        for (mage.Mana mana : card.getMana()) {
            if (mana.getWhite() > 0) {
                colors++;
            }
            if (mana.getBlue() > 0) {
                colors++;
            }
            if (mana.getBlack() > 0) {
                colors++;
            }
            if (mana.getRed() > 0) {
                colors++;
            }
            if (mana.getGreen() > 0) {
                colors++;
            }
        }
        return colors > 1;
    }

    private static void addColorPips(Map<ColoredManaSymbol, Integer> colorPips, Card card) {
        for (String symbol : card.getManaCostSymbols()) {
            for (int i = 0; i < symbol.length(); i++) {
                ColoredManaSymbol color = ColoredManaSymbol.lookup(symbol.charAt(i));
                if (color != null && color != ColoredManaSymbol.O) {
                    increment(colorPips, color);
                }
            }
        }
    }

    private static boolean hasXInManaCost(Card card) {
        for (String symbol : card.getManaCostSymbols()) {
            if (symbol.toUpperCase(Locale.ROOT).contains("X")) {
                return true;
            }
        }
        return false;
    }

    private static int count(Map<DeckRole, Integer> counts, DeckRole role) {
        return counts.getOrDefault(role, 0);
    }

    private static void incrementRoleCard(Map<DeckRole, Map<String, Integer>> counts, DeckRole role, String cardName) {
        Map<String, Integer> cardCounts = counts.computeIfAbsent(role, key -> new LinkedHashMap<>());
        increment(cardCounts, cardName);
    }

    private static void addFeatures(Map<String, Integer> featureCounts,
                                    Map<String, Map<String, Integer>> featureCardCounts,
                                    Card card,
                                    CardFeatureVector featureVector) {
        for (Map.Entry<String, Integer> entry : featureVector.getFeatures().entrySet()) {
            String feature = entry.getKey();
            int amount = entry.getValue();
            featureCounts.put(feature, featureCounts.getOrDefault(feature, 0) + amount);
            Map<String, Integer> cardCounts = featureCardCounts.computeIfAbsent(feature, key -> new LinkedHashMap<>());
            cardCounts.put(card.getName(), cardCounts.getOrDefault(card.getName(), 0) + 1);
        }
    }

    private static void addMechanics(Map<String, Integer> mechanicCounts,
                                     Map<String, Map<String, Integer>> mechanicCardCounts,
                                     Card card,
                                     Set<String> mechanics) {
        for (String mechanic : mechanics) {
            increment(mechanicCounts, mechanic);
            mechanicCardCounts
                    .computeIfAbsent(mechanic, key -> new LinkedHashMap<>())
                    .merge(card.getName(), 1, Integer::sum);
        }
    }

    private static Map<String, Integer> asCountMap(Set<String> values) {
        Map<String, Integer> counts = new LinkedHashMap<>();
        for (String value : values) {
            counts.put(value, 1);
        }
        return counts;
    }

    private static <T> void increment(Map<T, Integer> counts, T key) {
        counts.put(key, counts.getOrDefault(key, 0) + 1);
    }
}
