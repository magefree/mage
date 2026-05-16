package mage.cards.decks.analysis;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Sparse, explainable card features used as the generic substrate for deck profiles.
 */
public final class CardFeatureVector implements Serializable {

    private static final int HIGH_MANA_VALUE_BUCKET = 7;
    private static final Pattern NON_WORD = Pattern.compile("[^a-z0-9]+");

    private static final TextSignal[] RULE_VERBS = {
            signal("verb:attack", "attack(?:s|ed|ing)?"),
            signal("verb:add", "add(?:s|ed|ing)?"),
            signal("verb:block", "block(?:s|ed|ing)?"),
            signal("verb:cast", "cast(?:s|ing)?"),
            signal("verb:copy", "cop(?:y|ies|ied)"),
            signal("verb:create", "creat(?:e|es|ed|ing)"),
            signal("verb:destroy", "destroy(?:s|ed|ing)?"),
            signal("verb:discard", "discard(?:s|ed|ing)?"),
            signal("verb:draw", "draw(?:s|ing)?|drawn"),
            signal("verb:exile", "exil(?:e|es|ed|ing)"),
            signal("verb:fight", "fight(?:s|ing)?|fought"),
            signal("verb:gain", "gain(?:s|ed|ing)?"),
            signal("verb:mill", "mill(?:s|ed|ing)?"),
            signal("verb:prevent", "prevent(?:s|ed|ing)?"),
            signal("verb:proliferate", "proliferat(?:e|es|ed|ing)"),
            signal("verb:return", "return(?:s|ed|ing)?"),
            signal("verb:sacrifice", "sacrific(?:e|es|ed|ing)"),
            signal("verb:scry", "scr(?:y|ies|ied)"),
            signal("verb:search", "search(?:es|ed|ing)?"),
            signal("verb:surveil", "surveil(?:s|ed|ing)?"),
            signal("verb:tap", "tap(?:s|ped|ping)?"),
            signal("verb:untap", "untap(?:s|ped|ping)?")
    };

    private static final TextSignal[] RULE_OBJECTS = {
            signal("object:artifact", "artifacts?"),
            signal("object:battle", "battles?"),
            signal("object:card", "cards?"),
            signal("object:commander", "commanders?"),
            signal("object:counter", "counters?"),
            signal("object:creature", "creatures?"),
            signal("object:enchantment", "enchantments?"),
            signal("object:equipment", "equipment"),
            signal("object:graveyard", "graveyards?"),
            signal("object:land", "lands?"),
            signal("object:library", "libraries|library"),
            signal("object:mana", "mana"),
            signal("object:opponent", "opponents?"),
            signal("object:permanent", "permanents?"),
            signal("object:player", "players?"),
            signal("object:spell", "spells?"),
            signal("object:token", "tokens?")
    };

    private static final TextSignal[] KEYWORDS = {
            signal("keyword:deathtouch", "deathtouch"),
            signal("keyword:defender", "defender"),
            signal("keyword:double_strike", "double\\s+strike"),
            signal("keyword:first_strike", "first\\s+strike"),
            signal("keyword:flash", "flash"),
            signal("keyword:flying", "flying"),
            signal("keyword:haste", "haste"),
            signal("keyword:hexproof", "hexproof"),
            signal("keyword:indestructible", "indestructible"),
            signal("keyword:lifelink", "lifelink"),
            signal("keyword:menace", "menace"),
            signal("keyword:reach", "reach"),
            signal("keyword:trample", "trample"),
            signal("keyword:vigilance", "vigilance"),
            signal("keyword:ward", "ward")
    };

    private static final Pattern COUNTER_SPELL_PATTERN = Pattern.compile("\\bcounter(?:s|ed|ing)?\\s+(?:target|all|each|that|up to|activated|triggered|spell|ability)\\b");

    private final Map<String, Integer> features;

    private CardFeatureVector(Map<String, Integer> features) {
        this.features = Collections.unmodifiableMap(new LinkedHashMap<>(features));
    }

    public static CardFeatureVector from(Card card) {
        Map<String, Integer> features = new LinkedHashMap<>();
        add(features, "manaValue:" + Math.min(card.getManaValue(), HIGH_MANA_VALUE_BUCKET));
        add(features, "manaValueBand:" + manaValueBand(card.getManaValue()));
        addColors(features, card.getColor());
        for (CardType cardType : card.getCardType()) {
            add(features, "type:" + normalize(cardType.name()));
        }
        for (SuperType superType : card.getSuperType()) {
            add(features, "supertype:" + normalize(superType.name()));
        }
        for (SubType subType : card.getSubtype()) {
            add(features, "subtype:" + normalize(subType.toString()));
        }
        for (String symbol : card.getManaCostSymbols()) {
            add(features, "costSymbol:" + normalize(symbol));
        }
        for (Ability ability : card.getAbilities()) {
            addAbilityFeatures(features, ability);
        }
        addRulesTextFeatures(features, String.join(" ", card.getRules()).toLowerCase(Locale.ROOT));
        return new CardFeatureVector(features);
    }

    public Map<String, Integer> getFeatures() {
        return features;
    }

    private static void addAbilityFeatures(Map<String, Integer> features, Ability ability) {
        String rule = ability.getRule();
        if (rule != null && !rule.isEmpty()) {
            addRulesTextFeatures(features, rule.toLowerCase(Locale.ROOT));
        }
        for (Effect effect : ability.getAllEffects()) {
            Outcome outcome = effect.getOutcome();
            if (outcome != null) {
                add(features, "outcome:" + normalize(outcome.name()));
            }
        }
    }

    private static void addRulesTextFeatures(Map<String, Integer> features, String rules) {
        if (rules == null || rules.isEmpty()) {
            return;
        }
        for (TextSignal keyword : KEYWORDS) {
            keyword.addIfPresent(features, rules);
        }
        for (TextSignal verb : RULE_VERBS) {
            if ("verb:counter".equals(verb.feature)) {
                continue;
            }
            verb.addIfPresent(features, rules);
        }
        if (COUNTER_SPELL_PATTERN.matcher(rules).find()) {
            add(features, "verb:counter");
        }
        for (TextSignal object : RULE_OBJECTS) {
            object.addIfPresent(features, rules);
        }
        if (rules.contains("+1/+1 counter")) {
            add(features, "counter:+1/+1");
        }
        if (rules.contains("-1/-1 counter")) {
            add(features, "counter:-1/-1");
        }
        if (rules.contains("{x}") || rules.contains(" x ")) {
            add(features, "scales:x");
        }
        if (rules.contains("from your graveyard") || rules.contains("from a graveyard")) {
            add(features, "zone:graveyard");
        }
        if (rules.contains("from your library") || rules.contains("search your library")) {
            add(features, "zone:library");
        }
        if (rules.contains("from your hand")) {
            add(features, "zone:hand");
        }
        if (rules.contains("until end of turn")) {
            add(features, "duration:end_of_turn");
        }
    }

    private static void addColors(Map<String, Integer> features, ObjectColor color) {
        if (color == null || color.isColorless()) {
            add(features, "color:colorless");
            return;
        }
        if (color.isWhite()) {
            add(features, "color:white");
        }
        if (color.isBlue()) {
            add(features, "color:blue");
        }
        if (color.isBlack()) {
            add(features, "color:black");
        }
        if (color.isRed()) {
            add(features, "color:red");
        }
        if (color.isGreen()) {
            add(features, "color:green");
        }
        if (color.isMulticolored()) {
            add(features, "color:multicolor");
        }
    }

    private static String manaValueBand(int manaValue) {
        if (manaValue <= 1) {
            return "cheap";
        }
        if (manaValue <= 3) {
            return "early";
        }
        if (manaValue <= 5) {
            return "mid";
        }
        return "late";
    }

    private static void add(Map<String, Integer> features, String feature) {
        if (feature == null || feature.isEmpty()) {
            return;
        }
        features.put(feature, features.getOrDefault(feature, 0) + 1);
    }

    private static String normalize(String value) {
        return NON_WORD.matcher(value.toLowerCase(Locale.ROOT)).replaceAll("_").replaceAll("^_|_$", "");
    }

    private static TextSignal signal(String feature, String expression) {
        return new TextSignal(feature, Pattern.compile("\\b(?:" + expression + ")\\b"));
    }

    private static final class TextSignal {
        private final String feature;
        private final Pattern pattern;

        private TextSignal(String feature, Pattern pattern) {
            this.feature = feature;
            this.pattern = pattern;
        }

        private void addIfPresent(Map<String, Integer> features, String rules) {
            if (pattern.matcher(rules).find()) {
                add(features, feature);
            }
        }
    }
}
