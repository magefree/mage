package mage.verify;

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
}
