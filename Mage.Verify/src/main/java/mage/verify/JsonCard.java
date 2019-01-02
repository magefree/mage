package mage.verify;

import java.util.List;

class JsonCard {
    // docs: https://mtgjson.com/v4/docs.html

    public String artist;
    public String borderColor;
    public List<String> colorIdentity;
    public List<String> colorIndicator;
    public List<String> colors;
    public float convertedManaCost;
    public float faceConvertedManaCost;
    public String flavorText;
    public List<JsonForeignData> foreignData;
    public String frameVersion;
    public boolean hasFoil;
    public boolean hasNonFoil;
    public boolean isOnlineOnly;
    public boolean isOversized;
    public boolean isReserved;
    public boolean isTimeshifted;
    public String layout;
    public JsonLegalities legalities;
    public String loyalty;
    public String manaCost;
    public int multiverseId;
    public String name;
    public List<String> names;
    public String number;
    public String originalText;
    public String originalType;
    public List<String> printings;
    public String power;
    public String rarity;
    public boolean starter;
    public String side;
    public List<JsonRuling> rulings;
    public List<String> subtypes;
    public List<String> supertypes;
    public String text;
    public String toughness;
    public String type;
    public List<String> types;
    public String uuid;
    public List<String> variations;
    public String watermark;
    public String tcgplayerProductId;
    public String scryfallId;
    public boolean isAlternative;
    public String frameEffect;
}
