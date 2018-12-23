package mage.verify;

import java.util.List;

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
