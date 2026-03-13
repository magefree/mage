package mage.player.ai;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.cards.Card;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Minimal HTTP/JSON client for the local XMage Ollama bridge.
 * Uses JDK 8 APIs only to match XMage build constraints.
 */
final class OllamaBridgeClient {

    private static final Logger logger = Logger.getLogger(OllamaBridgeClient.class);

    private final String baseUrl;
    private final int connectTimeoutMs;
    private final int readTimeoutMs;
    private final String strategyProfile;

    OllamaBridgeClient() {
        this.baseUrl = readSetting("XMAGE_OLLAMA_BRIDGE_URL", "http://127.0.0.1:8787");
        this.connectTimeoutMs = parseIntSetting("XMAGE_OLLAMA_CONNECT_TIMEOUT_MS", 1500);
        this.readTimeoutMs = parseIntSetting("XMAGE_OLLAMA_READ_TIMEOUT_MS", 2500);
        this.strategyProfile = readSetting("XMAGE_OLLAMA_STRATEGY", "");
    }

    BridgeDecision decide(Game game, Player player, List<ActivatedAbility> playableOptions, String abilityInfoBuilderPrefix) throws IOException {
        List<Map<String, Object>> legalActions = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < playableOptions.size(); i++) {
            ActivatedAbility ability = playableOptions.get(i);
            Map<String, Object> action = new LinkedHashMap<String, Object>();
            action.put("id", "action-" + i);
            action.put("type", resolveActionType(ability));
            action.put("label", abilityInfoBuilderPrefix + ability.toString());
            action.put("manaValue", ability.getManaCosts().manaValue());
            action.put("tags", buildActionTags(ability, game));
            legalActions.add(action);
        }

        Map<String, Object> payload = new LinkedHashMap<String, Object>();
        payload.put("game", buildGameView(game, player));
        payload.put("agent", buildAgentView(player));
        payload.put("decisionContext", buildDecisionContext(game));
        payload.put("players", buildPlayersView(game));
        payload.put("stack", buildStackView(game));
        payload.put("legalActions", legalActions);

        String response = postJson("/v1/decide", SimpleJson.stringify(payload));
        return new BridgeDecision(
                SimpleJson.extractStringField(response, "actionId"),
                SimpleJson.extractStringField(response, "source"),
                SimpleJson.extractStringField(response, "reason")
        );
    }

    MulliganDecision decideMulligan(Game game, Player player, List<Card> handCards) throws IOException {
        Map<String, Object> payload = new LinkedHashMap<String, Object>();
        payload.put("game", buildMulliganGameView(game));
        payload.put("agent", buildAgentView(player));
        payload.put("mulligan", buildMulliganView(game, player, handCards));

        String response = postJson("/v1/mulligan", SimpleJson.stringify(payload));
        return new MulliganDecision(
                SimpleJson.extractStringField(response, "decision"),
                SimpleJson.extractStringField(response, "source"),
                SimpleJson.extractStringField(response, "reason")
        );
    }

    private Map<String, Object> buildGameView(Game game, Player player) {
        Map<String, Object> gameView = new LinkedHashMap<String, Object>();
        gameView.put("turn", game.getState().getTurnNum());
        gameView.put("phase", game.getTurn() != null && game.getTurn().getPhase() != null ? game.getTurn().getPhase().getType().name() : null);
        gameView.put("step", game.getTurnStepType() != null ? game.getTurnStepType().name() : null);
        gameView.put("activePlayerId", stringifyUuid(game.getActivePlayerId()));
        gameView.put("priorityPlayerId", stringifyUuid(game.getPriorityPlayerId()));
        gameView.put("format", game.getGameType() != null ? game.getGameType().toString() : null);
        gameView.put("matchType", game.getNumPlayers() > 2 ? "MULTIPLAYER" : "DUEL");
        return gameView;
    }

    private Map<String, Object> buildMulliganGameView(Game game) {
        Map<String, Object> gameView = new LinkedHashMap<String, Object>();
        gameView.put("format", game.getGameType() != null ? game.getGameType().toString() : null);
        gameView.put("matchType", "MATCH");
        return gameView;
    }

    private Map<String, Object> buildAgentView(Player player) {
        Map<String, Object> agent = new LinkedHashMap<String, Object>();
        agent.put("playerId", stringifyUuid(player.getId()));
        agent.put("name", player.getName());
        agent.put("strategyProfile", strategyProfile);
        return agent;
    }

    private Map<String, Object> buildDecisionContext(Game game) {
        Map<String, Object> context = new LinkedHashMap<String, Object>();
        context.put("turnsSinceAction", 0);
        List<Map<String, Object>> actionHistory = new ArrayList<Map<String, Object>>();
        Map<String, Object> recent = new LinkedHashMap<String, Object>();
        recent.put("turn", game.getState().getTurnNum());
        recent.put("actorId", stringifyUuid(game.getPriorityPlayerId()));
        recent.put("description", "Priority on " + (game.getTurnStepType() != null ? game.getTurnStepType().name() : "UNKNOWN"));
        actionHistory.add(recent);
        context.put("actionHistory", actionHistory);
        return context;
    }

    private List<Map<String, Object>> buildPlayersView(Game game) {
        List<Map<String, Object>> players = new ArrayList<Map<String, Object>>();
        for (UUID playerId : game.getPlayerList()) {
            Player current = game.getPlayer(playerId);
            if (current == null) {
                continue;
            }
            Map<String, Object> item = new LinkedHashMap<String, Object>();
            item.put("id", stringifyUuid(current.getId()));
            item.put("name", current.getName());
            item.put("life", current.getLife());
            item.put("poison", current.getCountersCount("poison"));
            item.put("energy", current.getCountersCount("energy"));
            item.put("handCount", current.getHand().size());
            item.put("libraryCount", current.getLibrary().size());
            item.put("battlefield", buildBattlefieldView(game, current));
            players.add(item);
        }
        return players;
    }

    private List<Map<String, Object>> buildBattlefieldView(Game game, Player player) {
        List<Map<String, Object>> battlefield = new ArrayList<Map<String, Object>>();
        for (mage.game.permanent.Permanent permanent : game.getBattlefield().getAllActivePermanents(player.getId())) {
            Map<String, Object> item = new LinkedHashMap<String, Object>();
            item.put("name", permanent.getName());
            item.put("power", permanent.getPower().getValue());
            item.put("toughness", permanent.getToughness().getValue());
            item.put("tapped", permanent.isTapped());
            item.put("summoningSick", Boolean.FALSE);
            battlefield.add(item);
        }
        return battlefield;
    }

    private List<Map<String, Object>> buildStackView(Game game) {
        List<Map<String, Object>> stack = new ArrayList<Map<String, Object>>();
        for (StackObject stackObject : game.getStack()) {
            Map<String, Object> item = new LinkedHashMap<String, Object>();
            item.put("controllerId", stringifyUuid(stackObject.getControllerId()));
            item.put("name", stackObject.getName());
            item.put("type", stackObject.getStackAbility() != null ? "ABILITY" : "SPELL");
            stack.add(item);
        }
        return stack;
    }

    private Map<String, Object> buildMulliganView(Game game, Player player, List<Card> handCards) {
        Map<String, Object> mulligan = new LinkedHashMap<String, Object>();
        mulligan.put("handSize", handCards.size());
        mulligan.put("onPlay", game.getStartingPlayerId() != null && game.getStartingPlayerId().equals(player.getId()));
        mulligan.put("freeMulligan", Boolean.FALSE);
        List<Map<String, Object>> hand = new ArrayList<Map<String, Object>>();
        for (Card card : handCards) {
            Map<String, Object> item = new LinkedHashMap<String, Object>();
            item.put("name", card.getName());
            item.put("tags", buildCardTags(card));
            hand.add(item);
        }
        mulligan.put("hand", hand);
        return mulligan;
    }

    private List<String> buildActionTags(Ability ability, Game game) {
        List<String> tags = new ArrayList<String>();
        if (ability.isUsesStack()) {
            tags.add("stack");
        }
        if (ability.isManaAbility()) {
            tags.add("mana");
        }
        String text = ability.toString().toLowerCase();
        if (text.contains("target")) {
            tags.add("target");
        }
        if (text.contains("damage")) {
            tags.add("damage");
        }
        if (text.contains("draw")) {
            tags.add("card-draw");
        }
        if (ability.getSourceId() != null) {
            Card sourceCard = game.getCard(ability.getSourceId());
            if (sourceCard != null) {
                tags.addAll(buildCardTags(sourceCard));
            }
        }
        return tags;
    }

    private List<String> buildCardTags(Card card) {
        List<String> tags = new ArrayList<String>();
        if (card.isLand()) {
            tags.add("land");
        }
        if (card.isCreature()) {
            tags.add("creature");
        }
        if (card.isInstant()) {
            tags.add("interaction");
        }
        if (card.isSorcery()) {
            tags.add("sorcery");
        }
        if (card.getManaValue() == 1) {
            tags.add("one-drop");
        }
        if (card.getCardType().contains(CardType.PLANESWALKER)) {
            tags.add("planeswalker");
        }
        return tags;
    }

    private String resolveActionType(ActivatedAbility ability) {
        if (ability.isManaAbility()) {
            return "ACTIVATE_MANA";
        }
        String text = ability.toString().toLowerCase();
        if (text.contains("play ") && text.contains("land")) {
            return "PLAY_LAND";
        }
        if (text.contains("cast")) {
            return "CAST_SPELL";
        }
        return "ACTIVATE_ABILITY";
    }

    private String postJson(String path, String payload) throws IOException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(baseUrl + path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(connectTimeoutMs);
            connection.setReadTimeout(readTimeoutMs);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(payload.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();

            int status = connection.getResponseCode();
            InputStream stream = status >= 200 && status < 300 ? connection.getInputStream() : connection.getErrorStream();
            String responseBody = readAll(stream);
            if (status != 200) {
                throw new IOException("Bridge request failed: HTTP " + status + " " + responseBody);
            }
            return responseBody;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private String readAll(InputStream stream) throws IOException {
        if (stream == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();
        return builder.toString();
    }

    private static String stringifyUuid(UUID value) {
        return value == null ? null : value.toString();
    }

    private static String readSetting(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            value = System.getenv(key);
        }
        return value == null || value.trim().isEmpty() ? defaultValue : value.trim();
    }

    private static int parseIntSetting(String key, int defaultValue) {
        String value = readSetting(key, null);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            logger.warn("Invalid integer setting for " + key + ": " + value);
            return defaultValue;
        }
    }

    static final class BridgeDecision {
        private final String actionId;
        private final String source;
        private final String reason;

        BridgeDecision(String actionId, String source, String reason) {
            this.actionId = actionId;
            this.source = source;
            this.reason = reason;
        }

        String getActionId() {
            return actionId;
        }

        String getSource() {
            return source;
        }

        String getReason() {
            return reason;
        }
    }

    static final class MulliganDecision {
        private final String decision;
        private final String source;
        private final String reason;

        MulliganDecision(String decision, String source, String reason) {
            this.decision = decision;
            this.source = source;
            this.reason = reason;
        }

        boolean isKeep() {
            return "KEEP".equalsIgnoreCase(decision);
        }

        String getDecision() {
            return decision;
        }

        String getSource() {
            return source;
        }

        String getReason() {
            return reason;
        }
    }

    private static final class SimpleJson {
        private SimpleJson() {
        }

        static String stringify(Object value) {
            if (value == null) {
                return "null";
            }
            if (value instanceof String) {
                return quote((String) value);
            }
            if (value instanceof Number || value instanceof Boolean) {
                return value.toString();
            }
            if (value instanceof Map) {
                StringBuilder builder = new StringBuilder("{");
                boolean first = true;
                for (Object entryObject : ((Map<?, ?>) value).entrySet()) {
                    Map.Entry<?, ?> entry = (Map.Entry<?, ?>) entryObject;
                    if (!first) {
                        builder.append(',');
                    }
                    builder.append(quote(String.valueOf(entry.getKey()))).append(':').append(stringify(entry.getValue()));
                    first = false;
                }
                return builder.append('}').toString();
            }
            if (value instanceof List) {
                StringBuilder builder = new StringBuilder("[");
                List<?> list = (List<?>) value;
                for (int i = 0; i < list.size(); i++) {
                    if (i > 0) {
                        builder.append(',');
                    }
                    builder.append(stringify(list.get(i)));
                }
                return builder.append(']').toString();
            }
            return quote(String.valueOf(value));
        }

        static String extractStringField(String json, String fieldName) {
            String compact = json.replace(" ", "").replace("\n", "").replace("\r", "");
            String marker = "\"" + fieldName + "\":\"";
            int start = compact.indexOf(marker);
            if (start < 0) {
                throw new IllegalArgumentException("Bridge response does not contain " + fieldName + ": " + json);
            }
            start += marker.length();
            int end = compact.indexOf('"', start);
            if (end < 0) {
                throw new IllegalArgumentException("Bridge response contains malformed " + fieldName + ": " + json);
            }
            return compact.substring(start, end);
        }

        private static String quote(String value) {
            return "\"" + value
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t") + "\"";
        }
    }
}
