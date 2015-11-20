package mage.verify;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

class JsonSet {

    static Map<String, JsonSet> loadAll() throws IOException {
        return new ObjectMapper().readValue(
                JsonSet.class.getResourceAsStream("AllSets.json"),
                new TypeReference<Map<String, JsonSet>>() {});
    }

    public String name;
    public String code;
    public String oldCode;
    public String gathererCode;
    public String magicCardsInfoCode;
    public String[] magicRaritiesCodes;
    public String releaseDate;
    public String border;
    public String type;
    public List<Object> booster; // [String|[String]]
    public List<JsonCard> cards;
    public String block;
    public boolean onlineOnly;
}
