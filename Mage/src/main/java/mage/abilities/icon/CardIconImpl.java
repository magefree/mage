package mage.abilities.icon;

import java.io.Serializable;

/**
 * @author JayDi85
 */
public class CardIconImpl implements CardIcon, Serializable {

    private final CardIconType cardIconType;
    private final String text;
    private final String hint;

    public CardIconImpl(CardIconType cardIconType, String hint) {
        this(cardIconType, hint, "");
    }

    public CardIconImpl(CardIconType cardIconType, String hint, String text) {
        this.text = text;
        this.hint = hint;
        this.cardIconType = cardIconType;
    }

    public CardIconImpl(final CardIconImpl icon) {
        this.cardIconType = icon.cardIconType;
        this.text = icon.text;
        this.hint = icon.hint;
    }

    @Override
    public CardIconType getIconType() {
        return cardIconType;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getHint() {
        return hint;
    }

    @Override
    public CardIcon copy() {
        return new CardIconImpl(this);
    }
}
