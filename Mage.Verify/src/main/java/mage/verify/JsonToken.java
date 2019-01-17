package mage.verify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import static mage.verify.MtgJson.MTGJSON_IGNORE_NEW_PROPERTIES;

@JsonIgnoreProperties(ignoreUnknown = MTGJSON_IGNORE_NEW_PROPERTIES)
public class JsonToken {
    public String artist;
    public String borderColor;
    public List<String> colorIdentity;
    public List<String> colorIndicator;
    public List<String> colors;
    public String loyalty;
    public String name;
    public String number;
    public String power;
    public List<String> reverseRelated;
    public String side;
    public String text;
    public String toughness;
    public String type;
    public String uuid;
    public String watermark;
    public boolean isOnlineOnly;
    public String scryfallId;
}
