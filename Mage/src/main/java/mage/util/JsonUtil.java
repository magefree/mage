package mage.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author JayDi85
 */
public class JsonUtil {

    public static JsonObject getAsObject(JsonObject json, String field) {
        return json.has(field) ? json.get(field).getAsJsonObject() : null;
    }

    public static JsonArray getAsArray(JsonObject json, String field) {
        return json.has(field) ? json.get(field).getAsJsonArray() : null;
    }

    public static String getAsString(JsonObject json, String field) {
        return getAsString(json, field, "");
    }

    public static String getAsString(JsonObject json, String field, String nullValue) {
        return json.has(field) ? json.get(field).getAsString() : nullValue;
    }

    public static int getAsInt(JsonObject json, String field) {
        return getAsInt(json, field, 0);
    }

    public static int getAsInt(JsonObject json, String field, int nullValue) {
        return json.has(field) ? json.get(field).getAsInt() : nullValue;
    }

    public static double getAsDouble(JsonObject json, String field) {
        return getAsDouble(json, field, 0.0);
    }

    public static double getAsDouble(JsonObject json, String field, double nullValue) {
        return json.has(field) ? json.get(field).getAsDouble() : nullValue;
    }

    public static boolean getAsBoolean(JsonObject json, String field) {
        return getAsBoolean(json, field, false);
    }

    public static boolean getAsBoolean(JsonObject json, String field, boolean nullValue) {
        return json.has(field) ? json.get(field).getAsBoolean() : nullValue;
    }
}
