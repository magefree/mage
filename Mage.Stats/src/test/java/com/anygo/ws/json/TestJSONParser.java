package com.anygo.ws.json;

import com.xmage.ws.util.json.JSONParser;
import com.xmage.ws.util.json.JSONValidationException;
import junit.framework.Assert;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.Test;

/**
 *
 * @author noxx
 */
public class TestJSONParser {

    @Test
    public void testParse() throws Exception {
        JSONParser parser = new JSONParser();
        parser.parseJSON("{}");
        parser.parseJSON("{\"test\" : 1}");
        parser.parseJSON("{\"test\" : \"test\"}");
        parser.parseJSON("{\"list\" : [\"1\", \"2\", \"3\"]}");
        parser.parseJSON("{test:test}");

        testError(parser, "{");
        testError(parser, "}");
        testError(parser, "{{}");
        testError(parser, "{\"test\" : [}}");
    }

    @Test
    public void testQueryForInt() throws Exception {
        JSONParser parser = new JSONParser();
        parser.parseJSON("{\"test\" : 1}");
        Assert.assertEquals(1, parser.getInt("test"));

        parser = new JSONParser();
        parser.parseJSON("{test : { internal : {level : 2}}}");
        Assert.assertEquals(2, parser.getInt("test.internal.level"));
        Assert.assertFalse("No cache should have been used", parser.isHitCache());

        Assert.assertEquals(2, parser.getInt("test.internal.level"));
        Assert.assertTrue("Cache should have been used this time!", parser.isHitCache());
    }

    @Test
    public void testQueryForJSONArray() throws Exception {
        JSONParser parser = new JSONParser();
        parser.parseJSON("{\"test\" : [\"1\", \"2\", \"3\"]}");
        Assert.assertTrue(parser.getJSONArray("test") instanceof JSONArray);
        Assert.assertEquals("1", parser.getJSONArray("test").get(0));

        parser = new JSONParser();
        parser.parseJSON("{\"test\" : [1,2,3]}");
        Assert.assertTrue(parser.getJSONArray("test") instanceof JSONArray);
        Assert.assertFalse(parser.isHitCache());
        Assert.assertEquals(2, parser.getJSONArray("test").get(1));
        Assert.assertTrue(parser.isHitCache());

        Assert.assertTrue(parser.getJSONArray("test") instanceof JSONArray);
        Assert.assertEquals(2, parser.getJSONArray("test").get(1));
        Assert.assertTrue(parser.isHitCache());

        parser = new JSONParser();
        parser.parseJSON("{\"test\" : [{second_level: \"3\"}, {\"third_level\" : 2}]}");
        Assert.assertTrue(parser.getJSONArray("test") instanceof JSONArray);
        Assert.assertTrue(parser.getJSONArray("test").get(0) instanceof JSONObject);
        Assert.assertEquals(2, parser.getInt("test[1].third_level"));
        Assert.assertEquals("3", parser.getString("test[0].second_level"));

        parser = new JSONParser();
        parser.parseJSON("{\"test\" : [{1:1},{1:1},{1:1},{1:1},{1:1},{1:1},{1:1},{1:1},{1:1},{2:3},{4:5}]}");
        Assert.assertTrue(parser.getJSONArray("test") instanceof JSONArray);
        Assert.assertEquals(5, parser.getInt("test[10].4"));
    }

    @Test
    //TODO: implement
    public void testErrors() throws Exception {
        //JSONParser parser = new JSONParser();
        //parser.parseJSON("{test : { internal : {level : \"2\"}}}");
        //parser.getInt("test.internal.level");
    }

    @Test
    public void testExtendedCache() throws Exception {
        JSONParser parser = new JSONParser();
        parser.parseJSON("{test : { internal : {level : 2}}}");
        Assert.assertEquals(2, parser.getInt("test.internal.level"));
        Assert.assertFalse("No cache should have been used", parser.isHitCache());

        Assert.assertTrue(parser.getJSON("test") instanceof JSONObject);
        Assert.assertFalse("No cache should have been used", parser.isHitCache());
        Assert.assertTrue(parser.getJSON("test.internal") instanceof JSONObject);
        Assert.assertFalse("No cache should have been used", parser.isHitCache());

        parser = new JSONParser();
        parser.parseJSON("{test : { internal : {level : 2}}}");
        parser.setCachePolicy(JSONParser.CachePolicy.CACHE_ALL_LEVELS);
        Assert.assertEquals(2, parser.getInt("test.internal.level"));
        Assert.assertFalse("No cache should have been used", parser.isHitCache());

        Assert.assertTrue(parser.getJSON("test") instanceof JSONObject);
        Assert.assertTrue("Cache should have been used this time!", parser.isHitCache());
        Assert.assertTrue(parser.getJSON("test.internal") instanceof JSONObject);
        Assert.assertTrue("Cache should have been used this time!", parser.isHitCache());
    }

    @Test
    public void testExtendedIndexes() throws Exception {
        JSONParser parser = new JSONParser();
        parser.parseJSON("{\"test\" : [1,2,3,4,5]}");
        Assert.assertEquals(1, parser.getInt("test[].$first"));
        Assert.assertEquals(2, parser.getInt("test[].$second"));
        Assert.assertEquals(3, parser.getInt("test[].$third"));
        Assert.assertEquals(4, parser.getInt("test[].$fourth"));
        Assert.assertEquals(5, parser.getInt("test[].$fifth"));

        parser = new JSONParser();
        parser.parseJSON("{\"test\" : [{1:1},{2:2},{3:3},{4:4},{5:5}]}");
        Assert.assertEquals(1, parser.getInt("test[].$first.1"));
        Assert.assertEquals(2, parser.getInt("test[].$second.2"));
        Assert.assertEquals(3, parser.getInt("test[].$third.3"));
        Assert.assertEquals(4, parser.getInt("test[].$fourth.4"));
        Assert.assertEquals(5, parser.getInt("test[].$fifth.5"));

        parser = new JSONParser();
        parser.parseJSON("{\"contacts\": {\"phones\": [\n" +
                "   {\"phone\": \"100000\"},\n" +
                "   {\"phone\": \"+7 999 1234567\"}\n" +
                "  ]}}");

        Assert.assertEquals("100000", parser.getString("contacts.phones[].$first.phone"));

    }

    private void testError(JSONParser parser, String jsonToTest) throws Exception {
        try {
            parser.parseJSON(jsonToTest);
            Assert.assertTrue("Should have thrown an exception", false);
        } catch (JSONValidationException j) {
            // ok
        }
    }
}
