package mage.verify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import static mage.verify.MtgJson.MTGJSON_IGNORE_NEW_PROPERTIES;

@JsonIgnoreProperties(ignoreUnknown = MTGJSON_IGNORE_NEW_PROPERTIES)
public class JsonLegalities {
    @JsonProperty("1v1")
    public String oneVersusOne;
    public String brawl;
    public String commander;
    public String duel;
    public String frontier;
    public String future;
    public String legacy;
    public String modern;
    public String penny;
    public String pauper;
    public String standard;
    public String vintage;
    public String oldschool;
}
