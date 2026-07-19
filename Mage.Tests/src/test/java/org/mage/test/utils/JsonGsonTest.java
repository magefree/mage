package org.mage.test.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import mage.util.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Gson (Google json) lib uses for scryfall and mtgjson data.
 * <p>
 * Tests:
 * - unknown data parser tests here (JsonParser.parseReader)
 * - class parser tests in mtgjson verify tests (new Gson().fromJson)
 */
public class JsonGsonTest {

    @Test
    public void test_ReadByStreamParser() {
        String sampleFileName = Paths.get("src", "test", "data", "scryfall-card.json").toString();
        try {
            // low level parser for unknown data structure
            JsonObject json = JsonParser.parseReader(new FileReader(sampleFileName)).getAsJsonObject();

            // data types
            Assertions.assertEquals("card", JsonUtil.getAsString(json, "object"), "string");
            JsonArray jsonFaces = JsonUtil.getAsArray(json, "card_faces");
            Assertions.assertEquals(60370, JsonUtil.getAsInt(json, "mtgo_id"), "int");
            Assertions.assertTrue(JsonUtil.getAsBoolean(json, "highres_image"), "boolean");
            Assertions.assertEquals(4.0, JsonUtil.getAsDouble(json, "cmc"), 0.0, "double");
            Assertions.assertNotNull(jsonFaces, "array");

            Assertions.assertEquals(2, jsonFaces.size(), "Card must have 2 faces");
            Assertions.assertEquals("Infectious Curse", JsonUtil.getAsString(jsonFaces.get(1).getAsJsonObject(), "name"), "Unknown second side");
        } catch (IOException e) {
            e.printStackTrace();
            Assertions.fail("Can't load sample json file: " + sampleFileName);
        }
    }
}
