package mage.verify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import static mage.verify.MtgJsonService.MTGJSON_IGNORE_NEW_PROPERTIES;

@JsonIgnoreProperties(ignoreUnknown = MTGJSON_IGNORE_NEW_PROPERTIES)
class MtgJsonCard {
    // docs: https://mtgjson.com/v4/docs.html
    public List<String> colorIdentity;
    public List<String> colors;
    public String layout;
    public String manaCost;
    public String number;
    public String power;
    public List<String> subtypes;
    public List<String> supertypes;
    public String text;
    public String toughness;
    public List<String> types;
}
