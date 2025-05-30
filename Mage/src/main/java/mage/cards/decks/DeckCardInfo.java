package mage.cards.decks;

import mage.util.Copyable;

import java.io.Serializable;

/**
 * Client side: card record in deck file
 *
 * @author LevelX2
 */
public class DeckCardInfo implements Serializable, Copyable<DeckCardInfo> {

    static final int MAX_AMOUNT_PER_CARD = 99;

    private String cardName;
    private String setCode;
    private String cardNumber;
    private int amount;

    public DeckCardInfo() {
        super();
    }

    protected DeckCardInfo(final DeckCardInfo info) {
        this.cardName = info.cardName;
        this.setCode = info.setCode;
        this.cardNumber = info.cardNumber;
        this.amount = info.amount;
    }

    public DeckCardInfo(String cardName, String cardNumber, String setCode) {
        this(cardName, cardNumber, setCode, 1);
    }

    public static void makeSureCardAmountFine(int amount, String cardName) {
        // runtime check
        if (amount > MAX_AMOUNT_PER_CARD) {
            // xmage uses 1 for amount all around, but keep that protection anyway
            throw new IllegalArgumentException("Found too big amount for a deck's card: " + cardName + " - " + amount);
        }
    }

    public DeckCardInfo(String cardName, String cardNumber, String setCode, int amount) {
        makeSureCardAmountFine(amount, cardName);

        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.setCode = setCode;
        this.amount = amount;
    }

    public String getCardName() {
        return cardName;
    }

    public String getSetCode() {
        return setCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getAmount() {
        return amount;
    }

    public String getCardKey() {
        return setCode + cardNumber;
    }

    @Override
    public DeckCardInfo copy() {
        return new DeckCardInfo(this);
    }
}
