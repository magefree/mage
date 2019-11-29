package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class SnakeToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("ZEN", "KTK", "MM2", "C15", "C19"));
    }

    public SnakeToken() {
        this((String) null);
    }

    public SnakeToken(String setCode) {
        super("Snake", "1/1 green Snake creature token");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C15")) {
            setTokenType(1);
        }
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.SNAKE);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public SnakeToken(final SnakeToken token) {
        super(token);
    }

    public SnakeToken copy() {
        return new SnakeToken(this);
    }
}