package mage.abilities.icon;

import java.io.Serializable;

/**
 * @author JayDi85
 */
public class CardIconImpl implements CardIcon, Serializable {

    private final CardIconType cardIconType;
    private final String text;
    private final String hint;

    // Utility Icons
    public static final CardIconImpl FACE_DOWN = new CardIconImpl(CardIconType.OTHER_FACEDOWN, "Card is face down");
    public static final CardIconImpl COMMANDER = new CardIconImpl(CardIconType.COMMANDER, "Card is commander");
    public static final CardIconImpl RINGBEARER = new CardIconImpl(CardIconType.RINGBEARER, "Ring-bearer");

    // Ability Icons
    public static final CardIconImpl ABILITY_CREW = new CardIconImpl(CardIconType.ABILITY_CREW,
            "Crew");
    public static final CardIconImpl ABILITY_DEATHTOUCH = new CardIconImpl(CardIconType.ABILITY_DEATHTOUCH,
            "Deathtouch");
    public static final CardIconImpl ABILITY_DEFENDER = new CardIconImpl(CardIconType.ABILITY_DEFENDER, "Defender");
    public static final CardIconImpl ABILITY_DOUBLE_STRIKE = new CardIconImpl(CardIconType.ABILITY_DOUBLE_STRIKE,
            "Double Strike");
    public static final CardIconImpl ABILITY_FIRST_STRIKE = new CardIconImpl(CardIconType.ABILITY_FIRST_STRIKE,
            "First Strike");
    public static final CardIconImpl ABILITY_FLYING = new CardIconImpl(CardIconType.ABILITY_FLYING, "Flying");
    public static final CardIconImpl ABILITY_INDESTRUCTIBLE = new CardIconImpl(CardIconType.ABILITY_INDESTRUCTIBLE,
            "Indestructible");
    public static final CardIconImpl ABILITY_INFECT = new CardIconImpl(CardIconType.ABILITY_INFECT, "Infect");
    public static final CardIconImpl ABILITY_LIFELINK = new CardIconImpl(CardIconType.ABILITY_LIFELINK, "Lifelink");
    public static final CardIconImpl ABILITY_TRAMPLE = new CardIconImpl(CardIconType.ABILITY_TRAMPLE, "Trample");
    public static final CardIconImpl ABILITY_VIGILANCE = new CardIconImpl(CardIconType.ABILITY_VIGILANCE, "Vigilance");

    // "Target protection" abilities
    public static final CardIconImpl ABILITY_HEXPROOF = new CardIconImpl(CardIconType.ABILITY_HEXPROOF, "Hexproof");
    public static final CardIconImpl ABILITY_SHROUD = new CardIconImpl(CardIconType.ABILITY_HEXPROOF, "Shroud");

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

    public static CardIconImpl variableCost(int costX) {
        return new CardIconImpl(CardIconType.OTHER_COST_X, "Announced X = " + costX, "x=" + costX);
    }
}
