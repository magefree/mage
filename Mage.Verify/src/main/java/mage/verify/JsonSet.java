package mage.verify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import static mage.verify.MtgJson.MTGJSON_IGNORE_NEW_PROPERTIES;

@JsonIgnoreProperties(ignoreUnknown = MTGJSON_IGNORE_NEW_PROPERTIES)
class JsonSet {
    public int baseSetSize;
    public String block;
    public List<Object> boosterV3; // [["rare", "mythic rare"], "uncommon", "uncommon", "uncommon", "common"]
    public List<JsonCard> cards;
    public String code;
    public boolean isFoilOnly;
    public boolean isOnlineOnly;
    public JsonMeta meta;
    public String mtgoCode;
    public String name;
    public String releaseDate;
    public List<JsonToken> tokens;
    public int totalSetSize;
    public String type;
    public String tcgplayerGroupId;
}
