package mage.verify;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Map;

class JsonSet {
    public String name;
    public String code;
    public String oldCode;
    public String gathererCode;
    public String magicCardsInfoCode;
    public String[] magicRaritiesCodes;
    public String[] alternativeNames;
    public String releaseDate;
    public String border;
    public String type;
    public List<Object> booster; // [String|[String]]
    public List<JsonCard> cards;
    public String block;
    public boolean onlineOnly;
    public String mkm_id;
    public String mkm_name;
    public Map<String, String> translations;
    public int baseSetSize;
    @JsonIgnore
    public List<Booster> boosterV3;
    public String borderColor;
    public Meta meta;
    public String mtgoCode;
    public List<Token> tokens;
    public int totalSetSize;
    public boolean isOnlineOnly;
    public boolean isFoilOnly;
}
