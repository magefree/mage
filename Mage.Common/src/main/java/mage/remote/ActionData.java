
package mage.remote;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import java.util.UUID;

public class ActionData {

    @Expose
    public UUID gameId;
    @Expose
    public String sessionId;
    @Expose
    public String type;
    @Expose
    public Object value;
    @Expose
    public String message;

    public String toJson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setExclusionStrategies(new CustomExclusionStrategy()).create();

        return gson.toJson(this);
    }

    public ActionData(String type, UUID gameId, String sessionId) {
        this.type = type;
        this.sessionId = sessionId;
        this.gameId = gameId;
    }

    public ActionData(String type, UUID gameId) {
        this.type = type;
        this.gameId = gameId;
    }

    static class CustomExclusionStrategy implements ExclusionStrategy {

        // FIXME: Very crude way of whitelisting, as it applies to all levels of the JSON tree.
        private final java.util.Set<String> KEEP = new java.util.HashSet<>(
                java.util.Arrays.asList(
                        new String[]{
                            "id",
                            "choice",
                            "damage",
                            "abilityType",
                            "ability",
                            "abilities",
                            "method",
                            "data",
                            "options",
                            "life",
                            "players",
                            "zone",
                            "step",
                            "phase",
                            "attackers",
                            "blockers",
                            "tapped",
                            "damage",
                            "combat",
                            "paid",
                            "hand",
                            "stack",
                            "manaValue",
                            "gameId",
                            "canPlayInHand",
                            "gameView",
                            "sessionId",
                            "power",
                            "choices",
                            "targets",
                            "loyalty",
                            "toughness",
                            "power",
                            "type",
                            "priorityTime",
                            "manaCost",
                            "value",
                            "message",
                            "cardsView",
                            "name",
                            "count",
                            "counters",
                            "battlefield",
                            "parentId"
                        }));

        public CustomExclusionStrategy() {
        }

        // This method is called for all fields. if the method returns true the
        // field is excluded from serialization
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            String name = f.getName();
            return !KEEP.contains(name);
        }

        // This method is called for all classes. If the method returns true the
        // class is excluded.
        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    }
}
