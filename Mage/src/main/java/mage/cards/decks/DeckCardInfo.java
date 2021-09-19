

package mage.cards.decks;

import mage.util.Copyable;

import java.io.Serializable;

/**
 *
 * @author LevelX2
 */
public class DeckCardInfo implements Serializable, Copyable<DeckCardInfo> {

    private String cardName;
    private String setCode;
    private String cardNum;
    private int quantity;

    public DeckCardInfo() {
        super();
    }

    public DeckCardInfo(final DeckCardInfo info) {
        this.cardName = info.cardName;
        this.setCode = info.setCode;
        this.cardNum = info.cardNum;
        this.quantity = info.quantity;
    }

    public DeckCardInfo(String cardName, String cardNum, String setCode) {
        this(cardName, cardNum, setCode, 1);
    }

    public DeckCardInfo(String cardName, String cardNum, String setCode, int quantity) {
        this.cardName = cardName;
        this.cardNum = cardNum;
        this.setCode = setCode;
        this.quantity = quantity;
    }

    public String getCardName() {
        return cardName;
    }

    public String getSetCode() {
        return setCode;
    }

    public String getCardNum() {
        return cardNum;
    }

    public int getQuantity() {
        return quantity;
    }

    public DeckCardInfo increaseQuantity() {
        quantity++;
        return this;
    }

    public String getCardKey() {
        return setCode + cardNum;
    }

    @Override
    public DeckCardInfo copy() {
        return new DeckCardInfo(this);
    }
}
