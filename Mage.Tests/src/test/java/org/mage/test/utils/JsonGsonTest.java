package org.mage.test.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import mage.util.JsonUtil;
import org.junit.Assert;
import org.junit.Test;

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
            Assert.assertEquals("string", "card", JsonUtil.getAsString(json, "object"));
            JsonArray jsonFaces = JsonUtil.getAsArray(json, "card_faces");
            Assert.assertEquals("int", 60370, JsonUtil.getAsInt(json, "mtgo_id"));
            Assert.assertTrue("boolean", JsonUtil.getAsBoolean(json, "highres_image"));
            Assert.assertEquals("double", 4.0, JsonUtil.getAsDouble(json, "cmc"), 0.0);
            Assert.assertNotNull("array", jsonFaces);

            Assert.assertEquals("Card must have 2 faces", 2, jsonFaces.size());
            Assert.assertEquals("Unknown second side", "Infectious Curse", JsonUtil.getAsString(jsonFaces.get(1).getAsJsonObject(), "name"));
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Can't load sample json file: " + sampleFileName);
        }
    }
}
