package mage.cards;

import java.io.Serializable;
import mage.constants.Rarity;

public final class CardSetInfo implements Serializable {

    private final String name;
    private final String cardNumber;
    private final String expansionSetCode;
    private final Rarity rarity;
    private final CardGraphicInfo graphicInfo;

    public CardSetInfo(String name, String expansionSetCode, String cardNumber, Rarity rarity) {
        this(name, expansionSetCode, cardNumber, rarity, null);
    }

    public CardSetInfo(String name, String expansionSetCode, String cardNumber, Rarity rarity, CardGraphicInfo graphicInfo) {
        this.name = name;
        this.expansionSetCode = expansionSetCode;
        this.cardNumber = cardNumber;
        this.rarity = rarity;
        if (graphicInfo == null && Rarity.LAND.equals(rarity)) {
            // Workaround to get images of basic land permanents loaded
            this.graphicInfo = new CardGraphicInfo(null, true);
        } else {
            this.graphicInfo = graphicInfo;
        }
    }

    public String getName() {
        return this.name;
    }

    public String getExpansionSetCode() {
        return this.expansionSetCode;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public Rarity getRarity() {
        return this.rarity;
    }

    public CardGraphicInfo getGraphicInfo() {
        return this.graphicInfo;
    }
}
