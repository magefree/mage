package mage.abilities.costs;

import mage.abilities.Ability;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * AI-facing metadata for preselected spell cost variants.
 * <p>
 * The normal human/control-AI prompt flow remains unchanged when these tags
 * are absent. Strategic AI can copy an ability, tag the copy with a cost plan,
 * and let the regular activation path apply and validate the actual costs.
 */
public final class CastCostPlan {

    public static final int DEFAULT_MAX_REPEAT_COUNT = 4;
    public static final String OPTIONAL_ADDITIONAL_COSTS_TAG = "aiCastPlanOptionalAdditionalCosts";
    public static final String OPTIONAL_ADDITIONAL_COST_COUNTS_TAG = "aiCastPlanOptionalAdditionalCostCounts";
    public static final String VARIANT_LABEL_TAG = "aiCastPlanVariantLabel";
    public static final String SOURCE_PRESERVED_TAG = "aiCastPlanSourcePreserved";

    private CastCostPlan() {
    }

    public static Ability withOptionalAdditionalCost(Ability ability, String costKey, String variantLabel) {
        return withOptionalAdditionalCostCount(ability, costKey, 1, variantLabel);
    }

    public static Ability withOptionalAdditionalCostCount(Ability ability, String costKey, int count, String variantLabel) {
        Ability variant = ability.copy();
        setOptionalAdditionalCostCount(variant, costKey, count);
        variant.setCostsTag(VARIANT_LABEL_TAG, variantLabel);
        return variant;
    }

    public static void addOptionalAdditionalCost(Ability ability, String costKey) {
        Set<String> costs = getSelectedOptionalAdditionalCosts(ability);
        costs.add(costKey);
        ability.setCostsTag(OPTIONAL_ADDITIONAL_COSTS_TAG, new HashSet<>(costs));
        if (getOptionalAdditionalCostCount(ability, costKey) == 0) {
            setOptionalAdditionalCostCount(ability, costKey, 1);
        }
    }

    public static void setOptionalAdditionalCostCount(Ability ability, String costKey, int count) {
        if (count <= 0) {
            return;
        }
        Set<String> costs = getSelectedOptionalAdditionalCosts(ability);
        costs.add(costKey);
        ability.setCostsTag(OPTIONAL_ADDITIONAL_COSTS_TAG, new HashSet<>(costs));

        Map<String, Integer> counts = getSelectedOptionalAdditionalCostCounts(ability);
        counts.put(costKey, count);
        ability.setCostsTag(OPTIONAL_ADDITIONAL_COST_COUNTS_TAG, new HashMap<>(counts));
    }

    public static boolean isOptionalAdditionalCostSelected(Ability ability, String costKey) {
        return getOptionalAdditionalCostCount(ability, costKey) > 0
                || getSelectedOptionalAdditionalCosts(ability).contains(costKey);
    }

    public static int getOptionalAdditionalCostCount(Ability ability, String costKey) {
        Integer count = getSelectedOptionalAdditionalCostCounts(ability).get(costKey);
        if (count != null) {
            return count;
        }
        return getSelectedOptionalAdditionalCosts(ability).contains(costKey) ? 1 : 0;
    }

    public static int getOptionalAdditionalCostCountByPrefix(Ability ability, String costKeyPrefix) {
        int total = 0;
        for (Map.Entry<String, Integer> entry : getSelectedOptionalAdditionalCostCounts(ability).entrySet()) {
            if (entry.getKey().startsWith(costKeyPrefix)) {
                total += entry.getValue();
            }
        }
        if (total > 0) {
            return total;
        }
        return (int) getSelectedOptionalAdditionalCosts(ability).stream()
                .filter(costKey -> costKey.startsWith(costKeyPrefix))
                .count();
    }

    public static Ability withSourcePreserved(Ability ability) {
        ability.setCostsTag(SOURCE_PRESERVED_TAG, Boolean.TRUE);
        return ability;
    }

    public static boolean preservesSource(Ability ability) {
        Map<String, Object> tags = ability.getCostsTagMap();
        return tags != null && Boolean.TRUE.equals(tags.get(SOURCE_PRESERVED_TAG));
    }

    public static String getVariantLabel(Ability ability) {
        Map<String, Object> tags = ability.getCostsTagMap();
        if (tags == null) {
            return "";
        }
        Object value = tags.get(VARIANT_LABEL_TAG);
        return value instanceof String ? (String) value : "";
    }

    @SuppressWarnings("unchecked")
    private static Set<String> getSelectedOptionalAdditionalCosts(Ability ability) {
        Map<String, Object> tags = ability.getCostsTagMap();
        if (tags == null) {
            return new HashSet<>();
        }
        Object value = tags.get(OPTIONAL_ADDITIONAL_COSTS_TAG);
        if (!(value instanceof Set)) {
            return new HashSet<>();
        }
        return new HashSet<>((Set<String>) value);
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Integer> getSelectedOptionalAdditionalCostCounts(Ability ability) {
        Map<String, Object> tags = ability.getCostsTagMap();
        if (tags == null) {
            return new HashMap<>();
        }
        Object value = tags.get(OPTIONAL_ADDITIONAL_COST_COUNTS_TAG);
        if (!(value instanceof Map)) {
            return new HashMap<>();
        }
        return new HashMap<>((Map<String, Integer>) value);
    }
}
