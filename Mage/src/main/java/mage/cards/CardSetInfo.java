package mage.cards;

import mage.constants.Rarity;
import mage.util.Copyable;

import java.io.Serializable;

public final class CardSetInfo implements Serializable, Copyable<CardSetInfo> {

    private final String name;
    private final String cardNumber;
    private final String expansionSetCode;
    private final Rarity rarity;
    private final CardGraphicInfo graphicInfo;

    public CardSetInfo(String name, CardSetInfo cardSetInfo) {
        this(name, cardSetInfo.expansionSetCode, cardSetInfo.cardNumber, cardSetInfo.rarity, cardSetInfo.graphicInfo);
    }

    public CardSetInfo(String name, String expansionSetCode, String cardNumber, Rarity rarity) {
        this(name, expansionSetCode, cardNumber, rarity, null);
    }

    public CardSetInfo(String name, String expansionSetCode, String cardNumber, Rarity rarity, CardGraphicInfo graphicInfo) {
        this.name = name;
        this.expansionSetCode = expansionSetCode;
        this.cardNumber = cardNumber;
        this.rarity = rarity;
        this.graphicInfo = graphicInfo;
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

    private CardSetInfo(final CardSetInfo info) {
        this.name = info.name;
        this.expansionSetCode = info.expansionSetCode;
        this.cardNumber = info.cardNumber;
        this.rarity = info.rarity;
        this.graphicInfo = info.getGraphicInfo() != null ? info.getGraphicInfo().copy() : null;
    }

    @Override
    public CardSetInfo copy() {
        return new CardSetInfo(this);
    }
}
