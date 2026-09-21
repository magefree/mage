package mage.player.ai.jev;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Calls Jev, TypeSafe's System One model, through OpenRouter's decisions router.
 * <p>
 * The request body is the same one the TypeSafe API takes: a state to judge, a
 * map of named questions, and a model id. Answers come back under the ids that
 * were sent. See https://docs.typesafe.ai/api and
 * https://openrouter.ai/~typesafe/jev-latest
 * <p>
 * Configuration comes from the environment (a system property of the same name,
 * lowercased and dotted, also works):
 * <ul>
 * <li>{@code OPENROUTER_API_KEY} - required, https://openrouter.ai/keys</li>
 * <li>{@code JEV_MODEL} - defaults to {@code ~typesafe/jev-latest}</li>
 * <li>{@code JEV_ENDPOINT} - defaults to OpenRouter's decisions endpoint</li>
 * <li>{@code JEV_TIMEOUT_MS} - defaults to 8000</li>
 * </ul>
 * Nothing here throws: a failed call returns no answers, so the caller can fall
 * back to its own logic instead of stalling the game.
 */
public class JevClient {

    private static final Logger logger = Logger.getLogger(JevClient.class);

    // OpenRouter still serves decisions from its alpha path, it may move
    public static final String OPENROUTER_ENDPOINT = "https://openrouter.ai/api/alpha/decisions";
    public static final String OPENROUTER_MODEL = "~typesafe/jev-latest";

    private static final int MAX_ATTEMPTS = 3;
    private static final long FIRST_BACKOFF_MS = 500;

    private final Gson gson = new Gson();
    private final String endpoint;
    private final String apiKey;
    private final String model;
    private final int timeoutMillis;

    public JevClient(String endpoint, String apiKey, String model, int timeoutMillis) {
        this.endpoint = endpoint;
        this.apiKey = apiKey;
        this.model = model;
        this.timeoutMillis = timeoutMillis;
    }

    /**
     * Builds a client from the environment, or returns null when no OpenRouter
     * key is configured.
     */
    public static JevClient fromEnvironment() {
        String apiKey = setting("OPENROUTER_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            logger.warn("Jev: OPENROUTER_API_KEY is not set, JevPlayer will fall back to passing/default choices");
            return null;
        }
        String endpoint = setting("JEV_ENDPOINT");
        String model = setting("JEV_MODEL");
        // measured p90 of a call is about 0.7s, so 8s only cuts calls that have
        // already hung, and the caller's fallback is safe
        int timeout = 8000;
        String configuredTimeout = setting("JEV_TIMEOUT_MS");
        if (configuredTimeout != null) {
            try {
                timeout = Integer.parseInt(configuredTimeout.trim());
            } catch (NumberFormatException e) {
                logger.warn("Jev: bad JEV_TIMEOUT_MS value " + configuredTimeout + ", using " + timeout);
            }
        }
        return new JevClient(
                endpoint == null || endpoint.isEmpty() ? OPENROUTER_ENDPOINT : endpoint,
                apiKey,
                model == null || model.isEmpty() ? OPENROUTER_MODEL : model,
                timeout
        );
    }

    private static String setting(String name) {
        String value = System.getenv(name);
        if (value == null || value.isEmpty()) {
            value = System.getProperty(name.toLowerCase().replace('_', '.'));
        }
        return value;
    }

    /**
     * Asks every question about the same state in one call. Independent
     * questions are answered in parallel by the model.
     *
     * @return the answers by question id, empty when the call failed
     */
    public Map<String, JevAnswer> ask(Object state, Map<String, JevQuestion> questions) {
        Map<String, JevAnswer> answers = new LinkedHashMap<>();
        if (questions == null || questions.isEmpty()) {
            return answers;
        }

        JsonObject request = new JsonObject();
        request.add("state", gson.toJsonTree(state));
        request.addProperty("model", model);
        request.add("questions", gson.toJsonTree(questions));
        String body = gson.toJson(request);

        long started = System.currentTimeMillis();
        String response = post(body);
        long elapsed = System.currentTimeMillis() - started;
        if (response == null) {
            return answers;
        }
        try {
            JsonObject parsed = gson.fromJson(response, JsonObject.class);
            JsonElement answersElement = parsed == null ? null : parsed.get("answers");
            if (answersElement == null || !answersElement.isJsonObject()) {
                logger.warn("Jev: response without answers: " + abbreviate(response));
                return answers;
            }
            for (Map.Entry<String, JsonElement> entry : answersElement.getAsJsonObject().entrySet()) {
                if (entry.getValue() != null && entry.getValue().isJsonObject()) {
                    answers.put(entry.getKey(), new JevAnswer(entry.getValue().getAsJsonObject()));
                }
            }
            if (logger.isDebugEnabled()) {
                JsonElement usage = parsed.get("usage");
                logger.debug("Jev timing: " + elapsed + " ms for " + questions.size()
                        + " question(s), body " + body.length() + " chars"
                        + (usage == null ? "" : ", usage " + usage));
            }
        } catch (RuntimeException e) {
            logger.warn("Jev: can't parse response: " + abbreviate(response), e);
        }
        return answers;
    }

    /**
     * Convenience for a single choice question.
     *
     * @return the chosen option key, or null when the call failed
     */
    public String choose(Object state, String instructions, Map<String, String> options) {
        Map<String, JevAnswer> answers = ask(state,
                Collections.singletonMap("q", JevQuestion.choice(instructions, options)));
        JevAnswer answer = answers.get("q");
        return answer == null ? null : answer.getChoice();
    }

    /**
     * Convenience for a single yes/no question.
     *
     * @return probability of yes, or the default value when the call failed
     */
    public double noul(Object state, String instructions, double defaultValue) {
        Map<String, JevAnswer> answers = ask(state,
                Collections.singletonMap("q", JevQuestion.noul(instructions)));
        JevAnswer answer = answers.get("q");
        return answer == null ? defaultValue : answer.getNoul();
    }

    private String post(String body) {
        long backoff = FIRST_BACKOFF_MS;
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) new URL(endpoint).openConnection();
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(timeoutMillis);
                connection.setReadTimeout(timeoutMillis);
                connection.setDoOutput(true);
                connection.setRequestProperty("Authorization", "Bearer " + apiKey);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("X-Title", "XMage JevAI");

                byte[] payload = body.getBytes(StandardCharsets.UTF_8);
                try (OutputStream out = connection.getOutputStream()) {
                    out.write(payload);
                }

                int status = connection.getResponseCode();
                if (status >= 200 && status < 300) {
                    return read(connection.getInputStream());
                }

                String error = read(connection.getErrorStream());
                // rate limited or overloaded: back off and try again
                if ((status == 429 || status == 529 || status >= 500) && attempt < MAX_ATTEMPTS) {
                    logger.info("Jev: HTTP " + status + ", retry " + attempt + '/' + (MAX_ATTEMPTS - 1));
                    Thread.sleep(backoff);
                    backoff *= 2;
                    continue;
                }
                logger.warn("Jev: HTTP " + status + ": " + abbreviate(error));
                return null;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            } catch (IOException | RuntimeException e) {
                if (attempt < MAX_ATTEMPTS) {
                    logger.info("Jev: call failed (" + e.getMessage() + "), retry " + attempt + '/' + (MAX_ATTEMPTS - 1));
                    continue;
                }
                logger.warn("Jev: call failed", e);
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        return null;
    }

    private static String read(InputStream stream) throws IOException {
        if (stream == null) {
            return "";
        }
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line);
            }
        }
        return text.toString();
    }

    private static String abbreviate(String text) {
        if (text == null) {
            return "";
        }
        return text.length() <= 500 ? text : text.substring(0, 500) + "...";
    }

    public String getModel() {
        return model;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
