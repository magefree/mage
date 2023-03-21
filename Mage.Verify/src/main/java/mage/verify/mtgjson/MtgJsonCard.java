package mage.verify.mtgjson;

import java.util.List;

public final class MtgJsonCard {
    // v5 support
    // https://mtgjson.com/data-models/card-atomic/
    // contains only used fields, if you need more for tests then just add it here

    public String name;
    public String asciiName; // mtgjson uses it for some cards like El-Hajjaj
    public String number; // from sets source only, see https://mtgjson.com/data-models/card/

    public String faceName;
    public String side;

    public String manaCost;
    public List<String> colorIdentity;
    public List<String> colors;

    public List<String> supertypes;
    public List<String> types;
    public List<String> subtypes;

    public String text; // rules splits by \n

    public String loyalty;
    public String power;
    public String toughness;

    public Integer edhrecRank;
    public String layout;
    public boolean isFullArt;
    public List<String> printings; // set codes with that card

    public String getRealCardName() {
        // xmage split a double faced card to two different cards, but mtgjson/scryfall uses full name,
        // so use faceName property for full name searching
        if ("transform".equals(layout)
                || "flip".equals(layout)
                || "adventure".equals(layout)
                || "modal_dfc".equals(layout)
                || "reversible_card".equals(layout) // reversible_card - example: Zndrsplt, Eye of Wisdom
                || "meld".equals(layout)) { // meld - mtgjson uses composite names for meld cards, but scryfall uses simple face names
            return faceName;
        }

        return asciiName != null ? asciiName : name;
    }
}
