package mage.verify.mtgjson;

import mage.constants.Rarity;

import java.util.List;

/**
 * MTGJSON v5: card class
 * <p>
 * Contains card related data nd only used fields, if you need more for tests then just add it here
 * <p>
 * API docs <a href="https://mtgjson.com/data-models/card/card-set/">here</a>
 *
 * @author JayDi85
 */
public final class MtgJsonCard {
    public String name;
    public String asciiName; // mtgjson uses it for some cards like El-Hajjaj
    public String number; // from sets source only, see https://mtgjson.com/data-models/card-set/
    public String rarity; // from sets source only, see https://mtgjson.com/data-models/card-set/

    public String faceName;
    public String side;

    public String manaCost;
    public List<String> colorIdentity;
    public List<String> colors;

    public List<String> supertypes;
    public List<String> types;
    public List<String> subtypes;

    public String text; // rules splits by \n, can be null on empty abilities list

    public String loyalty;
    public String defense;
    public String power;
    public String toughness;

    public Integer edhrecRank;
    public String layout;
    public boolean isFullArt;
    public String frameVersion;
    public List<String> printings; // set codes with that card
    public boolean isFunny;

    @Override
    public String toString() {
        return number + " - " + this.getNameAsFull()
                + (this.getNameAsFull().equals(this.getNameAsFace()) ? "" : String.format(" (face: %s)", this.getNameAsFace()));
    }

    /**
     * @return single side name like Ice from Fire // Ice
     */
    public String getNameAsFace() {
        // return single side name
        return faceName != null ? faceName : (asciiName != null ? asciiName : name);
    }

    /**
     * @return full card name like Fire // Ice
     */
    public String getNameAsFull() {
        // xmage split a double faced card to two different cards, but mtgjson/scryfall uses full name,
        // so use faceName property for full name searching
        if ("transform".equals(layout)
                || "flip".equals(layout)
                || "adventure".equals(layout)
                || "modal_dfc".equals(layout)
                || "reversible_card".equals(layout) // reversible_card - example: Zndrsplt, Eye of Wisdom
                || "meld".equals(layout)) { // meld - mtgjson uses composite names for meld cards, but scryfall uses simple face names
            return getNameAsFace();
        }

        return getNameAsASCII();
    }

    public String getNameAsUnicode() {
        return this.name;
    }

    public String getNameAsASCII() {
        return this.asciiName != null ? this.asciiName : this.name;
    }

    public boolean isUseUnicodeName() {
        return this.asciiName != null && this.name != null && !this.asciiName.equals(this.name);
    }

    /**
     * @return the Rarity of the card if present in the mtgjson file
     * null if not present.
     */
    public Rarity getRarity() {
        if (rarity.isEmpty()) {
            return null;
        }

        switch (rarity) {
            case "common":
                return Rarity.COMMON;
            case "uncommon":
                return Rarity.UNCOMMON;
            case "rare":
                return Rarity.RARE;
            case "mythic":
                return Rarity.MYTHIC;
            case "special":
                return Rarity.SPECIAL;
            case "bonus":
                return Rarity.BONUS;

            default: // Maybe a new rarity has been introduced?
                throw new EnumConstantNotPresentException(Rarity.class, rarity);
        }
    }
}
