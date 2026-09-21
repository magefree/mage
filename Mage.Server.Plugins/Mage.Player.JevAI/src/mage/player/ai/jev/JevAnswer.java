package mage.player.ai.jev;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * One typed answer from Jev. Only the field matching the question type is filled.
 */
public class JevAnswer {

    private final String type;
    private final String choice;
    private final double noul;
    private final double score;
    private final double confidence;
    private final Map<String, Double> probabilities = new LinkedHashMap<>();

    JevAnswer(JsonObject json) {
        this.type = string(json, "type");
        this.choice = string(json, "choice");
        this.noul = number(json, "noul");
        this.score = number(json, "score");
        this.confidence = number(json, "confidence");
        JsonElement probs = json.get("probabilities");
        if (probs != null && probs.isJsonObject()) {
            for (Map.Entry<String, JsonElement> entry : probs.getAsJsonObject().entrySet()) {
                if (entry.getValue() != null && entry.getValue().isJsonPrimitive()) {
                    this.probabilities.put(entry.getKey(), entry.getValue().getAsDouble());
                }
            }
        }
    }

    private static String string(JsonObject json, String field) {
        JsonElement element = json.get(field);
        return element == null || element.isJsonNull() ? null : element.getAsString();
    }

    private static double number(JsonObject json, String field) {
        JsonElement element = json.get(field);
        return element == null || element.isJsonNull() ? 0.0 : element.getAsDouble();
    }

    public String getType() {
        return type;
    }

    /**
     * The selected option key of a choice question, or null.
     */
    public String getChoice() {
        return choice;
    }

    /**
     * Probability that the answer to a noul question is yes.
     */
    public double getNoul() {
        return noul;
    }

    /**
     * Probability weighted position on the score levels, starting at 0.
     */
    public double getScore() {
        return score;
    }

    /**
     * How concentrated the distribution is. Not a correctness guarantee.
     */
    public double getConfidence() {
        return confidence;
    }

    public Map<String, Double> getProbabilities() {
        return probabilities;
    }

    @Override
    public String toString() {
        if ("noul".equals(type)) {
            return "noul=" + noul;
        }
        if ("score".equals(type)) {
            return "score=" + score + " (confidence " + confidence + ')';
        }
        return "choice=" + choice + " (confidence " + confidence + ')';
    }
}
