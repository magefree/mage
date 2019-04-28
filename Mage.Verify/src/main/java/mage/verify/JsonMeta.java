package mage.verify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import static mage.verify.MtgJson.MTGJSON_IGNORE_NEW_PROPERTIES;

@JsonIgnoreProperties(ignoreUnknown = MTGJSON_IGNORE_NEW_PROPERTIES)
public class JsonMeta {
    public String date;
    public String version;
}
