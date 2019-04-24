package mage.verify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import static mage.verify.MtgJson.MTGJSON_IGNORE_NEW_PROPERTIES;

@JsonIgnoreProperties(ignoreUnknown = MTGJSON_IGNORE_NEW_PROPERTIES)
public class JsonForeignData {
    public String flavorText;
    public String language;
    public int multiverseId;
    public String name;
    public String text;
    public String type;
}
