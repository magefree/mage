package mage.player.ai.jev;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A single System One question, serialized as-is into the request body.
 * <p>
 * See https://docs.typesafe.ai/primitives for the three question types.
 */
public class JevQuestion {

    private final String type;
    private final String instructions;
    private final Object criteria;

    private JevQuestion(String type, String instructions, Object criteria) {
        this.type = type;
        this.instructions = instructions;
        this.criteria = criteria;
    }

    /**
     * Yes/no judgement, answered with the probability of "yes".
     */
    public static JevQuestion noul(String instructions) {
        return new JevQuestion("noul", instructions, null);
    }

    public static JevQuestion noul(String instructions, String whenTrue, String whenFalse) {
        Map<String, String> criteria = new LinkedHashMap<>();
        criteria.put("true", whenTrue);
        criteria.put("false", whenFalse);
        return new JevQuestion("noul", instructions, criteria);
    }

    /**
     * Pick exactly one option. Keys are the answers the model may return,
     * values describe them.
     */
    public static JevQuestion choice(String instructions, Map<String, String> options) {
        return new JevQuestion("choice", instructions, new LinkedHashMap<>(options));
    }

    /**
     * Position on ordered levels, from lowest to highest.
     */
    public static JevQuestion score(String instructions, List<String> levels) {
        return new JevQuestion("score", instructions, new ArrayList<>(levels));
    }

    public String getType() {
        return type;
    }
}
